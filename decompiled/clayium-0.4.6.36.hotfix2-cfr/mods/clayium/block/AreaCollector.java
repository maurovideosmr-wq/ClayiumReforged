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
import mods.clayium.block.tile.TileAreaCollector;
import net.minecraft.client.renderer.texture.IIconRegister;

public class AreaCollector
extends AreaMiner {
    public AreaCollector(int tier) {
        super(tier, "clayium:areacollector", TileAreaCollector.class, 16);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_energy");
        this.registerExtractIcons(par1IconRegister, "export");
    }
}

