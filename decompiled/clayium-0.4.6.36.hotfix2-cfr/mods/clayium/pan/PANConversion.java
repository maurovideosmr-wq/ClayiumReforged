/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.pan;

import mods.clayium.pan.IPANConversion;
import mods.clayium.util.crafting.IItemPattern;
import net.minecraft.item.ItemStack;

public class PANConversion
implements IPANConversion {
    private IItemPattern[] patterns;
    private ItemStack[] results;
    private double energy;

    public PANConversion(IItemPattern[] patterns, ItemStack[] results, double energy) {
        this.patterns = patterns;
        this.results = results;
        this.energy = energy;
    }

    @Override
    public IItemPattern[] getPatterns() {
        return this.patterns;
    }

    @Override
    public ItemStack[] getResults() {
        return this.results;
    }

    @Override
    public double getEnergy() {
        return this.energy;
    }
}

