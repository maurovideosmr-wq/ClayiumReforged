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
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.tile.TileSolarClayFabricator;
import net.minecraft.client.renderer.texture.IIconRegister;

public class SolarClayFabricator
extends ClayNoRecipeMachines {
    public SolarClayFabricator(String guititle, int tier) {
        super(guititle, "", tier, TileSolarClayFabricator.class, 2);
        this.guiId = 1;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.UpOverlayIcon = par1IconRegister.func_94245_a("clayium:solar");
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

