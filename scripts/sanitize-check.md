# Sanitise check (run before every zip or share)

Goal: prove the kit holds no personal data and none of the source project's art or names. Run from the folder that CONTAINS `launcher-studio-kit/`. Every command must print nothing (except the allowed hits listed). Use the Grep tool, or Git Bash:

```
cd launcher-studio-kit
grep -rniE "udrid|shadow|@gmail|@[a-z0-9-]+\.(com|net|org)" . 
grep -rniE "SM-G781U|galaxy s20|S20 FE" .
grep -rniE "deliverance|kingdom come|warhorse" .
grep -rnE "\bC5\b|medieval|veritas|nosce|oxblood|unifraktur|cinzel|im fell" -i .
grep -rniE "C9A55A|8C6D3A|6B5430|4A3A20|E6D9B8|F2E6C6|A89A76|6A1D16|A8352B|1D1A14|12100C|0A0907|F0A050|ECDFBF" .
grep -rniE "C:\\\\Users|/Users/|medieval-keystore|secrets\.txt|ghp_|github_pat_|AKIA|BEGIN (RSA|PRIVATE)" .
grep -rniE "password *[:=]|api[_-]?key *[:=]|token *[:=]" .
find . -type f \( -iname "*.png" -o -iname "*.jpg" -o -iname "*.ttf" -o -iname "*.otf" -o -iname "*.jks" -o -iname "*.svg" \)
```

## Allowed hits (and only these)
- `LICENSE`: the credit line contains the project name "Deliverance" and the author handle (udrid) the owner chose. This is the required credit; nothing else may.
- `scripts/sanitize-check.md` (this file) and `MANIFEST.md`/`CHANGELOG.md`: they list the search words themselves.
- Words like "password" in sentences that tell the user NOT to share one (`playbook/ci-and-signing.md`, `CLAUDE.md.template`, agent files).
- `kit-guide.md` line `gh repo fork MM2-0/Kvaesitso`: public upstream project name.
- The generic words `keystore.jks`, `KEYSTORE_PASSWORD` and `SIGNING_KEY_PASSWORD` in the build workflow and signing guide are names of files and secrets, not values.
- "Samsung" appears only as an example phone make in `ops/templates/api-facts.md` and `README.md`.
- The example fonts "Lora" and "Nunito" and made-up hex colours in `example-dry-run/`: invented for the example.
- The last command (`find`) must list no files at all: the kit has no images, fonts or keys.

## Checklist
- [ ] All greps above clean except the allowed hits.
- [ ] No file under `ops/` or `tasks/` of the real project was copied in (the kit contains only templates).
- [ ] `LICENSE` has the author's name filled in by whoever shares it (or still the placeholder if you share your own copy privately).
- [ ] The README says the kit is derived from a real project and contains none of its art.
- [ ] `MANIFEST.md` lists exactly the files that exist (`Get-ChildItem -Recurse -File` count matches).
- [ ] The zip opens and shows `launcher-studio-kit/` at the top with `.claude/agents/` inside.
