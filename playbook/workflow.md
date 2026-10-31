# The workflow

One loop, repeated for each batch of screens. The person only does the parts marked YOU.

## The loop
1. SURVEY (guide, phone-stylist): list the screens in this batch, where the code is, how they look now, what is inside the launcher (changeable) and what is outside (assets and steps only).
2. PREVIEW (phone-stylist): a picture of 2 or 3 variants where a real design choice exists. No code yet. Preview pictures are made as a web page plus a PNG (see `seeing-the-ui.md`, "Preview pictures").
3. ASK (YOU): multiple choice, at most four questions per batch, recommendation first, costs stated. The guide writes each answer to `ops/decisions.md`.
4. BRIEF (guide): `tasks/<id>/brief.md` per task (`ops/templates/brief.md`), STATUS row added.
5. BUILD (threads): one thread per task, in its own worktree and branch `claude/<id>`. Commits locally. Writes testlist, handoff, lessons. Never pushes.
6. MERGE (guide): finished branches of the batch are merged into `claude/integrate-<n>`. The guide reads the diff itself.
7. CHECK: the gate (`ops/gate.md`) always; the deep check (`final-check`, Opus) ONLY when YOU ask.
8. READY TO PUSH (guide to YOU): plain words: what changes on the phone, what was verified, what was NOT. YOU answer yes or no. A yes covers this batch only.
9. BUILD ON GITHUB (guide): push once, watch the build, give the install steps (`ci-and-signing.md`).
10. TEST (YOU): follow `tasks/<id>/testlist.md` on the phone and report.
11. FIX: bugs go into `tasks/<id>/bugs.md`; a NEW thread fixes them from brief + handoff + bugs. Two failed fixes of one bug: stop, write the root cause, escalate to the strongest model.
12. HAND OFF: STATUS row to DONE, thread archived, lessons moved to `ops/lessons-inbox.md`.

## Batches
Group related tasks (for example "Home screen", then "search layer plus apps page", then "landscape"). Do all steps of a batch locally, then ONE push. This saves 10 minutes of build and one phone test per task.

## Worktrees
Each thread works in its own git worktree (a separate folder with its own branch), so two threads never overwrite each other. Rules: independent tasks only in parallel; never two threads on the same files; copy `local.properties` into a fresh worktree if you compile locally (it is not in git). Windows: keep the project path short (see `usage-and-cost.md`, long paths).

## Merging into an integration branch
`git checkout -b claude/integrate-<n> main`, then `git merge claude/<id>` for each finished task. If two branches touch one file, the guide resolves it and says so. Upstream-file edits are kept in separate commits so a merge with newer Kvaesitso stays possible.

## One push per batch
Only the guide pushes, only after the person's yes for that batch. Pushing to a `claude/**` branch starts the build automatically.

## Rules that never bend
- Design decisions are the person's, as multiple choice.
- Owner steps: plain words, numbered, at most 3 do-lines per step, visible result first.
- Say what was NOT verified.
- Few parallel threads, cheap models for cheap jobs (`usage-and-cost.md`).
