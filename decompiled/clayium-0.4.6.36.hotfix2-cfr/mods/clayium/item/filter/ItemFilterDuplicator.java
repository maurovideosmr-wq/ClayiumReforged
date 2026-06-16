/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item.filter;

import mods.clayium.item.filter.ItemFilterTemp;
import net.minecraft.item.ItemStack;

public class ItemFilterDuplicator
extends ItemFilterTemp {
    @Override
    public boolean isCopy(ItemStack filter) {
        return true;
    }

    @Override
    public ItemStack clearCopyFlag(ItemStack filter) {
        return filter;
    }

    @Override
    public ItemStack setCopyFlag(ItemStack filter) {
        return filter;
    }
}

