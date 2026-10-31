package de.mm20.launcher2.ui.vpd.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.calendar.CalendarRepository
import de.mm20.launcher2.notifications.Notification
import de.mm20.launcher2.notifications.NotificationRepository
import de.mm20.launcher2.permissions.PermissionGroup
import de.mm20.launcher2.permissions.PermissionsManager
import de.mm20.launcher2.preferences.ui.VpdListSettings
import de.mm20.launcher2.search.CalendarEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/** One row of the Alerts card. Title and text are read as CharSequence (AF-1) and never logged. */
data class VpdAlert(
    val notification: Notification,
    val title: String,
    val text: String,
    val ongoing: Boolean,
)

/**
 * Data of the Calendar and Alerts cards. Everything is collected with WhileSubscribed, and the
 * screen collects with the lifecycle, so nothing runs while Home is not visible (AF-15).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class VpdCardsVM : ViewModel(), KoinComponent {
    private val calendarRepository: CalendarRepository by inject()
    private val notificationRepository: NotificationRepository by inject()
    private val permissionsManager: PermissionsManager by inject()
    private val listSettings: VpdListSettings by inject()

    val hasCalendarPermission = permissionsManager.hasPermission(PermissionGroup.Calendar)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /** Real notification-listener state: true once Kvaesitso's listener is bound. */
    val hasNotificationAccess = permissionsManager.hasPermission(PermissionGroup.Notifications)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /** Events of the next 14 days from all calendars, tasks left out, in start order. */
    val events = combine(flow { emit(System.currentTimeMillis()) }, listSettings.hiddenCalendars) { now, hidden -> now to hidden }
        .flatMapLatest { (now, hidden) ->
            calendarRepository.findMany(
                from = now,
                to = now + 14 * 24 * 60 * 60 * 1000L,
                excludeCalendars = hidden.toList(),
            )
        }
        .map { list ->
            list.filter { !it.isTask }.sortedBy { it.startTime ?: it.endTime }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<CalendarEvent>())

    private val dismissed = MutableStateFlow<Set<String>>(emptySet())

    /** Newest first. */
    val alerts = combine(notificationRepository.notifications, dismissed, listSettings.hiddenAlertApps) { all, gone, hiddenApps ->
        all.filter { it.key !in gone && !it.isGroupSummary && it.packageName !in hiddenApps }
            .mapNotNull { n ->
                val title = n.extras.getCharSequence(android.app.Notification.EXTRA_TITLE)
                    ?.toString()?.takeIf { it.isNotBlank() }
                val text = n.extras.getCharSequence(android.app.Notification.EXTRA_TEXT)
                    ?.toString()?.takeIf { it.isNotBlank() }
                if (title == null && text == null) return@mapNotNull null
                VpdAlert(
                    notification = n,
                    title = title ?: "",
                    text = text ?: "",
                    ongoing = !n.isClearable ||
                            n.flags and android.app.Notification.FLAG_ONGOING_EVENT != 0,
                )
            }
            .sortedByDescending { it.notification.postTime }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<VpdAlert>())

    fun clear(alert: VpdAlert) {
        if (alert.ongoing) return
        dismissed.value = dismissed.value + alert.notification.key
        notificationRepository.cancelNotification(alert.notification)
    }
}
