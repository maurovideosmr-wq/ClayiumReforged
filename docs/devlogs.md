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

## 2026-06-17 Clay Work Table LDLib Atlas Pass

- Current goal: adapt Clay Work Table GUI to the Figma `CLAYGUI` frame and the new compact LDLib atlas.
- Changed files: `src/main/java/dev/clayium/clayium/client/screen/ClayWorkTableScreen.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableOperations.java`, `src/main/resources/assets/clayium/textures/gui/container/clay_work_table.png`, `src/main/resources/assets/clayium/lang/en_us.json`, `src/test/java/dev/clayium/clayium/menu/ClayWorkTableOperationsTest.java`, `build.gradle`, `gradle.properties`, `src/main/templates/META-INF/neoforge.mods.toml`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: the packaged GUI texture is now a 96x80 atlas instead of a full 256x256 GUI sheet; the screen draws Figma-aligned background and slot primitives, uses LDLib2 `GUIContext`/`SpriteTexture` for arrow and action button slices, and returns the Work Table to the legacy/Figma 4-slot layout.
- Verification state: `.\gradlew test --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew build --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew runData --no-watch-fs --stacktrace --console=plain` passed with no generated files; `.\gradlew runGameTestServer --no-watch-fs --stacktrace --console=plain` passed in a dedicated-server environment; after stopping Gradle daemons, `.\gradlew clean build --no-watch-fs --stacktrace --console=plain` passed.
- Unresolved TODOs: run client smoke to inspect exact pixel placement and action-button states in Minecraft; `run/logs/latest.log` and `run/logs/debug.log` were locked by another process during `runData` startup but did not fail the task; the first `clean build` attempt hit a lock on `build/moddev/artifacts/minecraft-patched-26.1.2.76.jar` and succeeded after `.\gradlew --stop`.
- Next commands: `.\gradlew runClient --no-watch-fs --stacktrace --console=plain` for visual inspection, then adjust any pixel offsets observed in-game.

## 2026-06-17 Clay Work Table Feedback Repair

- Current goal: repair Clay Work Table GUI and processing feedback from manual review: hover-only yellow buttons, ghost output previews, fixed-slot output locking, and legacy repeated-button processing.
- Changed files: `src/main/java/dev/clayium/clayium/client/screen/ClayWorkTableScreen.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableOperations.java`, `src/main/java/dev/clayium/clayium/block/entity/ClayWorkTableBlockEntity.java`, `src/main/resources/assets/clayium/lang/en_us.json`, `src/test/java/dev/clayium/clayium/menu/ClayWorkTableOperationsTest.java`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: restored a hidden slot 4 for in-progress input and 3 menu data values for `cookTime`, `timeToCook`, and `cookingMethod`; one button press advances one unit of work; completion clears highlight state; outputs merge only into their fixed visible slots; same-input recipes now choose the largest matching input count like the 1.7.10 recipe map.
- Verification state: `.\gradlew test --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew build --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runData --stacktrace --no-watch-fs --console=plain` passed with no generated files; `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain` passed as dedicated-server smoke; `.\gradlew clean build --stacktrace --no-watch-fs --console=plain` passed.
- Unresolved TODOs: add real 26.x GameTests for Work Table button progression and output locking; run client smoke/manual visual inspection for exact pixel feel and hover ghost presentation.
- Next commands: `.\gradlew runClient --stacktrace --no-watch-fs --console=plain`, then test in-game with blocked output slots, four hand-knead presses, and hover ghost previews.

## 2026-06-17 Clay Work Table Manual Feedback Follow-up

- Current goal: address manual feedback that slot frames looked raised and single-item Work Table processing could not continue after the first click; verify all old Work Table recipes are present.
- Changed files: `src/main/java/dev/clayium/clayium/client/screen/ClayWorkTableScreen.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableOperations.java`, `src/test/java/dev/clayium/clayium/menu/ClayWorkTableOperationsTest.java`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: Work Table slots now render as inset frames with dark top/left and light bottom/right edges; client button availability can continue the current method from synced `cookingMethod/timeToCook` because the hidden in-progress slot is not a network-visible slot; server clicks still validate the hidden slot strictly.
- Verification state: old `ClayWorkTableRecipes` contains 16 kneading recipes and `ClayWorkTableOperations` now has a unit test asserting those 16 signatures; `.\gradlew test --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew build --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain` passed.
- Unresolved TODOs: run client smoke/manual visual inspection for the updated inset slot styling and the single-item repeated-click path.
- Next commands: `.\gradlew clean build --stacktrace --no-watch-fs --console=plain`, then `.\gradlew runClient --stacktrace --no-watch-fs --console=plain` for visual/manual verification.

## 2026-06-17 Clay Work Table JEI Adapter

- Current goal: add JEI recipe viewing for Clay Work Table operations using the Figma `ClayWrokTableJEI` layout and old NEI behavior as reference.
- Changed files: `src/main/java/dev/clayium/clayium/client/jei/**`, `src/main/resources/assets/clayium/lang/en_us.json`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: JEI remains optional (`compileOnly` plus `localRuntime`); the category renders one operation per recipe page, displays the required input, optional tool cycle, main output, optional byproduct, the selected action button, and the work tick count above that button.
- Verification state: `.\gradlew test --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew build --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runData --stacktrace --no-watch-fs --console=plain` passed with no generated files; `.\gradlew runServer --stacktrace --no-watch-fs --console=plain` reached `Done` and was manually stopped after the stdin `stop` command did not terminate the long-running server process; a controlled `.\gradlew runClient --stacktrace --no-watch-fs --console=plain` smoke reached `Sound engine started` and created `jei:textures/atlas/gui.png-atlas` before being manually stopped.
- Unresolved TODOs: inspect the JEI category visually in client and confirm the Figma-aligned pixels in-game; Jade logged `Failed to collect shearable blocks` during server startup, unrelated to Clayium's JEI adapter but worth tracking before treating Jade as stable.
- Next commands: run client manually, open JEI's Clay Work Table category, and compare input/tool/output/button positions against the Figma `ClayWrokTableJEI` frame.

## 2026-06-17 Registry Guardrails and Work Table Inventory Drop

- Current goal: stop Phase 1 registry duplication from spreading and make Clay Work Table inventory dropping explicit.
- Changed files: `src/main/java/dev/clayium/clayium/registry/ClayMaterial.java`, `src/main/java/dev/clayium/clayium/registry/ClayPartType.java`, `src/main/java/dev/clayium/clayium/registry/PartRegistry.java`, `src/main/java/dev/clayium/clayium/registry/MachineHullTier.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumItems.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumBlocks.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumCreativeTabs.java`, `src/main/java/dev/clayium/clayium/block/entity/ClayWorkTableBlockEntity.java`, `src/test/java/dev/clayium/clayium/registry/PartRegistryTest.java`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: keep `org.gradle.java.home` in `gradle.properties` so local Gradle keeps using Java 25; register clay and dense clay parts through `ClayMaterial x ClayPartType` in `PartRegistry`; keep common `ClayiumItems.CLAY_*` and `DENSE_CLAY_*` aliases for readable recipe and menu code; group the creative tab as blocks, tools, then material parts; keep Work Table hardcoded operations only as a temporary legacy bridge until a custom JSON recipe type is implemented.
- Verification state: official NeoForge docs were checked for `DeferredRegister` and container/block-entity behavior; `.\gradlew test --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew clean build --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain` passed as a dedicated-server smoke check.
- Unresolved TODOs: add a real GameTest that places a Clay Work Table, inserts inventory including the hidden in-progress slot where possible, destroys the block, and asserts dropped stacks; move Clay Work Table recipes to a custom recipe type plus JSON/datagen before expanding beyond Phase 1.
- Next commands: run `git diff --check`, then continue with recipe/data-driven migration helpers or the Work Table drop GameTest.

## 2026-06-17 Work Table Datapack Recipes and Block Drops

- Current goal: make Clay Work Table recipes datapack/custom-recipe driven and fix Phase 1 block self-drops plus harvest-tool tags.
- Changed files: `build.gradle`, `src/main/java/dev/clayium/clayium/Clayium.java`, `src/main/java/dev/clayium/clayium/data/**`, `src/main/java/dev/clayium/clayium/recipe/**`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableOperations.java`, `src/main/java/dev/clayium/clayium/client/**`, `src/main/java/dev/clayium/clayium/registry/ClayiumBlocks.java`, `src/generated/resources/data/**`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: add `clayium:clay_work_table` recipe serializer/type and sync it through NeoForge recipe sync; use `ClayWorkTableOperations` only as the default legacy recipe seed for datagen; represent recipe outputs with `ItemStackTemplate` to avoid datagen-time component binding; source JEI from the synced recipe cache; generate block loot and mineable tags with server datagen; exclude datagen `.cache` from packaged resources.
- Verification state: old 1.7.10 source shows raw clay machine hull uses shovel harvest level 0, machine hull and machine blocks use pickaxe harvest level 0, and Clay Work Table has no explicit harvest tool; `.\gradlew --version --no-watch-fs` passed with Java 25; `.\gradlew tasks --all --no-watch-fs --stacktrace` passed and showed `runClient`, `runServer`, `runData`, `runGameTestServer`, and `test`; `.\gradlew test --no-watch-fs --stacktrace` passed; `.\gradlew runData --no-watch-fs --stacktrace` passed and generated 16 Work Table recipe JSON files, 4 block loot tables, and 2 mineable tag files; `.\gradlew clean build --no-watch-fs --stacktrace` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed as a dedicated-server load check; jar inspection confirmed `.cache` is not packaged.
- Unresolved TODOs: add real 26.x GameTests for Work Table datapack recipe processing, block drops, and harvest-tool behavior; run client/manual JEI inspection after custom recipe sync to confirm recipe pages still appear.
- Next commands: run `git diff --check`, inspect generated JSON diffs, then commit if the user accepts the P2 scope.

## 2026-06-17 Phase 1 GameTest Suite

- Current goal: replace the Phase 1 dedicated-server smoke check with actual GameTests for the playable closure and legacy harvest behavior.
- Changed files: `AGENTS.md`, `src/AGENTS.md`, `src/main/java/dev/clayium/clayium/Clayium.java`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTestInstance.java`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTests.java`, `docs/devlogs.md`.
- Key decisions: register Phase 1 tests through `RegisterGameTestsEvent` using a tiny custom direct-function `GameTestInstance`; keep all tests required; cover registry ids, data-driven Work Table recipe cache, menu button processing and output locking, crafting/smelting recipes, self-drops, Work Table inventory drops, and wooden-tier harvest acceptance for tool-tagged Clayium machine blocks.
- Verification state: official NeoForge 26.1 Game Tests docs were checked for `RegisterGameTestsEvent`, custom test instances, and `runGameTestServer`; `.\gradlew compileJava --no-watch-fs --stacktrace` passed; `.\gradlew test --no-watch-fs --stacktrace` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed with JEI/Jade/LDLib in local runtime and logs showed `clayium:gametest/default` ran 5 tests; the first `.\gradlew clean build --no-watch-fs --stacktrace` was blocked by a Windows file lock on `build/moddev/artifacts/minecraft-patched-26.1.2.76.jar`, then `.\gradlew --stop --no-watch-fs` released daemon locks and the repeated `clean build` passed; `git diff --check` passed with CRLF warnings only.
- Unresolved TODOs: inspect the JEI category visually after custom recipe sync; extend the GameTest suite when Phase 2 adds more machine tiers and recipe types.
- Next commands: review and commit the GameTest/datapack recipe batch when accepted.

## 2026-06-17 Full Migration Roadmap

- Current goal: inspect the Phase 1 NeoForge 26.x code and the old compiling/decompiled Clayium 0.4.6.36 reference, then write a phased full-port roadmap.
- Changed files: `docs/clayium-migration-roadmap.md`, `docs/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: treat Phase 1 as accepted baseline; split the rest of the migration by durable systems rather than raw file count; place a Phase 1.5 baseline/ledger step before expanding materials or machines; separate materials, machine framework, early machine loop, full material economy, advanced machines, logistics, area/PAN/tools, integrations, and release hardening.
- Verification state: inspected root/docs/src DOX, README, devlogs, build metadata, current new code structure, and old CFR domains; no source code was changed for this roadmap task; `git diff --check` passed with existing LF/CRLF warnings only; `docs/clayium-migration-roadmap.md` has no trailing whitespace matches.
- Unresolved TODOs: create the actual legacy migration ledger in a later task; run client visual checks for Work Table/JEI before Phase 2; decide whether Phase 2 starts with compressed clay/materials or a narrower material slice.
- Next commands: `git diff --check`, review `docs/clayium-migration-roadmap.md`, then start the Phase 1.5 ledger or Phase 2 material slice after baseline commit.

## 2026-06-17 Clay Work Table In-Progress Lock

- Current goal: prevent Clay Work Table from deleting hidden in-progress input when players click another valid operation while a recipe is partially complete.
- Changed files: `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTests.java`, `src/AGENTS.md`, `docs/devlogs.md`.
- Key decisions: remove the old `buttonState == 2` reset path; while `cookingMethod` is nonzero, only that same button id can continue processing. Different buttons now stay unavailable even if the visible input has a valid recipe, so changing input/tool mid-process cannot clear the reserved hidden stack.
- Verification state: `.\gradlew compileJava --no-watch-fs --stacktrace` passed; `.\gradlew test --no-watch-fs --stacktrace` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace` passed and logs showed `clayium:gametest/default` plus 108 required tests passed; `.\gradlew clean build --no-watch-fs --stacktrace` passed; `git diff --check` passed with CRLF warnings only.
- Unresolved TODOs: manually inspect the client screen to ensure disabled buttons during in-progress work feel clear enough; extend this same lock concept to future machines that reserve inputs across multiple clicks or ticks.
- Next commands: review and commit the Work Table lock fix with the pending Phase 1 batch.

## 2026-06-17 Phase 1.5 Ledger Start

- Current goal: start Phase 1.5 by freezing the Phase 1 baseline as an auditable reference, creating the legacy migration ledger, and splitting naming/deferred/verification decisions into dedicated docs.
- Changed files: `AGENTS.md`, `docs/AGENTS.md`, `docs/phase1-baseline.md`, `docs/legacy-port-ledger.md`, `docs/legacy-id-naming-rules.md`, `docs/deferred-legacy-features.md`, `docs/phase1.5-verification.md`, `docs/devlogs.md`.
- Key decisions: use `c3fedce Complete phase 1 playable base` as the Phase 1 baseline; make `legacy-port-ledger.md` a checklist and require every future legacy migration to update it in the same change; keep old metadata id decisions in a separate naming document; keep dropped/deferred old systems out of the ledger notes unless the decision is also recorded in `deferred-legacy-features.md`.
- Verification state: re-read root/docs DOX and `docs/clayium-migration-roadmap.md`; inspected old `CBlocks`, `CItems`, `CMaterials`, `CRecipes`, GUI, tile, network, and plugin domains for the initial ledger shape; `git diff --check` passed with LF/CRLF working-copy warnings only; user manually confirmed the Clay Work Table byproduct ghost/tooltip is present and the apparent missing byproduct was tooltip occlusion.
- Unresolved TODOs: run the full Phase 1.5 command checklist in `docs/phase1.5-verification.md`; perform the client Work Table/JEI visual checklist; expand ledger rows as each Phase 2+ content family is actually migrated.
- Next commands: `git diff --check`, then run the Phase 1.5 Gradle/client checklist with `--no-watch-fs` when ready.

## 2026-06-17 Phase 2 Material Catalog Slice

- Current goal: implement the Phase 2 early material catalog slice without opening the full material universe or adding temporary machine-era bridge recipes.
- Changed files: `build.gradle`, `README.md`, `docs/AGENTS.md`, `docs/clayium-migration-roadmap.md`, `docs/legacy-id-naming-rules.md`, `docs/legacy-port-ledger.md`, `docs/phase2-content-catalog.md`, `src/AGENTS.md`, `src/main/java/dev/clayium/clayium/registry/**`, `src/main/java/dev/clayium/clayium/data/**`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTests.java`, `src/generated/resources/**`, and Phase 2 PNG textures under `src/main/resources/assets/clayium/textures/**`.
- Key decisions: use `ClayiumContentCatalog` as the source of truth for cataloged blocks, standalone items, material forms, statuses, creative grouping, and generated resources; keep simple/basic hulls, early circuits, CEE items, and compressed clay shards registered-only until real machine production chains exist; switch the `data` run to `clientData()` so one `runData` pass generates both client assets and server data.
- Verification state: `.\gradlew --version --no-watch-fs --console=plain` passed with Java 25; `.\gradlew tasks --all --no-watch-fs --console=plain` passed and showed `runClient`, `runServer`, `runData`, `runGameTestServer`, and `test`; `.\gradlew runData --stacktrace --no-watch-fs --console=plain` passed and the second run wrote 0 files; `.\gradlew test --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew clean build --stacktrace --no-watch-fs --console=plain` passed; `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain` passed. A `runServer` pipe-stop smoke was attempted, hung as a long-running server process, and the related Java processes were killed; do not use that as an automatic closeout check in this workspace.
- Unresolved TODOs: run client/manual visual inspection for the new Phase 2 textures and creative tab entries; migrate the real simple/basic hull and circuit production chains after the shared machine framework exists; consider a safer explicit timeout/log-scrape wrapper before using `runServer` or `runClient` as automated checks again.
- Next commands: `git diff --check`, inspect generated resource diffs, then commit the Phase 2 catalog slice when accepted.

## 2026-06-17 Clay Tool Behavior Migration

- Current goal: turn the current Clayium clay tools from plain item skins into catalog-driven 26.x tools while keeping Clayium Addition hammers out of scope.
- Changed files: `src/main/java/dev/clayium/clayium/item/**`, `src/main/java/dev/clayium/clayium/registry/ClayiumContentCatalog.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumItems.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumTags.java`, `src/main/java/dev/clayium/clayium/data/**`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableOperations.java`, `src/main/java/dev/clayium/clayium/recipe/ClayWorkTableToolRequirement.java`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTests.java`, `src/AGENTS.md`, `docs/phase2-content-catalog.md`, `docs/legacy-port-ledger.md`, `docs/clayium-migration-roadmap.md`, `docs/devlogs.md`.
- Key decisions: port Clay Shovel as wood-tier baseline with 500 durability, 32 speed on clay blocks, and 12 speed on future clay ores; port Clay Pickaxe as stone-tier baseline with 500 durability and 32 speed on future clay ores; keep Clay Wrench as a single-stack tagged machine tool with tooltip until directional machine rotation exists; route Work Table tool matching through tags so future compatible tools do not require hard-coded item checks.
- Verification state: `.\gradlew compileJava --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew runData --no-watch-fs --stacktrace --console=plain` passed and wrote tool tag/lang resources; `.\gradlew test --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew clean build --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace --console=plain` passed and logs showed `clayium:gametest/default` ran 6 tests with all 109 required tests passing; `git diff --check` passed with LF/CRLF warnings only.
- Unresolved TODOs: Clay Ore drop bonus must be implemented when Clay Ore/worldgen is migrated; wrench machine rotation must be wired when directional machine blocks exist. Jade still logs its known shearable-block collection error during GameTest startup, unrelated to Clayium tests.
- Next commands: inspect generated tag/resource diffs, then continue with machine framework work or commit the Phase 2 batch when accepted.

## 2026-06-17 Crafting Tool Durability Hotfix

- Current goal: port the old `ItemCraftingTools` durability/container behavior for the baked Clay Work Table tool trio.
- Changed files: `src/main/java/dev/clayium/clayium/item/ClayCraftingToolItem.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumContentCatalog.java`, `src/main/java/dev/clayium/clayium/registry/ClayiumItems.java`, `src/main/java/dev/clayium/clayium/menu/ClayWorkTableMenu.java`, `src/main/java/dev/clayium/clayium/gametest/ClayiumGameTests.java`, `src/AGENTS.md`, `docs/phase2-content-catalog.md`, `docs/legacy-port-ledger.md`, `docs/devlogs.md`.
- Key decisions: keep old default progression rate values for the trio (`60`, `60`, `36`); replace a max-damage tool with `4`, `3`, or `2` clay balls respectively; consume one durability on every valid tool-assisted Work Table button press; capture the accepted recipe before damaging the tool so a final breaking click still produces outputs.
- Verification state: `.\gradlew compileJava --no-watch-fs --stacktrace --console=plain` passed after removing the unavailable `setNoRepair()` call; `.\gradlew test --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew runGameTestServer --no-watch-fs --stacktrace --console=plain` passed; `.\gradlew clean build --no-watch-fs --stacktrace --console=plain` passed; `git diff --check` passed with LF/CRLF working-copy warnings only.
- Unresolved TODOs: none for this hotfix; future compatible third-party Work Table tools can be tagged, but only `ClayCraftingToolItem` currently receives the legacy Clayium remainder behavior.
- Next commands: inspect the small source/docs diff, then commit the hotfix with the pending Phase 2 batch when accepted.

## 2026-06-17 Phase 2 Acceptance Closeout

- Current goal: close Phase 2 as the accepted material-catalog slice, update public docs, and commit/push the batch.
- Changed files: `README.md`, `docs/clayium-migration-roadmap.md`, `docs/phase2-content-catalog.md`, `docs/devlogs.md`, plus the pending Phase 2 catalog, generated resource, tool behavior, GameTest, and ledger changes recorded above.
- Key decisions: do not add temporary survival bridge recipes for simple/basic hulls or early circuits; their real production chain starts after the shared machine framework exists. Treat registered-only ids as complete for this Phase 2 slice when catalog, resources, creative tab, ledger, and no-bridge tests are present.
- Verification state: Phase 2 batch previously passed `.\gradlew --version --no-watch-fs --console=plain`, `.\gradlew tasks --all --no-watch-fs --console=plain`, `.\gradlew runData --stacktrace --no-watch-fs --console=plain`, `.\gradlew test --stacktrace --no-watch-fs --console=plain`, `.\gradlew clean build --stacktrace --no-watch-fs --console=plain`, and `.\gradlew runGameTestServer --stacktrace --no-watch-fs --console=plain`; a closeout audit found all six Phase 2 blocks, all twenty-two Phase 2 standalone items, and no missing generated block/item/model/loot resources; `git diff --check` passed with LF/CRLF warnings only.
- Unresolved TODOs: optional client/manual visual inspection for Phase 2 textures and creative tab grouping; implement real simple/basic hull and circuit production during the next machine phases.
- Next commands: commit and push the accepted Phase 2 batch, then begin Phase 3 machine framework planning/implementation.
