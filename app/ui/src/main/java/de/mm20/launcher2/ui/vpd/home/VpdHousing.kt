package de.mm20.launcher2.ui.vpd.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts

/** What the three coins show. */
data class VpdCoinState(
    val connection: VpdConnection,
    /** false when nothing is connected or the permission was refused (grey, no nagging). */
    val bluetoothConnected: Boolean,
    val battery: VpdBattery?,
)

private val SlotColor = Color(0xFF1B0B0D)
private val CoinIdle = Color(0xFF8F9194)
private val CoinDark = Color(0xFF2B2B2E)

/**
 * Top housing, stage y 8 to 46 (mock-up ".srch"): search slot on the left, coins on the right.
 * The camera ring is drawn separately by [VpdCameraRing] at the real hole. Place this composable
 * at stage (12, 8).
 */
@Composable
fun VpdHousing(
    st: VpdStage,
    coins: VpdCoinState,
    onSearch: () -> Unit,
    onConnection: () -> Unit,
    onBluetooth: () -> Unit,
    onBattery: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalVpdColors.current
    val shape = RoundedCornerShape(st.dp(12f))
    Box(
        modifier = modifier
            .size(st.dp(336f), st.dp(38f))
            .shadow(st.dp(4f), shape)
            .clip(shape)
            .background(Brush.verticalGradient(colors.chromeHousing))
            .border(st.dp(1.5f), colors.chromeFrameLight, shape)
    ) {
        // search slot (left)
        Box(
            modifier = Modifier
                .offset(st.dp(10f), st.dp(5f))
                .size(st.dp(132f), st.dp(28f))
                .clip(RoundedCornerShape(st.dp(14f)))
                .background(SlotColor)
                .clickable(onClick = onSearch)
                .semantics { contentDescription = "Search" }
                .padding(horizontal = st.dp(12f)),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                "Dial, find, sum, jot…",
                color = Color(0xFFFBEEE0).copy(alpha = 0.62f),
                fontFamily = VpdFonts.Body,
                fontSize = st.sp(11f),
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )
        }
        // coins (right)
        Row(
            modifier = Modifier.offset(st.dp(336f - 10f - 3 * 28f - 2 * 5f), st.dp(5f)),
            horizontalArrangement = Arrangement.spacedBy(st.dp(5f)),
        ) {
            Coin(st, "Connection", onConnection) { ConnectionIcon(coins.connection, it) }
            Coin(st, "Bluetooth", onBluetooth) { BluetoothIcon(coins.bluetoothConnected, it) }
            Coin(st, "Battery", onBattery) { BatteryRing(coins.battery, it) }
        }
    }
}

@Composable
private fun Coin(
    st: VpdStage,
    label: String,
    onClick: () -> Unit,
    content: @Composable (Dp) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(st.dp(28f))
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(Color.White, Color(0xFFC3C5C8), Color(0xFF8F9194)),
                    center = Offset(st.px(10f), st.px(8f)),
                    radius = st.px(26f),
                )
            )
            .border(st.dp(2f), Color(0xFFE9E9E6), CircleShape)
            .clickable(onClick = onClick)
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center,
    ) {
        content(st.dp(16f))
    }
}

@Composable
private fun ConnectionIcon(connection: VpdConnection, size: Dp) {
    val tint = if (connection == VpdConnection.None) CoinIdle else CoinDark
    Canvas(Modifier.size(size)) {
        val w = this.size.width
        if (connection == VpdConnection.Mobile) {
            for (i in 0 until 4) {
                val h = w * (0.3f + 0.2f * i)
                val x = w * (0.12f + 0.25f * i)
                drawLine(tint, Offset(x, w), Offset(x, w - h), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
            }
        } else {
            val stroke = Stroke(width = w * 0.11f, cap = StrokeCap.Round)
            val cx = w / 2f
            val base = w * 0.9f
            for (i in 1..3) {
                val r = w * 0.27f * i
                drawArc(
                    tint, startAngle = 225f, sweepAngle = 90f, useCenter = false,
                    topLeft = Offset(cx - r, base - r), size = Size(2 * r, 2 * r), style = stroke,
                )
            }
            drawCircle(tint, w * 0.07f, Offset(cx, base))
        }
    }
}

@Composable
private fun BluetoothIcon(connected: Boolean, size: Dp) {
    val tint = if (connected) CoinDark else CoinIdle
    Canvas(Modifier.size(size)) {
        val w = this.size.width
        val s = Stroke(width = w * 0.12f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        val p = Path().apply {
            moveTo(w * 0.22f, w * 0.30f)
            lineTo(w * 0.78f, w * 0.72f)
            lineTo(w * 0.50f, w * 0.95f)
            lineTo(w * 0.50f, w * 0.05f)
            lineTo(w * 0.78f, w * 0.28f)
            lineTo(w * 0.22f, w * 0.70f)
        }
        drawPath(p, tint, style = s)
    }
}

@Composable
private fun BatteryRing(battery: VpdBattery?, size: Dp) {
    Canvas(Modifier.size(size)) {
        val w = this.size.width
        val stroke = w * 0.16f
        val inset = stroke / 2f
        val arcSize = Size(w - stroke, w - stroke)
        drawArc(
            CoinIdle.copy(alpha = 0.45f), 0f, 360f, false,
            topLeft = Offset(inset, inset), size = arcSize, style = Stroke(stroke),
        )
        if (battery != null) {
            drawArc(
                CoinDark, -90f, 360f * battery.percent / 100f, false,
                topLeft = Offset(inset, inset), size = arcSize, style = Stroke(stroke),
            )
        }
    }
}

/**
 * Camera ring around the real camera hole. [centrePx] and [diameterPx] come from the display
 * cutout, measured, not hard-coded (AF-3). The light is green while charging and red when low
 * (D-125). Tapping opens the camera.
 */
@Composable
fun VpdCameraRing(
    centrePx: Offset,
    diameterPx: Float,
    charging: Boolean,
    low: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val d = with(density) { diameterPx.toDp() }
    val light = when {
        charging -> Color(0xFF3DDC84)
        low -> Color(0xFFE53935)
        else -> null
    }
    Box(
        modifier = modifier
            .offset(
                with(density) { (centrePx.x - diameterPx / 2f).toDp() },
                with(density) { (centrePx.y - diameterPx / 2f).toDp() },
            )
            .size(d)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Camera" },
    ) {
        Canvas(Modifier.size(d)) {
            val w = this.size.width
            val c = Offset(w / 2f, w / 2f)
            // chrome ring only: the real hole shows through the middle (hole is about 0.62 of the ring)
            drawCircle(
                Brush.verticalGradient(listOf(Color(0xFFFBFAF6), Color(0xFFB9BBBD), Color(0xFFE9E9E5))),
                w * 0.405f, c, style = Stroke(w * 0.19f),
            )
            drawCircle(Color(0xFF9A9C9F), w * 0.495f, c, style = Stroke(w * 0.01f))
            if (light != null) {
                drawCircle(light, w * 0.325f, c, style = Stroke(w * 0.035f))
            }
        }
    }
}
