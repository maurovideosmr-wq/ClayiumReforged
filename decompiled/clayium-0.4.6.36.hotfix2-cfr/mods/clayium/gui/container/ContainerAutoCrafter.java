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

import mods.clayium.block.tile.TileAutoCrafter;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotMemory;
import mods.clayium.gui.SlotResultWithTexture;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerAutoCrafter
extends ContainerTemp {
    public ContainerAutoCrafter(InventoryPlayer player, TileAutoCrafter tile, Block block) {
        super(player, (IInventory)tile, block, new Object[0]);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        int i;
        int j;
        TileAutoCrafter tile = (TileAutoCrafter)this.tile;
        for (j = 0; j < 3; ++j) {
            for (i = 0; i < 3; ++i) {
                SlotWithTexture slot = new SlotWithTexture((IInventory)tile, i + j * 3, i * 18 + (this.machineGuiSizeX - 54) / 2 + 1, j * 18 + 18);
                slot.setRestricted();
                this.addMachineSlotToContainer(slot);
            }
        }
        for (j = 0; j < 3; ++j) {
            for (i = 0; i < 3; ++i) {
                this.addMachineSlotToContainer(new SlotMemory((IInventory)tile, i + j * 3 + 15, i * 18 + 5, j * 18 + 18, RectangleTexture.SmallSlotFilterTexture));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (i = 0; i < 2; ++i) {
                this.addMachineSlotToContainer(new SlotResultWithTexture((IInventory)tile, i + j * 2 + 9, i * 18 + (this.machineGuiSizeX - 36) - 5, j * 18 + 18));
            }
        }
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY = 84;
        super.initParameters(player);
    }

    @Override
    public boolean canTransferToMachineInventory(ItemStack itemstack1) {
        TileAutoCrafter tile = (TileAutoCrafter)this.tile;
        boolean res = false;
        for (int i = 0; i < 9; ++i) {
            res |= tile.func_94041_b(i, itemstack1);
        }
        return res;
    }

    @Override
    public boolean transferStackToMachineInventory(ItemStack itemstack1) {
        TileAutoCrafter tile = (TileAutoCrafter)this.tile;
        boolean res = false;
        for (int i = 0; i < 9; ++i) {
            if (!tile.func_94041_b(i, itemstack1)) continue;
            res |= this.func_75135_a(itemstack1, i, i + 1, false);
        }
        return res;
    }

    @Override
    public boolean transferStackFromMachineInventory(ItemStack itemstack1, int slot) {
        return this.transferStackToPlayerInventory(itemstack1, true);
    }
}

