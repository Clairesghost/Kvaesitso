> PARKED (D-194, 2026-11-01): owner kept local tasks (T1). Use this brief only if Google Tasks comes back.

# Task brief: G1-google-tasks

One task = one stage = one branch `claude/G1-google-tasks`. Read only this, `CLAUDE.md`, `docs/design-system.md`, your row in `ops/STATUS.md`, `tasks/B3-cards/handoff.md` and AF-33 / AF-34 in `ops/api-facts.md`. Search the code with Grep and open only matching files. The cards are in `app/ui/src/main/java/de/mm20/launcher2/ui/vpd/cards/`.

Goal: the debug build is always signed with the owner's fixed key, and the Tasks card shows and adds the owner's real Google Tasks after one sign-in with Google. Implements D-185, D-187, D-190, D-121 (title opens the tasks app).

Files in scope (new files in `de.mm20.launcher2.ui.vpd.tasks` or `.cards`; upstream edits tiny, each in its own commit):
1. Fixed key (AF-34), own commit: in `.github/workflows/build-debug.yml`, before the build, a step that reads the secret `DEBUG_KEYSTORE_BASE64` and writes it, base64-decoded, to `~/.android/debug.keystore` (create the folder). If the secret is empty, the step fails with a plain message ("Fixed debug key missing: add the DEBUG_KEYSTORE_BASE64 secret"). No Gradle change: the standard debug signing (alias androiddebugkey, standard debug passwords) picks the file up. Never print the secret.
2. Dependency: `com.google.android.gms:play-services-auth` (newest stable you can confirm; AF-33 saw 22.0.0) through `gradle/libs.versions.toml` and only the module that needs it. HTTP: the repo's existing Ktor client (`libs.bundles.ktor`), kotlinx.serialization for JSON.
3. Sign-in (AF-33): `Identity.getAuthorizationClient(activity).authorize(AuthorizationRequest)` with the scope `https://www.googleapis.com/auth/tasks`. If the result `hasResolution()`, launch its pending intent with an activity-result launcher from the card (only when the owner taps "Connect Google Tasks" or "Reconnect", never by itself). Use the returned access token for the REST calls; ask the AuthorizationClient again when a call returns 401 (silently when it can; if it needs the owner, the card shows "Reconnect Google Tasks"). Store no tokens yourself. Store only "connected" (Boolean) and the chosen list id in `core/preferences` (like `VpdHomeSettings`). No Web client, no server.
4. Tasks REST (AF-33): list id = the first list from `GET https://tasks.googleapis.com/tasks/v1/users/@me/lists` (store it). Read `GET /tasks/v1/lists/{id}/tasks?showCompleted=false` and show up to 2 open tasks (in Google's order; tasks with a due date first if Google's order does not do that already: keep it simple). Tick box: `PATCH .../tasks/{taskId}` with `status=completed`; the row goes away (no animation when the motion gate is closed). "Add a task…" last row (D-187): tap opens a small inline text field in the row with the keyboard; Done/Enter sends `POST .../lists/{id}/tasks` with the title; empty text does nothing.
5. Card (`VpdTasksCard`): header "Tasks" (script); tapping the title opens the Google Tasks app if installed (launch intent for package `com.google.android.apps.tasks`, ask `api-scout` to confirm the package), otherwise nothing. Rows in the B3 slots (header 22 + rows of 24): not connected = "Connect Google Tasks"; connected = up to 2 task rows (dotted dividers, small tick box, Libre Franklin 12) + "Add a task…" as the third row (94 = 22 + 3 x 24); no open tasks = "All done" + "Add a task…"; needs the owner = "Reconnect Google Tasks"; no internet / error = keep the last list shown this run, else one quiet row "Tasks unavailable right now".
6. Refresh on Home resume and after a tick or add; no polling, no timers (AF-15). Nothing runs while Home is not visible.
7. Privacy: never log task titles or tokens; log only HTTP codes and exception types with tag "launcher".

Done when (visible on the phone):
- This build needs one last uninstall of the B3 build (different key); every build after it installs over the old one without uninstalling.
- Tap "Connect Google Tasks": Google's own account and consent screens appear; after allowing, the card shows the owner's open Google Tasks.
- Tick one: it disappears here and is completed in the Google Tasks app.
- "Add a task…", type, Done: it appears on the card and in the Google Tasks app.
- Restart the launcher: still connected, tasks shown, no sign-in again.

Out of scope: several task lists, due-date editing, Notes (dropped), search "Add as task" (B4), Today sheet (B4). Do not change other cards, the dial or the housing.
Android calls needed: AF-33, AF-34 (UNVERIFIED on phone). Ask `api-scout` for: the Google Tasks app package name; how to launch the AuthorizationResult pending intent from Compose (IntentSenderRequest + StartIntentSenderForResult) and read the result with `Identity.getAuthorizationClient(...).getAuthorizationResultFromIntent`. Write AF lines for what you learn.
Motion or battery impact: small. Only network calls on resume or after a tap; tick-away animation gated by `VpdMotionGate`. Battery checklist in CLAUDE.md applies.
Sellable parts [M]: none.
Owner setup (done by the owner outside the code, the guide walks them through): Google Cloud project, Tasks API on, consent screen External and PUBLISHED (D-190), Android OAuth client with package `de.mm20.launcher2.debug` and the SHA-1 from `Documents\vpd-signing\sha1.txt` on the owner's PC. Nothing in the code needs a client id or key; do not add any.
Open questions for the owner: none. Anything unclear: simplest option that matches the mock-up, listed in the handoff.
Model: mid (Sonnet class). Commit in small steps (workflow key, preferences, auth + REST, card rows, add/tick).

Deliver: commit locally on `claude/G1-google-tasks` (messages end with the co-author trailer); `tasks/G1-google-tasks/testlist.md` from `ops/templates/testlist.md` (visible result first; the "uninstall once more" step in plain words; at least one "should NOT happen"; restart-the-launcher step; Google's unverified-app screen explained: tap Advanced, then Go to Vintage Pocket Dial); `handoff.md`; 0 to 3 lessons lines; update your STATUS row. Re-read every new file's imports. Do NOT push. Say READY TO PUSH with a plain-words summary and what you could not verify, then stop.
