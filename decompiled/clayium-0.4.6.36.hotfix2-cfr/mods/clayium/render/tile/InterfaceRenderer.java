/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.tile;

import mods.clayium.block.tile.ISynchronizedInterface;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class InterfaceRenderer
extends TileEntitySpecialRenderer {
    private int count = 0;

    public void func_147500_a(TileEntity tile, double x, double y, double z, float partialTicktime) {
        if (tile != null && tile instanceof ISynchronizedInterface && ((ISynchronizedInterface)tile).isSynced() && ((ISynchronizedInterface)tile).acceptCoordChanger()) {
            ISynchronizedInterface in;
            World cworld;
            boolean b;
            MovingObjectPosition p = Minecraft.func_71410_x().field_71451_h.func_70614_a(9999.0, 0.0f);
            boolean bl = b = p.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && p.field_72311_b == tile.field_145851_c && p.field_72312_c == tile.field_145848_d && p.field_72309_d == tile.field_145849_e;
            if (b && (cworld = (in = (ISynchronizedInterface)tile).getCoreWorld()) != null) {
                int cx = tile.field_145851_c + in.getCoreBlockXCoord();
                int cy = tile.field_145848_d + in.getCoreBlockYCoord();
                int cz = tile.field_145849_e + in.getCoreBlockZCoord();
                Block cblock = cworld.func_147439_a(tile.field_145851_c + in.getCoreBlockXCoord(), tile.field_145848_d + in.getCoreBlockYCoord(), tile.field_145849_e + in.getCoreBlockZCoord());
                if (cblock != null) {
                    ItemStack itemstack = UtilBuilder.getItemBlock(cworld, cx, cy, cz);
                    float ticktime = (float)tile.func_145831_w().func_72820_D() + partialTicktime;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
                    float f = 0.8f;
                    ForgeDirection direction = ForgeDirection.getOrientation((int)p.field_72310_e);
                    switch (direction) {
                        case UP: {
                            GL11.glTranslatef((float)0.0f, (float)f, (float)0.0f);
                            break;
                        }
                        case DOWN: {
                            GL11.glTranslatef((float)0.0f, (float)(-f), (float)0.0f);
                            break;
                        }
                        case NORTH: {
                            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(-f));
                            break;
                        }
                        case SOUTH: {
                            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)f);
                            break;
                        }
                        case WEST: {
                            GL11.glTranslatef((float)(-f), (float)0.0f, (float)0.0f);
                            break;
                        }
                        case EAST: {
                            GL11.glTranslatef((float)f, (float)0.0f, (float)0.0f);
                            break;
                        }
                    }
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)0.0f, (float)0.05f, (float)0.0f);
                    GL11.glRotatef((float)(ticktime * 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                    float f1 = 0.25f;
                    GL11.glScalef((float)f1, (float)f1, (float)f1);
                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)0.0f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.7f);
                    GL11.glEnable((int)32826);
                    GL11.glAlphaFunc((int)516, (float)0.0f);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
                    TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
                    texturemanager.func_110577_a(texturemanager.func_130087_a(itemstack.func_94608_d()));
                    RenderBlocks render = RenderBlocks.getInstance();
                    render.field_147844_c = false;
                    render.func_147800_a(cblock, itemstack.func_77960_j(), 0.0f);
                    GL11.glPopMatrix();
                    GL11.glDisable((int)2896);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glEnable((int)32826);
                    GL11.glAlphaFunc((int)516, (float)0.0f);
                    GL11.glEnable((int)3042);
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)0.0f, (float)0.4f, (float)0.0f);
                    GL11.glRotatef((float)(180.0f - RenderManager.field_78727_a.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glPushMatrix();
                    f1 = 0.01f;
                    GL11.glScalef((float)f1, (float)f1, (float)f1);
                    GL11.glDisable((int)2929);
                    InterfaceRenderer.drawString(itemstack.func_82833_r());
                    GL11.glEnable((int)2929);
                    GL11.glPopMatrix();
                    GL11.glTranslatef((float)0.0f, (float)-0.1f, (float)0.0f);
                    GL11.glPushMatrix();
                    f1 = 0.005f;
                    GL11.glScalef((float)f1, (float)f1, (float)f1);
                    GL11.glDisable((int)2929);
                    InterfaceRenderer.drawString("" + cx + "," + cy + "," + cz + ";" + cworld.field_73011_w.func_80007_l());
                    GL11.glEnable((int)2929);
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GL11.glEnable((int)2896);
                    GL11.glPopMatrix();
                    if (cworld.field_73011_w.field_76574_g == tile.func_145831_w().field_73011_w.field_76574_g) {
                        float cr = (float)(Math.sin((double)(ticktime * 0.1f) + 0.0) + 1.0) * 0.5f;
                        float cg = (float)(Math.sin((double)(ticktime * 0.1f) + 2.0943951023931953) + 1.0) * 0.5f;
                        float cb = (float)(Math.sin((double)(ticktime * 0.1f) + 4.1887902047863905) + 1.0) * 0.5f;
                        float cf = (float)(Math.sin((double)(ticktime * 0.12f) + 0.0) + 1.0) * 0.15f + 0.2f;
                        UtilRender.renderBox((double)cx + cblock.func_149704_x(), (double)cy + cblock.func_149665_z(), (double)cz + cblock.func_149706_B(), (double)cx + cblock.func_149753_y(), (double)cy + cblock.func_149669_A(), (double)cz + cblock.func_149693_C(), 2, cr, cg, cb, cf, false);
                        Tessellator tessellator = Tessellator.field_78398_a;
                        GL11.glDisable((int)3553);
                        GL11.glDisable((int)2929);
                        GL11.glAlphaFunc((int)516, (float)0.1f);
                        GL11.glEnable((int)3042);
                        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
                        tessellator.func_78371_b(1);
                        tessellator.func_78369_a(cr, cg, cb, cf);
                        tessellator.func_78377_a(x + 0.5, y + 0.5, z + 0.5);
                        tessellator.func_78377_a(x + (double)in.getCoreBlockXCoord() + 0.5, y + (double)in.getCoreBlockYCoord() + 0.5, z + (double)in.getCoreBlockZCoord() + 0.5);
                        tessellator.func_78381_a();
                        GL11.glEnable((int)2929);
                        GL11.glEnable((int)3553);
                    }
                    GL11.glAlphaFunc((int)516, (float)0.1f);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
                }
            }
        }
    }

    public static void drawString(String s) {
        GL11.glPushMatrix();
        GL11.glScalef((float)1.0f, (float)-1.0f, (float)1.0f);
        FontRenderer fontrenderer = RenderManager.field_78727_a.func_78716_a();
        int i = fontrenderer.func_78256_a(s);
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)0.1f, (float)1.0f);
        GL11.glDisable((int)3553);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(0.0f, 0.0f, 0.0f, 0.5f);
        tessellator.func_78377_a((double)(-(i /= 2) - 1), -1.0, 0.0);
        tessellator.func_78377_a((double)(-i - 1), 8.0, 0.0);
        tessellator.func_78377_a((double)(i + 1), 8.0, 0.0);
        tessellator.func_78377_a((double)(i + 1), -1.0, 0.0);
        tessellator.func_78381_a();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)32823);
        fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, -1);
        GL11.glPopMatrix();
    }
}

