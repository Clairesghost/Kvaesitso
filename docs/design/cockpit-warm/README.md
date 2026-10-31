# Warm cockpit (D-23, D-26, D-27, D-25, D-28): preview pictures only

No app code, no commits, no build. Pictures are 1080x2400 (Galaxy S20 FE portrait); the black dot is the camera hole, our own clock is left of it and battery right of it. All text is placeholder. The optional extras (folder, power / dark-light / rotate) are NOT on the Home (D-24). Panels are soft and rounded with thin glowing lines (D-27).

## Files
- `home-A-dark`, `home-A-light`: A amber phosphor (amber/gold on warm brown-black; light = warm paper, brown-amber ink).
- `home-B-dark`, `home-B-light`: B sunset coral and peach (plum-charcoal; light = soft peach-cream).
- `home-C-dark`, `home-C-light`: C warm cream with teal ink and a technical grid (dark = warm slate).
  Each Home has: own clock and battery, the RADAR CLOCK (sweep = seconds hand, hour and minute hands, blips for today's events: the ringed one is the next, the alert one is the dentist), a weather scope card with readouts (tap opens Samsung Weather), the agenda panel, and 8 generic icons. The space freed by the extras went to a bigger radar clock plus a date / next-event readout column and larger app icons.
- `radar-sweep-options`: sheet explaining sweep behaviour (continuous, ping 10 s, ping 5 s, Motion Minimal, Motion Off) with what animates and what stays still. Open the .html with `#live` on the end to see a small live CSS sweep; the PNG is static.
- `compare-warm`: three dark Homes side by side, then three light.
- `swatches-warm`: hex values, 4 accent candidates and the alert colour per variant, all contrast numbers.
- `fonts-warm`: the three dark Homes in both font pairs.
- `fonts/`: all SIL OFL 1.1, licence texts next to the fonts: Share Tech Mono, Rajdhani (kept); DM Mono, Nunito (softer proposal; OFL-dmmono.txt, OFL-nunito.txt).
All six Homes are drawn in the kept pair (Share Tech Mono + Rajdhani); `fonts-warm` shows the softer pair.

## Contrast
Every text pair (main, muted, accent, alert on ground, panel and the tinted next-event strip) and all 24 accent candidates were computed: all are 4.5 or more (lowest 4.5, B light accent on the next-event strip). On light looks, lines and text are dark ink with only a faint soft shadow; the glow is used on dark looks only.

## Sweep: is every second busy or hard on the phone? (facts, nothing measured)
- A continuous sweep means a small round area is redrawn 12 times a second for as long as the Home screen is showing and the screen is on. The phone cannot rest between those redraws, so it costs more than a still picture. The screen itself (its brightness) is normally the biggest drain, and the sweep is a small extra on top.
- It stops when you open another app, turn the screen off, or turn Battery Saver on (see the rules in CLAUDE.md). It would not run in the background.
- A ping every 10 s moves the line only about 2 s in 10: roughly 80% fewer redraws than continuous (every 5 s: 60% fewer). That is design arithmetic, not a measurement. Minimal redraws once a minute; Off never.
- Your S20 FE has an AMOLED screen, where dark pixels use less power; thin amber lines on dark are cheap, large light backgrounds cost more.
- Nothing has been measured. After a day, check Settings > Battery and tell us the number.

## Questions for the owner
1. Which variant? (A) amber phosphor (the closest to the original cockpit, warm but still techy), (B) sunset coral and peach (softest), (C) cream with teal ink and grid (calm, paper-like). My lean: B for "soft", A for "closest to what you picked".
2. Radar sweep: (A) ping every 10 s (calmest, my lean), (B) ping every 5 s, (C) continuous every second, (D) no sweep except Full motion mode. Motion Minimal and Off apply to all.
3. Clocks: the radar clock is big AND the corner shows a digital time (needed because the real Samsung bar is hidden). Is that two clocks too many? (A) keep both as drawn, (B) drop the corner time and read time from the radar only, (C) keep the corner time and make the radar a decoration without hands.
4. Fonts: (A) keep Share Tech Mono + Rajdhani, (B) the softer DM Mono + Nunito (my lean for warmth).

## NOT verified
- Nothing ran on the phone; these are web-page pictures rendered with Edge. Real font rendering, text size with the phone's large-text setting, and the punch-hole position were not checked on the S20 FE.
- Whether Samsung Weather can be opened from a tap is assumed, not tested.
- Thin line and glow contrast (non-text) was not computed; the lines are decoration. The text was computed, not measured on the screen.
- Frame counts and the "80% / 60% fewer" figures are arithmetic from the design, not measurements. The live CSS sweep is a rough idea, not the final animation. How a ping would behave in a real app (timers, battery rules) was not built or tested.
- The blip positions are drawn as a rule (angle = clock position, distance = hours away); that rule is my suggestion, not decided.
- Generic glyphs and weather art are placeholders.
