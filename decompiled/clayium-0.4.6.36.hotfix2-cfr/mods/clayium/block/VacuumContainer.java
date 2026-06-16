/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.tile.TileVacuumContainer;
import mods.clayium.gui.TextureExtra;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class VacuumContainer
extends ClayContainer
implements ITieredBlock {
    public VacuumContainer(Material material, String iconstr) {
        super(material, TileVacuumContainer.class, iconstr, 1);
        this.guiId = 22;
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.LeftIcon = this.RightIcon = new TextureExtra("clayium:vacuumcontainerside_", this.machineIconStr, "clayium:vacuumcontainerside").register(par1IconRegister);
        this.BackIcon = this.RightIcon;
        this.FrontIcon = new TextureExtra("clayium:vacuumcontainer_", this.machineIconStr, "clayium:vacuumcontainer").register(par1IconRegister);
        this.UpIcon = new TextureExtra("clayium:vacuumcontainertop_", this.machineIconStr, "clayium:vacuumcontainertop").register(par1IconRegister);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import");
        this.registerExtractIcons(par1IconRegister, "export");
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 6;
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 6;
    }
}

