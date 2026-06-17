# Clayium Reforged

Clayium Reforged is a NeoForge 26.x / Minecraft 26.x migration of the classic Clayium mod.

This repository is in a Phase 2 material-catalog alpha with the Phase 2 catalog slice accepted. The project keeps the mod id and resource namespace as `clayium`, while modern source code lives under `dev.clayium.clayium`.

## Current Target

- Minecraft: `26.1.2`
- NeoForge: `26.1.2.76`
- Java: `25`
- Gradle Wrapper: `9.2.1`
- ModDevGradle: `2.0.141`
- Mod version: `0.1.0-alpha.1`

## Phase 1 Status

Phase 1 is complete as the first playable NeoForge 26.x base. It covers the early clay loop from raw clay shaping through the Clay Work Table into the first Clay Machine Hull.

Completed:

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
  - in-progress operations lock to the original action to prevent hidden-input loss
  - fixed main/byproduct output slots with output locking
  - hover-only action button highlight
  - hover ghost output preview
  - all 16 legacy Work Table kneading recipes implemented and covered by tests
- Data-driven Clay Work Table recipes:
  - custom `clayium:clay_work_table` recipe type
  - JSON/datapack loading
  - datagen output for the Phase 1 legacy recipe seed
  - shared recipe source for menu logic, JEI display, and tests
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
- Phase 1 datagen:
  - blockstates, models, item models, lang, recipes, block loot tables, and mineable tags
- Phase 1 automated coverage:
  - unit tests for Work Table legacy recipe signatures and registry helpers
  - GameTests for registry surface, Work Table datapack recipes, menu processing, crafting/smelting recipes, block drops, Work Table inventory drops, and wooden-tier harvest behavior

## Phase 2 Status

Phase 2's material-catalog slice is accepted. New cataloged content now flows from `ClayiumContentCatalog` into registration, creative tabs, datagen, tags, recipes, tests, and the legacy migration ledger.

Completed in Phase 2:

- Catalog-driven block, standalone item, and material-form registration.
- New compressed clay blocks:
  - `compressed_clay`
  - `industrial_clay`
  - `advanced_industrial_clay`
- Registered-only early machine hull ids:
  - `dense_clay_machine_hull`
  - `simple_machine_hull`
  - `basic_machine_hull`
- Early material forms:
  - clay and dense clay dust
  - industrial and advanced industrial clay plate, large plate, and dust
  - energized clay dust
- Early progression items:
  - clay/simple/basic circuits and clay circuit board
  - CEE board, CEE circuit, and CEE
  - compressed, industrial, and advanced industrial clay shards
- Clay shovel, clay pickaxe, and clay wrench item ids with vanilla crafting recipes.
- Phase 2 datagen for generated blockstates, 26.x item definitions, models, lang, loot tables, tags, and recipes.
- Catalog-driven legacy tier tooltips for current block items, material forms, tools, circuits, shards, and machine hulls.
- GameTest/JUnit coverage for the expanded registry surface, early material recipes, no temporary hull/circuit recipes, block drops, and harvest tags.

Registered-only means the ids, resources, creative tab entries, loot/tags where applicable, and tests exist, but no temporary survival bridge recipe is added until the real machine production chain is migrated. Simple/basic hull and early circuit production are intentionally left for the next machine phase instead of being bridged with temporary recipes.

## Not Yet Implemented

- Full machine and energy systems.
- 1.7.10 world/save compatibility.
- JEI coverage beyond the Clay Work Table category.
- Jade providers and LDLib-driven production UI adapters.
- IC2, GregTech, NEI, Multipart, MineTweaker, RF, CoFH API, or other old integrations.
- Full material catalog, machine runtime, survival chain for simple/basic hulls and circuits, logistics, area tools, PAN systems, and later-game Clayium content.

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

This is a migration work-in-progress. The current branch represents the completed Phase 1 playable base plus the accepted Phase 2 material catalog slice, not a finished full Clayium port.
