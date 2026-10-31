# Handoff: B3-cards stage 1

What changed (files, commits, new settings; plain words):
- New package `de.mm20.launcher2.ui.vpd.cards` (all new files, no new settings, nothing stored):
  VpdCardPanel (tan/near-black placard: gradient, 3 px frame, 6 px corners, chrome bezel, shadow, dotted divider), VpdCardsVM (events from `CalendarRepository.findMany`, notifications from `NotificationRepository`, permission flows, a "dismissed" key set), VpdCalendarCard, VpdAlertsCard (rows, sideways swipe), VpdTasksCard (shell), VpdCards (placement: Calendar 412-516, Alerts 524-602, Tasks 610-704, x 12 to 348), VpdCardActions (open Calendar, notification access page, notification list, notification action, toast).
- Upstream edit: `VpdHomeScreen.kt`, one import and one call `VpdCards(...)` (own commit).
- Calendar: next event today (or "Nothing on today" + "Next: <title>, <day> <time>"), "Then…" line with up to two more, "Now, until <time>" for a running event, "all day". Tasks (isTask) are left out. No permission: row "Show your calendar", tap asks Android. Tap the card: Calendar app (ACTION_VIEW on `content://com.android.calendar/time/<today noon>`, same call as Kvaesitso's widget).
- Alerts: newest 2 non-summary notifications (title + text read with getCharSequence), app icon, "Open" pill and ✕, "+N more ›" in the header (opens the shade via the repo's `NotificationShadeController`), "all clear" when empty, "Show your alerts" row when the listener is not bound. Swipe past 90 stage units flies off (160 ms) and calls `cancelNotification`; shorter springs back; ongoing/uncleareable rows only wiggle (max 18 units), no ✕. Motion Off or `VpdMotionGate` says no: no animation, the row just goes.
- Tasks: title "Tasks" and one row "Connect Google Tasks" (toast "Coming in the next update"). Rows 2 and 3 (y 646-670 and 670-694 within the card) are empty slots for G1: two task rows or one task row and the "Add a task…" row.
- Data is collected with `collectAsStateWithLifecycle` and `WhileSubscribed`; the "now" used for the agenda is refreshed on resume; no timers.

How it was checked (compile, screenshot test, read-through; say which): read-through of every new file and its imports only. NOT compiled, NOT run (no Java/Android SDK here).

NOT verified (everything that only the phone can show):
- That it compiles (cloud build is the first compile).
- Looks: fit of the text inside each plaque, date tab proportions, Night shade, that nothing overlaps the dial (ends ~y 410) or the chevron (706).
- That Samsung Calendar opens from the ACTION_VIEW call (AF below).
- That the notification listener delivers rows and cancelNotification clears them on Android 13; that `hasPermission(Notifications)` (it means "listener bound") does not briefly show "Show your alerts" after Home opens.
- That `expandNotificationsPanel` reflection works on Android 13 (AF-24): if not, "+N more ›" does nothing and the error is only in the log. No second route was written (rule: no try-A-then-B).
- That the page scaffold's own drags do not fight the row swipe, and vertical swipes over the cards still move the page.
- Swipe threshold feel (90 units = 90 dp at 360-wide stage scale of 1).

Android facts learned (proposed AF lines for `ops/api-facts.md`, from api-scout 2026-10-31, all UNVERIFIED on phone):
- AF-30: Open Calendar at today via `ACTION_VIEW` on `CalendarContract.CONTENT_URI/time/<ms>` with tryStart; no `<queries>` needed (startActivity needs no package visibility). The `makeMainSelectorActivity(CATEGORY_APP_CALENDAR)` route is not used.
- AF-31: `PermissionsManager.hasPermission(Notifications)` is "the listener is bound" (set by `NotificationService.onListenerConnected`), not a settings check; the access page is `Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS`.
- AF-32: `Notification.title/text` in `data/notifications` use `getString` (null for styled text); the cards read `getCharSequence` themselves. `Notification.flags` is lost after a ranking update (copy constructor), so ongoing = `!isClearable || FLAG_ONGOING_EVENT`.
- Missing calendar permission: `CalendarRepository.findMany` does not throw, it returns an empty or partial list.

Sellable parts added ([M] rows): none.

Open questions for the owner (decided simply, to confirm):
- Date tab is 72 units wide with the date number in script; no countdown and no "Leave by" line (not in the brief).
- Alert row shows "title  text" in one line (title not bold-separated); the sender weight is Medium (500) for the whole line. Say if the title should be a separate bolder word.
- Alerts rows also show "2 min / 1 h" age; "Open" and a tap on the row do the same thing.
- "Show your alerts" row text: "Tap to allow notification access. It stays on your phone."
- Hidden/filtered calendars are not honoured (all calendars, per brief).

Lessons (0 to 3 lines, also in `ops/lessons-inbox.md`): see inbox.
Next stage needs: G1 puts task rows in `VpdTasksCard` (slots after the 22-unit header, 24 units each, three rows in the 94-unit card). B4 (Today sheet) can reuse `VpdCardsVM`.
Branch and last commit: claude/B3-cards (see `git log`).
