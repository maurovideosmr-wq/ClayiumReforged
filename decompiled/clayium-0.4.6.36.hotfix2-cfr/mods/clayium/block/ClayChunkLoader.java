/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.IClayChunkLoader;
import mods.clayium.block.tile.TileClayChunkLoader;
import mods.clayium.util.UtilBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ClayChunkLoader
extends ClayContainerTiered {
    public ClayChunkLoader(int tier) {
        super(Material.field_151573_f, TileClayChunkLoader.class, 0, tier);
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(6.0f);
        this.func_149752_b(25.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te != null && te instanceof IClayChunkLoader) {
            ((IClayChunkLoader)te).releaseTicket();
        }
        super.func_149749_a(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        this.setSameIcons(par1IconRegister.func_94245_a("clayium:zk60ahull"));
        this.setSameOverlayIcons(par1IconRegister.func_94245_a("clayium:chunkloader"));
    }
}

