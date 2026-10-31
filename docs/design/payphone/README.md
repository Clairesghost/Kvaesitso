# Payphone handheld: round 3 previews (D-36, D-37, D-39, D-35)

Pictures only. No Kotlin, nothing committed, nothing pushed, nothing built. Phone: Galaxy S20 FE, 1080 x 2400, punch-hole top centre (kept clear: the date plate sits left of it, the battery plate right of it). All text is placeholder (fake agenda, fake weather 17 degrees, fake battery 78%, fake date).

"Inspired by only": the owner's 2nd photo (red retro payphone phone case). Nothing copied: no "1957 / Public Telephone / Local Call" text, no 1:1 layout, no art. The generic names, the seals, the clock and the chrome parts are drawn fresh. The word "PINNED" on the favourites tray is my placeholder wording: please confirm or change it.
Note: `docs/design-system.md` does not exist in this workspace (only a template), so the colours come from round 2 (`cabinet-handheld`) and the D-decisions.

## Files (open the PNG; the HTML with the same name is the source)
| What | File |
|---|---|
| 1 Red lacquer and chrome, day | `home-red-day.png` |
| 1 Red lacquer and chrome, night (lens and placard glow) | `home-red-night.png` |
| 2 Forest green and ivory | `home-green.png` |
| 3 Midnight blue and copper | `home-blue.png` |
| 4 Cream and brass, for daytime | `home-cream.png` |
| Spinning dial in four stills, plus apps beyond 12 | `spin-storyboard.png` |
| Weather lens/slot: sun, rain, snow, clear night, one motion each | `weather-states.png` |
| Everything side by side | `compare.png` |
| Fonts (Limelight, Josefin Sans, Lato; all SIL OFL, licence texts next to them) | `fonts/` |

## How the screen is built (top to bottom, one handheld object)
1. Date plate (top left) and battery gauge (top right). Real Samsung bar hidden.
2. Coin-slot strip = WEATHER: glass bubble lens at the left, the slot, and a dark plate with temperature and words. Tap would open the weather app (D-19).
3. Rotary dial = HOME: 12 wax seals sit in the 12 finger holes, each with its plain name (Phone, Messages, Camera, Web, Music, Maps, Mail, Notes, Photos, Calendar, Calc, Settings). The centre disc is the ONE clock (no digital time). A chrome finger stop sits at the lower right like a real dial.
4. Framed placard = AGENDA (three fake lines).
5. "Pinned" tray = the 3 favourites, outside the ring, they never move (proposed: Phone, Messages, Camera).
6. Coin-return flap at the bottom: decoration only for now (a button and a slot), keeps the body whole. [M]
Extras (switches, folders) are not on Home.

## Colour schemes (D-35). Hex values and contrast (WCAG AA needs 4.5 to 1 for text)
| | 1 Red day | 1 Red night | 2 Forest green | 3 Midnight blue | 4 Cream and brass |
|---|---|---|---|---|---|
| Body top / mid / bottom | #d9443a / #b3201f / #7a1315 | #92242a / #6e1216 / #3d080c | #4a7160 / #2c5a46 / #1a362a | #3d5690 / #243660 / #0f1a33 | #fdf8e8 / #efe4c8 / #cdbf9a |
| Metal light / mid / dark | #f7f9fa / #b4bcc3 / #59626a (chrome) | #c9d1d8 / #8b939a / #3a4148 | #f4dc9a / #c99b3e / #6f4c14 (brass) | #f6c0a0 / #c4713f / #6d3418 (copper) | #f6e3a8 / #b88a2f / #6a4a12 (brass) |
| Dial ring / dial hole | #18171a / #07070a | #0d0c0f / #040405 | #0f1a14 / #060c09 | #0a1124 / #04070f | #3a2e22 / #1d1610 |
| Paper (placard, date, name tabs) / ink | #f4ead0 / #1f1814 | #d9cdac / #1f1814 | #f3ecd6 / #1a1810 | #f1e8dc / #16130f | #fffaf0 / #2a2118 |
| "TODAY" heading colour | #9a1b1b | #8a1a1a | #8f2f22 | #9a4a22 | #8a3b1c |
| Dark plate (weather, battery, tray) / its text | #141416 / #f3e9cf | #0a0a0c / #e8dcc0 | #0f1a14 / #f3ecd6 | #0a1124 / #f6dcc8 | #3a2e22 / #fbf1d6 |
| Ink on paper (agenda, date, names) | 14.6 | 11.1 | 15.0 | 15.3 | 15.2 |
| "TODAY" on paper | 6.9 | 5.9 | 6.8 | 5.1 | 7.4 |
| Weather and battery text on dark plate | 15.2 | 14.5 | 15.1 | 14.3 | 11.7 |
Seal wax colours: red scheme #d94a3a #e0a23a #3f8f95 #6f9bd0 #86a14a #a876b5 (night = 12% darker); green #a8412f #c18a2a #3f7f82 #6b4a7a #7d8f3e #b86a4a; blue #c9653d #d9a441 #4c8f9a #8a5d9c #6f9a5a #c9536b; cream #b5452f #c28a1f #3a7d80 #6b4a7a #6f8a35 #b86a4a.
All text pairs pass AA (numbers computed from the colours; the seal pictures themselves are decoration, the name tab carries the meaning). Not measured on the real AMOLED screen. Effort per extra scheme: MEDIUM (colours only, same layout, but a setting plus tested colours). Battery: LOW. [M] each scheme is a sellable theme.

## Spinning dial (D-39), see `spin-storyboard.png`
Four stills: (1) at rest, (2) finger drag with a trail, (3) let go: momentum with ghost seals sliding, (4) tap on a hole, pressed state with a gold ring. Labels in the picture: "tap opens the app", "drag spins", "pinned favourites stay". Seals stay upright while the ring turns.

Apps beyond the first 12 (up to about 24), three ways in the picture:
- A (recommended, simplest): one ring with a belt of up to 24 apps. The 12 holes always show 12 seals. Each notch moves everything one hole along; the seal that passes the finger stop is swapped for the next app on the belt, out of sight. No second screen, seals stay big, one new thing to code.
- B: two rings. Inner seals would be about half size and crowd the clock. Not recommended.
- C: a second page by sideways swipe. The swipe fights the launcher's own swipes (AF-22). Not recommended.

## Weather (D-37), see `weather-states.png`
Four states, one small motion each: sun (glare band crosses once, about 8 s, then rests), rain (five beads slide slowly), snow (about a dozen flakes drift), clear night (three stars twinkle one at a time; moon and rim glow still). All: only the lens area (about 240 x 240 px), at most 12 frames per second, under the Motion setting (Full / Minimal / Off), only while Home is on screen, screen on, Battery Saver off, battery above 15% or charging.

## Honest build notes (plain words)
| Part | Effort | Battery | Notes |
|---|---|---|---|
| Static Home body, strip, placard, pinned tray, clock, date, battery | MEDIUM | LOW | New Home component with drawn pictures; colours from a scheme setting |
| Colour scheme setting (up to 4) | MEDIUM, then EASY per extra scheme | LOW | Same layout, colours swapped |
| Weather lens as pre-drawn pictures swapped by weather + one small slow effect | MEDIUM | LOW | Recommended. Weather data needs the owner's yes (location, cost, privacy) |
| Real bending-glass lens | HARD | MED | Needs AGSL `RuntimeShader`: UNVERIFIED on this phone (AF-16: not used, not tested). Probe first |
| SPINNING DIAL | HARD | LOW to MED (only while turning) | New Home component. Taps and drags must be consumed by the ring so the scaffold's swipe gestures and the default double-tap SCREEN LOCK do not fire (AF-22): UNVERIFIED on the phone, probe first. Seals are moved by position (angle) and kept upright. The fling stops by itself (decay animation). Own launches must call the launch counter (AF-23, `reportLaunch`). Animate only while Home is resumed |
| Belt of up to 24 (option A) | MEDIUM on top of the dial | LOW | Just an offset into the app list |
| Click sound/feel at each notch | EASY | LOW | Small haptic tick; owner to say if wanted |

## Questions for the owner (pick a number or letter)
1. Which colour schemes to ship in the setting?  A) All four (recommended; red as the default)  B) Red + cream (one dark, one light)  C) Red + green + blue  D) Other (tell me).
   Effort: A MEDIUM; B/C a little less. Battery: LOW.
2. Apps beyond 12?  A) One ring with a belt of up to 24 (recommended)  B) Two rings  C) Second page  D) Only 12, no more.
   Effort: A MEDIUM, B HARD, C MEDIUM to HARD, D none.
3. Which 3 pinned favourites?  A) Phone, Messages, Camera (shown)  B) Phone, Camera, Web  C) Tell me three.
   Effort: EASY. And is the word "PINNED" on the tray OK, or no word at all?
4. Calm enough, and the battery?  A) Yes as shown (battery top right, coin-return flap is decoration)  B) Calmer: drop the coin-return flap and give the dial more room  C) Put the battery gauge in the coin-return flap instead of the top corner.
   Effort: EASY. Battery: LOW.

## NOT verified
- Nothing ran on the phone; these are web-page pictures (headless Edge). Real colours on the AMOLED screen, text sharpness and the punch-hole position are unchecked.
- Whether the dial can swallow taps and drags so the launcher's swipes and the double-tap screen lock stay quiet (AF-22), and whether turning feels smooth on the phone.
- Whether a status bar hidden on Home leaves the punch-hole clear (AF-18 says the bar can be hidden; not tried).
- AGSL lens (AF-16), the weather source, every motion (nothing is animated in these pictures), the click feel.
- Contrast numbers are from the flat colours, not measured on the real screen; the night glow was judged by eye.
- Lock screen, always-on display, status and navigation bars are outside the launcher and are not restyled by this.
