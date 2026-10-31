package de.mm20.launcher2.ui.vpd.cards

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import de.mm20.launcher2.search.CalendarEvent
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.home.VpdStage
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle as JavaTextStyle
import java.util.Date
import java.util.Locale

/** What the card shows, worked out from the event list and the clock (no timers). */
internal data class VpdAgenda(
    val next: CalendarEvent?,
    val then: List<CalendarEvent>,
    /** true when nothing is left today ([next] is then the first later event, if any). */
    val nothingToday: Boolean,
)

internal fun agendaFor(events: List<CalendarEvent>, now: Long): VpdAgenda {
    val zone = ZoneId.systemDefault()
    val endOfToday = LocalDate.now().plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()
    val today = events.filter { it.endTime > now && (it.startTime ?: it.endTime) < endOfToday }
    if (today.isNotEmpty()) return VpdAgenda(today.first(), today.drop(1).take(2), false)
    val later = events.firstOrNull { (it.startTime ?: it.endTime) >= endOfToday }
    return VpdAgenda(later, emptyList(), true)
}

/** Calendar card: date tab (weather space stays empty until B5), next event, "Then…" line. */
@Composable
internal fun VpdCalendarCard(
    st: VpdStage,
    events: List<CalendarEvent>,
    hasPermission: Boolean,
    now: Long,
    onOpenCalendar: () -> Unit,
    onAskPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalVpdColors.current
    val context = LocalContext.current
    val agenda = remember(events, now) { agendaFor(events, now) }
    val today = remember(now) { LocalDate.now() }

    VpdPanel(
        st = st,
        modifier = modifier,
        onClick = if (hasPermission) onOpenCalendar else onAskPermission,
    ) {
        Row(Modifier.fillMaxSize()) {
            // date tab; the space above the number is for the weather icon and temperature (B5)
            Column(
                modifier = Modifier
                    .width(st.dp(72f))
                    .fillMaxHeight()
                    .drawBehind {
                        drawLine(
                            c.textOnCard.copy(alpha = 0.4f),
                            Offset(size.width, st.px(6f)),
                            Offset(size.width, size.height - st.px(6f)),
                            strokeWidth = st.px(1f),
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    today.dayOfMonth.toString(),
                    style = TextStyle(
                        fontFamily = VpdFonts.Heading,
                        fontWeight = FontWeight.Bold,
                        fontSize = st.sp(34f),
                        color = c.textOnCard,
                    ),
                )
                Text(
                    today.dayOfWeek.getDisplayName(JavaTextStyle.SHORT, Locale.getDefault()) + " · " +
                            today.month.getDisplayName(JavaTextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.padding(bottom = st.dp(8f)),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = VpdFonts.Body,
                        fontWeight = FontWeight.Bold,
                        fontSize = st.sp(9f),
                        color = c.textOnCard,
                    ),
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = st.dp(12f)),
                verticalArrangement = Arrangement.Center,
            ) {
                val next = agenda.next
                when {
                    !hasPermission -> {
                        CardScript(st, "Show your calendar")
                        CardLine(st, "Tap to let the launcher read your events.", 11f, 0.75f)
                    }
                    agenda.nothingToday || next == null -> {
                        CardScript(st, "Nothing on today")
                        if (next != null) {
                            CardLine(
                                st,
                                "Next: ${eventName(next)}, ${dayWord(next)} ${timeWord(next, context)}",
                                11f, 0.75f,
                            )
                        }
                    }
                    else -> {
                        CardScript(st, eventName(next))
                        CardLine(st, nowOrTime(next, now, context), 12f, 1f, FontWeight.Medium)
                        if (agenda.then.isNotEmpty()) {
                            CardLine(
                                st,
                                "Then " + agenda.then.joinToString(" · ") {
                                    "${eventName(it)} ${timeWord(it, context)}"
                                },
                                10.5f, 0.75f,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardScript(st: VpdStage, text: String) {
    Text(
        text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            fontFamily = VpdFonts.Heading,
            fontWeight = FontWeight.Bold,
            fontSize = st.sp(19f),
            color = LocalVpdColors.current.textOnCard,
        ),
    )
}

@Composable
private fun CardLine(
    st: VpdStage,
    text: String,
    size: Float,
    alpha: Float,
    weight: FontWeight = FontWeight.Normal,
) {
    Text(
        text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            fontFamily = VpdFonts.Body,
            fontWeight = weight,
            fontSize = st.sp(size),
            color = LocalVpdColors.current.textOnCard.copy(alpha = alpha),
        ),
    )
}

private fun eventName(e: CalendarEvent): String = e.label.ifBlank { "Event" }

private fun timeWord(e: CalendarEvent, context: Context): String {
    if (e.allDay) return "all day"
    return DateFormat.getTimeFormat(context).format(Date(e.startTime ?: e.endTime))
}

private fun nowOrTime(e: CalendarEvent, now: Long, context: Context): String {
    if (e.allDay) return "All day"
    val start = e.startTime ?: e.endTime
    if (start <= now) {
        return "Now, until " + DateFormat.getTimeFormat(context).format(Date(e.endTime))
    }
    return timeWord(e, context)
}

private fun dayWord(e: CalendarEvent): String {
    val day = Instant.ofEpochMilli(e.startTime ?: e.endTime).atZone(ZoneId.systemDefault()).toLocalDate()
    return if (day == LocalDate.now().plusDays(1)) "tomorrow"
    else day.dayOfWeek.getDisplayName(JavaTextStyle.SHORT, Locale.getDefault())
}
