# Task brief: T0-build (EXAMPLE)

Goal: The fork of Kvaesitso builds in GitHub Actions, produces a signed APK that installs on Robin's phone, and the phone's original launcher stays installed (D-phone, rules chosen).
Files in scope: `.github/workflows/build-apk.yml` (new, from `scripts/build-apk.yml.template`), `.gitignore` (add `*.jks`, `*.b64`), `ops/` copies of the templates. Nothing in `app/`.
Done when:
- A run on branch `claude/T0-build` is green and an APK `launcher-apk` is downloadable.
- Robin installed it using `playbook/ci-and-signing.md` and sees the stock-looking launcher; the app's name is still the upstream name (naming is decided in a later batch).
- The signing key is backed up outside the project (Robin confirmed).
Out of scope: any look change, renaming, app id change.
Android calls needed: none.
Motion or battery impact: none.
Sellable parts: none.
Open questions for the owner:
1. Where should the project folder live? A. `C:\launcher` (short path, recommended). B. In Documents (long path; may hit Windows path-length problems). C. Other.
2. Who types the signing-key password? A. Robin, in their own terminal window (recommended; Claude never sees it). B. Robin skips signing and uses a debug-signed build (cannot update over later builds).
Model for this thread: mid (Sonnet class); the steps are mostly commands and plain-words instructions.
Note for the guide: this is the only task that needs a push before Robin has a design; ask for the yes in plain words.
