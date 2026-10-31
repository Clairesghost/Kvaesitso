# Design previews, batch 1 (portrait Home)

Phone: Galaxy S20 FE, 1080x2400, small punch-hole at top centre. Inspired only by the owner's photo, nothing copied. All art is placeholder (generic glyphs, fake text, no real numbers or data). Each picture has an HTML file with the same name; the PNGs are the pictures to look at.

## Files
| File | What it shows |
|---|---|
| `home-dark-A.png` / `.html` | Home, dark look, font set A (full phone size 1080x2400) |
| `home-light-A.png` / `.html` | Home, light (cream) look, font set A |
| `home-dark-B.png` / `.html` | Home, dark look, font set B |
| `fonts-specimen.png` / `.html` | Both font sets side by side, with names and licences |
| `folders-options.png` / `.html` | Folder option 1 (3 loose apps + 3 brass drawers), one drawer opened, and option 2 (drawers only) |
| `weather-lens-states.png` / `.html` | The weather lens: sun glare and rain beads, dark and light |
| `swatches.png` / `.html` | Palettes with hex values, 4 accent colours, alert colours, contrast ratios |
| `fonts/` | Font files and their licence files (SIL OFL 1.1): Limelight, Lato, Josefin Sans, Source Sans 3 |

## Layout shown (top to bottom)
Own clock and battery in the top corners (clear of the punch-hole), round weather lens (tap opens Samsung Weather), ONE big brass clock, ONE calendar / next-task panel, apps and drawers, three flat brass switches (Motion Full/Min/Off, Look Dark/Light, Rotate which follows the phone's auto-rotate).

## Fonts
- Set A: Limelight (art-deco headings) + Lato (body). Both SIL OFL 1.1.
- Set B: Josefin Sans (geometric deco headings, calmer) + Source Sans 3 (body). Both SIL OFL 1.1.

## What would animate later (not in the pictures)
Clock hands (tick once a second at most, only while Home is on screen), lens weather (slow glare or drip, 12 frames per second or less), switch press (knob slides on press, resets on resume). All follow the Battery and motion checklist in CLAUDE.md and the Motion switch (Full / Minimal / Off).

## Questions for the owner
1. Which font set: A (bolder, more art-deco, more "retro") or B (cleaner, calmer)?
2. Which folder option: 1 (3 favourite apps stay loose, 3 brass drawers) or 2 (only drawers)?
3. Which accent colour (used for small highlights such as a switched-on state): Verdigris, Copper coral, Amber glow or Sky glass? (Recommended: Verdigris.)
4. Alert colour (D-2.3): vermilion (recommended), rose red or amber-red? And is the Home calm enough, or should anything be removed?

## NOT verified
- Never seen on the real phone; sizes are drawn at 360x800 dp scaled 3x, real S20 FE insets, gesture bar and rounded corners not checked.
- Fonts rendered in desktop Edge only. Limelight has no true lowercase look and may need checking at small sizes; Lato file comes from the Google Fonts repository (version not checked against the Lato 2.0 release). Licence texts were downloaded as-is; copyright lines not reviewed.
- Weather lens, calendar and tasks content are placeholders. Whether Google Calendar/Tasks and Samsung Weather can be hosted or themed is unverified (D-16, D-19).
- Hiding the Samsung status bar and the battery number: unverified (AF-19).
- Contrast ratios are calculated (WCAG formula), not tested on the screen outdoors.
- The light-look lens sheet shares one gradient definition per page, so a light lens next to a dark one may use dark-look brass shades; the full Home pictures are correct.
- Landscape is not designed yet (D-10, later batch). Monetization rows not added (nothing sellable marked, D-4 says not for sale). Nothing committed or pushed.
