package dev.clayium.clayium.item;

import dev.clayium.clayium.registry.ClayiumTags;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockState;

public final class ClayShovelItem extends ShovelItem {
    private static final float CLAY_BLOCK_SPEED = 32.0F;
    private static final float CLAY_ORE_SPEED = 12.0F;

    public ClayShovelItem(Item.Properties properties) {
        super(ClayiumToolMaterials.CLAY_SHOVEL, 1.5F, -3.0F, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(ClayiumTags.Blocks.CLAY_SHOVEL_FAST)) {
            return CLAY_BLOCK_SPEED;
        }
        if (state.is(ClayiumTags.Blocks.CLAY_ORES)) {
            return CLAY_ORE_SPEED;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("item.clayium.clay_shovel.tooltip"));
    }
}
