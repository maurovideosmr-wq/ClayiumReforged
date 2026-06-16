/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelChest
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.tile;

import mods.clayium.block.tile.TileMetalChest;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MetalChestRenderer
extends TileEntitySpecialRenderer {
    public static MetalChestRenderer INSTANCE = new MetalChestRenderer();
    private static final ResourceLocation base = new ResourceLocation("clayium", "textures/entity/metalchest/base.png");
    private static final ResourceLocation dark = new ResourceLocation("clayium", "textures/entity/metalchest/dark.png");
    private static final ResourceLocation light = new ResourceLocation("clayium", "textures/entity/metalchest/light.png");
    private ModelChest field_147510_h = new ModelChest();

    public void renderChest(int metadata, float prevLidAngle, float lidAngle, CMaterial material, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        if (material == null) {
            material = CMaterials.ALUMINIUM;
        }
        ModelChest modelchest = this.field_147510_h;
        GL11.glPushMatrix();
        GL11.glEnable((int)32826);
        GL11.glTranslatef((float)((float)p_147500_2_), (float)((float)p_147500_4_ + 1.0f), (float)((float)p_147500_6_ + 1.0f));
        GL11.glScalef((float)1.0f, (float)-1.0f, (float)-1.0f);
        GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        int short1 = 0;
        if (metadata == 2) {
            short1 = 180;
        }
        if (metadata == 3) {
            short1 = 0;
        }
        if (metadata == 4) {
            short1 = 90;
        }
        if (metadata == 5) {
            short1 = -90;
        }
        GL11.glRotatef((float)short1, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
        float f1 = prevLidAngle + (lidAngle - prevLidAngle) * p_147500_8_;
        f1 = 1.0f - f1;
        f1 = 1.0f - f1 * f1 * f1;
        modelchest.field_78234_a.field_78795_f = -(f1 * (float)Math.PI / 2.0f);
        GL11.glDisable((int)3042);
        this.func_147499_a(base);
        GL11.glColor4f((float)((float)material.colors[0][0] / 255.0f), (float)((float)material.colors[0][1] / 255.0f), (float)((float)material.colors[0][2] / 255.0f), (float)1.0f);
        modelchest.func_78231_a();
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)-0.1f, (float)-1.0f);
        this.func_147499_a(dark);
        GL11.glColor4f((float)((float)material.colors[1][0] / 255.0f), (float)((float)material.colors[1][1] / 255.0f), (float)((float)material.colors[1][2] / 255.0f), (float)1.0f);
        modelchest.func_78231_a();
        GL11.glPolygonOffset((float)-0.2f, (float)-2.0f);
        this.func_147499_a(light);
        GL11.glColor4f((float)((float)material.colors[2][0] / 255.0f), (float)((float)material.colors[2][1] / 255.0f), (float)((float)material.colors[2][2] / 255.0f), (float)1.0f);
        modelchest.func_78231_a();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)32823);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)32826);
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void func_147500_a(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        if (tile instanceof TileMetalChest) {
            this.renderChest(tile.func_145830_o() ? tile.func_145831_w().func_72805_g(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e) : 0, ((TileMetalChest)tile).prevLidAngle, ((TileMetalChest)tile).lidAngle, ((TileMetalChest)tile).material, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
        }
    }
}

