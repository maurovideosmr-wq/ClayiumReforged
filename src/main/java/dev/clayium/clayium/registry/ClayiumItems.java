package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Clayium.MOD_ID);

    private static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_BLOCKS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_TOOLS_MUTABLE = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CREATIVE_PARTS_MUTABLE = new ArrayList<>();
    public static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS = Collections.unmodifiableList(CREATIVE_ITEMS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_BLOCKS = Collections.unmodifiableList(CREATIVE_BLOCKS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_TOOLS = Collections.unmodifiableList(CREATIVE_TOOLS_MUTABLE);
    public static final List<DeferredItem<? extends Item>> CREATIVE_PARTS = Collections.unmodifiableList(CREATIVE_PARTS_MUTABLE);

    public static final DeferredItem<BlockItem> DENSE_CLAY = blockItem("dense_clay", ClayiumBlocks.DENSE_CLAY);
    public static final DeferredItem<BlockItem> CLAY_WORK_TABLE = blockItem("clay_work_table", ClayiumBlocks.CLAY_WORK_TABLE);
    public static final DeferredItem<BlockItem> RAW_CLAY_MACHINE_HULL = blockItem("raw_clay_machine_hull", ClayiumBlocks.RAW_CLAY_MACHINE_HULL);
    public static final DeferredItem<BlockItem> CLAY_MACHINE_HULL = blockItem("clay_machine_hull", ClayiumBlocks.CLAY_MACHINE_HULL);

    public static final DeferredItem<Item> RAW_CLAY_ROLLING_PIN = toolItem("raw_clay_rolling_pin");
    public static final DeferredItem<Item> RAW_CLAY_SLICER = toolItem("raw_clay_slicer");
    public static final DeferredItem<Item> RAW_CLAY_SPATULA = toolItem("raw_clay_spatula");
    public static final DeferredItem<Item> CLAY_ROLLING_PIN = toolItem("clay_rolling_pin");
    public static final DeferredItem<Item> CLAY_SLICER = toolItem("clay_slicer");
    public static final DeferredItem<Item> CLAY_SPATULA = toolItem("clay_spatula");

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

    private ClayiumItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static DeferredItem<Item> toolItem(String name) {
        return trackTool(ITEMS.registerSimpleItem(name));
    }

    private static DeferredItem<BlockItem> blockItem(String name, Supplier<? extends Block> block) {
        return trackBlock(ITEMS.registerSimpleBlockItem(name, block));
    }

    private static DeferredItem<Item> part(ClayMaterial material, ClayPartType partType) {
        return PARTS.get(material, partType);
    }

    private static <T extends Item> DeferredItem<T> trackBlock(DeferredItem<T> item) {
        CREATIVE_BLOCKS_MUTABLE.add(item);
        return track(item);
    }

    private static <T extends Item> DeferredItem<T> trackTool(DeferredItem<T> item) {
        CREATIVE_TOOLS_MUTABLE.add(item);
        return track(item);
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
