# <Project name> design system

Copy to `docs/design-system.md`. The guide fills every blank from the owner's decisions (`ops/decisions.md`, D-n) and the previews the owner chose. Until a blank is filled it says TBD; threads must not invent values. Mark sellable parts [M] (see `ops/monetization.md`).

## Look
- One-line mood (D-n): TBD
- Inspired by (only inspired: never copy logos, art, text or names): TBD
- Overall feel words (3): TBD

## Palette roles (write the hex value for each role; pick from the owner's choice)
| Role | Meaning | Value |
|---|---|---|
| Ground | main background | TBD |
| Panel top / bottom | panel gradient ends | TBD |
| Line | thin frames and dividers | TBD |
| Accent | main highlight colour | TBD |
| Accent deep / dark | pressed and darker versions | TBD |
| Danger / alert | badges, warnings | TBD |
| Text | normal text | TBD |
| Text bright | headings | TBD |
| Text dim | hints and placeholders | TBD |
| Glow | ambient light or effect colour | TBD |
Rules: never pure white on dark ground (unless the owner chose it); check contrast at 10 sp for small caps; a colour-scheme setting, if the owner wants one, swaps this table only.

## Fonts (licence file for each goes in `docs/licenses/fonts`)
| Use | Font | Licence | Notes |
|---|---|---|---|
| Headings | TBD | TBD | |
| Labels / small caps | TBD | TBD | |
| Body | TBD | TBD | |
| Numbers / clock | TBD | TBD | |

## Motifs
- Panel style (frame, corners sharp or round, background): TBD
- Ribbon / header strip style: TBD
- Emblem / icon style (line art, filled, letters in shapes): TBD [M]
- Dividers and ornaments: TBD
- Hero element (orb, clock, mascot, none): TBD [M]
- Texture over everything (grain, vignette, none): TBD

## Rules for any new screen
1. Reuse the pieces above; invent a new motif only after asking (multiple choice, with previews).
2. Layout: Home is drawn on a stage of TBD x TBD units scaled to the screen, inside the system bars and the camera cut-out. Sizes in stage units.
3. Motion (hard rules; see the checklist in `CLAUDE.md`): animate only while resumed, screen on, Battery Saver off, battery above 15% or charging, remove-animations off, and the owner's Animations setting allows it. Small regions; ambient effects at 12 fps or less; at most two at once; reaction effects start on press and always reset.
4. Taps: launch about 120 ms after the press effect; instantly when animations are off.
5. Text colours and sizes: TBD (minimum size, contrast rule).
6. Never show invented personal data; placeholders for money and contacts until the owner connects real data. Never log notification text or coordinates.
7. Every phone-only fact goes to `ops/api-facts.md`.

## What the launcher can and cannot style
Can (we draw it): Home, the typing layer and search results, launcher settings, widget page, sheets and dialogs, app picker, icon shapes and an icon pack, a wallpaper the launcher sets.
Cannot (stock system): the pull-down quick panel, lock screen, always-on display, other apps' insides, the system navigation bar. Partly reachable with the phone maker's theme apps or a keyboard theme: we produce the pictures and steps, the owner applies them.

## Screen inventory (status: not started / preview / built / owner verified)
| Screen | Status | Decision ids |
|---|---|---|
| Home portrait | not started | |
| Home landscape | not started | |
| Typing layer and search results | not started | |
| App list / apps page | not started | |
| Launcher settings | not started | |
| Widget / feed page | not started | |
| Sheets, dialogs, item cards | not started | |
| Wallpaper | not started | |
| Lock screen picture | not started | |
| Always-on display picture | not started | |
| Icon pack | not started | |
| Keyboard picture / theme | not started | |
