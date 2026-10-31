# Task brief: T1-tasks

One task = one stage = one branch `claude/T1-tasks`. Read only this, `CLAUDE.md`, `docs/design-system.md`, your row in `ops/STATUS.md` and `tasks/B3-cards/handoff.md` (the Tasks card shell and its row slots). Grep the mock-up `docs/design/refined/dial-mockup-cute.html` for the tasks block (tick boxes, dotted rows, add line); never read it whole.

Goal: the Tasks card holds real tasks, stored inside the launcher on the phone (D-190). Also make every build sign with the owner's fixed debug key, so updates install over the old app and tasks survive (D-191, AF-34).

Files in scope:
- Workflow `.github/workflows/build-debug.yml`: before the build, a step that writes the secret `DEBUG_KEYSTORE_BASE64` (base64 text) to `~/.android/debug.keystore` (`echo "$X" | base64 -d`). If the secret is empty, fail the job with a plain message ("Fixed debug key missing: add the DEBUG_KEYSTORE_BASE64 secret"), so no build is ever signed with a random key. The key uses Android's standard debug alias and passwords (alias `androiddebugkey`, passwords `android`), so no Gradle change. Never print the secret.
- Storage: a new small store in a new file, e.g. `de.mm20.launcher2.ui.vpd.tasks` (a JSON file in the app's files folder written with kotlinx.serialization, or a DataStore). Do NOT add a Room table or touch upstream databases. Each task: id, text, created time, done flag. Writes off the main thread; a write failure is logged with the tag `launcher` (never log task text).
- Card (`VpdTasksCard.kt`, from B3): remove the "Connect Google Tasks" row and its toast. Row slots: the newest 2 not-yet-removed tasks (tick box + text, dotted rows, 12 px Franklin, smaller tick boxes per D-115), then the last row "Add a task…" (D-187). Tap "Add a task…": an inline text field in that row with the keyboard; Done on the keyboard saves (empty text saves nothing); Back or tapping elsewhere cancels. More than 2 tasks: "+N more" next to the "Tasks" title (D-193; no action until B4, the Today/tasks sheet). No tasks: the two task rows stay empty with the dotted lines.
- Tick (D-192): tap the tick box or the task text, and the task is crossed out at once; tap again to undo. Crossed-out tasks are removed the next time Home resumes (ON_RESUME), not while you look at them.
- Data is read with the lifecycle (collect only while STARTED, AF-15). No timers.

Done when (visible on the phone):
- "Add a task…", type "milk", Done: "milk" shows as the top task.
- Tick it: it is crossed out; tap again: back to normal. Tick, press Home or leave and come back: it is gone.
- Add 3 tasks: the newest 2 show and "+1 more" next to the title.
- Close the launcher from recent apps and open it again: the tasks are still there.
- The next build installs over this one without uninstalling (only from the second build signed with the fixed key; say so in the testlist).

Out of scope: Google Tasks (parked, AF-33), the full task list sheet and search "Add as task" (B4), due dates, reminders, Notes (dropped, D-186). Do not touch the dial, housing, Calendar or Alerts cards.
Android calls needed: none new.
Motion or battery impact: none (no animation; the cross-out is instant).
Sellable parts [M]: none.
Open questions for the owner: none (D-190 to D-193). Anything unclear: pick the simplest option matching the mock-up and list it in the handoff.
Model: mid (Sonnet class).

Deliver: commit locally on `claude/T1-tasks` (co-author trailer); `tasks/T1-tasks/testlist.md` from `ops/templates/testlist.md` (first install of this build still needs one uninstall, because it is the first with the fixed key; a "should NOT happen" step; a restart step); `tasks/T1-tasks/handoff.md`; 0 to 3 lines in `ops/lessons-inbox.md`; update your STATUS row. Re-read every new file's imports. Do NOT push. Say READY TO PUSH with a plain-words summary and what you could not verify, then stop.
