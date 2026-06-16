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
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileClayContainerTiered;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClayNoRecipeMachines
extends ClayContainerTiered {
    protected String iconstr;
    private String guititle;

    public ClayNoRecipeMachines(String guititle, String iconstr, String machineIconStr, int tier, Class<? extends TileClayContainer> tileEntityClass, int metaMode) {
        this(guititle, iconstr, machineIconStr, tier, tileEntityClass, 1, metaMode);
    }

    public ClayNoRecipeMachines(String guititle, String iconstr, int tier, Class<? extends TileClayContainer> tileEntityClass, int metaMode) {
        this(guititle, iconstr, tier, tileEntityClass, 1, metaMode);
    }

    public ClayNoRecipeMachines(String guititle, String iconstr, String machineIconStr, int tier, Class<? extends TileClayContainer> tileEntityClass, int guiId, int metaMode) {
        super(Material.field_151573_f, tileEntityClass, machineIconStr, guiId, metaMode, tier);
        this.initClayNoRecipeMachines(iconstr, guititle);
    }

    public ClayNoRecipeMachines(String guititle, String iconstr, int tier, Class<? extends TileClayContainer> tileEntityClass, int guiId, int metaMode) {
        super(Material.field_151573_f, tileEntityClass, guiId, metaMode, tier);
        this.initClayNoRecipeMachines(iconstr, guititle);
    }

    private void initClayNoRecipeMachines(String iconstr, String guititle) {
        this.iconstr = iconstr;
        this.guititle = guititle;
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public TileEntity func_149915_a(World world, int par2) {
        TileClayContainerTiered tile = (TileClayContainerTiered)super.func_149915_a(world, par2);
        tile.containerName(this.guititle);
        return tile;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        if (this.iconstr != null && !this.iconstr.equals("")) {
            this.FrontOverlayIcon = par1IconRegister.func_94245_a(this.iconstr);
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import", "import_energy");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

