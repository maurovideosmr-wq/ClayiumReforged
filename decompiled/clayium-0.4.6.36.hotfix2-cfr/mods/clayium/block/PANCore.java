/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import mods.clayium.block.ClayContainer;
import mods.clayium.block.IPANConductor;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.tile.TilePANCore;
import mods.clayium.util.UtilBuilder;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PANCore
extends ClayContainer
implements IPANConductor,
ITieredBlock {
    public PANCore() {
        super(Material.field_151573_f, TilePANCore.class, "clayium:pancore", 41, 1);
        this.func_149711_c(4.0f).func_149752_b(4.0f);
    }

    @Override
    protected boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof TilePANCore) {
            ((TilePANCore)te).debug(player);
        }
        return super.onBlockRightClicked(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 11;
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 11;
    }
}

