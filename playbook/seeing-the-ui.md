# Seeing the screens without a phone

The Claude workspace often cannot run the app. Cloud PCs usually cannot run the Android emulator (no nested virtualisation). These routes give pictures anyway.

## 1. Preview pictures (before any code)
Write each design as one web page (HTML + CSS, your fonts via local files) and render it to a PNG with a headless browser:
- Windows (Edge): `msedge --headless=new --screenshot="docs/design/<name>.png" --window-size=390,844 "file:///<full path>/docs/design/<name>.html"`
- Mac (Chrome): `"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome" --headless=new --screenshot=docs/design/<name>.png --window-size=390,844 file://$PWD/docs/design/<name>.html`
Then LOOK at the PNG (the Read tool shows images). Notes from experience: the built-in browser pane may run no JavaScript on local files and may refuse pages over about 500 KB, so ship a frozen copy (scripts removed) next to a live page, plus the PNG. Show 2 or 3 variants side by side early instead of one at a time.

## 2. Can this computer run the emulator? (probe once, record the answer in `ops/decisions.md`)
```
"%ANDROID_HOME%\emulator\emulator" -accel-check
```
Mac/Linux: `$ANDROID_HOME/emulator/emulator -accel-check`. If it says virtualization is not supported, there is no emulator here: do not try software mode (it crashes or is far too slow). Use route 3. If the answer is "supported", you can install a system image in Android Studio's SDK manager and run the app on it; screenshots with `adb exec-out screencap -p > shot.png`.

## 3. Robolectric screenshot tests (draw the real screen on the JVM)
A unit test renders your Compose screen with fake data and fixed phone-like insets and writes PNGs. Needs Java 17 and the Android SDK once (Android Studio brings both); the first run downloads about 300 MB, later runs about 1 minute.
Add to the app module's `build.gradle.kts` (test-only; not in the APK):
```
android {
    testOptions.unitTests.isIncludeAndroidResources = true
}
dependencies {
    testImplementation(libs.junit)
    testImplementation("org.robolectric:robolectric:4.17")
    testImplementation("io.mockk:mockk:1.14.11")
    testImplementation("androidx.compose.ui:ui-test-junit4:<same version as the compose version used by the project>")
    testImplementation("androidx.compose.ui:ui-test-manifest:<same version>")
}
```
Copy `scripts/ScreenshotTestTemplate.kt` into `app/ui/src/test/java/<package>/` and replace the placeholders. Run (Git Bash or terminal, from the project root, with JAVA_HOME at Java 17, for example Android Studio's `jbr` folder):
```
./gradlew :app:ui:testDebugUnitTest --tests "*ScreenshotTemplate*" -i
```
Pictures land in `docs/design/shots/`. Open them and look. Blue band = navigation bar, red mark = camera hole: nothing of yours may sit under either.

What these pictures catch: overlaps, clipping, wrong position, wrong colours. What they do NOT show: anything that reads the real phone (battery, Wi-Fi, notifications, installed apps, the actual camera cut-out), maker-specific behaviour, real speed and battery, scaled fonts. Always say so in the handoff.

## 4. On-screen numbers for phone-only facts
When only the phone can answer (for example where the camera hole really is), ship a test build that prints the numbers on screen so the person can send a screenshot. Do this instead of guessing an offset.

## Adding a picture for a new screen
Copy one `@Test` in the template, set the screen size via `@Config(qualifiers = "w411dp-h914dp-port-420dpi")`, render the composable you changed inside `rule.setContent { ... }`. If it needs a view model, replace it with a fake (stub only the fields the screen reads; `mockk(relaxed = true)` for the rest).
