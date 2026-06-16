/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import mods.clayium.block.tile.Inventories;
import mods.clayium.block.tile.InventoryMultiPage;
import mods.clayium.block.tile.InventoryOffsetted;
import mods.clayium.block.tile.TilePANAdapter;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotMemory;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.gui.container.ContainerNormalInventory;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPANAdapter
extends ContainerTemp {
    TilePANAdapter tilePANAdapter;

    public ContainerPANAdapter(InventoryPlayer player, TilePANAdapter tile, Block block) {
        super(player, ContainerPANAdapter.getInventory(tile), block, new Object[0]);
        this.tilePANAdapter = tile;
        this.tilePANAdapter.onSlotChange();
    }

    private static IInventory getInventory(TilePANAdapter tile) {
        return new Inventories(new IInventory[]{new InventoryMultiPage((IInventory)tile, 0, 18, tile.getPageNum()), new InventoryOffsetted((IInventory)tile, 144, 9)});
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        int i;
        int j;
        for (j = 0; j < 3; ++j) {
            for (i = 0; i < 3; ++i) {
                this.addMachineSlotToContainer(new SlotMemory(this, this.tile, i + j * 3, i * 18 + (this.machineGuiSizeX - 54) / 2 - 30 + 1, j * 18 + 18, RectangleTexture.SmallSlotFilterTexture));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (i = 0; i < 3; ++i) {
                SlotMemory slot = new SlotMemory(this, this.tile, i + j * 3 + 9, i * 18 + (this.machineGuiSizeX - 54) / 2 + 30 + 1, j * 18 + 18);
                this.addMachineSlotToContainer(slot);
            }
        }
        for (int i2 = 0; i2 < 9; ++i2) {
            SlotWithTexture slot = new SlotWithTexture(this, this.tile, 18 + i2, i2 * 18 + (this.machineGuiSizeX - 162) / 2 + 1, 74);
            this.addMachineSlotToContainer(slot);
        }
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY = 96;
        super.initParameters(player);
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

    public boolean isMultipage() {
        return ((InventoryMultiPage)((Inventories)this.tile).get(0)).isMultiPage();
    }

    public int getPresentPageNum() {
        return ((InventoryMultiPage)((Inventories)this.tile).get(0)).getPresentPage() + 1;
    }

    public int getMaxPageNum() {
        return ((InventoryMultiPage)((Inventories)this.tile).get((int)0)).pageNum;
    }

    @Override
    public boolean pushClientButton(EntityPlayer player, int action) {
        int action0 = Inventories.getInnerActionId(action);
        if (action0 == ContainerNormalInventory.buttonIdPrevious || action0 == ContainerNormalInventory.buttonIdNext) {
            return this.func_75140_a(player, action);
        }
        return true;
    }

    public void func_75130_a(IInventory p_75130_1_) {
        this.tilePANAdapter.onSlotChange();
    }
}

