# Vintage Pocket Dial design system

Source of truth for how every screen looks. Built from the owner's decisions (`ops/decisions.md`, D-n) and the approved mock-up `docs/design/refined/dial-mockup-cute.html` (published as the "Cute Vintage Phone" artifact). If this file and the mock-up disagree, ask the owner. Nothing in here is verified on the phone yet.

## Look
- Mood (D-72, D-77): a cute 1950s pay-phone. Cherry lacquer, chrome, ivory rotary dial, tan placards. Polished and a little playful.
- Inspired by (only inspired): a red 1957 pay-phone (owner's reference photo). No logos, art or names are copied.
- Feel words: cherry, chrome, playful.
- Name: **Vintage Pocket Dial** (D-153). Launcher icon: a cherry square with the round dial and clock (icon option A, D-157).

## Two looks: Day and Night (D-144, D-146)
Setting "Day or night look": Auto, Day, Night (Auto follows the time of day for now; later the phone's dark mode). Only the table below changes between looks.

## Palette roles
| Role | Meaning | Day | Night (black lacquer edition) |
|---|---|---|---|
| Ground | phone body | cherry gradient `#c52a35` → `#a01a24` → `#6b0c15` | black gradient `#303034` → `#18181b` → `#0b0b0d` |
| Dial plate | dark band the discs sit on | `#5a0f16` → `#2a0509` | `#3a1219` → `#1f0b10` (not dimmed) |
| Centre band | round red area round the clock | `#c52a35` → `#8d141d` | `#c0222d` → `#7a1019`, shine capped |
| Chrome | rims, housing, bezels | `#fdfdfb`, `#b9bbbd`, `#e6e6e2`; frames `#dcdedf` + `#7d7f82` | same, top housing about 22% darker (brightness .78) |
| Disc | app discs | ivory `#fffaf0` → `#e6d7b6`, rim `#8d141d` | same |
| Card (placard) | the three cards | tan `#f2edd2` → `#e4dcbb`, 3 px black frame, chrome bezel | near-black `#232226` → `#141416` with a faint top sheen, same chrome bezel |
| Sheet | Today, app list, settings, editor, pop-ups | cream `#fbf4e3` → `#f1e6cc` | `#1c1c1f` → `#111113` with a chrome rim |
| Accent | headings, active toggles | cherry `#8d141d` | coral `#ff6a5f` |
| Alert badge | counts on discs | `#d62b37`, ivory rim (kept red, D-128) | same |
| Text | normal | `#15110f` on tan, `#3a0d12` on cream | `#f1ece0` |
| Text dim | hints | `#7a4a43` | `#cfc6b4`, `#a8a49b` for placeholders |
Rules: never pure white text on dark; keep text contrast at least 4.5 to 1 (3 to 1 for large script); the weather corner colours get lighter versions at night.

## Fonts
| Use | Font | Licence | Notes |
|---|---|---|---|
| Headings (event title, date number, Alerts, Tasks, Today, Apps, Settings, timer, setup titles, search bar text) | Oleo Script Swash Caps 700 (D-111, D-112) | SIL OFL (Google Fonts) | script only for big words; keep it at 15 px or larger where possible |
| Everything else (body, times, buttons, rows, task lines, notes) | Libre Franklin 400, 500, 600, 700 | SIL OFL (Google Fonts) | sender names weight 500 (D-118) |
| Numbers | Libre Franklin (clock number card 8 px bold; date number in the heading font) | | |
Licence files must go in `docs/licenses/fonts` when the fonts are bundled (the app must not load them from the web).

## Motifs
- Panel: black 3 px frame, 6 px corners, chrome bezel (2 px `#dcdedf` + 1.5 px `#7d7f82`), soft drop shadow.
- Dial: outer chrome rim only 2 px (D-133); dark plate; thin ivory ring round the red centre band (D-132); 12 flat ivory discs with cherry rims and bold single-colour cherry pictograms about 25% larger than the first draft (D-128); static tick marks that do NOT turn with the ring (D-142); chrome finger stop at the bottom right with a soft cream lamp glow at night only (D-151).
- Clock: ivory face, cherry hands, no date window (D-124), small tan number card showing the 12-hour time (D-119). Tap: quick timer pop-up (D-140). Hold: opens the Clock app (D-139).
- Top housing: one chrome plate with the search slot on the left (placeholder "Dial, find, sum, jot…"), the camera button in the middle (a ring, no black outline) and three coin gauges on the right: connection, Bluetooth, battery (D-102 to D-104). Bluetooth icon grey when nothing is connected, no red dot (D-122). Battery ring plain dark (D-125). No camera status light (D-126).
- Cards under the dial, top to bottom: Calendar (date tab with weather icon and temperature + event title + time + "Then…" line); Alerts (script title, latest 2 notifications, outlined action button, ✕, "+N more ›" opens the Today sheet); Tasks / Notes (title + "Notes ›" switch + dotted rows) (D-107, D-115). Rows are separated with dotted lines. No dotted underline under titles (D-152).
- Dividers: dotted 1 px lines at 25–35% opacity.
- Badges: red, ivory rim, white number.
- Texture: none.

## Layout (stage 360 x 780 units, scaled to the screen width)
Top housing 8–46; dial 60–364; calendar 384–488; Alerts 496–574; Tasks 582–704; chevron 706–750. Inside the system bars and the camera cut-out. All sizes in stage units; they are dp at 360 dp wide.

## Rules for any new screen
1. Reuse the pieces above; invent a new motif only after asking (multiple choice, with previews).
2. Sheets fill the screen below the housing (left/right 6, top 54, bottom 6), script title top left, ✕ or Done top right, scroll inside.
3. Motion (hard rules; see `CLAUDE.md`): animate only while the launcher is resumed and visible, screen on, Battery Saver off, battery above 15% or charging, phone's remove-animations off, and the Motion setting (Full / Minimal / Off) not Off. At most 2 ambient effects at once; ambient effects at 12 frames per second or less. Today the only ambient effect is the small weather icon motion. No timers or polling when not visible (the ring-returns-home timer fires once, only while visible).
4. Taps: launch about 120 ms after the press effect; instantly when animations are off. Dial spin: flick with friction, snap to a slot; the ring returns home 5 s after it stops, if that setting is on (D-126).
5. Text: nothing under 11 sp where avoidable; the owner will judge tap sizes at practical testing (D-126). Tap areas under 48 dp are listed in `ops/phone-checks.md`.
6. Never show invented personal data in the real app: demo weather, events, notifications and "Leave by" stay in the mock-up only (D-153). Never log notification text, contacts or coordinates.
7. Every phone-only fact goes to `ops/api-facts.md`.

## What the launcher can and cannot style (AF-24, AF-8)
Can (we draw it): Home, search results, app list, settings, sheets, dialogs, dial editor, first-run setup, icon.
Cannot: Android's pull-down quick panel and notification shade (stays Android's own, D-137), the lock screen (dropped, D-156), always-on display, other apps' insides, the system navigation bar. Wi-Fi, Bluetooth, location, airplane and battery saver can only open their settings pages.

## Screen inventory
| Screen | Status | Decision ids |
|---|---|---|
| Home portrait, Day and Night | preview approved in mock-up | D-72 to D-152 |
| Home landscape | not started (ask) | |
| Search results | text chips only in the mock-up; icon list not drawn | D-84 |
| App list (A to Z, letter strip, Find an app) | preview | D-135 |
| Launcher settings (motion, alerts, dial, look, how to go back) | preview | D-138 |
| Dial editor (drag discs on a mini dial) | preview | D-141 |
| Today sheet (notifications + coming up) | preview | D-79, D-108 |
| Quick timer pop-up | preview | D-140 |
| First-run setup (4 steps) | preview | D-154 |
| Pull-down quick panel | not ours (Android) | D-137 |
| Wallpaper | not started (offer) | |
| Lock screen picture | dropped | D-156 |
| Icon | option A chosen, artwork to produce | D-157 |

## Open choices
- Weather source for the real weather corner and "Rain in 20 min": a free service sending approximate location, or only open the Samsung Weather app. Needs the owner's answer (cost, privacy).
- Swipe to clear on the Alerts block (not built in the mock-up).
- Night colour tweaks the owner has not seen on a real phone.
