/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.misc.TileFluidTab;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class TileEntityFluidTabRenderer
extends TileEntitySpecialRenderer {
    public void renderTileEntityCupAt(TileFluidTab par1Tile, double par2, double par4, double par6, float par8) {
        this.setRotation(par1Tile, (float)par2, (float)par4, (float)par6);
    }

    public void setTileEntityRenderer(TileEntityRendererDispatcher par1TileEntityRenderer) {
        super.func_147497_a(par1TileEntityRenderer);
    }

    public void setRotation(TileFluidTab par0Tile, float par1, float par2, float par3) {
        Tessellator tessellator = Tessellator.field_78398_a;
        if (par0Tile.getFluidIcon() != null) {
            GL11.glPushMatrix();
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)2.0f, (float)2.0f, (float)2.0f, (float)0.75f);
            GL11.glTranslatef((float)par1, (float)(par2 + 0.5f), (float)par3);
            GL11.glScalef((float)1.0f, (float)-1.0f, (float)-1.0f);
            GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            IIcon iicon = par0Tile.getFluidIcon();
            float f14 = iicon.func_94209_e();
            float f15 = iicon.func_94212_f();
            float f4 = iicon.func_94206_g();
            float f5 = iicon.func_94210_h();
            this.func_147499_a(TextureMap.field_110575_b);
            float f = 0.0625f;
            tessellator.func_78382_b();
            tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
            tessellator.func_78374_a(0.0 + (double)f, -0.4, -1.0 + (double)f, (double)f15, (double)f4);
            tessellator.func_78374_a(1.0 - (double)f, -0.4, -1.0 + (double)f, (double)f14, (double)f4);
            tessellator.func_78374_a(1.0 - (double)f, -0.4, 0.0 - (double)f, (double)f14, (double)f5);
            tessellator.func_78374_a(0.0 + (double)f, -0.4, 0.0 - (double)f, (double)f15, (double)f5);
            tessellator.func_78381_a();
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }

    public void func_147500_a(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
        this.renderTileEntityCupAt((TileFluidTab)par1TileEntity, par2, par4, par6, par8);
    }
}

