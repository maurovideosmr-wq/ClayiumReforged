# Devlogs

## 2026-06-16 Phase 1 Start

- Current goal: implement Clayium Reforged Phase 1 for NeoForge 26.x.
- Changed files: initialized Git and copied official MDK Gradle wrapper; creating Gradle, source, resource, docs skeleton.
- Key decisions: keep `mod_id=clayium`, display name `Clayium Reforged`, package `dev.clayium.clayium`, NeoForge `26.1.2.76`, ModDevGradle `2.0.141`, Java 25.
- Verification state: Java 25 confirmed; Gradle wrapper not yet run.
- Unresolved TODOs: implement phase-one items, Clay Work Table, datagen, tests, DOX closeout.
- Next commands: `.\gradlew --version`, then compile-driven API implementation.

## 2026-06-16 Phase 1 Playable Core Draft

- Current goal: finish a compile-checked first playable loop for dense clay, Clay Work Table processing, raw clay tools, raw hull, and clay machine hull.
- Changed files: `src/main/java/dev/clayium/clayium/**`, `src/main/resources/assets/clayium/**`, `src/main/resources/data/clayium/recipe/**`, `src/AGENTS.md`, root `AGENTS.md`.
- Key decisions: Clay Work Table uses a 5-slot block entity and server-side `clickMenuButton` processing.
- Verification state: official Maven confirms NeoForge `26.1.2.76` and ModDevGradle `2.0.141`; `.\gradlew --version` requires `GRADLE_OPTS=-Dorg.gradle.java.home=C:\Progra~1\Java\jdk-25` because user-level Gradle properties force JDK 21; `tasks --all` reached a `mods.clayium` Gradle configuration error, then was fixed by declaring the mod before `neoForge.unitTest`.
- Unresolved TODOs: compile and repair 26.x API mismatches; add proper NeoForge datagen providers or mark current JSON as bootstrapped resources; add GameTests for work table operations and hull smelting; rerun the full verification plan.
- Next commands: rerun Gradle with file-system watching disabled, then run `.\gradlew clean build --stacktrace`.

## 2026-06-16 Gradle VFS Watch Hang

- Current goal: unblock Gradle configuration for the NeoForge 26.x scaffold.
- Changed files: `gradle.properties`, `docs/devlogs.md`.
- Key decisions: project Gradle now sets `org.gradle.vfs.watch=false` because thread dumps showed `tasks --all` stuck in `DefaultWatchableFileSystemDetector.detectUnsupportedFileSystems` before project configuration began.
- Verification state: `jcmd` thread dumps confirmed the stuck daemon was in Gradle file-system watch detection; no project compile had started.
- Unresolved TODOs: cancel or let the old running Gradle process exit, then rerun `tasks --all` with the updated property and `--no-watch-fs`.
- Next commands: `.\gradlew tasks --all --no-configuration-cache --no-watch-fs --console=rich --stacktrace`, followed by `.\gradlew clean build --no-watch-fs --stacktrace`.

## 2026-06-16 Phase 1 Verification Pass

- Current goal: complete compile and smoke verification for the first playable Clayium Reforged scaffold.
- Changed files: `build.gradle`, `gradle.properties`, `settings.gradle`, `src/main/java/dev/clayium/clayium/**`, `src/main/resources/**`, `src/test/java/dev/clayium/clayium/menu/ClayWorkTableOperationsTest.java`, `AGENTS.md`, `docs/devlogs.md`.
- Key decisions: client screen code uses the 26.x `GuiGraphicsExtractor` rendering extraction API; Work Table recipe data now records `ToolRequirement` so JUnit can verify deterministic recipe metadata without constructing runtime `ItemStack`s before registries are fully bound.
- Verification state: `.\gradlew --version --no-watch-fs` passed with `GRADLE_OPTS=-Dorg.gradle.java.home=C:\Progra~1\Java\jdk-25`; user-provided `.\gradlew tasks --all --console=rich --info --no-watch-fs` passed and showed `runClient`, `runServer`, `runData`, `runGameTestServer`, and `test`; `.\gradlew clean build --no-watch-fs --stacktrace` passed; `.\gradlew test --no-watch-fs --stacktrace` passed; `.\gradlew runData --no-watch-fs --stacktrace` passed with no providers and no generated files; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed as a startup smoke check; `git diff --check` passed.
- Unresolved TODOs: add real data providers instead of relying on hand-written JSON; add actual 26.x data-driven GameTests for Clay Work Table processing and raw hull smelting; decide how to run `runServer` and `runClient` smoke checks without leaving interactive processes open.
- Next commands: implement data providers and GameTest resources, then rerun `runData`, `runGameTestServer`, `runServer`, and `runClient` with `--no-watch-fs`.

## 2026-06-16 Optional Integration Runtime Setup

- Current goal: add JEI, Jade, and LDLib/LDLib2 to the 26.x dev/test environment without implementing integration adapters yet.
- Changed files: `build.gradle`, `gradle.properties`, `AGENTS.md`, `docs/devlogs.md`.
- Key decisions: use Jared Maven for JEI and Modrinth Maven for Jade/LDLib; keep all three integrations on `compileOnly` plus `localRuntime` so APIs are visible to IDE/compile and jars are present in local runs, but Clayium does not publish or require them.
- Verification state: resolved `compileClasspath` with JEI `29.6.2.31`, Jade `26.1.3+neoforge`, and LDLib `mc26.1.x-26.1.2.20-neoforge`; resolved `localRuntime` with JEI/Jade/LDLib jars; `.\gradlew build --no-watch-fs --stacktrace` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed and loaded `jei`, `jade`, and `ldlib2`. `.\gradlew clean build --no-watch-fs --stacktrace` could not clean because `build/moddev/artifacts/minecraft-patched-26.1.2.76.jar` was locked by another process during manual testing.
- Unresolved TODOs: rerun `clean build` after the manual client/test process releases the locked file; implement actual JEI/Jade/LDLib integration only in later scoped phases.
- Next commands: after closing client/manual tests, run `.\gradlew clean build --no-watch-fs --stacktrace`, then continue with datagen and GameTest work.

## 2026-06-16 Furnace Recipe Ingredient Fix

- Current goal: repair the missing three clay crafting tool smelting recipes observed during manual testing.
- Changed files: `src/main/resources/data/clayium/recipe/clay_rolling_pin_from_smelting.json`, `src/main/resources/data/clayium/recipe/clay_slicer_from_smelting.json`, `src/main/resources/data/clayium/recipe/clay_spatula_from_smelting.json`, `src/main/resources/data/clayium/recipe/clay_machine_hull_from_smelting.json`, `src/test/java/dev/clayium/clayium/data/RecipeResourceFormatTest.java`, `docs/devlogs.md`.
- Key decisions: old Clayium registers smelting from raw rolling pin, raw slicer, and raw spatula into the baked tools; the 26.x JSON bug was the ingredient object form, so all affected furnace recipes now use string ingredients. The raw hull recipe had the same parse error and was fixed with the tools.
- Verification state: `.\gradlew test --no-watch-fs --stacktrace` passed; `.\gradlew build --no-watch-fs --stacktrace` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed; `run/logs/latest.log` has no `Couldn't parse data file` matches after the run.
- Unresolved TODOs: `clean build` still needs a clear run directory/build artifact lock window if manual client testing keeps files open.
- Next commands: rerun `.\gradlew clean build --no-watch-fs --stacktrace` after closing any client/manual test process, then continue with datagen/GameTest coverage.
