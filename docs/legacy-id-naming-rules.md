# Legacy Id Naming Rules

Last updated: 2026-06-17

Use this document when flattening old 1.7.10 metadata items or blocks into NeoForge 26.x registry ids. The goal is stable, explicit ids that match modern data and tag workflows without recreating old metadata models.

## Global Rules

- Keep namespace `clayium`.
- Use lower-case `snake_case`.
- Do not include old Java prefixes such as `item`, `block`, `tile`, or metadata index numbers in registry ids.
- Do not use one metadata carrier item/block as a new primary data model.
- Prefer display-name-derived ids when old metadata names were player-facing.
- Keep ids short enough for recipes and tags to remain readable, but do not abbreviate unclear names.
- If old metadata maps to a future behavior family, add the registry id only when that behavior is implemented or explicitly stubbed.
- Every new item must go through the registry helper/table appropriate to its family.
- Every new material part should be generated from `ClayMaterial x ClayPartType` unless it is intentionally outside the material matrix.

## Material Parts

Pattern: `<material>_<part_type>`

Examples:

- `clay_plate`
- `clay_gear`
- `dense_clay_plate`
- `dense_clay_water_wheel`

Material names should be the canonical modern material id, not the old icon name. Shape names should match `ClayPartType` ids. If a shape is renamed, update datagen, tests, and this document together.

## Machine Hulls

Pattern: `<tier_or_material>_machine_hull`

Known mappings:

- `blockRawClayMachineHull.0` -> `raw_clay_machine_hull`
- `blockMachineHull.0` -> `clay_machine_hull`
- `blockMachineHull.1` -> `dense_clay_machine_hull`
- `blockMachineHull.2` -> `simple_machine_hull`
- `blockMachineHull.3` -> `basic_machine_hull`
- `blockMachineHull.4` -> `advanced_machine_hull`
- `blockMachineHull.5` -> `precision_machine_hull`
- `blockMachineHull.6` -> `clay_steel_machine_hull`
- `blockMachineHull.7` -> `clayium_machine_hull`
- `blockMachineHull.8` -> `ultimate_machine_hull`
- `blockMachineHull.9` -> `antimatter_machine_hull`
- `blockMachineHull.10` -> `pure_antimatter_machine_hull`
- `blockMachineHull.11` -> `oec_machine_hull`
- `blockMachineHull.12` -> `opa_machine_hull`

## Compressed Clay Blocks

Use names from old display names instead of numeric metadata.

- `blockCompressedClay.0` -> `dense_clay` (already represented by the Phase 1 block)
- `blockCompressedClay.1` -> `compressed_clay`
- `blockCompressedClay.2` -> `industrial_clay`
- `blockCompressedClay.3` -> `advanced_industrial_clay`
- `blockCompressedClay.4` -> `energetic_clay`
- `blockCompressedClay.5` -> `compressed_energetic_clay`
- `blockCompressedClay.6` -> `double_compressed_energetic_clay`
- `blockCompressedClay.7` -> `triple_compressed_energetic_clay`
- `blockCompressedClay.8` -> `quadruple_compressed_energetic_clay`
- `blockCompressedClay.9` -> `quintuple_compressed_energetic_clay`
- `blockCompressedClay.10` -> `sextuple_compressed_energetic_clay`
- `blockCompressedClay.11` -> `septuple_compressed_energetic_clay`
- `blockCompressedClay.12` -> `octuple_compressed_energetic_clay`

## Misc And Circuit Items

Split old `itemMisc` metadata into explicit ids.

- `ClayCircuit` -> `clay_circuit`
- `ClayCircuitBoard` -> `clay_circuit_board`
- `SimpleCircuit` -> `simple_circuit`
- `CEEBoard` -> `cee_board`
- `CEECircuit` -> `cee_circuit`
- `CEE` -> `cee`
- `LaserParts` -> `laser_parts`
- `AntimatterSeed` -> `antimatter_seed`
- `SynchronousParts` -> `synchronous_parts`
- `TeleportationParts` -> `teleportation_parts`
- `Manipulator1` -> `manipulator`
- `Manipulator2` -> `manipulator_2`
- `Manipulator3` -> `manipulator_3`
- `BasicCircuit` -> `basic_circuit`
- `AdvancedCircuit` -> `advanced_circuit`
- `PrecisionCircuit` -> `precision_circuit`
- `IntegratedCircuit` -> `integrated_circuit`
- `ClayCore` -> `clay_core`
- `ClayBrain` -> `clay_brain`
- `ClaySpirit` -> `clay_spirit`
- `ClaySoul` -> `clay_soul`
- `ClayAnima` -> `clay_anima`
- `ClayPsyche` -> `clay_psyche`

Do not encode old tier numbers in ids unless the display name itself used the number.

## Tools, Filters, Capsules, And Gadgets

- Raw clay crafting tools are explicit ids: `raw_clay_rolling_pin`, `raw_clay_slicer`, `raw_clay_spatula`.
- Baked clay crafting tools are explicit ids: `clay_rolling_pin`, `clay_slicer`, `clay_spatula`.
- Clay piping tool metadata should split into behavior ids, for example `clay_io_tool`, `clay_piping_tool`, and `clay_memory_tool` unless old behavior review finds better player-facing names.
- Filters should use `filter_<kind>` ids:
  - `filter_duplicator`
  - `filter_whitelist`
  - `filter_blacklist`
  - `filter_fuzzy`
  - `filter_tag`
  - `filter_item_name`
  - `filter_translation_key`
  - `filter_unique_id`
  - `filter_mod_id`
  - `filter_item_damage`
  - `filter_block_metadata`
  - `filter_block_harvestable`
- Fluid capsules should include capacity in a stable suffix once the modern capacity unit is chosen, for example `fluid_capsule_1000mb`.
- Clay shooters should use explicit tier/player-facing names after behavior review, not array indexes.
- Gadgets should use `gadget_<behavior>` or `gadget_<behavior>_<tier>` ids after the old metadata list is reviewed.

## Machines

Pattern: `<machine_name>` for standalone machines and `<tier>_<machine_name>` or `<machine_name>_<tier>` only after the shared machine-tier convention is chosen in Phase 3.

Do not create ad hoc per-machine naming conventions before Phase 3. Machine registration should go through a `MachineType`/tier table once that framework exists.

## Tags Instead Of OreDictionary

- Do not copy old OreDictionary keys as registry ids.
- Use current NeoForge/Minecraft 26.x tag conventions for cross-mod matching.
- Define only the tags needed by the current phase.
- Recipe JSON and JEI displays should read the same recipe/tag data used by gameplay logic.
