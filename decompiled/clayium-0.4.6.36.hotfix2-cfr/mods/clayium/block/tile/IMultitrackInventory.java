/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface IMultitrackInventory
extends ISidedInventory {
    public int[] getAccessibleSlotsFromSide(int var1, int var2);

    public boolean canInsertItem(int var1, ItemStack var2, int var3, int var4);

    public boolean canExtractItem(int var1, ItemStack var2, int var3, int var4);
}

