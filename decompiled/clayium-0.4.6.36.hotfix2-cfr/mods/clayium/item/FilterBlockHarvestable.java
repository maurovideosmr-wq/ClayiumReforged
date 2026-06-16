/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.IGrowable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import java.util.List;
import mods.clayium.item.ISpecialFilter;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class FilterBlockHarvestable
implements ISpecialFilter {
    @Override
    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack, boolean lastResult) {
        return false;
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, World world, int x, int y, int z, boolean lastResult) {
        if (lastResult) {
            return true;
        }
        if (world == null) {
            return false;
        }
        Block block = world.func_147439_a(x, y, z);
        if (block instanceof IGrowable) {
            return !((IGrowable)block).func_149851_a(world, x, y, z, world.field_72995_K);
        }
        return false;
    }

    @Override
    public void addTooltip(NBTTagCompound filterTag, List list, int indent) {
    }

    @Override
    public void openGui(ItemStack itemstack, World world, EntityPlayer player) {
    }
}

