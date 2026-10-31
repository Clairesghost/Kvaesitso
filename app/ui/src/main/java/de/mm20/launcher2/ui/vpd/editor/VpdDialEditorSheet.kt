package de.mm20.launcher2.ui.vpd.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.apps.VpdAppsVM
import de.mm20.launcher2.ui.vpd.home.DIAL_BOX_H
import de.mm20.launcher2.ui.vpd.home.DIAL_BOX_W
import de.mm20.launcher2.ui.vpd.home.DIAL_CX
import de.mm20.launcher2.ui.vpd.home.DIAL_CY
import de.mm20.launcher2.ui.vpd.home.DIAL_SCALE
import de.mm20.launcher2.ui.vpd.home.Disc
import de.mm20.launcher2.ui.vpd.home.VpdClock
import de.mm20.launcher2.ui.vpd.home.VpdHomeVM
import de.mm20.launcher2.ui.vpd.home.VpdMotionGate
import de.mm20.launcher2.ui.vpd.home.VpdStage
import de.mm20.launcher2.ui.vpd.home.drawDialPlate
import de.mm20.launcher2.ui.vpd.sheet.VpdSheet
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private const val SLOTS = 12
private const val MINI = 0.82f
private const val RING_R = 112f
private const val DISC_R = 23f

/**
 * Dial editor (D-141, D-143): a mini dial drawn with the Home dial's own code. Drag a disc onto
 * another to swap, tap a disc to replace it from a list (with Find), tap an empty disc to fill it.
 * Every change is saved at once and Home shows it at once.
 */
@Composable
internal fun VpdDialEditorSheet(
    st: VpdStage,
    originX: Dp,
    height: Dp,
    place: SavableSearchable?,
    motion: VpdMotion,
    gate: VpdMotionGate,
    onPlaced: () -> Unit,
    onClose: () -> Unit,
) {
    val c = LocalVpdColors.current
    val homeVm = viewModel<VpdHomeVM>()
    val appsVm = viewModel<VpdAppsVM>()
    val dial by homeVm.dialApps.collectAsStateWithLifecycle()
    val installed by appsVm.apps.collectAsStateWithLifecycle()
    val animate = remember { gate.animationsAllowed(motion) }

    val slots: List<SavableSearchable?> = List(SLOTS) { dial.getOrNull(it) }
    val slotsNow by rememberUpdatedState(slots)
    val placeNow by rememberUpdatedState(place)

    var pickSlot by remember { mutableStateOf<Int?>(null) }
    var query by remember { mutableStateOf("") }
    var dragFrom by remember { mutableStateOf(-1) }
    var dragPos by remember { mutableStateOf(Offset.Zero) }

    val est = remember(st.k, st.density) { VpdStage(st.k * MINI, st.density) }
    val ds = remember(est.k, st.density) { VpdStage(est.k * DIAL_SCALE, st.density) }
    val small = remember(st.k, st.density) { VpdStage(st.k * 0.7f, st.density) }

    fun set(index: Int, app: SavableSearchable?) {
        val list = slotsNow.toMutableList()
        // an app sits on the dial once: moving it empties its old disc
        if (app != null) for (i in list.indices) if (list[i]?.key == app.key) list[i] = null
        list[index] = app
        homeVm.saveDial(list)
    }

    VpdSheet(
        st = st, originX = originX, height = height, title = "The 12 apps", closeLabel = "Done",
        animate = animate, onClose = onClose,
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = st.dp(12f))) {
            Text(
                if (place != null) "Tap a disc to put ${place.label} there."
                else "Drag a disc onto another to swap. Tap a disc to change it.",
                color = c.textDim, fontFamily = VpdFonts.Body, fontSize = st.sp(12f),
                modifier = Modifier.padding(horizontal = st.dp(4f), vertical = st.dp(2f)),
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                Box(
                    modifier = Modifier
                        .size(est.dp(DIAL_BOX_W), est.dp(DIAL_BOX_H))
                        .pointerInput(est.k) {
                            val centre = Offset(DIAL_CX * est.k, DIAL_CY * est.k)
                            fun discCentre(i: Int): Offset {
                                val a = Math.toRadians((i * 30.0))
                                return Offset(
                                    centre.x + RING_R * ds.k * sin(a).toFloat(),
                                    centre.y - RING_R * ds.k * cos(a).toFloat(),
                                )
                            }
                            fun hit(p: Offset): Int {
                                for (i in 0 until SLOTS) {
                                    if ((p - discCentre(i)).getDistance() <= (DISC_R + 4f) * ds.k) return i
                                }
                                return -1
                            }
                            awaitEachGesture {
                                val down = awaitFirstDown(requireUnconsumed = false)
                                val from = hit(down.position)
                                if (from < 0) return@awaitEachGesture
                                down.consume()
                                var dragging = false
                                while (true) {
                                    val e = awaitPointerEvent()
                                    val ch = e.changes.firstOrNull { it.id == down.id } ?: break
                                    ch.consume()
                                    if (!ch.pressed) {
                                        if (dragging) {
                                            val to = hit(ch.position)
                                            dragFrom = -1
                                            if (to >= 0 && to != from) {
                                                val list = slotsNow.toMutableList()
                                                val tmp = list[from]
                                                list[from] = list[to]
                                                list[to] = tmp
                                                homeVm.saveDial(list)
                                            }
                                        } else {
                                            val p = placeNow
                                            if (p != null) {
                                                set(from, p)
                                                onPlaced()
                                            } else {
                                                pickSlot = from
                                                query = ""
                                            }
                                        }
                                        break
                                    }
                                    if (!dragging && (ch.position - down.position).getDistance() > 8f * est.k) {
                                        dragging = true
                                        dragFrom = from
                                    }
                                    if (dragging) dragPos = ch.position
                                }
                                dragFrom = -1
                            }
                        },
                ) {
                    Canvas(Modifier.size(est.dp(DIAL_BOX_W), est.dp(DIAL_BOX_H))) {
                        drawDialPlate(Offset(DIAL_CX * est.k, DIAL_CY * est.k), ds.k, c)
                    }
                    VpdClock(st = est, modifier = Modifier.size(est.dp(DIAL_BOX_W), est.dp(DIAL_BOX_H)))
                    for (i in 0 until SLOTS) {
                        val app = slots[i]
                        val a = Math.toRadians(i * 30.0)
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        (est.px(DIAL_CX) + ds.px(RING_R) * sin(a).toFloat() - ds.px(DISC_R)).roundToInt(),
                                        (est.px(DIAL_CY) - ds.px(RING_R) * cos(a).toFloat() - ds.px(DISC_R)).roundToInt(),
                                    )
                                }
                                .size(ds.dp(DISC_R * 2f))
                                .scale(if (pickSlot == i) 1.12f else 1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (dragFrom != i) Disc(ds, c, app, pickSlot == i, homeVm::icon)
                            if (app == null && dragFrom != i) {
                                Text("+", color = c.accent, fontSize = ds.sp(22f), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    if (dragFrom >= 0) {
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        (dragPos.x - ds.px(DISC_R)).roundToInt(),
                                        (dragPos.y - ds.px(DISC_R)).roundToInt(),
                                    )
                                }
                                .size(ds.dp(DISC_R * 2f))
                                .scale(1.15f),
                        ) {
                            Disc(ds, c, slots[dragFrom], true, homeVm::icon)
                        }
                    }
                }
            }

            // replace list
            val slot = pickSlot
            if (slot != null) {
                Text(
                    "Put on this disc", color = c.accent, fontFamily = VpdFonts.Heading, fontSize = st.sp(17f),
                    modifier = Modifier.padding(top = st.dp(4f), start = st.dp(4f)),
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = st.dp(6f))
                        .fillMaxWidth()
                        .height(st.dp(38f))
                        .clip(RoundedCornerShape(st.dp(19f)))
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
                val onDial = slots.mapNotNull { it?.key }.toSet()
                val q = query.trim().lowercase()
                val choices = installed.filter {
                    it.app.key !in onDial && (q.isEmpty() || it.label.lowercase().contains(q))
                }
                LazyColumn(Modifier.fillMaxWidth().weight(1f)) {
                    if (slots[slot] != null) {
                        item {
                            PickRow(st, "Leave this disc empty") {
                                set(slot, null)
                                pickSlot = null
                            }
                        }
                    }
                    items(choices.size) { i ->
                        val choice = choices[i]
                        PickRow(st, choice.label, icon = { Disc(small, c, choice.app, false, appsVm::icon) }) {
                            set(slot, choice.app)
                            pickSlot = null
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PickRow(st: VpdStage, text: String, icon: (@Composable () -> Unit)? = null, onClick: () -> Unit) {
    val c = LocalVpdColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = st.dp(46f))
            .clickable(onClick = onClick)
            .padding(horizontal = st.dp(4f), vertical = st.dp(3f)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
            Box(Modifier.size(st.dp(10f)))
        }
        Text(text, color = c.textOnSheet, fontFamily = VpdFonts.Body, fontSize = st.sp(14f))
    }
}
