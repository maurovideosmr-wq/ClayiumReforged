# Clayium 全量迁移 Roadmap

状态检查时间：2026-06-17

## 当前基线

- 目标平台：Minecraft `26.1.2`、NeoForge `26.1.2.76`、Java `25`、Gradle Wrapper `9.2.1`、ModDevGradle `2.0.141`。
- 旧版参考：`clayium-0.4.6.36.hotfix2.jar`，Minecraft `1.7.10`，CFR 输出在 `decompiled/clayium-0.4.6.36.hotfix2-cfr`，资源在 `decompiled/clayium-0.4.6.36.hotfix2-resources`。
- 旧版规模：CFR 输出约 356 个 Java 文件；主要复杂度集中在 `block`、`gui`、`item`、`util/crafting`、`network`、`plugin`、`pan`、`render`、`worldgen`。
- 新版 Phase 1 基线：`src/main/java/dev/clayium/clayium` 已建立 NeoForge 26.x scaffold、DeferredRegister 注册层、Clay Work Table、材料部件注册骨架、custom recipe/datagen/GameTest/JEI 骨架。
- Phase 1 当前能力：Dense Clay、Clay Work Table、Raw/Clay Machine Hull、raw/baked clay tools、Clay/Dense Clay parts、Work Table 数据驱动 recipe、JEI Work Table category、loot/tags、基础 crafting/smelting、JUnit 和 GameTest 回归。
- Phase 2 已验收切片：`ClayiumContentCatalog` 已驱动早期 compressed/industrial/advanced clay、dense/simple/basic hull registered-only、早期 dust/plate/large_plate、circuit/progression item、clay shovel/pickaxe/wrench、datagen、ledger 和测试；simple/basic hull 与 circuit 的真实生产链明确交给后续机器阶段，不做临时桥接配方。

本文假设 Phase 1 已作为可继续开发的基线被接受。旧代码只作为行为和内容参考，不直接复制旧包名或 CFR 输出。

## 迁移原则

- 先迁移玩法骨架，再扩展内容数量。每个阶段都应产出一个能启动、能测试、能玩的纵向切片。
- 迁移行为，不迁移旧结构。旧的 metadata item/block、`GameRegistry`、GUI id、FML packet、ASM、NEI/MineTweaker 接口都要改写成 26.x 的注册、数据、网络、菜单和可选集成模型。
- 保持 `clayium` mod id 和资源 namespace；Java 包继续使用 `dev.clayium.clayium`。
- 注册 id 保持扁平、显式、稳定。不要重新引入 1.7.10 metadata id 作为主要数据模型。
- Recipe 和资源优先数据驱动；批量 JSON、loot、tag、model、lang 优先由 datagen 生成。
- 行为变更优先加 GameTest；确定性纯逻辑优先加 JUnit。
- 可选集成默认保持 `compileOnly` 和 `localRuntime`，除非某个阶段明确要发布硬依赖。
- 客户端 UI、render、JEI/Jade/LDLib 适配必须隔离在 client-only 路径，保持 dedicated server 可加载。
- 每个阶段结束前更新 DOX/devlog，运行对应验证命令，并记录没有自动化覆盖的手动复现步骤。

## 旧代码域划分

| 旧代码域 | 主要内容 | 迁移策略 |
| --- | --- | --- |
| `core/ClayiumCore.java` | 配置、启动、GUI id、网络、实体、世界生成、集成入口 | 拆成 config、registry、network、datagen、client、integration 子系统 |
| `block/CBlocks.java` | 所有方块、机器族、tile entity、harvest 设置 | 按机器家族和玩法阶段拆到 explicit block ids 与 block entity 类型 |
| `item/CMaterials.java` | 材料、形状、OD、tier、metadata item 映射 | 扩展当前 `ClayMaterial`、`ClayPartType`、`PartRegistry`，用 tag 替代旧 OD 语义 |
| `item/CItems.java` | 工具、过滤器、胶囊、gadget、misc/circuit metadata item | 按功能家族分阶段迁移，metadata misc 拆成显式 item |
| `util/crafting/CRecipes.java` | 25 个机器/工艺配方表和大量集成配方 | 先统一 recipe schema 和 machine recipe runtime，再批量迁移 recipes |
| `block/tile/*` | 机器状态、库存、能量、同步、自动化 | 建 NeoForge 26.x block entity 基类和 capability/attachment 边界 |
| `gui/*` | Container、GUI、slot、旧 draw helpers | 迁移到 menu/screen/LDLib 适配；共享 slot/layout helper |
| `network/*` | GUI button/text/key/mouse/PAN packets | 改写为 26.x custom payload，按 feature 注册 |
| `plugin/nei/*` | 旧 NEI recipe displays | 转为 JEI category；Jade provider 独立补充 |
| `plugin/*` | IC2、GregTech、Multipart、MineTweaker 等旧集成 | 默认不进核心阶段，后期按现代可用 mod/API 单独评估 |
| `pan/*`、`laser/*` | PAN 网络、激光系统 | 等能源/物流基建稳定后迁移 |
| `worldgen/*` | Clay ore、Clay tree | 用 26.x feature/placed feature/biome modifier/datagen 迁移 |
| `asm/*`、`sample/*` | 旧 ASM transformer | 默认废弃，不作为 NeoForge 26.x 迁移目标 |

## 阶段路线

### Phase 1.5：基线冻结与迁移账本

目标：把 Phase 1 变成后续开发的可审计起点。

交付物：

- 冻结 Phase 1 git baseline，确认当前未提交改动是否全部属于 Phase 1。
- 建立 `legacy-port-ledger`，列出旧 `CBlocks`、`CItems`、`CMaterials`、`CRecipes`、GUI、tile、network、integration 的迁移状态。
- 为旧 metadata 项制定 id 命名规则：材料部件、机器 hull、compressed clay、misc/circuit、upgrade、filter、gadget 分开处理。
- 明确哪些旧功能不迁移或延后：ASM、1.7.10 save 兼容、NEI 本体、已消失的旧 mod API。
- 跑一次全套 Phase 1 验证，并记录客户端 JEI Work Table 目视检查结果。

验收：

- `.\gradlew --version --no-watch-fs`
- `.\gradlew tasks --all --no-watch-fs --stacktrace`
- `.\gradlew test --no-watch-fs --stacktrace`
- `.\gradlew clean build --no-watch-fs --stacktrace`
- `.\gradlew runData --no-watch-fs --stacktrace`
- `.\gradlew runGameTestServer --no-watch-fs --stacktrace`
- `.\gradlew runServer --no-watch-fs --stacktrace`
- `.\gradlew runClient --no-watch-fs --stacktrace`，手动确认 Work Table 和 JEI category
- `git diff --check`

### Phase 2：材料、部件、压缩粘土与早期工具

目标：完整恢复早期材料经济，使后续机器 recipes 有稳定输入输出。

已验收落地切片：

- 已建立 `ClayiumContentCatalog`，新增内容从 catalog row 进入 registry、creative tab、datagen、tag、recipe、ledger 和测试。
- 已迁移 compressed/industrial/advanced clay 的注册、资源、loot/tag、压缩/反压缩 recipe。
- 已注册 simple/basic hull 与早期 circuit/progression item，但不添加临时生存桥接 recipe。
- 已扩展早期 dust/plate/large_plate material form，并避免全 material x shape 展开。
- 已把 clay shovel/pickaxe/wrench 从“皮肤 item”迁到 catalog 工具角色、真实工具组件、旧版耐久/挖掘速度、tooltip 和现代 item/block tags；wrench 的机器旋转行为等机器框架后接。
- 已验收“不做临时桥接配方”：simple/basic hull 和早期 circuit 的可生存生产链由 Phase 3/4 的机器框架与早期机器线提供。

范围：

- 本阶段验收范围以已落地切片为准；下列更大材料/工具扩展按 ledger 分配到 Phase 3+ / Phase 5+ 时继续迁移。
- 扩展 `ClayMaterial` 与 `ClayPartType` 到旧 `CMaterials` 的核心形状：plate、stick、short stick、ring、gear、blade、needle、disc、cylinder、pipe、large plate、grinding head、bearing、spindle、cutting head、water wheel、dust、ingot、gem 等。
- 迁移 compressed clay 阶梯、compressed clay shard、dense/industrial/advanced industrial clay 的基础材料链。
- 拆分旧 `itemMisc` 的 circuit、board、laser parts、synchronous parts 等关键 progression item。
- 补齐 clay piping tools、direction memory、synchronizer 的基础 item 和 recipes。
- 将旧 OreDictionary 语义改成 tags；只为当前阶段实际使用的材料生成 tag。
- 扩展 datagen：item model、lang、recipe、tag、loot。

不做：

- 不实现机器运行逻辑。
- 不迁移 filters/gadgets/capsules 的完整行为。
- 不引入外部 mod 集成 recipes。

验收：

- 所有新增材料/部件有注册、模型、贴图、lang、必要 recipe/tag。
- JUnit 覆盖材料和 part registry 矩阵。
- GameTest 覆盖 early progression crafting/smelting。
- `runData` 生成结果可审查且 deterministic。

### Phase 3：通用机器框架

目标：在搬运大量机器前，先把现代机器运行时抽出来。

范围：

- 建立 tier、machine hull、machine block、machine block entity、menu、screen、slot layout 的共享模型。
- 建立 recipe runtime 抽象，覆盖旧 `SimpleMachinesRecipes` 和 `Recipes` 两类输入输出模型。
- 建立 Clay Energy 存储/消耗/传输的 26.x 表达，优先使用 NeoForge capability/attachment 方向，避免旧 `IClayEnergy` 直接照搬。
- 建立机器同步和按钮网络：progress、energy、redstone mode、side config、文本输入等都走 26.x payload 或 menu data。
- 建立 JEI/Jade/LDLib 适配基类，但只接入样板机器。
- 建立通用 GameTest fixture：放置机器、注入物品/能量、推进 tick、断言输出和掉落。

样板机器建议：

- Bending Machine 或 Cutting Machine 作为 `SimpleMachinesRecipes` 样板。
- Assembler 作为多输入 `Recipes` 样板。

验收：

- 至少 1 个简单机器和 1 个多输入机器完整可玩。
- 机器 recipe JSON、datagen、JEI display、GameTest 全部走共享基建。
- dedicated server 不加载 client 类。

### Phase 4：早期机器与第一条自动化生产线

目标：恢复 Clayium 早中期“材料加工到机器升级”的主线循环。

范围：

- 简单加工机器：Bending Machine、Wire Drawing Machine、Pipe Drawing Machine、Cutting Machine、Lathe、Milling Machine。
- 基础物质处理：Condenser、Grinder、Decomposer、Smelter。
- Clay Crafting Table 与早期自动加工入口。
- Clay Water Wheel、Dense Clay Water Wheel 或阶段内最小能源入口。
- 早期 hull、circuit、machine recipe，确保玩家能从 Work Table 进入机器时代。
- JEI 覆盖当前机器 recipe category。

验收：

- 从粘土球到第一批 tiered machine 的流程可以纯 survival 完成。
- GameTest 覆盖至少一条端到端生产链。
- JEI 能展示所有当前阶段机器 recipes。

### Phase 5：完整材料经济、矿物与世界生成

目标：恢复中期材料扩展，让机器 recipes 不再只靠手写少量样例。

范围：

- 扩展 Clayium 自有材料：industrial clay、advanced industrial clay、energetic clay、excited clay、organic clay、clay steel、clayium、ultimate alloy、antimatter、pure antimatter、OEC、OPA 等。
- 迁移 impure/pure 元素链：aluminium、silicon、magnesium、sodium、lithium、zirconium、zinc、manganese、calcium、potassium、nickel 等。
- Compressed Clay 多级方块、machine hull 多 tier、other hull/material blocks。
- Clay ore、Clay tree、必要 worldgen/datapack 配置。
- 系统化 tags，建立材料兼容层，避免每个 recipe 写死单个 item。

验收：

- 材料 ledger 显示核心材料和形状已迁移或明确延期。
- 生成资源可重复，旧资源贴图映射有审查记录。
- GameTest 覆盖世界生成注册、关键 mining/drop/tag、材料链 recipe。

### Phase 6：高级加工、化学、反应与高阶能源

目标：迁移旧 `CRecipes` 的高级 recipe map 和中后期机器。

范围：

- Assembler、Inscriber、Centrifuge、Chemical Reactor、Alloy Smelter、Blast Furnace、Electrolysis Reactor、Matter Transformer。
- Clay Reactor、CA Condenser、CA Injector、CA Reactor、Energetic Clay Condenser/Decomposer。
- Salt Extractor、Quartz Crucible、Solar Clay Fabricator、Clay Fabricator。
- Overclocker、Resonator、Energy Storage Upgrade 与高阶能源规则。
- Progression rate/config 的现代配置方案。

验收：

- 每个 recipe map 有 serializer、datagen、JEI category、runtime test。
- 高阶机器支持能源、tier、overclock、输入输出锁定、失败状态。
- 至少一条从早期材料到 antimatter/pure antimatter 前置材料的自动化链可测试。

### Phase 7：储存、物流、接口、过滤器与流体

目标：恢复 Clayium 的自动化网络和库存/流体操作能力。

范围：

- Buffer、Multitrack Buffer、Storage Container、Vacuum Container、Metal Chest。
- Clay Distributor、Clay Interface、Redstone Interface、Clay Laser Interface、Clay Energy Laser、Laser Reflector。
- Auto Crafter、Auto Clay Condenser、Auto Trader。
- Fluid Translator、Fluid Transfer Machine、capsule 系统。
- Piping tools、IO tool、memory card、direction memory。
- Filter 家族：whitelist、blacklist、fuzzy、ore/tag、item name、unlocalized/name equivalent、unique id、mod id、damage、block metadata、harvestable、duplicator。

验收：

- 自动化行为有 headless GameTest：插入、抽取、过滤、方向、红石、流体转移。
- Jade provider 显示机器状态、能源、过滤器或库存摘要。
- 客户端 GUI 与 dedicated server 边界通过 `runServer` 验证。

### Phase 8：区域机器、PAN、实体与玩家工具

目标：迁移大体量但相对独立的世界交互系统。

范围：

- Area Collector、Miner、Area Miner、Advanced Area Miner、Area Replacer、Area Activator。
- Clay Marker、Open Pit Marker、Ground Leveling Marker、Prism Marker。
- PAN Core、PAN Adapter、PAN Duplicator、PAN Cable。
- Clay Ball、Teleport Ball、Clay Shooter、Instant Teleporter。
- Gadget holder 和 gadgets：overclock、flight、health、auto eat、repeated attack、long arm。
- Chunk Loader 如仍保留，必须重新评估现代 NeoForge 和服务器规则。

验收：

- 区域机器有边界、权限、性能、掉落和中断测试。
- PAN 网络有保存/加载、连接、断开、跨区块行为测试。
- 实体和玩家工具有 dedicated server 与客户端 smoke。

### Phase 9：外部集成与配方扩展包

目标：把旧时代集成从核心玩法中解耦，并按现代生态重新选择。

范围：

- JEI：所有机器 category、transfer handling、catalyst、info pages。
- Jade：方块、机器、能源、储存、区域机器 provider。
- LDLib2：可复用机器 UI adapter，只在确实降低复杂度时推进。
- CraftTweaker/KubeJS：替代旧 MineTweaker 的脚本扩展路线，单独评估。
- Mekanism、FTB、Pipez、Compact Machines、Architectury 等现代可选集成按当前 26.x 可用性重新验证。
- 旧 IC2、GregTech、ForgeMultipart、Thermal、AE2 等 1.7.10 集成不得原样迁移；每个都需要独立可行性和目标 mod 决策。

验收：

- 核心 jar 不硬依赖可选集成。
- 集成缺失时 dedicated server 和 client 都能启动。
- 每个集成有最小 smoke 或 GameTest/JUnit 替代验证。

### Phase 10：平衡、配置、兼容与发布硬化

目标：从“功能迁移完成”推进到“可维护 alpha/beta 发布”。

范围：

- 现代 config：progression rate、utility mode 是否保留、worldgen、integration toggles、RF/FE conversion 等。
- 数据版本和迁移策略：不承诺 1.7.10 世界兼容，除非单独开阶段；但要保证 26.x alpha 内部 id 稳定。
- 语言：补齐 en_us，评估从旧 `ja_JP.lang` 迁移 `ja_jp.json`。
- 资源审计：旧贴图搬运、命名、缺失材质、GUI atlas、model consistency。
- 性能审计：区域机器、网络、recipe cache、JEI loading、datagen 时间。
- 发布流程：版本号、changelog、known issues、manual QA checklist。

验收：

- `clean build`、`test`、`runData`、`runGameTestServer`、`runServer`、`runClient` 全部完成或有明确环境阻塞说明。
- 无缺失模型/贴图/recipe parse 错误。
- 至少一份 survival progression QA 记录从新世界走到高阶目标。

## 推荐优先级

1. 先做 Phase 1.5。没有迁移账本，后面很容易重复判断旧 metadata 和 recipe。
2. Phase 2 与 Phase 3 不要合并。材料和机器基建同时铺开会让 diff 过大，也会让 GameTest 很难定位失败。
3. Phase 4 只追求第一条完整机器生产线，不追求把所有低阶机器一次性补齐。
4. Phase 5 之后再扩大 recipe 数量。材料、tag、datagen、recipe schema 稳定前，大量 recipe 搬运会变成返工。
5. 外部集成放到 Phase 9。JEI/Jade 可以随阶段补当前机器，但旧 mod 兼容不要拖慢核心迁移。

## 每阶段固定收尾

- 重新读取受影响路径的 DOX 链。
- 更新最近的 `AGENTS.md` 和必要的父级文档。
- 更新 `docs/devlogs.md`，记录目标、改动文件、关键决策、验证状态、未决 TODO、下一步命令。
- 运行该阶段对应验证；最少需要 `.\gradlew clean build --no-watch-fs --stacktrace` 和 `git diff --check`。
- 审查 datagen diff，确认命名、namespace、tag、loot、recipe 和结构资源没有批量误写。
- 在最终报告中列出没有自动化覆盖的行为和具体手动复现步骤。

## 下一步建议

- 提交已验收的 Phase 2 material catalog slice。
- Phase 3 先做通用机器框架、机器 recipe runtime、能源/同步/菜单边界和样板机器。
- Phase 4 用机器框架补 simple/basic hull、早期 circuit、真实机器加工链和第一条 survival 自动化生产线。
- Phase 5 之后再扩大材料数量和 recipe 数量，避免在机器 schema 稳定前批量返工。
