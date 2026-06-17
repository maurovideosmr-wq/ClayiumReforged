package dev.clayium.clayium.item;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

public final class ClayiumBlockItem extends BlockItem implements ClayiumTiered {
    private final int tier;

    public ClayiumBlockItem(Block block, Item.Properties properties, int tier) {
        super(block, properties);
        this.tier = tier;
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
