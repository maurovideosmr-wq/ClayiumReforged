/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.util.ForgeDirection
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.block.tile.TileCobblestoneGenerator;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilTransfer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSaltExtractor
extends TileCobblestoneGenerator {
    public static int energyPerWork = 30;

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, -1, -1, 0, -1, -1};
        this.extractRoutes = new int[]{-1, -1, 0, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = true;
        this.clayEnergySlot = this.containerItemStacks.length;
        this.containerItemStacks = new ItemStack[this.clayEnergySlot + 1];
    }

    @Override
    public void initParamsByTier(int tier) {
        super.initParamsByTier(tier);
        this.listSlotsInsert = new ArrayList();
        this.listSlotsInsert.add(new int[]{this.clayEnergySlot});
        int[] slots = new int[this.slotsDrop.length + 1];
        for (int i = 0; i < this.slotsDrop.length; ++i) {
            slots[i] = i;
        }
        slots[this.slotsDrop.length] = this.clayEnergySlot;
        this.slotsDrop = slots;
    }

    @Override
    public void produce() {
        int count = this.countWater();
        if (this.externalControlState >= 0 && count > 0 && this.consumeClayEnergy(this.progressEfficiency * energyPerWork)) {
            this.progress += this.progressEfficiency * count;
            while (true) {
                if (this.progress < progressMax) break;
                this.setSyncFlag();
                ItemStack salt = CMaterials.get(CMaterials.SALT, CMaterials.DUST).func_77946_l();
                this.progress -= progressMax;
                UtilTransfer.produceItemStack(salt, this.containerItemStacks, 0, this.inventoryX * this.inventoryY, this.func_70297_j_());
                if (this.externalControlState <= 0) continue;
                --this.externalControlState;
                if (this.externalControlState != 0) continue;
                this.externalControlState = -1;
            }
        }
    }

    @Override
    public boolean isScheduled() {
        return this.countWater() > 0;
    }

    public int countWater() {
        ForgeDirection[] sides = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.DOWN};
        int count = 0;
        for (ForgeDirection side : sides) {
            Block block = this.field_145850_b.func_147439_a(this.field_145851_c + side.offsetX, this.field_145848_d + side.offsetY, this.field_145849_e + side.offsetZ);
            if (block.func_149688_o() != Material.field_151586_h) continue;
            ++count;
        }
        return count;
    }

    @Override
    public int[] func_94128_d(int side) {
        return this.getAccessibleSlotsFromSideDefault(side);
    }

    @Override
    public boolean func_102007_a(int slot, ItemStack itemstack, int side) {
        return this.canInsertItemDefault(slot, itemstack, side);
    }

    @Override
    public boolean func_102008_b(int slot, ItemStack itemstack, int side) {
        return this.canExtractItemDefault(slot, itemstack, side);
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return this.isItemValidForSlotDefault(slot, itemstack);
    }
}

