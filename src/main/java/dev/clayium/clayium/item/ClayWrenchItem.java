package dev.clayium.clayium.item;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public final class ClayWrenchItem extends Item implements ClayiumTiered {
    private final int tier;

    public ClayWrenchItem(Item.Properties properties, int tier) {
        super(properties.stacksTo(1));
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        ClayiumTooltips.appendTier(builder, this.clayiumTier());
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.clayium.clay_wrench.tooltip"));
    }

    @Override
    public int clayiumTier() {
        return this.tier;
    }
}
