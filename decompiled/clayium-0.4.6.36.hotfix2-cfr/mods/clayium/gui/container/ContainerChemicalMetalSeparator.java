/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotFurnace
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.gui.container.ContainerClayMachines;
import mods.clayium.item.CMaterials;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerChemicalMetalSeparator
extends ContainerClayMachines {
    public ContainerChemicalMetalSeparator(InventoryPlayer player, TileChemicalMetalSeparator tile, Block block) {
        super(player, tile, block);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY = 96;
        super.initParameters(player);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        this.addMachineSlotToContainer(new SlotWithTexture(this.tile, 0, 25, 44, RectangleTexture.LargeSlotTexture));
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.addMachineSlotToContainer((Slot)new SlotFurnace(player.field_70458_d, this.tile, i * 4 + j + 1, 85 + 18 * j, 17 + 18 * i));
            }
        }
    }

    @Override
    public boolean canTransferToMachineInventory(ItemStack itemstack1) {
        return itemstack1.func_77969_a(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST));
    }
}

