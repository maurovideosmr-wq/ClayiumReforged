/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.tile;

import mods.clayium.block.tile.TileClayMarker;
import mods.clayium.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ClayMarkerRenderer
extends TileEntitySpecialRenderer {
    static double[] ds1 = new double[]{0.1875, -0.1875};
    static double[] ds2 = new double[]{0.125, -0.125};

    public void func_147500_a(TileEntity tile, double x, double y, double z, float p_147500_8_) {
        if (tile instanceof TileClayMarker) {
            UtilRender.setLightValue(15, 15);
            TileClayMarker tile1 = (TileClayMarker)tile;
            switch (tile1.state) {
                case 1: {
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)1);
                    GL11.glDisable((int)2912);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)2896);
                    GL11.glDisable((int)2884);
                    Tessellator tessellator = Tessellator.field_78398_a;
                    GL11.glDisable((int)3553);
                    GL11.glLineWidth((float)3.0f);
                    tessellator.func_78371_b(1);
                    tessellator.func_78369_a(0.1f, 0.5f, 0.2f, 0.3f);
                    for (double d1 : ds1) {
                        for (double d2 : ds1) {
                            tessellator.func_78377_a(x + 0.0 + (double)TileClayMarker.maxRange, y + 0.5 + d1, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 1.0 - (double)TileClayMarker.maxRange, y + 0.5 + d1, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.0 + (double)TileClayMarker.maxRange, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 1.0 - (double)TileClayMarker.maxRange, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.5 + d2, z + 0.0 + (double)TileClayMarker.maxRange);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.5 + d2, z + 1.0 - (double)TileClayMarker.maxRange);
                        }
                    }
                    tessellator.func_78381_a();
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2929);
                    GL11.glEnable((int)2912);
                    GL11.glLineWidth((float)2.0f);
                    tessellator.func_78371_b(1);
                    tessellator.func_78369_a(0.2f, 0.7f, 0.3f, 1.0f);
                    double d = 0.02;
                    for (double d1 : ds1) {
                        for (double d2 : ds1) {
                            tessellator.func_78377_a(x + 0.0 + (double)TileClayMarker.maxRange, y + 0.5 + d1, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 1.0 - (double)TileClayMarker.maxRange, y + 0.5 + d1, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.0 + (double)TileClayMarker.maxRange, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 1.0 - (double)TileClayMarker.maxRange, z + 0.5 + d2);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.5 + d2, z + 0.0 + (double)TileClayMarker.maxRange);
                            tessellator.func_78377_a(x + 0.5 + d1, y + 0.5 + d2, z + 1.0 - (double)TileClayMarker.maxRange);
                        }
                    }
                    tessellator.func_78381_a();
                    GL11.glEnable((int)2896);
                    GL11.glEnable((int)3553);
                    GL11.glEnable((int)2884);
                    GL11.glBlendFunc((int)770, (int)771);
                    Block block = tile.func_145831_w().func_147439_a(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
                    UtilRender.renderBox((double)tile.field_145851_c + block.func_149704_x(), (double)tile.field_145848_d + block.func_149665_z(), (double)tile.field_145849_e + block.func_149706_B(), (double)tile.field_145851_c + block.func_149753_y(), (double)tile.field_145848_d + block.func_149669_A(), (double)tile.field_145849_e + block.func_149693_C(), 1, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 2: 
                case 3: 
                case 4: {
                    UtilRender.renderAxisAlignedBB(tile1, 0.1f, 0.1f, 0.7f);
                    Block block1 = tile.func_145831_w().func_147439_a(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
                    UtilRender.renderBox((double)tile.field_145851_c + block1.func_149704_x(), (double)tile.field_145848_d + block1.func_149665_z(), (double)tile.field_145849_e + block1.func_149706_B(), (double)tile.field_145851_c + block1.func_149753_y(), (double)tile.field_145848_d + block1.func_149669_A(), (double)tile.field_145849_e + block1.func_149693_C(), Math.max(1, tile1.getBoxAppearance()), 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
}

