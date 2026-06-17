package dev.clayium.clayium.item;

import dev.clayium.clayium.registry.ClayiumTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

final class ClayiumToolMaterials {
    static final int LEGACY_DURABILITY = 500;
    static final ToolMaterial CLAY_SHOVEL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            LEGACY_DURABILITY,
            2.0F,
            0.0F,
            15,
            ClayiumTags.Items.CLAY_TOOL_REPAIR_MATERIALS
    );
    static final ToolMaterial CLAY_PICKAXE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            LEGACY_DURABILITY,
            4.0F,
            1.0F,
            5,
            ClayiumTags.Items.CLAY_TOOL_REPAIR_MATERIALS
    );

    private ClayiumToolMaterials() {
    }
}
