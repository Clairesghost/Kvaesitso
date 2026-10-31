# Owner checks on the phone: B3-cards

How to get the build onto the phone: see `playbook/ci-and-signing.md`, "Install on the phone". Keep your old launcher installed.

## A. The three cards
1. Where to be: press Home.
   Do: look under the spinning dial.
   You should see: three cream-coloured plaques with black frames and a silver edge: Calendar, Alerts, Tasks. Switch the phone to Dark mode: all three turn near-black.
   Report: yes/no, and a screenshot if anything is cut off or overlaps the dial or the little arrow at the bottom.

## B. Calendar card
1. Do: look at the left of the Calendar plaque, then at the right.
   You should see: today's date number with the weekday and month; to the right the title of your next event today with its time. If you have more events today, a small "Then…" line. The space above the date number is empty on purpose (weather comes later).
2. Do: tap the Calendar plaque.
   You should see: your phone's Calendar app opens.
   Report: the name of the app that opened, and whether the event and time are right.
3. If the plaque says "Show your calendar": tap it, then tap "Allow" in the Android question.
   You should see: your events appear.
4. If you have no events today: the plaque says "Nothing on today" and, if something is coming, "Next: <title>, <day> <time>".

## C. Alerts card
1. Do: open Settings > Notifications > Advanced settings > Device & app notifications (or just tap the plaque row "Show your alerts"). Turn on this launcher.
   You should see: after you come back to Home, the Alerts plaque shows your newest two notifications with the app picture, the text, an "Open" button and a ✕.
   Report: yes/no.
2. Do: send yourself a message so a new notification arrives, then press Home.
   You should see: it appears at the top of the Alerts plaque.
3. Do: drag one row sideways, more than a thumb's width, and let go.
   You should see: it flies off and is gone. Pull down Android's notification shade: it is gone there too.
4. Do: drag a row only a little and let go.
   You should see: it springs back and stays.
5. Do: tap the ✕ on a row.
   You should see: the row goes and the notification is gone from Android's list too.
6. Do: if more than two notifications are waiting, tap "+N more ›" at the top right of the plaque.
   You should see: Android's own notification list opens.
   Report: yes/no. (This one may not work on your phone; tell me if nothing happens.)
7. Do: tap a row, or its "Open" button.
   You should see: the app that sent it opens.
8. Do: start music so a "now playing" notification appears, then try to drag it sideways.
   You should see: it only wiggles a little and springs back, and it has no ✕.
9. When nothing is waiting: the plaque says "all clear".

## D. Tasks card
1. Do: tap "Connect Google Tasks".
   You should see: a short message "Coming in the next update". Nothing else happens.

## E. Should NOT happen
1. Do: drag a row in Alerts left and right a few times, and also try to spin the dial and swipe the home page.
   You should NOT see: the page behind sliding sideways while you swipe a row, the phone locking, or the dial moving when you touch a plaque.
2. Do: turn on Battery Saver (or switch the launcher's Motion to Off if you can) and swipe a row away.
   You should NOT see: a fly-off animation; the row just disappears.
3. You should NOT see any notification text in the plaque for notifications you already cleared, or in any error message.

## F. Restart check
1. Close the launcher completely (recent-apps button, swipe its card away), then press Home.
   You should see: the three plaques again, the same as before, with Alerts and Calendar filled with current data.
   Report: yes/no.

## What was NOT verified (copied from the handoff)
- Nothing was compiled or run; the first cloud build is the first compile.
- Everything above, including the look, layout and the "+N more" button, has not been seen on a phone.
