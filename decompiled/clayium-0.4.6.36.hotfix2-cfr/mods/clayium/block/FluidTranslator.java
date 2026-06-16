/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.ClayBuffer;
import mods.clayium.block.tile.TileFluidTranslator;
import mods.clayium.util.UtilLocale;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class FluidTranslator
extends ClayBuffer {
    public FluidTranslator(int tier) {
        super(tier, TileFluidTranslator.class);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_l", "import");
        this.registerExtractIcons(par1IconRegister, "export_l", "export");
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.FluidTranslator");
        return ret;
    }
}

