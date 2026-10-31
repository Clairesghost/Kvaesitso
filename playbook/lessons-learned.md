# The 25 most useful lessons

Distilled from a real project (a launcher built over about 40 threads by a non-technical owner). Each is generic. Check against your phone.

## Process
1. Decide first, build second. Every design question as multiple choice with previews; code written before a preview was approved was usually redone.
2. Show 2 or 3 rendered options side by side early. One-at-a-time design rounds took 8 or more iterations for a single header element.
3. Batch the work and push once. A cloud build takes 5 to 12 minutes and a phone test costs the person real time.
4. The workspace cannot run the app: always list what was NOT verified, and let the phone be the judge.
5. One thread = one stage = one branch; fixes after phone tests go to a NEW thread built from brief + handoff + bugs. Two failed fixes means stop and find the root cause.
6. Read the diff yourself; never trust "done". A missing import only shows in the cloud build, so re-read imports of every new file before asking to push.
7. Run the deep (strongest-model) check only on request and once per batch; per-task checks burned usage for little gain.
8. When only the phone can answer (cut-out position, a package name), ship on-screen numbers in the first test build instead of guessing.

## Android and launcher facts
9. The phone maker's status-bar icons cannot be moved or filtered. The only options: hide the whole bar and redraw what you want. Show the person this limit before designing.
10. The status bar is hidden reliably through the scaffold configuration, not from a child screen (the scaffold re-shows it on resume).
11. Camera cut-outs are often a box from the screen edge down past the hole; for a punch hole use bottom minus half the width. Test both landscape turns.
12. Hiding Home's own search bar: the real text field lives in the scaffold; read the text from the search view model, not from your look-alike bar.
13. A text field in the middle of Home leaves no room once the keyboard opens: use an overlay with the field at the top.
14. Only torch and ringer (normal/vibrate) can be toggled without special access on Android 13; auto-rotate needs "Modify system settings"; the rest can only open a settings page.
15. Sharing text to another app opens it and lets the user choose; nothing auto-sends without an API key or an accessibility service. Offer clipboard backup.
16. Weather: a background job cannot get GPS without background-location permission; take the fix in the foreground.
17. A free default launcher setting can lock rotation to portrait: check the upstream setting before promising landscape.
18. Package names of vendor system apps written from memory are wrong sometimes: always have a fallback (letter in a shape) and ask the person which fell back.

## Motion and battery
19. Gate every animation: launcher resumed, screen on, Battery Saver off, battery above 15% or charging, remove-animations off, and the person's Animations setting. Old code paths without a gate kept polling and animating.
20. A press effect started on Release is invisible when the same release opens an app: start on Press and delay the launch about 120 ms; reset the effect on resume and when the gate closes.
21. Ambient effects: small regions, 5 to 12 fps, at most two at once. Pre-render complex art (glass, smoke) in layers instead of computing it live.
22. Pollers made with `while (true) { delay }` keep running when Home is stopped; use lifecycle-aware tickers.

## Layout and art
23. Compose text boxes are taller than the glyphs: set explicit line heights when stacking text above a fixed line; check layout from the person's screenshot, not arithmetic.
24. Fit text by measuring (text measurer: shrink, then shorten) instead of guessing widths; check at 360 dp wide and at large font size.
25. Keep sellable art in separate files with an author line and its own licence; keep fonts' licence files; letter-in-a-shape fallback icons keep any icon pack looking complete.

## Windows and tools
- No Python on the workspace PC: use the file tools and shell to edit, not Python scripts.
- A fresh git worktree has no `local.properties`; copy it from the main folder before compiling.
- Run git from the project root; a `cd` into a subfolder made a path-specific commit fail.
- System Java that is too new (27) breaks the build: point `JAVA_HOME` at Java 17 (Android Studio's `jbr`).
