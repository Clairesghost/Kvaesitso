package de.mm20.launcher2.ui.vpd.sheet

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.VpdStage
import kotlin.math.roundToInt

/**
 * Sheet shell (docs/design-system.md, "Rules for any new screen" 2): fills the screen below the
 * housing (left/right 6, top 54, bottom 6 stage units), script title top left, close top right.
 * Back closes it (the caller closes it on the Home button, AF-7). It slides up only when
 * [animate] is true (motion gate and Motion setting); otherwise it just appears.
 */
@Composable
internal fun VpdSheet(
    st: VpdStage,
    originX: Dp,
    height: Dp,
    title: String,
    closeLabel: String,
    animate: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val c = LocalVpdColors.current
    val shape = RoundedCornerShape(st.dp(14f))
    val slide = remember { Animatable(if (animate) 1f else 0f) }
    LaunchedEffect(Unit) {
        if (slide.value > 0f) slide.animateTo(0f, tween(220))
    }
    BackHandler(onBack = onClose)

    Box(
        modifier = modifier
            .offset(originX + st.dp(6f), st.dp(54f))
            .offset { IntOffset(0, (st.px(60f) * slide.value).roundToInt()) }
            .alpha(1f - 0.6f * slide.value)
            .size(st.dp(348f), height)
            .shadow(st.dp(8f), shape, clip = false)
            .clip(shape)
            .background(Brush.verticalGradient(c.sheet))
            .border(
                st.dp(if (c.isNight) 2.5f else 1.5f),
                Brush.linearGradient(if (c.isNight) c.chrome else listOf(c.chromeFrameDark, c.chromeFrameDark)),
                shape,
            )
            // the sheet swallows every touch so nothing reaches Home underneath
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false).consume()
                    while (true) {
                        val e = awaitPointerEvent()
                        e.changes.forEach { it.consume() }
                        if (e.changes.none { it.pressed }) break
                    }
                }
            },
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(st.dp(52f))
                    .padding(start = st.dp(16f), end = st.dp(8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    color = c.accent,
                    fontFamily = VpdFonts.Heading,
                    fontSize = st.sp(26f),
                )
                Box(
                    modifier = Modifier
                        .height(st.dp(44f))
                        .clip(RoundedCornerShape(st.dp(22f)))
                        .clickable(onClick = onClose)
                        .padding(horizontal = st.dp(14f))
                        .semantics { contentDescription = if (closeLabel == "Done") "Done" else "Close" },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = closeLabel,
                        color = c.textOnSheet,
                        fontFamily = VpdFonts.Body,
                        fontSize = st.sp(if (closeLabel == "Done") 15f else 20f),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                    )
                }
            }
            content()
        }
    }
}
