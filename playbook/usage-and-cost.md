# Keeping usage and cost low

Claude Code usage is limited by your plan. A launcher project has many small tasks; these habits keep it affordable.

## The rules
1. Few parallel threads: 2 or 3 at a time. Each thread starts cold and re-reads the project.
2. Right model for the job: cheapest for lookups, test steps and checks of facts (`api-scout`); mid (Sonnet class) for writing and fixing code and for the guide; strongest (Opus class) only for planning a new feature or a bug that survived two fixes.
3. The deep check (`final-check`) is expensive: run it ONLY when you say so, once per push batch on the merged branch, plus risky changes (gestures, rotation, very many files). Never for notes or one-line fixes. If you skip it, the guide says so in READY TO PUSH.
4. Batch pushes: one build and one phone test per batch, not per task.
5. Preview before code: a wrong design costs a whole build cycle; a wrong preview costs a picture.
6. Archive finished threads. Start a fresh session for each work session; the guide re-reads `ops/STATUS.md` and `ops/decisions.md`, not the history.
7. Short briefs. Point to files; do not paste them. Threads read only CLAUDE.md, their STATUS row and their task folder.
8. Do not use `/loop`, schedules or idle polling in threads.
9. Every rebuild costs about 10 minutes of your time too: ask the thread to re-read imports and run the screenshot test first (`seeing-the-ui.md`).

## Where the tokens go (rough)
Reading code is cheap per file, expensive per folder. Search first, open second. Pictures cost more than text; view only the pictures that matter.

## Disk and Windows tips
- Each git worktree is a full copy of the source and Gradle caches grow large (several GB each). Delete worktrees of archived threads (`git worktree remove <folder>`), and run `git worktree prune`.
- Windows long paths: paths over 260 characters break git and Gradle. Keep the project folder close to the drive root (`C:\launcher`) and worktree names short. If you hit it: `git config --global core.longpaths true`, and in Windows settings turn on long paths (search "Enable Win32 long paths").
- Gradle cache can be cleared safely: delete the `.gradle` folder in your user folder when disk is full (the next build re-downloads).
- Build outputs (`build/` folders) are not needed in git; make sure they are in `.gitignore`.
- Screenshot pictures: keep only the final ones; delete drafts.

## What a typical first version costs
One interview session, one or two preview rounds, three to six build threads, one or two push batches, one fix round. If your plan is small, do the Home screen first and stop there; a launcher with only a finished Home is already useful.
