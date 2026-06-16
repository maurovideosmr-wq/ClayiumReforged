/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.item.gadget;

import java.util.Arrays;
import java.util.List;
import mods.clayium.item.CItems;
import mods.clayium.item.IItemGadget;
import mods.clayium.item.filter.ItemFilterTemp;
import mods.clayium.util.UtilItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GadgetAutoEat
implements IItemGadget {
    public List<String> itemNames;

    public GadgetAutoEat(String ... itemNames) {
        this.itemNames = Arrays.asList(itemNames);
    }

    public GadgetAutoEat() {
        this("AutoEat0", "AutoEat1");
    }

    @Override
    public void update(List<ItemStack> list, Entity entity, boolean isRemote) {
        for (ItemStack item : list) {
            if (item == null || item.func_77973_b() != CItems.itemGadget) continue;
            this.update(this.itemNames.indexOf(CItems.itemGadget.getItemName(item)), item, entity, isRemote);
        }
    }

    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack) {
        if (filterTag == null) {
            return true;
        }
        ItemStack[] filters = UtilItemStack.tagList2Items(filterTag.func_150295_c("Items", 10));
        if (filters == null) {
            return true;
        }
        boolean ret = true;
        for (ItemStack filter : filters) {
            ret &= filter == null;
            if ((!ItemFilterTemp.isFilter(filter) || !ItemFilterTemp.match(filter, itemstack)) && !ItemFilterTemp.matchBetweenItemstacks(filter, itemstack, false)) continue;
            return true;
        }
        return ret;
    }

    public void update(int itemIndex, ItemStack item, Entity entity, boolean isRemote) {
        if (itemIndex >= 0 && entity instanceof EntityPlayer && !isRemote && ((EntityPlayer)entity).func_71024_bL().func_75121_c()) {
            EntityPlayer player = (EntityPlayer)entity;
            ItemStack[] inv = player.field_71071_by.field_70462_a;
            int pHeal = player.func_71024_bL().func_75116_a();
            float pSat = player.func_71024_bL().func_75115_e();
            NBTTagCompound filterTag = item.func_77978_p();
            boolean bestTiming = true;
            float bestRate = 0.0f;
            int bestIndex = -1;
            for (int i = 0; i < inv.length; ++i) {
                if (inv[i] == null || !this.filterMatch(filterTag, inv[i]) || !(inv[i].func_77973_b() instanceof ItemFood)) continue;
                int tHeal = pHeal;
                float tSat = pSat;
                int heal = ((ItemFood)inv[i].func_77973_b()).func_150905_g(inv[i]);
                float sat = ((ItemFood)inv[i].func_77973_b()).func_150906_h(inv[i]);
                float val0 = (float)Math.min(heal, 20 - tHeal) + Math.min(sat, (float)Math.min(tHeal + heal, 20) - tSat);
                boolean best0 = true;
                if (itemIndex == 0) {
                    while (tSat > 0.0f || tHeal > 0) {
                        float val1;
                        if (tSat > 0.0f) {
                            tSat -= 1.0f;
                        }
                        if (tSat <= 0.0f) {
                            tSat = 0.0f;
                            --tHeal;
                        }
                        if (!((val1 = (float)Math.min(heal, 20 - tHeal) + Math.min(sat, (float)Math.min(tHeal + heal, 20) - tSat)) > val0)) continue;
                        val0 = val1;
                        best0 = false;
                    }
                }
                if (!(bestRate < val0 / ((float)heal + sat))) continue;
                bestRate = val0 / ((float)heal + sat);
                bestIndex = i;
                bestTiming = best0;
            }
            if (bestIndex >= 0 && bestTiming) {
                inv[bestIndex].func_77950_b(entity.field_70170_p, player);
                if (inv[bestIndex] != null && inv[bestIndex].field_77994_a == 0) {
                    inv[bestIndex] = null;
                }
                player.field_71071_by.func_70296_d();
            }
        }
    }

    @Override
    public boolean match(ItemStack itemStack, World world, Entity entity, int slot, boolean isCurrentItem) {
        if (itemStack != null && itemStack.func_77973_b() == CItems.itemGadget) {
            return this.itemNames.contains(CItems.itemGadget.getItemName(itemStack));
        }
        return false;
    }
}

