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

import mods.clayium.block.tile.TileAutoTrader;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotResultWithTexture;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerAutoTrader
extends ContainerTemp {
    public ContainerAutoTrader(InventoryPlayer player, TileAutoTrader tile, Block block) {
        super(player, (IInventory)tile, block, new Object[0]);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY += 16;
        super.initParameters(player);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        this.addMachineSlotToContainer(new SlotWithTexture(this.tile, 0, 36, 53, RectangleTexture.SmallSlotImport1Texture));
        this.addMachineSlotToContainer(new SlotWithTexture(this.tile, 1, 62, 53, RectangleTexture.SmallSlotImport2Texture));
        this.addMachineSlotToContainer(new SlotResultWithTexture(this.tile, 2, 120, 53, RectangleTexture.LargeSlotTexture));
    }

    @Override
    public boolean canTransferToMachineInventory(ItemStack itemstack1) {
        return true;
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

