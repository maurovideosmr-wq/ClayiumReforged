/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.item.gadget;

import java.util.Arrays;
import java.util.List;
import mods.clayium.item.CItems;
import mods.clayium.item.IItemGadget;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GadgetOrdinal
implements IItemGadget {
    public List<String> itemNames;

    public GadgetOrdinal(String ... itemNames) {
        this.itemNames = Arrays.asList(itemNames);
    }

    @Override
    public boolean match(ItemStack itemStack, World world, Entity entity, int slot, boolean isCurrentItem) {
        if (itemStack != null && itemStack.func_77973_b() == CItems.itemGadget) {
            return this.itemNames.contains(CItems.itemGadget.getItemName(itemStack));
        }
        return false;
    }

    @Override
    public void update(List<ItemStack> list, Entity entity, boolean isRemote) {
        int i = -1;
        for (ItemStack item : list) {
            if (item == null || item.func_77973_b() != CItems.itemGadget) continue;
            i = Math.max(this.itemNames.indexOf(CItems.itemGadget.getItemName(item)), i);
        }
        this.update(i, entity, isRemote);
    }

    public abstract void update(int var1, Entity var2, boolean var3);
}

