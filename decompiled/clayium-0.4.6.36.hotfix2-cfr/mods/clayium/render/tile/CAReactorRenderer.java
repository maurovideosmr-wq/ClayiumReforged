/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 */
package mods.clayium.render.tile;

import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilRender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class CAReactorRenderer
extends TileEntitySpecialRenderer {
    public void func_147500_a(TileEntity tile, double x, double y, double z, float p_147500_8_) {
        TileCAReactor tile1;
        if (tile instanceof TileCAReactor && ClayiumCore.cfgCAReactorGlittering && (tile1 = (TileCAReactor)tile).isDoingWork() && tile1.hullCoords != null) {
            UtilRender.setLightValue(15, 15);
            int j = 1;
            while ((double)j < tile1.reactorRank + 1.01) {
                for (int[] coord : tile1.hullCoords) {
                    int[] rel = tile1.getRelativeCoord(coord[0], coord[1], coord[2]);
                    float s = 1.0f;
                    float i = (float)tile1.reactorRank + 1.0f - (float)j;
                    float r = (float)j / ((float)tile1.reactorRank + 1.0f);
                    float f = 0.01f * ((float)Math.pow(i, 1.6) + 1.0f);
                    UtilRender.renderBox((float)rel[0] - f, (float)rel[1] - f, (float)rel[2] - f, (float)rel[0] + s + f, (float)rel[1] + s + f, (float)rel[2] + s + f, 1, 1.0f, 1.0f, 0.3f + 0.05f * (2.0f * r - r * r) * (float)j, 0.11f, false);
                }
                ++j;
            }
        }
    }
}

