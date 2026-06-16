/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import java.util.List;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileClayLaserInterface;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ClayLaserInterface
extends ClayContainerTiered {
    protected String iconstr;

    public ClayLaserInterface(int tier) {
        this("clayium:laserinterface", TileClayLaserInterface.class, tier);
    }

    public ClayLaserInterface(String iconstr, Class<? extends TileClayLaserInterface> tileEntityClass, int tier) {
        super(Material.field_151573_f, tileEntityClass, 2, tier);
        this.iconstr = iconstr;
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.func_71045_bC();
        TileClayContainer te = (TileClayContainer)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te.isUsable(itemStack, player, side, hitX, hitY, hitZ)) {
            te.useItem(itemStack, player, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.FrontOverlayIcon = par1IconRegister.func_94245_a(this.iconstr);
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.LaserInterface");
        ret.addAll(super.getTooltip(itemStack));
        return ret;
    }
}

