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
import mods.clayium.block.tile.TileAutoTrader;
import net.minecraft.client.renderer.texture.IIconRegister;

public class AutoTrader
extends ClayNoRecipeMachines {
    public AutoTrader(int tier) {
        super(null, null, "clayium:az91dhull", tier, TileAutoTrader.class, 1);
        this.guiId = 19;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.UpOverlayIcon = par1IconRegister.func_94245_a("clayium:autotradertop");
        this.RightOverlayIcon = this.LeftOverlayIcon = par1IconRegister.func_94245_a("clayium:autotraderside");
        this.BackOverlayIcon = this.LeftOverlayIcon;
        this.FrontOverlayIcon = this.LeftOverlayIcon;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_1", "import_2", "import", "import_energy");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

