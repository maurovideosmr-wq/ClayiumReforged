/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import java.util.HashMap;
import java.util.Map;
import mods.clayium.block.tile.IGeneralInterface;
import mods.clayium.block.tile.INormalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryOffsetted
implements INormalInventory,
IGeneralInterface {
    private IInventory inventory;
    protected int offset = 0;
    protected int offsetmin = Integer.MIN_VALUE;
    protected int offsetmax = Integer.MAX_VALUE;
    protected int inventorySize = 0;
    protected Map<Integer, Integer> buttonMap = new HashMap<Integer, Integer>();

    public InventoryOffsetted(IInventory inventory) {
        this(inventory, 0);
    }

    public InventoryOffsetted(IInventory inventory, int initialOffset) {
        this(inventory, initialOffset, inventory.func_70302_i_());
    }

    public InventoryOffsetted(IInventory inventory, int initialOffset, int inventorySize) {
        this.inventory = inventory;
        this.setOffset(initialOffset);
        this.inventorySize = inventorySize;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setOffsetBound(int min, int max) {
        this.offsetmin = min;
        this.offsetmax = max;
    }

    public void addButton(int buttonid, int offset) {
        this.buttonMap.put(buttonid, offset);
    }

    @Override
    public void markForStrongUpdate() {
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).markForStrongUpdate();
        }
    }

    @Override
    public void markForWeakUpdate() {
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).markForWeakUpdate();
        }
    }

    @Override
    public void setSyncFlag() {
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).setSyncFlag();
        }
    }

    @Override
    public void setInstantSyncFlag() {
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).setInstantSyncFlag();
        }
    }

    @Override
    public void setRenderSyncFlag() {
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).setRenderSyncFlag();
        }
    }

    @Override
    public void pushButton(EntityPlayer player, int action) {
        if (this.buttonMap.containsKey(action)) {
            this.offset += this.buttonMap.get(action).intValue();
            this.offset = Math.max(this.offsetmin, Math.min(this.offset, this.offsetmax));
            return;
        }
        if (this.inventory instanceof IGeneralInterface) {
            ((IGeneralInterface)this.inventory).setRenderSyncFlag();
        }
    }

    public int func_70302_i_() {
        return this.inventorySize;
    }

    public ItemStack func_70301_a(int slot) {
        if (this.offset + slot >= 0 && this.offset + slot < this.inventory.func_70302_i_()) {
            return this.inventory.func_70301_a(this.offset + slot);
        }
        return null;
    }

    public ItemStack func_70298_a(int slot, int p_70298_2_) {
        if (this.offset + slot >= 0 && this.offset + slot < this.inventory.func_70302_i_()) {
            return this.inventory.func_70298_a(this.offset + slot, p_70298_2_);
        }
        return null;
    }

    public ItemStack func_70304_b(int slot) {
        if (this.offset + slot >= 0 && this.offset + slot < this.inventory.func_70302_i_()) {
            return this.inventory.func_70304_b(this.offset + slot);
        }
        return null;
    }

    public void func_70299_a(int slot, ItemStack p_70299_2_) {
        if (this.offset + slot >= 0 && this.offset + slot < this.inventory.func_70302_i_()) {
            this.inventory.func_70299_a(this.offset + slot, p_70299_2_);
        }
    }

    public String func_145825_b() {
        return this.inventory.func_145825_b();
    }

    public boolean func_145818_k_() {
        return this.inventory.func_145818_k_();
    }

    public int func_70297_j_() {
        return this.inventory.func_70297_j_();
    }

    public void func_70296_d() {
        this.inventory.func_70296_d();
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_) {
        return this.inventory.func_70300_a(p_70300_1_);
    }

    public void func_70295_k_() {
        this.inventory.func_70295_k_();
    }

    public void func_70305_f() {
        this.inventory.func_70305_f();
    }

    public boolean func_94041_b(int slot, ItemStack p_94041_2_) {
        if (this.offset + slot >= 0 && this.offset + slot < this.inventory.func_70302_i_()) {
            return this.inventory.func_94041_b(this.offset + slot, p_94041_2_);
        }
        return false;
    }

    @Override
    public int getInventoryX() {
        if (this.inventory instanceof INormalInventory) {
            return ((INormalInventory)this.inventory).getInventoryX();
        }
        return 1;
    }

    @Override
    public int getInventoryY() {
        if (this.inventory instanceof INormalInventory) {
            return ((INormalInventory)this.inventory).getInventoryY();
        }
        return 1;
    }

    @Override
    public int getInventoryP() {
        if (this.inventory instanceof INormalInventory) {
            return ((INormalInventory)this.inventory).getInventoryP();
        }
        return 1;
    }

    @Override
    public int getInventoryStart() {
        if (this.inventory instanceof INormalInventory) {
            return ((INormalInventory)this.inventory).getInventoryStart();
        }
        return 0;
    }
}

