# DOX framework

- DOX is the AGENTS.md hierarchy installed here.
- Agents must follow DOX instructions across any edits.

## Core Contract

- AGENTS.md files are binding work contracts for their subtrees.
- Work products, source materials, instructions, records, assets, and durable docs must stay understandable from the nearest applicable AGENTS.md plus every parent AGENTS.md above it.

## Read Before Editing

1. Read the root AGENTS.md.
2. Identify every file or folder you expect to touch.
3. Walk from the repository root to each target path.
4. Read every AGENTS.md found along each route.
5. If a parent AGENTS.md lists a child AGENTS.md whose scope contains the path, read that child and continue from there.
6. Use the nearest AGENTS.md as the local contract and parent docs for repo-wide rules.
7. If docs conflict, the closer doc controls local work details, but no child doc may weaken DOX.

Do not rely on memory. Re-read the applicable DOX chain in the current session before editing.

## Update After Editing

Every meaningful change requires a DOX pass before the task is done.

Update the closest owning AGENTS.md when a change affects:

- purpose, scope, ownership, or responsibilities
- durable structure, contracts, workflows, or operating rules
- required inputs, outputs, permissions, constraints, side effects, or artifacts
- user preferences about behavior, communication, process, organization, or quality
- AGENTS.md creation, deletion, move, rename, or index contents

Update parent docs when parent-level structure, ownership, workflow, or child index changes. Update child docs when parent changes alter local rules. Remove stale or contradictory text immediately. Small edits that do not change behavior or contracts may leave docs unchanged, but the DOX pass still must happen.

## Hierarchy

- Root AGENTS.md is the DOX rail: project-wide instructions, global preferences, durable workflow rules, and the top-level Child DOX Index.
- Child AGENTS.md files own domain-specific instructions and their own Child DOX Index.
- Each parent explains what its direct children cover and what stays owned by the parent.
- The closer a doc is to the work, the more specific and practical it must be.

## Child Doc Shape

- Create a child AGENTS.md when a folder becomes a durable boundary with its own purpose, rules, responsibilities, workflow, materials, or quality standards.
- Work Guidance must reflect the current standards of the project or user instructions; if there are no specific standards or instructions yet, leave it empty.
- Verification must reflect an existing check; if no verification framework exists yet, leave it empty and update it when one exists.

Default section order:

- Purpose
- Ownership
- Local Contracts
- Work Guidance
- Verification
- Child DOX Index

## Style

- Keep docs concise, current, and operational.
- Document stable contracts, not diary entries.
- Put broad rules in parent docs and concrete details in child docs.
- Prefer direct bullets with explicit names.
- Do not duplicate rules across many files unless each scope needs a local version.
- Delete stale notes instead of explaining history.
- Trim obvious statements, repeated rules, misplaced detail, and warnings for risks that no longer exist.

## NeoForge 26.x Contract

- Target NeoForge 26.x / Minecraft 26.x only. Treat older 1.x-era Minecraft and NeoForge instructions as historical reference unless the user explicitly asks for backport work.
- Minecraft 26.1+ uses the CalVer-style `year.release.patch` Minecraft version line; NeoForge versions use the first three components for the Minecraft version and the final component for the NeoForge release, for example `26.1.0.10-beta`.
- Use the selected 26.x documentation set from the official NeoForge docs before changing APIs, Gradle configuration, metadata, networking, data generation, rendering, or registry code.
- Prefer ModDevGradle 2.x for the root NeoForge mod build unless the project explicitly chooses NeoGradle. Check the official NeoForged Project Listing before pinning plugin or NeoForge versions.
- Use Java 25 and Gradle wrapper 9.1.0 or newer for 26.x work. Update `java.toolchain.languageVersion`, IDE Gradle JVM, and CI JDK together.
- Remove Parchment as a required mapping dependency unless it is intentionally kept for extra javadocs. Do not depend on Parchment to recover parameter names in 26.x.
- Keep `gradle.properties` as the source of truth for shared mod metadata and dependency version properties. Keep `src/main/resources/META-INF/neoforge.mods.toml` aligned with those properties.
- Use `modLoader="javafml"` and the 26.x-compatible JavaFML loader range in `neoforge.mods.toml` unless a child AGENTS.md explicitly owns an alternative language loader.
- Do not carry old dependency coordinates forward blindly. Re-resolve 26.x-compatible versions for Compact Machines, LDLib2, Yoga, JEI, Jade, Pipez, Mekanism, FTB libraries, Architectury, and other optional runtime/test mods before editing build files.
- Do not assume 1.21.x binary artifacts are valid in 26.x. If an old artifact is kept, mark it as reference-only or vendor-only unless the build proves it loads on the target 26.x NeoForge version.

## NeoForge 26.x Migration Guardrails

- Build from the official 26.x MDK or a clean ModDevGradle 2.x scaffold when possible. If migrating, keep a compiling previous-version workspace nearby for comparison, but implement against 26.x source and docs.
- For registry- or data-file representations, do not instantiate runtime-only stacks before registries are loaded. Use the 26.x template/resource APIs such as item/fluid stack templates where required.
- Review GUI and rendering code carefully. 26.x continues the rendering extraction refactor; update old `render`/`GuiGraphics` patterns to the current extraction classes and methods instead of shim-writing around compile errors.
- Update changed vanilla construction helpers instead of reintroducing removed constructors. Known examples include `ChunkPos.containing`, `ChunkPos.pack`, and `ChunkPos.unpack` patterns.
- Validate event-bus side, logical side, registry access, networking payload registration, data components, tags, recipes, attachments, capabilities, and resource paths against the selected 26.x docs before patching.
- Prefer small compile-driven migration patches. Do not rename packages, registries, IDs, resource namespaces, or saved-data keys unless the user asks or compatibility requires it.
- For client-only code, isolate client entrypoints and classes so dedicated server runs do not load client classes.
- For data generation, keep generated assets deterministic and review diffs before accepting mass output.

## NeoForge 26.x Test Authoring

- Do not treat `runClient`, `runServer`, or a successful launch as behavioral proof. Runtime smoke checks are useful, but build, unit tests, and GameTests are the preferred verification path.
- For behavior changes to blocks, items, block entities, menus, recipes, ingredients, attachments, capabilities, networking, automation, world interaction, redstone, ticking, or saved data, add or update NeoForge/Minecraft GameTests when the behavior can be exercised in a headless server.
- Prefer a `gameTestServer` run configuration in the Gradle build. With ModDevGradle, the run type must be `gameTestServer`; the expected Gradle task is normally `runGameTestServer`. If the task is missing and the project owns the build script, add the run configuration instead of silently skipping GameTests.
- If GameTests need to be available from other run configs, set the run property `neoforge.enableGameTest=true`; by default, NeoForge enables GameTests for `client` and `gameTestServer` runs.
- GameTests may be registered through data files/datagen under `data/<namespace>/test_instance` and related test environment/structure resources. The selected 26.1.x API also supports programmatic tests through `RegisterGameTestsEvent`; verify the current artifact before adding or changing custom `GameTestInstance` codecs.
- For deterministic logic that does not need a live world, add JUnit tests under `src/test`. Configure ModDevGradle unit testing with `neoForge.unitTest.enable()`, `testedMod = mods.<mod name>`, JUnit Platform, and the project mod loaded into the test runtime.
- For tests that need a Minecraft server but not full GameTest structures, use the NeoForge test framework dependency `net.neoforged:testframework:<neo_version>` and JUnit server fixtures such as `EphemeralTestServerProvider` where compatible with the selected 26.x NeoForge version.
- Keep test resources deterministic. Generated test data must be reviewed like normal datagen output; do not accept mass rewrites without checking names, namespaces, structures, and required/optional test flags.
- Prefer small regression tests close to the changed behavior over broad manual smoke plans. If a behavior cannot be tested automatically, document the concrete manual reproduction steps in the final report rather than inventing test coverage.

## Optional Integration Dependencies

- JEI, Jade, and LDLib/LDLib2 are available for IDE compile/reference and local runtime testing only. Keep them on optional dev/test configurations such as `compileOnly` and `localRuntime` until explicit integration work begins.
- Do not add Clayium JEI categories, Jade providers, LDLib UI/adapters, or hard runtime dependencies during Phase 1 unless the user explicitly expands the scope.

## Verification

In this workspace, run Gradle with `--no-watch-fs`; Gradle VFS watching has hung during early Windows file-system detection here. When confirming Java 25, set `GRADLE_OPTS=-Dorg.gradle.java.home=C:\Progra~1\Java\jdk-25` because the user-level Gradle properties currently point at JDK 21.

Run relevant checks before closeout, in this order when practical:

1. `./gradlew --version` to confirm Java 25 and Gradle 9.1+ are active for 26.x work.
2. `./gradlew tasks --all` after Gradle, NeoForge, MDG/NG, source-set, run-config, or test wiring changes; confirm `test`, `runClient`, `runServer`, `runData`, and especially `runGameTestServer` or the project-specific GameTest task name.
3. `./gradlew clean build --stacktrace` after source, resource, metadata, dependency, or Gradle changes. This is the minimum non-negotiable compile/package check.
4. `./gradlew test --stacktrace` after any unit-test wiring or deterministic logic change. Do not assume `build` covered the intended NeoForge unit-test setup unless the Gradle output shows the expected tests ran.
5. `./gradlew runGameTestServer --stacktrace` after any in-game behavior change that can be represented as a GameTest, and after any GameTest/datagen/test-resource change. Treat required GameTest failures as task failures.
6. `./gradlew runData --stacktrace` after provider, tag, model, loot, recipe, lang, test instance, test environment, structure reference, or generated-resource changes. Review generated diffs before accepting them.
7. `./gradlew runServer --stacktrace` after common/server logic changes, networking changes, registry/data-pack changes, or client/server boundary changes. Use it to catch dedicated-server client-class loading issues.
8. `./gradlew runClient --stacktrace` after client UI, rendering, resource, input, model, screen, or manual `/test` workflow changes when the environment can launch Minecraft.
9. `git diff --check` before final reporting.

If a check cannot be run, report the exact command, the exact reason, and the highest-confidence partial verification that was completed. Do not replace skipped GameTests with “client launched” unless the changed behavior is genuinely client-only and no headless test is possible.

## Closeout

1. Re-check changed paths against the DOX chain.
2. Update nearest owning docs and any affected parents or children.
3. Refresh every affected Child DOX Index.
4. Remove stale or contradictory text.
5. Run existing verification when relevant.
6. Report any docs intentionally left unchanged and why.

## User Preferences

When the user requests a durable behavior change, record it here or in the relevant child AGENTS.md.

- Treat Codex CLI/Desktop context compact as unreliable for long work until the user explicitly says this changed. Do not depend on `/compact`, auto compact, or remote compact to preserve project continuity.
- Work in short phases for long tasks. Before a phase risks large context or after meaningful changes, record a manual handoff in `docs/devlogs.md` instead of waiting for compaction.
- Handoff/devlog entries should include current goal, changed files, key decisions, verification state, unresolved TODOs, and next commands.
- When migrating any legacy Clayium content, update `docs/legacy-port-ledger.md` in the same change. If the ledger lacks a row, add one before marking the migration complete.

## Child DOX Index

- `docs/AGENTS.md` — durable migration notes, handoffs, and devlogs.
- `src/AGENTS.md` — NeoForge 26.x mod sources, resources, generated assets, and tests.
