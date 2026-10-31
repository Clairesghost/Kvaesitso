package de.mm20.launcher2.ui.vpd.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.applications.AppRepository
import de.mm20.launcher2.icons.IconService
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.ui.VpdColour
import de.mm20.launcher2.preferences.ui.VpdHomeSettings
import de.mm20.launcher2.preferences.ui.VpdListSettings
import de.mm20.launcher2.preferences.ui.VpdLookSettings
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.searchable.PinnedLevel
import de.mm20.launcher2.services.favorites.FavoritesService
import de.mm20.launcher2.ui.vpd.usage.VpdUsage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VpdHomeVM(application: Application) : AndroidViewModel(application), KoinComponent {
    private val favoritesService: FavoritesService by inject()
    private val iconService: IconService by inject()
    private val homeSettings: VpdHomeSettings by inject()
    private val lookSettings: VpdLookSettings by inject()
    private val listSettings: VpdListSettings by inject()
    private val appRepository: AppRepository by inject()

    /** Old order (D-180): pinned first, then most used. Extra long: owner-set discs take slots. */
    private val favouriteApps = favoritesService.getFavorites(
        includeTypes = listOf("app"),
        minPinnedLevel = PinnedLevel.FrequentlyUsed,
        limit = 2 * DIAL_SLOTS,
    )

    /** Packages by real usage, most used first (D-196). null = not read yet. */
    private val usageRank = MutableStateFlow<List<String>?>(null)

    /** Whether the owner switched on Usage access for the launcher. */
    val usageAccess = MutableStateFlow(VpdUsage.hasAccess(application))

    /**
     * Reads the usage ranking off the main thread. Called once per Home resume; no timers (AF-15).
     */
    fun refreshUsage() {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val (access, ranked) = withContext(Dispatchers.Default) {
                val access = VpdUsage.hasAccess(context)
                access to if (access) VpdUsage.rankedPackages(context) else emptyList()
            }
            usageAccess.value = access
            usageRank.value = ranked
        }
    }

    /** What is saved: 12 keys, "" = fill automatically, "-" = empty on purpose, null = nothing saved. */
    val dialSaved = listSettings.dialApps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /**
     * The 12 dial discs, null = blank disc. Discs the owner set stay. Every automatic disc shows
     * the next most-used app that is not already on the dial; without usage access the old
     * favourites order fills in (D-180, D-196). "-" keeps a disc blank.
     */
    val dialApps = combine(
        listSettings.dialApps,
        appRepository.findMany(),
        favouriteApps,
        usageRank,
    ) { saved, installed, favourites, ranked ->
        val byKey = installed.associateBy { it.key }
        val keys = List(DIAL_SLOTS) { saved?.getOrNull(it) ?: "" }
        val slots = arrayOfNulls<SavableSearchable>(DIAL_SLOTS)
        val taken = HashSet<String>()
        for (i in 0 until DIAL_SLOTS) {
            val app = if (keys[i] == "" || keys[i] == BLANK) null else byKey[keys[i]]
            if (app != null) {
                slots[i] = app
                taken += app.key
            }
        }
        val byPackage = HashMap<String, SavableSearchable>()
        for (app in installed) byPackage.getOrPut(app.componentName.packageName) { app }
        val candidates = (ranked.orEmpty().mapNotNull { byPackage[it] } + favourites)
            .filter { it.key !in taken }
            .distinctBy { it.key }
            .iterator()
        for (i in 0 until DIAL_SLOTS) {
            // "-" stays blank; an empty or uninstalled disc takes the next app
            if (slots[i] == null && keys[i] != BLANK && candidates.hasNext()) slots[i] = candidates.next()
        }
        slots.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<SavableSearchable?>())

    val motion = homeSettings.motion
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val ringReturnsHome = homeSettings.ringReturnsHome
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val look = lookSettings.look
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val colour = lookSettings.colour
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VpdColour.Cherry)

    fun icon(app: SavableSearchable, sizePx: Int): Flow<LauncherIcon?> =
        iconService.getIcon(app, sizePx)

    /** Launch counter (AF-14, AF-23). */
    fun reportLaunch(app: SavableSearchable) = favoritesService.reportLaunch(app)

    /** Saves the dial: 12 keys, "" = fill automatically, "-" = blank on purpose. Home follows at once. */
    fun saveDial(keys: List<String>) {
        listSettings.setDialApps(List(DIAL_SLOTS) { keys.getOrNull(it) ?: "" })
    }

    companion object {
        const val DIAL_SLOTS = 12

        /** Saved marker: this disc stays blank. */
        const val BLANK = "-"
    }
}
