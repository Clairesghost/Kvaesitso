# Owner checks on the phone: B1-foundation

How to get the build onto the phone: `playbook/ci-and-signing.md`, "Install on the phone". This is the debug build, so it installs NEXT TO the original launcher and does not replace it.

## A. Name and icon
1. Where to be: the phone's list of all apps (swipe up on the Samsung home screen, or open Settings > Apps).
   Do: look for the new launcher.
   You should see: an app called "Vintage Pocket Dial" with a cherry-red square icon showing a round dial with a small clock in the middle.
   Report: yes/no, and a screenshot of the icon if it looks wrong (cut off, blurry, wrong colour).
2. Do: press and hold the icon, or look at it on a home screen with a round or squircle icon shape.
   You should see: the dial stays inside the shape, nothing cut off at the edges.

## B. It still works like before
1. Do: press the Home button and choose "Vintage Pocket Dial" as the home app (once, not "always" if you want to be safe).
   You should see: the normal Kvaesitso home screen, with its usual look. Nothing has been redrawn yet; this stage only changes the name and icon.
2. Do: open the app list, search for an app and open it.
   You should see: everything works as it did.
   Report: anything that crashes or looks different from plain Kvaesitso.

## C. Should NOT happen
1. Do: look at your Samsung launcher.
   You should NOT see: it replaced, renamed or removed. The original launcher must still be there and still work.
2. You should NOT see: red or script lettering anywhere inside the launcher yet (the new fonts and colours are only bundled, not used on screens).

## D. Restart check
Nothing is saved by this stage that you can see (the Look choice has no screen yet), so no restart check is needed.

## What was NOT verified (copied from the handoff)
- Nothing was compiled or run. The cloud build may still show an error.
- How the icon looks on the real phone, and whether the name shows correctly.
- The fonts and the Look setting are not used by any screen yet, so they cannot be seen.

Report: "icon and name OK" or a screenshot of what is wrong.
