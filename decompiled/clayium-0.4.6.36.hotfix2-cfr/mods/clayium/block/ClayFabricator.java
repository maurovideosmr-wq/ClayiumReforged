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
import mods.clayium.block.tile.TileClayFabricator;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ClayFabricator
extends ClayNoRecipeMachines {
    public ClayFabricator(int tier) {
        super(null, "clayium:clayfabricator", tier, TileClayFabricator.class, 1);
        this.guiId = 1;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

