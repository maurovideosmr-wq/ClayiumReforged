/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.common.registry.GameRegistry$UniqueIdentifier
 *  net.minecraft.block.Block
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraftforge.oredict.OreDictionary
 */
package mods.clayium.util;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.OreDictionaryStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

public class UtilItemStack {
    public static boolean areItemDamageEqual(ItemStack itemstack1, ItemStack itemstack2) {
        return UtilItemStack.areItemEqual(itemstack1, itemstack2) && UtilItemStack.areDamageEqual(itemstack1, itemstack2);
    }

    public static boolean areItemDamageEqualOrDamageable(ItemStack itemstack1, ItemStack itemstack2) {
        return UtilItemStack.areItemEqual(itemstack1, itemstack2) && (UtilItemStack.areDamageEqual(itemstack1, itemstack2) || itemstack1.func_77973_b().func_77645_m());
    }

    public static boolean areTypeEqual(ItemStack itemstack1, ItemStack itemstack2) {
        return UtilItemStack.areItemDamageEqual(itemstack1, itemstack2) && UtilItemStack.areTagEqual(itemstack1, itemstack2);
    }

    public static boolean areStackEqual(ItemStack itemstack1, ItemStack itemstack2) {
        return UtilItemStack.areTypeEqual(itemstack1, itemstack2) && UtilItemStack.areSizeEqual(itemstack1, itemstack2);
    }

    public static boolean areItemEqual(ItemStack itemstack1, ItemStack itemstack2) {
        if (itemstack1 == null || itemstack2 == null) {
            return itemstack1 == null && itemstack2 == null;
        }
        return itemstack1.func_77973_b() == itemstack2.func_77973_b();
    }

    public static boolean areDamageEqual(ItemStack itemstack1, ItemStack itemstack2) {
        if (itemstack1 == null || itemstack2 == null) {
            return itemstack1 == null && itemstack2 == null;
        }
        return itemstack1.func_77960_j() == itemstack2.func_77960_j();
    }

    public static boolean areSizeEqual(ItemStack itemstack1, ItemStack itemstack2) {
        if (itemstack1 == null || itemstack2 == null) {
            return itemstack1 == null && itemstack2 == null;
        }
        return itemstack1.field_77994_a == itemstack2.field_77994_a;
    }

    public static boolean areTagEqual(ItemStack itemstack1, ItemStack itemstack2) {
        return ItemStack.func_77970_a((ItemStack)itemstack1, (ItemStack)itemstack2);
    }

    public static boolean haveSameOD(ItemStack itemstack1, ItemStack itemstack2) {
        int[] ids = OreDictionary.getOreIDs((ItemStack)itemstack1);
        int[] ids1 = OreDictionary.getOreIDs((ItemStack)itemstack2);
        for (int id : ids) {
            for (int id1 : ids1) {
                if (id1 != id) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean hasOreName(ItemStack itemstack, String orename) {
        for (int i : OreDictionary.getOreIDs((ItemStack)itemstack)) {
            if (!OreDictionary.getOreName((int)i).equals(orename)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasOreName(ItemStack itemstack, int oreid) {
        for (int i : OreDictionary.getOreIDs((ItemStack)itemstack)) {
            if (i != oreid) continue;
            return true;
        }
        return false;
    }

    public static String[] getOreNames(ItemStack itemstack) {
        int[] ids = OreDictionary.getOreIDs((ItemStack)itemstack);
        String[] res = new String[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            res[i] = OreDictionary.getOreName((int)ids[i]);
        }
        return res;
    }

    public static GameRegistry.UniqueIdentifier findUniqueIdentifierFor(Item item) {
        if (item == null) {
            return null;
        }
        if (item instanceof ItemBlock) {
            return GameRegistry.findUniqueIdentifierFor((Block)((ItemBlock)item).field_150939_a);
        }
        return GameRegistry.findUniqueIdentifierFor((Item)item);
    }

    public static int getItemStackHashCode(ItemStack item) {
        if (item == null || item.func_77973_b() == null) {
            return 0;
        }
        int prime = 31;
        int result = 1;
        result = 31 * result + UtilItemStack.findUniqueIdentifierFor(item.func_77973_b()).hashCode();
        result = 31 * result + item.func_77960_j();
        result = 31 * result + item.field_77994_a;
        result = 31 * result + (item.func_77978_p() == null ? 0 : item.func_77978_p().hashCode());
        return result;
    }

    public static int getItemStackHashCode(Iterable items) {
        if (items == null) {
            return 0;
        }
        int prime = 31;
        int result = 1;
        for (Object item : items) {
            result = 31 * result + (item instanceof ItemStack ? UtilItemStack.getItemStackHashCode((ItemStack)item) : (item != null ? item.hashCode() : 0));
        }
        return result;
    }

    public static int getItemStackHashCode(Object[] items) {
        return UtilItemStack.getItemStackHashCode(Arrays.asList(items));
    }

    public static boolean areStacksEqual(Object[] items1, Object[] items2) {
        if (items1 == null) {
            return items2 == null;
        }
        if (items2 == null) {
            return false;
        }
        if (items1.length != items2.length) {
            return false;
        }
        for (int i = 0; i < items1.length; ++i) {
            if (!(items1[i] == null && items2[i] != null || items1[i] instanceof ItemStack && items2[i] instanceof ItemStack && !UtilItemStack.areStackEqual((ItemStack)items1[i], (ItemStack)items2[i])) && items1[i].equals(items2[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean areStacksEqual(Collection items1, Collection items2) {
        return items1 == null ? items2 == null : (items2 == null ? false : UtilItemStack.areStacksEqual(items1.toArray(new Object[0]), items2.toArray(new Object[0])));
    }

    public static ItemStack[] object2ItemStacks(Object object) {
        if (object instanceof ItemStack) {
            return new ItemStack[]{(ItemStack)object};
        }
        if (object instanceof ItemStack[]) {
            return (ItemStack[])object;
        }
        if (object instanceof OreDictionaryStack) {
            OreDictionaryStack odstack = (OreDictionaryStack)object;
            ItemStack[] stacks = OreDictionary.getOres((String)odstack.getOreName()).toArray(new ItemStack[0]);
            ItemStack[] stacks1 = new ItemStack[stacks.length];
            for (int i = 0; i < stacks.length; ++i) {
                stacks1[i] = stacks[i].func_77946_l();
                stacks1[i].field_77994_a = odstack.stackSize;
            }
            return stacks1;
        }
        if (object instanceof String) {
            return UtilItemStack.object2ItemStacks(new OreDictionaryStack((String)object, 1));
        }
        if (object instanceof IItemPattern) {
            return ((IItemPattern)object).toItemStacks();
        }
        return null;
    }

    public static ItemStack[] getItemsFromTag(String name, ItemStack item) {
        return item == null || !item.func_77942_o() ? null : UtilItemStack.getItemsFromTag(name, item.func_77978_p());
    }

    public static ItemStack[] getItemsFromTag(String name, NBTTagCompound tag) {
        return tag == null ? null : UtilItemStack.tagList2Items(tag.func_150295_c(name, 10));
    }

    public static void setItemsToTag(String name, ItemStack[] items, NBTTagCompound tag) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        tag.func_74782_a(name, (NBTBase)UtilItemStack.items2TagList(items));
    }

    public static List<ItemStack> tagList2ItemList(NBTTagList tagList) {
        if (tagList == null) {
            return null;
        }
        ArrayList<ItemStack> res = new ArrayList<ItemStack>();
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            NBTTagCompound tagCompound1 = tagList.func_150305_b(i);
            short byte0 = tagCompound1.func_74765_d("Slot");
            while (res.size() <= byte0) {
                res.add(null);
            }
            res.set(byte0, ItemStack.func_77949_a((NBTTagCompound)tagCompound1));
        }
        return res;
    }

    public static ItemStack[] tagList2Items(NBTTagList tagList) {
        List<ItemStack> list = UtilItemStack.tagList2ItemList(tagList);
        return list == null ? null : list.toArray(new ItemStack[0]);
    }

    public static void tagList2Items(NBTTagList tagList, ItemStack[] itemstacks) {
        if (tagList == null || itemstacks == null) {
            return;
        }
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            NBTTagCompound tagCompound1 = tagList.func_150305_b(i);
            short byte0 = tagCompound1.func_74765_d("Slot");
            if (byte0 < 0 || byte0 >= itemstacks.length) continue;
            itemstacks[byte0] = ItemStack.func_77949_a((NBTTagCompound)tagCompound1);
        }
    }

    public static NBTTagList items2TagList(ItemStack[] items) {
        NBTTagList tagList = new NBTTagList();
        if (items != null) {
            for (int i = 0; i < items.length; ++i) {
                if (items[i] == null) continue;
                NBTTagCompound tagCompound1 = new NBTTagCompound();
                tagCompound1.func_74777_a("Slot", (short)i);
                items[i].func_77955_b(tagCompound1);
                tagList.func_74742_a((NBTBase)tagCompound1);
            }
        }
        return tagList;
    }
}

