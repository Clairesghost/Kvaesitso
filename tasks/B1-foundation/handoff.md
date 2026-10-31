# Handoff: B1-foundation stage 1

What changed (files, commits, new settings; plain words):
- Name: `app_name` is now "Vintage Pocket Dial" in `core/base` main, debug and nightly strings. `applicationId` unchanged (debug still ends in `.debug`).
- Icon: new adaptive vector icon (cherry square, round dial with ten discs and a clock, icon option A) in `app/app/src/main/res` (`mipmap-anydpi-v26/vpd_launcher.xml`, `drawable/vpd_launcher_{background,foreground,monochrome}.xml`). The manifest `android:icon` points at `@mipmap/vpd_launcher`. The old `ic_launcher` files in `core/base` are untouched (still used by shortcuts.xml).
- Fonts: `app/ui/src/main/res/font/oleo_script_swash_caps_bold.ttf` and `libre_franklin.ttf` (Libre Franklin from Google Fonts is ONE variable font file covering 100-900, so weights 400/500/600/700 come from variation settings, not four files). OFL texts in `docs/licenses/fonts/` (both verified as SIL OFL 1.1, from github.com/google/fonts).
- Tokens: new package `de.mm20.launcher2.ui.vpd` in `app/ui` (`VpdColors.kt` Day/Night roles, `VpdType.kt` fonts and text styles, `VpdLookResolver.kt` Auto -> Day/Night by phone dark mode). Nothing uses them yet; existing themes untouched.
- Look setting: `VpdLook {Auto, Day, Night}` and `VpdLookSettings` (new file in `core/preferences/.../ui`), one new field `vpdLook` in `LauncherSettingsData` (default Auto, so old saved settings still load), one line in `Module.kt`. No screen.
- Upstream edits (separate commits): manifest icon line, three strings files, `LauncherSettingsData.kt` (one field), `Module.kt` (import + one line).

How it was checked (compile, screenshot test, read-through; say which):
- Read-through only (no Java/Android SDK here). Every new file's imports re-read. XML hand-checked.

NOT verified (everything that only the phone can show; be honest):
- Nothing compiled. Possible build-time risks: `FontVariation.weight(Int)` / `Font(resId, weight, variationSettings=...)` signatures with Compose 1.13 beta; the `R` import `de.mm20.launcher2.ui.R` for fonts.
- Icon appearance (proportions scaled by 0.66 into the adaptive safe zone; clock pin colour in Day is my guess, `#8d141d`, the design only specifies amber for Night).
- The monochrome (themed) icon is a simple outline version, not in the design.
- The app does not appear in the in-app open-source licence screen: that list is generated from dependencies, so the fonts are covered only by `docs/licenses/fonts/` for now.
- Auto follows the phone's dark mode as the brief says; the design-system text says "time of day". Owner may want to reconcile later.

Android facts learned (proposed AF lines for `ops/api-facts.md`):
- None verified on the phone. Assumption: variable fonts in `res/font` work from API 26 via Compose `variationSettings` (minSdk is 26).

Sellable parts added ([M] rows): none

Lessons (0 to 3 lines, also in `ops/lessons-inbox.md`):
- see inbox

Next stage needs:
- Provide `LocalVpdColors` from `VpdLookSettings.look` + system dark mode where the first redrawn screen is built; a settings screen row for Look.

Branch and last commit: claude/B1-foundation (local only, not pushed); see `git log -7`.
