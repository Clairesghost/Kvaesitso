# Bugs: B2-home

| # | Symptom (what the owner saw, in their words) | Root cause (leave empty until known) | Fix attempts | Status |
|---|---|---|---|---|
| 1 | With the charger plugged in, the camera ring shows no green line. | Probable, not proven on the phone: the light was a 1 to 3 px line drawn right on the hole's edge (radius 0.325 x ring, stroke 0.035 x ring), where Samsung's black mask around the punch-hole most likely covers it. Second possible cause: charging only counted EXTRA_STATUS CHARGING or FULL. | 1 (stage 3: 3 dp band, 2 dp outside the hole edge, mock-up green #5ad17a; EXTRA_PLUGGED != 0 also counts as charging) | fix written, not yet tested on phone |

Phone, Android version and build number where it happened: Galaxy S20 FE (SM-G781U1/DS), Android 13, stage 1 build of B2-home.
Screenshot or screen recording from the owner (path): none.
