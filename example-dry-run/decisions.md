# Owner-approved decisions (EXAMPLE: made-up user "Robin", cosy forest theme)

This file shows what the kit-guide writes into `ops/decisions.md` after the interview. Robin and everything below is invented for the example. No real person, art or brand.

## Phone
- Make and model: Example phone (a mid-range phone with a small centred punch-hole camera)
- Android version: 14
- Screen shape: punch hole, top centre
- Original launcher kept as fallback: the phone's own launcher
- APK install from outside the Play Store allowed: not yet (guide gave steps, ci-and-signing.md)

## Rules chosen by the owner
- Push rule: nothing goes to GitHub without Robin's yes; per-batch approval.
- Deep check (`final-check`) only when Robin says so.

## Decisions (interview batches 1 to 6)
- D-1 (G1 look and mood, 2026-11-02, Robin): Q1.1 A cosy and warm. Q1.2 inspired by a forest cabin in autumn (no brand). Q1.3 B some decoration (leaf corners, thin frames). Q1.4 A dark ground (deep green-brown).
- D-2 (G2 palette, Robin): Q2.1 A earth tones. Ground 1F2A22, panel top 2B3A2F, panel bottom 222E26, line 4E6B55, accent E0A458 (honey), alert C8553D (clay red), text EDE6D3, text dim A9A58F. Q2.4 A one scheme only.
- D-3 (G3 fonts, Robin): headings Lora (serif, SIL OFL), body Nunito (SIL OFL), clock digits Nunito Light, large. Q3.4 B larger small text (minimum 12 sp).
- D-4 (G4 Home layout, Robin): Q4.1 B two Home pages (swipe sideways). Page 1: header, weather, next events, search bar. Page 2: notifications list, most-used apps, quick chips (torch, ringer). Q4.3 A framed cards, soft corners (12 dp). Q4.4 A important things at the bottom. Q4.5 A fixed layout drawn by Claude.
- D-5 (G5 header, Robin): date and clock; Q5.2 A one look; Q5.3 B small digital clock beside the date; Q5.4 A weather icon plus number; Q5.5 A ignore the camera hole (keep a gap, no pill; system status bar stays visible).
- D-6 (G6 hero element, Robin): Q6.1 A none. Q6.2/6.3/6.4 not asked (skipped by D-6).
- D-7 (G7 dock, Robin): Q7.1 A bottom row; Q7.2 four buttons; Q7.3 phone, messages, browser, camera by category with fallback; Q7.4 B icon in a rounded-square frame; Q7.5 A dot badge (needs notification access, Robin agrees to grant once).
- D-8 (G8 gestures, Robin): Q8.1 A swipe up opens the plain app list; Q8.2 A swipe down opens Android's notification panel; Q8.3 nothing else, long press on empty Home opens launcher settings; Q8.4 C alphabet list with fast scroller; Q8.5 B search at the bottom.
- D-9 (G9 typing layer, Robin): Q9.1 A full-screen layer, bar at top; Q9.2 most-used apps; Q9.3 apps, contacts, settings; Q9.4 chips: torch and ringer act (no rotate); Q9.5 Home closes the layer.
- D-10 (G10 landscape, Robin): Q10.1 A portrait only.
- D-11 (G11 animations, Robin): Q11.1 B minimal (press effects only); Q11.2 yes, a Full/Minimal/Off switch; Q11.3 B short fades; Q11.4 yes, will report battery after a day.
- D-12 (G12 wallpaper etc., Robin): Q12.1 A a picture made for the theme (soft forest-floor gradient with leaf shapes, drawn by Claude, preview first); Q12.2 B no lock-screen picture; Q12.3 B skip always-on display; Q12.4 A leave bars.
- D-13 (G13 icon pack, Robin): Q13.1 B same rounded-square shape for all icons (original glyph inside); Q13.2 original icon; Q13.3 A inside the launcher.
- D-14 (G14 keyboard, Robin): Q14.1 A leave.
- D-15 (G15 sell or keep, Robin): Q15.1 B share free with friends by APK link; Q15.2 not selling; Q15.3 A working name "Mossgate" (rename and trademark search before any public release); Q15.4 A no money features; Q15.5 A no AI features.

## Delegated to Claude
- DELEGATED (G12 wallpaper exact leaf shapes): Claude draws two options in the preview; Robin still picks.

## LIMITS (wanted something stock Android cannot do; offered instead)
- LIMIT (G12 lock screen): Robin asked for a matching lock screen. A launcher cannot restyle it; offered a picture plus steps; Robin chose no picture (D-12).
- LIMIT (G5 notification pill): not asked; ignored camera hole by choice (D-5).

## Open (for later batches)
- Page-two frame art and wallpaper previews (batch 2).
- App name final choice (D-15).
