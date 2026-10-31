package de.mm20.launcher2.ui.vpd

import de.mm20.launcher2.preferences.ui.VpdLook

/** Auto follows the phone's dark mode for now. */
fun VpdLook.isNight(systemDark: Boolean): Boolean = when (this) {
    VpdLook.Auto -> systemDark
    VpdLook.Day -> false
    VpdLook.Night -> true
}

fun VpdLook.colors(systemDark: Boolean): VpdColors =
    if (isNight(systemDark)) VpdNightColors else VpdDayColors
