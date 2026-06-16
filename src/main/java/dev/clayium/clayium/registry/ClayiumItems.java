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
    public static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS = Collections.unmodifiableList(CREATIVE_ITEMS_MUTABLE);

    public static final DeferredItem<BlockItem> DENSE_CLAY = blockItem("dense_clay", ClayiumBlocks.DENSE_CLAY);
    public static final DeferredItem<BlockItem> CLAY_WORK_TABLE = blockItem("clay_work_table", ClayiumBlocks.CLAY_WORK_TABLE);
    public static final DeferredItem<BlockItem> RAW_CLAY_MACHINE_HULL = blockItem("raw_clay_machine_hull", ClayiumBlocks.RAW_CLAY_MACHINE_HULL);
    public static final DeferredItem<BlockItem> CLAY_MACHINE_HULL = blockItem("clay_machine_hull", ClayiumBlocks.CLAY_MACHINE_HULL);

    public static final DeferredItem<Item> RAW_CLAY_ROLLING_PIN = item("raw_clay_rolling_pin");
    public static final DeferredItem<Item> RAW_CLAY_SLICER = item("raw_clay_slicer");
    public static final DeferredItem<Item> RAW_CLAY_SPATULA = item("raw_clay_spatula");
    public static final DeferredItem<Item> CLAY_ROLLING_PIN = item("clay_rolling_pin");
    public static final DeferredItem<Item> CLAY_SLICER = item("clay_slicer");
    public static final DeferredItem<Item> CLAY_SPATULA = item("clay_spatula");

    public static final DeferredItem<Item> CLAY_PLATE = item("clay_plate");
    public static final DeferredItem<Item> CLAY_STICK = item("clay_stick");
    public static final DeferredItem<Item> CLAY_SHORT_STICK = item("clay_short_stick");
    public static final DeferredItem<Item> CLAY_RING = item("clay_ring");
    public static final DeferredItem<Item> CLAY_SMALL_RING = item("clay_small_ring");
    public static final DeferredItem<Item> CLAY_GEAR = item("clay_gear");
    public static final DeferredItem<Item> CLAY_BLADE = item("clay_blade");
    public static final DeferredItem<Item> CLAY_NEEDLE = item("clay_needle");
    public static final DeferredItem<Item> CLAY_DISC = item("clay_disc");
    public static final DeferredItem<Item> CLAY_SMALL_DISC = item("clay_small_disc");
    public static final DeferredItem<Item> CLAY_CYLINDER = item("clay_cylinder");
    public static final DeferredItem<Item> CLAY_PIPE = item("clay_pipe");
    public static final DeferredItem<Item> CLAY_LARGE_BALL = item("clay_large_ball");
    public static final DeferredItem<Item> CLAY_LARGE_PLATE = item("clay_large_plate");
    public static final DeferredItem<Item> CLAY_GRINDING_HEAD = item("clay_grinding_head");
    public static final DeferredItem<Item> CLAY_BEARING = item("clay_bearing");
    public static final DeferredItem<Item> CLAY_SPINDLE = item("clay_spindle");
    public static final DeferredItem<Item> CLAY_CUTTING_HEAD = item("clay_cutting_head");
    public static final DeferredItem<Item> CLAY_WATER_WHEEL = item("clay_water_wheel");

    public static final DeferredItem<Item> DENSE_CLAY_PLATE = item("dense_clay_plate");
    public static final DeferredItem<Item> DENSE_CLAY_STICK = item("dense_clay_stick");
    public static final DeferredItem<Item> DENSE_CLAY_SHORT_STICK = item("dense_clay_short_stick");
    public static final DeferredItem<Item> DENSE_CLAY_RING = item("dense_clay_ring");
    public static final DeferredItem<Item> DENSE_CLAY_SMALL_RING = item("dense_clay_small_ring");
    public static final DeferredItem<Item> DENSE_CLAY_GEAR = item("dense_clay_gear");
    public static final DeferredItem<Item> DENSE_CLAY_BLADE = item("dense_clay_blade");
    public static final DeferredItem<Item> DENSE_CLAY_NEEDLE = item("dense_clay_needle");
    public static final DeferredItem<Item> DENSE_CLAY_DISC = item("dense_clay_disc");
    public static final DeferredItem<Item> DENSE_CLAY_SMALL_DISC = item("dense_clay_small_disc");
    public static final DeferredItem<Item> DENSE_CLAY_CYLINDER = item("dense_clay_cylinder");
    public static final DeferredItem<Item> DENSE_CLAY_PIPE = item("dense_clay_pipe");
    public static final DeferredItem<Item> DENSE_CLAY_LARGE_PLATE = item("dense_clay_large_plate");
    public static final DeferredItem<Item> DENSE_CLAY_GRINDING_HEAD = item("dense_clay_grinding_head");
    public static final DeferredItem<Item> DENSE_CLAY_BEARING = item("dense_clay_bearing");
    public static final DeferredItem<Item> DENSE_CLAY_SPINDLE = item("dense_clay_spindle");
    public static final DeferredItem<Item> DENSE_CLAY_CUTTING_HEAD = item("dense_clay_cutting_head");
    public static final DeferredItem<Item> DENSE_CLAY_WATER_WHEEL = item("dense_clay_water_wheel");

    private ClayiumItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static DeferredItem<Item> item(String name) {
        return track(ITEMS.registerSimpleItem(name));
    }

    private static DeferredItem<BlockItem> blockItem(String name, Supplier<? extends Block> block) {
        return track(ITEMS.registerSimpleBlockItem(name, block));
    }

    private static <T extends Item> DeferredItem<T> track(DeferredItem<T> item) {
        CREATIVE_ITEMS_MUTABLE.add(item);
        return item;
    }
}
