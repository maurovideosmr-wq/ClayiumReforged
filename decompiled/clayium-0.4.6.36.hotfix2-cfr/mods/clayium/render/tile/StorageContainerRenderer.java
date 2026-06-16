/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraftforge.client.MinecraftForgeClient
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.tile;

import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilLocale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class StorageContainerRenderer
extends TileEntitySpecialRenderer {
    public void func_147500_a(TileEntity tile, double x, double y, double z, float partialTicktime) {
        if (tile != null && tile instanceof TileStorageContainer) {
            ItemStack itemstack = ((TileStorageContainer)tile).containerItemStacks[0];
            int stackSize = 0;
            if (itemstack == null) {
                itemstack = ((TileStorageContainer)tile).containerItemStacks[1];
            } else {
                stackSize = itemstack.field_77994_a;
            }
            if (itemstack != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
                UtilDirection direction = UtilDirection.getOrientation(((TileStorageContainer)tile).getFrontDirection());
                switch (direction) {
                    case NORTH: {
                        GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                        break;
                    }
                    case SOUTH: {
                        GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                        break;
                    }
                    case WEST: {
                        GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                        break;
                    }
                    case EAST: {
                        GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                        break;
                    }
                }
                if (MinecraftForgeClient.getRenderPass() == 0) {
                    EntityItem entityitem = new EntityItem(tile.func_145831_w(), 0.0, 0.0, 0.0, itemstack.func_77946_l());
                    entityitem.func_92059_d().field_77994_a = 1;
                    entityitem.field_70290_d = 0.0f;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)0.0f, (float)-0.03f, (float)0.51f);
                    RenderItem.field_82407_g = true;
                    RenderManager.field_78727_a.func_147940_a((Entity)entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                    RenderItem.field_82407_g = false;
                    GL11.glPopMatrix();
                }
                if (MinecraftForgeClient.getRenderPass() == 1) {
                    boolean b;
                    String s = String.valueOf(UtilLocale.StackSizeNumeral(stackSize));
                    FontRenderer fontrenderer = RenderManager.field_78727_a.func_78716_a();
                    GL11.glPushMatrix();
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.15f, (float)-0.55f);
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    float f = 1.6f;
                    float f1 = 0.016666668f * f;
                    GL11.glScalef((float)(-f1), (float)(-f1), (float)f1);
                    GL11.glDisable((int)2896);
                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.01f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, -603979776);
                    MovingObjectPosition p = Minecraft.func_71410_x().field_71451_h.func_70614_a(9999.0, 0.0f);
                    boolean bl = b = p.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && p.field_72311_b == tile.field_145851_c && p.field_72312_c == tile.field_145848_d && p.field_72309_d == tile.field_145849_e;
                    if (ClayiumCore.proxy.renderAsPipingMode() || b) {
                        GL11.glTranslatef((float)0.0f, (float)-20.0f, (float)-5.0f);
                        f1 = 0.5f;
                        GL11.glScalef((float)f1, (float)f1, (float)f1);
                        s = itemstack.func_82833_r();
                        int i = fontrenderer.func_78256_a(s);
                        float f2 = i > 64 ? 64.0f / (float)i : 1.0f;
                        GL11.glScalef((float)f2, (float)1.0f, (float)1.0f);
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glEnable((int)32823);
                        GL11.glPolygonOffset((float)0.1f, (float)1.0f);
                        Tessellator tessellator = Tessellator.field_78398_a;
                        GL11.glDisable((int)3553);
                        tessellator.func_78382_b();
                        tessellator.func_78369_a(0.0f, 0.0f, 0.0f, 0.5f);
                        tessellator.func_78377_a((double)(-(i /= 2) - 1), -1.0, 0.0);
                        tessellator.func_78377_a((double)(-i - 1), 8.0, 0.0);
                        tessellator.func_78377_a((double)(i + 1), 8.0, 0.0);
                        tessellator.func_78377_a((double)(i + 1), -1.0, 0.0);
                        tessellator.func_78381_a();
                        GL11.glDisable((int)32823);
                        GL11.glEnable((int)3553);
                        fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, -1);
                    }
                    GL11.glEnable((int)2896);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            }
        }
    }
}

