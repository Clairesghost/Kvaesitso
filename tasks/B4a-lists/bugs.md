# Bugs: B4a-lists

## Bug 1: lists in the sheets do not scroll (owner, phone, 2026-11-01)
- Seen: in the dial editor ("Put on this disc") the app list shows about 6 rows and does not move with a finger. The owner's screenshot shows the editor, not the app list (the editor list has no A to Z strip by design). Main app list and Settings use the same sheet, so they are likely hit too.
- Root cause (read, not proven on the phone): `VpdSheet` had a pointerInput that consumed EVERY pointer change (down and moves) to stop touches reaching Home. The lists' scroll detection lost the drag to it.
- Fix 1 (guide, 2026-11-01): the swallow is replaced by a tap/double-tap/long-press catcher plus two do-nothing `scrollable`s (vertical, horizontal) on the sheet. Lists scroll first; leftover drags and empty-area taps end at the sheet, so the launcher frame (page swipes, double-tap lock) behind it still never reacts.
- Check on the phone: app list, Settings and the editor's list all scroll; a double tap on an empty part of a sheet does NOT lock the screen; a swipe down at the top of a list does NOT open another page.
