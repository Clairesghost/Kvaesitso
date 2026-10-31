# Owner checks on the phone: B4a-lists

How to get the build onto the phone: see `playbook/ci-and-signing.md`, "Install on the phone". This build installs OVER the old one (same key). Do not uninstall first.

# STAGE 2 (do these first)

## S2-A. Turn on Usage access (once)
1. Where to be: Home, then open the Apps sheet (tap the little arrow at the bottom), then "Launcher settings".
   Do: look at the "Dial" group.
   You should see: a row "Fill the dial with my most-used apps" with "Tap to allow" next to it, and under it the words "Only how long you use each app; it stays on your phone."
   Report: yes/no.
2. Do: tap that row.
   You should see: an Android page about "Usage data access". It may show the list of apps, or only Vintage Pocket Dial.
   Report: which of the two you saw.
3. Do: find "Vintage Pocket Dial" and switch it on. Allow if Android asks. Press the back arrow until you are on Home.
   If you cannot find the page by tapping, go by hand (check on your phone, the menu names may differ): Settings > Apps > the three dots at the top right > Special access > Usage data access > Vintage Pocket Dial.
   You should see: Home again.

## S2-B. The empty discs fill
1. Where to be: Home (come back to it once after switching Usage access on).
   Do: look at the dial.
   You should see: every disc has an app, none empty. They are apps you use most (your most-used app is usually near the top).
   Report: do they look like your real most-used apps (yes/no)? Is any disc empty?
2. Do: open Settings (via the Apps sheet) > "Fill the dial with my most-used apps".
   You should see: the word "On" next to it.
3. Do: open Settings > "Choose the 12 apps".
   You should see: the same 12 apps on the small dial. No quiet "tap to allow" line under the instructions any more.
4. Do: tap one disc, pick another app from the list, then press Done.
   You should see: that disc shows the app you picked on Home; the other discs still show your most-used apps.
5. Do: open the editor again, tap the disc you changed, pick "Leave this disc empty".
   You should see: that disc is a plain blank "+" disc, on Home too. It stays blank (no app jumps in).

## S2-C. Bug 1 checks (lists scroll)
1. Do: open the editor, tap a disc so the "Put on this disc" list appears, and drag the list up and down with a finger.
   You should see: the list scrolls all the way to the bottom (many apps, not only 6).
2. Do: open the Apps sheet and scroll it. Open Settings and scroll it down to "More launcher settings".
   You should see: both scroll with a finger drag.
   Report: yes/no for each of the three lists.

## S2-D. Should NOT happen (stage 2)
1. Do: note the app on the disc you picked yourself in S2-B step 4. Use a few other apps for a while (open some apps you normally do not use, several times), go back to Home.
   You should NOT see: your own pick change to something else. Only discs you did not set may change.
2. Do: double tap on an empty part of a sheet (Apps, Settings or editor).
   You should NOT see: the screen lock itself, or another page opening.
3. Do: with Usage access ON, open Home several times.
   You should NOT see: anything slowing or the phone getting warm. (The ranking is read once each time you come back to Home.)

## S2-E. Without Usage access (optional)
1. Do: switch Usage access for Vintage Pocket Dial off again in Android, come back to Home.
   You should see: the empty discs fall back to the old order (pinned apps, then the launcher's own most opened). The row in Settings says "Tap to allow".

## S2-F. Restart check
1. Close the launcher completely (recent-apps button, swipe its card away), then press Home.
   You should see: the discs you picked are still yours, the one you left blank is still blank, the rest are filled from usage.

## What was NOT verified in stage 2
- Not compiled, not run (no Android SDK here). The cloud build is the first compile.
- Whether the "package:" form of the Usage access link opens the launcher's own row on One UI 5, or only the general list.
- Whether Samsung's battery or Device care tools switch Usage access off by themselves.
- Whether the numbers Android gives for the last 14 days are filled on this phone (if the discs look random, tell me).

---

# STAGE 1 checks (from the first B4a build)


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
