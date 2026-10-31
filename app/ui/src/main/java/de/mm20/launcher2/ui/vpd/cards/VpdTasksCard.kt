package de.mm20.launcher2.ui.vpd.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.VpdStage

/** Row height of the Tasks card. Header 22 + three rows of 24 = 94. */
private const val ROW = 24f

/**
 * Tasks card shell (D-186 to D-189). Until G1 the first row says "Connect Google Tasks".
 * G1 puts up to two task rows (tick box + text) in the first two row slots and the
 * "Add a task…" row in the third; nothing is stored here.
 */
@Composable
internal fun VpdTasksCard(
    st: VpdStage,
    onConnect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalVpdColors.current
    VpdPanel(st = st, modifier = modifier) {
        Column(Modifier.fillMaxSize().padding(horizontal = st.dp(10f))) {
            Text(
                "Tasks",
                modifier = Modifier.height(st.dp(22f)),
                style = TextStyle(
                    fontFamily = VpdFonts.Heading,
                    fontWeight = FontWeight.Bold,
                    fontSize = st.sp(15f),
                    color = c.textOnCard,
                ),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(st.dp(ROW))
                    .vpdDottedTop(st, c.textOnCard.copy(alpha = 0.35f))
                    .clickable(onClick = onConnect),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Connect Google Tasks",
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
        }
    }
}
