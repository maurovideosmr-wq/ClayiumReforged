package dev.clayium.clayium.registry;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ClayiumContentCatalog {
    private static final List<BlockSpec> BLOCKS = List.of(
            block("dense_clay", "Dense Clay", "dense_clay", 0, BlockKind.COMPRESSED_CLAY, HarvestTool.SHOVEL, ContentStatus.PORTED),
            block("compressed_clay", "Compressed Clay", "compressed_clay", 1, BlockKind.COMPRESSED_CLAY, HarvestTool.SHOVEL, ContentStatus.PORTED),
            block("industrial_clay", "Industrial Clay", "industrial_clay", 2, BlockKind.COMPRESSED_CLAY, HarvestTool.SHOVEL, ContentStatus.PORTED),
            block("advanced_industrial_clay", "Advanced Industrial Clay", "advanced_industrial_clay", 3, BlockKind.COMPRESSED_CLAY, HarvestTool.SHOVEL, ContentStatus.PORTED),
            block("clay_work_table", "Clay Work Table", "clay_work_table", 0, BlockKind.WORK_TABLE, HarvestTool.NONE, ContentStatus.PORTED),
            block("raw_clay_machine_hull", "Raw Clay Machine Hull", "raw_clay_machine_hull", 1, BlockKind.RAW_MACHINE_HULL, HarvestTool.SHOVEL, ContentStatus.PORTED),
            machineHull(MachineHullTier.CLAY),
            machineHull(MachineHullTier.DENSE_CLAY),
            machineHull(MachineHullTier.SIMPLE),
            machineHull(MachineHullTier.BASIC)
    );
    private static final Map<String, BlockSpec> BLOCKS_BY_ID = BLOCKS.stream()
            .collect(Collectors.toUnmodifiableMap(BlockSpec::id, Function.identity()));

    private static final List<MaterialFormSpec> MATERIAL_FORMS = createMaterialForms();
    private static final List<MaterialFormSpec> REGISTERED_MATERIAL_ITEMS = MATERIAL_FORMS.stream()
            .filter(MaterialFormSpec::registersItem)
            .toList();
    private static final Map<ClayPartType, Set<ClayMaterial>> MATERIALS_BY_PART_TYPE = REGISTERED_MATERIAL_ITEMS.stream()
            .collect(Collectors.groupingBy(
                    MaterialFormSpec::partType,
                    () -> new EnumMap<>(ClayPartType.class),
                    Collectors.mapping(MaterialFormSpec::material, Collectors.toUnmodifiableSet())
            ));

    private static final List<SimpleItemSpec> SIMPLE_ITEMS = List.of(
            rawTool("raw_clay_rolling_pin", "Raw Clay Rolling Pin", ContentStatus.PORTED),
            rawTool("raw_clay_slicer", "Raw Clay Slicer", ContentStatus.PORTED),
            rawTool("raw_clay_spatula", "Raw Clay Spatula", ContentStatus.PORTED),
            workTableTool("clay_rolling_pin", "Clay Rolling Pin", 60, 4, ContentStatus.PORTED),
            workTableTool("clay_slicer", "Clay Slicer", 60, 3, ContentStatus.PORTED),
            workTableTool("clay_spatula", "Clay Spatula", 36, 2, ContentStatus.PORTED),
            tool("clay_shovel", "Clay Shovel", SimpleItemKind.CLAY_SHOVEL, ContentStatus.PORTED),
            tool("clay_pickaxe", "Clay Pickaxe", SimpleItemKind.CLAY_PICKAXE, ContentStatus.PORTED),
            tool("clay_wrench", "Clay Wrench", SimpleItemKind.CLAY_WRENCH, ContentStatus.PORTED),
            progression("clay_circuit_board", "Clay Circuit Board", 2, ContentStatus.REGISTERED_ONLY),
            progression("clay_circuit", "Clay Circuit", 2, ContentStatus.REGISTERED_ONLY),
            progression("simple_circuit", "Simple Circuit", 3, ContentStatus.REGISTERED_ONLY),
            progression("basic_circuit", "Basic Circuit", 4, ContentStatus.REGISTERED_ONLY),
            progression("cee_board", "CEE Board", 3, ContentStatus.REGISTERED_ONLY),
            progression("cee_circuit", "CEE Circuit", 3, ContentStatus.REGISTERED_ONLY),
            progression("cee", "Clay Energy Excitor", 3, ContentStatus.REGISTERED_ONLY),
            progression("compressed_clay_shard", "Compressed Clay Shard", 1, ContentStatus.REGISTERED_ONLY),
            progression("industrial_clay_shard", "Industrial Clay Shard", 2, ContentStatus.REGISTERED_ONLY),
            progression("advanced_industrial_clay_shard", "Advanced Industrial Clay Shard", 3, ContentStatus.REGISTERED_ONLY)
    );
    private static final Map<String, SimpleItemSpec> SIMPLE_ITEMS_BY_ID = SIMPLE_ITEMS.stream()
            .collect(Collectors.toUnmodifiableMap(SimpleItemSpec::id, Function.identity()));

    private ClayiumContentCatalog() {
    }

    public static List<BlockSpec> blocks() {
        return BLOCKS;
    }

    public static BlockSpec block(String id) {
        BlockSpec spec = BLOCKS_BY_ID.get(id);
        if (spec == null) {
            throw new IllegalArgumentException("Unknown Clayium block id: " + id);
        }
        return spec;
    }

    public static List<MaterialFormSpec> materialForms() {
        return MATERIAL_FORMS;
    }

    public static List<MaterialFormSpec> registeredMaterialItems() {
        return REGISTERED_MATERIAL_ITEMS;
    }

    public static Optional<MaterialFormSpec> materialForm(ClayMaterial material, ClayPartType partType) {
        return MATERIAL_FORMS.stream()
                .filter(spec -> spec.material() == material && spec.partType() == partType)
                .findFirst();
    }

    public static Set<ClayMaterial> registeredMaterialsFor(ClayPartType partType) {
        return MATERIALS_BY_PART_TYPE.getOrDefault(partType, Set.of());
    }

    public static List<SimpleItemSpec> simpleItems() {
        return SIMPLE_ITEMS;
    }

    public static SimpleItemSpec simpleItem(String id) {
        SimpleItemSpec spec = SIMPLE_ITEMS_BY_ID.get(id);
        if (spec == null) {
            throw new IllegalArgumentException("Unknown Clayium item id: " + id);
        }
        return spec;
    }

    private static List<MaterialFormSpec> createMaterialForms() {
        List<MaterialFormSpec> forms = new ArrayList<>();
        forms.add(form(ClayMaterial.CLAY, ClayPartType.BLOCK, MaterialFormStatus.EXTERNAL, "minecraft:clay"));
        forms.add(form(ClayMaterial.CLAY, ClayPartType.BALL, MaterialFormStatus.EXTERNAL, "minecraft:clay_ball"));
        forms.add(form(ClayMaterial.DENSE_CLAY, ClayPartType.BLOCK, MaterialFormStatus.BLOCK, "dense_clay"));
        forms.add(form(ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.BLOCK, MaterialFormStatus.BLOCK, "industrial_clay"));
        forms.add(form(ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.BLOCK, MaterialFormStatus.BLOCK, "advanced_industrial_clay"));

        addItemForms(forms, ClayMaterial.CLAY,
                ClayPartType.PLATE,
                ClayPartType.STICK,
                ClayPartType.SHORT_STICK,
                ClayPartType.RING,
                ClayPartType.SMALL_RING,
                ClayPartType.GEAR,
                ClayPartType.BLADE,
                ClayPartType.NEEDLE,
                ClayPartType.DISC,
                ClayPartType.SMALL_DISC,
                ClayPartType.CYLINDER,
                ClayPartType.PIPE,
                ClayPartType.LARGE_BALL,
                ClayPartType.LARGE_PLATE,
                ClayPartType.GRINDING_HEAD,
                ClayPartType.BEARING,
                ClayPartType.SPINDLE,
                ClayPartType.CUTTING_HEAD,
                ClayPartType.WATER_WHEEL,
                ClayPartType.DUST);
        addItemForms(forms, ClayMaterial.DENSE_CLAY,
                ClayPartType.PLATE,
                ClayPartType.STICK,
                ClayPartType.SHORT_STICK,
                ClayPartType.RING,
                ClayPartType.SMALL_RING,
                ClayPartType.GEAR,
                ClayPartType.BLADE,
                ClayPartType.NEEDLE,
                ClayPartType.DISC,
                ClayPartType.SMALL_DISC,
                ClayPartType.CYLINDER,
                ClayPartType.PIPE,
                ClayPartType.LARGE_PLATE,
                ClayPartType.GRINDING_HEAD,
                ClayPartType.BEARING,
                ClayPartType.SPINDLE,
                ClayPartType.CUTTING_HEAD,
                ClayPartType.WATER_WHEEL,
                ClayPartType.DUST);
        addItemForms(forms, ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.PLATE, ClayPartType.LARGE_PLATE, ClayPartType.DUST);
        addItemForms(forms, ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.PLATE, ClayPartType.LARGE_PLATE, ClayPartType.DUST);
        addItemForms(forms, ClayMaterial.ENERGIZED_CLAY, ClayPartType.DUST);
        return List.copyOf(forms);
    }

    private static void addItemForms(List<MaterialFormSpec> forms, ClayMaterial material, ClayPartType... partTypes) {
        for (ClayPartType partType : partTypes) {
            forms.add(form(material, partType, MaterialFormStatus.ITEM, material.itemId(partType)));
        }
    }

    private static MaterialFormSpec form(ClayMaterial material, ClayPartType partType, MaterialFormStatus status, String id) {
        return new MaterialFormSpec(material, partType, status, id);
    }

    private static BlockSpec block(String id, String translation, String texture, int tier, BlockKind kind, HarvestTool harvestTool, ContentStatus status) {
        return new BlockSpec(id, translation, texture, tier, kind, harvestTool, status);
    }

    private static BlockSpec machineHull(MachineHullTier tier) {
        return block(tier.id(), tier.translation(), tier.texture(), tier.tier(), BlockKind.MACHINE_HULL, HarvestTool.PICKAXE, tier.status());
    }

    private static SimpleItemSpec tool(String id, String translation, SimpleItemKind kind, ContentStatus status) {
        return tool(id, translation, kind, 0, 0, status);
    }

    private static SimpleItemSpec rawTool(String id, String translation, ContentStatus status) {
        return tool(id, translation, SimpleItemKind.RAW_CLAY_TOOL, status);
    }

    private static SimpleItemSpec workTableTool(String id, String translation, int durability, int brokenClayBallCount, ContentStatus status) {
        return tool(id, translation, SimpleItemKind.WORK_TABLE_TOOL, durability, brokenClayBallCount, status);
    }

    private static SimpleItemSpec tool(String id, String translation, SimpleItemKind kind, int durability, int brokenClayBallCount, ContentStatus status) {
        return new SimpleItemSpec(id, translation, CreativeCategory.TOOLS, ItemModelKind.GENERATED, kind, ClayMaterial.CLAY.tier(), durability, brokenClayBallCount, status);
    }

    private static SimpleItemSpec progression(String id, String translation, int tier, ContentStatus status) {
        return new SimpleItemSpec(id, translation, CreativeCategory.PROGRESSION, ItemModelKind.GENERATED, SimpleItemKind.GENERIC, tier, 0, 0, status);
    }

    public enum BlockKind {
        COMPRESSED_CLAY,
        WORK_TABLE,
        RAW_MACHINE_HULL,
        MACHINE_HULL
    }

    public enum HarvestTool {
        NONE,
        SHOVEL,
        PICKAXE
    }

    public enum CreativeCategory {
        BLOCKS,
        TOOLS,
        PARTS,
        PROGRESSION
    }

    public enum ItemModelKind {
        GENERATED
    }

    public enum SimpleItemKind {
        GENERIC,
        RAW_CLAY_TOOL,
        WORK_TABLE_TOOL,
        CLAY_SHOVEL,
        CLAY_PICKAXE,
        CLAY_WRENCH;

        public boolean isClayTool() {
            return this != GENERIC;
        }

        public boolean isMiningTool() {
            return this == CLAY_SHOVEL || this == CLAY_PICKAXE;
        }
    }

    public enum ContentStatus {
        PORTED,
        REGISTERED_ONLY,
        DEFERRED
    }

    public enum MaterialFormStatus {
        ITEM,
        BLOCK,
        EXTERNAL,
        DEFERRED
    }

    public record BlockSpec(
            String id,
            String translation,
            String texture,
            int tier,
            BlockKind kind,
            HarvestTool harvestTool,
            ContentStatus status
    ) {
    }

    public record MaterialFormSpec(
            ClayMaterial material,
            ClayPartType partType,
            MaterialFormStatus status,
            String id
    ) {
        public boolean registersItem() {
            return this.status == MaterialFormStatus.ITEM;
        }

        public String translation() {
            return this.partType.translation(this.material);
        }

        public int tier() {
            return this.material.tier();
        }
    }

    public record SimpleItemSpec(
            String id,
            String translation,
            CreativeCategory creativeCategory,
            ItemModelKind modelKind,
            SimpleItemKind kind,
            int tier,
            int durability,
            int brokenClayBallCount,
            ContentStatus status
    ) {
    }
}
