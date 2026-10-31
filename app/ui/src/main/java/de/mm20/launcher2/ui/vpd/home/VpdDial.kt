package de.mm20.launcher2.ui.vpd.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.ui.component.ShapedLauncherIcon
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.roundToInt
import kotlin.math.sin

private const val SLOT_DEG = 30f
private const val RING_R = 112f
private const val DISC_R = 23f
private const val CENTRE_Y = 152f
private const val CLOCK_ZONE = 52f
private const val DIAL_ZONE = 152f
private const val HOME_DELAY_MS = 5000L
private const val PRESS_DELAY_MS = 120L
private const val HOLD_MS = 500L

private class DialJobs {
    var spin: Job? = null
    var home: Job? = null
    fun cancel() {
        spin?.cancel()
        home?.cancel()
        spin = null
        home = null
    }
}

/**
 * The dial (stage 360 x 304, centre at 180 / 152): plate, static ticks, finger stop, 12 discs that
 * spin with the ring, and the clock in the middle. It consumes every touch on itself so the
 * scaffold's swipes and double tap do not fire on it (AF-22).
 */
@Composable
fun VpdDial(
    st: VpdStage,
    apps: List<SavableSearchable>,
    motion: VpdMotion,
    ringReturnsHome: Boolean,
    gate: VpdMotionGate,
    iconFor: (SavableSearchable, Int) -> Flow<LauncherIcon?>,
    onLaunch: (SavableSearchable) -> Unit,
    onClockHold: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalVpdColors.current
    var rot by remember { mutableFloatStateOf(0f) }
    var pressed by remember { mutableIntStateOf(-1) }
    val jobs = remember { DialJobs() }
    val scope = rememberCoroutineScope()

    val appsNow by rememberUpdatedState(apps)
    val motionNow by rememberUpdatedState(motion)
    val returnsNow by rememberUpdatedState(ringReturnsHome)
    val onLaunchNow by rememberUpdatedState(onLaunch)
    val onHoldNow by rememberUpdatedState(onClockHold)

    fun armHome() {
        jobs.home?.cancel()
        if (!returnsNow) return
        val r = ((rot % 360f) + 360f) % 360f
        if (r < 0.5f || r > 359.5f) return
        jobs.home = scope.launch {
            delay(HOME_DELAY_MS)
            val target = (rot / 360f).roundToInt() * 360f
            if (gate.animationsAllowed(motionNow)) {
                val a = Animatable(rot)
                a.animateTo(target, tween(450)) { rot = value }
            }
            rot = target
        }
    }

    fun settle(velocityDegPerMs: Float) {
        jobs.spin?.cancel()
        jobs.spin = scope.launch {
            try {
                val allowed = gate.animationsAllowed(motionNow)
                val a = Animatable(rot)
                if (allowed && motionNow == VpdMotion.Full && abs(velocityDegPerMs) > 0.05f) {
                    a.animateDecay(velocityDegPerMs * 1000f, exponentialDecay<Float>(1.5f, 25f)) {
                        rot = value
                    }
                }
                val target = (a.value / SLOT_DEG).roundToInt() * SLOT_DEG
                if (allowed) {
                    a.animateTo(target, tween(if (motionNow == VpdMotion.Full) 180 else 100)) {
                        rot = value
                    }
                }
                rot = target
                armHome()
            } catch (e: CancellationException) {
                throw e
            }
        }
    }

    // Press effects always reset when the launcher comes back or goes away; the timer is
    // cancelled when Home is not on screen (CLAUDE.md battery checklist).
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { pressed = -1 }
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        pressed = -1
        jobs.cancel()
        if (returnsNow) rot = (rot / 360f).roundToInt() * 360f else rot = (rot / SLOT_DEG).roundToInt() * SLOT_DEG
    }

    Box(
        modifier = modifier
            .size(st.dp(360f), st.dp(304f))
            .pointerInput(st.k) {
                val k = st.k
                val centre = Offset(180f * k, CENTRE_Y * k)

                fun discCentre(i: Int): Offset {
                    val a = Math.toRadians((i * SLOT_DEG + rot).toDouble())
                    return Offset(
                        centre.x + RING_R * k * sin(a).toFloat(),
                        centre.y - RING_R * k * cos(a).toFloat(),
                    )
                }

                fun hitDisc(p: Offset): Int {
                    for (i in 0 until 12) {
                        if ((p - discCentre(i)).getDistance() <= (DISC_R + 3f) * k) return i
                    }
                    return -1
                }

                fun angleOf(p: Offset): Float {
                    val d = p - centre
                    return (atan2(d.x, -d.y) * 180.0 / Math.PI).toFloat()
                }

                fun wrap180(v: Float): Float {
                    var d = v
                    while (d > 180f) d -= 360f
                    while (d < -180f) d += 360f
                    return d
                }

                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val dist = (down.position - centre).getDistance() / k
                    if (dist > DIAL_ZONE) return@awaitEachGesture
                    down.consume()

                    if (dist < CLOCK_ZONE) {
                        // hold half a second opens the Clock app (D-139); tap does nothing in B2
                        val released = withTimeoutOrNull(HOLD_MS) { consumeUntilUp(); true }
                        if (released == null) {
                            onHoldNow()
                            consumeUntilUp()
                        }
                        return@awaitEachGesture
                    }

                    jobs.cancel()
                    val animated = gate.animationsAllowed(motionNow)
                    val hit = hitDisc(down.position)
                    if (animated && hit in appsNow.indices) pressed = hit

                    var lastAngle = angleOf(down.position)
                    var lastT = down.uptimeMillis
                    var v = 0f
                    var moved = 0f
                    var spun = false
                    while (true) {
                        val event = awaitPointerEvent()
                        val ch = event.changes.firstOrNull { it.id == down.id } ?: break
                        event.changes.forEach { it.consume() }
                        if (!ch.pressed) {
                            if (ch.uptimeMillis - lastT > 100L) v = 0f
                            break
                        }
                        val a = angleOf(ch.position)
                        val d = wrap180(a - lastAngle)
                        val dt = maxOf(1L, ch.uptimeMillis - lastT).toFloat()
                        moved += (ch.position - ch.previousPosition).getDistance()
                        if (moved > 6f * k) {
                            if (!spun) {
                                spun = true
                                pressed = -1
                            }
                            rot += d
                            v = v * 0.6f + (d / dt) * 0.4f
                        }
                        lastAngle = a
                        lastT = ch.uptimeMillis
                    }

                    if (spun) {
                        settle(v)
                    } else {
                        val app = appsNow.getOrNull(hit)
                        if (app == null) {
                            pressed = -1
                        } else {
                            scope.launch {
                                if (animated) delay(PRESS_DELAY_MS)
                                pressed = -1
                                onLaunchNow(app)
                            }
                        }
                        armHome()
                    }
                }
            }
    ) {
        Canvas(Modifier.size(st.dp(360f), st.dp(304f))) {
            drawDialPlate(st.k, colors)
        }
        VpdClock(st = st, modifier = Modifier.size(st.dp(360f), st.dp(304f)))

        for (i in 0 until 12) {
            val app = apps.getOrNull(i)
            Box(
                modifier = Modifier
                    .offset {
                        val a = Math.toRadians((i * SLOT_DEG + rot).toDouble())
                        IntOffset(
                            (st.px(180f) + st.px(RING_R) * sin(a).toFloat() - st.px(DISC_R)).roundToInt(),
                            (st.px(CENTRE_Y) - st.px(RING_R) * cos(a).toFloat() - st.px(DISC_R)).roundToInt(),
                        )
                    }
                    .size(st.dp(DISC_R * 2f))
                    .scale(if (pressed == i) 0.9f else 1f),
                contentAlignment = Alignment.Center,
            ) {
                Disc(st, colors, app, pressed == i, iconFor)
            }
        }
    }
}

private suspend fun AwaitPointerEventScope.consumeUntilUp() {
    while (true) {
        val e = awaitPointerEvent()
        e.changes.forEach { it.consume() }
        if (e.changes.none { it.pressed }) return
    }
}

@Composable
private fun Disc(
    st: VpdStage,
    colors: VpdColors,
    app: SavableSearchable?,
    isPressed: Boolean,
    iconFor: (SavableSearchable, Int) -> Flow<LauncherIcon?>,
) {
    val rim = colors.discRim
    Box(
        modifier = Modifier
            .size(st.dp(DISC_R * 2f))
            .background(
                Brush.radialGradient(
                    colors.disc,
                    center = Offset(st.px(DISC_R * 0.8f), st.px(DISC_R * 0.7f)),
                    radius = st.px(DISC_R * 1.6f),
                ),
                CircleShape,
            )
            .border(st.dp(2.5f), if (isPressed) rim.copy(alpha = 0.7f) else rim, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        if (app != null) {
            val sizePx = st.px(DISC_R * 1.3f).roundToInt().coerceAtLeast(1)
            val icon by remember(app, sizePx) { iconFor(app, sizePx) }.collectAsState(null)
            ShapedLauncherIcon(
                size = st.dp(DISC_R * 1.3f),
                icon = { icon },
                shape = CircleShape,
            )
        }
    }
}

/** Plate, rims, ticks, finger stop, centre band (mock-up dial svg, centre 180 / 152). */
private fun DrawScope.drawDialPlate(k: Float, colors: VpdColors) {
    val c = Offset(180f * k, CENTRE_Y * k)
    fun r(u: Float) = u * k
    val chrome = Brush.linearGradient(
        colors.chrome,
        start = Offset(c.x - r(146f), c.y - r(146f)),
        end = Offset(c.x + r(146f), c.y + r(146f)),
    )
    // outer chrome rim 2 px (D-133) and dark plate
    drawCircle(chrome, r(146f), c)
    drawCircle(
        Brush.radialGradient(colors.dialPlate, center = Offset(c.x, c.y - r(30f)), radius = r(200f)),
        r(144f), c,
    )
    drawCircle(Color.White.copy(alpha = 0.5f), r(145.2f), c, style = Stroke(r(1.6f)))
    drawCircle(Color.Black.copy(alpha = 0.28f), r(142.8f), c, style = Stroke(r(2.5f)))

    // static tick marks: they do NOT turn (D-142)
    for (i in 0 until 36) {
        rotate(i * 10f, pivot = c) {
            drawLine(
                Color(0xFFCFD0D2),
                Offset(c.x, c.y - r(141f)), Offset(c.x, c.y - r(137f)),
                strokeWidth = r(if (i % 3 == 0) 2f else 1f),
            )
        }
    }

    // chrome finger stop, bottom right
    rotate(135f, pivot = c) {
        val tl = Offset(c.x - r(7f), c.y - r(158f))
        drawRoundRect(chrome, tl, Size(r(14f), r(30f)), androidx.compose.ui.geometry.CornerRadius(r(5f)))
        drawRoundRect(
            Color(0xFF6B6D70), tl, Size(r(14f), r(30f)), androidx.compose.ui.geometry.CornerRadius(r(5f)),
            style = Stroke(r(1f)),
        )
    }

    // centre band: chrome edge, coloured ring, gloss
    drawCircle(chrome, r(88f), c)
    drawCircle(
        Brush.radialGradient(colors.centreRing, center = Offset(c.x, c.y - r(20f)), radius = r(120f)),
        r(86f), c,
    )
    drawCircle(
        Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.38f), Color.Transparent),
            startY = c.y - r(86f), endY = c.y,
        ),
        r(86f), c,
    )
    // clock rim
    drawCircle(chrome, r(52f), c)
    drawCircle(colors.clockFace, r(49f), c)

    // thin ivory ring round the centre band (D-132)
    drawCircle(Color(0xFF6B6D70), r(93.5f), c, style = Stroke(r(11.4f)))
    drawCircle(chrome, r(93.5f), c, style = Stroke(r(10.4f)))
    drawCircle(Color(0xFFF6EDD6), r(93.5f), c, style = Stroke(r(8f)))

    // cream lamp glow at night only (D-151)
    if (colors.isNight) {
        val lamp = Offset(c.x + r(101f), c.y + r(101f))
        drawCircle(
            Brush.radialGradient(
                listOf(Color(0xD9FFF3D6), Color(0x59FFE2A6), Color.Transparent),
                center = lamp, radius = r(17f),
            ),
            r(17f), lamp,
        )
    }
}
