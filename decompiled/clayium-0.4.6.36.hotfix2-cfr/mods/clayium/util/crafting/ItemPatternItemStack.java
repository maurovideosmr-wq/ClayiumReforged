/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.util.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.item.ItemStack;

public class ItemPatternItemStack
implements IItemPattern {
    private List<ItemStack> items;

    public ItemPatternItemStack(List<ItemStack> itemstacks) {
        this.items = itemstacks;
    }

    public ItemPatternItemStack(ItemStack itemstack) {
        this.items = itemstack != null ? new ArrayList<ItemStack>(Arrays.asList(itemstack.func_77946_l())) : new ArrayList<ItemStack>(Arrays.asList(new ItemStack[]{null}));
    }

    public String toString() {
        String ret = "";
        if (this.items == null) {
            ret = "null";
        } else {
            for (int i = 0; i < this.items.size(); ++i) {
                ret = ret + (this.items.get(i) == null ? null : this.items.get(i).toString());
                if (i >= this.items.size() - 1) continue;
                ret = ret + ",";
            }
        }
        return "ItemPatternItemStack<" + ret + ">";
    }

    @Override
    public boolean match(ItemStack itemstack, boolean checkStackSize) {
        for (ItemStack item : this.items) {
            if (!this.canBeCrafted(itemstack, item, checkStackSize)) continue;
            return true;
        }
        return false;
    }

    public boolean canBeCrafted(ItemStack ingred, ItemStack recipe, boolean checkStackSize) {
        return Recipes.canBeCrafted(ingred, recipe, checkStackSize);
    }

    @Override
    public boolean hasIntersection(IItemPattern pattern, boolean checkStackSize) {
        if (pattern == null) {
            return false;
        }
        for (ItemStack item : this.items) {
            ItemStack[] patternitems;
            if (pattern.match(item, checkStackSize)) {
                return true;
            }
            if (item == null || item.func_77960_j() != Short.MAX_VALUE) continue;
            for (ItemStack patternitem : patternitems = pattern.toItemStacks()) {
                if (!UtilItemStack.areItemEqual(patternitem, item)) continue;
                return true;
            }
        }
        return false;
    }

    public List<ItemStack> getPatternItems() {
        return this.items;
    }

    @Override
    public int getStackSize(ItemStack itemstack) {
        for (ItemStack item : this.items) {
            if (item == null && itemstack == null) {
                return 1;
            }
            if (!(item != null && item.func_77960_j() == Short.MAX_VALUE ? UtilItemStack.areItemEqual(itemstack, item) : UtilItemStack.areItemDamageEqual(itemstack, item))) continue;
            return Math.max(item.field_77994_a, 1);
        }
        return 1;
    }

    @Override
    public ItemStack[] toItemStacks() {
        return this.items.toArray(new ItemStack[0]);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + UtilItemStack.getItemStackHashCode(this.items);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ItemPatternItemStack other = (ItemPatternItemStack)obj;
        return !(this.items == null ? other.items != null : !UtilItemStack.areStacksEqual(this.items, other.items));
    }

    @Override
    public ItemStack isSimple() {
        if (this.items.size() != 1) {
            return null;
        }
        ItemStack ret = this.items.get(0);
        return ret != null && ret.func_77960_j() == Short.MAX_VALUE ? null : ret;
    }

    @Override
    public boolean isAvailable() {
        return this.items != null && this.items.size() >= 1;
    }
}

