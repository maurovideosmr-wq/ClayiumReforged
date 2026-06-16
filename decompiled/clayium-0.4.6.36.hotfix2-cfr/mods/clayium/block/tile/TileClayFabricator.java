/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileSolarClayFabricator;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilTransfer;
import net.minecraft.item.ItemStack;

public class TileClayFabricator
extends TileSolarClayFabricator {
    public float exponentOfNumber;

    @Override
    public void initParams() {
        super.initParams();
        this.exponentOfNumber = 0.8f;
        this.insertRoutes = new int[]{-1, 0, -1, -1, -1, -1};
        this.extractRoutes = new int[]{0, -1, -1, -1, -1, -1};
        this.slotsDrop = new int[]{0, 1};
    }

    @Override
    public void initParamsByTier(int tier) {
        this.initCraftTime = 0.01f;
        if (tier >= 8) {
            this.acceptableTier = 11;
            this.baseCraftTime = 5.0f;
            this.exponentOfNumber = 0.85f;
            this.initCraftTime = (float)(Math.pow(10.0, this.acceptableTier) * 64.0 / (Math.pow(this.baseCraftTime, this.acceptableTier) * Math.pow(64.0, this.exponentOfNumber)) / (double)(ClayiumCore.multiplyProgressionRateF(4.5E7f) / 20.0f));
        }
        if (tier >= 9) {
            this.acceptableTier = 13;
            this.baseCraftTime = 2.0f;
            this.exponentOfNumber = 0.3f;
            this.initCraftTime = (float)(Math.pow(10.0, this.acceptableTier) * 64.0 / (Math.pow(this.baseCraftTime, this.acceptableTier) * Math.pow(64.0, this.exponentOfNumber)) / (double)(ClayiumCore.multiplyProgressionRateF(1.0E9f) / 20.0f));
        }
        if (tier >= 13) {
            this.acceptableTier = 13;
            this.baseCraftTime = 1.3f;
            this.exponentOfNumber = 0.06f;
            this.initCraftTime = (float)(Math.pow(10.0, this.acceptableTier) * 64.0 / (Math.pow(this.baseCraftTime, this.acceptableTier) * Math.pow(64.0, this.exponentOfNumber)) / (double)(ClayiumCore.multiplyProgressionRateF(1.0E12f) / 20.0f));
        }
    }

    protected boolean canCraft(int tier, int size) {
        return tier >= 0 && tier <= this.acceptableTier ? UtilTransfer.canProduceItemStack(TileClayFabricator.getCompressedClay(tier, size), this.containerItemStacks, 1, 2, this.func_70297_j_()) >= 1 : false;
    }

    @Override
    public boolean canProceedCraft() {
        return this.containerItemStacks[2] == null ? (this.containerItemStacks[0] == null ? false : this.canCraft(TileClayFabricator.getTierOfCompressedClay(this.containerItemStacks[0], false), this.containerItemStacks[0].field_77994_a)) : this.canCraft(TileClayFabricator.getTierOfCompressedClay(this.containerItemStacks[2], false), this.containerItemStacks[2].field_77994_a);
    }

    @Override
    public void proceedCraft() {
        if (this.containerItemStacks[2] == null) {
            this.machineTimeToCraft = (long)(Math.pow(this.baseCraftTime, TileClayFabricator.getTierOfCompressedClay(this.containerItemStacks[0], false)) * Math.pow(this.containerItemStacks[0].field_77994_a, this.exponentOfNumber) * (double)this.multCraftTime);
            this.containerItemStacks[2] = this.containerItemStacks[0].func_77946_l();
        }
        ++this.machineCraftTime;
        this.isDoingWork = true;
        this.clayEnergy = (long)(Math.pow(10.0, TileClayFabricator.getTierOfCompressedClay(this.containerItemStacks[2], false)) * (double)this.containerItemStacks[2].field_77994_a * (double)this.machineCraftTime / (double)this.machineTimeToCraft);
        if (this.machineCraftTime >= this.machineTimeToCraft) {
            this.clayEnergy = 0L;
            ItemStack result = TileClayFabricator.getCompressedClay(TileClayFabricator.getTierOfCompressedClay(this.containerItemStacks[2], false));
            result.field_77994_a = this.containerItemStacks[2].field_77994_a;
            this.containerItemStacks[2] = null;
            UtilTransfer.produceItemStack(result, this.containerItemStacks, 1, 2, this.func_70297_j_());
            this.machineCraftTime = 0L;
            if (this.externalControlState > 0) {
                --this.externalControlState;
                if (this.externalControlState == 0) {
                    this.externalControlState = -1;
                }
            }
        }
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return slot == 0 ? TileClayFabricator.getTierOfCompressedClay(itemstack, false) >= 0 && TileClayFabricator.getTierOfCompressedClay(itemstack, false) <= this.acceptableTier : true;
    }
}

