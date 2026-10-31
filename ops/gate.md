# Gate

Copy to `ops/gate.md`. A stage passes only if all hold. The workspace may not be able to compile, so the build can run in GitHub Actions.
1. Push the branch (only after the owner's yes); the build workflow for it finishes green (`gh run list` / `gh run watch`).
2. No new `catch` without logging; no upstream-file edits outside the task brief.
3. Diff read by the guide (and a `reviewer` pass for medium or high risk changes).
4. `tasks/<id>/testlist.md` exists and follows `ops/templates/testlist.md`.
5. Motion and battery checklist in `CLAUDE.md` ticked for every animated thing.
6. Anything sellable has a row in `ops/monetization.md`.
