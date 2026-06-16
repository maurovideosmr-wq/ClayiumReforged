/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.oredict.OreDictionary
 */
package mods.clayium.util.crafting;

import java.util.ArrayList;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.IItemPattern;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemPatternOreDictionary
implements IItemPattern {
    private String oreDictionary;
    private int id;
    private int stackSize;

    public ItemPatternOreDictionary(String odName, int stackSize) {
        this.oreDictionary = odName;
        this.id = OreDictionary.getOreID((String)odName);
        this.stackSize = stackSize;
    }

    public ItemPatternOreDictionary(int odId, int stackSize) {
        this.id = odId;
        this.stackSize = stackSize;
    }

    public String toString() {
        String ret = this.oreDictionary == null ? "null" : this.oreDictionary;
        return "ItemPatternItemStack<" + ret + ":" + this.stackSize + ">";
    }

    @Override
    public boolean match(ItemStack itemstack, boolean checkStackSize) {
        return itemstack != null && (!checkStackSize || itemstack.field_77994_a >= this.stackSize) && UtilItemStack.hasOreName(itemstack, this.id);
    }

    @Override
    public boolean hasIntersection(IItemPattern pattern, boolean checkStackSize) {
        if (pattern == null) {
            return false;
        }
        for (ItemStack item : this.toItemStacks()) {
            if (!pattern.match(item, checkStackSize)) continue;
            return true;
        }
        return false;
    }

    public String getPatternString() {
        if (this.oreDictionary == null) {
            this.oreDictionary = OreDictionary.getOreName((int)this.id);
        }
        return this.oreDictionary;
    }

    @Override
    public int getStackSize(ItemStack itemstack) {
        return this.stackSize;
    }

    @Override
    public ItemStack[] toItemStacks() {
        ItemStack[] stacks = OreDictionary.getOres((Integer)this.id).toArray(new ItemStack[0]);
        ItemStack[] stacks1 = new ItemStack[stacks.length];
        for (int i = 0; i < stacks.length; ++i) {
            stacks1[i] = stacks[i].func_77946_l();
            stacks1[i].field_77994_a = this.stackSize;
        }
        return stacks1;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.id;
        result = 31 * result + this.stackSize;
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
        ItemPatternOreDictionary other = (ItemPatternOreDictionary)obj;
        if (this.id != other.id) {
            return false;
        }
        return this.stackSize == other.stackSize;
    }

    @Override
    public ItemStack isSimple() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        ArrayList list = OreDictionary.getOres((Integer)this.id);
        return list != null && list.size() != 0;
    }
}

