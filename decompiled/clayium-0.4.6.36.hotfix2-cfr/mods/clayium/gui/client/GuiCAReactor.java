/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.config.GuiButtonExt
 *  net.minecraft.block.Block
 *  net.minecraft.client.resources.I18n
 */
package mods.clayium.gui.client;

import cpw.mods.fml.client.config.GuiButtonExt;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.gui.client.GuiClayMachines;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;

public class GuiCAReactor
extends GuiClayMachines {
    public GuiCAReactor(ContainerTemp container, TileCAReactor tile, Block block) {
        super(container, (TileClayContainer)tile, block);
    }

    @Override
    protected void func_146979_b(int mouseX, int mouseZ) {
        super.func_146979_b(mouseX, mouseZ);
        ContainerTemp container = (ContainerTemp)this.field_147002_h;
        TileCAReactor tile = (TileCAReactor)this.tile;
        if (!tile.errorMessage.equals("")) {
            ((GuiButtonExt)this.field_146292_n.get((int)0)).field_146126_j = I18n.func_135052_a((String)"gui.CAReactor.invalid", (Object[])new Object[0]);
            ((GuiButtonExt)this.field_146292_n.get((int)0)).field_146124_l = true;
        } else {
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.CAReactor.rankSize", (Object[])new Object[]{tile.reactorRank + 1.0, tile.reactorHullNum}), 6, 16, 0x404040);
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.CAReactor.efficiency", (Object[])new Object[]{tile.getEfficiency()}), 64, container.machineGuiSizeY - 12, 0x404040);
            ((GuiButtonExt)this.field_146292_n.get((int)0)).field_146126_j = I18n.func_135052_a((String)"gui.CAReactor.constructed", (Object[])new Object[0]);
            ((GuiButtonExt)this.field_146292_n.get((int)0)).field_146124_l = false;
        }
    }

    @Override
    public void addButton() {
        ContainerTemp container = (ContainerTemp)this.field_147002_h;
        int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
        int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
        this.field_146292_n.add(new GuiButtonExt(1, offsetX + container.machineGuiSizeX - 84, offsetY + container.machineGuiSizeY - 1, 80, 10, ""));
        ((GuiButtonExt)this.field_146292_n.get((int)0)).field_146124_l = false;
    }
}

