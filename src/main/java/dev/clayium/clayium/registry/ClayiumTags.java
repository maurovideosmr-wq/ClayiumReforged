package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ClayiumTags {
    private ClayiumTags() {
    }

    public static final class Items {
        public static final TagKey<Item> CLAY_TOOLS = item("tools/clay");
        public static final TagKey<Item> RAW_CLAY_TOOLS = item("tools/raw_clay");
        public static final TagKey<Item> CLAY_WORK_TABLE_TOOLS = item("tools/clay_work_table");
        public static final TagKey<Item> CLAY_MACHINE_TOOLS = item("tools/clay_machine");
        public static final TagKey<Item> CLAY_MINING_TOOLS = item("tools/clay_mining");
        public static final TagKey<Item> CLAY_ROLLING_PINS = item("tools/clay_rolling_pins");
        public static final TagKey<Item> CLAY_SLICERS = item("tools/clay_slicers");
        public static final TagKey<Item> CLAY_SPATULAS = item("tools/clay_spatulas");
        public static final TagKey<Item> CLAY_WRENCHES = item("tools/clay_wrenches");
        public static final TagKey<Item> CLAY_TOOL_REPAIR_MATERIALS = item("repairs/clay_tools");

        private Items() {
        }
    }

    public static final class Blocks {
        public static final TagKey<Block> CLAY_SHOVEL_FAST = block("mineable/clay_shovel_fast");
        public static final TagKey<Block> CLAY_ORES = block("ores/clay");

        private Blocks() {
        }
    }

    private static TagKey<Item> item(String path) {
        return TagKey.create(Registries.ITEM, id(path));
    }

    private static TagKey<Block> block(String path) {
        return TagKey.create(Registries.BLOCK, id(path));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Clayium.MOD_ID, path);
    }
}
