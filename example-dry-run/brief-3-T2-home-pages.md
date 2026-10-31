# Task brief: T2-home-pages (EXAMPLE)

Goal: Two Home pages that swipe sideways (D-4). Page 1: header with date and small clock (D-5), weather icon plus number, next calendar events, search bar at the bottom of the page above the dock (D-8 Q8.5 is for the app list; here the bar opens the typing layer, D-9). Page 2: notifications list, most-used apps, torch and ringer chips.
Files in scope:
- NEW `app/ui/src/main/java/<package>/forest/`: `ForestHome.kt` (pager with two pages), `ForestHeader.kt`, `ForestEvents.kt`, `ForestNotifications.kt`, `ForestChips.kt`, `ForestMostUsed.kt`, `UsageCounter.kt` (own counter, `AF-14`).
- ONE upstream edit (own commit): the place where the launcher chooses its Home component, to use `ForestHome`.
- NEW settings entry "Animations: Full / Minimal / Off" in a new settings file.
Done when:
- Swiping sideways moves between two pages with a short fade; a dot row shows which page.
- Page 1 shows today's date and a small clock; weather shows icon plus number after the location permission is granted (`AF-2`); with no permission it shows a plain "Weather off" line, never a crash.
- Page 2 lists waiting notifications after notification access is granted (`AF-1`); tapping one opens the app; a swipe removes it; titles are shown but never logged.
- The torch chip toggles the torch; the ringer chip switches Normal and Vibrate (`AF-8`).
- Pressing Home while the typing layer is open closes it (`AF-7`).
- Text stays inside its frame at 360 dp wide and at the largest font size.
Out of scope: wallpaper, landscape (portrait only, D-10), icon shapes.
Android calls needed: AF-1, AF-2, AF-7, AF-8, AF-14; calendar read permission: ask `api-scout` for the Android 14 permission flow.
Motion or battery impact: small (page fade, press effects); no polling when Home is stopped (`AF-15`).
Sellable parts: none.
Open questions for the owner (in the preview step):
1. Page dots: A. Honey dots at the bottom (recommended). B. A thin line. C. None.
2. Empty states for page 2: A. A short friendly sentence. B. Nothing shown.
Model for this thread: mid (Sonnet class). Needs `T1-theme-and-dock` merged first (same files in `forest/`); do not run in parallel with it.
