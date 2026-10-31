package de.mm20.launcher2.ui.vpd.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/** Stage size in units (docs/design-system.md, "Layout"). */
const val STAGE_WIDTH = 360f
const val STAGE_HEIGHT = 780f

/** Scale from stage units to pixels: [k] pixels per unit. */
@Immutable
class VpdStage(val k: Float, val density: Density) {
    fun px(units: Float): Float = units * k
    fun dp(units: Float): Dp = with(density) { (units * k).toDp() }
    fun sp(units: Float): TextUnit = with(density) { (units * k).toSp() }
}
