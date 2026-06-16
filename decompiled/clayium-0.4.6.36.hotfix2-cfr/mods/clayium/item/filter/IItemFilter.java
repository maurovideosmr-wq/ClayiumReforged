/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.item.filter;

import java.util.List;
import mods.clayium.item.filter.IFilterSizeChecker;
import mods.clayium.item.filter.IItemWithFilterSize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IItemFilter
extends IFilterSizeChecker,
IItemWithFilterSize {
    public void addFilterInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4);

    public void addTooltip(NBTTagCompound var1, List var2, int var3);

    public boolean filterMatch(NBTTagCompound var1, ItemStack var2);

    public boolean filterMatch(NBTTagCompound var1, World var2, int var3, int var4, int var5);

    public boolean isCopy(ItemStack var1);

    public ItemStack setCopyFlag(ItemStack var1);

    public ItemStack clearCopyFlag(ItemStack var1);
}

