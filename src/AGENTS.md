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
- Keep generated or copied legacy PNG usage limited to assets required by the current playable phase.

# Verification

- Run `.\gradlew clean build --stacktrace` after source, resource, metadata, dependency, or Gradle changes.
- Run `.\gradlew runData --stacktrace` after provider, recipe, model, lang, or generated-resource changes once data providers exist.
- Run `.\gradlew runGameTestServer --stacktrace` after GameTest or in-game behavior test changes.

# Child DOX Index
