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
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayBuffer;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.MultitrackBuffer;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderClayContainer
implements ISimpleBlockRenderingHandler {
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId == this.getRenderId()) {
            ((ClayContainer)block).setInitialBlockBounds();
            int metadata1 = ((ClayContainer)block).metaMode != 0 ? 3 + metadata / 16 * 16 : metadata;
            Tessellator tessellator = Tessellator.field_78398_a;
            renderer.func_147775_a(block);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            renderer.func_147775_a(block);
            tessellator.func_78382_b();
            tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
            renderer.func_147768_a(block, 0.0, 0.0, 0.0, block.func_149691_a(0, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(0, metadata1) != null) {
                renderer.func_147768_a(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(0, metadata1));
            }
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
            renderer.func_147806_b(block, 0.0, 0.0, 0.0, block.func_149691_a(1, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(1, metadata1) != null) {
                renderer.func_147806_b(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(1, metadata1));
            }
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78375_b(0.0f, 0.0f, -1.0f);
            renderer.func_147761_c(block, 0.0, 0.0, 0.0, block.func_149691_a(2, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(2, metadata1) != null) {
                renderer.func_147761_c(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(2, metadata1));
            }
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78375_b(0.0f, 0.0f, 1.0f);
            renderer.func_147734_d(block, 0.0, 0.0, 0.0, block.func_149691_a(3, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(3, metadata1) != null) {
                renderer.func_147734_d(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(3, metadata1));
            }
            if (block instanceof ClayBuffer || block instanceof MultitrackBuffer) {
                renderer.func_147734_d(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getInsertIcons(null, 0, 0, 0)[0]);
            }
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78375_b(-1.0f, 0.0f, 0.0f);
            renderer.func_147798_e(block, 0.0, 0.0, 0.0, block.func_149691_a(4, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(4, metadata1) != null) {
                renderer.func_147798_e(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(4, metadata1));
            }
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
            renderer.func_147764_f(block, 0.0, 0.0, 0.0, block.func_149691_a(5, metadata1));
            if (block instanceof ClayContainer && ((ClayContainer)block).getOverlayIcon(5, metadata1) != null) {
                renderer.func_147764_f(block, 0.0, 0.0, 0.0, ((ClayContainer)block).getOverlayIcon(5, metadata1));
            }
            tessellator.func_78381_a();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            renderer.func_147775_a(block);
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (modelId == this.getRenderId()) {
            if (block instanceof ClayContainer) {
                if (!((ClayContainer)block).renderAsPipe(world, x, y, z)) {
                    int route;
                    boolean io;
                    boolean underlay;
                    int renderMode;
                    Tessellator tessellator = Tessellator.field_78398_a;
                    int n = renderMode = ((ClayContainer)block).isTransparent() || ((ClayContainer)block).isOverlayTransparent() ? block.func_149701_w() : 2;
                    boolean bl = ClayContainer.renderPass == 0 ? !((ClayContainer)block).isTransparent() : (underlay = ((ClayContainer)block).isTransparent());
                    boolean overlay = ClayContainer.renderPass == 0 ? !((ClayContainer)block).isOverlayTransparent() : ((ClayContainer)block).isOverlayTransparent();
                    boolean bl2 = io = ClayContainer.renderPass == 0;
                    if (underlay) {
                        renderer.func_147784_q(block, x, y, z);
                    }
                    double d = 0.0;
                    if (renderMode == 1) {
                        d = 3.0E-4;
                    }
                    if (renderMode == 0) {
                        tessellator.func_78381_a();
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glAlphaFunc((int)516, (float)0.1f);
                        tessellator.func_78382_b();
                    }
                    boolean flag = false;
                    float f3 = 0.5f;
                    float f4 = 1.0f;
                    float f5 = 0.8f;
                    float f6 = 0.6f;
                    float f7 = f4;
                    float f8 = f4;
                    float f9 = f4;
                    float f10 = f3;
                    float f11 = f5;
                    float f12 = f6;
                    float f13 = f3;
                    float f14 = f5;
                    float f15 = f6;
                    float f16 = f3;
                    float f17 = f5;
                    float f18 = f6;
                    TileClayContainer te = UtilBuilder.safeGetTileEntity(world, x, y, z) instanceof TileClayContainer ? (TileClayContainer)UtilBuilder.safeGetTileEntity(world, x, y, z) : null;
                    Block block1 = block;
                    int l = block.func_149677_c(world, x, y, z);
                    IIcon[] insertIcons = ((ClayContainer)block1).getInsertIcons(world, x, y, z);
                    IIcon[] extractIcons = ((ClayContainer)block1).getExtractIcons(world, x, y, z);
                    IIcon filterIcon = ((ClayContainer)block1).FilterIcon;
                    tessellator.func_78380_c(renderer.field_147855_j > 0.0 ? l : block.func_149677_c(renderer.field_147845_a, x, y - 1, z));
                    tessellator.func_78386_a(f10, f13, f16);
                    tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 0) != null && overlay) {
                        renderer.func_147768_a(block, (double)x, (double)y - d, (double)z, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 0));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 0) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147768_a(block, (double)x, (double)y - d * 2.0, (double)z, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 0) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147768_a(block, (double)x, (double)y - d * 3.0, (double)z, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 0) - 6] != null) {
                            renderer.func_147768_a(block, (double)x, (double)y - d * 4.0, (double)z, filterIcon);
                        }
                    }
                    tessellator.func_78380_c(renderer.field_147857_k < 1.0 ? l : block.func_149677_c(renderer.field_147845_a, x, y + 1, z));
                    tessellator.func_78386_a(f7, f8, f9);
                    tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 1) != null && overlay) {
                        renderer.func_147806_b(block, (double)x, (double)y + d, (double)z, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 1));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 1) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147806_b(block, (double)x, (double)y + d * 2.0, (double)z, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 1) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147806_b(block, (double)x, (double)y + d * 3.0, (double)z, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 1) - 6] != null) {
                            renderer.func_147806_b(block, (double)x, (double)y + d * 4.0, (double)z, filterIcon);
                        }
                    }
                    tessellator.func_78380_c(renderer.field_147851_l > 0.0 ? l : block.func_149677_c(renderer.field_147845_a, x, y, z - 1));
                    tessellator.func_78386_a(f11, f14, f17);
                    tessellator.func_78375_b(0.0f, 0.0f, -1.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 2) != null && overlay) {
                        renderer.func_147761_c(block, (double)x, (double)y, (double)z - d, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 2));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 2) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147761_c(block, (double)x, (double)y, (double)z - d * 2.0, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 2) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147761_c(block, (double)x, (double)y, (double)z - d * 3.0, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 2) - 6] != null) {
                            renderer.func_147761_c(block, (double)x, (double)y, (double)z - d * 4.0, filterIcon);
                        }
                    }
                    tessellator.func_78380_c(renderer.field_147853_m < 1.0 ? l : block.func_149677_c(renderer.field_147845_a, x, y, z + 1));
                    tessellator.func_78386_a(f11, f14, f17);
                    tessellator.func_78375_b(0.0f, 0.0f, 1.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 3) != null && overlay) {
                        renderer.func_147734_d(block, (double)x, (double)y, (double)z + d, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 3));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 3) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147734_d(block, (double)x, (double)y, (double)z + d * 2.0, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 3) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147734_d(block, (double)x, (double)y, (double)z + d * 3.0, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 3) - 6] != null) {
                            renderer.func_147734_d(block, (double)x, (double)y, (double)z + d * 4.0, filterIcon);
                        }
                    }
                    tessellator.func_78380_c(renderer.field_147859_h > 0.0 ? l : block.func_149677_c(renderer.field_147845_a, x - 1, y, z));
                    tessellator.func_78386_a(f12, f15, f18);
                    tessellator.func_78375_b(-1.0f, 0.0f, 0.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 4) != null && overlay) {
                        renderer.func_147798_e(block, (double)x - d, (double)y, (double)z, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 4));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 4) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147798_e(block, (double)x - d * 2.0, (double)y, (double)z, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 4) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147798_e(block, (double)x - d * 3.0, (double)y, (double)z, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 4) - 6] != null) {
                            renderer.func_147798_e(block, (double)x - d * 4.0, (double)y, (double)z, filterIcon);
                        }
                    }
                    tessellator.func_78380_c(renderer.field_147861_i < 1.0 ? l : block.func_149677_c(renderer.field_147845_a, x + 1, y, z));
                    tessellator.func_78386_a(f12, f15, f18);
                    tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
                    if (((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 5) != null && overlay) {
                        renderer.func_147764_f(block, (double)x + d, (double)y, (double)z, ((ClayContainer)block).getOverlayIcon(renderer.field_147845_a, x, y, z, 5));
                    }
                    if (te != null && io) {
                        route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 5) - 6];
                        if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                            renderer.func_147764_f(block, (double)x + d * 2.0, (double)y, (double)z, insertIcons[route]);
                        }
                        route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), 5) - 6];
                        if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                            renderer.func_147764_f(block, (double)x + d * 3.0, (double)y, (double)z, extractIcons[route]);
                        }
                        if (filterIcon != null && te.filters[UtilDirection.direction2Side(te.getFrontDirection(), 5) - 6] != null) {
                            renderer.func_147764_f(block, (double)x + d * 4.0, (double)y, (double)z, filterIcon);
                        }
                    }
                    if (renderMode == 0) {
                        tessellator.func_78381_a();
                        GL11.glDisable((int)3042);
                        GL11.glAlphaFunc((int)516, (float)0.5f);
                        tessellator.func_78382_b();
                    }
                } else {
                    UtilDirection[] directions;
                    boolean pipingMode = ClayiumCore.proxy.renderAsPipingMode();
                    ClayContainer block1 = (ClayContainer)block;
                    TileClayContainer te = UtilBuilder.safeGetTileEntity(world, x, y, z) instanceof TileClayContainer ? (TileClayContainer)UtilBuilder.safeGetTileEntity(world, x, y, z) : null;
                    Tessellator tessellator = Tessellator.field_78398_a;
                    float o = ClayContainer.pipeWidth;
                    renderer.field_152631_f = true;
                    block.func_149676_a(0.5f - o, 0.5f - o, 0.5f - o, 0.5f + o, 0.5f + o, 0.5f + o);
                    renderer.func_147775_a(block);
                    renderer.func_147784_q(block, x, y, z);
                    IIcon[] insertIcons = block1.getInsertPipeIcons(world, x, y, z);
                    IIcon[] extractIcons = block1.getExtractPipeIcons(world, x, y, z);
                    for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
                        if (block1.checkPipeConnection(world, x, y, z, direction)) {
                            block.func_149676_a(0.5f - o + (direction.offsetX == 1 ? o * 2.0f : 0.0f) - (direction.offsetX == -1 ? 0.5f - o : 0.0f), 0.5f - o + (direction.offsetY == 1 ? o * 2.0f : 0.0f) - (direction.offsetY == -1 ? 0.5f - o : 0.0f), 0.5f - o + (direction.offsetZ == 1 ? o * 2.0f : 0.0f) - (direction.offsetZ == -1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetX == -1 ? o * 2.0f : 0.0f) + (direction.offsetX == 1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetY == -1 ? o * 2.0f : 0.0f) + (direction.offsetY == 1 ? 0.5f - o : 0.0f), 0.5f + o - (direction.offsetZ == -1 ? o * 2.0f : 0.0f) + (direction.offsetZ == 1 ? 0.5f - o : 0.0f));
                            renderer.func_147775_a(block);
                            renderer.func_147784_q(block, x, y, z);
                            if (pipingMode) {
                                int route = te.insertRoutes[UtilDirection.direction2Side(te.getFrontDirection(), direction.ordinal()) - 6];
                                if (insertIcons != null && route >= 0 && route < insertIcons.length) {
                                    renderer.func_147757_a(insertIcons[route]);
                                    renderer.func_147784_q(block, x, y, z);
                                }
                                route = te.extractRoutes[UtilDirection.direction2Side(te.getFrontDirection(), direction.ordinal()) - 6];
                                if (extractIcons != null && route >= 0 && route < extractIcons.length) {
                                    renderer.func_147757_a(extractIcons[route]);
                                    renderer.func_147784_q(block, x, y, z);
                                }
                                if (renderer.func_147744_b()) {
                                    renderer.func_147771_a();
                                }
                            }
                        }
                        block.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    }
                    renderer.field_152631_f = false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClayiumCore.clayContainerRenderId;
    }
}

