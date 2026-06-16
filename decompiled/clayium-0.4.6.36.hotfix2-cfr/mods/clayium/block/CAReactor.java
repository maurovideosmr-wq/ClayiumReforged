/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayMachines;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.util.UtilBuilder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class CAReactor
extends ClayMachines {
    private IIcon[] FrontOverlayIcons;

    public CAReactor(String recipeId, String iconstr, int tier, Class<? extends TileClayContainer> tileEntityClass, int guiId) {
        super(recipeId, iconstr, tier, tileEntityClass, guiId);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.FrontOverlayIcons = new IIcon[]{this.FrontOverlayIcon, par1IconRegister.func_94245_a(this.iconstr + "_1")};
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public IIcon getOverlayIcon(IBlockAccess world, int x, int y, int z, int side) {
        if (UtilBuilder.safeGetTileEntity(world, x, y, z) instanceof TileCAReactor) {
            TileCAReactor te = (TileCAReactor)UtilBuilder.safeGetTileEntity(world, x, y, z);
            this.FrontOverlayIcon = this.FrontOverlayIcons[te.constructed ? 1 : 0];
        }
        IIcon iicon = super.getOverlayIcon(world, x, y, z, side);
        this.FrontOverlayIcon = this.FrontOverlayIcons[0];
        return iicon;
    }
}

