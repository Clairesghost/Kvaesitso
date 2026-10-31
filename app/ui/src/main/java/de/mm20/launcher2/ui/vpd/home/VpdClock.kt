package de.mm20.launcher2.ui.vpd.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import kotlinx.coroutines.delay
import java.util.Calendar

/**
 * Clock in the centre of the dial: 12 ticks, two hands, pin, and the enamel plate at six o'clock
 * with the 12-hour time in script (D-165). No date window (D-124). Updates once per minute and only
 * while the launcher is started (AF-15). Same box as the dial (DIAL_BOX_W x DIAL_BOX_H, centre DIAL_CX / DIAL_CY), drawn DIAL_SCALE bigger (D-183).
 */
@Composable
fun VpdClock(st: VpdStage, modifier: Modifier = Modifier) {
    val ds = remember(st.k, st.density) { VpdStage(st.k * DIAL_SCALE, st.density) }
    val colors = LocalVpdColors.current
    var nowMs by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            while (true) {
                nowMs = System.currentTimeMillis()
                delay(60_000L - nowMs % 60_000L + 50L)
            }
        }
    }
    val cal = Calendar.getInstance().apply { timeInMillis = nowMs }
    val hour24 = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    val hour12 = if (hour24 % 12 == 0) 12 else hour24 % 12
    val text = "$hour12:${minute.toString().padStart(2, '0')} ${if (hour24 < 12) "am" else "pm"}"
    val hourAngle = (hour24 % 12) * 30f + minute / 2f
    val minuteAngle = minute * 6f
    val marks = colors.clockMarks

    Box(modifier = modifier.size(st.dp(DIAL_BOX_W), st.dp(DIAL_BOX_H))) {
        Canvas(Modifier.size(st.dp(DIAL_BOX_W), st.dp(DIAL_BOX_H))) {
            val k = ds.k
            val c = Offset(DIAL_CX * st.k, DIAL_CY * st.k)
            for (i in 0 until 12) {
                val major = i % 3 == 0
                rotate(i * 30f, pivot = c) {
                    drawLine(
                        marks,
                        Offset(c.x, c.y - 44f * k), Offset(c.x, c.y - (if (major) 37f else 40f) * k),
                        strokeWidth = (if (major) 2.2f else 1.2f) * k, cap = StrokeCap.Round,
                    )
                }
            }
            // enamel plate: chrome rim, cream plate, half under the clock at six o'clock (D-165)
            val tl = Offset(c.x - 32f * k, c.y + 41f * k)
            drawRoundRect(Color(0xFFD6D8DA), tl, Size(64f * k, 22f * k), CornerRadius(11f * k))
            drawRoundRect(
                Color(0xFFFFFAF0),
                Offset(tl.x + 2.4f * k, tl.y + 2.4f * k), Size(59.2f * k, 17.2f * k), CornerRadius(8.6f * k),
            )
            drawRoundRect(
                Color(0xFFC9B890),
                Offset(tl.x + 2.4f * k, tl.y + 2.4f * k), Size(59.2f * k, 17.2f * k), CornerRadius(8.6f * k),
                style = Stroke(1f * k),
            )
            // hands never reach the plate (minute hand is 32 long, plate starts 41 down)
            rotate(hourAngle, pivot = c) {
                drawLine(marks, c, Offset(c.x, c.y - 22f * k), strokeWidth = 3.4f * k, cap = StrokeCap.Round)
            }
            rotate(minuteAngle, pivot = c) {
                drawLine(marks, c, Offset(c.x, c.y - 32f * k), strokeWidth = 2.2f * k, cap = StrokeCap.Round)
            }
            drawCircle(colors.clockPin, 3.4f * k, c)
        }
        Box(
            modifier = Modifier
                .offset(
                    st.dp(DIAL_CX) - ds.dp(32f),
                    st.dp(DIAL_CY) + ds.dp(41f),
                )
                .size(ds.dp(64f), ds.dp(22f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text,
                color = Color(0xFF8D141D),
                fontFamily = VpdFonts.Heading,
                fontSize = ds.sp(13f),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}
