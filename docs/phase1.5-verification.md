# Phase 1.5 Verification

Last updated: 2026-06-17

Phase 1.5 owns the verification pass after freezing the Phase 1 baseline and creating the migration ledger. Check items only after the command or manual inspection actually runs.

## Environment

- Baseline commit: `c3fedce Complete phase 1 playable base`.
- Expected Java: `25`
- Expected Gradle Wrapper: `9.2.1`
- Expected NeoForge: `26.1.2.76`
- Required local setup:

```powershell
$env:GRADLE_OPTS='-Dorg.gradle.java.home=C:\Progra~1\Java\jdk-25'
```

Always add `--no-watch-fs` in this workspace.

## Command Checklist

- [ ] `.\gradlew --version --no-watch-fs`
  - Result:
  - Notes:
- [ ] `.\gradlew tasks --all --no-watch-fs --stacktrace`
  - Result:
  - Confirmed tasks: `test`, `runClient`, `runServer`, `runData`, `runGameTestServer`
- [ ] `.\gradlew test --no-watch-fs --stacktrace`
  - Result:
  - Notes:
- [ ] `.\gradlew clean build --no-watch-fs --stacktrace`
  - Result:
  - Notes:
- [ ] `.\gradlew runData --no-watch-fs --stacktrace`
  - Result:
  - Generated-resource diff review:
- [ ] `.\gradlew runGameTestServer --no-watch-fs --stacktrace`
  - Result:
  - Required tests observed:
- [ ] `.\gradlew runServer --no-watch-fs --stacktrace`
  - Result:
  - Dedicated-server client-class loading notes:
- [ ] `.\gradlew runClient --no-watch-fs --stacktrace`
  - Result:
  - Manual client inspection notes:
- [ ] `git diff --check`
  - Result:
  - Notes:

## Manual Client Checklist

- [ ] Creative tab shows Phase 1 blocks, tools, and parts in the expected grouped order.
- [ ] Clay Work Table opens without log errors.
- [ ] Clay Work Table slot positions match the current GUI texture and do not look raised.
- [ ] Work Table action buttons highlight yellow only on hover.
- [x] Work Table ghost output preview appears on valid hover with input present.
  - 2026-06-17 user manual check: byproduct ghost/tooltip is present for `Cut round` (`Clay Ring` and `Small Clay Disc`); initial report was tooltip occlusion, not missing output data.
- [ ] Repeated button presses advance progress one click at a time.
- [ ] In-progress operation cannot be switched to another button.
- [ ] Output slot locking prevents crafting into incompatible occupied output slots.
- [ ] JEI Clay Work Table category appears.
- [ ] JEI Clay Work Table recipe pages show input, optional tool, main output, byproduct output, action button, and click count.
- [ ] JEI Work Table arrow and button texture positions match the Figma-derived layout after the last offset pass.

## Last Known Phase 1 Evidence

- `README.md` records Phase 1 as playable and lists completed functionality.
- `docs/devlogs.md` records prior passing `test`, `clean build`, `runData`, `runGameTestServer`, and `git diff --check` passes during Phase 1.
- User gameplay testing accepted the Phase 1 batch before the `c3fedce` baseline commit.

Do not treat this section as a substitute for the Phase 1.5 command checklist above.
