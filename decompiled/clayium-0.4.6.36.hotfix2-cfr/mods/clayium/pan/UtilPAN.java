/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.pan;

import mods.clayium.block.IPANConductor;
import mods.clayium.core.ClayiumCore;
import mods.clayium.plugin.multipart.UtilMultipart;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class UtilPAN {
    public static boolean isPANConductor(IBlockAccess world, int x, int y, int z) {
        if (world == null) {
            return false;
        }
        if (y < 0 || y >= 255) {
            return false;
        }
        Block b = world.func_147439_a(x, y, z);
        if (UtilPAN.isPANConductor(b, x, y, z)) {
            return true;
        }
        return ClayiumCore.IntegrationID.MULTI_PART.loaded() && UtilMultipart.containsPANConductor(world, x, y, z);
    }

    public static boolean isPANConductor(Block block, int x, int y, int z) {
        return block instanceof IPANConductor;
    }
}

