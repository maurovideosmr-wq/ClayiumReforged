/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.INormalInventory;
import mods.clayium.block.tile.TileClayContainerTiered;
import net.minecraft.item.ItemStack;

public class TileClayBuffer
extends TileClayContainerTiered
implements INormalInventory {
    public int inventoryX = 0;
    public int inventoryY = 0;

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, -1, -1, 0, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = true;
        this.containerItemStacks = new ItemStack[54];
        this.clayEnergySlot = -1;
    }

    @Override
    public void initParamsByTier(int tier) {
        this.setDefaultTransportationParamsByTier(tier, TileClayContainerTiered.ParamMode.BUFFER);
        switch (tier) {
            case 4: {
                this.inventoryY = 1;
                this.inventoryX = 1 + 1;
                break;
            }
            case 5: {
                this.inventoryY = 2;
                this.inventoryX = 1 + 2;
                break;
            }
            case 6: {
                this.inventoryY = 3;
                this.inventoryX = 1 + 3;
                break;
            }
            case 7: {
                this.inventoryY = 4;
                this.inventoryX = 1 + 4;
                break;
            }
            case 8: {
                this.inventoryY = 4;
                this.inventoryX = 5 + 4;
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                this.inventoryY = 6;
                this.inventoryX = 3 + 6;
                break;
            }
            default: {
                this.inventoryY = 1;
                this.inventoryX = 1;
            }
        }
        int slotNum = this.inventoryX * this.inventoryY;
        int[] slots = new int[slotNum];
        int[] slots2 = new int[slotNum];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = i;
            slots2[i] = slots.length - i - 1;
        }
        this.listSlotsInsert.add(slots);
        this.listSlotsExtract.add(slots2);
        this.slotsDrop = slots;
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return true;
    }

    @Override
    public int[] func_94128_d(int side) {
        int slotNum = this.inventoryX * this.inventoryY;
        int[] slots = new int[slotNum];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canExtractItemUnsafe(int slot, ItemStack itemstack, int route) {
        return true;
    }

    @Override
    public boolean canInsertItemUnsafe(int slot, ItemStack itemstack, int route) {
        return true;
    }

    @Override
    public int getInventoryX() {
        return this.inventoryX;
    }

    @Override
    public int getInventoryY() {
        return this.inventoryY;
    }

    @Override
    public int getInventoryStart() {
        return 0;
    }

    @Override
    public int getInventoryP() {
        return 1;
    }
}

