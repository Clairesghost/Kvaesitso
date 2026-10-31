# STATUS (the guide updates this before stopping)

Updated 2026-10-28. `main` = untouched upstream Kvaesitso fork (no build yet). PHASE 0 done. PHASE 1 interview in progress: D-1 to D-8 recorded. Direction: calm retro-futurist brass and chrome, two looks (dark/light), one clock + weather lens, three toggle switches, apps plus one calendar/next-task widget, project only for owner.

| Task id | Stage | State | Branch | Notes |
|---|---|---|---|---|
| P14-cute | 1 | Design approved through D-157; docs/design-system.md written (D-158); open: weather source, swipe to clear; next: brief for the first build batch | (none, page only) | docs/design/refined/dial-mockup-cute.html (D-77): exact copy of phone 1 from the compare page, alone. Loaded in the Browser pane, no errors. Not committed. |
| P13-vintage | 1 | v2 published (icons, clock and weather options), awaiting owner choice | (none, page only) | Full vintage mock-up docs/design/refined/dial-mockup-vintage.html (D-73). Loaded in the Browser pane; by script: both pull-down stages, media next, brightness, clear notification, side panel, app list + filter, all four colours and night. Console only shows pointer-capture errors from fake test events. Not tried: real touch, swipe from the edge, A-Z strip drag. Not committed. |
| P12-compare | 1 | CHOSEN: vintage phone (D-72) | (none, page only) | Side-by-side page docs/design/refined/dial-mockup-compare.html (D-71): cute vintage phone vs retro-modern, same dial. Loaded in the Browser pane, no console errors; spin by pointer events, calc search, add task, all weather and time settings run through by script. Not tried: real touch, long-press, pull-downs (not included). Not committed. |
| P11-chartroom | 1 | awaiting owner feedback | (none, page only) | New theme CHART ROOM (D-70): docs/design/refined/dial-mockup-chartroom.html. Loaded in the Browser pane, no console errors; clicked through pull-down stages, media card, brightness, side panel, five weather scenes at all times of day. Not tried: touch swipes and drags. Earlier steampunk/gaslight pages remain as references. Not committed. |
| P10-steam | 1 | awaiting owner feedback | (none, page only) | Steampunk variant docs/design/refined/dial-mockup-steam.html (D-61), copy of Pocket Dial. Loaded in the Browser pane, no errors; idle animations could not be watched (the pane reports the page hidden), gate logic read only. Not committed. |
| P9-pocket | 1 | CHOSEN as direction (D-59); owner reviewing details, then docs/design-system.md | (none, page only) | New mock-up docs/design/refined/dial-mockup-pocket.html (D-58). The guide LOADED it in the Browser pane (no console errors; clicked through music, drawer, quick settings, settings, schemes, search calc, battery gate). Not tested: touch drag, long-press by hand, swipes. Not committed. |
| P8-spinoff | 1 | v10 published, awaiting owner feedback | (none, page only) | Save point docs/design/refined/dial-mockup-OG.html (copy of v18). Spin-off docs/design/refined/dial-mockup-spinoff.html, changes per D-57. Not re-checked in a browser by the guide. Not committed. |
| P7b-dial-mockup | 1 | frozen as the OG save point (v18) | (none, page only) | Interactive moving mock-up of the spinning dial (night scheme, round 4): docs/design/refined/dial-mockup.html, also published as a private page. Not run in a browser by the guide; no script error check was possible (no node, headless Edge printed nothing). Not committed. |
| P7-refined | 1 | awaiting owner approval | (none, pictures only) | Round 4 in docs/design/refined/ (schemes a/b/c/d, layouts i/ii, icon styles 1-3, weather, spin). Guide looked at compare.png: far closer to photo 1. Watch: ring labels are very small (about 38 dp discs), weakest contrast 4.7:1. Open: scheme set, layout, icon style, calm enough, "PINNED" word. Not committed. |
| P6-payphone | 1 | rejected by owner as too bold (D-40); superseded by P7 | (none, pictures only) | Pictures in docs/design/payphone/ (4 colour schemes, red day/night, spin storyboard, weather states, compare.png). Guide looked at compare.png. Open: schemes to ship, apps beyond 12, pinned favourites, calm enough. AF-22/23 UNVERIFIED on phone. Not committed. |
| P5-cabinet-handheld | 1 | superseded by P6 (owner blended with payphone, D-37) | (none, pictures only) | Round 2 in docs/design/cabinet-handheld/: 2 forms, 3 palettes, 2 icon styles, 4 weather-lens states. Guide looked at compare.png. Open: form, palette, icon style, calmness. Real bending lens = AGSL RuntimeShader UNVERIFIED (AF-16); fallback = pre-drawn lens pictures swapped by weather. Not committed. |
| P4-scenes | 1 | owner chose cabinet as the start (D-33); superseded by P5 | (none, pictures only) | 3 scene sketches (steampunk, bench, cabinet), day + night, in docs/design/scenes/. Guide looked at compare.png. Open: which scene, how many drawn apps, corner battery/date, how much motion. Stylist added one [M] row to ops/monetization.md. Not committed. |
| P3-cockpit-warm | 1 | rejected by owner (D-29); archived | (none, pictures only) | 3 warm variants (A amber, B coral, C cream/teal) + radar sweep options in docs/design/cockpit-warm/. Guide looked at compare-warm.png and radar-sweep-options.png. Open: variant, sweep speed, two clocks (radar + corner digital), font pair. Not committed. |
| P2-themes | 1 | owner chose cockpit (D-23); superseded by P3 | (none, pictures only) | 3 themes in docs/design/themes/ (cottage, cockpit, minimal; dark+light). Guide looked at compare-all.png. Note: clock is small in cottage/cockpit (corner clock = the one clock). Not committed. |
| P1-previews | 1 | superseded by P2-themes (owner rejected brass, D-21) | (none, pictures only) | Previews in docs/design/ (home-dark-A, home-light-A, home-dark-B, folders-options, fonts-specimen, swatches, weather-lens-states). Guide looked at dark A, light A, folders. Light look: app glyphs faint. Not committed. |
| T0-build | 1 | todo | claude/T0-build | Fork builds in GitHub Actions; signed APK installs; original launcher kept |

States: `todo`, `active` (a thread is working; only then may it be messaged), `READY TO PUSH`, `pushed`, `DONE (owner verified on phone <date>)`, `blocked: <why>`, `deferred: <why>`.

## Open for the owner
1. Nothing waiting. Next batch of questions is coming (palette, fonts, third switch, calendar source).

1. Nothing waiting. The spinning dial check and the previews are under way.

## Next steps for the guide
- Research answered by the owner (D-55). Mock-up v17 done: 48 dp tap areas, search extras + new hint, A to Z in the pull-up. Still open from the owner: refined look approval (round 4 questions: scheme set, layout, icon style), 12h time done, bottom pill job done (all apps), landscape later. Money panel dropped. Next: finish portrait approval, then docs/design-system.md, then api-scout probes (AF-16, AF-18, AF-22) before any build; remind owner that phone tests come only after the first build (batch, push approval).
- 2nd reference received (D-36). Blend decided (D-37). Next: P6 preview round = payphone body (rotary ring with 12 seal icons, centre disc = the clock, coin-slot strip = animated weather, framed placard = agenda) + bubble lens + wax seals; colour schemes list (D-35: red payphone plus a few more, colours only). Draw the dial as still ring first unless owner says spin.
- api-scout before promising spin: gesture conflict with Kvaesitso swipes, drag vs tap on a rotating ring, fling/detent, battery.
- Still never asked: dock/gestures (G7-G8), search (G9), landscape design (G10), name, money, AI (G15). Before any build: api-scout on tap targets over one big picture, status bar hiding on the phone, Google widgets, AGSL on Android 13 (AF-16).
- D-1 to D-20 recorded. Interview batches 1-3 done. Still to ask: accent/alert colours (from preview), G7 dock, G8 gestures and apps page, G9 search, G11 animation detail, G12-14 wallpaper/icons/keyboard, G15 name/money/AI.
- api-scout before building: hosting Google Calendar/Tasks widgets in Kvaesitso and theming them; weather source for the lens.
- Next concrete step: phone-stylist draws portrait previews (docs/design/): dark + light, two font sets, two folder options. Owner approves BEFORE any code.
- Then G7-G11 as needed, G15 remaining (name, money features, AI).
- Then phone-stylist makes preview pictures in docs/design/ BEFORE any code; owner approves previews; then write docs/design-system.md.
- Also before first install: walk owner through allowing APK installs from outside the Play Store.
| B0-pipeline | build | DONE: debug workflow ran green on claude/B1-foundation | main | |
| B1-foundation | build | DONE (owner verified on phone 2026-10-31): installed, name and icon right, home screen is plain Kvaesitso as expected, lock screen unchanged. Build run 41215748144 green. | claude/B1-foundation | merged into main locally (not pushed) |
| B2-home | build | DONE (owner verified on phone 2026-11-01): home, coins, dial spin and return, clock hold, Day/Night, bigger dial (D-183), charging ring. Battery number after a day: parked by the owner, ask later. | claude/B2-home-s3 | merged into main locally (not pushed) |

| B3-cards | build | active (thread started 2026-11-01) | claude/B3-cards | tasks/B3-cards/brief.md |
| G1-google-tasks | build | todo (after B3): fixed signing key, Google project with the owner, sign in, tasks on the card | claude/G1-google-tasks | ops/build-plan.md |

## Parked by the owner
- Battery check (Settings > Battery, Vintage Pocket Dial percentage after a day of use): owner said put a pin in it (2026-11-01). Ask again before B6.
