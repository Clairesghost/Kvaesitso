package de.mm20.launcher2.ui.vpd.apps

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.applications.AppRepository
import de.mm20.launcher2.icons.IconService
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.ui.VpdListSettings
import de.mm20.launcher2.search.Application as LauncherApplication
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.services.favorites.FavoritesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/** One app of the list with the category it shows under. */
data class VpdApp(
    val app: LauncherApplication,
    val label: String,
    val packageName: String,
    val category: VpdAppCategory,
)

/**
 * All launchable apps (work profile included, as Kvaesitso does) with their category. The
 * automatic category comes from `ApplicationInfo.category`; a saved move wins (D-168).
 */
class VpdAppsVM(application: Application) : AndroidViewModel(application), KoinComponent {
    private val appRepository: AppRepository by inject()
    private val listSettings: VpdListSettings by inject()
    private val iconService: IconService by inject()
    private val favoritesService: FavoritesService by inject()

    private val autoCategories = HashMap<String, VpdAppCategory>()

    val apps = combine(appRepository.findMany(), listSettings.appCategories) { installed, moved ->
        installed
            .filter { !it.isPrivate }
            .map { app ->
                val pkg = app.componentName.packageName
                VpdApp(
                    app = app,
                    label = app.labelOverride ?: app.label,
                    packageName = pkg,
                    category = VpdAppCategory.fromName(moved[pkg]) ?: autoCategory(pkg),
                )
            }
            .sortedBy { it.label.lowercase() }
    }.flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<VpdApp>())

    private fun autoCategory(pkg: String): VpdAppCategory = autoCategories.getOrPut(pkg) {
        try {
            VpdAppCategory.fromAndroid(getApplication<Application>().packageManager.getApplicationInfo(pkg, 0).category)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("launcher", "No category for a package: not found", e)
            VpdAppCategory.Other
        }
    }

    fun move(app: VpdApp, to: VpdAppCategory) = listSettings.setAppCategory(app.packageName, to.name)

    fun icon(app: SavableSearchable, sizePx: Int): Flow<LauncherIcon?> = iconService.getIcon(app, sizePx)

    /** Launch counter (AF-14, AF-23). */
    fun reportLaunch(app: SavableSearchable) = favoritesService.reportLaunch(app)
}
