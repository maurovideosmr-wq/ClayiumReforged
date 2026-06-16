/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.recipe.GuiCraftingRecipe
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.util.ResourceLocation
 */
package mods.clayium.gui.client;

import codechicken.nei.recipe.GuiCraftingRecipe;
import java.util.ArrayList;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.GuiPictureButton;
import mods.clayium.gui.client.GuiClayEnergyTemp;
import mods.clayium.gui.client.GuiTemp;
import mods.clayium.gui.container.ContainerClayMachines;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.util.UtilTier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClayMachines
extends GuiClayEnergyTemp {
    protected static ResourceLocation TEXTURE = new ResourceLocation("clayium", "textures/gui/clayworktable.png");
    protected int progressBarPosX;
    protected int progressBarPosY;
    protected int progressBarSizeX;
    protected int progressBarSizeY;

    public GuiClayMachines(InventoryPlayer invPlayer, TileClayMachines tile, Block block) {
        super(new ContainerClayMachines(invPlayer, tile, block), tile, block);
    }

    public GuiClayMachines(ContainerTemp container, TileClayContainer tile, Block block) {
        super(container, tile, block);
    }

    @Override
    public void func_73863_a(int mousex, int mousey, float p_73863_3_) {
        super.func_73863_a(mousex, mousey, p_73863_3_);
        this.calculateProgressBarOffsets();
        if (ClayiumCore.IntegrationID.NEI.loaded() && !this.getOutputId().equals("")) {
            int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
            int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
            if (offsetX + this.progressBarPosX <= mousex && offsetY + this.progressBarPosY <= mousey && offsetX + this.progressBarPosX + this.progressBarSizeX > mousex && offsetY + this.progressBarPosY + this.progressBarSizeY > mousey) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("Recipes");
                this.func_146283_a(list, mousex, mousey);
            }
        }
    }

    @Override
    protected void func_146976_a(float partialTick, int mouseX, int mouseZ) {
        super.func_146976_a(partialTick, mouseX, mouseZ);
        this.calculateProgressBarOffsets();
        TileClayMachines tileClayMachines = (TileClayMachines)this.tile;
        int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
        int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
        ResourceLocation ProgressBarTexture = new ResourceLocation("clayium", "textures/gui/progressbarfurnace.png");
        this.calculateProgressBarOffsets();
        this.field_146297_k.func_110434_K().func_110577_a(ProgressBarTexture);
        this.func_73729_b(offsetX + this.progressBarPosX, offsetY + this.progressBarPosY, 0, 0, this.progressBarSizeX, this.progressBarSizeY);
        this.func_73729_b(offsetX + this.progressBarPosX, offsetY + this.progressBarPosY, 0, this.progressBarSizeY, tileClayMachines.getCraftProgressScaled(this.progressBarSizeX), this.progressBarSizeY);
        this.drawButton();
    }

    @Override
    public void addButton() {
        int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
        int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
        TileClayMachines tileClayMachines = (TileClayMachines)this.tile;
        ContainerClayMachines container = (ContainerClayMachines)this.field_147002_h;
        if (UtilTier.canManufactualCraft(tileClayMachines.getTier())) {
            this.field_146292_n.add(new GuiPictureButton(1, offsetX + (container.machineGuiSizeX - 16) / 2, offsetY + 56, 16, 16, GuiTemp.ButtonTexture, 0, 0));
        }
    }

    public void drawButton() {
        TileClayMachines tileClayMachines = (TileClayMachines)this.tile;
        if (UtilTier.canManufactualCraft(tileClayMachines.getTier())) {
            ((GuiPictureButton)((Object)this.field_146292_n.get((int)0))).field_146124_l = tileClayMachines.canPushButton() != 0;
        }
    }

    protected boolean contains(Block[] blocks, Block block) {
        for (Block b : blocks) {
            if (b != block) continue;
            return true;
        }
        return false;
    }

    @Override
    public void func_73864_a(int mousex, int mousey, int button) {
        this.calculateProgressBarOffsets();
        String outputId = this.getOutputId();
        if (ClayiumCore.IntegrationID.NEI.loaded() && !outputId.equals("")) {
            int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
            int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
            if (offsetX + this.progressBarPosX <= mousex && offsetY + this.progressBarPosY <= mousey && offsetX + this.progressBarPosX + this.progressBarSizeX > mousex && offsetY + this.progressBarPosY + this.progressBarSizeY > mousey) {
                GuiCraftingRecipe.openRecipeGui((String)outputId, (Object[])new Object[0]);
                return;
            }
        }
        super.func_73864_a(mousex, mousey, button);
    }

    protected void calculateProgressBarOffsets() {
        ContainerClayMachines container = (ContainerClayMachines)this.field_147002_h;
        this.progressBarSizeX = 24;
        this.progressBarSizeY = 17;
        this.progressBarPosX = (container.machineGuiSizeX - this.progressBarSizeX) / 2;
        this.progressBarPosY = 35;
    }

    protected String getOutputId() {
        TileClayMachines tileClayMachines = (TileClayMachines)this.tile;
        String recipeid = tileClayMachines.getNEIOutputId();
        return recipeid == null ? "" : recipeid;
    }
}

