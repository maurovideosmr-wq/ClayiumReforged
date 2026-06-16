/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mods.clayium.block.PANCable;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderPANCable
implements ISimpleBlockRenderingHandler {
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId == this.getRenderId()) {
            float o = PANCable.pipeWidth;
            UtilRender.renderInvCuboid(renderer, block, 0.5f - o, 0.5f - o, 0.5f - o, 0.5f + o, 0.5f + o, 0.5f + o, block.func_149691_a(metadata, 0));
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (modelId == this.getRenderId()) {
            UtilDirection[] directions;
            Tessellator tessellator = Tessellator.field_78398_a;
            float o = PANCable.pipeWidth;
            block.func_149676_a(0.5f - o, 0.5f - o, 0.5f - o, 0.5f + o, 0.5f + o, 0.5f + o);
            renderer.func_147775_a(block);
            renderer.func_147784_q(block, x, y, z);
            for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
                if (((PANCable)block).checkPipeConnection(world, x, y, z, direction)) {
                    block.func_149676_a(0.5f - o + (direction.offsetX == 1 ? o * 2.0f : 0.0f) - (direction.offsetX == -1 ? 0.5f - o : 0.0f), 0.5f - o + (direction.offsetY == 1 ? o * 2.0f : 0.0f) - (direction.offsetY == -1 ? 0.5f - o : 0.0f), 0.5f - o + (direction.offsetZ == 1 ? o * 2.0f : 0.0f) - (direction.offsetZ == -1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetX == -1 ? o * 2.0f : 0.0f) + (direction.offsetX == 1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetY == -1 ? o * 2.0f : 0.0f) + (direction.offsetY == 1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetZ == -1 ? o * 2.0f : 0.0f) + (direction.offsetZ == 1 ? 0.5f - o : 0.0f));
                    renderer.func_147775_a(block);
                    renderer.func_147784_q(block, x, y, z);
                }
                block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            return true;
        }
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClayiumCore.panCableRenderId;
    }
}

