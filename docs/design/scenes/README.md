# Scene sketches: three Home concepts (rough, D-29 to D-31)

Phone: Galaxy S20 FE, 1080x2400, punch-hole top centre (kept clear in all sketches). Inspired only by the owner's first photo (brass, chrome, glass); nothing copied. Everything is placeholder: fake agenda text, fake weather (17 degrees, light rain), fake battery (78%), fake date. Rough on purpose: the layout, order and readability are the real proposal, the finish is not.

## Pictures (open the PNG; the HTML with the same name is the source)
| Concept | Day | Night |
|---|---|---|
| 1 Steampunk | `steam-day.png` | `steam-night.png` |
| 2 Clockmaker's bench | `bench-day.png` | `bench-night.png` |
| 3 Apothecary cabinet | `cabinet-day.png` | `cabinet-night.png` |
| All six side by side | `compare.png` | |

Fonts used (all SIL OFL, licence texts next to them in `fonts/`): Limelight (numerals), Josefin Sans (plates), Lato (labels and agenda).

## What every sketch has (same parts, day and night)
- ONE clock only: the scene's clock object. No separate digital time anywhere. (Our own clock stays; the Samsung bar is hidden.)
- Top-left corner: the date on a plate or label (not a clock).
- Top-right corner: battery as an object. Steampunk = small pressure gauge, bench = a candle that burns down, cabinet = glass vial that empties. Always shows "78%" as a number too.
- Weather object: steampunk = glass dome on a ledge with a temperature plate; bench = loupe over a tiny weather picture with a tag; cabinet = bell jar with a tiny landscape and a brass plate.
- Agenda object: steampunk = brass-framed punched card; bench = pinned note; cabinet = label card in a drawer front.
- 12 drawn-object apps (Phone, Messages, Camera, Web, Music, Maps, Mail, Notes, Photos, Calendar, Calc, Settings) with a name label under each. The owner said about 8 to 12; 12 is shown, 8 would just leave out the last row. No brand logos.
- Extras (switches, folders) are not on Home.
- Night: the same picture with the lamp, candle or clock face glowing. Day/night would follow the real time (and later the real weather).

## How each would be built (plain words)
### 1 Steampunk
- One background picture: wall, arched window with city, airship and smokestack, brass ledge, riveted app panel, the gear ring around the clock, the dome and the card frame.
- Live: clock hands (second hand ticks), battery gauge needle, the glow at night, a small drift of smoke, rain drops on the dome (one small area). Agenda text and weather numbers are real text on top.
- Apps: 12 pictures plus a name label each; tap areas are invisible boxes laid over the picture.
- Effort: HARD (most parts, most metal to draw and keep consistent). Battery: LOW to MED (two or three small moving parts at 12 frames or less; smoke is the only extra).
- Depends on unverified: weather source, hiding the status bar, tap areas over a picture, 12 hand-drawn icons per look.

### 2 Clockmaker's bench
- One background picture: wood bench, felt mat, lamp shade, tray with compartments.
- Live: watch hands and the small seconds hand (the watch dial could be its own picture so hands sit exactly), candle flame and its height, lamp glow at night, rain in the loupe. Agenda and weather text live.
- Apps: 12 small tool pictures in a tray; very forgiving for tap areas because each has its own compartment box.
- Effort: MEDIUM (simplest surfaces: flat wood and felt, one big focal object). Battery: LOW (hands plus a flame).
- Depends on unverified: same four things as above. Wood grain can be a repeated tile, which keeps the app small.

### 3 Apothecary cabinet
- One background picture: cabinet, shelves, drawer fronts, label holder, bell jar glass, mantel clock case.
- Live: clock hands and pendulum, candle flame and night glow, rain in the bell jar, vial level for battery. Text live.
- Apps: 8 jars/bottles plus 4 drawers; each is a tap box on the shelf. The jars could later show a tiny badge (for example a count of messages) without redrawing anything.
- Effort: MEDIUM to HARD (8 different glass vessels with contents to draw, but the layout is a plain grid). Battery: LOW.
- Depends on unverified: same four things; glass looks best as real pictures with see-through parts, which need a check on the phone.

## Readability (WCAG AA needs 4.5 to 1 for normal text; all passed by a wide margin)
Date plate 7.0, clock numerals 11.0 to 14.0, agenda ink 9.2 to 13.5, app labels 14.8, weather plates 7.0, battery number on dark 13.2 (all to 1). Measured from the colours in the sketch. Not measured: text over the glowing night effects (the glow sits behind the plates, so it should not change these numbers) and real phone brightness. App labels sit on a light plaque in all three so they stay readable on any scene.

## Questions for the owner (pick a letter)
1. Which scene do you want to go on with?  A) Steampunk  B) Clockmaker's bench  C) Apothecary cabinet  D) Mix (say which parts)  E) None, try another idea.
   Recommendation: B for effort and calm; A if the wow factor matters most.
2. How many Home apps drawn as objects?  A) 8 (bigger icons, more room)  B) 12 as shown  C) 6 plus a button for the rest.
3. Battery and date in the corners as shown (object plus number)?  A) Yes  B) Number only, no object  C) Hide the date.
4. How much should the scene move?  A) Hands and glow only  B) Also a small weather effect in one area (as sketched)  C) Nothing moves (cheapest on battery).

## NOT verified
- Nothing was run on the phone; these are pictures from a web page. Sizes are right for 1080x2400 but real text sharpness and colours on the AMOLED screen are unchecked.
- Whether the Samsung status bar can be hidden and the punch-hole kept clear (sketches assume yes).
- Whether tap areas can be laid exactly over one big background picture on different font and display sizes.
- Weather: no source chosen; the owner has not said yes to location or cost. Sketch weather is fake.
- Day/night timing and the automatic matching shape for apps beyond the 12 are not designed.
- A real handwriting font for the agenda was not added (only OFL fonts already in the repo were used).
- Looks are inspired only; no brand logos or names were used. Sellable art: noted in `ops/monetization.md`.
