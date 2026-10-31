# Three new themes (D-21 / D-22): preview pictures only

No app code was written. Pictures are 1080x2400 (Galaxy S20 FE portrait). The tiny black dot at the top is the camera hole; the clock sits left of it and the battery right of it.

## Files
Home screens (each has an .html and a .png):
- `home-cottage-dark` / `home-cottage-light`: cozy cottage. Weather is a window with curtains; the calendar is a stitched notebook page; icons are stitched patches.
- `home-cockpit-dark` / `home-cockpit-light`: sci-fi cockpit. Weather is a radar scope with readouts; the calendar is an "agenda" panel; icons are cut-corner frames. The light one is the bright "lab / daylight" version.
- `home-minimal-dark` / `home-minimal-light`: clean minimal. Big clock, weather is a small ring with a sun glyph; the next task is shown as big type; icons are bare thin glyphs.
- `compare-all.png` (+ .html): all three dark Homes on top, all three light Homes below.
- `swatches-cottage`, `swatches-cockpit`, `swatches-minimal` (.html + .png): colours with hex values (dark and light), 4 accent candidates, recommended alert colour, font names.
- `fonts/`: the fonts and their licence texts (all SIL Open Font License 1.1, may be bundled): Caveat, Patrick Hand (cottage); Share Tech Mono, Rajdhani (cockpit); Manrope (minimal). Each has its own `OFL-*.txt`.

Every Home shows the same parts: our own clock and battery in the top corners, one weather element, one calendar / next-task widget, 8 generic app icons, and the OPTIONAL extras (a folder, Power Full/Min/Off, Dark/light, Auto-rotate). All text is made-up placeholder text. Weather art is placeholder.

## Contrast (WCAG AA, text needs 4.5 or more)
I computed every text/background pair used (main text, muted text, accent text, alert text, text on tiles, text on a switched-on switch) for all 6 Homes. All pass; the lowest is 5.09 (cottage light alert on paper). All 12 accent candidates pass against their background (lowest 4.8, cockpit green in light mode). This fixes the faint light glyphs of the brass round: light-mode glyphs are now dark ink or a deep accent.

## Questions for the owner
1. Which theme do you want? (A) Cozy cottage, (B) Sci-fi cockpit, (C) Clean minimal, (D) none, show me different ones. My lean: pick by looking at `compare-all.png`; I have no favourite to push.
2. The optional extras (folder + three switches): (A) show them on the Home as in the pictures, (B) hide them from the Home and keep them in settings, (C) show only the folder.
3. Accent colour for your theme (see the swatch page; the pictures use the first): cottage Honey / Sage / Rose / Sky; cockpit Cyan / Green / Amber / Magenta; minimal Blue / Coral / Green / Violet.
4. Clock: your brief says ONE clock in the top corner. I made it bigger in the minimal theme (big type) and smaller in the other two. Is that fine, or do you want the same size in all themes? (A) fine as drawn, (B) same size everywhere.

## NOT verified
- Nothing ran on the phone; these are web-page pictures. Real fonts, sizes, the real punch-hole position and Samsung's hidden bar were not checked on the S20 FE. Text size on the phone may differ if you use large-text settings.
- Whether Samsung Weather can be opened from a tap (as an app shortcut) is assumed, not tested.
- The radar sweep and any motion are not shown; motion would follow the battery rules in CLAUDE.md.
- The "Power" and "Auto-rotate" switches are drawn only; their real behaviour is not built.
- I looked at the pictures myself and fixed overlaps, but small differences from a real phone screen are possible.
- Contrast was computed from the colour values, not measured on the phone screen.
