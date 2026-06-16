/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.IIcon
 *  net.minecraftforge.client.MinecraftForgeClient
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.util;

import mods.clayium.block.tile.IAxisAlignedBBContainer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class UtilRender {
    public static UtilRender INSTANCE = new UtilRender();

    public static void renderInvCuboid(RenderBlocks renderer, Block block, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, IIcon icon) {
        Tessellator tessellator = Tessellator.field_78398_a;
        float minX_ = (float)block.func_149704_x();
        float maxX_ = (float)block.func_149753_y();
        float minY_ = (float)block.func_149665_z();
        float maxY_ = (float)block.func_149669_A();
        float minZ_ = (float)block.func_149706_B();
        float maxZ_ = (float)block.func_149693_C();
        block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.func_147775_a(block);
        GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
        block.func_149676_a(minX, minY, minZ, maxX, maxY, maxZ);
        renderer.func_147775_a(block);
        tessellator.func_78382_b();
        tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
        renderer.func_147768_a(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        tessellator.func_78382_b();
        tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
        renderer.func_147806_b(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        tessellator.func_78382_b();
        tessellator.func_78375_b(0.0f, 0.0f, -1.0f);
        renderer.func_147764_f(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        tessellator.func_78382_b();
        tessellator.func_78375_b(0.0f, 0.0f, 1.0f);
        renderer.func_147798_e(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        tessellator.func_78382_b();
        tessellator.func_78375_b(-1.0f, 0.0f, 0.0f);
        renderer.func_147761_c(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        tessellator.func_78382_b();
        tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
        renderer.func_147734_d(block, 0.0, 0.0, 0.0, icon);
        tessellator.func_78381_a();
        GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        block.func_149676_a(minX_, minY_, minZ_, maxX_, maxY_, maxZ_);
        renderer.func_147775_a(block);
    }

    public static void renderAxisAlignedBB(IAxisAlignedBBContainer tile, float r, float g, float b, float alpha, boolean border) {
        int state = tile.getBoxAppearance();
        int pass = MinecraftForgeClient.getRenderPass();
        if (state != 0 && pass == 1 && tile.hasAxisAlignedBB()) {
            double minX = tile.getAxisAlignedBB().field_72340_a - TileEntityRendererDispatcher.field_147554_b;
            double minY = tile.getAxisAlignedBB().field_72338_b - TileEntityRendererDispatcher.field_147555_c;
            double minZ = tile.getAxisAlignedBB().field_72339_c - TileEntityRendererDispatcher.field_147552_d;
            double maxX = tile.getAxisAlignedBB().field_72336_d - TileEntityRendererDispatcher.field_147554_b;
            double maxY = tile.getAxisAlignedBB().field_72337_e - TileEntityRendererDispatcher.field_147555_c;
            double maxZ = tile.getAxisAlignedBB().field_72334_f - TileEntityRendererDispatcher.field_147552_d;
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)-1.0f, (float)-1.0f);
            GL11.glDisable((int)2912);
            if (state == 2) {
                GL11.glDisable((int)2929);
            }
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2884);
            Tessellator tessellator = Tessellator.field_78398_a;
            GL11.glDisable((int)3553);
            tessellator.func_78382_b();
            tessellator.func_78369_a(r, g, b, alpha);
            tessellator.func_78377_a(minX, minY, minZ);
            tessellator.func_78377_a(minX, minY, maxZ);
            tessellator.func_78377_a(minX, maxY, maxZ);
            tessellator.func_78377_a(minX, maxY, minZ);
            tessellator.func_78377_a(maxX, minY, minZ);
            tessellator.func_78377_a(maxX, minY, maxZ);
            tessellator.func_78377_a(maxX, maxY, maxZ);
            tessellator.func_78377_a(maxX, maxY, minZ);
            tessellator.func_78377_a(minX, minY, minZ);
            tessellator.func_78377_a(minX, minY, maxZ);
            tessellator.func_78377_a(maxX, minY, maxZ);
            tessellator.func_78377_a(maxX, minY, minZ);
            tessellator.func_78377_a(minX, maxY, minZ);
            tessellator.func_78377_a(minX, maxY, maxZ);
            tessellator.func_78377_a(maxX, maxY, maxZ);
            tessellator.func_78377_a(maxX, maxY, minZ);
            tessellator.func_78377_a(minX, minY, minZ);
            tessellator.func_78377_a(maxX, minY, minZ);
            tessellator.func_78377_a(maxX, maxY, minZ);
            tessellator.func_78377_a(minX, maxY, minZ);
            tessellator.func_78377_a(minX, minY, maxZ);
            tessellator.func_78377_a(maxX, minY, maxZ);
            tessellator.func_78377_a(maxX, maxY, maxZ);
            tessellator.func_78377_a(minX, maxY, maxZ);
            tessellator.func_78381_a();
            if (border) {
                GL11.glLineWidth((float)2.0f);
                tessellator.func_78371_b(2);
                tessellator.func_78369_a(r, g, b, 1.0f);
                tessellator.func_78377_a(minX, minY, minZ);
                tessellator.func_78377_a(minX, minY, maxZ);
                tessellator.func_78377_a(maxX, minY, maxZ);
                tessellator.func_78377_a(maxX, minY, minZ);
                tessellator.func_78381_a();
                tessellator.func_78371_b(2);
                tessellator.func_78369_a(r, g, b, 1.0f);
                tessellator.func_78377_a(minX, maxY, minZ);
                tessellator.func_78377_a(minX, maxY, maxZ);
                tessellator.func_78377_a(maxX, maxY, maxZ);
                tessellator.func_78377_a(maxX, maxY, minZ);
                tessellator.func_78381_a();
                tessellator.func_78371_b(1);
                tessellator.func_78369_a(r, g, b, 1.0f);
                tessellator.func_78377_a(minX, minY, minZ);
                tessellator.func_78377_a(minX, maxY, minZ);
                tessellator.func_78377_a(minX, minY, maxZ);
                tessellator.func_78377_a(minX, maxY, maxZ);
                tessellator.func_78377_a(maxX, minY, minZ);
                tessellator.func_78377_a(maxX, maxY, minZ);
                tessellator.func_78377_a(maxX, minY, maxZ);
                tessellator.func_78377_a(maxX, maxY, maxZ);
                tessellator.func_78381_a();
            }
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2912);
            if (state == 2) {
                GL11.glEnable((int)2929);
            }
            GL11.glDisable((int)32823);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2884);
        }
    }

    public static void renderAxisAlignedBB(IAxisAlignedBBContainer tile, float r, float g, float b) {
        UtilRender.renderAxisAlignedBB(tile, r, g, b, 0.3f, true);
    }

    public static void renderBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int state, float r, float g, float b, float alpha, boolean border) {
        UtilRender utilRender = INSTANCE;
        utilRender.getClass();
        UtilRender.renderAxisAlignedBB(utilRender.new AABBforRender(minX, minY, minZ, maxX, maxY, maxZ, state), r, g, b, alpha, border);
    }

    public static void renderBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int state, float r, float g, float b, boolean border) {
        UtilRender.renderBox(minX, minY, minZ, maxX, maxY, maxZ, state, r, g, b, 0.3f, true);
    }

    public static void renderBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int state, float r, float g, float b) {
        UtilRender.renderBox(minX, minY, minZ, maxX, maxY, maxZ, state, r, g, b, true);
    }

    public static void setLightValue(int sky, int block) {
        int i = sky << 20 | block << 4;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.func_77475_a((int)OpenGlHelper.field_77476_b, (float)((float)j / 1.0f), (float)((float)k / 1.0f));
    }

    public class AABBforRender
    implements IAxisAlignedBBContainer {
        private AxisAlignedBB aabb;
        private int state;

        public AABBforRender(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int state) {
            this.aabb = AxisAlignedBB.func_72330_a((double)minX, (double)minY, (double)minZ, (double)maxX, (double)maxY, (double)maxZ);
            this.state = state;
        }

        @Override
        public AxisAlignedBB getAxisAlignedBB() {
            return this.aabb;
        }

        @Override
        public boolean hasAxisAlignedBB() {
            return true;
        }

        @Override
        public int getBoxAppearance() {
            return this.state;
        }

        @Override
        public void setAxisAlignedBB(AxisAlignedBB aabb) {
            this.aabb = aabb;
        }
    }
}

