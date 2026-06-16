/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.pan;

import mods.clayium.util.crafting.IItemPattern;
import net.minecraft.item.ItemStack;

public interface IPANConversion {
    public IItemPattern[] getPatterns();

    public ItemStack[] getResults();

    public double getEnergy();
}

