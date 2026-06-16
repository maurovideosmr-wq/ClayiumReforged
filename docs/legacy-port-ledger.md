# Legacy Port Ledger

Last updated: 2026-06-17

This is the migration checklist for old Clayium `0.4.6.36.hotfix2` content. Every agent migrating legacy Clayium content must update this checklist in the same change. If no row exists for the content being migrated, add the row before marking the work complete.

## Status Key

- `[x]` Migrated or intentionally resolved in the NeoForge 26.x codebase.
- `[ ]` Not migrated yet, or only recorded as future work.
- Status text:
  - `Done`: implemented and verified for the current scope.
  - `Partial`: some representative content exists, but the old family is not complete.
  - `Planned`: expected in a later phase.
  - `Deferred`: intentionally delayed or replaced by a modern equivalent.
  - `Dropped`: intentionally not migrated.

## Ledger Maintenance Checklist

- [ ] Before migrating old content, locate the relevant old source rows in `decompiled/clayium-0.4.6.36.hotfix2-cfr`.
- [ ] Add missing ledger rows for new registry ids, recipes, menus, block entities, packets, resources, or integrations before coding the migration.
- [ ] Mark rows `Done` only after code, resources/datagen, tests, and docs are updated for the intended scope.
- [ ] Keep metadata flattening decisions aligned with `docs/legacy-id-naming-rules.md`.
- [ ] If a legacy feature is deferred or dropped, link it to `docs/deferred-legacy-features.md`.
- [ ] Record verification commands or manual checks in `docs/phase1.5-verification.md` or the phase-specific devlog.

## Initial Source Survey Checklist

These boxes mean the old source domain has been surveyed for the initial Phase 1.5 ledger shape, not that the whole domain has been migrated.

- [x] `block/CBlocks.java`: first Phase 1 blocks and tool tags covered; full machine/block catalog pending.
- [x] `item/CItems.java`: raw/baked clay crafting tools covered; most old misc/tools/filters/gadgets pending.
- [x] `item/CMaterials.java`: Clay and Dense Clay Phase 1 parts covered through table-driven registration; full material catalog pending.
- [x] `util/crafting/CRecipes.java`: Clay Work Table and first survival loop covered; machine recipe maps pending.
- [x] `gui/*`: Clay Work Table menu/screen and JEI display covered; other GUI families pending.
- [x] `block/tile/*`: Clay Work Table block entity covered; machine/container block entities pending.
- [x] `network/*`: Work Table button processing handled through menu clicks; old custom packet families pending modern payload rewrite.
- [x] `pan/*` and `laser/*`: not migrated.
- [x] `render/*`: not migrated except current vanilla model/client screen work.
- [x] `worldgen/*`: not migrated.
- [x] `plugin/nei/*`: Clay Work Table display replaced by JEI; full NEI coverage pending JEI equivalents.
- [x] `plugin/minetweaker/*`: not migrated.
- [x] `plugin/multipart/*`: not migrated.
- [x] `plugin/LoadIC2Plugin.java`, `plugin/UtilGT.java`, and old IC2/GT helpers: not migrated.
- [x] `asm/*` and `sample/*`: dropped for NeoForge 26.x.

## Blocks From `CBlocks`

- [x] `blockClayWorkTable` -> `clay_work_table` (`Done`, Phase 1): menu, screen, inventory drop, Work Table recipes, JEI, tests.
- [ ] `blockClayCraftingTable` (`Planned`, Phase 4): old advanced crafting table behavior not yet ported.
- [x] `blockDenseClay` -> `dense_clay` (`Done`, Phase 1): registered, craftable, loot/tagged.
- [x] `blockCompressedClay` meta 0 -> `dense_clay` (`Done`, Phase 1): old dense clay metadata entry represented by explicit block id.
- [ ] `blockCompressedClay` metas 1-12 (`Planned`, Phase 2/5): compressed/industrial/energetic clay ladder pending.
- [x] `blockRawClayMachineHull` -> `raw_clay_machine_hull` (`Done`, Phase 1): registered, smelts into first hull, shovel tag retained.
- [x] `blockMachineHull` meta 0 -> `clay_machine_hull` (`Done`, Phase 1): registered, self-drop, wooden pickaxe harvest accepted by tag.
- [ ] `blockMachineHull` metas 1-12 (`Planned`, Phase 2/5): dense through OPA machine hulls pending.
- [ ] `blockOthersHull`, `blockMaterial`, `blockSiliconeColored` (`Planned`, Phase 5): metadata block families pending explicit ids.
- [ ] Simple machine arrays: bending, wire drawing, pipe drawing, cutting, lathe, milling, condenser, grinder, decomposer, smelter (`Planned`, Phase 3/4): require shared machine runtime first.
- [ ] Multi-input/high-tier machines: assembler, inscriber, centrifuge, chemical reactor, alloy smelter, blast furnace, reactor, CA machines (`Planned`, Phase 3/6): require recipe/runtime framework.
- [ ] Energy generation/storage/upgrades: water wheels, fabricators, resonator, overclocker, energy storage upgrade, Clay Energy laser (`Planned`, Phase 4/6): energy model pending.
- [ ] Storage/logistics/interface blocks: buffer, multitrack buffer, distributor, interfaces, auto crafter, storage/vacuum container, auto trader (`Planned`, Phase 7): automation framework pending.
- [ ] World blocks: clay ore, clay tree sapling/leaf/log (`Planned`, Phase 5): worldgen and feature datagen pending.
- [ ] Area and marker blocks: marker variants, area collector/miner/replacer/activator (`Planned`, Phase 8): area-machine framework pending.
- [ ] Fluid blocks: fluid translator and fluid transfer machine (`Planned`, Phase 7): fluid/capsule model pending.
- [ ] PAN blocks: PAN core, adapter, duplicator, cable (`Planned`, Phase 8): PAN system pending.
- [ ] `blocksRFGenerator` (`Deferred`, Phase 9): old RF/CoFH integration not a core dependency.
- [ ] `blockClayChunkLoader` (`Deferred`, Phase 8/10): must be re-evaluated against modern server rules before migration.

## Items From `CItems`

- [ ] `itemMisc` metadata family (`Planned`, Phase 2/5): split into explicit circuit, board, laser, antimatter, synchronous, teleportation, manipulator, core, brain, spirit, soul, anima, and psyche ids.
- [x] `itemLargeClayBall` -> `clay_large_ball` (`Done`, Phase 1): represented by part registry.
- [x] `itemRawClayCraftingTools` metas 0-2 -> raw rolling pin/slicer/spatula (`Done`, Phase 1): split explicit ids and smelting.
- [x] `itemClayRollingPin`, `itemClaySlicer`, `itemClaySpatula` (`Done`, Phase 1): split explicit tool ids.
- [ ] `itemClayWrench` (`Planned`, Phase 2/3): machine interaction behavior pending.
- [ ] `itemClayPipingTools` metadata family (`Planned`, Phase 7): IO, piping, and memory tools pending.
- [ ] `itemCompressedClayShard` metadata family (`Planned`, Phase 2): shard ladder pending compressed clay migration.
- [ ] Clay and Clay Steel shovels/pickaxes (`Planned`, Phase 2/5): tool materials and recipes pending.
- [ ] `itemSynchronizer`, `itemDirectionMemory` (`Planned`, Phase 7): machine direction/sync behavior pending.
- [ ] Filter family (`Planned`, Phase 7): duplicator, whitelist, blacklist, fuzzy, ore/tag, name/id, damage/metadata, and harvestable filters pending.
- [ ] `itemsClayShooter`, `itemInstantTeleporter` (`Planned`, Phase 8): entity/player tools pending.
- [ ] `itemsCapsule` (`Planned`, Phase 7): fluid capsule model pending.
- [ ] `itemGadgetHolder`, `itemGadget` (`Planned`, Phase 8): gadget container and behaviors pending.

## Materials And Shapes From `CMaterials`

- [x] `CLAY` material (`Partial`, Phase 1): Phase 1 shapes migrated.
- [x] `DENSE_CLAY` material (`Partial`, Phase 1): Phase 1 shapes migrated.
- [ ] Core clay progression materials: `IND_CLAY`, `ADVIND_CLAY`, `ENG_CLAY`, `EXC_CLAY`, `ORG_CLAY` (`Planned`, Phase 2/5).
- [ ] Clayium-owned high-tier materials: `CLAY_STEEL`, `CLAYIUM`, `ULTIMATE_ALLOY`, `ANTIMATTER`, `PURE_ANTIMATTER`, compressed pure antimatter, `OCTUPLE_CLAY`, `OEC`, `OPA` equivalents (`Planned`, Phase 5/6).
- [ ] Pure/impure element chain (`Planned`, Phase 5): aluminium, silicon, magnesium, sodium, lithium, zirconium, zinc, manganese, calcium, potassium, nickel, and related impure forms.
- [ ] External-mod material aliases (`Deferred`, Phase 9): old EnderIO, Thermal, AE2, TConstruct, GregTech, IC2, Botania, ProjectRed, Galacticraft, Thaumcraft, and other compatibility material names need modern feasibility checks.
- [x] Phase 1 shapes: plate, stick, short_stick, ring, small_ring, gear, blade, needle, disc, small_disc, cylinder, pipe, large_ball, large_plate, grinding_head, bearing, spindle, cutting_head, water_wheel (`Done`, Phase 1 for Clay/Dense Clay only).
- [ ] Later shapes: block, ball, dust, ingot, gem, crystal and extended material-shape matrix (`Planned`, Phase 2/5).
- [x] Removed unused Dense Clay large ball artifact (`Done`, Phase 1): old leftover not part of active recipe chain.

## Recipes From `CRecipes`

- [x] Clay Work Table kneading recipes (`Done`, Phase 1): 16 legacy recipes seeded into custom JSON/datapack recipe type.
- [x] Early crafting/smelting loop (`Done`, Phase 1): dense clay, Work Table, raw/baked tools, raw hull, clay machine hull.
- [ ] `registerToolRecipes` remainder (`Planned`, Phase 2): wrench, clay tools, piping tools, filters, shooters, capsules, gadgets pending.
- [ ] `registerMainMaterialRecipes` remainder (`Planned`, Phase 2/5): compressed clay and material progression pending.
- [ ] `registerHullRecipes` remainder (`Planned`, Phase 2/5): tiered hull chain pending.
- [ ] `registerMachineRecipes` (`Planned`, Phase 3/4/6/7/8): machine blocks need shared machine registry/runtime first.
- [ ] `registerMaterialRecipes` (`Planned`, Phase 5/6): bulk material processing pending.
- [ ] Simple machine maps: bending, wire drawing, pipe drawing, cutting, lathe, milling, condenser, grinder, decomposer, energetic condenser, electrolysis reactor, transformer, CA condenser, CA reactor, smelter, energetic decomposer (`Planned`, Phase 3/4/6).
- [ ] Multi-input recipe maps: assembler, inscriber, centrifuge, chemical reactor, alloy smelter, blast furnace, reactor, CA injector, fluid transfer machine (`Planned`, Phase 3/6/7).
- [ ] Old optional integration recipes (`Deferred`, Phase 9): only migrate after modern target mods and APIs are chosen.

## GUI, Block Entity, Network, And Integration

- [x] `TileClayWorkTable` equivalent -> `ClayWorkTableBlockEntity` (`Done`, Phase 1): inventory, hidden in-progress slot, drops.
- [x] Clay Work Table container/menu (`Done`, Phase 1): server-authoritative processing and output locking.
- [x] Clay Work Table client GUI (`Done`, Phase 1): Figma/atlas-aligned screen and hover ghost preview.
- [x] `NEIClayWorkTable` behavior -> JEI Work Table category (`Done`, Phase 1): input/output/action/click count display.
- [ ] Other old container classes, 27 total in `gui/container` (`Planned`, Phase 3+): migrate per machine family.
- [ ] Other old client GUI classes, 20 total in `gui/client` (`Planned`, Phase 3+): keep client-only.
- [ ] Other old block entities, 63 total in `block/tile` (`Planned`, Phase 3+): migrate through shared machine/container bases.
- [ ] Old network packet classes, 13 total in `network` (`Planned`, Phase 3/7/8): rewrite as 26.x payloads or menu data.
- [ ] NEI machine categories (`Planned`, Phase 4/6/7/8): replace with JEI categories as machines are migrated.
- [ ] Jade providers (`Planned`, Phase 3+): not present in old mod; add modern status displays where useful.
- [ ] LDLib production UI adapters (`Planned`, Phase 3+): optional, only where they reduce duplicated machine UI code.
- [ ] MineTweaker, IC2, GregTech, Multipart, RF/CoFH, and other old integrations (`Deferred`, Phase 9): no hard core dependency.
