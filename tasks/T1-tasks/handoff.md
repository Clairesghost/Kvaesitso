# Handoff: T1-tasks stage 1

What changed (plain words):
- New package `de.mm20.launcher2.ui.vpd.tasks`: `VpdTasksStore` (tasks kept in one JSON file `vpd-tasks.json` in the app's files folder; each task id, text, created, done; atomic write via .tmp + rename; unreadable file renamed to `.bad`; errors logged under tag `launcher`, class name only), `VpdTasksVM` (AndroidViewModel; all file work on Dispatchers.IO; text trimmed, max 200 characters).
- `VpdTasksCard.kt` rewritten: "Connect Google Tasks" row and toast removed from the card; two task rows (newest first, 12-unit tick box, 12 Franklin text, dotted rows), then "Add a task…" row with an inline text field (Done saves, empty saves nothing), "+N more" next to the title.
- `VpdCards.kt` (own code from B3): wires the VM; ON_RESUME calls `removeDone()` (crossed-out tasks go on resume). Data collected with `collectAsStateWithLifecycle`. No timers.
- `.github/workflows/build-debug.yml`: new step writes `DEBUG_KEYSTORE_BASE64` to `~/.android/debug.keystore`; fails with "Fixed debug key missing: add the DEBUG_KEYSTORE_BASE64 secret" if empty. Secret is never echoed. No Gradle change.
- No upstream files touched. `VpdCardActions.comingSoon` is now unused (left in place).

How it was checked: read-through of every new file and its imports only. NOT compiled, NOT run.
Keyboard lift (guide review): while adding and the keyboard is up, the card is moved up (graphicsLayer, no animation) so its bottom is 8 units above the keyboard top; back to place when adding ends. Uses the card bottom from onGloballyPositioned, the root view height and WindowInsets.ime. UNVERIFIED: that ime insets reach this composable under the scaffold (if the activity consumes them, nothing lifts and the line stays hidden); no second route written.

NOT verified: compile; that the fixed key is accepted by Gradle (AF-34 still UNVERIFIED); looks; that the lift above the keyboard works; Samsung keyboard Done key; `isImeVisible` cancel behaviour.

Simple choices made (confirm or change):
- Cancel: Back / anything that closes the keyboard cancels the add line. Tapping elsewhere on the screen with the keyboard still open does NOT cancel (no outside-tap catcher was written, to avoid touching the page scaffold). Say if needed.
- Tick check mark is drawn in the text colour; crossed-out text is also paler (55%).
- New task text capitalises the first letter (keyboard setting); newlines become spaces.
- "+N more" is plain text (no action until B4).
- Crossed-out tasks still count toward the "+N more" until removed on resume.
- Tasks are not included in Kvaesitso's backup.

Sellable parts added ([M] rows): none.
Next stage needs: B4 Today/tasks sheet can read `VpdTasksStore`/`VpdTasksVM.tasks`; "Add as task" from search can call `VpdTasksVM.add`.
Branch: claude/T1-tasks (see `git log`).
