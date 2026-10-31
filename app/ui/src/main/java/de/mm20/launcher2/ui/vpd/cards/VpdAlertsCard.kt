package de.mm20.launcher2.ui.vpd.cards

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.VpdMotionGate
import de.mm20.launcher2.ui.vpd.home.VpdStage
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

/** Rows shown at once (D-116). */
private const val SHOWN = 2

/** Alerts card: script title, the 2 newest notifications, "+N more ›" (D-188). */
@Composable
internal fun VpdAlertsCard(
    st: VpdStage,
    alerts: List<VpdAlert>,
    hasAccess: Boolean,
    motion: VpdMotion,
    gate: VpdMotionGate,
    now: Long,
    onOpenList: () -> Unit,
    onAskAccess: () -> Unit,
    onOpen: (VpdAlert) -> Unit,
    onClear: (VpdAlert) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalVpdColors.current
    val dotted = c.textOnCard.copy(alpha = 0.35f)
    VpdPanel(st = st, modifier = modifier) {
        Column(Modifier.fillMaxSize().padding(horizontal = st.dp(10f))) {
            // header: 20 units high
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(st.dp(22f))
                    .then(if (hasAccess && alerts.size > SHOWN) Modifier.clickable(onClick = onOpenList) else Modifier),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "Alerts",
                    style = TextStyle(
                        fontFamily = VpdFonts.Heading,
                        fontWeight = FontWeight.Bold,
                        fontSize = st.sp(15f),
                        color = c.textOnCard,
                    ),
                )
                if (hasAccess) {
                    Text(
                        if (alerts.isEmpty()) "all clear"
                        else if (alerts.size > SHOWN) "+${alerts.size - SHOWN} more ›" else "",
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontWeight = FontWeight.Bold,
                            fontSize = st.sp(11f),
                            color = c.textOnCard.copy(alpha = 0.75f),
                        ),
                    )
                }
            }
            if (!hasAccess) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(st.dp(54f))
                        .vpdDottedTop(st, dotted)
                        .clickable(onClick = onAskAccess),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Show your alerts",
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = st.sp(12f),
                            color = c.textOnCard,
                        ),
                    )
                    Text(
                        "Tap to allow notification access. It stays on your phone.",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontSize = st.sp(10f),
                            color = c.textOnCard.copy(alpha = 0.75f),
                        ),
                    )
                }
            } else {
                alerts.take(SHOWN).forEach { alert ->
                    androidx.compose.runtime.key(alert.notification.key) {
                        AlertRow(
                            st = st,
                            alert = alert,
                            motion = motion,
                            gate = gate,
                            now = now,
                            dotted = dotted,
                            onOpen = { onOpen(alert) },
                            onClear = { onClear(alert) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertRow(
    st: VpdStage,
    alert: VpdAlert,
    motion: VpdMotion,
    gate: VpdMotionGate,
    now: Long,
    dotted: androidx.compose.ui.graphics.Color,
    onOpen: () -> Unit,
    onClear: () -> Unit,
) {
    val c = LocalVpdColors.current
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(0f) }
    var width by remember { mutableIntStateOf(0) }
    val threshold = st.px(90f)
    val ongoing = alert.ongoing

    Box(
        Modifier
            .fillMaxWidth()
            .height(st.dp(26f))
            .vpdDottedTop(st, dotted)
            .onSizeChanged { width = it.width },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offset.value
                    alpha = if (ongoing) 1f else (1f - abs(offset.value) / st.px(260f)).coerceIn(0.25f, 1f)
                }
                .pointerInput(ongoing, motion) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val x = offset.value
                            val animate = gate.animationsAllowed(motion)
                            if (!ongoing && abs(x) > threshold) {
                                if (animate) {
                                    scope.launch {
                                        offset.animateTo(sign(x) * (width + st.px(40f)), tween(160))
                                        onClear()
                                    }
                                } else {
                                    onClear()
                                }
                            } else {
                                scope.launch {
                                    if (animate) {
                                        offset.animateTo(0f, spring(stiffness = Spring.StiffnessMedium))
                                    } else {
                                        offset.snapTo(0f)
                                    }
                                }
                            }
                        },
                        onDragCancel = { scope.launch { offset.snapTo(0f) } },
                        onHorizontalDrag = { change, dx ->
                            change.consume()
                            val limit = st.px(18f)
                            val next = if (ongoing) (offset.value + dx * 0.25f).coerceIn(-limit, limit)
                            else offset.value + dx
                            scope.launch(start = CoroutineStart.UNDISPATCHED) { offset.snapTo(next) }
                        },
                    )
                }
                .clickable(onClick = onOpen),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(st.dp(6f)),
        ) {
            AppIcon(alert.notification.packageName, st)
            Row(Modifier.weight(1f)) {
                Text(
                    buildString {
                        if (alert.title.isNotEmpty()) append(alert.title)
                        if (alert.title.isNotEmpty() && alert.text.isNotEmpty()) append("  ")
                        append(alert.text)
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = VpdFonts.Body,
                        fontWeight = FontWeight.Medium,
                        fontSize = st.sp(12f),
                        color = c.textOnCard,
                    ),
                )
            }
            Text(
                ago(now - alert.notification.postTime),
                maxLines = 1,
                style = TextStyle(
                    fontFamily = VpdFonts.Body,
                    fontSize = st.sp(10.5f),
                    color = c.textOnCard.copy(alpha = 0.65f),
                ),
            )
            Box(
                modifier = Modifier
                    .height(st.dp(20f))
                    .border(st.dp(1f), c.textOnCard, RoundedCornerShape(st.dp(10f)))
                    .padding(horizontal = st.dp(9f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Open",
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = VpdFonts.Body,
                        fontWeight = FontWeight.Medium,
                        fontSize = st.sp(10.5f),
                        color = c.textOnCard,
                    ),
                )
            }
            if (!ongoing) {
                Box(
                    modifier = Modifier
                        .size(st.dp(22f))
                        .clickable(onClick = onClear),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "✕",
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontWeight = FontWeight.Medium,
                            fontSize = st.sp(12f),
                            color = c.textOnCard.copy(alpha = 0.7f),
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun AppIcon(packageName: String, st: VpdStage) {
    val context = LocalContext.current
    val bitmap = remember(packageName) {
        try {
            val drawable = context.packageManager.getApplicationIcon(packageName)
            val bmp = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            drawable.setBounds(0, 0, 64, 64)
            drawable.draw(canvas)
            bmp.asImageBitmap()
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("launcher", "No app icon for a notification", e)
            null
        }
    }
    if (bitmap != null) {
        Image(bitmap, contentDescription = null, modifier = Modifier.size(st.dp(16f)))
    } else {
        Box(Modifier.size(st.dp(16f)))
    }
}

private fun ago(millis: Long): String {
    val minutes = millis / 60_000
    return when {
        minutes < 1 -> "now"
        minutes < 60 -> "$minutes min"
        else -> "${minutes / 60} h"
    }
}
