/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.resources.I18n
 */
package mods.clayium.gui.client;

import mods.clayium.block.tile.TileAreaMiner;
import mods.clayium.gui.GuiPictureButton;
import mods.clayium.gui.client.GuiClayEnergyTemp;
import mods.clayium.gui.client.GuiTemp;
import mods.clayium.gui.container.ContainerAreaMiner;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;

public class GuiAreaMiner
extends GuiClayEnergyTemp {
    public GuiAreaMiner(ContainerAreaMiner container, TileAreaMiner tile, Block block) {
        super(container, tile, block);
    }

    @Override
    protected void func_146979_b(int mouseX, int mouseZ) {
        super.func_146979_b(mouseX, mouseZ);
        ContainerTemp container = (ContainerTemp)this.field_147002_h;
        long energy = ((TileAreaMiner)this.tile).laserEnergy;
        this.field_146289_q.func_78276_b(UtilLocale.laserGui(energy), 64, container.machineGuiSizeY - 12, 0x404040);
        if (((TileAreaMiner)this.tile).getTier() >= 9) {
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.AreaMiner.harvest", (Object[])new Object[0]), container.machineGuiSizeX - 48, 21, 0x404040);
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.AreaMiner.fortune", (Object[])new Object[0]), container.machineGuiSizeX - 48, 39, 0x404040);
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.AreaMiner.silktouch", (Object[])new Object[0]), container.machineGuiSizeX - 48, 57, 0x404040);
        }
    }

    @Override
    public void addButton() {
        int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
        int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
        if (((TileAreaMiner)this.tile).getTier() >= 9) {
            offsetX = (this.field_146294_l - this.field_146999_f) / 2 - 10;
        }
        if (((TileAreaMiner)this.tile).getTier() <= 9) {
            this.field_146292_n.add(new GuiPictureButton(0, offsetX + 16, offsetY + 16, 16, 16, GuiTemp.ButtonTexture, 16, 0));
            this.field_146292_n.add(new GuiPictureButton(1, offsetX + 16 + 18, offsetY + 16, 16, 16, GuiTemp.ButtonTexture, 32, 0));
            this.field_146292_n.add(new GuiPictureButton(2, offsetX + 16, offsetY + 16 + 18, 16, 16, GuiTemp.ButtonTexture, 64, 0));
            this.field_146292_n.add(new GuiPictureButton(3, offsetX + 16 + 18, offsetY + 16 + 18, 16, 16, GuiTemp.ButtonTexture, 48, 0));
        } else {
            this.field_146292_n.add(new GuiPictureButton(0, offsetX + 16, offsetY + 16, 16, 16, GuiTemp.ButtonTexture, 16, 0));
            this.field_146292_n.add(new GuiPictureButton(1, offsetX + 16, offsetY + 16 + 18, 16, 16, GuiTemp.ButtonTexture, 32, 0));
            this.field_146292_n.add(new GuiPictureButton(2, offsetX + 16, offsetY + 16 + 36, 16, 16, GuiTemp.ButtonTexture, 64, 0));
            this.field_146292_n.add(new GuiPictureButton(3, offsetX + 16, offsetY + 16 + 54, 16, 16, GuiTemp.ButtonTexture, 48, 0));
        }
    }
}

