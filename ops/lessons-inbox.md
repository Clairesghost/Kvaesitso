# Lessons inbox

Copy to `ops/lessons-inbox.md`. Threads add 0 to 3 lines each: only surprises. Format: `- <task id>: <what surprised us, and what to do next time>`. The guide moves useful ones into `CLAUDE.md`, `ops/api-facts.md` or `playbook/lessons-learned.md` and clears the inbox about once a week.


- B1-foundation: CLAUDE.md is not committed on main, so new worktrees do not contain it; the thread had to read it from the main checkout. Commit it, or put the path in the brief.
- B1-foundation: Google Fonts ships Libre Franklin only as one variable file, not static weights; plan for variationSettings, not four files.
- B2-home: no Python or Java in the workspace, so a code stage cannot be compiled before pushing; keep new code in new files and re-read imports, and expect the first cloud build to find a few errors.
- B2-home: callbackFlow must end in awaitClose even on early-exit paths, or it crashes when collected.
- B2-home s2: a branch can only be checked out in one worktree; a later stage thread has to branch off (…-s2) and the guide merges it back.
- B3-cards: Kvaesitso's `Notification.flags` is dropped after a ranking update and `title/text` use getString (null for styled text); read extras yourself and treat `!isClearable` as ongoing.
- B3-cards: `PermissionsManager.hasPermission(Notifications)` only means "listener bound", so a freshly granted access can look missing for a moment.
- T1-tasks: Keyboard Back is not delivered to the app on Android 13, so cancel an inline text field by watching `WindowInsets.isImeVisible` instead of BackHandler.
- T1-tasks: the guide's Python shortcut does not exist on this PC; use sed or the Write tool for edits (a bash heredoc with odd quotes silently wrote nothing).
- B4a-lists: working copies here are CRLF, so multi-line perl/sed replacements silently miss; use the Edit tool on existing files and Write for new ones.
- B4a-lists: a bash heredoc with apostrophes can fail and write nothing; use the Write tool for big files and ls afterwards.
- B4a-s2: usage access is an owner switch in Android settings, not a pop-up; the app can only check it and open the page, so the launcher must work fully without it (fallback to favourites).
- B4a-s2: a saved list that mixes "owner set" and "automatic" needs explicit markers ("" auto, "-" blank); showing the filled list in the editor and saving it back would turn automatic discs into the owner's own.
