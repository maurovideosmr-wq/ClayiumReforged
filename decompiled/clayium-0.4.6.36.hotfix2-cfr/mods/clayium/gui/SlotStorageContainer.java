/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui;

import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.gui.ITexture;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.util.UtilItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotStorageContainer
extends SlotWithTexture {
    public SlotStorageContainer(TileStorageContainer p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ITexture texture) {
        super((IInventory)p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, texture);
    }

    @Override
    public boolean func_75214_a(ItemStack itemstack) {
        ItemStack inv = ((TileStorageContainer)this.field_75224_c).containerItemStacks[0];
        ItemStack inv1 = ((TileStorageContainer)this.field_75224_c).containerItemStacks[1];
        return !(inv != null && !UtilItemStack.areTypeEqual(itemstack, inv) || inv1 != null && !((TileStorageContainer)this.field_75224_c).checkFilterSlot(itemstack, inv1));
    }
}

