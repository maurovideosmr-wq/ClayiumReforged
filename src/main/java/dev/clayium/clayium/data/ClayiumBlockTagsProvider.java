package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import dev.clayium.clayium.registry.ClayiumTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public final class ClayiumBlockTagsProvider extends BlockTagsProvider {
    public ClayiumBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Clayium.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            Block block = ClayiumBlocks.catalogBlock(spec.id()).get();
            switch (spec.harvestTool()) {
                case NONE -> {
                }
                case SHOVEL -> this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block);
                case PICKAXE -> this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            }
            if (spec.kind() == ClayiumContentCatalog.BlockKind.COMPRESSED_CLAY) {
                this.tag(common("storage_blocks")).add(block);
                this.tag(common("storage_blocks/" + spec.id())).add(block);
            }
            if (spec.harvestTool() == ClayiumContentCatalog.HarvestTool.SHOVEL) {
                this.tag(ClayiumTags.Blocks.CLAY_SHOVEL_FAST).add(block);
            }
        }
        this.tag(ClayiumTags.Blocks.CLAY_SHOVEL_FAST).add(Blocks.CLAY);
    }

    private static TagKey<Block> common(String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", path));
    }
}
