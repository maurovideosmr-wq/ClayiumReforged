/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import java.util.List;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.tile.TileRedstoneInterface;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RedstoneInterface
extends ClayContainerTiered {
    public RedstoneInterface(int tier) {
        super(Material.field_151573_f, TileRedstoneInterface.class, 0, tier);
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    protected boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        String state;
        world.func_147471_g(x, y, z);
        TileRedstoneInterface te1 = (TileRedstoneInterface)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        String string = state = player.func_70093_af() ? te1.getState() : te1.changeState();
        if (!world.field_72995_K) {
            player.func_145747_a((IChatComponent)new ChatComponentText(state));
        }
        return true;
    }

    @Override
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.setSameOverlayIcons(par1IconRegister.func_94245_a("clayium:redstoneinterface"));
    }

    public boolean func_149744_f() {
        return true;
    }

    public int func_149709_b(IBlockAccess world, int x, int y, int z, int direction) {
        TileRedstoneInterface te = (TileRedstoneInterface)UtilBuilder.safeGetTileEntity(world, x, y, z);
        return te.getProvidingWeakPower();
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.RedstoneInterface");
        ret.addAll(super.getTooltip(itemStack));
        return ret;
    }
}

