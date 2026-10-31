package de.mm20.launcher2.ui.vpd.home

import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VpdHomeVM : ViewModel(), KoinComponent {
    private val favoritesService: FavoritesService by inject()
    private val iconService: IconService by inject()
    private val homeSettings: VpdHomeSettings by inject()
    private val lookSettings: VpdLookSettings by inject()
    private val listSettings: VpdListSettings by inject()
    private val appRepository: AppRepository by inject()

    private val favouriteApps = favoritesService.getFavorites(
        includeTypes = listOf("app"),
        minPinnedLevel = PinnedLevel.FrequentlyUsed,
        limit = DIAL_SLOTS,
    )

    /**
     * The 12 dial discs, null = empty disc. Once the owner saved a dial (editor) that list is used,
     * an uninstalled app leaves an empty disc; until then the favourites, pinned first, then most
     * used (D-180).
     */
    val dialApps = combine(
        listSettings.dialApps,
        appRepository.findMany(),
        favouriteApps,
    ) { saved, installed, favourites ->
        if (saved == null) {
            favourites
        } else {
            val byKey = installed.associateBy { it.key }
            List(DIAL_SLOTS) { i -> saved.getOrNull(i)?.let { byKey[it] } }
        }
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

    /** Saves the dial: 12 keys, "" = empty disc. Home follows at once. */
    fun saveDial(slots: List<SavableSearchable?>) {
        listSettings.setDialApps(List(DIAL_SLOTS) { slots.getOrNull(it)?.key ?: "" })
    }

    companion object {
        const val DIAL_SLOTS = 12
    }
}
