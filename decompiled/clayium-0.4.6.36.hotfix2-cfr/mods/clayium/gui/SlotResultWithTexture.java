/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui;

import mods.clayium.gui.ITexture;
import mods.clayium.gui.SlotWithTexture;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotResultWithTexture
extends SlotWithTexture {
    public SlotResultWithTexture(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    public SlotResultWithTexture(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ITexture texture) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, texture);
    }

    @Override
    public boolean func_75214_a(ItemStack p_75214_1_) {
        return false;
    }
}

