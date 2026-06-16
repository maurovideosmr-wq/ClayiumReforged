/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.misc;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.core.ClayiumCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderFluidTab
implements ISimpleBlockRenderingHandler {
    private IIcon boxIIcon;

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        this.boxIIcon = Blocks.field_150344_f.func_149733_h(1);
        if (modelID == this.getRenderId()) {
            this.renderInvCuboid(renderer, block, 0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f, this.boxIIcon);
            this.renderInvCuboid(renderer, block, 0.0f, 0.125f, 0.0f, 1.0f, 1.0f, 0.0625f, this.boxIIcon);
            this.renderInvCuboid(renderer, block, 0.0f, 0.125f, 0.9375f, 1.0f, 1.0f, 1.0f, this.boxIIcon);
            this.renderInvCuboid(renderer, block, 0.0f, 0.125f, 0.0625f, 0.0625f, 1.0f, 0.9375f, this.boxIIcon);
            this.renderInvCuboid(renderer, block, 0.9375f, 0.125f, 0.0625f, 1.0f, 1.0f, 0.9375f, this.boxIIcon);
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        this.boxIIcon = Blocks.field_150344_f.func_149733_h(1);
        if (modelId == this.getRenderId()) {
            renderer.func_147757_a(this.boxIIcon);
            block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            renderer.func_147757_a(this.boxIIcon);
            block.func_149676_a(0.0f, 0.125f, 0.0f, 1.0f, 1.0f, 0.0625f);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            renderer.func_147757_a(this.boxIIcon);
            block.func_149676_a(0.0f, 0.125f, 0.9375f, 1.0f, 1.0f, 1.0f);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            renderer.func_147757_a(this.boxIIcon);
            block.func_149676_a(0.0f, 0.125f, 0.0625f, 0.0625f, 1.0f, 0.9375f);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            renderer.func_147757_a(this.boxIIcon);
            block.func_149676_a(0.9375f, 0.125f, 0.0625f, 1.0f, 1.0f, 0.9375f);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            renderer.func_147771_a();
            block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            renderer.func_147775_a(block);
            return true;
        }
        return false;
    }

    public boolean shouldRender3DInInventory(int a) {
        return true;
    }

    public int getRenderId() {
        return ClayiumCore.fluidTabRenderId;
    }

    private void renderInvCuboid(RenderBlocks renderer, Block block, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, IIcon icon) {
        Tessellator tessellator = Tessellator.field_78398_a;
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
        block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.func_147775_a(block);
    }
}

