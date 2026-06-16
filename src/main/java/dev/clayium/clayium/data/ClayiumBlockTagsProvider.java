package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayiumBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public final class ClayiumBlockTagsProvider extends BlockTagsProvider {
    public ClayiumBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Clayium.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ClayiumBlocks.DENSE_CLAY.get())
                .add(ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ClayiumBlocks.CLAY_MACHINE_HULL.get());
    }
}
