/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.storage.MapData
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.item;

import java.util.Random;
import mods.clayium.item.IItemExRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemDamagedRenderer
implements IItemRenderer {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private RenderManager renderManager = RenderManager.field_78727_a;
    private Random random = new Random();
    private RenderBlocks renderBlocksIr = new RenderBlocks();

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        switch (type) {
            case ENTITY: {
                return true;
            }
            case EQUIPPED: {
                return true;
            }
            case EQUIPPED_FIRST_PERSON: {
                return true;
            }
            case INVENTORY: {
                return true;
            }
            case FIRST_PERSON_MAP: {
                return false;
            }
        }
        return false;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        switch (helper) {
            case ENTITY_ROTATION: {
                return this.renderManager.field_78733_k.field_74347_j;
            }
            case ENTITY_BOBBING: {
                return true;
            }
            case EQUIPPED_BLOCK: {
                return true;
            }
            case BLOCK_3D: {
                return false;
            }
            case INVENTORY_BLOCK: {
                return false;
            }
        }
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object ... data) {
        byte b0 = 1;
        if (itemstack.field_77994_a > 1) {
            b0 = 2;
        }
        if (itemstack.field_77994_a > 5) {
            b0 = 3;
        }
        if (itemstack.field_77994_a > 20) {
            b0 = 4;
        }
        if (itemstack.field_77994_a > 40) {
            b0 = 5;
        }
        b0 = this.getMiniItemCount(itemstack, b0);
        switch (type) {
            case ENTITY: {
                RenderBlocks render = (RenderBlocks)data[0];
                EntityItem entity = (EntityItem)data[1];
                GL11.glTranslatef((float)0.0f, (float)-0.05f, (float)0.0f);
                Item item = itemstack.func_77973_b();
                if (item instanceof IItemExRenderer) {
                    ((IItemExRenderer)item).preRenderItem(type, itemstack, data);
                }
                for (int j = 0; j < item.getRenderPasses(itemstack.func_77960_j()); ++j) {
                    this.random.setSeed(187L);
                    IIcon iicon1 = item.getIcon(itemstack, j);
                    int k = item.func_82790_a(itemstack, j);
                    float f5 = (float)(k >> 16 & 0xFF) / 255.0f;
                    float f6 = (float)(k >> 8 & 0xFF) / 255.0f;
                    float f7 = (float)(k & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)1.0f);
                    if (item instanceof IItemExRenderer) {
                        ((IItemExRenderer)item).preRenderItemPass(type, itemstack, j, data);
                    }
                    this.renderDroppedItem(entity, iicon1, b0, 0.0f, f5, f6, f7, j);
                    if (!(item instanceof IItemExRenderer)) continue;
                    ((IItemExRenderer)item).postRenderItemPass(type, itemstack, j, data);
                }
                if (!(item instanceof IItemExRenderer)) break;
                ((IItemExRenderer)item).postRenderItem(type, itemstack, data);
                break;
            }
            case EQUIPPED: {
                RenderBlocks render1 = (RenderBlocks)data[0];
                EntityLivingBase entity1 = (EntityLivingBase)data[1];
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                Item item1 = itemstack.func_77973_b();
                if (item1 instanceof IItemExRenderer) {
                    ((IItemExRenderer)item1).preRenderItem(type, itemstack, data);
                }
                for (int x = 0; x < item1.getRenderPasses(itemstack.func_77960_j()); ++x) {
                    int k1 = item1.func_82790_a(itemstack, x);
                    float f10 = (float)(k1 >> 16 & 0xFF) / 255.0f;
                    float f11 = (float)(k1 >> 8 & 0xFF) / 255.0f;
                    float f12 = (float)(k1 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)(1.0f * f10), (float)(1.0f * f11), (float)(1.0f * f12), (float)1.0f);
                    if (item1 instanceof IItemExRenderer) {
                        ((IItemExRenderer)item1).preRenderItemPass(type, itemstack, x, data);
                    }
                    this.renderItem(entity1, itemstack, x, IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
                    if (!(item1 instanceof IItemExRenderer)) continue;
                    ((IItemExRenderer)item1).postRenderItemPass(type, itemstack, x, data);
                }
                if (!(item1 instanceof IItemExRenderer)) break;
                ((IItemExRenderer)item1).postRenderItem(type, itemstack, data);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                RenderBlocks render2 = (RenderBlocks)data[0];
                EntityLivingBase entity2 = (EntityLivingBase)data[1];
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                Item item2 = itemstack.func_77973_b();
                if (item2 instanceof IItemExRenderer) {
                    ((IItemExRenderer)item2).preRenderItem(type, itemstack, data);
                }
                for (int x = 0; x < item2.getRenderPasses(itemstack.func_77960_j()); ++x) {
                    int k1 = item2.func_82790_a(itemstack, x);
                    float f10 = (float)(k1 >> 16 & 0xFF) / 255.0f;
                    float f11 = (float)(k1 >> 8 & 0xFF) / 255.0f;
                    float f12 = (float)(k1 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)(1.0f * f10), (float)(1.0f * f11), (float)(1.0f * f12), (float)1.0f);
                    if (item2 instanceof IItemExRenderer) {
                        ((IItemExRenderer)item2).preRenderItemPass(type, itemstack, x, data);
                    }
                    this.renderItem(entity2, itemstack, x, IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
                    if (!(item2 instanceof IItemExRenderer)) continue;
                    ((IItemExRenderer)item2).postRenderItemPass(type, itemstack, x, data);
                }
                if (!(item2 instanceof IItemExRenderer)) break;
                ((IItemExRenderer)item2).postRenderItem(type, itemstack, data);
                break;
            }
            case INVENTORY: {
                RenderBlocks render3 = (RenderBlocks)data[0];
                Item item3 = itemstack.func_77973_b();
                TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
                RenderItem renderi = RenderItem.getInstance();
                renderi.field_77023_b = 50.0f;
                OpenGlHelper.func_148821_a((int)0, (int)0, (int)0, (int)0);
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)true);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78382_b();
                tessellator.func_78378_d(-1);
                tessellator.func_78377_a(-2.0, 18.0, 0.0);
                tessellator.func_78377_a(18.0, 18.0, 0.0);
                tessellator.func_78377_a(18.0, -2.0, 0.0);
                tessellator.func_78377_a(-2.0, -2.0, 0.0);
                tessellator.func_78381_a();
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)3008);
                if (item3 instanceof IItemExRenderer) {
                    ((IItemExRenderer)item3).preRenderItem(type, itemstack, data);
                }
                for (int l = 0; l < item3.getRenderPasses(itemstack.func_77960_j()); ++l) {
                    OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
                    int i1 = item3.func_82790_a(itemstack, l);
                    float f = (float)(i1 >> 16 & 0xFF) / 255.0f;
                    float f1 = (float)(i1 >> 8 & 0xFF) / 255.0f;
                    float f2 = (float)(i1 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f, (float)f1, (float)f2, (float)1.0f);
                    GL11.glDisable((int)2896);
                    GL11.glEnable((int)3008);
                    if (item3 instanceof IItemExRenderer) {
                        ((IItemExRenderer)item3).preRenderItemPass(type, itemstack, l, data);
                    }
                    texturemanager.func_110577_a(itemstack.func_94608_d() == 0 ? TextureMap.field_110575_b : TextureMap.field_110576_c);
                    IIcon iicon = item3.getIcon(itemstack, l);
                    if (iicon != null) {
                        renderi.func_94149_a(0, 0, iicon, 16, 16);
                    }
                    if (item3 instanceof IItemExRenderer) {
                        ((IItemExRenderer)item3).postRenderItemPass(type, itemstack, l, data);
                    }
                    GL11.glDisable((int)3008);
                    GL11.glEnable((int)2896);
                    if (!itemstack.hasEffect(l)) continue;
                    renderi.renderEffect(texturemanager, 0, 0);
                }
                if (!(item3 instanceof IItemExRenderer)) break;
                ((IItemExRenderer)item3).postRenderItem(type, itemstack, data);
                break;
            }
            case FIRST_PERSON_MAP: {
                EntityPlayer player = (EntityPlayer)data[0];
                TextureManager engine = (TextureManager)data[1];
                MapData mapData = (MapData)data[2];
            }
        }
    }

    private void renderDroppedItem(EntityItem p_77020_1_, IIcon p_77020_2_, int p_77020_3_, float p_77020_4_, float p_77020_5_, float p_77020_6_, float p_77020_7_, int pass) {
        Tessellator tessellator = Tessellator.field_78398_a;
        if (p_77020_2_ == null) {
            TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
            ResourceLocation resourcelocation = texturemanager.func_130087_a(p_77020_1_.func_92059_d().func_94608_d());
            p_77020_2_ = ((TextureMap)texturemanager.func_110581_b(resourcelocation)).func_110572_b("missingno");
        }
        float f14 = p_77020_2_.func_94209_e();
        float f15 = p_77020_2_.func_94212_f();
        float f4 = p_77020_2_.func_94206_g();
        float f5 = p_77020_2_.func_94210_h();
        float f6 = 1.0f;
        float f7 = 0.5f;
        float f8 = 0.25f;
        if (this.renderManager.field_78733_k.field_74347_j) {
            GL11.glPushMatrix();
            if (RenderItem.field_82407_g) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            float f9 = 0.0625f;
            float f10 = 0.021875f;
            ItemStack itemstack = p_77020_1_.func_92059_d();
            int j = itemstack.field_77994_a;
            int b0 = j < 2 ? 1 : (j < 16 ? 2 : (j < 32 ? 3 : 4));
            b0 = this.getMiniItemCount(itemstack, (byte)b0);
            GL11.glTranslatef((float)(-f7), (float)(-f8), (float)(-((f9 + f10) * (float)b0 / 2.0f)));
            for (int k = 0; k < b0; ++k) {
                if (k > 0 && this.shouldSpreadItems()) {
                    float x = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                    float y = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                    float z = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                    GL11.glTranslatef((float)x, (float)y, (float)(f9 + f10));
                } else {
                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(f9 + f10));
                }
                if (itemstack.func_94608_d() == 0) {
                    this.bindTexture(TextureMap.field_110575_b);
                } else {
                    this.bindTexture(TextureMap.field_110576_c);
                }
                GL11.glColor4f((float)p_77020_5_, (float)p_77020_6_, (float)p_77020_7_, (float)1.0f);
                ItemRenderer.func_78439_a((Tessellator)tessellator, (float)f15, (float)f4, (float)f14, (float)f5, (int)p_77020_2_.func_94211_a(), (int)p_77020_2_.func_94216_b(), (float)f9);
                if (!itemstack.hasEffect(pass)) continue;
                GL11.glDepthFunc((int)514);
                GL11.glDisable((int)2896);
                this.renderManager.field_78724_e.func_110577_a(RES_ITEM_GLINT);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)768, (int)1);
                float f11 = 0.76f;
                GL11.glColor4f((float)(0.5f * f11), (float)(0.25f * f11), (float)(0.8f * f11), (float)1.0f);
                GL11.glMatrixMode((int)5890);
                GL11.glPushMatrix();
                float f12 = 0.125f;
                GL11.glScalef((float)f12, (float)f12, (float)f12);
                float f13 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0f * 8.0f;
                GL11.glTranslatef((float)f13, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                ItemRenderer.func_78439_a((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)255, (int)255, (float)f9);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef((float)f12, (float)f12, (float)f12);
                f13 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0f * 8.0f;
                GL11.glTranslatef((float)(-f13), (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                ItemRenderer.func_78439_a((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)255, (int)255, (float)f9);
                GL11.glPopMatrix();
                GL11.glMatrixMode((int)5888);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2896);
                GL11.glDepthFunc((int)515);
            }
            GL11.glPopMatrix();
        } else {
            p_77020_5_ = (float)((double)p_77020_5_ / 1.4);
            p_77020_6_ = (float)((double)p_77020_6_ / 1.4);
            p_77020_7_ = (float)((double)p_77020_7_ / 1.4);
            for (int l = 0; l < p_77020_3_; ++l) {
                GL11.glPushMatrix();
                if (l > 0) {
                    float f10 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f16 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef((float)f10, (float)f16, (float)f17);
                }
                if (!RenderItem.field_82407_g) {
                    GL11.glRotatef((float)(180.0f - this.renderManager.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (p_77020_1_.func_92059_d().func_94608_d() == 0) {
                    this.bindTexture(TextureMap.field_110575_b);
                } else {
                    this.bindTexture(TextureMap.field_110576_c);
                }
                GL11.glColor4f((float)p_77020_5_, (float)p_77020_6_, (float)p_77020_7_, (float)1.0f);
                tessellator.func_78382_b();
                tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
                tessellator.func_78374_a((double)(0.0f - f7), (double)(0.0f - f8), 0.0, (double)f14, (double)f5);
                tessellator.func_78374_a((double)(f6 - f7), (double)(0.0f - f8), 0.0, (double)f15, (double)f5);
                tessellator.func_78374_a((double)(f6 - f7), (double)(1.0f - f8), 0.0, (double)f15, (double)f4);
                tessellator.func_78374_a((double)(0.0f - f7), (double)(1.0f - f8), 0.0, (double)f14, (double)f4);
                tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
                tessellator.func_78374_a((double)(0.0f - f7), (double)(1.0f - f8), 0.0, (double)f15, (double)f4);
                tessellator.func_78374_a((double)(f6 - f7), (double)(1.0f - f8), 0.0, (double)f14, (double)f4);
                tessellator.func_78374_a((double)(f6 - f7), (double)(0.0f - f8), 0.0, (double)f14, (double)f5);
                tessellator.func_78374_a((double)(0.0f - f7), (double)(0.0f - f8), 0.0, (double)f15, (double)f5);
                tessellator.func_78381_a();
                GL11.glPopMatrix();
            }
        }
    }

    protected void bindTexture(ResourceLocation p_110776_1_) {
        this.renderManager.field_78724_e.func_110577_a(p_110776_1_);
    }

    public byte getMiniItemCount(ItemStack stack, byte original) {
        return original;
    }

    public boolean shouldSpreadItems() {
        return true;
    }

    public void renderItem(EntityLivingBase p_78443_1_, ItemStack p_78443_2_, int p_78443_3_, IItemRenderer.ItemRenderType type) {
        GL11.glPushMatrix();
        TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
        IIcon iicon = p_78443_1_.func_70620_b(p_78443_2_, p_78443_3_);
        if (iicon == null) {
            GL11.glPopMatrix();
            return;
        }
        texturemanager.func_110577_a(texturemanager.func_130087_a(p_78443_2_.func_94608_d()));
        TextureUtil.func_152777_a((boolean)false, (boolean)false, (float)1.0f);
        Tessellator tessellator = Tessellator.field_78398_a;
        float f = iicon.func_94209_e();
        float f1 = iicon.func_94212_f();
        float f2 = iicon.func_94206_g();
        float f3 = iicon.func_94210_h();
        float f4 = 0.0f;
        float f5 = 0.3f;
        GL11.glEnable((int)32826);
        GL11.glTranslatef((float)(-f4), (float)(-f5), (float)0.0f);
        float f6 = 1.5f;
        GL11.glScalef((float)f6, (float)f6, (float)f6);
        GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)-0.9375f, (float)-0.0625f, (float)0.0f);
        ItemRenderer.func_78439_a((Tessellator)tessellator, (float)f1, (float)f2, (float)f, (float)f3, (int)iicon.func_94211_a(), (int)iicon.func_94216_b(), (float)0.0625f);
        if (p_78443_2_.hasEffect(p_78443_3_)) {
            GL11.glDepthFunc((int)514);
            GL11.glDisable((int)2896);
            texturemanager.func_110577_a(RES_ITEM_GLINT);
            GL11.glEnable((int)3042);
            OpenGlHelper.func_148821_a((int)768, (int)1, (int)1, (int)0);
            float f7 = 0.76f;
            GL11.glColor4f((float)(0.5f * f7), (float)(0.25f * f7), (float)(0.8f * f7), (float)1.0f);
            GL11.glMatrixMode((int)5890);
            GL11.glPushMatrix();
            float f8 = 0.125f;
            GL11.glScalef((float)f8, (float)f8, (float)f8);
            float f9 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0f * 8.0f;
            GL11.glTranslatef((float)f9, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            ItemRenderer.func_78439_a((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)f8, (float)f8, (float)f8);
            f9 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0f * 8.0f;
            GL11.glTranslatef((float)(-f9), (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            ItemRenderer.func_78439_a((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glDepthFunc((int)515);
        }
        GL11.glDisable((int)32826);
        texturemanager.func_110577_a(texturemanager.func_130087_a(p_78443_2_.func_94608_d()));
        TextureUtil.func_147945_b();
        GL11.glPopMatrix();
    }
}

