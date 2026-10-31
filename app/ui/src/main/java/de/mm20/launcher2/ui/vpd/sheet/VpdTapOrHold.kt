package de.mm20.launcher2.ui.vpd.sheet

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.withTimeoutOrNull

const val VPD_HOLD_MS = 500L

/**
 * Press, then tap or hold for half a second. [onPress] fires at once (start of the press effect),
 * [onCancel] when a scroll takes the touch over. A hold fires [onHold] and the lift after it does nothing.
 */
fun Modifier.vpdTapOrHold(
    onPress: () -> Unit = {},
    onCancel: () -> Unit = {},
    onHold: (() -> Unit)? = null,
    onTap: () -> Unit,
): Modifier = pointerInput(onHold != null) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = true)
        onPress()
        var finished = false
        val up = withTimeoutOrNull(if (onHold != null) VPD_HOLD_MS else Long.MAX_VALUE) {
            val r = waitForUpOrCancellation()
            finished = true
            r
        }
        when {
            finished && up != null -> {
                up.consume()
                onTap()
            }
            finished -> onCancel()
            else -> {
                onHold?.invoke()
                waitForUpOrCancellation()?.consume()
            }
        }
    }
}
