/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface ISpecialFilter {
    public boolean filterMatch(NBTTagCompound var1, ItemStack var2, boolean var3);

    public boolean filterMatch(NBTTagCompound var1, World var2, int var3, int var4, int var5, boolean var6);

    public void addTooltip(NBTTagCompound var1, List var2, int var3);

    public void openGui(ItemStack var1, World var2, EntityPlayer var3);
}

