# Handoff: B2-home stage 1

What changed (files, commits, new settings; plain words):
- New package `de.mm20.launcher2.ui.vpd.home`: VpdHomeComponent (the new home, picked in SharedLauncherActivity), VpdHomeScreen (layout, stage 360x780 scaled to width, shrinks if height needs), VpdHousing (search slot, 3 coins, camera ring), VpdDial (plate, ticks, finger stop, 12 discs, spin/fling/snap/return-home, tap-to-launch, press effect, clock hold), VpdClock (hands, plate, once-a-minute ticker), VpdStatus (network, Bluetooth, battery as event flows), VpdMotionGate, VpdActions (camera, clock, settings pages), VpdHomeVM, VpdStage.
- Upstream edits (small, own commits): SharedLauncherActivity (home = VpdHomeComponent, search bar style Hidden, status bar hidden, one import); LauncherSettingsData (+vpdMotion, +vpdRingReturnsHome); preferences Module (+VpdHomeSettings); AndroidManifest (+BLUETOOTH_CONNECT).
- New stored settings (no screen yet): Motion Full/Minimal/Off (default Full), Ring returns home on/off (default on).
- Behaviour: dial apps = favourites of type app, up to 12, order from the favourites service (pinned first, then most used). Motion Off = snap at once, no press shrink. Minimal = no glide, short snap. Gate also turns animation off for Battery Saver, screen off, remove-animations, battery 15% or less and not charging (read at touch time, no receivers).
- Bluetooth: one plain explanation window on first Home, then Android's Nearby devices question; refused = grey, no more asking (tap on the coin then opens Bluetooth settings).

How it was checked: read-through of every new file and its imports only. NOT compiled, NOT run (no Java/Android SDK here).

NOT verified (only the phone can show):
- That it compiles (cloud build is the first compile).
- Camera ring position/size around the real hole (uses cutout bounds: hole diameter = rect width, ring = 1.62x). AF-3.
- Dial drag/fling feel, that the scaffold's swipe and double-tap do not fire on the dial (AF-22), that swipes on the empty body still work.
- Bluetooth coin for headphones (A2DP/headset/ACL), watches probably will not show (AF-26).
- Camera, Clock (SHOW_ALARMS) and settings intents on One UI; whether the battery coin opens the Battery saver page rather than battery usage.
- Icons shown in circular discs (ShapedLauncherIcon with CircleShape); night look colours; chevron also opens keyboard if "open keyboard on search" is on.
- Battery cost (owner reports after a day).

Android facts learned (proposed AF lines for `ops/api-facts.md`, from api-scout 2026-10-31, all UNVERIFIED on phone):
- AF-25: BLUETOOTH_CONNECT was not declared in the repo; added in B2 (D-182). Runtime "Nearby devices" prompt.
- AF-26: Headphones: BluetoothAdapter.getProfileConnectionState(A2DP/HEADSET) read on each ACL_CONNECTED/DISCONNECTED, A2DP CONNECTION_STATE_CHANGED and adapter STATE_CHANGED broadcast; watch (BLE) may not show.
- AF-27: Camera: Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA), no <queries>.
- AF-28: Clock: Intent(AlarmClock.ACTION_SHOW_ALARMS); no hard-coded Samsung package.
- AF-29: Network: ConnectivityManager.registerDefaultNetworkCallback + hasTransport(WIFI/CELLULAR), ACCESS_NETWORK_STATE only; VPN may report neither.
- Settings pages: Settings.Panel.ACTION_INTERNET_CONNECTIVITY, ACTION_BLUETOOTH_SETTINGS, ACTION_BATTERY_SAVER_SETTINGS.

Open questions for the owner (decided simply, to confirm):
- Wording of the Bluetooth explanation window and its plain Material look (not styled yet).
- Chevron is a plain white "^" (mock-up has none specified); it opens search, which may open the keyboard if that Kvaesitso setting is on.
- Tick marks: 36 small chrome ticks at the plate edge (copied from the mock-up).
- Coin glyphs are simple drawn shapes (arcs, bars, rune, ring), not final art.
- Tap on a disc with no app does nothing; fewer than 12 favourites leave empty discs.
- On leaving Home (pause) the ring jumps back to home at once if the return setting is on.

Sellable parts added ([M] rows): none.
Lessons (0 to 3 lines, also in `ops/lessons-inbox.md`): see inbox.
Next stage needs: B3 cards go under the dial in the plain cherry body (stage y 384 to 704); VpdStage and LocalVpdColors are provided by VpdHomeScreen.
Branch and last commit: claude/B2-home (see `git log`).

## Stage 2 (dial 15% bigger, D-183)

Branch: `claude/B2-home-s2` (made from `claude/B2-home` because that branch is checked out in another worktree; merge it back or push it as it is).

What changed:
- Whole dial drawn uniformly 1.15 times bigger (plate, rims, ticks, finger stop, ivory ring, centre band, clock, enamel plate, 12 discs, touch zones). One factor: `DIAL_SCALE` in `VpdStage.kt`; `VpdDial` and `VpdClock` derive a second `VpdStage` (`ds`, k * 1.15) and use it for all sizes and for hit-testing (`hitDisc`, `angleOf`, clock zone, dial zone); the centre comes from the plain stage scale.
- Dial box is still 360 units wide (so the parent cannot clamp it), now 350 high, top at stage y 52 (`DIAL_TOP`), centre at stage (180, 227), outer chrome rim radius 168 (inside the box, 7 units from the top edge). Disc ring radius 112 -> 128.8.
- Housing, camera ring, chevron (706-750) untouched. Files: VpdStage, VpdDial, VpdClock, VpdHomeScreen.

For B3: place the cards at Calendar 412-516, Alerts 524-602, Tasks 610-704 (Tasks is 2 rows). The dial's drawing ends at about y 395 (finger stop to about 410 at the bottom right). Nothing to build for it now.

Owner phone results (stage 1 test):
- Working: coins open the Internet, Bluetooth and Battery saver pages; dial spins, flick and the 5 s return work; holding the clock opens the alarm clock; search slot and chevron work; Day/Night switch works.
- Not yet reported: camera ring position, Bluetooth coin with headphones, charger light, the "should NOT happen" checks, restart check, battery number.

NOT verified: not compiled, not run (no Java/Android SDK here). Not seen: that nothing is cut off at the edges, that the finger stop and the enamel plate sit right at the new size, that the discs on a small-height phone still fit (the stage may shrink to fit height), that touch zones line up with the drawing.
