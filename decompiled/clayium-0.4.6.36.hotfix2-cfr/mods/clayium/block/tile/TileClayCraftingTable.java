/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileClayContainerTiered;
import net.minecraft.item.ItemStack;

public class TileClayCraftingTable
extends TileClayContainerTiered {
    @Override
    protected void initParams() {
        this.containerItemStacks = new ItemStack[9];
        this.slotsDrop = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }
}

