/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.tile.TileLaserReflector;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilDirection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LaserReflector
extends ClayContainer
implements ITieredBlock {
    public LaserReflector() {
        super(Material.field_151573_f, TileLaserReflector.class, 2);
        this.func_149672_a(Block.field_149778_k);
        this.preventFirstPass();
    }

    @Override
    public int func_149645_b() {
        return ClayiumCore.laserReflectorRenderId;
    }

    @Override
    public boolean func_149686_d() {
        return false;
    }

    @Override
    public boolean func_149662_c() {
        return false;
    }

    @Override
    protected boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 7;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        this.setSameIcons(par1IconRegister.func_94245_a("clayium:laserreflector"));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public int func_149701_w() {
        return 1;
    }

    @Override
    public void func_149719_a(IBlockAccess world, int x, int y, int z) {
        float f = 0.125f;
        switch (UtilDirection.getOrientation(world.func_72805_g(x, y, z))) {
            case UP: 
            case DOWN: {
                this.func_149676_a(0.0f + f * 2.0f, 0.0f + f, 0.0f + f * 2.0f, 1.0f - f * 2.0f, 1.0f - f, 1.0f - f * 2.0f);
                break;
            }
            case NORTH: 
            case SOUTH: {
                this.func_149676_a(0.0f + f * 2.0f, 0.0f + f * 2.0f, 0.0f + f, 1.0f - f * 2.0f, 1.0f - f * 2.0f, 1.0f - f);
                break;
            }
            case EAST: 
            case WEST: {
                this.func_149676_a(0.0f + f, 0.0f + f * 2.0f, 0.0f + f * 2.0f, 1.0f - f, 1.0f - f * 2.0f, 1.0f - f * 2.0f);
                break;
            }
            default: {
                this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
        return super.getNormalSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 7;
    }
}

