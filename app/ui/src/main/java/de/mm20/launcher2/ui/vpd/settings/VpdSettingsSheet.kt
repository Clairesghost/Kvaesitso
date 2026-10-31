package de.mm20.launcher2.ui.vpd.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.preferences.ui.VpdColour
import de.mm20.launcher2.preferences.ui.VpdLook
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.ui.settings.SettingsActivity
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.VpdFonts
import de.mm20.launcher2.ui.vpd.vpdColors
import de.mm20.launcher2.ui.vpd.home.VpdMotionGate
import de.mm20.launcher2.ui.vpd.home.VpdStage
import de.mm20.launcher2.ui.vpd.home.VpdHomeVM
import de.mm20.launcher2.ui.vpd.sheet.VpdSheet
import de.mm20.launcher2.ui.vpd.usage.VpdUsage

/** Our own Settings page (D-138, D-177, D-189, D-195): Look, Motion, Dial, Alerts, Calendars, Go back, More. */
@Composable
internal fun VpdSettingsSheet(
    st: VpdStage,
    originX: Dp,
    height: Dp,
    gate: VpdMotionGate,
    onClose: () -> Unit,
    onChooseDial: () -> Unit,
) {
    val c = LocalVpdColors.current
    val context = LocalContext.current
    val vm = viewModel<VpdSettingsVM>()
    val look by vm.lookSetting.collectAsStateWithLifecycle()
    val colour by vm.colour.collectAsStateWithLifecycle()
    val motion by vm.motion.collectAsStateWithLifecycle()
    val returns by vm.ringReturnsHome.collectAsStateWithLifecycle()
    val access by vm.hasNotificationAccess.collectAsStateWithLifecycle()
    val alertApps by vm.alertApps.collectAsStateWithLifecycle()
    val calendars by vm.calendarList.collectAsStateWithLifecycle()
    val hiddenCalendars by vm.hiddenCalendars.collectAsStateWithLifecycle()
    val calendarPermission by vm.hasCalendarPermission.collectAsStateWithLifecycle()
    val homeVm = viewModel<VpdHomeVM>()
    val usageAccess by homeVm.usageAccess.collectAsStateWithLifecycle()
    val animate = remember { gate.animationsAllowed(motion) }

    VpdSheet(
        st = st, originX = originX, height = height, title = "Settings", closeLabel = "Done",
        animate = animate, onClose = onClose,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = st.dp(16f), vertical = st.dp(4f)),
        ) {
            GroupTitle(st, "Look")
            ChoiceRow(
                st, "Day or night look",
                listOf("Auto" to VpdLook.Auto, "Day" to VpdLook.Day, "Night" to VpdLook.Night),
                look, vm::setLook,
            )
            ColourRow(st, colour, vm::setColour)

            GroupTitle(st, "Motion")
            ChoiceRow(
                st, "Motion",
                listOf("Full" to VpdMotion.Full, "Minimal" to VpdMotion.Minimal, "Off" to VpdMotion.Off),
                motion, vm::setMotion,
            )
            Hint(st, "Off stops every animation. Full and Minimal also stop by themselves when the phone is in Battery Saver, the battery is low, or Android's remove-animations is on.")

            GroupTitle(st, "Dial")
            ActionRow(st, "Choose the 12 apps", onChooseDial)
            ActionRow(
                st, "Fill the dial with my most-used apps",
                status = if (usageAccess) "On" else "Tap to allow",
            ) { VpdUsage.openAccessPage(context) }
            Hint(st, "Only how long you use each app; it stays on your phone.")
            ChoiceRow(
                st, "Ring returns home",
                listOf("On" to true, "Off" to false),
                returns, vm::setRingReturnsHome,
            )

            GroupTitle(st, "Alerts")
            ActionRow(
                st,
                if (access) "Notification access: on" else "Notification access: off. Tap to allow",
            ) { openPage(context, Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)) }
            Label(st, "Show on the Alerts card")
            if (alertApps.isEmpty()) {
                Hint(st, "Apps appear here after they send a notification.")
            }
            for (app in alertApps) {
                SwitchRow(st, app.label, !app.hidden) { vm.setAlertHidden(app.packageName, !it) }
            }

            GroupTitle(st, "Calendars")
            if (!calendarPermission) {
                Hint(st, "Allow calendar access from the Calendar card on Home, then the calendars on this phone are listed here.")
            } else if (calendars.isEmpty()) {
                Hint(st, "No calendars found on this phone.")
            }
            for (cal in calendars) {
                SwitchRow(st, cal.name, cal.id !in hiddenCalendars) { vm.setCalendarHidden(cal.id, !it) }
            }

            GroupTitle(st, "Go back to your old launcher")
            Hint(st, "1. Open Android Settings, then Apps.\n2. Tap Choose default apps, then Home app.\n3. Pick One UI Home.")
            ActionRow(st, "Open the Home app page") {
                openPage(context, Intent(Settings.ACTION_HOME_SETTINGS))
            }

            GroupTitle(st, "More")
            ActionRow(st, "More launcher settings") {
                openPage(context, Intent(context, SettingsActivity::class.java))
            }
            Box(Modifier.size(st.dp(16f)))
        }
    }
}

private fun openPage(context: Context, intent: Intent) {
    try {
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (e: ActivityNotFoundException) {
        Log.e("launcher", "Nothing answers ${intent.action}", e)
    } catch (e: SecurityException) {
        Log.e("launcher", "Not allowed to open ${intent.action}", e)
    }
}

@Composable
private fun GroupTitle(st: VpdStage, text: String) {
    val c = LocalVpdColors.current
    Text(
        text,
        modifier = Modifier.padding(top = st.dp(18f), bottom = st.dp(4f)),
        color = c.accent, fontFamily = VpdFonts.Heading, fontSize = st.sp(19f),
    )
}

@Composable
private fun Label(st: VpdStage, text: String) {
    val c = LocalVpdColors.current
    Text(
        text,
        modifier = Modifier.padding(top = st.dp(6f)),
        color = c.textOnSheet, fontFamily = VpdFonts.Body, fontWeight = FontWeight.Medium, fontSize = st.sp(14f),
    )
}

@Composable
private fun Hint(st: VpdStage, text: String) {
    val c = LocalVpdColors.current
    Text(
        text,
        modifier = Modifier.padding(vertical = st.dp(4f)),
        color = c.textDim, fontFamily = VpdFonts.Body, fontSize = st.sp(12f),
    )
}

@Composable
private fun ActionRow(st: VpdStage, text: String, status: String? = null, onClick: () -> Unit) {
    val c = LocalVpdColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = st.dp(48f))
            .clip(RoundedCornerShape(st.dp(10f)))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text, Modifier.weight(1f),
            color = c.textOnSheet, fontFamily = VpdFonts.Body, fontWeight = FontWeight.Medium, fontSize = st.sp(14f),
        )
        if (status != null) {
            Text(status, color = c.textDim, fontFamily = VpdFonts.Body, fontSize = st.sp(12f))
            Box(Modifier.size(st.dp(6f)))
        }
        Text("›", color = c.accent, fontSize = st.sp(22f))
    }
}

@Composable
private fun SwitchRow(st: VpdStage, text: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    val c = LocalVpdColors.current
    Row(
        modifier = Modifier.fillMaxWidth().heightIn(min = st.dp(48f)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text, Modifier.weight(1f).padding(end = st.dp(8f)),
            color = c.textOnSheet, fontFamily = VpdFonts.Body, fontSize = st.sp(14f),
        )
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = c.accent,
                checkedThumbColor = Color(0xFFFFFAF0),
                uncheckedTrackColor = c.textDim.copy(alpha = 0.25f),
                uncheckedThumbColor = c.textDim,
                uncheckedBorderColor = c.textDim,
            ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> ChoiceRow(
    st: VpdStage,
    label: String,
    options: List<Pair<String, T>>,
    selected: T,
    onSelect: (T) -> Unit,
) {
    val c = LocalVpdColors.current
    Column(Modifier.padding(vertical = st.dp(4f))) {
        Text(
            label, color = c.textOnSheet, fontFamily = VpdFonts.Body,
            fontWeight = FontWeight.Medium, fontSize = st.sp(14f),
        )
        FlowRow(
            modifier = Modifier.padding(top = st.dp(6f)),
            horizontalArrangement = Arrangement.spacedBy(st.dp(6f)),
            verticalArrangement = Arrangement.spacedBy(st.dp(6f)),
        ) {
            for ((name, value) in options) {
                Pill(st, name, value == selected) { onSelect(value) }
            }
        }
    }
}

@Composable
private fun Pill(st: VpdStage, text: String, selected: Boolean, swatch: Color? = null, onClick: () -> Unit) {
    val c = LocalVpdColors.current
    val shape = RoundedCornerShape(st.dp(18f))
    Row(
        modifier = Modifier
            .heightIn(min = st.dp(36f))
            .clip(shape)
            .background(if (selected) c.accent else Color.Transparent)
            .border(st.dp(1.2f), c.accent, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = st.dp(14f)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (swatch != null) {
            Box(
                Modifier
                    .size(st.dp(12f))
                    .background(swatch, CircleShape)
                    .border(st.dp(1f), Color(0x66000000), CircleShape),
            )
            Box(Modifier.size(st.dp(6f)))
        }
        Text(
            text,
            color = if (selected) (if (c.isNight) Color(0xFF1C1C1F) else Color(0xFFFFFAF0)) else c.accent,
            fontFamily = VpdFonts.Body, fontWeight = FontWeight.SemiBold, fontSize = st.sp(13f),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColourRow(st: VpdStage, selected: VpdColour, onSelect: (VpdColour) -> Unit) {
    val c = LocalVpdColors.current
    Column(Modifier.padding(vertical = st.dp(4f))) {
        Text(
            "Colour", color = c.textOnSheet, fontFamily = VpdFonts.Body,
            fontWeight = FontWeight.Medium, fontSize = st.sp(14f),
        )
        FlowRow(
            modifier = Modifier.padding(top = st.dp(6f)),
            horizontalArrangement = Arrangement.spacedBy(st.dp(6f)),
            verticalArrangement = Arrangement.spacedBy(st.dp(6f)),
        ) {
            for (option in VpdColour.entries) {
                Pill(
                    st, option.name, option == selected,
                    swatch = vpdColors(false, option).ground.first(),
                ) { onSelect(option) }
            }
        }
    }
}
