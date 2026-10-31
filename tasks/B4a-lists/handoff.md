# Handoff: B4a-lists stage 1

What changed (plain words):
- Preferences (core/preferences, own commit): `VpdColour`, `VpdListSettings` (app category moves: package -> category; dial: 12 keys, "" = empty disc, null = nothing saved; hidden alert packages; hidden calendar ids as "local:<id>"), `VpdLookSettings.colour`, 5 new fields in `LauncherSettingsData`, Koin factory.
- Colours: `vpdColors(night, colour)` in VpdColors.kt, values from the mock-up CSS. Body, dial plate, clock ring, accent, disc rims and (day) clock marks change; chrome, discs, cards stay.
- `ui.vpd.sheet`: `VpdSheet` shell (left/right 6, top 54, bottom 6, script title, X or Done, swallows all touches, Back closes, slides up only if the motion gate and Motion allow), `VpdPage` stack, `vpdTapOrHold` (press, tap, 0.5 s hold).
- `ui.vpd.apps`: `VpdAppsVM` (AppRepository, Android category, saved moves), `VpdAppListSheet` (Find field, Launcher settings row, chips, category sections, 4 per row, letter strip with bubble and 1.6 s glow, hold menu Move to / Put on the dial / App info, tap = press effect + launch 120 ms later + launch counter).
- `ui.vpd.settings`: `VpdSettingsVM`, `VpdSettingsSheet` (Look, Motion, Dial, Alerts, Calendars, Go back, More; Click feel and Click sound not shown).
- `ui.vpd.editor`: `VpdDialEditorSheet` (mini dial using the Home dial's own `drawDialPlate`, `Disc`, `VpdClock`; drag to swap, tap to replace with Find, empty disc fills, "Leave this disc empty").
- Home (own B2 code): `VpdHomeVM.dialApps` uses the saved list when there is one (else favourites, D-180); `VpdDial` takes nullable slots and `onDiscHold` (half-second hold on a disc, a drag still spins); `VpdHomeScreen` holds the page stack, opens the sheets (chevron, swipe up, hold), follows the colour setting, closes all sheets on the Home button (ON_PAUSE while `isNewIntent`).
- Cards: `VpdCardsVM` leaves out hidden alert apps and hidden calendars (`excludeCalendars`).
- Upstream files edited: only `LauncherSettingsData.kt` and the preferences `Module.kt` (own commit). Swipe up is done inside Home; `SharedLauncherActivity` is untouched.

How it was checked: read-through of every new file and its imports. NOT compiled, NOT run.

NOT verified (only the phone can show):
- That it compiles.
- Swipe up: my detector sits under everything on Home and takes an up-swipe that starts below stage y 404 and was not used by a child. The scaffold's own swipe-up (owner's gesture setting, default Widgets) is still configured; if both fire, the old page opens too. Fix then: set that gesture to No action, or a one-line config edit.
- That Back closes the sheet before the scaffold's back handler (BackHandler is composed only while a sheet is open, so it registers later), and that the Home button closes all via `isNewIntent`.
- Letter strip feel, glow, hold times, menu and dialog look (plain Material), the Find field with the keyboard up (the sheet may be covered), scrolling in Settings.
- How many apps land in Other (Samsung apps often declare no category).
- `Settings.ACTION_HOME_SETTINGS` on One UI 5 (which screen opens); `SettingsActivity` launched from here.
- Work-profile apps in the list.

Proposed AF lines (api-scout 2026-11-01, docs and code only, UNVERIFIED on phone):
- AF-35: `ApplicationInfo.category` is -1 (undefined) unless the app declares `android:appCategory` or the installer sets it. Read with `packageManager.getApplicationInfo(pkg, 0).category` (QUERY_ALL_PACKAGES is declared).
- AF-36: `Settings.ACTION_HOME_SETTINGS` (API 21) should open the Home app chooser; One UI 5 may open Default apps instead. A miss is logged.
- AF-37: `CalendarRepository.getCalendars("local")` lists device calendars with id "local:<id>"; `findMany(excludeCalendars = listOf("local:<id>"))` filters them.
- AF-38: `AppRepository.findMany()` has no category; keys are "app://pkg:class" (+ ":serial" for other profiles); a saved key is resolved by looking it up in that list.

Open questions for the owner (decided simply, to confirm):
- Menu names under "Go back": Settings > Apps > Choose default apps > Home app > One UI Home (check on your phone).
- A sheet slides in; closing is instant.
- Hold menu and "Move to…" use plain Material pop-ups, not styled yet.
- Swipe up is accepted only below the dial (stage y 404), not over the housing or dial.
- Done and X go back one sheet (Settings -> Apps); Back does the same.
- The Alerts list shows only apps that have a notification right now (plus ones already hidden).
- The Calendars list shows device calendars only (not Tasks.org or plugins).
- Chips show only categories that have apps.
- "Put on the dial" empties the app's old disc if it was already on the dial.
- Apps hidden with Kvaesitso's own hide feature are still listed.

Sellable parts added ([M] rows): none.
Lessons: see inbox.
Next stage needs: B4b can reuse `VpdSheet`, `VpdPage` (add pages there) and `vpdTapOrHold`.
Branch: claude/B4a-lists (see `git log`).

# Handoff: B4a-lists stage 2 (D-196, branch claude/B4a-s2)

What changed (plain words):
- Manifest (own commit): `PACKAGE_USAGE_STATS` with `tools:ignore="ProtectedPermissions"`.
- New `ui.vpd.usage.VpdUsage`: `hasAccess` (AppOps `unsafeCheckOpNoThrow` on `OPSTR_GET_USAGE_STATS`), `rankedPackages` (`queryAndAggregateUsageStats` over 14 days, most foreground time first, this launcher left out, nothing logged), `openAccessPage` (Usage access page with `package:` data when the phone resolves it, else the general page).
- `VpdHomeVM` (now an `AndroidViewModel`): `refreshUsage()` runs on `Dispatchers.Default`, called once per Home resume from `VpdHomeScreen` (ON_RESUME). No timers. `dialApps` now: owner-set discs stay; every empty disc ("" or an uninstalled app) takes the next app from usage ranking, then from the old favourites (D-180) as top-up and as the whole fallback without access; "-" (`VpdHomeVM.BLANK`) stays blank. Nothing saved (null) = all 12 automatic. New `dialSaved` (raw keys) and `usageAccess` flows.
- Editor: works on raw keys. Tapping/replacing a disc, a swap (both discs) or "Leave this disc empty" ("-") makes those discs the owner's own; untouched automatic discs stay automatic. "Put on the dial" frees the app's old disc (shown again by the auto fill). One quiet line "Fill empty discs with my most-used apps: tap to allow" when access is off.
- Settings > Dial: row "Fill the dial with my most-used apps", status "On" / "Tap to allow", explanation line under it. Nothing asks on start.
- `VpdListSettings` comment only (marker meaning).

Owner's old save (1 app + 11 ""): the 11 empties now fill automatically, no migration needed.

How it was checked: read-through of every changed file and its imports. NOT compiled, NOT run.

NOT verified: compile; that the `package:` link opens the launcher's own row on One UI 5 (api-scout: UNVERIFIED, docs only); that `queryAndAggregateUsageStats` returns data on this phone; Samsung Device care possibly resetting Usage access; the menu path Settings > Apps > three dots > Special access > Usage data access (check on your phone); that the refresh runs after coming back from the Android page (it should, Home resumes).

Decided simply (to confirm): ranking = 14 days of foreground time; only Home-resume refresh (the Home dial may show the old fill until the first read finishes, a moment); work-profile duplicates use the first app of the package; the editor's auto-filled discs look exactly like owner-set ones.

Proposed AF lines (api-scout 2026-10-31, docs only; UNVERIFIED on phone):
- AF-39: `PACKAGE_USAGE_STATS` is granted by the owner in Settings (app-op), no runtime dialog. Check `AppOpsManager.unsafeCheckOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), packageName) == MODE_ALLOWED` (API 29). Read `UsageStatsManager.queryAndAggregateUsageStats(begin, end)` (one total per package; no bucket overlap). UNVERIFIED on phone.
- AF-40: `Settings.ACTION_USAGE_ACCESS_SETTINGS` with `package:<pkg>` data may open the app's own toggle or the general list on One UI 5. UNVERIFIED on phone (the owner reports which).
