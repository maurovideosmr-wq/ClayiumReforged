/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.recipe.GuiCraftingRecipe
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.gui.client;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import mods.clayium.block.tile.TileClayWorkTable;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.GuiPictureButton;
import mods.clayium.gui.container.ContainerClayWorkTable;
import mods.clayium.network.GuiButtonPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GuiClayWorkTable
extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation("clayium", "textures/gui/clayworktable.png");
    private TileClayWorkTable tileClayWorkTable;

    public GuiClayWorkTable(int x, int y, int z) {
        super((Container)new ContainerClayWorkTable(x, y, z));
    }

    public GuiClayWorkTable(InventoryPlayer invPlayer, TileClayWorkTable tile) {
        super((Container)new ContainerClayWorkTable(invPlayer, tile));
        this.tileClayWorkTable = tile;
    }

    protected void func_146979_b(int mouseX, int mouseZ) {
        super.func_146979_b(mouseX, mouseZ);
        String string = this.tileClayWorkTable.func_145818_k_() ? this.tileClayWorkTable.func_145825_b() : I18n.func_135052_a((String)this.tileClayWorkTable.func_145825_b(), (Object[])new Object[0]);
        this.field_146289_q.func_78276_b(string, 6, 6, 0x404040);
        this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"container.inventory", (Object[])new Object[0]), 8, this.field_147000_g - 94, 0x404040);
    }

    protected void func_146976_a(float partialTick, int mouseX, int mouseZ) {
        int i1;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.field_146297_k.func_110434_K().func_110577_a(TEXTURE);
        int k = (this.field_146294_l - this.field_146999_f) / 2;
        int l = (this.field_146295_m - this.field_147000_g) / 2;
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.tileClayWorkTable.isBurning()) {
            i1 = 6;
            this.func_73729_b(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        i1 = this.tileClayWorkTable.getCookProgressScaled(80);
        this.func_73729_b(k + 48, l + 29, 176, 0, i1, 16);
        for (int i = 0; i < 6; ++i) {
            ((GuiPictureButton)((Object)this.field_146292_n.get((int)i))).field_146124_l = this.tileClayWorkTable.canPushButton(i + 1) != 0;
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        int k = (this.field_146294_l - this.field_146999_f) / 2;
        int l = (this.field_146295_m - this.field_147000_g) / 2;
        this.field_146292_n.add(new GuiPictureButton(1, k + 40, l + 52, 16, 16, TEXTURE, 176, 32));
        this.field_146292_n.add(new GuiPictureButton(2, k + 56, l + 52, 16, 16, TEXTURE, 192, 32));
        this.field_146292_n.add(new GuiPictureButton(3, k + 72, l + 52, 16, 16, TEXTURE, 208, 32));
        this.field_146292_n.add(new GuiPictureButton(4, k + 88, l + 52, 16, 16, TEXTURE, 224, 32));
        this.field_146292_n.add(new GuiPictureButton(5, k + 104, l + 52, 16, 16, TEXTURE, 240, 32));
        this.field_146292_n.add(new GuiPictureButton(6, k + 120, l + 52, 16, 16, TEXTURE, 176, 80));
    }

    protected void func_146284_a(GuiButton guibutton) {
        switch (guibutton.field_146127_k) {
            case 1: {
                break;
            }
        }
        if (!guibutton.field_146124_l) {
            return;
        }
        ClayiumCore.packetDispatcher.sendToServer((IMessage)new GuiButtonPacket(this.tileClayWorkTable.field_145851_c, this.tileClayWorkTable.field_145848_d, this.tileClayWorkTable.field_145849_e, guibutton.field_146127_k));
        this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, guibutton.field_146127_k);
    }

    public void func_73864_a(int mousex, int mousey, int button) {
        if (ClayiumCore.IntegrationID.NEI.loaded()) {
            int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
            int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
            int progressBarPosX = 48;
            int progressBarPosY = 33;
            int progressBarSizeX = 80;
            int progressBarSizeY = 12;
            if (offsetX + progressBarPosX <= mousex && offsetY + progressBarPosY <= mousey && offsetX + progressBarPosX + progressBarSizeX > mousex && offsetY + progressBarPosY + progressBarSizeY > mousey) {
                GuiCraftingRecipe.openRecipeGui((String)"ClayWorkTable", (Object[])new Object[0]);
                return;
            }
        }
        super.func_73864_a(mousex, mousey, button);
    }

    public void func_73863_a(int mousex, int mousey, float p_73863_3_) {
        super.func_73863_a(mousex, mousey, p_73863_3_);
        if (ClayiumCore.IntegrationID.NEI.loaded()) {
            int offsetX = (this.field_146294_l - this.field_146999_f) / 2;
            int offsetY = (this.field_146295_m - this.field_147000_g) / 2;
            int progressBarPosX = 48;
            int progressBarPosY = 33;
            int progressBarSizeX = 80;
            int progressBarSizeY = 12;
            if (offsetX + progressBarPosX <= mousex && offsetY + progressBarPosY <= mousey && offsetX + progressBarPosX + progressBarSizeX > mousex && offsetY + progressBarPosY + progressBarSizeY > mousey) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("Recipes");
                this.func_146283_a(list, mousex, mousey);
            }
        }
    }
}

