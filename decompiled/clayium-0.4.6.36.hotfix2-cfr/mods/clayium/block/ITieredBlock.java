/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public interface ITieredBlock {
    public int getTier(ItemStack var1);

    public int getTier(IBlockAccess var1, int var2, int var3, int var4);
}

