/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerNothing
extends ContainerTemp {
    public ContainerNothing(InventoryPlayer player, IInventory tile) {
        this(player, tile, 32);
    }

    public ContainerNothing(InventoryPlayer player, IInventory tile, int machineGuiSizeY) {
        super(player, tile, null, machineGuiSizeY);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY = (Integer)this.additionalParams[0];
        super.initParameters(player);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
    }

    @Override
    public boolean canTransferToMachineInventory(ItemStack itemstack1) {
        return false;
    }

    @Override
    public boolean transferStackToMachineInventory(ItemStack itemstack1) {
        return false;
    }

    @Override
    public boolean transferStackFromMachineInventory(ItemStack itemstack1, int slot) {
        return false;
    }
}

