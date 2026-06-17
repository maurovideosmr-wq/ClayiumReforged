package dev.clayium.clayium.data;

import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class ClayiumLootTableProvider extends LootTableProvider {
    public ClayiumLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ClayiumBlockLootProvider::new, LootContextParamSets.BLOCK)),
                registries
        );
    }

    private static final class ClayiumBlockLootProvider extends BlockLootSubProvider {
        private ClayiumBlockLootProvider(HolderLookup.Provider registries) {
            super(Set.<Item>of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
                this.dropSelf(ClayiumBlocks.catalogBlock(spec.id()).get());
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ClayiumContentCatalog.blocks().stream()
                    .map(spec -> (Block) ClayiumBlocks.catalogBlock(spec.id()).get())
                    .toList();
        }
    }
}
