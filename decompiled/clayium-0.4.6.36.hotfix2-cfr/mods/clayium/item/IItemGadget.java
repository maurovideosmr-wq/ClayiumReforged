/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemGadget {
    public boolean match(ItemStack var1, World var2, Entity var3, int var4, boolean var5);

    public void update(List<ItemStack> var1, Entity var2, boolean var3);
}

