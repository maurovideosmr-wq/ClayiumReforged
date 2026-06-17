package dev.clayium.clayium.item;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public final class ClayCraftingToolItem extends Item implements ClayiumTiered {
    private final int brokenClayBallCount;
    private final int tier;

    public ClayCraftingToolItem(Properties properties, int brokenClayBallCount, int tier) {
        super(properties);
        if (brokenClayBallCount <= 0) {
            throw new IllegalArgumentException("Broken clay crafting tools must return at least one clay ball");
        }
        this.brokenClayBallCount = brokenClayBallCount;
        this.tier = tier;
    }

    public ItemStack getAfterWorkTableUse(ItemStack stack) {
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            return new ItemStack(Items.CLAY_BALL, this.brokenClayBallCount);
        }
        ItemStack damaged = stack.copyWithCount(1);
        damaged.setDamageValue(stack.getDamageValue() + 1);
        return damaged;
    }

    public int brokenClayBallCount() {
        return this.brokenClayBallCount;
    }

    @Override
    public int clayiumTier() {
        return this.tier;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        ClayiumTooltips.appendTier(builder, this.clayiumTier());
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
    }
}
