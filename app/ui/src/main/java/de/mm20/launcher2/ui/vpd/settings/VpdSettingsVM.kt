package de.mm20.launcher2.ui.vpd.settings

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.calendar.CalendarRepository
import de.mm20.launcher2.calendar.providers.CalendarList
import de.mm20.launcher2.notifications.NotificationRepository
import de.mm20.launcher2.permissions.PermissionGroup
import de.mm20.launcher2.permissions.PermissionsManager
import de.mm20.launcher2.preferences.ui.VpdColour
import de.mm20.launcher2.preferences.ui.VpdHomeSettings
import de.mm20.launcher2.preferences.ui.VpdListSettings
import de.mm20.launcher2.preferences.ui.VpdLook
import de.mm20.launcher2.preferences.ui.VpdLookSettings
import de.mm20.launcher2.preferences.ui.VpdMotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/** An app that has posted notifications (or is already hidden) with its name. */
data class VpdAlertApp(val packageName: String, val label: String, val hidden: Boolean)

/** Data of the Settings page. Nothing runs while the page is not on screen (WhileSubscribed). */
class VpdSettingsVM(application: Application) : AndroidViewModel(application), KoinComponent {
    private val look: VpdLookSettings by inject()
    private val home: VpdHomeSettings by inject()
    private val lists: VpdListSettings by inject()
    private val notifications: NotificationRepository by inject()
    private val calendars: CalendarRepository by inject()
    private val permissions: PermissionsManager by inject()

    val lookSetting = look.look.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VpdLook.Auto)
    val colour = look.colour.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VpdColour.Cherry)
    val motion = home.motion.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VpdMotion.Full)
    val ringReturnsHome = home.ringReturnsHome.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val hasNotificationAccess = permissions.hasPermission(PermissionGroup.Notifications)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val alertApps = combine(
        notifications.notifications.map { list -> list.filter { !it.isGroupSummary }.map { it.packageName } },
        lists.hiddenAlertApps,
    ) { posted, hidden ->
        (posted.toSet() + hidden)
            .map { pkg -> VpdAlertApp(pkg, labelOf(pkg), pkg in hidden) }
            .sortedBy { it.label.lowercase() }
    }.flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<VpdAlertApp>())

    val calendarList = calendars.getCalendars("local")
        .map { list -> list.sortedBy { it.name.lowercase() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<CalendarList>())

    val hiddenCalendars = lists.hiddenCalendars
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet<String>())

    val hasCalendarPermission = permissions.hasPermission(PermissionGroup.Calendar)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private fun labelOf(pkg: String): String {
        val pm = getApplication<Application>().packageManager
        return try {
            pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("launcher", "Alerts list: a package is gone", e)
            pkg
        }
    }

    fun setLook(value: VpdLook) = look.setLook(value)
    fun setColour(value: VpdColour) = look.setColour(value)
    fun setMotion(value: VpdMotion) = home.setMotion(value)
    fun setRingReturnsHome(value: Boolean) = home.setRingReturnsHome(value)
    fun setAlertHidden(pkg: String, hidden: Boolean) = lists.setAlertAppHidden(pkg, hidden)
    fun setCalendarHidden(id: String, hidden: Boolean) = lists.setCalendarHidden(id, hidden)
}
