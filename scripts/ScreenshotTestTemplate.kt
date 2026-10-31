// Launcher Studio Kit, screenshot test template (CC BY 4.0). Copy to
//   app/ui/src/test/java/<your/package/path>/ScreenshotTemplate.kt
// then replace every <PLACEHOLDER>. Not a pass/fail test: it writes PNGs you LOOK at.
// Needs the test dependencies from playbook/seeing-the-ui.md.
// Run: ./gradlew :app:ui:testDebugUnitTest --tests "*ScreenshotTemplate*" -i
package <your.package>

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.core.graphics.Insets
import androidx.core.view.DisplayCutoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE) // draws with the real Android graphics stack
@Config(sdk = [35]) // use an SDK level Robolectric supports; independent of your phone's Android version
class ScreenshotTemplate {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    // <PHONE>: screen in pixels at its density, nav bar and camera cut-out sizes in pixels.
    // Example numbers for a 1080 x 2400 px phone at 2.625 density (411 x 914 dp): nav 126, cut-out 84.
    private val navPx = 126
    private val cutPx = 84

    @Test @Config(qualifiers = "w411dp-h914dp-port-420dpi") // <PHONE> size in dp and dpi
    fun portrait() = shoot("home-portrait", Side.Bottom, Side.Top)

    @Test @Config(qualifiers = "w914dp-h411dp-land-420dpi")
    fun landscapeCameraLeft() = shoot("home-landscape-camera-left", Side.Right, Side.Left)

    @Test @Config(qualifiers = "w914dp-h411dp-land-420dpi")
    fun landscapeCameraRight() = shoot("home-landscape-camera-right", Side.Left, Side.Right)

    @Test @Config(qualifiers = "w360dp-h640dp-port-xhdpi") // smallest phone you want to support
    fun smallPortrait() = shoot("home-small-360x640-portrait", Side.Bottom, Side.Top)

    private enum class Side { Left, Top, Right, Bottom }

    /** nav = side where the navigation bar sits; camera = side where the cut-out sits. */
    private fun shoot(name: String, nav: Side, camera: Side) {
        val owner: ViewModelStoreOwner = <YourFakeViewModelOwner>() // fakes for every view model the screen reads
        rule.mainClock.autoAdvance = false // animations frozen; advance by hand below
        val activity = rule.activity
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        rule.setContent {
            CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
                // <YOUR SCREEN>: the composable you want to see, e.g. HomeScreen(Modifier.fillMaxSize())
            }
        }
        val root = activity.window.decorView
        val w = root.width
        val h = root.height
        val navInsets = when (nav) {
            Side.Left -> Insets.of(navPx, 0, 0, 0)
            Side.Right -> Insets.of(0, 0, navPx, 0)
            Side.Bottom -> Insets.of(0, 0, 0, navPx)
            Side.Top -> Insets.NONE
        }
        val hole = when (camera) {
            Side.Left -> Rect(0, h / 2 - 45, cutPx, h / 2 + 45)
            Side.Right -> Rect(w - cutPx, h / 2 - 45, w, h / 2 + 45)
            Side.Top -> Rect(w / 2 - 45, 0, w / 2 + 45, cutPx)
            Side.Bottom -> Rect(w / 2 - 45, h - cutPx, w / 2 + 45, h)
        }
        val safe = when (camera) {
            Side.Left -> Rect(cutPx, 0, 0, 0)
            Side.Right -> Rect(0, 0, cutPx, 0)
            Side.Top -> Rect(0, cutPx, 0, 0)
            Side.Bottom -> Rect(0, 0, 0, cutPx)
        }
        val status = if (camera == Side.Top) Insets.of(0, cutPx, 0, 0) else Insets.NONE
        val insets = WindowInsetsCompat.Builder()
            .setInsets(WindowInsetsCompat.Type.navigationBars(), navInsets)
            .setInsetsIgnoringVisibility(WindowInsetsCompat.Type.navigationBars(), navInsets)
            .setInsets(WindowInsetsCompat.Type.statusBars(), status)
            .setInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars(), status)
            .setInsets(WindowInsetsCompat.Type.displayCutout(), Insets.of(safe))
            .setDisplayCutout(DisplayCutoutCompat(safe, listOf(hole)))
            .build()
        rule.runOnUiThread { ViewCompat.dispatchApplyWindowInsets(root, insets) }
        rule.mainClock.advanceTimeBy(1500)

        val bmp = rule.onRoot().captureToImage().asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
        // mark where the phone's own parts sit: navigation bar (blue) and camera hole (red)
        val c = Canvas(bmp)
        val blue = Paint().apply { color = 0x553060FF }
        when (nav) {
            Side.Left -> c.drawRect(0f, 0f, navPx.toFloat(), h.toFloat(), blue)
            Side.Right -> c.drawRect((w - navPx).toFloat(), 0f, w.toFloat(), h.toFloat(), blue)
            Side.Bottom -> c.drawRect(0f, (h - navPx).toFloat(), w.toFloat(), h.toFloat(), blue)
            Side.Top -> Unit
        }
        val red = Paint().apply { color = 0xCCFF3030.toInt() }
        c.drawCircle(hole.exactCenterX(), hole.exactCenterY(), 16f, red)

        // Gradle runs tests with the module folder as working directory, so go up to the project root.
        val dir = File("../../docs/design/shots").also { it.mkdirs() }
        File(dir, "$name.png").outputStream().use { bmp.compress(Bitmap.CompressFormat.PNG, 100, it) }
        println("screenshot written: ${File(dir, "$name.png").absolutePath}")
    }
}
