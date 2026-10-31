# Build plan (the guide keeps this current)

Source of truth for the look: `docs/design-system.md` and the mock-up `docs/design/refined/dial-mockup-cute.html`. One stage = one thread = one branch `claude/<id>`. Nothing is pushed or built on GitHub without the owner's yes; a yes covers one batch. The workspace has no Java or Android SDK, so only GitHub Actions compiles (`.github/workflows/build-debug.yml`, debug build installs next to the original launcher).

| Stage | What | Depends on | Owner checks on the phone |
|---|---|---|---|
| B0 | Build pipeline (debug APK workflow) | none | none; first run proves the build compiles |
| B1 | Foundation: name "Vintage Pocket Dial", launcher icon A, bundled fonts + licences, Day/Night colour and type tokens, Look setting (Auto/Day/Night) | B0 | app shows the new name and icon; installs next to the old launcher |
| B2 | Home shell: top housing (search slot, camera ring, 3 status coins from real data), the dial (12 discs, spin, flick, snap, return home, tick marks fixed, clock, enamel time plate), Day and Night | B1 | spin, tap an app, battery and Wi-Fi coins are real |
| B3 | Cards: Calendar (all calendars), Alerts (real notifications, AF-1, swipe to clear), Tasks card shell (Google Tasks in G1; Notes dropped, D-186) | B2 | real events and notifications; swipe clears |
| T1 | Local tasks (D-190 to D-193): tasks stored in the launcher, newest 2 on the card, tick to cross out, "Add a task…" row; fixed debug signing key in the workflow (D-191, AF-34) | B3 | add, tick, untick, restart keeps them; update installs over the old app |
| B4 | App list (categories, letter strip), search results, Settings, dial editor, first-run, About | B3 | the lists and menus work with real apps |
| B5 | Weather (free service, owner decisions D-159), forecast card, landscape layout | B3 | weather and rotation |
| B6 | Polish: click feel, motion rules gates, accessibility labels, screen checks | all | the checks in `ops/phone-checks.md` |

Rules that apply to every stage: `CLAUDE.md` (new files, small upstream edits in separate commits, no empty catch, re-read every new file's imports, battery checklist, tasks/<id>/testlist.md and handoff.md, lessons lines).
