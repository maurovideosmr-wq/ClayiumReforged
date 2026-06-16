# Purpose

- Own NeoForge 26.x source code, resources, generated assets, and tests for Clayium Reforged.

# Ownership

- `main/java/dev/clayium/clayium` owns new 26.x Java code under package `dev.clayium.clayium`.
- `main/resources/assets/clayium` owns client assets for the preserved `clayium` namespace.
- `main/resources/data/clayium` owns server data files such as recipes and test data.
- `generated/resources` is reserved for deterministic output from `runData`.

# Local Contracts

- Treat `decompiled/` as read-only reference material. Do not copy old CFR Java packages into `main/java`.
- Keep registry ids flat and explicit; do not reintroduce 1.7.10 metadata item or block ids.
- Keep client-only classes under `dev.clayium.clayium.client` and register them through client-only entrypoints.
- Keep source and data compatible with NeoForge 26.x / Minecraft 26.x only.

# Work Guidance

- Prefer small compile-driven migration patches.
- Register first-phase objects through `DeferredRegister` registries in `dev.clayium.clayium.registry`.
- Register new items through `ClayiumItems` helpers. Material parts must go through `ClayMaterial`, `ClayPartType`, and `PartRegistry`; keep common static aliases for frequently used parts.
- Keep creative tab output grouped by block items, tools, and material parts instead of relying on one flat append-only list.
- When a repeated registry, lang, model, or recipe pattern appears more than twice, add or extend a helper before copying another block of declarations.
- Any newly added source asset or data resource must be covered by deterministic datagen or a test before the phase closes.
- Keep generated or copied legacy PNG usage limited to assets required by the current playable phase.
- Keep `textures/gui/container/clay_work_table.png` as the 96x80 LDLib atlas: y=0 arrow progress, y=16 arrow base, and y=32/48/64 disabled/available/hover 16x16 action buttons ordered hand knead, hand press, rolling pin, cut plate, cut round, cut strips.
- Keep Clay Work Table storage at 5 slots: visible input/tool/main output/byproduct output slots `0..3`, plus hidden in-progress input slot `4`.
- Drop Clay Work Table container contents when the block is destroyed or replaced by another block.
- Keep Phase 1 block self-drops in datagen loot tables. Keep dense clay and raw clay machine hull mineable with shovel; keep clay machine hull mineable with pickaxe. Do not add harvest tier gates unless legacy behavior requires them; wooden-tier tools must remain valid for Clayium machine blocks that only declare a tool class.
- Keep Phase 1 GameTests under `dev.clayium.clayium.gametest` and registered through `RegisterGameTestsEvent`. The suite must cover the playable Phase 1 closure: registry surface, datapack Work Table recipes, menu processing, crafting/smelting recipes, block drops, and harvest-tool behavior.
- Keep Clay Work Table visible slots aligned to the Figma/legacy layout: input at 17,30, tool at 80,17, main output at 143,30, and byproduct output at 143,55.
- Keep Clay Work Table processing server-authoritative: action buttons advance `cookTime` one press at a time, progress is `cookTime / timeToCook`, output slots are fixed-position locked, and button yellow state is hover-only.
- While Clay Work Table has a hidden in-progress input, lock processing to the original button id. Other buttons must stay unavailable and must not clear progress, consume visible input, or replace the reserved hidden input.
- Keep Clay Work Table runtime processing data-driven through the `clayium:clay_work_table` recipe type. `ClayWorkTableOperations` is only the default legacy recipe seed for datagen and tests; do not use it as a second runtime recipe list.
- Store Clay Work Table recipe outputs as `ItemStackTemplate` and only create runtime `ItemStack`s in menu/JEI execution paths after registries and item components are bound.
- Draw Clay Work Table slot frames as inset/recessed controls: dark top/left edge, light bottom/right edge.
- Keep JEI Clay Work Table integration client-only under `dev.clayium.clayium.client.jei`; generate JEI recipe displays from `ClayWorkTableRecipeCache` so JEI, menu logic, and datapack recipes share the same recipe data.
- Keep the JEI Clay Work Table category aligned to the Figma `ClayWrokTableJEI` frame: 176x62 background, input item at 17,16, tool item at 80,3, main output at 143,16, byproduct output at 143,41, and action buttons at y=42.

# Verification

- Run `.\gradlew clean build --stacktrace` after source, resource, metadata, dependency, or Gradle changes.
- Run `.\gradlew runData --stacktrace` after provider, recipe, model, lang, or generated-resource changes once data providers exist.
- Run `.\gradlew runGameTestServer --stacktrace` after GameTest or in-game behavior test changes.

# Child DOX Index
