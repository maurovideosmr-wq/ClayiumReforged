package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.block.ClayWorkTableBlock;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
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

    private static final Map<String, DeferredBlock<? extends Block>> BLOCKS_BY_ID = registerCatalogBlocks();

    public static final DeferredBlock<Block> DENSE_CLAY = block("dense_clay");
    public static final DeferredBlock<Block> COMPRESSED_CLAY = block("compressed_clay");
    public static final DeferredBlock<Block> INDUSTRIAL_CLAY = block("industrial_clay");
    public static final DeferredBlock<Block> ADVANCED_INDUSTRIAL_CLAY = block("advanced_industrial_clay");
    public static final DeferredBlock<ClayWorkTableBlock> CLAY_WORK_TABLE = block("clay_work_table");
    public static final DeferredBlock<Block> RAW_CLAY_MACHINE_HULL = block("raw_clay_machine_hull");
    public static final DeferredBlock<Block> CLAY_MACHINE_HULL = machineHull(MachineHullTier.CLAY);
    public static final DeferredBlock<Block> DENSE_CLAY_MACHINE_HULL = machineHull(MachineHullTier.DENSE_CLAY);
    public static final DeferredBlock<Block> SIMPLE_MACHINE_HULL = machineHull(MachineHullTier.SIMPLE);
    public static final DeferredBlock<Block> BASIC_MACHINE_HULL = machineHull(MachineHullTier.BASIC);

    private ClayiumBlocks() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static DeferredBlock<Block> machineHull(MachineHullTier tier) {
        return block(tier.id());
    }

    public static DeferredBlock<? extends Block> catalogBlock(String id) {
        DeferredBlock<? extends Block> block = BLOCKS_BY_ID.get(id);
        if (block == null) {
            throw new IllegalArgumentException("Unknown Clayium block id: " + id);
        }
        return block;
    }

    public static Iterable<DeferredBlock<? extends Block>> catalogBlocks() {
        return BLOCKS_BY_ID.values();
    }

    private static Map<String, DeferredBlock<? extends Block>> registerCatalogBlocks() {
        Map<String, DeferredBlock<? extends Block>> blocks = new LinkedHashMap<>();
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            DeferredBlock<? extends Block> block = switch (spec.kind()) {
                case WORK_TABLE -> BLOCKS.registerBlock(
                        spec.id(),
                        ClayWorkTableBlock::new,
                        () -> blockProperties(spec)
                );
                case COMPRESSED_CLAY, RAW_MACHINE_HULL, MACHINE_HULL -> BLOCKS.registerSimpleBlock(
                        spec.id(),
                        properties -> blockProperties(spec)
                );
            };
            blocks.put(spec.id(), block);
        }
        return Collections.unmodifiableMap(blocks);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Block> DeferredBlock<T> block(String id) {
        return (DeferredBlock<T>) catalogBlock(id);
    }

    private static BlockBehaviour.Properties blockProperties(ClayiumContentCatalog.BlockSpec spec) {
        return switch (spec.kind()) {
            case COMPRESSED_CLAY -> withHarvestTool(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY)
                            .mapColor(MapColor.COLOR_ORANGE)
                            .strength(1.0F, 1.0F)
                            .sound(SoundType.GRAVEL),
                    spec
            );
            case WORK_TABLE -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
                    .mapColor(MapColor.TERRACOTTA_BROWN)
                    .strength(2.0F, 2.0F)
                    .sound(SoundType.WOOD);
            case RAW_MACHINE_HULL -> withHarvestTool(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY)
                            .mapColor(MapColor.TERRACOTTA_BROWN)
                            .strength(1.0F, 1.0F)
                            .sound(SoundType.GRAVEL),
                    spec
            );
            case MACHINE_HULL -> withHarvestTool(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                            .mapColor(MapColor.TERRACOTTA_BROWN)
                            .strength(2.0F, 5.0F)
                            .sound(SoundType.STONE),
                    spec
            );
        };
    }

    private static BlockBehaviour.Properties withHarvestTool(BlockBehaviour.Properties properties, ClayiumContentCatalog.BlockSpec spec) {
        return switch (spec.harvestTool()) {
            case NONE -> properties;
            case SHOVEL, PICKAXE -> properties.requiresCorrectToolForDrops();
        };
    }
}
