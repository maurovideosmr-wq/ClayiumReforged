/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.gui.client;

import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.gui.client.GuiClayContainerTemp;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiClayEnergyTemp
extends GuiClayContainerTemp {
    public GuiClayEnergyTemp(ContainerTemp container, TileClayContainer tile, Block block) {
        super(container, tile, block);
    }

    @Override
    protected void drawClayEnergyForegroundLayer() {
        ContainerTemp container = (ContainerTemp)this.field_147002_h;
        this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.Common.energy", (Object[])new Object[]{UtilLocale.ClayEnergyNumeral(this.tile.clayEnergy, false)}), 4, container.machineGuiSizeY - 12, 0x404040);
    }

    @Override
    protected void drawClayEnergyItem(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        if (!this.func_146272_n()) {
            return;
        }
        if (this.tile == null) {
            return;
        }
        if (this.tile.containerItemStacks == null) {
            return;
        }
        if (this.tile.containerItemStacks.length <= this.tile.clayEnergySlot || this.tile.clayEnergySlot < 0) {
            return;
        }
        ItemStack itemstack = this.tile.containerItemStacks[this.tile.clayEnergySlot];
        if (itemstack == null) {
            return;
        }
        int k = (this.field_146294_l - this.field_146999_f) / 2;
        int l = (this.field_146295_m - this.field_147000_g) / 2;
        int itemX = -12;
        int itemY = ((ContainerTemp)this.field_147002_h).machineGuiSizeY - 16;
        GL11.glPushMatrix();
        RenderHelper.func_74520_c();
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glEnable((int)2896);
        GuiClayEnergyTemp.field_146296_j.field_77023_b = 100.0f;
        field_146296_j.func_82406_b(this.field_146289_q, this.field_146297_k.func_110434_K(), itemstack, k + itemX, l + itemY);
        field_146296_j.func_77021_b(this.field_146289_q, this.field_146297_k.func_110434_K(), itemstack, k + itemX, l + itemY);
        GuiClayEnergyTemp.field_146296_j.field_77023_b = 0.0f;
        GL11.glDisable((int)2896);
        if (this.func_146978_c(itemX, itemY, 16, 16, p_73863_1_, p_73863_2_)) {
            this.func_146285_a(itemstack, p_73863_1_, p_73863_2_);
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        RenderHelper.func_74519_b();
    }
}

