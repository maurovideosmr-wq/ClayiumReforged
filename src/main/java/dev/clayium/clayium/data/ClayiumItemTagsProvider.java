package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayPartType;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import dev.clayium.clayium.registry.ClayiumItems;
import dev.clayium.clayium.registry.ClayiumTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

public final class ClayiumItemTagsProvider extends ItemTagsProvider {
    public ClayiumItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Clayium.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        for (ClayiumContentCatalog.MaterialFormSpec spec : ClayiumContentCatalog.registeredMaterialItems()) {
            Item item = ClayiumItems.PARTS.get(spec.material(), spec.partType()).get();
            if (spec.partType() == ClayPartType.DUST) {
                this.tag(common("dusts")).add(item);
                this.tag(common("dusts/" + spec.material().idPrefix())).add(item);
            } else if (spec.partType() == ClayPartType.PLATE) {
                this.tag(common("plates")).add(item);
                this.tag(common("plates/" + spec.material().idPrefix())).add(item);
            } else if (spec.partType() == ClayPartType.LARGE_PLATE) {
                this.tag(common("large_plates")).add(item);
                this.tag(common("large_plates/" + spec.material().idPrefix())).add(item);
            }
        }

        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            if (spec.kind() == ClayiumContentCatalog.BlockKind.COMPRESSED_CLAY) {
                Item item = ClayiumItems.blockItem(spec.id()).get();
                this.tag(common("storage_blocks")).add(item);
                this.tag(common("storage_blocks/" + spec.id())).add(item);
            }
        }

        for (ClayiumContentCatalog.SimpleItemSpec spec : ClayiumContentCatalog.simpleItems()) {
            Item item = ClayiumItems.simpleItem(spec.id()).get();
            if (spec.kind().isClayTool()) {
                this.tag(ClayiumTags.Items.CLAY_TOOLS).add(item);
            }
            switch (spec.kind()) {
                case GENERIC -> {
                }
                case RAW_CLAY_TOOL -> {
                    this.tag(ClayiumTags.Items.RAW_CLAY_TOOLS).add(item);
                    this.tag(ClayiumTags.Items.CLAY_MACHINE_TOOLS).add(item);
                }
                case WORK_TABLE_TOOL -> {
                    this.tag(ClayiumTags.Items.CLAY_WORK_TABLE_TOOLS).add(item);
                    this.tag(ClayiumTags.Items.CLAY_MACHINE_TOOLS).add(item);
                    this.tagWorkTableShapeTool(spec.id(), item);
                }
                case CLAY_SHOVEL -> {
                    this.tag(ClayiumTags.Items.CLAY_MINING_TOOLS).add(item);
                    this.tag(ItemTags.SHOVELS).add(item);
                    this.tag(ItemTags.MINING_ENCHANTABLE).add(item);
                    this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(item);
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                }
                case CLAY_PICKAXE -> {
                    this.tag(ClayiumTags.Items.CLAY_MINING_TOOLS).add(item);
                    this.tag(ItemTags.PICKAXES).add(item);
                    this.tag(ItemTags.MINING_ENCHANTABLE).add(item);
                    this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(item);
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                }
                case CLAY_WRENCH -> {
                    this.tag(ClayiumTags.Items.CLAY_MACHINE_TOOLS).add(item);
                    this.tag(ClayiumTags.Items.CLAY_WRENCHES).add(item);
                }
            }
        }

        this.tag(ClayiumTags.Items.CLAY_TOOL_REPAIR_MATERIALS)
                .add(ClayiumItems.CLAY_PLATE.get())
                .add(ClayiumItems.DENSE_CLAY_PLATE.get());
    }

    private static TagKey<Item> common(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }

    private void tagWorkTableShapeTool(String id, Item item) {
        if (id.endsWith("_rolling_pin")) {
            this.tag(ClayiumTags.Items.CLAY_ROLLING_PINS).add(item);
        } else if (id.endsWith("_slicer")) {
            this.tag(ClayiumTags.Items.CLAY_SLICERS).add(item);
        } else if (id.endsWith("_spatula")) {
            this.tag(ClayiumTags.Items.CLAY_SPATULAS).add(item);
        }
    }
}
