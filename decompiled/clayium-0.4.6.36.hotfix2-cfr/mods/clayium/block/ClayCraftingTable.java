/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.tile.TileClayCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ClayCraftingTable
extends ClayContainerTiered {
    public ClayCraftingTable(int tier) {
        super(Material.field_151571_B, TileClayCraftingTable.class, "clayium:compressedclay-0", 30, 0, tier);
        this.func_149672_a(Block.field_149767_g);
        this.func_149711_c(1.0f);
        this.func_149752_b(4.0f);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        this.UpOverlayIcon = par1IconRegister.func_94245_a("clayium:claycraftingtable");
        super.func_149651_a(par1IconRegister);
    }

    @Override
    public boolean func_149662_c() {
        return false;
    }

    @Override
    public boolean func_149686_d() {
        return false;
    }

    @Override
    public void setInitialBlockBounds() {
        this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
    }
}

