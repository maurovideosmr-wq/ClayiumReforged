package dev.clayium.clayium.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ClayCraftingToolItem extends Item {
    private final int brokenClayBallCount;

    public ClayCraftingToolItem(Properties properties, int brokenClayBallCount) {
        super(properties);
        if (brokenClayBallCount <= 0) {
            throw new IllegalArgumentException("Broken clay crafting tools must return at least one clay ball");
        }
        this.brokenClayBallCount = brokenClayBallCount;
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
}
