# Deferred Legacy Features

Last updated: 2026-06-17

This document records old Clayium features that are intentionally not migrated in Phase 1.5, are deferred to later phases, or are dropped as 1.7.10-era implementation details.

## Dropped

- Old ASM transformer/sample code: do not migrate to NeoForge 26.x.
- Direct 1.7.10 save/world compatibility: not promised for Clayium Reforged.
- NEI itself: replace user-facing recipe display with JEI.
- Old FML GUI id and packet model as architecture: rewrite into 26.x menus, synced data, and custom payloads.
- Wholesale CFR source copying: old decompiled Java remains reference-only.

## Deferred Until Modern Feasibility Review

- MineTweaker integration: evaluate CraftTweaker/KubeJS or another modern scripting target in Phase 9.
- ForgeMultipart integration: do not port old multipart classes directly; PAN/cable behavior should be designed for modern APIs in Phase 8/9.
- IC2 integration: old IC2 crop/filter helpers and energy assumptions need a modern target-mod decision in Phase 9.
- GregTech integration: old `UtilGT` compatibility and material assumptions need a modern target-mod decision in Phase 9.
- RF/CoFH integration: old RF generator support must not become a core dependency; evaluate FE/RF compatibility in Phase 9 or release hardening.
- Other 1.7.10-era mod APIs: AE2, Thermal, EnderIO, TConstruct, ProjectRed, Galacticraft, Thaumcraft, Botania, Compact Machines, and similar integrations must be re-resolved against the 26.x ecosystem before code is added.

## Deferred Gameplay Systems

- Chunk loading tickets and `blockClayChunkLoader`: defer until server rules, config, and permission behavior are explicitly designed.
- World generation: clay ore and clay tree belong to Phase 5 with 26.x feature/datapack/biome modifier work.
- PAN and laser systems: defer until Phase 8 after machine, energy, and logistics foundations exist.
- Area machines and markers: defer until Phase 8 with dedicated boundary, permission, and performance tests.
- Full fluid capsule and fluid transfer behavior: defer until Phase 7.
- Player gadgets, clay shooters, and instant teleporter: defer until Phase 8.

## Replacement Direction

- NEI recipe views -> JEI categories.
- Waila-like status display -> Jade providers.
- Old container GUI duplication -> shared menu/screen helpers and optional LDLib adapters where useful.
- OreDictionary -> modern tags.
- Old tile entity synchronization -> menu data, attachments/capabilities, and 26.x payloads.
- Old optional-mod hard references -> optional compile/runtime integrations with guarded loading.
