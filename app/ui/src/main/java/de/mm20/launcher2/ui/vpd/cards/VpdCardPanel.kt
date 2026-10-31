package de.mm20.launcher2.ui.vpd.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.home.VpdStage

/** Placard: card colours, 3 px black frame, 6 px corners, chrome bezel, soft shadow (design system "Panel"). */
@Composable
internal fun VpdPanel(
    st: VpdStage,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val c = LocalVpdColors.current
    val shape = RoundedCornerShape(st.dp(6f))
    Box(
        modifier = modifier
            .shadow(st.dp(5f), shape, clip = false)
            .drawBehind { bezel(st.k, c.chromeFrameLight, c.chromeFrameDark) }
            .clip(shape)
            .background(Brush.verticalGradient(c.card))
            .border(st.dp(3f), c.cardFrame, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        content = content,
    )
}

private fun DrawScope.bezel(k: Float, light: Color, dark: Color) {
    fun ring(centre: Float, width: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(-centre, -centre),
            size = Size(size.width + 2 * centre, size.height + 2 * centre),
            cornerRadius = CornerRadius(6f * k + centre),
            style = Stroke(width),
        )
    }
    ring(1f * k, 2f * k, light)
    ring(2.75f * k, 1.5f * k, dark)
}

/** Dotted divider along the top edge of a row. */
internal fun Modifier.vpdDottedTop(st: VpdStage, color: Color): Modifier = drawBehind {
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = st.px(1f),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(st.px(1f), st.px(2f))),
    )
}
