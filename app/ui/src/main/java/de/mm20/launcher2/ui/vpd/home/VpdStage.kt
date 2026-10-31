package de.mm20.launcher2.ui.vpd.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/** Stage size in units (docs/design-system.md, "Layout"). */
const val STAGE_WIDTH = 360f
const val STAGE_HEIGHT = 780f

/** Dial box (stage units, top at y 52) and its centre; the dial draws DIAL_SCALE bigger inside it (D-183). */
const val DIAL_BOX_W = 360f
const val DIAL_BOX_H = 350f
const val DIAL_CX = 180f
const val DIAL_CY = 175f
const val DIAL_SCALE = 1.15f
const val DIAL_TOP = 52f

/** Scale from stage units to pixels: [k] pixels per unit. */
@Immutable
class VpdStage(val k: Float, val density: Density) {
    fun px(units: Float): Float = units * k
    fun dp(units: Float): Dp = with(density) { (units * k).toDp() }
    fun sp(units: Float): TextUnit = with(density) { (units * k).toSp() }
}
