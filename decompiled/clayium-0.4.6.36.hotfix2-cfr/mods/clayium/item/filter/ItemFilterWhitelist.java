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
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.filter.IItemFilter;
import mods.clayium.item.filter.IItemWithFilterSize;
import mods.clayium.item.filter.ItemFilterTemp;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFilterWhitelist
extends ItemFilterTemp {
    private boolean fuzzy = false;

    public ItemFilterWhitelist() {
        this(false);
    }

    public ItemFilterWhitelist(boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack) {
        if (filterTag == null) {
            return false;
        }
        ItemStack[] filters = UtilItemStack.tagList2Items(filterTag.func_150295_c("Items", 10));
        if (filters == null) {
            return false;
        }
        for (ItemStack filter : filters) {
            if ((!ItemFilterTemp.isFilter(filter) || !ItemFilterTemp.match(filter, itemstack)) && !ItemFilterTemp.matchBetweenItemstacks(filter, itemstack, this.fuzzy)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, World world, int x, int y, int z) {
        if (filterTag == null) {
            return false;
        }
        ItemStack[] filters = UtilItemStack.tagList2Items(filterTag.func_150295_c("Items", 10));
        if (filters == null) {
            return false;
        }
        for (ItemStack filter : filters) {
            if ((!ItemFilterTemp.isFilter(filter) || !ItemFilterTemp.match(filter, world, x, y, z)) && !ItemFilterTemp.matchBetweenItemstacks(filter, UtilBuilder.getItemBlock(world, x, y, z), this.fuzzy)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void openGui(ItemStack itemstack, World world, EntityPlayer player) {
        player.openGui((Object)ClayiumCore.INSTANCE, 20, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
    }

    @Override
    public void addTooltip(NBTTagCompound filterTag, List list, int indent) {
        if (list.size() >= 100) {
            return;
        }
        if (filterTag != null) {
            ItemStack[] filters;
            String in = "";
            for (int i = 0; i < indent; ++i) {
                in = in + " ";
            }
            for (ItemStack filter : filters = UtilItemStack.tagList2Items(filterTag.func_150295_c("Items", 10))) {
                if (filter == null) continue;
                list.add(in + filter.func_82833_r());
                if (!ItemFilterTemp.isFilter(filter)) continue;
                ((IItemFilter)filter.func_77973_b()).addTooltip(filter.field_77990_d, list, indent + 1);
            }
        }
    }

    @Override
    public int getFilterSize(NBTTagCompound filterTag) {
        int res = 0;
        if (filterTag != null) {
            ItemStack[] filters;
            for (ItemStack filter : filters = UtilItemStack.tagList2Items(filterTag.func_150295_c("Items", 10))) {
                if (filter == null) continue;
                ++res;
                if (!(filter.func_77973_b() instanceof IItemWithFilterSize)) continue;
                res += ((IItemWithFilterSize)filter.func_77973_b()).getFilterSize(filter.func_77978_p());
            }
        }
        return res;
    }
}

