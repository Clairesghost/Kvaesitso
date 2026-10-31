package de.mm20.launcher2.ui.vpd.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.preferences.ui.VpdLook
import de.mm20.launcher2.preferences.ui.VpdMotion
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.ui.launcher.scaffold.LauncherScaffoldState
import de.mm20.launcher2.ui.vpd.LocalVpdColors
import de.mm20.launcher2.ui.vpd.colors
import kotlinx.coroutines.launch

private const val PREFS = "vpd_home"
private const val KEY_BT_ASKED = "bluetooth_asked"

/**
 * Vintage Pocket Dial home: cherry body, chrome housing, dial, clock. Stage is 360 x 780 units
 * scaled to the screen width (and shrunk if the height needs it), see docs/design-system.md "Layout".
 */
@Composable
internal fun VpdHomeScreen(
    state: LauncherScaffoldState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val vm = viewModel<VpdHomeVM>()
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val look by vm.look.collectAsStateWithLifecycle()
    val colors = (look ?: VpdLook.Auto).colors(isSystemInDarkTheme())
    val motion by vm.motion.collectAsStateWithLifecycle()
    val returnsHome by vm.ringReturnsHome.collectAsStateWithLifecycle()
    val apps by vm.dialApps.collectAsStateWithLifecycle()
    val gate = remember { VpdMotionGate(context) }

    // Bluetooth: asked once with a plain explanation (D-182); refused = grey, no nagging.
    var btGranted by remember { mutableStateOf(VpdStatus.hasBluetoothPermission(context)) }
    var showBtExplain by remember {
        mutableStateOf(
            Build.VERSION.SDK_INT >= 31 && !btGranted &&
                    !context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_BT_ASKED, false)
        )
    }
    val btLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        btGranted = it
    }

    val connection by remember { VpdStatus.connection(context) }
        .collectAsStateWithLifecycle(VpdConnection.None)
    val bluetooth by remember(btGranted) { VpdStatus.bluetoothConnected(context) }
        .collectAsStateWithLifecycle(false)
    val battery by remember { VpdStatus.battery(context) }.collectAsStateWithLifecycle(null)

    CompositionLocalProvider(LocalVpdColors provides colors) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors.ground)),
        ) {
            val widthPx = with(density) { maxWidth.toPx() }
            val heightPx = with(density) { maxHeight.toPx() }
            val navBottomPx = WindowInsets.navigationBars.getBottom(density)
            val k = minOf(widthPx / STAGE_WIDTH, (heightPx - navBottomPx) / STAGE_HEIGHT)
            val st = VpdStage(k, density)
            val originX = with(density) { ((widthPx - STAGE_WIDTH * k) / 2f).toDp() }

            // housing
            VpdHousing(
                st = st,
                coins = VpdCoinState(connection, bluetooth, battery),
                onSearch = {
                    scope.launch {
                        state.onSearchBarTap()
                        state.isSearchBarFocused = true
                    }
                },
                onConnection = { VpdActions.openInternetSettings(context) },
                onBluetooth = {
                    if (!btGranted && Build.VERSION.SDK_INT >= 31 && !wasBluetoothAsked(context)) {
                        showBtExplain = true
                    } else {
                        VpdActions.openBluetoothSettings(context)
                    }
                },
                onBattery = { VpdActions.openBatterySettings(context) },
                modifier = Modifier.offset(originX + st.dp(12f), st.dp(8f)),
            )

            // camera ring around the real hole (AF-3: measured, not hard-coded)
            val view = LocalView.current
            val cutoutTop = WindowInsets.displayCutout.getTop(density)
            val hole = remember(widthPx, cutoutTop) { measureHole(view, widthPx, st) }
            VpdCameraRing(
                centrePx = hole.first,
                diameterPx = hole.second,
                charging = battery?.charging == true,
                low = battery?.let { it.percent <= 15 && !it.charging } == true,
                onClick = { VpdActions.openCamera(context) },
            )

            // dial
            VpdDial(
                st = st,
                apps = apps,
                motion = motion ?: VpdMotion.Full,
                ringReturnsHome = returnsHome,
                gate = gate,
                iconFor = vm::icon,
                onLaunch = { app -> launchApp(context, vm, app) },
                onClockHold = { VpdActions.openClock(context) },
                modifier = Modifier.offset(originX, st.dp(DIAL_TOP)),
            )

            // chevron 706-750: opens the existing search page with the app list
            Box(
                modifier = Modifier
                    .offset(originX + st.dp(120f), st.dp(706f))
                    .size(st.dp(120f), st.dp(44f))
                    .clickable { scope.launch { state.onSearchBarTap() } }
                    .semantics { contentDescription = "All apps" },
            ) {
                Canvas(Modifier.size(st.dp(120f), st.dp(44f))) {
                    val p = Path().apply {
                        moveTo(size.width / 2f - 12f * k, size.height / 2f + 5f * k)
                        lineTo(size.width / 2f, size.height / 2f - 5f * k)
                        lineTo(size.width / 2f + 12f * k, size.height / 2f + 5f * k)
                    }
                    drawPath(
                        p, androidx.compose.ui.graphics.Color(0xFFFDFDFB).copy(alpha = 0.9f),
                        style = Stroke(3f * k, cap = StrokeCap.Round, join = StrokeJoin.Round),
                    )
                }
            }
        }

        if (showBtExplain) {
            AlertDialog(
                onDismissRequest = {
                    markBluetoothAsked(context)
                    showBtExplain = false
                },
                title = { Text("Show Bluetooth connections") },
                text = {
                    Text(
                        "To turn the Bluetooth coin dark when headphones are connected, Android " +
                                "needs your OK to see nearby devices. Nothing is sent anywhere."
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        markBluetoothAsked(context)
                        showBtExplain = false
                        btLauncher.launch(android.Manifest.permission.BLUETOOTH_CONNECT)
                    }) { Text("Allow") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        markBluetoothAsked(context)
                        showBtExplain = false
                    }) { Text("Not now") }
                },
            )
        }
    }
}

/** Camera hole: centre and ring diameter in px, from the display cutout, or the housing middle if none. */
private fun measureHole(view: android.view.View, widthPx: Float, st: VpdStage): Pair<Offset, Float> {
    val rects = ViewCompat.getRootWindowInsets(view)?.displayCutout?.boundingRects.orEmpty()
    val r = rects.filter { it.top <= 2 && it.width() > 0 }.minByOrNull { it.width() }
    if (r == null) return Offset(widthPx / 2f, st.px(27f)) to st.px(40f)
    val hole = r.width().toFloat()
    return Offset(r.exactCenterX(), r.bottom - hole / 2f) to hole * 1.62f
}

private fun wasBluetoothAsked(context: Context) =
    context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_BT_ASKED, false)

private fun markBluetoothAsked(context: Context) {
    context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(KEY_BT_ASKED, true).apply()
}

private fun launchApp(context: Context, vm: VpdHomeVM, app: SavableSearchable) {
    val options = ActivityOptionsCompat.makeBasic().toBundle()
    if (app.launch(context, options)) {
        vm.reportLaunch(app)
    } else {
        Log.e("launcher", "Could not launch dial app")
    }
}
