/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayMachines;
import mods.clayium.block.tile.TileClayChemicalReactor;
import mods.clayium.block.tile.TileClayContainer;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ClayChemicalReactor
extends ClayMachines {
    public ClayChemicalReactor(String recipeId, String iconstr, int tier) {
        this(recipeId, iconstr, tier, TileClayChemicalReactor.class);
    }

    public ClayChemicalReactor(String recipeId, String iconstr, int tier, Class<? extends TileClayContainer> tileEntityClass) {
        this(recipeId, null, iconstr, tier, tileEntityClass, 4, 1);
    }

    public ClayChemicalReactor(String recipeId, String guititle, String iconstr, int tier, Class<? extends TileClayContainer> tileEntityClass, int guiId, int metaMode) {
        super(recipeId, guititle, iconstr, tier, tileEntityClass, guiId, metaMode);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_1", "import_2", "import", "import_energy");
        this.registerExtractIcons(par1IconRegister, "export_1", "export_2", "export");
    }
}

