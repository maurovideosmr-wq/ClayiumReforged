/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.ICrafting
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.gui.container;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mods.clayium.block.tile.TilePANCore;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.network.PANCoreListPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPANCore
extends ContainerTemp {
    public ContainerPANCore(InventoryPlayer player, TilePANCore tile, Block block) {
        super(player, (IInventory)tile, block, new Object[0]);
    }

    @Override
    protected void initParameters(InventoryPlayer player) {
        this.machineGuiSizeY += 72;
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

    public void func_75132_a(ICrafting p_75132_1_) {
        super.func_75132_a(p_75132_1_);
        if (p_75132_1_ instanceof EntityPlayerMP) {
            ClayiumCore.packetDispatcher.sendTo((IMessage)new PANCoreListPacket(this.field_75152_c, (TilePANCore)this.tile), (EntityPlayerMP)p_75132_1_);
        }
    }
}

