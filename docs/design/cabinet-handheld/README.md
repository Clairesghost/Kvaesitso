# Handheld cabinet: rough previews (D-33)

Start: the apothecary cabinet. Change: the whole screen is now ONE object you hold, with its own edges, hinge and catches. Phone: Galaxy S20 FE, 1080x2400, punch-hole top centre (kept clear). Everything is placeholder: fake agenda text, fake weather, fake battery 78%, fake date. Pictures only; nothing was run on the phone.

"Inspired by only": the owner's first photo and the earlier cabinet. No brand logos or names used. Fonts (all SIL OFL, licence texts in `fonts/`): Limelight, Josefin Sans, Lato.

## Pictures (open the PNG; the HTML with the same name is the source)
| What | File |
|---|---|
| Everything on one sheet | `compare.png` |
| A. Form 1, opened pocket compact, day / night | `form1-day.png`, `form1-night.png` |
| A. Form 2, vintage radio-style gadget, day / night | `form2-day.png`, `form2-night.png` |
| B. Form 1 in palette 2 and palette 3 (palette 1 = form1-day) | `form1-palette2-day.png`, `form1-palette3-day.png` |
| C. 12 icons in two styles | `icons-sheet.png` |
| D. Weather lens in four states | `lens-states.png` |

## What is the same in every picture (less busy than the old cabinet)
- One object, edge to edge: no shelves and no wall. Fewer things on screen: lens, clock, agenda, 12 icons. Old dividers, candle, pendulum and extra drawers are gone.
- One clock (the round one). No separate digital time. Date plate top-left, battery top-right (glass cell plus "78%"), both clear of the punch-hole. Real Samsung bar hidden.
- Agenda is a paper card with fake text. 12 icons with plain names, no logos. Switches and folders are not on Home.
- Day and night are the same picture, darker, with the lens showing a moon and the clock face going dark. [M] candidates (sellable later): each palette, each icon style, each lens art set.

## The two forms
- Form 1, opened pocket compact (my pick, "best of the two"). Lid on top holding the big weather lens, a hinge across the middle, the body below with the clock, agenda and a tray of icons. Stitched leather, brass side catches. Reads most clearly as "a thing in the hand", the lens gets the most room, and the layout is a calm single column.
- Form 2, vintage radio-style gadget. Arched shoulders, bolted lens faceplate, clock and agenda side by side, icons as round brass-ringed buttons on a speaker-grille. Busier (grille lines, side vents, more metal) but the most "gadget" in feel.

## Palettes (form 1; hex values; text contrast checked, WCAG AA needs 4.5 to 1)
| | 1 Forest green and ivory, brass | 2 Midnight blue and copper | 3 Oxblood and champagne gold |
|---|---|---|---|
| Leather | #2c5a46 | #243660 | #6a1f2b |
| Lining | #14291f | #0f1a33 | #2a0d14 |
| Metal light / mid / dark | #f4dc9a / #c99b3e / #6f4c14 | #f6c0a0 / #c4713f / #6d3418 | #fbefd2 / #d3b883 / #7a6535 |
| Paper / ink | #f3ecd6 / #1a1810 | #f1e8dc / #16130f | #f6eee0 / #1d1412 |
| Icon wax colours | #a8412f #c18a2a #3f7f82 #6b4a7a #7d8f3e #b86a4a | #c9653d #d9a441 #4c8f9a #8a5d9c #6f9a5a #c9536b | #d8a14a #4f8a7d #b5707f #7c8bb0 #a9b35a #e0794f |
| Ink on paper (date, agenda, labels), day / night | 15.0 / 12.0 | 15.3 / 12.2 | 15.7 / 12.5 |
| "78%" on leather, day / night | 6.7 / 9.3 | 9.8 / 11.0 | 9.9 / 11.4 |
| Weather plate text, day / night | 10.7 / 7.1 | 8.7 / 6.0 | 13.4 / 8.8 |
| "TODAY" heading, day / night | 6.5 / 5.2 | 4.7 / 4.7 | 4.5 / 4.5 (at the limit) |
All pass. Measured from the colours in the sketch; not measured on the real screen. The icon pictures themselves are decorative: the name label carries the meaning.
Walnut and brass (the old cabinet) is not offered again because the owner asked for different colours; it can come back if wanted.

## Icon styles (sheet `icons-sheet.png`, same 12 apps)
- (i) Glass vials: a small bottle with cork, coloured liquid and a paper label showing the symbol. Fresher than the old jars, reads as "apothecary", tall shape so it needs more row height.
- (ii) Pressed wax seals: a round seal with the symbol raised on it, name ribbon below. Round and compact, so 12 fit calmly; this is the style used in all Home pictures. Calc is an abacus.
- Home pictures show seals only. If you pick vials, I redraw the Home with vials.

## The weather lens (`lens-states.png`)
A brass-rimmed glass dome over a tiny landscape. Light effects: a curved glare, a small second glare, a bright crescent at the lower edge, a darker rim so it feels round. States: sunny (sun, warm glare), rain (drops on the glass, streaks), snow or frost (frost from the rim, flakes), clear night (moon, stars, cool glow on the rim and the leather). Later motion, one effect only: drifting glare, a running drop, a creeping frost line, twinkling stars. Tap opens the Samsung Weather app (D-19).

## Questions for the owner (pick a letter)
1. Which form?  A) Form 1, opened pocket compact (recommended)  B) Form 2, radio-style gadget  C) Mix (say which parts)
   Effort: Form 1 MEDIUM, Form 2 MEDIUM to HARD (more metal and grille to draw). Battery: LOW for both.
2. Which colours?  A) Forest green and ivory (recommended: farthest from the old brown)  B) Midnight blue and copper  C) Oxblood and champagne gold  D) None, show me others
   Effort: EASY once one is drawn (the colours are swapped, not redrawn, but a look switch for two palettes is MEDIUM). Battery: LOW.
3. Which icon style?  A) Wax seals (recommended: calm and compact)  B) Glass vials  C) Seals on Home, vials in the apps page
   Effort: seals MEDIUM, vials MEDIUM (12 hand-drawn each; the rest of the apps get an automatic matching shape, HARD as told before). Battery: LOW.
4. Is it calm enough?  A) Yes as shown  B) Calmer still (drop the agenda frame, lens and clock only above the icons)  C) Fewer icons (8)
   Effort: EASY. Battery: LOW.

## Weather lens: how it would be built (and what is NOT verified)
- Cheapest and recommended first: pre-drawn pictures per state (4 states, day and night art) swapped by the weather, with one small slow effect on top (glare, drop, flake, star). Effort MEDIUM. Battery LOW (a small area, 12 frames per second or less, under the Motion setting, only while Home is on screen).
- A true live refraction (the landscape bending as a real lens) would need AGSL `RuntimeShader`. That is UNVERIFIED on this phone (ops/api-facts.md AF-16: not used and not tested). It would need a tiny probe on the phone first. Effort HARD. Battery MED (a shader redraws the lens area; would be limited to a tiny region and low frame rate). Until a probe passes, plan on the picture swap.
- The weather data itself still needs the owner's yes (location, cost, privacy); shown here as a placeholder.

## NOT verified
- Nothing ran on the phone; these are web-page pictures (headless Edge). Real colours on the AMOLED screen, text sharpness and the punch-hole position are unchecked.
- Whether tap areas can be laid exactly over a big background picture on different font and display sizes.
- Whether the Samsung status bar can be hidden on Home with the punch-hole kept clear (AF-18 says the bar can be hidden; not tried on the phone).
- The AGSL lens refraction (AF-16), weather source, and any motion (all effects are described only, nothing is animated in these pictures).
- Text over the night glow was judged by eye; contrast numbers are for the solid colours.
