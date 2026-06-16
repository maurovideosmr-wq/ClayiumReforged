/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotFurnace
 */
package mods.clayium.gui.container;

import mods.clayium.block.tile.TileClayCentrifuge;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotWithTexture;
import mods.clayium.gui.container.ContainerClayMachines;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerClayCentrifuge
extends ContainerClayMachines {
    protected int resultSlotNum = 1;

    public ContainerClayCentrifuge(InventoryPlayer player, TileClayCentrifuge tile, Block block) {
        super(player, tile, block);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.resultSlotNum = ((TileClayCentrifuge)this.tile).resultSlotNum;
        this.machineGuiSizeY = (this.resultSlotNum + 1) * 9 + 46;
        super.initParameters(player);
    }

    @Override
    public void setMachineInventorySlots(InventoryPlayer player) {
        this.addMachineSlotToContainer(new SlotWithTexture(this.tile, 0, 44, 35, RectangleTexture.LargeSlotTexture));
        for (int i = 0; i < this.resultSlotNum; ++i) {
            this.addMachineSlotToContainer((Slot)new SlotFurnace(player.field_70458_d, this.tile, i + 1, 116, 35 + 18 * i - 9 * (this.resultSlotNum - 1)));
        }
    }
}

