# Task brief: B1-foundation

One task = one stage = one branch `claude/B1-foundation`. Read only this, `CLAUDE.md`, `docs/design-system.md` and your row in `ops/STATUS.md`. Search the code with Grep and open only matching files.

Goal: make the launcher "Vintage Pocket Dial" in name, icon, fonts and colours, with a Look setting (Auto, Day, Night). Implements D-153 (name), D-157 (icon A), D-111/D-112 (fonts), D-144/D-146/D-163 (night), D-158 (design system tokens). No screens are redrawn in this stage.

Files in scope (new files preferred; upstream edits small, in separate commits):
- App label: change the launcher's visible name only (`app_name` string / the manifest label) to "Vintage Pocket Dial". Keep `applicationId` as it is.
- Launcher icon: new adaptive icon resources (cherry square, round dial with clock, see `docs/design/refined/icon-options.html` option A, drawn as vector). New files under `app/app/src/main/res`; point the manifest icon attributes at them.
- Fonts: bundle Oleo Script Swash Caps (700) and Libre Franklin (400, 500, 600, 700) as app resources; put the SIL OFL licence text of each in `docs/licenses/fonts/` and add them to the app's licence listing if it has one. Download from Google Fonts; verify the licence is OFL; if you cannot download them, stop and report.
- Tokens: a new package `de.mm20.launcher2.ui.vpd` (new files) holding the Day and Night colour roles and the two type styles from `docs/design-system.md` (Palette roles table, Fonts table), as Compose objects. Do not change existing themes.
- Look setting: a stored choice Auto / Day / Night (Auto follows the phone's dark mode for now). Put it where the launcher already stores its preferences (`core/preferences`) with a minimal new key; no screen yet.

Done when (visible on the phone):
- The app list shows "Vintage Pocket Dial" with the new icon; it installs next to the original launcher (debug build id ends in .debug).
- Nothing else about the launcher's behaviour changed (it still starts and works like Kvaesitso).

Out of scope: the home screen, dial, cards, menus, search, weather, landscape, haptics.
Android calls needed: none new. Ask `api-scout` if unsure.
Motion or battery impact: none.
Sellable parts [M]: none.
Open questions for the owner: none (all decided).
Model: mid (Sonnet class). The brief is mechanical; no strongest model needed.

Deliver: commit locally on `claude/B1-foundation` (commit messages end with the co-author trailer), `tasks/B1-foundation/testlist.md` from `ops/templates/testlist.md`, `tasks/B1-foundation/handoff.md` from `ops/templates/handoff.md`, 0 to 3 lines in `ops/lessons-inbox.md`, update your STATUS row. Re-read every new file's imports. Do NOT push. Say READY TO PUSH with a plain-words summary and what you could not verify, then stop.
