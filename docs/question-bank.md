# Question bank (the guide's interview)

How the guide uses this file: go group by group (G1 to G15). Ask at most four questions per batch, as multiple choice with the options below (reword to the person's own words; always add "other / you choose"). Say what each option costs. Record every answer in `ops/decisions.md` as `D-n`. Tags: POSSIBLE (the launcher can draw it), PARTLY (needs steps from the person or is limited), NOT POSSIBLE on stock Android (offer the alternative).
Cost words: EASY (one small task), MEDIUM (a few tasks), HARD (many tasks, art, several test rounds). Battery: LOW / MED / HIGH.

---
## G1 Look and mood
**Q1.1 Which mood is closest to what you want?**
A. Cosy and warm (soft colours, rounded shapes). B. Dark and dramatic (deep colours, sharp lines). C. Clean and minimal (lots of space, few colours). D. Playful and bright (strong colours, fun shapes). Other: a place, a film, a game, a season (we only take inspiration, never copy).
Cost: choice sets colour, fonts and shapes later. EASY. POSSIBLE.
**Q1.2 Is there something it should be "inspired by"?** A. A place or season. B. A game, film or book. C. A real era or craft style. D. Nothing, surprise me.
Note: inspired by only; names, logos and art of others are never copied (see Q15 and `playbook/monetizing.md`).
**Q1.3 How much decoration?** A. Almost none. B. Some (frames, small ornaments). C. A lot (every panel has a rich frame). D. Mixed: rich Home, plain elsewhere. Cost: more decoration = more art work (MEDIUM to HARD) and more drawing time on the phone.
**Q1.4 Dark or light ground?** A. Dark. B. Light. C. Follows the phone's day/night setting. D. Two looks, I switch. Cost: C and D double the colour work (MEDIUM). POSSIBLE.

## G2 Palette
**Q2.1 Main colours (pick a family):** A. Earth tones (browns, moss, cream). B. Jewel tones (deep red, gold, black). C. Cool (blues, greys, white). D. Pastel. Other: describe or give 2 colour names.
**Q2.2 One accent colour for highlights and buttons?** Offer 4 swatches in the preview; the person picks. EASY.
**Q2.3 Alert colour (badges, warnings)?** A. Red. B. Orange. C. Same as accent. D. You choose.
**Q2.4 One setting to switch whole colour schemes later?** A. No, one scheme only (EASY). B. Yes, 2 to 3 schemes, colours only, same layout (MEDIUM). C. Yes, also follows day/night (MEDIUM). POSSIBLE inside the launcher; the phone's own screens keep their colours (PARTLY).

## G3 Fonts
**Q3.1 Heading style:** A. Elegant serif. B. Hand-lettered or old-style. C. Clean sans-serif. D. Rounded and friendly. The guide offers 2 or 3 free fonts per choice with a picture; only fonts that may be bundled (for example SIL OFL).
**Q3.2 Body text style:** A. Same family as headings. B. Plain readable sans. C. Book-like serif.
**Q3.3 Clock numbers:** A. Same as headings. B. Big and thin. C. Big and bold. D. Digits in a special font.
**Q3.4 Small text size:** A. Normal. B. Larger (easier to read). Cost: larger needs more layout work. Fonts: free to add, adds a few hundred KB to the app each.

## G4 Home layout and sections
**Q4.1 How many Home pages?** A. One page. B. Two pages (swipe sideways). C. One page plus an apps page (swipe up). D. One page, two pages in landscape only. POSSIBLE; B costs MEDIUM.
**Q4.2 Which sections on Home (pick up to four)?** Clock and date; weather; next calendar events; notifications list; most-used apps; favourite contacts; quick-setting chips (torch, ringer, rotate, Wi-Fi page); music controls; notes; a money panel (placeholders first, real data only if you accept keys, cost and privacy steps); search bar; nothing else. POSSIBLE except where noted. Battery: weather and notifications LOW; animated panels MED.
**Q4.3 Panel style:** A. Framed cards. B. Floating without frames. C. One big sheet with dividers. D. Frosted glass.
**Q4.4 Where does the thumb reach?** A. Important things at the bottom. B. Important things at the top. C. Mixed. Cost: EASY, sets the order.
**Q4.5 Grid or free?** A. Fixed layout that Claude draws (best look, EASY to keep tidy). B. Freely movable widgets like a stock launcher (more flexible, looks less controlled). C. Mixed.

**Q4.6 Page indicator and empty panels (only if more than one page or a list panel):** A. Dots at the bottom. B. A thin line. C. None. And for a panel with nothing in it: A. A short friendly sentence. B. Hide the panel. EASY.

## G5 Header (top of Home)
**Q5.1 What does the header show?** Date; clock; weather; day-of-week artwork; greeting; nothing.
**Q5.2 One look or a different one per weekday?** A. One. B. Seven (a theme per day; MEDIUM to HARD, art). C. Two (weekday/weekend). Seven designs may be sold as a pack (a sellable part, [M]).
**Q5.3 Clock style:** A. Digital big. B. Digital small beside the date. C. Analogue drawn. D. Words ("quarter past ten").
**Q5.4 Weather:** A. Icon plus number. B. Little animated scene (battery MED). C. Words only. D. None. Weather needs a free service (see `ops/templates/api-facts.md` AF-2); location is read only while the screen is on.
**Q5.5 Camera hole or notch:** A. Ignore it (leave space). B. Use it (a status "pill" around it that shows notification icons and Wi-Fi, Bluetooth, sound state; the system status bar is hidden on Home). PARTLY: you cannot move the system's own icons, only hide the bar and redraw.

## G6 Hero element (orb or other centrepiece)
**Q6.1 Centrepiece:** A. None (more room). B. A glass orb or crystal that opens your AI assistant app. C. A big clock/dial. D. A character/mascot/picture. E. A search bar as the centrepiece. Cost: B, C, D are HARD (art, tuning, battery gate); E is EASY.
**Q6.2 What does a tap do?** A. Opens the assistant app (for example Claude) empty. B. Opens search. C. Opens an app of your choice. D. Nothing.
**Q6.3 Does it move?** A. Still. B. Slow ambient motion (5 fps, tiny region). C. Reacts when pressed. D. B and C. Battery: B and D MED, gated by the motion rules.
**Q6.4 Sending typed text to an AI app?** A. Share the text to the app; you choose where it goes (works, never sends by itself). B. Nothing. Auto-send is NOT POSSIBLE without an API key (cost, privacy) or an accessibility service (fragile, extra permission); we offer A.

## G7 Dock and shield buttons
**Q7.1 Dock position:** A. Bottom row. B. Side column. C. Floating pill. D. No dock.
**Q7.2 How many fixed app buttons?** 3, 4, 5 or 6.
**Q7.3 Which apps?** The person lists them; or "you pick sensible defaults" (phone, messages, browser, camera); defaults chosen by app category with a fallback if an app is missing. The person can change them later in a picker.
**Q7.4 Button look:** A. Plain icon. B. Icon in a framed shape (shield, circle, square). C. Letter in a shape (no app icons at all: uniform look, always works). D. Their own emblem per app (art, [M]).
**Q7.5 Badges:** A. Dot. B. Number. C. None. Needs notification access (the person grants once on Android's screen).

## G8 Gestures and apps page
**Q8.1 Swipe up from Home:** A. App list. B. Apps page with favourites, categories and a "today" strip. C. Search. D. Nothing.
**Q8.2 Swipe down:** A. Android's notification panel. B. Search. C. Nothing. NOTE: the system quick panel itself cannot be restyled.
**Q8.3 Swipe left/right, double tap, long press:** per gesture: nothing / open an app / open search / open launcher settings. Recommendation: nothing by default, keep settings reachable from one long press.
**Q8.4 Apps page categories:** A. Automatic (tags, then app category). B. You sort them by hand. C. No categories, alphabet list with a fast scroller. D. Hidden apps list.
**Q8.5 Search inside the apps page:** A. Top. B. Bottom (thumb). C. None.

## G9 Typing and search layer
**Q9.1 Tap the search bar: what appears?** A. Full-screen layer with the bar at the top, results below (recommended: the keyboard needs room). B. A bar that grows in place. C. Stock launcher search.
**Q9.2 Before the person types anything, show:** most-used apps; starred contacts; quick-setting chips; recent searches; nothing.
**Q9.3 What does search find?** Apps; contacts; calendar; settings; files; web. Each may need a permission the person grants once.
**Q9.4 Chips (only these can DO something on Android 13 without special access):** torch, ringer (normal/vibrate), auto-rotate (after "Modify system settings" is granted). Wi-Fi, Bluetooth, location, power saving only open their settings page. NOT POSSIBLE: toggling Wi-Fi/Bluetooth/mobile data directly.
**Q9.5 Press Home while searching:** closes the layer (recommended).

## G10 Landscape
**Q10.1 Does the phone turn?** A. Portrait only. B. Turns, with the same layout scaled (EASY). C. Turns, with a special layout that uses the width (MEDIUM). D. Turns with side columns plus a forecast/agenda strip (HARD).
**Q10.2 Camera hole in landscape:** keep content away from it on whichever side it ends up (recommended, needs tests for both turns).
**Q10.3 Rotation lock:** launcher follows the phone's auto-rotate setting, or always portrait. Upstream may lock portrait by a setting: the guide checks it.

## G11 Animations and battery
**Q11.1 How much motion?** A. None. B. Minimal (press effects only). C. A little ambient motion in one or two small places. D. Full. Hard rules apply anyway: motion stops when Battery Saver is on, battery below 15% (unless charging), screen off, or the phone's remove-animations is on.
**Q11.2 Settings switch for motion (Full/Minimal/Off)?** Recommended yes. EASY.
**Q11.3 Transitions:** A. Instant. B. Short fades (150 ms). C. Slides. D. Rich (reactive, smooth rotation; MEDIUM).
**Q11.4 After a day, will you check battery use and tell us?** (Settings > Battery > Battery usage > your launcher.) Recommended yes: it is the only real measurement.

## G12 Lock screen, wallpaper, always-on display
**Q12.1 Wallpaper:** A. A picture made for you, set by the launcher. B. A plain colour. C. Your own photo. D. Moving wallpaper (battery HIGH, not recommended).
**Q12.2 Lock screen:** NOT POSSIBLE for a launcher to restyle. We can make a matching PICTURE and write steps to set it as your lock screen wallpaper. A. Yes make one. B. No. Clock style and widgets on the lock screen belong to the phone maker (some makers offer an official theme app; the person checks if it exists on their phone: UNVERIFIED until checked).
**Q12.3 Always-on display:** PARTLY: a picture or clock style the phone's own settings allow. A. Make a picture. B. Skip.
**Q12.4 Status and navigation bars:** cannot be restyled by a launcher. Choices: A. Leave. B. Hide the status bar on Home and draw our own (see Q5.5). C. Make the navigation bar background match through the system gesture/colour setting if the phone allows (PARTLY).

## G13 Icon pack
**Q13.1 App icons across the phone:** A. Keep each app's own icon. B. Same shape for all (circle, rounded square, shield, hexagon). C. Monochrome tint in the accent colour. D. A full icon pack with your art. Cost: A, B EASY; C EASY to MEDIUM; D HARD (hundreds of icons for coverage; unknown apps fall back to a letter in a shape) and sellable ([M]).
**Q13.2 Apps without an icon in the pack:** letter in a shape (recommended) / original icon.
**Q13.3 Pack as a separate app?** A. Inside the launcher. B. A separate icon-pack app (other launchers can use it; its own licence file; can be sold). MEDIUM.

## G14 Keyboard
**Q14.1 Keyboard look:** A. Leave. B. A background picture that matches, for a keyboard app that supports it (steps for the person; the launcher cannot restyle any keyboard). C. A theme file for a keyboard app that accepts themes.
**Q14.2 Key press sounds or haptics:** the keyboard's own settings; we only write the steps.

## G15 Sell it or keep it
**Q15.1 What is this project for?** A. Only for me (no legal work needed beyond keeping licences). B. Share free with friends (APK link; keep licences and credit). C. Publish free in a store (needs a new app id, name and icon, a privacy policy; see `playbook/monetizing.md`). D. Sell it or parts of it.
**Q15.2 If D, which parts?** The launcher under its licence (GPL-3.0: you may charge, recipients get the source); art packs, wallpapers, icon pack; theme files; a guide or course. Each gets a row in `ops/monetization.md`.
**Q15.3 Name:** the app name. Check for trademarks before you grow attached (CHECK, not legal advice). A. Use a working name now, rename before publishing. B. Final name now.
**Q15.4 Money features inside the launcher?** (a money panel, bank links) A. Placeholders only. B. Manual entry. C. A bank-link service: needs a backend or provider, keys, cost, privacy and possibly regulation; the guide asks first and never sees bank logins.
**Q15.5 AI features:** A. Open the Claude (or other AI) app only. B. Calls an AI service from inside the launcher: needs an API key and paid usage, and keys must never be in the app. Recommended A.
