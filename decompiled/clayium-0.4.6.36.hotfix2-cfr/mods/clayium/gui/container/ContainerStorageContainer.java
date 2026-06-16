/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotMemory;
import mods.clayium.gui.SlotResultWithTexture;
import mods.clayium.gui.SlotStorageContainer;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerStorageContainer
extends ContainerTemp {
    public ContainerStorageContainer(InventoryPlayer player, TileStorageContainer tile, Block block) {
        super(player, (IInventory)tile, block, new Object[0]);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        TileStorageContainer tile = (TileStorageContainer)this.tile;
        this.addMachineSlotToContainer(new SlotStorageContainer(tile, tile.extractSlotNum + tile.insertSlotNum - 1, 44, 35, RectangleTexture.LargeSlotTexture));
        this.addMachineSlotToContainer(new SlotResultWithTexture((IInventory)tile, tile.extractSlotNum + tile.insertSlotNum, 116, 35, RectangleTexture.LargeSlotTexture));
        this.addMachineSlotToContainer(new SlotMemory((IInventory)tile, tile.extractSlotNum + tile.insertSlotNum + 1, 108 + (this.machineGuiSizeX - 108) / 2, 18));
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        super.initParameters(player);
    }

    @Override
    public boolean canTransferToMachineInventory(ItemStack itemstack1) {
        return this.tile.func_94041_b(0, itemstack1);
    }

    @Override
    public boolean transferStackToMachineInventory(ItemStack itemstack1) {
        return this.func_75135_a(itemstack1, 0, 2, false);
    }

    @Override
    public boolean transferStackFromMachineInventory(ItemStack itemstack1, int slot) {
        return this.transferStackToPlayerInventory(itemstack1, true);
    }
}

