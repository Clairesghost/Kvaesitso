# Launcher Studio Kit

Make your own Android phone home screen (a "launcher") that looks and works the way YOU decide, with Claude Code doing the building. A guide agent asks you multiple-choice questions, remembers your answers, makes previews before any code, and builds in small safe batches. Nothing goes online without your yes.

The kit comes from a real project (a themed launcher built by a non-technical owner with Claude over many sessions). It contains the process, rules, templates and Android facts we proved, and none of that project's art, names or designs: every design file is blank.

Licence: CC BY 4.0 (free to use and share, credit required). See `LICENSE`.

## What you need
- A Windows or Mac computer with Claude Code installed and signed in.
- A GitHub account (free).
- An Android phone that lets you install an APK file (almost all do; you switch on "Install unknown apps" once).
- About 1 to 2 hours across a few days for a first version, and a Claude plan with enough usage (see `playbook/usage-and-cost.md`).

## What it can do
- Re-draw the Home screen, the app list, search and typing layer, settings screens of the launcher, icon shapes and an icon pack, gestures (swipes, taps) inside the launcher, widgets like weather and notifications list, landscape layout, and a wallpaper the launcher sets.
- Produce pictures and steps for things you apply yourself (lock screen picture, always-on display picture, keyboard picture).

## What it cannot do
- Change the phone maker's system screens: the pull-down quick panel, the lock screen itself, the system navigation bar, other apps' insides (for a Samsung phone this includes One UI system screens).
- Work on a phone with limits you cannot lift: a locked bootloader means apps and launchers only, no custom system software. That is fine for this kit.
- Run the app on the computer. Only your phone can show how it really behaves; so every batch ends with a short checklist for you to try.
- Replace advice from a lawyer: licensing and selling notes are checklists, not legal advice.

## 10-minute quick start
1. Unzip the kit into a new short folder (no spaces) and open Claude Code there.
2. Paste the message from `START-HERE.md`.
3. Answer the questions. The guide sets up GitHub for you in small steps.
4. Look at the design previews, choose, say "go".
5. Approve the push, wait for the build, install the APK on your phone, try the checklist, report.

## The workflow in one picture
```
 YOU                      GUIDE (kit-guide)            THREADS / HELPERS
  |  answers (multiple choice)  |                              |
  |---------------------------->| writes ops/decisions.md      |
  |                             | previews  ---------------->  phone-stylist draws 2-3 variants
  |<-- previews, pick one ------|                              |
  |---- pick ------------------>| brief per task ---------->   thread builds (own branch, commits locally)
  |                             |<--- handoff + testlist ----- (never pushes)
  |                             | merge batch -> integration branch
  |                             | (deep check ONLY if you ask)
  |<-- READY TO PUSH (plain) ---|
  |---- "yes" ----------------->| push -> GitHub builds APK
  |<-- install steps + checklist|
  |---- test on phone --------->| report -> bugs -> NEW thread (2 failed fixes: escalate)
```

## Folder map
See `MANIFEST.md` for every file. Main ones: `START-HERE.md` (your first message), `.claude/agents/kit-guide.md` (the guide), `CLAUDE.md.template` (project rules), `docs/question-bank.md` (all interview questions), `playbook/` (how we work), `ops/templates/` (blank forms), `scripts/` (build workflow, screenshot test, zip script), `example-dry-run/` (a worked example with a made-up cosy-forest user).

## Honest limits of this kit
- It has not yet been tried by a stranger from start to finish.
- The Android facts in `ops/templates/api-facts.md` were verified on ONE phone model and Android version. Re-verify on yours; the guide's `api-scout` helps.
- Selling a launcher involves licences (Kvaesitso is GPL-3.0), names and trademarks: read `playbook/monetizing.md` first.
