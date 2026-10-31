package de.mm20.launcher2.ui.vpd.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.icons.IconService
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.ui.VpdHomeSettings
import de.mm20.launcher2.preferences.ui.VpdLookSettings
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.searchable.PinnedLevel
import de.mm20.launcher2.services.favorites.FavoritesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VpdHomeVM : ViewModel(), KoinComponent {
    private val favoritesService: FavoritesService by inject()
    private val iconService: IconService by inject()
    private val homeSettings: VpdHomeSettings by inject()
    private val lookSettings: VpdLookSettings by inject()

    /** The 12 dial apps: favourites, pinned first, then most used (D-180). Order comes from the favourites service. */
    val dialApps = favoritesService.getFavorites(
        includeTypes = listOf("app"),
        minPinnedLevel = PinnedLevel.FrequentlyUsed,
        limit = DIAL_SLOTS,
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val motion = homeSettings.motion
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val ringReturnsHome = homeSettings.ringReturnsHome
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val look = lookSettings.look
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun icon(app: SavableSearchable, sizePx: Int): Flow<LauncherIcon?> =
        iconService.getIcon(app, sizePx)

    /** Launch counter (AF-14, AF-23). */
    fun reportLaunch(app: SavableSearchable) = favoritesService.reportLaunch(app)

    companion object {
        const val DIAL_SLOTS = 12
    }
}
