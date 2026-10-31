package de.mm20.launcher2.ui.vpd.apps

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.Disc
import de.mm20.launcher2.ui.vpd.home.VpdMotionGate
import de.mm20.launcher2.ui.vpd.home.VpdStage
import de.mm20.launcher2.ui.vpd.sheet.VpdSheet
import de.mm20.launcher2.ui.vpd.sheet.vpdTapOrHold
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val PRESS_DELAY_MS = 120L
private const val GLOW_MS = 1600L
private val Letters = ('A'..'Z').toList() + '#'

private sealed interface ListRow {
    data class Header(val category: VpdAppCategory) : ListRow
    data class Tiles(val apps: List<VpdApp>) : ListRow
}

private fun letterOf(label: String): Char {
    val ch = label.firstOrNull { it.isLetterOrDigit() }?.uppercaseChar() ?: return '#'
    return if (ch in 'A'..'Z') ch else '#'
}

/** App list (D-135, D-168, D-171): Find field, settings row, chips, category sections, letter strip. */
@Composable
internal fun VpdAppListSheet(
    st: VpdStage,
    originX: Dp,
    height: Dp,
    motion: VpdMotion,
    gate: VpdMotionGate,
    onClose: () -> Unit,
    onOpenSettings: () -> Unit,
    onPutOnDial: (VpdApp) -> Unit,
) {
    val c = LocalVpdColors.current
    val context = LocalContext.current
    val vm = viewModel<VpdAppsVM>()
    val scope = rememberCoroutineScope()
    val apps by vm.apps.collectAsStateWithLifecycle()
    val animate = remember { gate.animationsAllowed(motion) }

    var query by remember { mutableStateOf("") }
    var chip by remember { mutableStateOf<VpdAppCategory?>(null) }
    var pressedKey by remember { mutableStateOf<String?>(null) }
    var menuFor by remember { mutableStateOf<VpdApp?>(null) }
    var moveFor by remember { mutableStateOf<VpdApp?>(null) }
    var glowLetter by remember { mutableStateOf<Char?>(null) }
    var sliding by remember { mutableStateOf(false) }
    var glowAnimated by remember { mutableStateOf(false) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { pressedKey = null }

    val filtered = remember(apps, query) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) apps else apps.filter { it.label.lowercase().contains(q) }
    }
    val categoriesPresent = remember(filtered) {
        VpdAppCategory.entries.filter { cat -> filtered.any { it.category == cat } }
    }
    val activeChip = chip?.takeIf { it in categoriesPresent }
    val rows = remember(filtered, activeChip) {
        buildList<ListRow> {
            for (cat in categoriesPresent) {
                if (activeChip != null && cat != activeChip) continue
                add(ListRow.Header(cat))
                filtered.filter { it.category == cat }.chunked(4).forEach { add(ListRow.Tiles(it)) }
            }
        }
    }
    val listState = rememberLazyListState()

    // the glow ends 1.6 s after the thumb leaves the strip (one delay, no repeating timer)
    LaunchedEffect(glowLetter, sliding) {
        if (glowLetter != null && !sliding) {
            delay(GLOW_MS)
            glowLetter = null
        }
    }

    fun launch(app: VpdApp) {
        scope.launch {
            val animated = gate.animationsAllowed(motion)
            pressedKey = app.app.key
            if (animated) delay(PRESS_DELAY_MS)
            pressedKey = null
            val options = ActivityOptionsCompat.makeBasic().toBundle()
            if (app.app.launch(context, options)) {
                vm.reportLaunch(app.app)
                onClose()
            } else {
                Log.e("launcher", "Could not launch an app from the list")
            }
        }
    }

    VpdSheet(
        st = st, originX = originX, height = height, title = "Apps", closeLabel = "✕",
        animate = animate, onClose = onClose,
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = st.dp(12f))) {
            // Find an app
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(st.dp(40f))
                    .clip(RoundedCornerShape(st.dp(20f)))
                    .background(c.textOnSheet.copy(alpha = 0.08f))
                    .padding(horizontal = st.dp(16f)),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (query.isEmpty()) {
                    Text("Find an app", color = c.textPlaceholder, fontFamily = VpdFonts.Body, fontSize = st.sp(14f))
                }
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    singleLine = true,
                    textStyle = TextStyle(color = c.textOnSheet, fontFamily = VpdFonts.Body, fontSize = st.sp(14f)),
                    cursorBrush = Brush.verticalGradient(listOf(c.accent, c.accent)),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            // Launcher settings row
            Row(
                modifier = Modifier
                    .padding(top = st.dp(8f))
                    .fillMaxWidth()
                    .height(st.dp(44f))
                    .clip(RoundedCornerShape(st.dp(10f)))
                    .clickable(onClick = onOpenSettings)
                    .padding(horizontal = st.dp(6f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "Launcher settings", Modifier.weight(1f), color = c.textOnSheet,
                    fontFamily = VpdFonts.Body, fontWeight = FontWeight.SemiBold, fontSize = st.sp(14f),
                )
                Text("›", color = c.accent, fontSize = st.sp(22f))
            }
            // category chips
            LazyRow(
                modifier = Modifier.padding(vertical = st.dp(6f)),
                horizontalArrangement = Arrangement.spacedBy(st.dp(6f)),
            ) {
                item { Chip(st, "All", activeChip == null) { chip = null } }
                items(categoriesPresent.size) { i ->
                    val cat = categoriesPresent[i]
                    Chip(st, cat.label, activeChip == cat) { chip = cat }
                }
            }
            // list and letter strip
            Box(Modifier.fillMaxWidth().weight(1f)) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize().padding(end = st.dp(26f)),
                ) {
                    items(rows.size) { i ->
                        when (val row = rows[i]) {
                            is ListRow.Header -> Text(
                                row.category.label,
                                modifier = Modifier.padding(top = st.dp(10f), bottom = st.dp(4f), start = st.dp(4f)),
                                color = c.accent, fontFamily = VpdFonts.Heading, fontSize = st.sp(17f),
                            )
                            is ListRow.Tiles -> Row(Modifier.fillMaxWidth()) {
                                for (j in 0 until 4) {
                                    val app = row.apps.getOrNull(j)
                                    Box(Modifier.weight(1f), contentAlignment = Alignment.TopCenter) {
                                        if (app != null) {
                                            AppTile(
                                                st = st, app = app,
                                                pressed = pressedKey == app.app.key,
                                                lit = glowLetter != null && letterOf(app.label) == glowLetter,
                                                glowAnimated = glowAnimated,
                                                iconFor = vm::icon,
                                                onPress = { pressedKey = app.app.key },
                                                onCancel = { pressedKey = null },
                                                onTap = { launch(app) },
                                                onHold = { pressedKey = null; menuFor = app },
                                            )
                                            DropdownMenu(
                                                expanded = menuFor?.app?.key == app.app.key,
                                                onDismissRequest = { menuFor = null },
                                            ) {
                                                DropdownMenuItem(
                                                    text = { MenuText("Move to…") },
                                                    onClick = { moveFor = app; menuFor = null },
                                                )
                                                DropdownMenuItem(
                                                    text = { MenuText("Put on the dial") },
                                                    onClick = { menuFor = null; onPutOnDial(app) },
                                                )
                                                DropdownMenuItem(
                                                    text = { MenuText("App info") },
                                                    onClick = { menuFor = null; app.app.openAppDetails(context) },
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(st.dp(24f))
                        .fillMaxHeight()
                        .pointerInput(rows) {
                            fun letterAt(y: Float): Char {
                                val i = (y / size.height * Letters.size).toInt().coerceIn(0, Letters.lastIndex)
                                return Letters[i]
                            }
                            suspend fun jump(l: Char) {
                                val index = rows.indexOfFirst { r ->
                                    r is ListRow.Tiles && r.apps.any { letterOf(it.label) == l }
                                }
                                if (index >= 0) listState.scrollToItem(index)
                            }
                            awaitEachGesture {
                                val down = awaitFirstDown(requireUnconsumed = false)
                                down.consume()
                                glowAnimated = gate.animationsAllowed(motion)
                                sliding = true
                                var current = letterAt(down.position.y)
                                glowLetter = current
                                scope.launch { jump(current) }
                                while (true) {
                                    val e = awaitPointerEvent()
                                    val ch = e.changes.firstOrNull { it.id == down.id } ?: break
                                    ch.consume()
                                    if (!ch.pressed) break
                                    val l = letterAt(ch.position.y)
                                    if (l != current) {
                                        current = l
                                        glowLetter = l
                                        scope.launch { jump(l) }
                                    }
                                }
                                sliding = false
                                // without the motion gate the highlight clears on release
                                if (!glowAnimated) glowLetter = null
                            }
                        },
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    for (l in Letters) {
                        Text(
                            l.toString(),
                            color = if (glowLetter == l) c.accent else c.textDim,
                            fontFamily = VpdFonts.Body, fontWeight = FontWeight.Bold,
                            fontSize = st.sp(11f),
                        )
                    }
                }
                // big letter bubble while sliding
                val bubble = glowLetter
                if (sliding && bubble != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(st.dp(84f))
                            .background(c.accent, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            bubble.toString(), color = Color(0xFFFFFAF0),
                            fontFamily = VpdFonts.Heading, fontSize = st.sp(44f),
                        )
                    }
                }
            }
        }
    }

    moveFor?.let { app ->
        AlertDialog(
            onDismissRequest = { moveFor = null },
            title = { Text("Move ${app.label} to…") },
            text = {
                Column {
                    for (cat in VpdAppCategory.entries) {
                        TextButton(onClick = { vm.move(app, cat); moveFor = null }) {
                            Text(cat.label + if (cat == app.category) "  ✓" else "")
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { moveFor = null }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun MenuText(text: String) {
    Text(text, fontFamily = VpdFonts.Body, fontWeight = FontWeight.Medium)
}

@Composable
private fun Chip(st: VpdStage, label: String, selected: Boolean, onClick: () -> Unit) {
    val c = LocalVpdColors.current
    Box(
        modifier = Modifier
            .height(st.dp(32f))
            .clip(RoundedCornerShape(st.dp(16f)))
            .background(if (selected) c.accent else Color.Transparent)
            .border(st.dp(1.2f), c.accent, RoundedCornerShape(st.dp(16f)))
            .clickable(onClick = onClick)
            .padding(horizontal = st.dp(14f)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            color = if (selected) (if (c.isNight) Color(0xFF1C1C1F) else Color(0xFFFFFAF0)) else c.accent,
            fontFamily = VpdFonts.Body, fontWeight = FontWeight.SemiBold, fontSize = st.sp(13f),
        )
    }
}

@Composable
private fun AppTile(
    st: VpdStage,
    app: VpdApp,
    pressed: Boolean,
    lit: Boolean,
    glowAnimated: Boolean,
    iconFor: (SavableSearchable, Int) -> Flow<LauncherIcon?>,
    onPress: () -> Unit,
    onCancel: () -> Unit,
    onTap: () -> Unit,
    onHold: () -> Unit,
) {
    val c = LocalVpdColors.current
    val glow by animateFloatAsState(
        targetValue = if (lit) 1f else 0f,
        animationSpec = if (glowAnimated) tween(250) else snap(),
        label = "letterGlow",
    )
    Column(
        modifier = Modifier
            .padding(vertical = st.dp(4f))
            .vpdTapOrHold(onPress = onPress, onCancel = onCancel, onHold = onHold, onTap = onTap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(st.dp(54f))
                .drawBehind {
                    if (glow > 0f) {
                        drawCircle(
                            Brush.radialGradient(
                                listOf(c.accent.copy(alpha = 0.75f * glow), Color.Transparent),
                                radius = size.minDimension * 0.62f,
                            ),
                        )
                    }
                }
                .scale(if (pressed) 0.9f else 1f),
            contentAlignment = Alignment.Center,
        ) {
            Disc(st, c, app.app, pressed, iconFor)
        }
        Text(
            app.label,
            modifier = Modifier.width(st.dp(70f)).padding(top = st.dp(2f)),
            color = c.textOnSheet, textAlign = TextAlign.Center,
            maxLines = 1, overflow = TextOverflow.Ellipsis,
            fontFamily = VpdFonts.Body, fontSize = st.sp(11f),
        )
    }
}
