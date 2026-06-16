/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDummy
extends ContainerTemp {
    public ContainerDummy() {
        super(null, null, null, new Object[0]);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
    }

    @Override
    public void setTileEntity(IInventory tile) {
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

    @Override
    public int addMachineSlotToContainer(Slot slot) {
        return 0;
    }

    @Override
    public void addMachineInventorySlots(InventoryPlayer player) {
    }

    @Override
    public void addPlayerInventorySlots(InventoryPlayer player) {
    }

    @Override
    public boolean func_75145_c(EntityPlayer player) {
        return true;
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public String getTextFieldString(EntityPlayer player, int id) {
        return null;
    }

    @Override
    public void setTextFieldString(EntityPlayer player, String string, int id) {
    }

    @Override
    public void sendTextFieldStringToClient(EntityPlayer player, String string, int id) {
    }

    @Override
    public ItemStack func_82846_b(EntityPlayer player, int par2) {
        return null;
    }

    @Override
    public boolean transferStackToPlayerInventory(ItemStack itemstack1, boolean flag) {
        return false;
    }

    @Override
    public boolean transferStackFromPlayerInventory(ItemStack itemstack1, int par2) {
        return false;
    }

    @Override
    public boolean func_75140_a(EntityPlayer player, int action) {
        return true;
    }

    public void func_75130_a(IInventory p_75130_1_) {
    }
}

