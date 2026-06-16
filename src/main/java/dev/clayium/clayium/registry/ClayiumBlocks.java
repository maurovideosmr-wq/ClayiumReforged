package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.block.ClayWorkTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Clayium.MOD_ID);

    public static final DeferredBlock<Block> DENSE_CLAY = BLOCKS.registerSimpleBlock(
            "dense_clay",
            properties -> properties
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(1.0F, 1.0F)
                    .sound(SoundType.GRAVEL)
    );

    public static final DeferredBlock<ClayWorkTableBlock> CLAY_WORK_TABLE = BLOCKS.registerBlock(
            "clay_work_table",
            ClayWorkTableBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
                    .mapColor(MapColor.TERRACOTTA_BROWN)
                    .strength(2.0F, 2.0F)
                    .sound(SoundType.WOOD)
    );

    public static final DeferredBlock<Block> RAW_CLAY_MACHINE_HULL = BLOCKS.registerSimpleBlock(
            "raw_clay_machine_hull",
            properties -> machineHullProperties()
    );

    public static final DeferredBlock<Block> CLAY_MACHINE_HULL = BLOCKS.registerSimpleBlock(
            "clay_machine_hull",
            properties -> machineHullProperties()
    );

    private ClayiumBlocks() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static BlockBehaviour.Properties machineHullProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                .mapColor(MapColor.TERRACOTTA_BROWN)
                .strength(2.0F, 5.0F)
                .sound(SoundType.STONE);
    }
}
