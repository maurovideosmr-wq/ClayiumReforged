/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.util.crafting;

import net.minecraft.item.ItemStack;

public interface IItemPattern {
    public boolean match(ItemStack var1, boolean var2);

    public boolean hasIntersection(IItemPattern var1, boolean var2);

    public int getStackSize(ItemStack var1);

    public ItemStack[] toItemStacks();

    public ItemStack isSimple();

    public boolean isAvailable();
}

