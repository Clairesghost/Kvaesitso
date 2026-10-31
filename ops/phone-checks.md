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

## App list (D-135)
- [ ] Tap the chevron: the app list opens. Letter strip jumps to a letter; typing in "Find an app" filters; tapping an app opens it. Report: any app missing or in the wrong order.
- [ ] Letter strip letters are about 18 px apart, below 48 dp: check they are easy to hit with a thumb.
- [ ] Apps with no drawn icon show a letter disc; check how many of your real apps get one.

## Pull-down and shade (AF-24)
- [ ] With the status bar hidden: swipe down from the top edge. Report how many swipes before Android's pull-down opens.
- [ ] Probe button: call the shade-open reflection and report whether the pull-down opens (AF-24).

## Settings page (D-138)
- [ ] Open Apps, tap "Launcher settings". Change Motion, a preview toggle and "Ring returns home", then close the launcher completely and reopen it: the choices should still be there. (The mock-up does not save them; the real app must.)
- [ ] "Go back to your old launcher": follow the 3 steps on the phone and report the real menu names. Then switch back to this launcher.
- [ ] Notification access: the row shows the real state (granted or not).

## Clock press (D-139)
- [ ] Hold the clock face for half a second: the phone's Clock / alarm app opens (Samsung app name unverified, AF-11). A short tap does nothing yet.
- [ ] Spinning the ring still works when the finger starts outside the clock.

## Quick timer (D-140)
- [ ] Tap the clock face: the timer pop-up opens. Pick 5 min: the phone's Clock app should start a 5 minute timer (Android's standard timer request; Samsung behaviour unverified). Report whether it started and whether the Clock app screen opened.
- [ ] "Other" opens the Clock app's timer screen.

## Dial editor (D-141)
- [ ] Settings > "Choose the 12 apps": drag one disc onto another with your finger. They swap, and the real dial matches. Report if the drag feels fiddly or a disc drops in the wrong slot.
- [ ] Tap a disc, pick a replacement app: it appears on the dial. Close and reopen the launcher: your dial should be remembered (the mock-up does not save it; the real app must).
- [ ] Hold a disc on the dial for half a second: the editor opens with that app selected. A short tap must still open the app. A spin must still spin.

## Night look (D-144)
- [ ] Set Look to Night, then Day, then Auto (Settings > Day or night look). Check at night and in bright daylight that text on the dark cards is easy to read and the dimmed dial is not too dark.
- [ ] Check the choice is remembered after closing the launcher. Later: Auto should follow the phone's own dark mode.
- [ ] Battery: dark cards use less power on your screen (OLED); report Settings > Battery after a day on Night.

## First-run setup (D-154)
- [ ] Fresh install: the 4 setup screens appear once. "Open Android's screen" opens the notification access list; after switching it on, come back and the Alerts block fills.
- [ ] "Skip" on every optional step works; nothing is asked twice. Setup does not come back after closing the launcher.
- [ ] "Choose home app" opens Android's chooser and the launcher becomes the home app. Press Home once to confirm.
- [ ] Weather location: only asks if you tap Allow; say what Android shows you.

## Swipe to clear (D-160)
- [ ] Drag an Alerts row sideways: it slides away and the notification is cleared in Android too. A short drag springs back.
- [ ] An ongoing one (music player, call, timer) refuses: no ✕, it springs back with a message. "Clear all" leaves it.
- [ ] Scrolling the page / spinning the ring is not triggered by a sideways drag on a row.

## Landscape (D-166)
- [ ] Turn the phone sideways (with auto-rotate on): the landscape home appears. Report which edge the camera hole is on, and whether the housing, cards or dial touch it.
- [ ] Spin the ring and tap an app in landscape; check the cards on both sides are readable and tappable.
- [ ] "Next days" forecast shows real data once the weather service is wired; before that it must not show made-up numbers (D-153).

## App list categories and letter strip (D-168)
- [ ] Open the app list. Count how many of your real apps land in "Other" (Android only tags some apps). Report the number and a few examples.
- [ ] Hold an app for half a second: the "Move to" menu opens. Move one app, close the launcher completely, reopen: it should still be in the new group.
- [ ] Slide a thumb down the letter strip: the big letter bubble follows and the list jumps. Check the strip is easy to hit (letters are about 14 px apart, below 48 dp).

## App list grid (D-169)
- [ ] The app list opens as category tiles. Tap a tile: that group appears as the A to Z list. The header button switches between grid and list. Say which you prefer to start in.
- [ ] Each tile's 2 x 2 peek shows the first four apps of the group; check the picks make sense for your real apps.

## App list, layout A (D-171)
- [ ] Open the app list: apps sit in an icon grid under category headings (4 per row; 6 sideways). Slide the right-hand strip: the list jumps to the first app with that letter and every app with that letter glows for a moment. Report how it feels with a thumb and whether the letters are easy to hit.
- [ ] Hold an app: the Move menu opens; a moved app stays moved after restarting the launcher.

## Search results (D-172)
- [ ] Type "12*4.5", "20% of 85", "5 mi", "70 f": a big answer card appears. Copy puts the answer on the clipboard.
- [ ] Type part of an app name: icons appear; tap one opens it. Type a contact's name: Call and Message buttons act on the right person (needs the contacts permission; ask first, privacy).
- [ ] Type any words: Add as task / Add as note put it in the Tasks / Notes card, and it is still there after restarting the launcher.
- [ ] The keyboard must not hide the result panel (the panel is near the top, check on the phone).

## Empty Alerts shows the forecast (D-173b)
- [ ] With no notifications, the Alerts block says "all clear" and shows a 5-day strip. Before the weather service is wired it must not show made-up numbers: show only "all clear".

## Dial click feel (D-174)
- [ ] Settings > Dial > Click feel: spin the ring slowly. Light and Firm should feel clearly different; Off is silent. Report which you prefer.
- [ ] Click sound On: a soft click per notch. Check it follows the phone's volume and does not play when the phone is on silent.
- [ ] Battery: spinning is the only time it runs; check Settings > Battery after a day.

## About page (D-175)
- [ ] Settings > About and licences: the licence rows open the real licence text and the source link works (once the project is published).
