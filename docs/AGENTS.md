# Purpose

- Own durable project notes and migration records for Clayium Reforged.

# Ownership

- `devlogs.md` records manual handoffs for long migration phases.
- `clayium-migration-roadmap.md` records the phased full-port roadmap from the Phase 1 NeoForge 26.x baseline to a complete Clayium migration.
- `phase1-baseline.md` records the frozen Phase 1 baseline commit and scope.
- `legacy-port-ledger.md` records the migration checklist for old Clayium source domains and player-facing content.
- `legacy-id-naming-rules.md` records metadata-to-registry-id naming rules for flattened 26.x content.
- `deferred-legacy-features.md` records old systems that are dropped, deferred, or replaced by modern equivalents.
- `phase1.5-verification.md` records Phase 1.5 verification commands and manual client checks.
- Keep implementation contracts in the nearest source/resource AGENTS.md if those folders gain local rules.

# Local Contracts

- Devlog entries must include current goal, changed files, key decisions, verification state, unresolved TODOs, and next commands.
- Every migration of legacy Clayium content must update `legacy-port-ledger.md` in the same change. If the ledger lacks a row for the content, add one before marking the migration complete.
- Registry ids for flattened old metadata content must follow `legacy-id-naming-rules.md` unless a closer source AGENTS.md records a more specific rule.
- Do not mark Phase 1.5 verification checklist items complete unless the command or manual check was actually run in this workspace.
- If an old feature is intentionally not migrated, update `deferred-legacy-features.md` instead of leaving the decision implicit.

# Work Guidance

- Keep Phase 1.5 planning records split by purpose: baseline, ledger, naming, deferred features, and verification.
- Prefer checklist edits in `legacy-port-ledger.md` over prose-only status notes, so future agents can see what remains.

# Verification

- For docs-only updates, run `git diff --check` before closeout.

# Child DOX Index
