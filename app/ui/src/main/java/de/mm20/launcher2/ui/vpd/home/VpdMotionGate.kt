package de.mm20.launcher2.ui.vpd.home

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.PowerManager
import android.provider.Settings
import de.mm20.launcher2.preferences.ui.VpdMotion

/**
 * Battery and motion checklist (CLAUDE.md, AF-20). Read at the moment a touch needs it, so there is
 * nothing registered and nothing polling. "Visible and resumed" is handled by the callers
 * (lifecycle effects), this class answers the rest.
 */
class VpdMotionGate(private val context: Context) {

    /** true when the dial and press effects may animate. */
    fun animationsAllowed(motion: VpdMotion): Boolean {
        if (motion == VpdMotion.Off) return false
        val power = context.getSystemService(PowerManager::class.java)
        if (power != null && (!power.isInteractive || power.isPowerSaveMode)) return false
        val scale = Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f
        )
        if (scale == 0f) return false
        val battery = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        if (battery != null) {
            val level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scaleMax = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
            if (level >= 0 && scaleMax > 0 && !charging && level * 100 / scaleMax <= 15) return false
        }
        return true
    }
}
