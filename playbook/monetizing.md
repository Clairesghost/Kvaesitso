# What can be sold from such a project

NOT legal advice. Everything marked CHECK needs a lawyer, an accountant or the licence text in your country. You decide what to sell and when.

## What you have
- The launcher: a fork of Kvaesitso, GPL-3.0. You MAY charge for it (APK, store listing, support, a supporter build). Everyone who receives it must be able to get the source, and the code stays GPL-3.0. It cannot be made closed.
- Your art (emblems, wallpapers, backgrounds, icon sets): yours, if you made it or have the right to use it (CHECK the terms of any AI tool or artist you used). Art that ships INSIDE the GPL app is distributed under GPL. To sell art separately, keep it in separate files or a separate repo with your own licence and sell it as its own product.
- Icon pack: sold as a separate app so other launchers can use it, with its own licence file. Needs many icons; unknown apps fall back to a letter in a shape.
- Wallpaper, lock-screen and always-on-display picture sets: sold as files with steps for the buyer.
- Theme files (colour schemes) and a themed colour-scheme pack: possible where the launcher imports them (CHECK the format's licence).
- A guide, template or course on how you built it (this kit's approach, with your own words and art): CC BY 4.0 of this kit means you may reuse and sell derived material if you credit the kit's author.
- Fonts: SIL OFL fonts may ship inside paid products but must not be sold alone; keep licence files.

## Play Store basics
1. A Google Play developer account (one-off fee; CHECK current price and identity checks).
2. Change the app id (`applicationId`), the app name and the app icon: do not publish under upstream's id, name or icon. Changing the id means current testers must reinstall.
3. A privacy policy page (what is read: notifications, contacts, location; what stays on the phone), a store listing with screenshots, content rating form, data-safety form.
4. Signing: keep your upload key safe; Play offers app signing.
5. Source: publish the source of the distributed app (GPL), link it in the listing.
6. Reviews can take days; launchers must declare the Home role properly.
Other stores or direct APK sales: same GPL rules; you handle payments and updates.

## Names and trademarks (CHECK)
- Do not use another brand's name, logo, characters or game art in the app, icon, store listing or art. "Inspired by" in your head is fine; in the listing it can still be a problem.
- Search the app name in the trademark registers of your target countries and in the Play Store before you grow attached. Use a working name until then.
- Keep upstream credit (Kvaesitso and its icon artist) in the About screen and licence files.

## Money features and keys
- A money panel with bank links needs a provider, keys, a backend and possibly regulation: ask first; never store bank logins. Manual entry is the safe start.
- Calling an AI service from the app needs an API key and paid usage per user; keys must never be hard-coded in an app. Opening the AI app instead is free and simple.
- Weather and other services: each has terms for commercial use (attribution, contact string, a licence).

## Checklist before selling (tick in `ops/monetization.md`)
- [ ] Source public and licence files present. [ ] App id, name, icon changed. [ ] Trademark search done. [ ] All fonts and art have licence notes. [ ] Third-party service terms read. [ ] Privacy policy written. [ ] Tax and business questions asked locally. [ ] Sellable art separate from GPL code. [ ] Credits to Kvaesitso and to this kit (if you reuse its text) in place.
