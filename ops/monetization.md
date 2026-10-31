# Monetisation notes

Copy to `ops/monetization.md`. NOT legal advice. Everything marked CHECK must be verified with a lawyer or the licence text before selling anything. The owner decides what to sell and when. Background: `playbook/monetizing.md`.

## What we have and how it could earn
| Part | Where | Licence / ownership | Ways to monetise | Risks / CHECK |
|---|---|---|---|---|
| The launcher app | whole repo | Fork of Kvaesitso, GPL-3.0: derived code stays GPL-3.0 and recipients get the source | Sell the APK or a store listing (GPL allows charging); paid support; donations; supporter build | CHECK: publish source of what you distribute; change app id, name and icon before publishing; credit upstream |
| (add one row per sellable part: art, icon pack, wallpaper set, theme files, guide) | | | | |

## Generic CHECK list (tick each before any sale)
- [ ] GPL-3.0: source for the distributed app is public; licence file and headers kept; changes noted; upstream credit kept.
- [ ] App name, icon and package id (applicationId) are yours, not upstream's and not a brand's. CHECK trademark search in the countries you sell in.
- [ ] Every font has a licence that allows bundling (SIL OFL: may ship inside a paid product, may not be sold alone; keep licence files).
- [ ] Every image, icon, sound and library has a licence file or is your own work (note who made it; AI-made art: CHECK the AI provider's current terms).
- [ ] No other brand's logos, characters, music or game art. "Inspired by" is fine; copying is not. Do not use a brand's name in the app name or store text without permission.
- [ ] Third-party services (weather, maps, etc.): CHECK each provider's terms for commercial use, attribution and key requirements.
- [ ] Privacy: what the app reads (notifications, contacts, location) stays on the phone; write a privacy policy before any store listing; no ads or trackers without the owner's decision.
- [ ] Taxes, business registration and store fees: CHECK with an accountant for your country.
- [ ] Sellable art lives in SEPARATE files or a separate repo with its own licence, never mixed into upstream files.

## Rules for threads
- When a task creates something sellable, add one row (what, where, licence, idea, risk). Mark the part [M] in `docs/design-system.md`.
- Never add paid-service keys, ads or trackers without the owner's explicit decision.

| [M] Scene sketches (steampunk / clockmaker bench / apothecary cabinet, day and night) | docs/design/scenes/ | Own drawings (generated vector art, rough); fonts Limelight, Josefin Sans, Lato under SIL OFL in `fonts/` | Scene theme packs, drawn-object icon packs (12 per scene) | CHECK: final art may be redrawn; any AI-made final art needs the provider's terms checked |
