/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mods.clayium.block.CBlocks;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.LaserReflector;
import mods.clayium.block.laser.IClayLaserManager;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.TessellatorHelper;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderLaserReflector
implements ISimpleBlockRenderingHandler {
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        UtilDirection direction = UtilDirection.SOUTH;
        if (modelId == this.getRenderId()) {
            GL11.glPushMatrix();
            TessellatorHelper.startDrawingQuads();
            IIcon iicon = renderer.func_147745_b(CBlocks.blockLaserReflector);
            float f14 = iicon.func_94209_e();
            float f15 = iicon.func_94212_f();
            float f4 = iicon.func_94206_g();
            float f5 = iicon.func_94210_h();
            float f = 0.125f;
            TessellatorHelper.pushMatrix();
            TessellatorHelper.translate(-0.5, -0.5, -0.5);
            TessellatorHelper.scale(1.2, 1.2, 1.2);
            TessellatorHelper.translate(0.5, 0.5, 0.5);
            switch (direction) {
                case UP: {
                    TessellatorHelper.rotate(0.0, 1.0, 0.0, 0.0);
                    break;
                }
                case DOWN: {
                    TessellatorHelper.rotate(180.0, 1.0, 0.0, 0.0);
                    break;
                }
                case NORTH: {
                    TessellatorHelper.rotate(-90.0, 1.0, 0.0, 0.0);
                    break;
                }
                case SOUTH: {
                    TessellatorHelper.rotate(90.0, 1.0, 0.0, 0.0);
                    break;
                }
                case WEST: {
                    TessellatorHelper.rotate(-90.0, 0.0, 0.0, -1.0);
                    break;
                }
                case EAST: {
                    TessellatorHelper.rotate(90.0, 0.0, 0.0, -1.0);
                    break;
                }
            }
            TessellatorHelper.translate(-0.5, -0.5, -0.5);
            TessellatorHelper.setColorRGBA(255, 255, 255, 225);
            TessellatorHelper.setNormal(0.0f, -1.0f, 0.0f);
            TessellatorHelper.addVertexWithUV(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), f14, f4);
            TessellatorHelper.addVertexWithUV(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), f15, f4);
            TessellatorHelper.addVertexWithUV(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), f15, f5);
            TessellatorHelper.addVertexWithUV(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), f14, f5);
            double x1 = 0.5;
            double y1 = 1.0 - (double)f;
            double z1 = 0.5;
            double x2 = 1.0 - (double)(f * 2.0f);
            double y2 = 0.0 + (double)f;
            double z2 = 0.0 + (double)(f * 2.0f);
            double x3 = 0.0 + (double)(f * 2.0f);
            double y3 = 0.0 + (double)f;
            double z3 = 0.0 + (double)(f * 2.0f);
            double xa = x2 - x1;
            double ya = y2 - y1;
            double za = z2 - z1;
            double xb = x3 - x1;
            double yb = y3 - y1;
            double zb = z3 - z1;
            double xc = ya * zb - yb * za;
            double yc = za * xb - zb * xa;
            double zc = xa * yb - xb * ya;
            double lc = Math.pow(xc * xc + yc * yc + zc * zc, 0.5);
            for (int i = 0; i < 4; ++i) {
                TessellatorHelper.setNormal((float)(xc / lc), (float)(yc / lc), (float)(zc / lc));
                TessellatorHelper.addVertexWithUV(x1, y1, z1, f15, f4);
                TessellatorHelper.addVertexWithUV(x2, y2, z2, f15, f5);
                TessellatorHelper.addVertexWithUV(x3, y3, z3, f14, f5);
                TessellatorHelper.addVertexWithUV(x1, y1, z1, f15, f4);
                TessellatorHelper.translate(0.5, 0.0, 0.5);
                TessellatorHelper.rotate(90.0, 0.0, 1.0, 0.0);
                TessellatorHelper.translate(-0.5, 0.0, -0.5);
            }
            TessellatorHelper.popMatrix();
            TessellatorHelper.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            TessellatorHelper.draw();
            GL11.glPopMatrix();
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
        UtilDirection direction = UtilDirection.getOrientation(((LaserReflector)world.func_147439_a(x, y, z)).getFront(world, x, y, z));
        if (te != null && te instanceof IClayLaserManager && ((IClayLaserManager)UtilBuilder.safeGetTileEntity(world, x, y, z)).getDirection() != null) {
            direction = ((IClayLaserManager)UtilBuilder.safeGetTileEntity(world, x, y, z)).getDirection();
        }
        if (modelId == this.getRenderId() && direction != null) {
            if (ClayContainer.renderPass == 0) {
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78381_a();
                IIcon iicon = renderer.func_147745_b(CBlocks.blockLaserReflector);
                float f14 = iicon.func_94209_e();
                float f15 = iicon.func_94212_f();
                float f4 = iicon.func_94206_g();
                float f5 = iicon.func_94210_h();
                float f = 0.125f;
                GL11.glPushMatrix();
                GL11.glEnable((int)32826);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDisable((int)2884);
                GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
                GL11.glTranslatef((float)(-(x >> 4) * 16), (float)(-(y >> 4) * 16), (float)(-(z >> 4) * 16));
                switch (direction) {
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
                GL11.glTranslatef((float)((x >> 4) * 16), (float)((y >> 4) * 16), (float)((z >> 4) * 16));
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                tessellator.func_78382_b();
                tessellator.func_78380_c(block.func_149677_c(world, x, y, z));
                tessellator.func_78370_a(255, 255, 255, 255);
                tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
                tessellator.func_78374_a(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), (double)f14, (double)f4);
                tessellator.func_78374_a(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), (double)f15, (double)f4);
                tessellator.func_78374_a(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), (double)f15, (double)f5);
                tessellator.func_78374_a(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), (double)f14, (double)f5);
                tessellator.func_78381_a();
                double x1 = 0.5;
                double y1 = 1.0 - (double)f;
                double z1 = 0.5;
                double x2 = 1.0 - (double)(f * 2.0f);
                double y2 = 0.0 + (double)f;
                double z2 = 0.0 + (double)(f * 2.0f);
                double x3 = 0.0 + (double)(f * 2.0f);
                double y3 = 0.0 + (double)f;
                double z3 = 0.0 + (double)(f * 2.0f);
                double xa = x2 - x1;
                double ya = y2 - y1;
                double za = z2 - z1;
                double xb = x3 - x1;
                double yb = y3 - y1;
                double zb = z3 - z1;
                double xc = ya * zb - yb * za;
                double yc = za * xb - zb * xa;
                double zc = xa * yb - xb * ya;
                double lc = Math.pow(xc * xc + yc * yc + zc * zc, 0.5);
                for (int i = 0; i < 4; ++i) {
                    tessellator.func_78382_b();
                    tessellator.func_78375_b((float)(xc / lc), (float)(yc / lc), (float)(zc / lc));
                    tessellator.func_78374_a(x1, y1, z1, (double)f15, (double)f4);
                    tessellator.func_78374_a(x2, y2, z2, (double)f15, (double)f5);
                    tessellator.func_78374_a(x3, y3, z3, (double)f14, (double)f5);
                    tessellator.func_78374_a(x1, y1, z1, (double)f15, (double)f4);
                    tessellator.func_78381_a();
                    GL11.glTranslatef((float)0.5f, (float)0.0f, (float)0.5f);
                    GL11.glTranslatef((float)(-(x >> 4) * 16), (float)(-(y >> 4) * 16), (float)(-(z >> 4) * 16));
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glTranslatef((float)((x >> 4) * 16), (float)((y >> 4) * 16), (float)((z >> 4) * 16));
                    GL11.glTranslatef((float)-0.5f, (float)0.0f, (float)-0.5f);
                }
                GL11.glPopMatrix();
                GL11.glEnable((int)2884);
                GL11.glDisable((int)32826);
                GL11.glDisable((int)3042);
                tessellator.func_78382_b();
                tessellator.func_78386_a(1.0f, 1.0f, 1.0f);
                return true;
            }
            IIcon iicon = renderer.func_147745_b(CBlocks.blockLaserReflector);
            float f14 = iicon.func_94209_e();
            float f15 = iicon.func_94212_f();
            float f4 = iicon.func_94206_g();
            float f5 = iicon.func_94210_h();
            float f = 0.125f;
            TessellatorHelper.pushMatrix();
            TessellatorHelper.translate((double)x + 0.5, (double)y + 0.5, (double)z + 0.5);
            switch (direction) {
                case UP: {
                    TessellatorHelper.rotate(0.0, 1.0, 0.0, 0.0);
                    break;
                }
                case DOWN: {
                    TessellatorHelper.rotate(180.0, 1.0, 0.0, 0.0);
                    break;
                }
                case NORTH: {
                    TessellatorHelper.rotate(-90.0, 1.0, 0.0, 0.0);
                    break;
                }
                case SOUTH: {
                    TessellatorHelper.rotate(90.0, 1.0, 0.0, 0.0);
                    break;
                }
                case WEST: {
                    TessellatorHelper.rotate(-90.0, 0.0, 0.0, -1.0);
                    break;
                }
                case EAST: {
                    TessellatorHelper.rotate(90.0, 0.0, 0.0, -1.0);
                    break;
                }
            }
            TessellatorHelper.translate(-0.5, -0.5, -0.5);
            TessellatorHelper.setBrightness(block.func_149677_c(world, x, y, z));
            TessellatorHelper.setColorRGBA(255, 255, 255, 225);
            TessellatorHelper.setNormal(0.0f, -1.0f, 0.0f);
            TessellatorHelper.addVertexWithUV(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), f14, f4);
            TessellatorHelper.addVertexWithUV(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 0.0 + (double)(f * 2.0f), f15, f4);
            TessellatorHelper.addVertexWithUV(1.0 - (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), f15, f5);
            TessellatorHelper.addVertexWithUV(0.0 + (double)(f * 2.0f), 0.0 + (double)f, 1.0 - (double)(f * 2.0f), f14, f5);
            double x1 = 0.5;
            double y1 = 1.0 - (double)f;
            double z1 = 0.5;
            double x2 = 1.0 - (double)(f * 2.0f);
            double y2 = 0.0 + (double)f;
            double z2 = 0.0 + (double)(f * 2.0f);
            double x3 = 0.0 + (double)(f * 2.0f);
            double y3 = 0.0 + (double)f;
            double z3 = 0.0 + (double)(f * 2.0f);
            double xa = x2 - x1;
            double ya = y2 - y1;
            double za = z2 - z1;
            double xb = x3 - x1;
            double yb = y3 - y1;
            double zb = z3 - z1;
            double xc = ya * zb - yb * za;
            double yc = za * xb - zb * xa;
            double zc = xa * yb - xb * ya;
            double lc = Math.pow(xc * xc + yc * yc + zc * zc, 0.5);
            for (int i = 0; i < 4; ++i) {
                TessellatorHelper.setNormal((float)(xc / lc), (float)(yc / lc), (float)(zc / lc));
                TessellatorHelper.addVertexWithUV(x1, y1, z1, f15, f4);
                TessellatorHelper.addVertexWithUV(x2, y2, z2, f15, f5);
                TessellatorHelper.addVertexWithUV(x3, y3, z3, f14, f5);
                TessellatorHelper.addVertexWithUV(x1, y1, z1, f15, f4);
                TessellatorHelper.translate(0.5, 0.0, 0.5);
                TessellatorHelper.rotate(90.0, 0.0, 1.0, 0.0);
                TessellatorHelper.translate(-0.5, 0.0, -0.5);
            }
            TessellatorHelper.popMatrix();
            TessellatorHelper.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClayiumCore.laserReflectorRenderId;
    }
}

