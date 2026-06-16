/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mods.clayium.item.ItemFilterTemp
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item;

import mods.clayium.item.ItemFilterTemp;
import net.minecraft.item.ItemStack;

public class ItemFilterDuplicator
extends ItemFilterTemp {
    public boolean isCopy(ItemStack filter) {
        return true;
    }

    public ItemStack clearCopyFlag(ItemStack filter) {
        return filter;
    }

    public ItemStack setCopyFlag(ItemStack filter) {
        return filter;
    }
}

