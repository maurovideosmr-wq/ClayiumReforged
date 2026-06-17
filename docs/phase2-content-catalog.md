# Phase 2 Content Catalog

Last updated: 2026-06-17

Phase 2 introduces `ClayiumContentCatalog` as the source of truth for early material content. New cataloged content should be traceable from one row to registration, creative tab grouping, datagen, tags, recipes, tests, and the legacy ledger. Legacy tier metadata belongs in the catalog or the owning material/tier enum so item and block tooltips can be generated from data instead of per-item strings.

## Scope

- Port only the early material foundation needed before the machine framework: compressed clay, industrial clay, advanced industrial clay, dense/simple/basic machine hull ids, early dust/plate/large plate forms, early circuit/progression items, clay shovel, clay pickaxe, clay wrench, and compressed clay shard items.
- Do not expand every material by every shape. Add only material-form rows that the current phase intentionally owns.
- Do not add temporary survival bridge recipes for machine-era content. Simple/basic hulls and early circuits can be registered-only until Phase 3/4 machines provide their real production chain.

## Acceptance

- Accepted on 2026-06-17 as the Phase 2 material-catalog slice.
- Registered-only rows are complete for this phase when they have stable ids, generated resources, creative-tab visibility, ledger status, and tests proving they do not receive temporary survival recipes.
- Real production for simple/basic hulls and early circuits belongs to the next machine phases, not to ad hoc bridge recipes.

## Catalog Rows

`BlockSpec` rows define:

- `id`: flattened registry id and resource id.
- `translation`: generated English display name.
- `texture`: texture basename under `assets/clayium/textures/block`.
- `tier`: legacy tier tooltip value for the block item; compressed clay blocks follow old metadata tier values, while machine hulls derive this from `MachineHullTier`.
- `kind`: block behavior family, such as compressed clay, Work Table, raw machine hull, or machine hull.
- `harvestTool`: generated mineable tag and block drop requirement.
- `status`: migration state for ledger and tests.

`MaterialFormSpec` rows define:

- `material`: `ClayMaterial` enum value.
- `partType`: `ClayPartType` enum value.
- `status`: `ITEM`, `BLOCK`, `EXTERNAL`, or `DEFERRED`.
- `id`: generated registry/resource id for items and blocks, or external id for vanilla references.
- `tier`: derived from `material.tier()` for generated tooltips.

`SimpleItemSpec` rows define:

- `id`: flattened registry id and resource id.
- `translation`: generated English display name.
- `creativeCategory`: `TOOLS`, `PROGRESSION`, `PARTS`, or `BLOCKS`.
- `modelKind`: generated item model family.
- `kind`: item behavior family for registry factories and tags, such as raw clay tool, Work Table tool, Clay Shovel, Clay Pickaxe, or Clay Wrench.
- `tier`: legacy tier tooltip value for the item; tool rows use the clay material tier and progression rows use old `itemMisc` tier values where available.
- `durability`: max damage for durable simple tools; `0` for non-durable simple items.
- `brokenClayBallCount`: clay balls returned when a baked Work Table crafting tool reaches its old break state; `0` for other simple items.
- `status`: migration state for ledger and tests.

## Status Rules

- `PORTED`: registered, generated, covered by recipes/tests where the current phase promises gameplay behavior.
- `REGISTERED_ONLY`: registered, generated, visible to creative/resources/ledger, but intentionally has no survival recipe or runtime behavior yet.
- `DEFERRED`: known legacy content that should not register or generate resources in the current phase.
- `ITEM`: material form has a Clayium item registry entry.
- `BLOCK`: material form is represented by a Clayium block item.
- `EXTERNAL`: material form is represented by vanilla or another namespace and should not register a Clayium item.
- `DEFERRED`: material form is recorded for later but does not register or generate resources yet.

## Adding Content

1. Add or update the `ClayMaterial`, `ClayPartType`, `MachineHullTier`, or future tier/type enum only when the legacy concept is actually entering scope.
2. Add the catalog row in `ClayiumContentCatalog`.
3. Add a common `ClayiumItems` or `ClayiumBlocks` alias only if code or recipes use it frequently.
4. Preserve or derive the legacy tier value in the catalog/material/tier enum before wiring registration.
5. For tool items, choose or add a `SimpleItemKind` and wire behavior through `ClayiumItems`, `ClayiumTags`, and tag datagen.
6. Extend datagen helpers instead of adding hand-written JSON.
7. Copy only required legacy PNGs into `src/main/resources/assets/clayium/textures`.
8. Add or update JUnit/GameTest coverage for the new registry surface, generated recipe expectations, and any promised behavior.
9. Update `docs/legacy-port-ledger.md` in the same change.

## Datagen Ownership

- `runData` owns generated blockstates, 26.x item definitions, block models, item models, language, loot tables, block tags, item tags, vanilla recipes, and Work Table recipe seed JSON.
- Hand-written JSON under `src/main/resources` should be limited to metadata, GUI/client assets that are not generated, or explicitly documented exceptions.
- Generated resources must be deterministic. If `runData` rewrites unrelated files, inspect and fix the provider rather than accepting noisy output.
