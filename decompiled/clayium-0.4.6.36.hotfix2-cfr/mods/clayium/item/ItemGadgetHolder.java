/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.IItemGadget;
import mods.clayium.item.ItemTiered;
import mods.clayium.util.UtilItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGadgetHolder
extends ItemTiered {
    protected static List<IItemGadget> gadgetList = new ArrayList<IItemGadget>();
    protected static Map<Entity, Map<IItemGadget, List<ItemStack>>> gadgetMapRemote = new HashMap<Entity, Map<IItemGadget, List<ItemStack>>>();
    protected static Map<Entity, Map<IItemGadget, List<ItemStack>>> gadgetMapServer = new HashMap<Entity, Map<IItemGadget, List<ItemStack>>>();
    protected static final String tagName = "Items";

    public static boolean addGadget(IItemGadget gadget) {
        return gadgetList.add(gadget);
    }

    public static List<IItemGadget> getGadgetList() {
        return gadgetList;
    }

    protected static Map<Entity, Map<IItemGadget, List<ItemStack>>> getGadgetMap(boolean theWorldIsRemote) {
        return theWorldIsRemote ? gadgetMapRemote : gadgetMapServer;
    }

    protected static Map<IItemGadget, List<ItemStack>> getGadgetMap(Entity entity, boolean isRemote) {
        Map<Entity, Map<IItemGadget, List<ItemStack>>> gadgetMap = ItemGadgetHolder.getGadgetMap(isRemote);
        if (!gadgetMap.containsKey(entity)) {
            gadgetMap.put(entity, ItemGadgetHolder.initGadgetList(null));
        }
        return gadgetMap.get(entity);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        p_77659_3_.openGui((Object)ClayiumCore.INSTANCE, 23, p_77659_2_, (int)p_77659_3_.field_70165_t, (int)p_77659_3_.field_70163_u, (int)p_77659_3_.field_70161_v);
        return super.func_77659_a(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    public void func_77663_a(ItemStack itemStack, World world, Entity entity, int slot, boolean isCurrentItem) {
        if (world == null) {
            return;
        }
        ItemStack[] items = UtilItemStack.getItemsFromTag(tagName, itemStack);
        if (items == null) {
            return;
        }
        Map<IItemGadget, List<ItemStack>> gadgetMap = ItemGadgetHolder.getGadgetMap(entity, world.field_72995_K);
        if (gadgetMap == null) {
            return;
        }
        for (IItemGadget gadget : ItemGadgetHolder.getGadgetList()) {
            List<ItemStack> list = gadgetMap.get(gadget);
            for (ItemStack item : items) {
                if (!gadget.match(item, world, entity, slot, isCurrentItem)) continue;
                list.add(item);
            }
        }
        for (ItemStack item : items) {
            if (item == null || item.func_77973_b() == null || item.func_77973_b() instanceof ItemGadgetHolder) continue;
            item.func_77973_b().func_77663_a(item, world, entity, slot, isCurrentItem);
        }
    }

    public static void clearGadgetList(Entity entity, boolean isRemote) {
        Map<IItemGadget, List<ItemStack>> gadgetMap = ItemGadgetHolder.getGadgetMap(entity, isRemote);
        if (gadgetMap == null) {
            return;
        }
        for (List<ItemStack> list : gadgetMap.values()) {
            list.clear();
        }
    }

    public static void updateGadget(Entity entity, boolean isRemote) {
        Map<IItemGadget, List<ItemStack>> gadgetMap = ItemGadgetHolder.getGadgetMap(entity, isRemote);
        if (gadgetMap == null) {
            return;
        }
        for (IItemGadget gadget : ItemGadgetHolder.getGadgetList()) {
            List<ItemStack> list = gadgetMap.get(gadget);
            gadget.update(list, entity, isRemote);
        }
    }

    public static Map<IItemGadget, List<ItemStack>> initGadgetList(Map<IItemGadget, List<ItemStack>> gadgetMap) {
        if (gadgetMap == null) {
            gadgetMap = new HashMap<IItemGadget, List<ItemStack>>();
        }
        for (IItemGadget gadget : gadgetList) {
            gadgetMap.put(gadget, new ArrayList());
        }
        return gadgetMap;
    }
}

