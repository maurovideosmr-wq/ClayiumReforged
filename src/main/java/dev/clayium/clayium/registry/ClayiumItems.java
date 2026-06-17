package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.item.ClayiumBlockItem;
import dev.clayium.clayium.item.ClayiumTieredItem;
import dev.clayium.clayium.item.ClayCraftingToolItem;
import dev.clayium.clayium.item.ClayPickaxeItem;
import dev.clayium.clayium.item.ClayShovelItem;
import dev.clayium.clayium.item.ClayWrenchItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Clayium.MOD_ID);

    private static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_BLOCKS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_TOOLS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_PROGRESSION_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_PARTS_MUTABLE = new ArrayList<>();
    public static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS = Collections.unmodifiableList(CREATIVE_ITEMS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_BLOCKS = Collections.unmodifiableList(CREATIVE_BLOCKS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_TOOLS = Collections.unmodifiableList(CREATIVE_TOOLS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_PROGRESSION = Collections.unmodifiableList(CREATIVE_PROGRESSION_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_PARTS = Collections.unmodifiableList(CREATIVE_PARTS_MUTABLE);

    private static final Map<String, DeferredItem<BlockItem>> BLOCK_ITEMS = registerBlockItems();
    private static final Map<String, DeferredItem<Item>> SIMPLE_ITEMS = registerSimpleItems();

    public static final DeferredItem<BlockItem> DENSE_CLAY = blockItem("dense_clay");
    public static final DeferredItem<BlockItem> COMPRESSED_CLAY = blockItem("compressed_clay");
    public static final DeferredItem<BlockItem> INDUSTRIAL_CLAY = blockItem("industrial_clay");
    public static final DeferredItem<BlockItem> ADVANCED_INDUSTRIAL_CLAY = blockItem("advanced_industrial_clay");
    public static final DeferredItem<BlockItem> CLAY_WORK_TABLE = blockItem("clay_work_table");
    public static final DeferredItem<BlockItem> RAW_CLAY_MACHINE_HULL = blockItem("raw_clay_machine_hull");
    public static final DeferredItem<BlockItem> CLAY_MACHINE_HULL = blockItem("clay_machine_hull");
    public static final DeferredItem<BlockItem> DENSE_CLAY_MACHINE_HULL = blockItem("dense_clay_machine_hull");
    public static final DeferredItem<BlockItem> SIMPLE_MACHINE_HULL = blockItem("simple_machine_hull");
    public static final DeferredItem<BlockItem> BASIC_MACHINE_HULL = blockItem("basic_machine_hull");

    public static final DeferredItem<Item> RAW_CLAY_ROLLING_PIN = simpleItem("raw_clay_rolling_pin");
    public static final DeferredItem<Item> RAW_CLAY_SLICER = simpleItem("raw_clay_slicer");
    public static final DeferredItem<Item> RAW_CLAY_SPATULA = simpleItem("raw_clay_spatula");
    public static final DeferredItem<Item> CLAY_ROLLING_PIN = simpleItem("clay_rolling_pin");
    public static final DeferredItem<Item> CLAY_SLICER = simpleItem("clay_slicer");
    public static final DeferredItem<Item> CLAY_SPATULA = simpleItem("clay_spatula");
    public static final DeferredItem<Item> CLAY_SHOVEL = simpleItem("clay_shovel");
    public static final DeferredItem<Item> CLAY_PICKAXE = simpleItem("clay_pickaxe");
    public static final DeferredItem<Item> CLAY_WRENCH = simpleItem("clay_wrench");

    public static final DeferredItem<Item> CLAY_CIRCUIT_BOARD = simpleItem("clay_circuit_board");
    public static final DeferredItem<Item> CLAY_CIRCUIT = simpleItem("clay_circuit");
    public static final DeferredItem<Item> SIMPLE_CIRCUIT = simpleItem("simple_circuit");
    public static final DeferredItem<Item> BASIC_CIRCUIT = simpleItem("basic_circuit");
    public static final DeferredItem<Item> CEE_BOARD = simpleItem("cee_board");
    public static final DeferredItem<Item> CEE_CIRCUIT = simpleItem("cee_circuit");
    public static final DeferredItem<Item> CEE = simpleItem("cee");
    public static final DeferredItem<Item> COMPRESSED_CLAY_SHARD = simpleItem("compressed_clay_shard");
    public static final DeferredItem<Item> INDUSTRIAL_CLAY_SHARD = simpleItem("industrial_clay_shard");
    public static final DeferredItem<Item> ADVANCED_INDUSTRIAL_CLAY_SHARD = simpleItem("advanced_industrial_clay_shard");

    public static final PartRegistry PARTS = PartRegistry.register(ITEMS, ClayiumItems::trackPart);

    public static final DeferredItem<Item> CLAY_PLATE = part(ClayMaterial.CLAY, ClayPartType.PLATE);
    public static final DeferredItem<Item> CLAY_STICK = part(ClayMaterial.CLAY, ClayPartType.STICK);
    public static final DeferredItem<Item> CLAY_SHORT_STICK = part(ClayMaterial.CLAY, ClayPartType.SHORT_STICK);
    public static final DeferredItem<Item> CLAY_RING = part(ClayMaterial.CLAY, ClayPartType.RING);
    public static final DeferredItem<Item> CLAY_SMALL_RING = part(ClayMaterial.CLAY, ClayPartType.SMALL_RING);
    public static final DeferredItem<Item> CLAY_GEAR = part(ClayMaterial.CLAY, ClayPartType.GEAR);
    public static final DeferredItem<Item> CLAY_BLADE = part(ClayMaterial.CLAY, ClayPartType.BLADE);
    public static final DeferredItem<Item> CLAY_NEEDLE = part(ClayMaterial.CLAY, ClayPartType.NEEDLE);
    public static final DeferredItem<Item> CLAY_DISC = part(ClayMaterial.CLAY, ClayPartType.DISC);
    public static final DeferredItem<Item> CLAY_SMALL_DISC = part(ClayMaterial.CLAY, ClayPartType.SMALL_DISC);
    public static final DeferredItem<Item> CLAY_CYLINDER = part(ClayMaterial.CLAY, ClayPartType.CYLINDER);
    public static final DeferredItem<Item> CLAY_PIPE = part(ClayMaterial.CLAY, ClayPartType.PIPE);
    public static final DeferredItem<Item> CLAY_LARGE_BALL = part(ClayMaterial.CLAY, ClayPartType.LARGE_BALL);
    public static final DeferredItem<Item> CLAY_LARGE_PLATE = part(ClayMaterial.CLAY, ClayPartType.LARGE_PLATE);
    public static final DeferredItem<Item> CLAY_GRINDING_HEAD = part(ClayMaterial.CLAY, ClayPartType.GRINDING_HEAD);
    public static final DeferredItem<Item> CLAY_BEARING = part(ClayMaterial.CLAY, ClayPartType.BEARING);
    public static final DeferredItem<Item> CLAY_SPINDLE = part(ClayMaterial.CLAY, ClayPartType.SPINDLE);
    public static final DeferredItem<Item> CLAY_CUTTING_HEAD = part(ClayMaterial.CLAY, ClayPartType.CUTTING_HEAD);
    public static final DeferredItem<Item> CLAY_WATER_WHEEL = part(ClayMaterial.CLAY, ClayPartType.WATER_WHEEL);
    public static final DeferredItem<Item> CLAY_DUST = part(ClayMaterial.CLAY, ClayPartType.DUST);

    public static final DeferredItem<Item> DENSE_CLAY_PLATE = part(ClayMaterial.DENSE_CLAY, ClayPartType.PLATE);
    public static final DeferredItem<Item> DENSE_CLAY_STICK = part(ClayMaterial.DENSE_CLAY, ClayPartType.STICK);
    public static final DeferredItem<Item> DENSE_CLAY_SHORT_STICK = part(ClayMaterial.DENSE_CLAY, ClayPartType.SHORT_STICK);
    public static final DeferredItem<Item> DENSE_CLAY_RING = part(ClayMaterial.DENSE_CLAY, ClayPartType.RING);
    public static final DeferredItem<Item> DENSE_CLAY_SMALL_RING = part(ClayMaterial.DENSE_CLAY, ClayPartType.SMALL_RING);
    public static final DeferredItem<Item> DENSE_CLAY_GEAR = part(ClayMaterial.DENSE_CLAY, ClayPartType.GEAR);
    public static final DeferredItem<Item> DENSE_CLAY_BLADE = part(ClayMaterial.DENSE_CLAY, ClayPartType.BLADE);
    public static final DeferredItem<Item> DENSE_CLAY_NEEDLE = part(ClayMaterial.DENSE_CLAY, ClayPartType.NEEDLE);
    public static final DeferredItem<Item> DENSE_CLAY_DISC = part(ClayMaterial.DENSE_CLAY, ClayPartType.DISC);
    public static final DeferredItem<Item> DENSE_CLAY_SMALL_DISC = part(ClayMaterial.DENSE_CLAY, ClayPartType.SMALL_DISC);
    public static final DeferredItem<Item> DENSE_CLAY_CYLINDER = part(ClayMaterial.DENSE_CLAY, ClayPartType.CYLINDER);
    public static final DeferredItem<Item> DENSE_CLAY_PIPE = part(ClayMaterial.DENSE_CLAY, ClayPartType.PIPE);
    public static final DeferredItem<Item> DENSE_CLAY_LARGE_PLATE = part(ClayMaterial.DENSE_CLAY, ClayPartType.LARGE_PLATE);
    public static final DeferredItem<Item> DENSE_CLAY_GRINDING_HEAD = part(ClayMaterial.DENSE_CLAY, ClayPartType.GRINDING_HEAD);
    public static final DeferredItem<Item> DENSE_CLAY_BEARING = part(ClayMaterial.DENSE_CLAY, ClayPartType.BEARING);
    public static final DeferredItem<Item> DENSE_CLAY_SPINDLE = part(ClayMaterial.DENSE_CLAY, ClayPartType.SPINDLE);
    public static final DeferredItem<Item> DENSE_CLAY_CUTTING_HEAD = part(ClayMaterial.DENSE_CLAY, ClayPartType.CUTTING_HEAD);
    public static final DeferredItem<Item> DENSE_CLAY_WATER_WHEEL = part(ClayMaterial.DENSE_CLAY, ClayPartType.WATER_WHEEL);
    public static final DeferredItem<Item> DENSE_CLAY_DUST = part(ClayMaterial.DENSE_CLAY, ClayPartType.DUST);

    public static final DeferredItem<Item> INDUSTRIAL_CLAY_PLATE = part(ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.PLATE);
    public static final DeferredItem<Item> INDUSTRIAL_CLAY_LARGE_PLATE = part(ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.LARGE_PLATE);
    public static final DeferredItem<Item> INDUSTRIAL_CLAY_DUST = part(ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.DUST);
    public static final DeferredItem<Item> ADVANCED_INDUSTRIAL_CLAY_PLATE = part(ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.PLATE);
    public static final DeferredItem<Item> ADVANCED_INDUSTRIAL_CLAY_LARGE_PLATE = part(ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.LARGE_PLATE);
    public static final DeferredItem<Item> ADVANCED_INDUSTRIAL_CLAY_DUST = part(ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.DUST);
    public static final DeferredItem<Item> ENERGIZED_CLAY_DUST = part(ClayMaterial.ENERGIZED_CLAY, ClayPartType.DUST);

    private ClayiumItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static DeferredItem<BlockItem> blockItem(String id) {
        DeferredItem<BlockItem> item = BLOCK_ITEMS.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Unknown Clayium block item id: " + id);
        }
        return item;
    }

    public static DeferredItem<Item> simpleItem(String id) {
        DeferredItem<Item> item = SIMPLE_ITEMS.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Unknown Clayium item id: " + id);
        }
        return item;
    }

    private static Map<String, DeferredItem<BlockItem>> registerBlockItems() {
        Map<String, DeferredItem<BlockItem>> blockItems = new LinkedHashMap<>();
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            DeferredItem<BlockItem> item = trackBlock(ITEMS.<BlockItem>registerItem(
                    spec.id(),
                    properties -> new ClayiumBlockItem(ClayiumBlocks.catalogBlock(spec.id()).get(), properties, spec.tier())
            ));
            blockItems.put(spec.id(), item);
        }
        return Collections.unmodifiableMap(blockItems);
    }

    private static Map<String, DeferredItem<Item>> registerSimpleItems() {
        Map<String, DeferredItem<Item>> simpleItems = new LinkedHashMap<>();
        for (ClayiumContentCatalog.SimpleItemSpec spec : ClayiumContentCatalog.simpleItems()) {
            DeferredItem<Item> item = registerSimpleItem(spec);
            trackSimpleItem(spec, item);
            simpleItems.put(spec.id(), item);
        }
        return Collections.unmodifiableMap(simpleItems);
    }

    private static DeferredItem<Item> registerSimpleItem(ClayiumContentCatalog.SimpleItemSpec spec) {
        return switch (spec.kind()) {
            case GENERIC, RAW_CLAY_TOOL -> ITEMS.<Item>registerItem(
                    spec.id(),
                    properties -> new ClayiumTieredItem(properties, spec.tier())
            );
            case WORK_TABLE_TOOL -> ITEMS.<Item>registerItem(
                    spec.id(),
                    properties -> new ClayCraftingToolItem(properties.durability(spec.durability()), spec.brokenClayBallCount(), spec.tier())
            );
            case CLAY_SHOVEL -> ITEMS.<Item>registerItem(spec.id(), properties -> new ClayShovelItem(properties, spec.tier()));
            case CLAY_PICKAXE -> ITEMS.<Item>registerItem(spec.id(), properties -> new ClayPickaxeItem(properties, spec.tier()));
            case CLAY_WRENCH -> ITEMS.<Item>registerItem(spec.id(), properties -> new ClayWrenchItem(properties, spec.tier()));
        };
    }

    private static DeferredItem<Item> part(ClayMaterial material, ClayPartType partType) {
        return PARTS.get(material, partType);
    }

    private static <T extends Item> DeferredItem<T> trackBlock(DeferredItem<T> item) {
        CREATIVE_BLOCKS_MUTABLE.add(item);
        return track(item);
    }

    private static void trackSimpleItem(ClayiumContentCatalog.SimpleItemSpec spec, DeferredItem<Item> item) {
        switch (spec.creativeCategory()) {
            case TOOLS -> CREATIVE_TOOLS_MUTABLE.add(item);
            case PROGRESSION -> CREATIVE_PROGRESSION_MUTABLE.add(item);
            case PARTS -> CREATIVE_PARTS_MUTABLE.add(item);
            case BLOCKS -> CREATIVE_BLOCKS_MUTABLE.add(item);
        }
        track(item);
    }

    private static void trackPart(DeferredItem<Item> item) {
        CREATIVE_PARTS_MUTABLE.add(item);
        track(item);
    }

    private static <T extends Item> DeferredItem<T> track(DeferredItem<T> item) {
        CREATIVE_ITEMS_MUTABLE.add(item);
        return item;
    }
}
