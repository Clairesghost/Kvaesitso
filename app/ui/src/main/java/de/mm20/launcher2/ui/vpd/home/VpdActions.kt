package de.mm20.launcher2.ui.vpd.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log

/** Things the housing and the clock open (AF-8, AF-27, AF-28). Each logs a miss, none retries another way. */
object VpdActions {

    private fun start(context: Context, intent: Intent) {
        try {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (e: ActivityNotFoundException) {
            Log.e("launcher", "Nothing answers ${intent.action}", e)
        } catch (e: SecurityException) {
            Log.e("launcher", "Not allowed to open ${intent.action}", e)
        }
    }

    fun openCamera(context: Context) =
        start(context, Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))

    fun openClock(context: Context) = start(context, Intent(AlarmClock.ACTION_SHOW_ALARMS))

    fun openInternetSettings(context: Context) =
        start(context, Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))

    fun openBluetoothSettings(context: Context) =
        start(context, Intent(Settings.ACTION_BLUETOOTH_SETTINGS))

    fun openBatterySettings(context: Context) =
        start(context, Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS))
}
