# Owner checks on the phone: B2-home

How to get the build onto the phone: see `playbook/ci-and-signing.md`, "Install on the phone". After installing, press the Home button and choose Vintage Pocket Dial as the home app ("Always" if asked). Your Samsung home screen stays installed.

## Stage 2: dial 15% bigger
1. Where to be: the home screen. Do: press the Home button.
   You should see: the dial about 15% bigger than before (the dark round plate is nearly as wide as the screen), with the clock and the 12 discs bigger too. The text under the clock is still readable.
   Report: yes/no, and a screenshot.
2. Do: look at the edges of the dial and under the silver top plate.
   You should NOT see: any part of the dial cut off at the left or right edge, or hidden under the silver housing at the top.
3. Do: tap three different discs, one at a time (go back to Home between).
   You should see: the app you touched opens each time, not its neighbour.
4. Do: turn the dial with a finger, flick it, wait 5 seconds.
   You should see: spinning, gliding and the return home work as before.
5. Do: hold a finger on the clock for half a second.
   You should see: the alarm clock opens. Report if the touch works over the whole clock face.

## Not yet reported from the first test (do these too)
Camera ring position (section A.1 / B.5), the Bluetooth coin with headphones (B.2), the charger light (B.3), the "should NOT happen" checks (D), the restart check (E) and the battery number after a day (F). Already confirmed OK: coins open their pages, spin, flick and 5 s return, clock hold, search slot, chevron, Day/Night.


## A. What Home looks like
1. Where to be: the home screen, phone in light mode.
   Do: press the Home button.
   You should see: a cherry-red phone body. At the top a silver plate with a dark search slot on the left that says "Dial, find, sum, jot…", a round silver ring around the real camera hole in the middle, and three small round silver coins on the right. Below that a dark round dial with up to 12 round ivory discs showing your favourite apps. In the middle of the dial a clock with the time in a small cream plate under the centre.
   Report: yes/no, and a screenshot if anything looks wrong (a ring not around the camera hole, text cut off, nothing under the dial).
2. Do: look at the clock and compare with your phone's time.
   You should see: the right time, as "10:42 pm" style text on the plate, and the hands never touching the plate.
   Report: is the time right? Does it change at the next minute?
3. Do: turn the phone's dark mode on (swipe down from the top, Dark mode), press Home.
   You should see: a black body and black clock face with ivory marks, a soft glow near the silver finger stop at the bottom right of the dial.
   Report: yes/no.

## B. The coins
1. First time only: a small window asks about Bluetooth ("Show Bluetooth connections").
   Do: tap Allow (or Not now to test refusing).
   You should see: Android's own "Nearby devices" question after Allow.
2. Do: connect headphones.
   You should see: the middle coin (Bluetooth) turns dark. When you disconnect it goes grey again.
   Report: did it change? Did it also react to a watch (it may not)?
3. Do: plug in the charger.
   You should see: the camera ring gets a green inner line; the battery coin shows a dark ring that matches your charge.
4. Do: tap each coin once (left: connection, middle: Bluetooth, right: battery).
   You should see: Android's Internet, Bluetooth and battery pages open. Press back to return.
   Report: which page opened for each.
5. Do: tap the camera ring. You should see: the camera opens. Report: camera app or something else?
6. Do: switch Wi-Fi off, then back on, then mobile data off.
   You should see: the left coin changes shape (Wi-Fi arcs, mobile bars) or goes grey with none.

## C. The dial
1. Do: put a finger on the dial ring and turn it, then let go quickly while moving.
   You should see: the discs turn with your finger, keep spinning a little, slow down by themselves and click into place. Icons stay upright.
2. Do: leave it alone for 5 seconds.
   You should see: the ring turns back to where it started.
3. Do: tap one disc.
   You should see: it shrinks slightly for a moment, then the app opens.
4. Do: hold a finger on the clock face for half a second.
   You should see: the Clock app opens. A quick tap does nothing.
   Report: which app opened.
5. Do: tap the search slot. You should see: Kvaesitso's search page with the keyboard open. Go back.
6. Do: tap the small arrow (chevron) at the bottom. You should see: the search page with your app list. Go back.

## D. Should NOT happen
1. Do: swipe up/down/left/right on the red body between the dial and the arrow.
   You should see: whatever those swipes did before (same as in the last build).
2. Do: turn or flick the dial, and double-tap on it.
   You should NOT see: the phone screen locking, the search page opening, or the home screen sliding away.
3. Do: turn on Battery Saver, then spin the dial.
   You should NOT see: spinning animation or glide; the ring jumps to the nearest slot.
4. Do: tap a disc and look at the time the app takes.
   You should NOT see: a disc stuck small afterwards (come back with Home).

## E. Restart check
1. Close the launcher completely (recent-apps button, swipe its card away), then press Home.
   You should see: the same discs and look as before. The Bluetooth window should NOT appear again.

## F. Battery check after a day
1. After a day of normal use open Settings > Battery > Battery usage and find Vintage Pocket Dial.
   Report: the percentage it shows.

## What was NOT verified (copied from the handoff)
- Nothing was compiled or run: no Java/Android here; only the cloud build can find compile mistakes.
- Everything above is only seen on the phone (see the handoff list).
