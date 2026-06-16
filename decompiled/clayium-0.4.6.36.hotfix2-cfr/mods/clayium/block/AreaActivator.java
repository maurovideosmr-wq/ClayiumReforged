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
import mods.clayium.block.AreaMiner;
import mods.clayium.block.tile.TileAreaActivator;
import net.minecraft.client.renderer.texture.IIconRegister;

public class AreaActivator
extends AreaMiner {
    public AreaActivator(int tier) {
        super(tier, "clayium:areaactivator", TileAreaActivator.class, 24);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import", "import_energy");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

