package de.mm20.launcher2.ui.vpd.cards

import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.mm20.launcher2.permissions.PermissionGroup
import de.mm20.launcher2.permissions.PermissionsManager
import de.mm20.launcher2.ui.utils.NotificationShadeController
import java.time.LocalDate
import java.time.ZoneId

/** What the cards open (AF-1, AF-24, AF-30). Each logs a miss and tries nothing else. */
internal object VpdCardActions {

    /** Calendar app on today, same call as Kvaesitso's own calendar widget (AF-30). */
    fun openCalendar(context: Context) {
        val noon = LocalDate.now().atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val builder = CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
        ContentUris.appendId(builder, noon)
        start(context, Intent(Intent.ACTION_VIEW).setData(builder.build()))
    }

    fun requestCalendarPermission(context: Context, permissions: PermissionsManager) {
        val activity = context as? AppCompatActivity
        if (activity == null) {
            Log.e("launcher", "Calendar permission: context is not an activity")
            return
        }
        permissions.requestPermission(activity, PermissionGroup.Calendar)
    }

    /** Android's notification access page (AF-1). */
    fun openNotificationAccess(context: Context) =
        start(context, Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))

    /** Android's own notification list: the repo's reflection call (AF-24, unverified on Android 13). */
    fun openNotificationList(context: Context) = NotificationShadeController(context).expandNotifications()

    fun openNotification(intent: PendingIntent?) {
        if (intent == null) return
        try {
            intent.send()
        } catch (e: PendingIntent.CanceledException) {
            Log.e("launcher", "Notification action was cancelled", e)
        }
    }

    fun comingSoon(context: Context) {
        Toast.makeText(context, "Coming in the next update", Toast.LENGTH_SHORT).show()
    }

    private fun start(context: Context, intent: Intent) {
        try {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (e: ActivityNotFoundException) {
            Log.e("launcher", "Nothing answers ${intent.action}", e)
        } catch (e: SecurityException) {
            Log.e("launcher", "Not allowed to open ${intent.action}", e)
        }
    }
}
