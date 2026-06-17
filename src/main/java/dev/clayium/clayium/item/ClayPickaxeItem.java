package dev.clayium.clayium.item;

import dev.clayium.clayium.registry.ClayiumTags;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockState;

public final class ClayPickaxeItem extends Item implements ClayiumTiered {
    private static final float CLAY_ORE_SPEED = 32.0F;
    private final int tier;

    public ClayPickaxeItem(Item.Properties properties, int tier) {
        super(properties.pickaxe(ClayiumToolMaterials.CLAY_PICKAXE, 1.0F, -2.8F));
        this.tier = tier;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(ClayiumTags.Blocks.CLAY_ORES)) {
            return CLAY_ORE_SPEED;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        ClayiumTooltips.appendTier(builder, this.clayiumTier());
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.clayium.clay_pickaxe.tooltip"));
    }

    @Override
    public int clayiumTier() {
        return this.tier;
    }
}
