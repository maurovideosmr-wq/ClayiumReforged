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

import mods.clayium.core.ClayiumCore;
import mods.clayium.item.CItems;
import mods.clayium.item.ItemDamaged;
import mods.clayium.item.filter.IFilterSizeChecker;
import mods.clayium.item.filter.IItemWithFilterSize;
import mods.clayium.item.filter.ItemFilterWhitelist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGadget
extends ItemDamaged
implements IFilterSizeChecker,
IItemWithFilterSize {
    public static ItemFilterWhitelist filterForAutoEat = (ItemFilterWhitelist)CItems.itemFilterWhitelist;

    @Override
    public int getFilterSize(NBTTagCompound filterTag) {
        return filterForAutoEat.getFilterSize(filterTag);
    }

    @Override
    public void checkFilterSize(ItemStack filter, EntityPlayer player, World world) {
        filterForAutoEat.checkFilterSize(filter, player, world);
    }

    public class ItemCallbackItemFilterGui
    extends ItemDamaged.ItemCallbackDefault {
        @Override
        public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
            player.openGui((Object)ClayiumCore.INSTANCE, 20, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
            return itemstack;
        }
    }
}

