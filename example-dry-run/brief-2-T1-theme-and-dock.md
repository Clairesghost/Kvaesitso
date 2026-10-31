# Task brief: T1-theme-and-dock (EXAMPLE)

Goal: Apply the forest look (D-1, D-2, D-3) as a launcher colour scheme and fonts, and build the bottom dock with four rounded-square buttons (D-7) that open phone, messages, browser and camera. Keep the system status bar visible (D-5).
Files in scope:
- NEW package `app/ui/src/main/java/<package>/forest/` : `ForestTheme.kt` (palette and font objects from `docs/design-system.md`), `ForestDock.kt` (dock row), `DockApps.kt` (picks apps by category with a letter-in-square fallback).
- NEW font files in `app/ui/src/main/res/font/` (Lora, Nunito) and their licence texts in `docs/licenses/fonts/`.
- ONE small upstream edit (own commit): where the launcher reads its colour scheme, to use `ForestTheme`.
Done when:
- Home shows the dark green-brown ground, honey accent and Lora headings; the dock sits at the bottom above the navigation bar and opens the four apps.
- If an app is missing, its button shows a letter in the rounded square.
- Dot badges appear only after notification access is granted (`AF-1`); without it nothing breaks.
- Press effect on a dock button: short fade, 120 ms before the app opens, nothing when the Animations setting is Off.
Out of scope: pages, header content, search layer, wallpaper.
Android calls needed: `AF-1` (notifications), `AF-9` (press timing); ask `api-scout` for the default-app lookup by role (browser, messages, phone, camera) on Android 14.
Motion or battery impact: small (press effect only; behind the motion gate).
Sellable parts: none (Robin shares free, D-15).
Open questions for the owner (ask in the preview step, not during the build):
1. Dock frame: A. Thin honey outline. B. Soft filled tile (recommended). C. Leaf-corner outline (more art work).
2. Badge colour: A. Clay red (recommended). B. Honey.
Model for this thread: mid (Sonnet class). Screenshots: add portrait and small-phone pictures with the template test (`playbook/seeing-the-ui.md`).
