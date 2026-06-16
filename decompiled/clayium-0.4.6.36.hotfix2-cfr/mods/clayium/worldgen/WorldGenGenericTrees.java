/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSapling
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.Direction
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.gen.feature.WorldGenAbstractTree
 *  net.minecraftforge.common.IPlantable
 *  net.minecraftforge.common.util.ForgeDirection
 */
package mods.clayium.worldgen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenGenericTrees
extends WorldGenAbstractTree {
    private final int minTreeHeight;
    private final boolean vinesGrow;
    private final int metaWood;
    private final int metaLeaves;
    private final Block blockWood;
    private final Block blockLeaves;
    private static final String __OBFID = "CL_00000438";

    public WorldGenGenericTrees(boolean notify, int minTreeHeight, Block blockWood, int metaWood, Block blockLeaves, int metaLeaves, boolean vinesGrow) {
        super(notify);
        this.minTreeHeight = minTreeHeight;
        this.blockWood = blockWood;
        this.metaWood = metaWood;
        this.blockLeaves = blockLeaves;
        this.metaLeaves = metaLeaves;
        this.vinesGrow = vinesGrow;
    }

    public boolean func_76484_a(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(3) + this.minTreeHeight;
        boolean flag = true;
        if (y >= 1 && y + l + 1 <= 256) {
            Block block;
            int k1;
            int b0;
            for (int i1 = y; i1 <= y + 1 + l; ++i1) {
                b0 = 1;
                if (i1 == y) {
                    b0 = 0;
                }
                if (i1 >= y + 1 + l - 2) {
                    b0 = 2;
                }
                for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
                        if (i1 >= 0 && i1 < 256) {
                            block = world.func_147439_a(j1, i1, k1);
                            if (this.isReplaceable(world, j1, i1, k1)) continue;
                            flag = false;
                            continue;
                        }
                        flag = false;
                    }
                }
            }
            if (!flag) {
                return false;
            }
            Block block2 = world.func_147439_a(x, y - 1, z);
            boolean isSoil = block2.canSustainPlant((IBlockAccess)world, x, y - 1, z, ForgeDirection.UP, (IPlantable)((BlockSapling)Blocks.field_150345_g));
            if (isSoil && y < 256 - l - 1) {
                int j2;
                int i2;
                int l1;
                int i3;
                block2.onPlantGrow(world, x, y - 1, z, x, y, z);
                b0 = 3;
                int b1 = 0;
                for (k1 = y - b0 + l; k1 <= y + l; ++k1) {
                    i3 = k1 - (y + l);
                    l1 = b1 + 1 - i3 / 2;
                    for (i2 = x - l1; i2 <= x + l1; ++i2) {
                        j2 = i2 - x;
                        for (int k2 = z - l1; k2 <= z + l1; ++k2) {
                            Block block1;
                            int l2 = k2 - z;
                            if (Math.abs(j2) == l1 && Math.abs(l2) == l1 && (random.nextInt(2) == 0 || i3 == 0) || !(block1 = world.func_147439_a(i2, k1, k2)).isAir((IBlockAccess)world, i2, k1, k2) && !block1.isLeaves((IBlockAccess)world, i2, k1, k2)) continue;
                            this.func_150516_a(world, i2, k1, k2, this.blockLeaves, this.metaLeaves);
                        }
                    }
                }
                for (k1 = 0; k1 < l; ++k1) {
                    block = world.func_147439_a(x, y + k1, z);
                    if (!block.isAir((IBlockAccess)world, x, y + k1, z) && !block.isLeaves((IBlockAccess)world, x, y + k1, z)) continue;
                    this.func_150516_a(world, x, y + k1, z, this.blockWood, this.metaWood);
                    if (!this.vinesGrow || k1 <= 0) continue;
                    if (random.nextInt(3) > 0 && world.func_147437_c(x - 1, y + k1, z)) {
                        this.func_150516_a(world, x - 1, y + k1, z, Blocks.field_150395_bd, 8);
                    }
                    if (random.nextInt(3) > 0 && world.func_147437_c(x + 1, y + k1, z)) {
                        this.func_150516_a(world, x + 1, y + k1, z, Blocks.field_150395_bd, 2);
                    }
                    if (random.nextInt(3) > 0 && world.func_147437_c(x, y + k1, z - 1)) {
                        this.func_150516_a(world, x, y + k1, z - 1, Blocks.field_150395_bd, 1);
                    }
                    if (random.nextInt(3) <= 0 || !world.func_147437_c(x, y + k1, z + 1)) continue;
                    this.func_150516_a(world, x, y + k1, z + 1, Blocks.field_150395_bd, 4);
                }
                if (this.vinesGrow) {
                    for (k1 = y - 3 + l; k1 <= y + l; ++k1) {
                        i3 = k1 - (y + l);
                        l1 = 2 - i3 / 2;
                        for (i2 = x - l1; i2 <= x + l1; ++i2) {
                            for (j2 = z - l1; j2 <= z + l1; ++j2) {
                                if (!world.func_147439_a(i2, k1, j2).isLeaves((IBlockAccess)world, i2, k1, j2)) continue;
                                if (random.nextInt(4) == 0 && world.func_147439_a(i2 - 1, k1, j2).isAir((IBlockAccess)world, i2 - 1, k1, j2)) {
                                    this.growVines(world, i2 - 1, k1, j2, 8);
                                }
                                if (random.nextInt(4) == 0 && world.func_147439_a(i2 + 1, k1, j2).isAir((IBlockAccess)world, i2 + 1, k1, j2)) {
                                    this.growVines(world, i2 + 1, k1, j2, 2);
                                }
                                if (random.nextInt(4) == 0 && world.func_147439_a(i2, k1, j2 - 1).isAir((IBlockAccess)world, i2, k1, j2 - 1)) {
                                    this.growVines(world, i2, k1, j2 - 1, 1);
                                }
                                if (random.nextInt(4) != 0 || !world.func_147439_a(i2, k1, j2 + 1).isAir((IBlockAccess)world, i2, k1, j2 + 1)) continue;
                                this.growVines(world, i2, k1, j2 + 1, 4);
                            }
                        }
                    }
                    if (random.nextInt(5) == 0 && l > 5) {
                        for (k1 = 0; k1 < 2; ++k1) {
                            for (i3 = 0; i3 < 4; ++i3) {
                                if (random.nextInt(4 - k1) != 0) continue;
                                l1 = random.nextInt(3);
                                this.func_150516_a(world, x + Direction.field_71583_a[Direction.field_71580_e[i3]], y + l - 5 + k1, z + Direction.field_71581_b[Direction.field_71580_e[i3]], Blocks.field_150375_by, l1 << 2 | i3);
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void growVines(World world, int x, int starty, int z, int vinelength) {
        this.func_150516_a(world, x, starty, z, Blocks.field_150395_bd, vinelength);
        int i1 = 4;
        while (world.func_147439_a(x, --starty, z).isAir((IBlockAccess)world, x, starty, z) && i1 > 0) {
            this.func_150516_a(world, x, starty, z, Blocks.field_150395_bd, vinelength);
            --i1;
        }
        return;
    }
}

