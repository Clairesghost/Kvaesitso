package de.mm20.launcher2.ui.vpd.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.permissions.PermissionsManager
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.ui.vpd.home.VpdMotionGate
import de.mm20.launcher2.ui.vpd.home.VpdStage
import org.koin.compose.koinInject

/** Stage rows (D-183): Calendar 412-516, Alerts 524-602, Tasks 610-704; all 12 to 348 wide. */
private const val CARD_X = 12f
private const val CARD_W = 336f

/**
 * The three cards under the dial. Data is collected with the lifecycle (only while Home is
 * STARTED); the clock reading is refreshed on resume, there is no timer.
 */
@Composable
internal fun VpdCards(
    st: VpdStage,
    originX: Dp,
    motion: VpdMotion,
    gate: VpdMotionGate,
) {
    val context = LocalContext.current
    val vm = viewModel<VpdCardsVM>()
    val permissions: PermissionsManager = koinInject()

    val events by vm.events.collectAsStateWithLifecycle()
    val calendarPermission by vm.hasCalendarPermission.collectAsStateWithLifecycle()
    val alerts by vm.alerts.collectAsStateWithLifecycle()
    val notificationAccess by vm.hasNotificationAccess.collectAsStateWithLifecycle()

    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) now = System.currentTimeMillis()
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    fun place(top: Float, height: Float) = Modifier
        .offset(originX + st.dp(CARD_X), st.dp(top))
        .size(st.dp(CARD_W), st.dp(height))

    Box {
        VpdCalendarCard(
            st = st,
            events = events,
            hasPermission = calendarPermission != false,
            now = now,
            onOpenCalendar = { VpdCardActions.openCalendar(context) },
            onAskPermission = { VpdCardActions.requestCalendarPermission(context, permissions) },
            modifier = place(412f, 104f),
        )
        VpdAlertsCard(
            st = st,
            alerts = alerts,
            hasAccess = notificationAccess != false,
            motion = motion,
            gate = gate,
            now = now,
            onOpenList = { VpdCardActions.openNotificationList(context) },
            onAskAccess = { VpdCardActions.openNotificationAccess(context) },
            onOpen = { VpdCardActions.openNotification(it.notification.contentIntent) },
            onClear = vm::clear,
            modifier = place(524f, 78f),
        )
        VpdTasksCard(
            st = st,
            onConnect = { VpdCardActions.comingSoon(context) },
            modifier = place(610f, 94f),
        )
    }
}
