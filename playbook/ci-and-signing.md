# Building the APK on GitHub and installing it

An APK is the install file of an Android app. GitHub builds it for you in the cloud, so you need no Android tools. A build takes about 5 to 12 minutes.

## One-time setup (the guide does most of it)
1. Fork Kvaesitso into your GitHub account and clone it: `gh repo fork MM2-0/Kvaesitso --clone`.
2. Copy `scripts/build-apk.yml.template` to `.github/workflows/build-apk.yml` and fill `<START_NUMBER>`. It builds on every push to a branch `claude/**`. Leave upstream workflow files alone.
3. Make ONE signing key for the life of the project. If you change the key later, the phone refuses to update the app and you must uninstall it first (losing its data). The guide runs, in a terminal:
   `keytool -genkeypair -v -keystore keystore.jks -alias launcher -keyalg RSA -keysize 2048 -validity 10000`
   It asks YOU for a password in your own window (choose one, write it in a password manager). The guide never asks you to tell it the password. Java comes with Android Studio or install a free JDK 17.
4. Back up `keystore.jks` and the passwords OUTSIDE the project (USB stick or cloud drive). Never commit them. Put `*.jks` and `*.b64` in `.gitignore`.
5. Set the four secrets with the GitHub tool (the tool reads the value from your terminal; Claude does not see it):
   - `gh secret set KEYSTORE < keystore.b64` (the key file as base64 text: `base64 -w0 keystore.jks > keystore.b64`, on Mac `base64 -i keystore.jks -o keystore.b64`)
   - `gh secret set KEYSTORE_PASSWORD`, `gh secret set SIGNING_KEY_ALIAS`, `gh secret set SIGNING_KEY_PASSWORD` (each asks you to type the value)
   If `gh` is missing: install GitHub CLI and run `gh auth login`. If you prefer buttons: GitHub website > your repository > Settings > Secrets and variables > Actions > New repository secret.
6. Never ask for, store or paste passwords, tokens or bank details into chat or files.

## Build (after the person's yes to a push)
```
git push -u origin claude/<branch>
gh run watch
```
Green = the APK exists. Red = the guide reads the log (`gh run view --log-failed`), fixes locally, and asks again before pushing.

## Install on the phone (give the person these steps)
1. On your phone's browser, open github.com, sign in, open your repository > Actions > the newest run with a green tick. (Easier: the guide gives you the exact link.)
   You should see: a list with "Artifacts" at the bottom, named `launcher-apk`.
2. Tap `launcher-apk` to download. It arrives as a zip file; open it in your Files app and tap Extract or the file name to unzip.
   You should see: a file ending in `.apk`.
3. Tap the `.apk`. The first time, Android says "For your security, your phone is not allowed to install unknown apps from this source." Tap Settings, switch on "Allow from this source" for the app you used (Files or Chrome), press Back, tap Install.
   You should see: "App installed". Tap Done (not Open yet).
4. Press the Home button. Android asks which home app to use. Choose the new launcher and tap "Always". To go back any time: Settings > Apps > Choose default apps > Home app > your old launcher.
   You should see: the new Home screen.
Report: it installed yes/no; what you see on Home. If the install says "App not installed" it is usually because an older build was signed with a different key: uninstall the old one and try again, and tell the guide.

Computers: if you are on the computer, `gh run download` saves the APK; send it to the phone by cable, cloud drive or email.

## Updating
Each new build installs over the last one (the version number rises with the run number). Settings are kept. Granted permissions are kept.

## Permissions the launcher may ask for
Notification access (for badges and a list of notifications), Modify system settings (auto-rotate chip), location (weather), contacts and calendar (search). Grant only what you want; each feature says what it needs. Android shows these screens; the guide writes the steps.
