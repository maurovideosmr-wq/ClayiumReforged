/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.item.filter.ItemFilterTemp;
import mods.clayium.util.UtilItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileVacuumContainer
extends TileStorageContainer {
    @Override
    public int getItemUseMode(ItemStack itemStack, EntityPlayer player) {
        int m = super.getItemUseMode(itemStack, player);
        return m == 99 ? -1 : m;
    }

    @Override
    public ItemStack func_70301_a(int slot) {
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            return super.func_70301_a(slot);
        }
        return null;
    }

    @Override
    public ItemStack func_70298_a(int slot, int size) {
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            return super.func_70298_a(slot, size);
        }
        return null;
    }

    @Override
    public void func_70299_a(int slot, ItemStack itemstack) {
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            super.func_70299_a(slot, itemstack);
            return;
        }
    }

    @Override
    public boolean checkFilterSlot(ItemStack itemstack, ItemStack filter) {
        return UtilItemStack.areTypeEqual(itemstack, filter) || ItemFilterTemp.match(filter, itemstack);
    }
}

