# My Launcher: rules for every Claude session

Project: a custom Android launcher (fork of Kvaesitso, GPL-3.0) for the owner's Samsung Galaxy S20 FE (SM-G781U1/DS), Android 13. Set up 2026-10-28.

## Project
- The owner decides every look and feature. Approved design: see `docs/design-system.md` and the previews in `docs/design/`. Do not redesign it.
- The phone is a normal phone: apps and launchers only, no custom system software.
- Build: GitHub Actions compiles the APK (`.github/workflows/`). Android Studio and local Gradle may not exist. The owner installs the APK on the phone.
- Keep the phone's original launcher installed as a fallback. Never ask for passwords, bank logins or card numbers. No personalised financial advice.

## Where things are
| What | Where |
|---|---|
| Code | `app/`, `core/`, `data/`, `services/`, `plugins/` (upstream layout) |
| Design | `docs/design/`, `docs/design-system.md` |
| Task status | `ops/STATUS.md` |
| Decisions by the owner | `ops/decisions.md` |
| Verified Android facts | `ops/api-facts.md` |
| Checks | `ops/gate.md` |
| Templates | `ops/templates/` |
| Question list | `docs/question-bank.md` |
| How we work | `playbook/` |

## Read these, nothing else by default
1. This file. 2. Your row in `ops/STATUS.md`. 3. `tasks/<id>/brief.md` (+ `handoff.md`, `bugs.md` if present).
Need more? Search the code (Grep), then open only the files that match. Never read whole folders.

## Owner rules
- Ask before ANY design decision: look, name, layout, wording, a new feature. Give choices as multiple choice (at most four per batch), recommendation first, with what each costs. Only decisions listed as delegated in `ops/decisions.md` may be made by Claude.
- Whenever the owner must do something (install, grant a permission, test, answer): numbered steps in plain words: where to be, what to tap, what they should see, what to report. Assume no technical background. Never say only a step code. At most 3 do-lines per step.
- **Never push to GitHub (or start a build) without the owner's explicit yes.** Commit locally, do all steps of a task in one batch, then report READY TO PUSH with a plain-words summary and wait. A yes covers that batch only.
- Say what was NOT verified. The workspace usually cannot run the app; only the phone can.
- Anything with money or accounts (finance panels, bank links, paid services): placeholders only, no invented numbers; ask first because of keys, cost and privacy.

## Battery and motion checklist (every screen with animation)
- [ ] Animates only while the launcher is on screen, the screen is on, Battery Saver is off, battery above 15% or charging, and the phone's remove-animations setting is off.
- [ ] A setting (Full / Minimal / Off) lets the owner turn motion down.
- [ ] Small screen regions only; ambient effects at 12 frames per second or less; at most two ambient effects at once.
- [ ] Press effects start on press and always reset (on resume and when the gate closes).
- [ ] No timers or polling when the launcher is not visible.
- [ ] The first build measures nothing; the owner checks Settings > Battery after a day and reports the number.

## Code rules (Kotlin / Android)
- Use `ops/api-facts.md`; don't re-derive facts. For an unverified call ask the `api-scout` agent. If still UNVERIFIED, write a tiny probe, test it on the phone, then add an AF entry.
- No empty `catch`. Log every caught error with a tag (`Log.e("launcher", ...)`). Never log notification text, contacts or coordinates.
- No "try A, catch, try B" guessing. Probe once and keep the call that works.
- Keep upstream code changes small and in separate commits so upstream updates stay mergeable. New code goes in new files or a new package where possible.
- Respect GPL-3.0: keep headers and `LICENSE.txt`. Fonts and art keep their own licence files.
- Re-read every new file's imports before asking to push: a missing import only shows in the cloud build.
- Commit messages end with the line Claude Code gives you (co-author trailer).

## Definition of done (each stage)
1. `ops/gate.md` checks pass; after the owner approves the push, the GitHub Actions build for the branch is green.
2. Owner checks for the phone are in `tasks/<id>/testlist.md` (from `ops/templates/testlist.md`): visible result first, at least one "should NOT happen" step, a restart-the-launcher step if the feature saves data.
3. `tasks/<id>/handoff.md` written from the template.
4. 0 to 3 lines in `ops/lessons-inbox.md` (surprises only).
5. STATUS row updated. Say FINISHED and stop.
6. If screenshots are available (`playbook/seeing-the-ui.md`): picture of each changed screen under `docs/design/shots/<id>/`, looked at.

## Threads and the guide
- One thread = one stage of one task, started in the project root, in its own worktree and branch `claude/<task-id>`. Never resumed after FINISHED; the guide archives it.
- No `/loop`, scheduled tasks or idle notices in threads. Never message a thread whose STATUS row isn't `active`.
- Independent stages may run in parallel on their own branches, never on the same files. Keep parallel threads few.
- Fixes after phone tests: a NEW thread from brief + handoff + bugs. Two failed fixes of one bug: stop, write the root cause in `bugs.md`, escalate.
- The guide (`kit-guide` agent) starts fresh each work session from `ops/STATUS.md`, updates it before stopping, reads diffs and the build log itself, and never trusts an agent's "done".
- Threads propose rule changes as lessons lines. Only the guide and the owner change this file, `decisions.md` or `api-facts.md`.

## Usage-frugality rules (save the owner's Claude usage)
- The deep check (`final-check`, strongest model) runs ONLY when the owner says so, once per push batch on the merged branch; never per task, never for notes or one-line fixes.
- Few parallel threads (2 or 3). One push per batch. Archive finished threads. Start fresh sessions instead of one endless one.
- Cheapest model for lookups and test steps; Sonnet-class for writing and fixing code; the strongest model only for planning a new feature or a bug that survived two fixes.

## Design and money
- New screens follow `docs/design-system.md`; the `phone-stylist` agent carries the design to more of the phone. Anything sellable gets a row in `ops/monetization.md` and an [M] mark in the design system.

## Models
- Cheapest (Haiku class): `api-scout`, test steps, gate runs, search helpers.
- Mid (Sonnet class): `kit-guide`, writing and fixing Kotlin, `phone-stylist`.
- Strongest (Opus class): `final-check` (on request), planning a new feature, a bug that survived two fixes.
