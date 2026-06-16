# Phase 1 Baseline

Last updated: 2026-06-17

## Baseline Commit

- Phase 1 accepted baseline: `c3fedce Complete phase 1 playable base`.
- Branch at freeze time: `main`.
- Remote baseline: `origin/main`.
- Scope: a playable NeoForge 26.x foundation from early clay shaping through Clay Work Table processing into the first Clay Machine Hull.

This baseline is the starting point for Phase 1.5 and later full-port work. Future migration work should compare against this commit when deciding whether a change belongs to Phase 1 cleanup or a later phase.

## Target Stack

- Minecraft: `26.1.2`
- NeoForge: `26.1.2.76`
- Java: `25`
- Gradle Wrapper: `9.2.1`
- ModDevGradle: `2.0.141`
- Mod id and namespace: `clayium`
- Java package root: `dev.clayium.clayium`
- Mod version: `0.1.0-alpha.1`

## Phase 1 Completed Surface

- NeoForge 26.x scaffold with ModDevGradle 2.x.
- DeferredRegister-based registry layers.
- Table-driven early item/block registration helpers:
  - `ClayMaterial`
  - `ClayPartType`
  - `PartRegistry`
  - `MachineHullTier`
- Blocks:
  - `dense_clay`
  - `clay_work_table`
  - `raw_clay_machine_hull`
  - `clay_machine_hull`
- Tools:
  - `raw_clay_rolling_pin`
  - `raw_clay_slicer`
  - `raw_clay_spatula`
  - `clay_rolling_pin`
  - `clay_slicer`
  - `clay_spatula`
- Early Clay and Dense Clay part items for Phase 1 shapes.
- Clay Work Table:
  - 5-slot block entity/menu model with hidden in-progress input.
  - Repeated button presses advance progress.
  - In-progress operation locking prevents switching action mid-craft.
  - Fixed output slots and output merge checks.
  - Inventory drops on block break.
  - GUI hover highlight and ghost output preview.
  - JEI category for Work Table recipes.
- Data-driven `clayium:clay_work_table` recipe type with JSON/datapack loading.
- Datagen for Phase 1 recipes, block loot, mineable tags, models, and lang.
- Unit tests and GameTests for the Phase 1 playable loop.

## Baseline Policy

- Do not move old CFR packages wholesale into `src/main/java`.
- Keep old `decompiled/` content as read-only reference.
- Keep `gradle.properties` as dependency and metadata source of truth.
- Any legacy content migrated after this baseline must update `docs/legacy-port-ledger.md` in the same change.
- Any new or changed registry id decision that touches old metadata content must follow `docs/legacy-id-naming-rules.md`.
- Any behavior intentionally skipped from the old mod must be listed in `docs/deferred-legacy-features.md`.

## Verification Record

Phase 1 had passing build/test/GameTest checks before the baseline commit, as recorded in `docs/devlogs.md` and `README.md`.

Phase 1.5 owns the next full verification pass. Record the actual command results in `docs/phase1.5-verification.md` instead of editing this baseline note.
