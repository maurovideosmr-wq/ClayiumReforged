/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileAutoClayCondenser;
import mods.clayium.block.tile.TileClayBuffer;
import mods.clayium.block.tile.TileClayContainerTiered;
import mods.clayium.util.UtilItemStack;
import net.minecraft.item.ItemStack;

public class TileCreativeEnergySource
extends TileClayBuffer {
    public static ItemStack oec;

    @Override
    public void initParamsByTier(int tier) {
        this.setDefaultTransportationParamsByTier(tier, TileClayContainerTiered.ParamMode.BUFFER);
        switch (tier) {
            default: 
        }
        this.inventoryY = 1;
        this.inventoryX = 1;
        int slotNum = this.inventoryX * this.inventoryY;
        int[] slots = new int[slotNum];
        int[] slots2 = new int[slotNum];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = i;
            slots2[i] = slots.length - i - 1;
        }
        this.listSlotsExtract.add(slots2);
        this.slotsDrop = new int[0];
    }

    public static ItemStack getEnergeticClay() {
        if (oec == null) {
            TileCreativeEnergySource.setEnergeticClay(TileAutoClayCondenser.getCompressedClay(13, 64));
        }
        return oec;
    }

    public static void setEnergeticClay(ItemStack item) {
        oec = item;
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        this.func_70299_a(0, TileCreativeEnergySource.getEnergeticClay().func_77946_l());
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return UtilItemStack.areStackEqual(TileCreativeEnergySource.getEnergeticClay(), itemstack);
    }

    @Override
    public boolean canExtractItemUnsafe(int slot, ItemStack itemstack, int route) {
        this.func_70299_a(0, TileCreativeEnergySource.getEnergeticClay().func_77946_l());
        return true;
    }

    @Override
    public boolean canInsertItemUnsafe(int slot, ItemStack itemstack, int route) {
        return itemstack == null;
    }

    @Override
    public ItemStack func_70301_a(int slot) {
        return TileCreativeEnergySource.getEnergeticClay().func_77946_l();
    }
}

