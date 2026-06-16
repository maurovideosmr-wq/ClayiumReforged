/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockLog
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.ITieredBlock;
import mods.clayium.core.ClayiumCore;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class ClayTreeLog
extends BlockLog
implements ITieredBlock {
    public static final String[] iconNames = new String[]{"claytree"};

    public ClayTreeLog() {
        this.func_149647_a(ClayiumCore.creativeTabClayium);
        this.func_149711_c(1.5f);
        this.func_149672_a(field_149767_g);
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister p_149651_1_) {
        this.field_150167_a = new IIcon[iconNames.length];
        this.field_150166_b = new IIcon[iconNames.length];
        for (int i = 0; i < this.field_150167_a.length; ++i) {
            this.field_150167_a[i] = p_149651_1_.func_94245_a(this.func_149641_N() + "_" + iconNames[i]);
            this.field_150166_b[i] = p_149651_1_.func_94245_a(this.func_149641_N() + "_" + iconNames[i] + "_top");
        }
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 7;
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 7;
    }
}

