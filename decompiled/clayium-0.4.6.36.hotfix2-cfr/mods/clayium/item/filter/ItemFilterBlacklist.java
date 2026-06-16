/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.item.filter;

import mods.clayium.item.filter.ItemFilterWhitelist;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFilterBlacklist
extends ItemFilterWhitelist {
    @Override
    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack) {
        return !super.filterMatch(filterTag, itemstack);
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, World world, int x, int y, int z) {
        return !super.filterMatch(filterTag, world, x, y, z);
    }
}

