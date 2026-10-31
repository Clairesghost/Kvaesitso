package de.mm20.launcher2.preferences.ui

import de.mm20.launcher2.preferences.LauncherDataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Vintage Pocket Dial: app categories moved by the owner, the 12 dial apps, and what is hidden
 * from the Alerts and Calendar cards.
 */
class VpdListSettings internal constructor(
    private val launcherDataStore: LauncherDataStore,
) {
    /** Package name -> category name the owner moved the app to. */
    val appCategories
        get() = launcherDataStore.data.map { it.vpdAppCategories }.distinctUntilChanged()

    fun setAppCategory(packageName: String, category: String) {
        launcherDataStore.update {
            it.copy(vpdAppCategories = it.vpdAppCategories + (packageName to category))
        }
    }

    /** 12 app keys in dial order, "" = empty disc. null = nothing saved yet (favourites are used, D-180). */
    val dialApps
        get() = launcherDataStore.data.map { it.vpdDialApps }.distinctUntilChanged()

    fun setDialApps(keys: List<String>) {
        launcherDataStore.update {
            it.copy(vpdDialApps = keys)
        }
    }

    /** Packages whose notifications are left out of the Alerts card. */
    val hiddenAlertApps
        get() = launcherDataStore.data.map { it.vpdHiddenAlertApps }.distinctUntilChanged()

    fun setAlertAppHidden(packageName: String, hidden: Boolean) {
        launcherDataStore.update {
            it.copy(
                vpdHiddenAlertApps = if (hidden) it.vpdHiddenAlertApps + packageName
                else it.vpdHiddenAlertApps - packageName
            )
        }
    }

    /** Calendar ids (as "local:<id>") left out of the Calendar card. */
    val hiddenCalendars
        get() = launcherDataStore.data.map { it.vpdHiddenCalendars }.distinctUntilChanged()

    fun setCalendarHidden(calendarId: String, hidden: Boolean) {
        launcherDataStore.update {
            it.copy(
                vpdHiddenCalendars = if (hidden) it.vpdHiddenCalendars + calendarId
                else it.vpdHiddenCalendars - calendarId
            )
        }
    }
}
