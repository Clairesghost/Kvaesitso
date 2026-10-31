# Things to check on the phone (running list)

Collected from the mock-up comments. When a build exists, these go into `tasks/<id>/testlist.md` in plain steps. Nothing here is verified yet.

## Taps and what they open
- [ ] Bluetooth coin: tap it. Expected: the full Bluetooth settings page opens (Android has no quick pop-up for this, AF-8). Report: what opened. Also check the coin is grey when nothing is connected and dark when earbuds are connected, with no red dot. (D-122)
- [ ] Wi-Fi / mobile coin: tap it. Expected: Wi-Fi panel or settings page (AF-8). (D-97)
- [ ] Weather corner opens the weather app you chose; the date/schedule area opens Calendar; the Tasks / Notes title opens the matching app. Samsung app names written from memory are unverified (AF-11). (D-121)

## Notifications
- [ ] Alerts block shows real notifications after notification access is granted (AF-1). Sender names, preview on/off per app, Reply / Archive / View / Open buttons, ✕ to dismiss.
- [ ] Swipe to clear a row (not built in the mock-up yet). Ongoing notifications (music, calls, timers) must NOT be clearable.

## Size and feel
- [ ] Tap sizes under 48 dp: status coins (28 px), Alerts rows (26 px), chevron.
- [ ] Script font (Oleo Script Swash Caps) readable at arm's length: event title, Alerts, Today sheet, search bar, task lines.
- [ ] Number card on the clock reads the right 12-hour time.
- [ ] Weather corner colour and motion only while the launcher is on screen, Battery Saver off, Motion Full. Check Settings > Battery after a day and report the number.
- [ ] Camera hole lines up with the camera button (AF-3 unmeasured).
