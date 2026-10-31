package de.mm20.launcher2.ui.vpd.usage

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.util.Log

/**
 * Usage access (D-196): how long each app was in the foreground over the last 14 days, used to
 * fill empty dial discs. Only totals per package are read; nothing is logged or sent anywhere.
 */
object VpdUsage {
    private const val DAYS = 14L

    /** True when the owner switched "Usage data access" on for this app (AppOps, AF-39). */
    fun hasAccess(context: Context): Boolean {
        val ops = context.getSystemService(AppOpsManager::class.java) ?: return false
        val mode = ops.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName,
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    /**
     * Package names, most used first. Empty without access. Blocking: call off the main thread.
     */
    fun rankedPackages(context: Context): List<String> {
        if (!hasAccess(context)) return emptyList()
        val manager = context.getSystemService(UsageStatsManager::class.java)
        if (manager == null) {
            Log.e("launcher", "UsageStatsManager is not available")
            return emptyList()
        }
        val end = System.currentTimeMillis()
        val begin = end - DAYS * 24L * 60L * 60L * 1000L
        // one total per package over the whole range, so no bucket is counted twice
        val stats = manager.queryAndAggregateUsageStats(begin, end)
        return stats.entries
            .filter { it.value.totalTimeInForeground > 0L && it.key != context.packageName }
            .sortedByDescending { it.value.totalTimeInForeground }
            .map { it.key }
    }

    /** Opens Android's Usage access page, on this app's own row when the phone supports it (AF-40). */
    fun openAccessPage(context: Context) {
        val own = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS, Uri.parse("package:${context.packageName}"))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val general = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val intent = if (own.resolveActivity(context.packageManager) != null) own else general
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("launcher", "Could not open the Usage access page", e)
        }
    }
}
