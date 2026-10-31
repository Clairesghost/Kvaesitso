# Refined handheld: round 4 previews (D-40)

Pictures only. No Kotlin, nothing committed, nothing pushed, nothing built. Phone: Galaxy S20 FE, 1080 x 2400, punch-hole top centre (the black dot in the pictures is only a stand-in; the date plate sits left of it and the battery plate right of it). All text is placeholder (fake agenda, fake weather, fake battery 78%, fake date, apps chosen only to fill the ring).

"Inspired by only": the owner's first photo (brushed chrome handheld, aged brass rims, glass portholes, painted retro-futurist city, round dial with thin line icons). Nothing is copied: the city, car, airship, clock, glyphs and layout are drawn fresh.

## What changed from round 3 (the answer to "classless, tacky")
- Body is brushed chrome with an aged brass rim and a thin dark-teal inlay line. No flat colour blocks and no lacquer: every metal part has layered gradients, fine brushed streaks, a light edge and a soft shadow.
- Dusty-teal painted panel with faint engraved sunburst lines (very low contrast) instead of a loud flat background.
- The dial is a brass-rimmed glass window onto ONE static painted city picture (muted teal, ochre, dusty rose, cream; an airship at the top, a classic car at the bottom). The icon ring sits on frosted, smoked glass over it. The one clock is in the middle, drawn on clear frosted glass (Roman numerals at 12, 3, 6, 9).
- Icons: engraved single-hue line glyphs on enamel discs with a brass rim. No rainbow seals. At most ONE accent colour in the whole picture (a muted rose for "TODAY" and the second hand in a, b, d; sage in c).
- Type: Cormorant Garamond and Cormorant SC (SIL OFL, licence in `fonts/OFL-cormorant.txt`). Small, restrained, small-caps labels.
- Weather: a glass bubble lens with a brass bezel that overlaps a chrome slot strip. Agenda: a brass-framed ivory placard with four small rivets (the only rivets). Date and battery: two small dark plates with brass rims.

## Files (open the PNG; the HTML with the same name is the source, drawn by `lib.js`)
| What | File |
|---|---|
| (a) Chrome and aged brass, dusty teal paint, day (DEFAULT), layout i | `home-a-day-i.png` |
| (a) same, layout ii: calmer, bigger dial, slimmer placard | `home-a-day-ii.png` |
| (b) same at night, warm lamp glow | `home-b-night.png` |
| (c) Graphite and rose gold | `home-c-graphite.png` |
| (d) Ivory enamel and brass (light) | `home-d-ivory.png` |
| Weather lens, 4 states, one small motion each | `weather-states.png` |
| Spinning dial, 4 stills, plus the belt of up to 24 | `spin-storyboard.png` |
| 12 icons, 3 glyph styles, plus style 1 in the other schemes | `icon-sheet.png` |
| Everything side by side (round 3 on the left for comparison) | `compare.png` |
| Fonts and licence | `fonts/` |
| Drawing code (re-render with `render.sh`) | `lib.js`, `sheets.js`, `render.sh` |

## Colours and contrast (WCAG AA needs 4.5 to 1 for text; computed from the flat colours)
| | (a) day | (b) night | (c) graphite and rose gold | (d) ivory |
|---|---|---|---|---|
| Chrome / body | #fbfcfc to #858f95 | #d3d9dc to #3f484e | #aab0b7 to #30343a | ivory #fffefa to #b9ad8b |
| Brass or rose gold, light / dark | #f1dfae / #66491f | #ebcd88 / #43300f | #f7d8c8 / #6c403a | #f3e2b2 / #69501f |
| Painted panel | #69979a to #41696d | #2f5357 to #16303a | #564f65 to #2f2b3b | #b9cfc7 to #90b1aa |
| Paper (placard) and ink | #efe7d3 / #15282b | #e6d8b6 / #0b191c | #ece2da / #16141d | #fbf6e8 / #223533 |
| Ink on paper | 12.4 | 12.7 | 14.3 | 12.0 |
| The one accent on paper | #8a3b47: 6.1 | #8a3b47: 5.3 | sage #4a6a5c: 4.7 | #7b3a45: 7.7 |
| Text on dark plates (weather, date) | 11.2 | 12.7 | 14.2 | 9.5 |
| Icon-ring labels over the smoked glass | 4.7 | 11.4 | 6.9 | 8.4 |
The ring-label number for (a) is the weakest and was computed over the middle tone of the painted picture; over its lightest part it can dip a little lower. A thin dark outline behind the letters is drawn to help; if the owner finds them hard to read, the fix is a darker glass (cost: EASY). Not measured on the real screen.

## Honest build notes
| Part | Effort | Battery | Notes |
|---|---|---|---|
| Static Home body, strip, placard, tray, date, battery as drawn pieces | MEDIUM | LOW | Painted metal looks come from pre-made pictures (one per piece and scheme) plus plain shapes; real-time brushed-metal effects are NOT needed. |
| Painted city scene | MEDIUM | LOW | ONE static background picture per scheme, drawn once, no animation. |
| Colour scheme setting (4 schemes) | MEDIUM first, then EASY per extra scheme IF colours are swapped; here each scheme also needs its own metal and scene pictures, so about MEDIUM each | LOW | [M] each scheme is a sellable theme. |
| Weather lens | MEDIUM (pre-drawn pictures swapped by weather, one small slow effect) | LOW | Motion only in the lens area (about 240 by 240 px), 12 frames a second or less, under the Motion setting (Full / Minimal / Off), only while Home is on screen, screen on, Battery Saver off, battery above 15% or charging. Weather source still needs the owner's yes (location, cost, privacy). |
| Real bending-glass lens | HARD | MED | Needs AGSL; UNVERIFIED (AF-16). Not used here. |
| Spinning dial, belt of up to 24 | HARD | LOW to MED (only while turning) | Under the Motion setting. Taps and drags must be taken by the ring so launcher swipes and double-tap lock stay quiet (AF-22, UNVERIFIED). |
| Icons: 12 drawn glyphs on discs | MEDIUM | LOW | All other apps get an automatic matching disc in the apps page (D-30). |
| Night lamp glow | EASY | LOW | Part of scheme (b): a static soft glow picture. Any animation of it would be a second ambient effect; none is shown. |

## Questions for the owner (pick a number or letter)
1. Which colour schemes go into the setting?
   A) All four: a (default), b night, c graphite and rose gold, d ivory (recommended).  B) a and b only (day and night of the same look).  C) a, b and d (no dark graphite).  D) Other, tell me.
   Cost: A about 4 times MEDIUM; B cheapest. Battery: LOW.
2. Which layout of the default scheme (a)?
   A) i: as round 3, placard bigger (recommended: easier to read the agenda).  B) ii: calmer, bigger dial, slimmer placard (less text space, three agenda lines stay).
   Cost: the same. Battery: LOW.
3. Which icon style (see `icon-sheet.png`)?
   A) Style 1: enamel disc with a brass rim, brass line glyph (recommended, shown everywhere).  B) Style 2: engraved brass disc (warmer, heavier).  C) Style 3: glass dimple with a bare glyph (calmest, least like a button).
   Cost: the same. Battery: LOW.
4. Is it calm and tasteful enough now? Anything still too loud?
   A) Yes, go on to build.  B) Calmer still: remove the faint sunburst lines and the rivets, make the paint plainer.  C) More of the first photo: show the painted city over the whole panel behind the dial (busier, I would not recommend it).  D) Something else, tell me.
   And is the word "PINNED" on the favourites tray OK, or no word at all? (My placeholder, still open from round 3.)

## NOT verified
- Nothing ran on the phone. These are web-page pictures rendered with headless Edge; real colours on the AMOLED screen, sharpness and the exact punch-hole position are unchecked.
- Whether the spinning dial turns smoothly and can swallow taps and drags without the launcher's own swipes or the double-tap lock firing (AF-22, still unverified).
- Whether the status bar can be hidden on Home so the date and battery plates sit clear of the punch-hole (AF-18, still unverified).
- The AGSL glass-bending lens (AF-16, unverified, not used). The weather source, every motion (nothing is animated in these pictures) and the click feel.
- Contrast numbers are from flat colours, not from the real screen; the night glow and all material looks were judged by eye on a monitor.
- The lock screen, always-on display, status and navigation bars are outside the launcher and are not restyled by any of this.
- Not decided by me and open for the owner: the 12 ring apps and their order, the 3 pinned favourites, the word "PINNED", the scene content (airship and car are optional and can be dropped), and all wording.
- [M] sellable parts to add to `ops/monetization.md` by the guide (I did not edit it): each colour scheme (a to d) as a theme, the painted scene per scheme, and the icon style sets.
