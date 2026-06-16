/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item;

import mods.clayium.item.ItemTiered;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingTools
extends ItemTiered {
    private ItemStack brokenItem;

    public ItemCraftingTools(ItemStack itemstack) {
        this.brokenItem = itemstack.func_77946_l();
        this.func_77625_d(1);
        this.setNoRepair();
    }

    public ItemStack getContainerItem(ItemStack itemstack) {
        if (itemstack.func_77960_j() >= this.func_77612_l()) {
            return this.brokenItem.func_77946_l();
        }
        return new ItemStack((Item)this, 1, itemstack.func_77960_j() + 1);
    }

    public boolean hasContainerItem(ItemStack itemstack) {
        return this.getContainerItem(itemstack) != null;
    }

    public boolean func_77630_h(ItemStack itemstack) {
        return itemstack.func_77960_j() >= this.func_77612_l();
    }
}

