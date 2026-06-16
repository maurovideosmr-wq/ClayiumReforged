/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block.tile;

import mods.clayium.block.ICAResonator;
import mods.clayium.block.tile.TileClayMachines;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class TileCAMachines
extends TileClayMachines {
    public double getResonance() {
        double res = 1.0;
        int range = 2;
        for (int xx = -range; xx <= range; ++xx) {
            for (int yy = -range; yy <= range; ++yy) {
                for (int zz = -range; zz <= range; ++zz) {
                    Block block = this.field_145850_b.func_147439_a(this.field_145851_c + xx, this.field_145848_d + yy, this.field_145849_e + zz);
                    if (!(block instanceof ICAResonator)) continue;
                    res *= ((ICAResonator)block).getResonance((IBlockAccess)this.field_145850_b, this.field_145851_c + xx, this.field_145848_d + yy, this.field_145849_e + zz);
                }
            }
        }
        return res;
    }
}

