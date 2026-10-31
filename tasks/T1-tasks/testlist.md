# Owner checks on the phone: T1-tasks

How to get the build onto the phone: see `playbook/ci-and-signing.md`, "Install on the phone".

**Important, first install only:** this is the first build signed with your fixed key. Android will not install it over the old build. Do this once:
1. On the phone, long-press the Vintage Pocket Dial icon in the app drawer, tap App info, then Uninstall. (Your original launcher stays.)
2. Install the new APK as usual. Every build after this one installs over the top, with no uninstall, and keeps your tasks.

## A. Tasks card
1. Where to be: the home screen, Tasks card at the bottom.
   You should see: the title "Tasks", two empty dotted rows, and a last row "Add a task…". No "Connect Google Tasks" row.
2. Do: tap "Add a task…", type "milk", press Done on the keyboard.
   You should see: "milk" with a small tick box as the top task; the keyboard closes.
   Report: does the text field stay visible above the keyboard? (yes/no)
3. Do: tap "milk" (or its tick box).
   You should see: a check mark and the text crossed out and paler. Tap again: back to normal.
4. Do: tick "milk" so it is crossed out, press the Home button, then open the launcher again (or open any app and come back).
   You should see: "milk" is gone.
5. Do: add three tasks: "one", "two", "three".
   You should see: only "three" and "two" show, and "+1 more" at the right of the "Tasks" title. Tapping "+1 more" does nothing yet (expected).

## B. Should NOT happen
1. Do: tap "Add a task…", leave it empty, press Done.
   You should NOT see: an empty task row appear.
2. Do: tap "Add a task…", type "cancel me", press the Back gesture (keyboard closes).
   You should NOT see: "cancel me" saved as a task. The row goes back to "Add a task…".
3. Do: tick a task and keep looking at the home screen for a minute.
   You should NOT see: the crossed-out task vanish by itself while you are looking.

## C. Restart check
1. Close the launcher completely (recent-apps button, swipe its card away), then press Home.
   You should see: the same tasks as before.
   Report: yes/no.

## What was NOT verified
- That it compiles (cloud build is the first compile); nothing run on a phone.
- That the fixed signing key works (first build with it). The build fails with a clear message if the GitHub secret is missing.
- Look and fit of the new rows; whether the keyboard hides the Add line.
- Report: A1 to A5, B1 to B3, C1 as yes/no, plus a screenshot of the Tasks card.
