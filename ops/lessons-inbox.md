# Lessons inbox

Copy to `ops/lessons-inbox.md`. Threads add 0 to 3 lines each: only surprises. Format: `- <task id>: <what surprised us, and what to do next time>`. The guide moves useful ones into `CLAUDE.md`, `ops/api-facts.md` or `playbook/lessons-learned.md` and clears the inbox about once a week.


- B1-foundation: CLAUDE.md is not committed on main, so new worktrees do not contain it; the thread had to read it from the main checkout. Commit it, or put the path in the brief.
- B1-foundation: Google Fonts ships Libre Franklin only as one variable file, not static weights; plan for variationSettings, not four files.
- B2-home: no Python or Java in the workspace, so a code stage cannot be compiled before pushing; keep new code in new files and re-read imports, and expect the first cloud build to find a few errors.
- B2-home: callbackFlow must end in awaitClose even on early-exit paths, or it crashes when collected.
- B2-home s2: a branch can only be checked out in one worktree; a later stage thread has to branch off (…-s2) and the guide merges it back.
