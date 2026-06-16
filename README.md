# Clayium Reforged

Clayium Reforged is a NeoForge 26.x / Minecraft 26.x migration of the classic Clayium mod.

This repository is in an early playable alpha. The project keeps the mod id and resource namespace as `clayium`, while modern source code lives under `dev.clayium.clayium`.

## Current Target

- Minecraft: `26.1.2`
- NeoForge: `26.1.2.76`
- Java: `25`
- Gradle Wrapper: `9.2.1`
- ModDevGradle: `2.0.141`
- Mod version: `0.1.0-alpha.1`

## Phase 1 Progress

Completed in the current foundation pass:

- Clean NeoForge 26.x project scaffold with ModDevGradle 2.x.
- Java package and registry structure for the new codebase.
- Preserved `mod_id = clayium` and `assets/data` namespace `clayium`.
- Phase-one blocks:
  - `dense_clay`
  - `clay_work_table`
  - `raw_clay_machine_hull`
  - `clay_machine_hull`
- Phase-one tools:
  - raw clay rolling pin, slicer, and spatula
  - baked clay rolling pin, slicer, and spatula
- Early Clay and Dense Clay parts.
- Clay Work Table menu and GUI:
  - legacy/Figma slot layout
  - 5-slot internal storage with hidden in-progress input
  - repeated button presses advance processing progress
  - fixed main/byproduct output slots with output locking
  - hover-only action button highlight
  - hover ghost output preview
  - all 16 legacy Work Table kneading recipes implemented and covered by tests
- Basic JEI integration for Clay Work Table recipes:
  - one recipe page per Work Table operation
  - displays input, optional tool, main output, byproduct output, action button, and click count
  - aligned to the Figma `ClayWrokTableJEI` frame
- Crafting and smelting JSON for the first playable loop:
  - dense clay
  - clay work table
  - raw clay tools
  - baked clay tools
  - raw clay machine hull
  - clay machine hull from smelting
- Optional dev/runtime dependencies resolved for integration work:
  - JEI, with the first Work Table category implemented
  - Jade, pending providers
  - LDLib2, pending broader production UI adapters
- Removed unused Dense Clay large ball legacy item/resources.

## Not Yet Implemented

- Full machine and energy systems.
- 1.7.10 world/save compatibility.
- JEI coverage beyond the Clay Work Table category.
- Jade providers and LDLib-driven production UI adapters.
- IC2, GregTech, NEI, Multipart, MineTweaker, RF, CoFH API, or other old integrations.
- Complete datagen providers.
- Full data-driven GameTest coverage for Work Table progression and the full Phase 1 loop.

## Development Notes

The old decompiled mod sources are reference material only. Do not copy the old CFR Java packages wholesale into `src/main/java`.

On this Windows workspace, Gradle file-system watching has hung during early detection, so local verification should use `--no-watch-fs`.

Recommended setup:

```powershell
$env:GRADLE_OPTS='-Dorg.gradle.java.home=C:\Progra~1\Java\jdk-25'
```

Useful checks:

```powershell
.\gradlew --version --no-watch-fs
.\gradlew tasks --all --no-watch-fs
.\gradlew test --stacktrace --no-watch-fs --console=plain
.\gradlew clean build --stacktrace --no-watch-fs --console=plain
.\gradlew runData --stacktrace --no-watch-fs --console=plain
.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain
```

Manual client smoke:

```powershell
.\gradlew runClient --stacktrace --no-watch-fs --console=plain
```

## Repository Status

This is a migration work-in-progress. The current branch represents the Phase 1 playable base, not a finished port.
