/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.tile;

import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.laser.IClayLaserManager;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilRender;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ClayLaserRenderer
extends TileEntitySpecialRenderer {
    TileEntity tile;

    public void func_147500_a(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.tile = p_147500_1_;
        if (p_147500_1_ instanceof IClayLaserManager) {
            this.renderClayLaser((IClayLaserManager)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_);
        }
    }

    public void renderClayLaser(IClayLaserManager manager, double x, double y, double z) {
        Tessellator tessellator = Tessellator.field_78398_a;
        if (manager.isIrradiating()) {
            UtilRender.setLightValue(15, 15);
            GL11.glPushMatrix();
            GL11.glEnable((int)32826);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
            switch (manager.getDirection()) {
                case UP: {
                    GL11.glRotatef((float)0.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case DOWN: {
                    GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case NORTH: {
                    GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case SOUTH: {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case WEST: {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)-1.0f);
                    break;
                }
                case EAST: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)-1.0f);
                    break;
                }
            }
            ClayLaser laser = manager.getClayLaser();
            int[] num = laser.numbers;
            int str = 0;
            int max = 0;
            for (int n : num) {
                str += n;
                max = Math.max(max, n);
            }
            float scale = 1.0f - 14.0625f / (float)(str + 14);
            GL11.glScalef((float)scale, (float)1.0f, (float)scale);
            GL11.glTranslatef((float)0.0f, (float)(-scale / 6.0f), (float)0.0f);
            GL11.glScalef((float)1.0f, (float)((float)manager.getLaserLength() + scale / 3.0f), (float)1.0f);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2884);
            GL11.glDepthMask((boolean)false);
            this.func_147499_a(new ResourceLocation("clayium", "textures/blocks/laser.png"));
            float f14 = 0.0f;
            float f15 = 1.0f;
            float f4 = 0.0f;
            float f5 = 1.0f;
            int k = ClayiumCore.cfgLaserQuality;
            int i = 0;
            for (i = 0; i < k; ++i) {
                tessellator.func_78382_b();
                tessellator.func_78370_a(255 * num[2] / max, 255 * num[1] / max, 255 * num[0] / max, (int)(26.0f + scale * 26.0f) * 8 / k);
                tessellator.func_78374_a(0.0, 1.0, 0.5, (double)f14, (double)f4);
                tessellator.func_78374_a(0.0, 1.0, -0.5, (double)f15, (double)f4);
                tessellator.func_78374_a(0.0, 0.0, -0.5, (double)f15, (double)f5);
                tessellator.func_78374_a(0.0, 0.0, 0.5, (double)f14, (double)f5);
                tessellator.func_78381_a();
                GL11.glRotatef((float)(180.0f / (float)k), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)32826);
            GL11.glDisable((int)3042);
            GL11.glDepthMask((boolean)true);
            GL11.glPopMatrix();
        }
    }
}

