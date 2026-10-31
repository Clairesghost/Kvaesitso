# Owner checks on the phone: B4a-lists

How to get the build onto the phone: see `playbook/ci-and-signing.md`, "Install on the phone". This build installs OVER the old one (same key). Do not uninstall first.

## A. Update and your tasks
1. Where to be: your phone, after the install.
   Do: open Vintage Pocket Dial.
   You should see: Home as before, and the tasks you added earlier on the Tasks card.
   Report: yes/no (did your tasks survive?).

## B. App list
1. Where to be: Home.
   Do: tap the little arrow (^) at the bottom. Close it with the X, then swipe up with one finger on the empty area under the dial or on the cards.
   You should see: a cream sheet (dark at night) called "Apps" with a "Find an app" box, a "Launcher settings" row, a row of round chips (All first), and apps in groups with round icons, four per row.
   Report: did both the arrow and the swipe open it? Did another page (old search or widgets) open as well? (It should not.)
2. Do: tap a chip such as Games. Type a few letters in "Find an app".
   You should see: only that group; only matching apps.
3. Do: put your thumb on the letters on the right edge and slide down slowly.
   You should see: a big letter bubble, the list jumps to the first app with that letter, and the apps with that letter glow for about 1.5 seconds after you lift off.
4. Do: hold one app for half a second. Pick "Move to…" and choose another group.
   You should see: the app is now in the new group.
   Report: roughly how many apps are in "Other" (few, half, most)?
5. Do: tap an app.
   You should see: the icon shrinks a little, then the app opens.

## C. Settings
1. Where to be: the Apps sheet.
   Do: tap "Launcher settings".
   You should see: a Settings sheet with Look, Motion, Dial, Alerts, Calendars, Go back to your old launcher, More.
2. Do: change Colour to Mint, then Midnight, then Powder, then back to Cherry. Close with Done and the X.
   You should see: the phone body, the dial plate and the ring round the clock change colour on Home at once. The chrome, ivory discs and cards stay.
3. Do: change "Day or night look" to Night and back to Auto. Change Motion to Off and spin the dial.
   You should see: Night look at once; with Motion Off the dial just snaps with no gliding.
4. Do: under Alerts switch one app off. Under Calendars switch one calendar off.
   You should see: that app's notifications no longer appear on the Alerts card; that calendar's events are gone from the Calendar card.
   Report: yes/no for each.
5. Do: tap "Open the Home app page", then "More launcher settings".
   You should see: Android's Home app page; then Kvaesitso's own settings.
   Report: the exact title of the first page.

## D. Dial editor
1. Where to be: Home. Do: hold one dial disc for half a second without moving.
   You should see: the "The 12 apps" sheet with a small dial; the Home dial did NOT spin.
2. Do: drag one disc onto another and let go. Tap a disc, pick an app from the list (try the Find box). Tap an empty disc if there is one.
   You should see: discs swap or change; press Done and Home shows the same order.
3. Do: in the Apps sheet hold an app, choose "Put on the dial", then tap a disc.
   You should see: the app sits on that disc.

## E. Should NOT happen
1. Do: spin the dial with a finger drag, and fling it.
   You should NOT see: the editor or the app list opening while spinning.
2. Do: with a sheet open, press Back, then open one again and press the round Home button.
   You should NOT see: Home underneath changing; Back closes one sheet, the Home button closes all.
3. Do: swipe down or sideways on the empty area under the dial.
   You should NOT see: the app list. (It is for swiping up only.)

## F. Restart check (things are saved)
1. Close the launcher completely (recent-apps button, swipe its card away), then press Home.
   You should see: the same colour, motion, dial order, moved app group and switched-off alert and calendar as before.

## What was NOT verified
- Not compiled, not run (no Android SDK here). The cloud build is the first compile.
- Nothing has been seen on a screen: looks, whether the swipe up also opens the old widgets or search page, hold times, letter strip feel, glow, menu look.
- Report anything that looked cut off, too small, or wrong at Night.
