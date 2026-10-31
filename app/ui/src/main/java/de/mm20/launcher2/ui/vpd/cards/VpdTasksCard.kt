package de.mm20.launcher2.ui.vpd.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.getBottom
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.VpdStage
import de.mm20.launcher2.ui.vpd.tasks.VpdTask

/** Row height of the Tasks card. Header 22 + three rows of 24 = 94. */
private const val ROW = 24f

/** Most task rows shown at once (D-187); the rest is "+N more" (D-193). */
private const val SHOWN = 2

/**
 * Tasks card (D-186 to D-193). Two task rows (newest first) and the "Add a task…" row.
 * [tasks] is every stored task, newest first, crossed-out ones included.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun VpdTasksCard(
    st: VpdStage,
    tasks: List<VpdTask>,
    onAdd: (String) -> Unit,
    onToggle: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalVpdColors.current
    val dotted = c.textOnCard.copy(alpha = 0.35f)
    var adding by remember { mutableStateOf(false) }

    // Back (or anything else that closes the keyboard) cancels the add line.
    val imeVisible = WindowInsets.isImeVisible
    var imeWasShown by remember { mutableStateOf(false) }
    LaunchedEffect(adding, imeVisible) {
        if (!adding) {
            imeWasShown = false
        } else if (imeVisible) {
            imeWasShown = true
        } else if (imeWasShown) {
            adding = false
        }
    }

    // While typing, lift the card so the line stays above the keyboard (no animation).
    val density = LocalDensity.current
    val rootHeight = LocalView.current.rootView.height
    val imeBottom = WindowInsets.ime.getBottom(density)
    var cardBottom by remember { mutableFloatStateOf(0f) }
    val lift = if (adding && imeVisible) {
        (cardBottom - (rootHeight - imeBottom) + with(density) { st.dp(8f).toPx() }).coerceAtLeast(0f)
    } else 0f

    val shown = tasks.take(SHOWN)
    val more = tasks.size - shown.size

    VpdPanel(
        st = st,
        modifier = modifier
            .onGloballyPositioned { cardBottom = it.boundsInWindow().bottom }
            .graphicsLayer { translationY = -lift },
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = st.dp(10f))) {
            Row(
                modifier = Modifier.fillMaxWidth().height(st.dp(22f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "Tasks",
                    style = TextStyle(
                        fontFamily = VpdFonts.Heading,
                        fontWeight = FontWeight.Bold,
                        fontSize = st.sp(15f),
                        color = c.textOnCard,
                    ),
                )
                if (more > 0) {
                    Text(
                        "+$more more",
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontWeight = FontWeight.Medium,
                            fontSize = st.sp(11f),
                            color = c.textOnCard.copy(alpha = 0.7f),
                        ),
                    )
                }
            }
            for (slot in 0 until SHOWN) {
                val task = shown.getOrNull(slot)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(st.dp(ROW))
                        .vpdDottedTop(st, dotted)
                        .then(if (task != null) Modifier.clickable { onToggle(task.id) } else Modifier),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (task != null) {
                        TickBox(st, checked = task.done)
                        Text(
                            task.text,
                            modifier = Modifier.padding(start = st.dp(8f)),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontFamily = VpdFonts.Body,
                                fontWeight = FontWeight.Medium,
                                fontSize = st.sp(12f),
                                color = c.textOnCard.copy(alpha = if (task.done) 0.55f else 1f),
                                textDecoration = if (task.done) TextDecoration.LineThrough else null,
                            ),
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(st.dp(ROW))
                    .vpdDottedTop(st, dotted)
                    .then(if (adding) Modifier else Modifier.clickable { adding = true }),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (adding) {
                    AddLine(
                        st = st,
                        onSave = { text ->
                            onAdd(text)
                            adding = false
                        },
                    )
                } else {
                    Text(
                        "Add a task…",
                        maxLines = 1,
                        style = TextStyle(
                            fontFamily = VpdFonts.Body,
                            fontWeight = FontWeight.Medium,
                            fontSize = st.sp(12f),
                            color = c.textOnCard.copy(alpha = 0.6f),
                        ),
                    )
                }
            }
        }
    }
}

/** The inline text field. Done saves (empty saves nothing). */
@Composable
private fun AddLine(st: VpdStage, onSave: (String) -> Unit) {
    val c = LocalVpdColors.current
    var text by remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }
    LaunchedEffect(Unit) { focus.requestFocus() }
    BasicTextField(
        value = text,
        onValueChange = { text = it.replace("\n", " ") },
        modifier = Modifier.fillMaxWidth().focusRequester(focus),
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = VpdFonts.Body,
            fontWeight = FontWeight.Medium,
            fontSize = st.sp(12f),
            color = c.textOnCard,
        ),
        cursorBrush = SolidColor(c.accent),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = { onSave(text) }),
    )
}

/** Small tick box (D-115): rounded square, with a check mark when [checked]. */
@Composable
private fun TickBox(st: VpdStage, checked: Boolean) {
    val c = LocalVpdColors.current
    val line = c.textOnCard
    Canvas(
        Modifier
            .size(st.dp(12f))
            .border(st.dp(1.5f), line, RoundedCornerShape(st.dp(3f)))
    ) {
        if (checked) {
            val w = size.width
            val h = size.height
            val path = Path().apply {
                moveTo(w * 0.22f, h * 0.52f)
                lineTo(w * 0.42f, h * 0.72f)
                lineTo(w * 0.78f, h * 0.28f)
            }
            drawPath(path, line, style = Stroke(width = st.px(1.5f), cap = StrokeCap.Round))
        }
    }
}
