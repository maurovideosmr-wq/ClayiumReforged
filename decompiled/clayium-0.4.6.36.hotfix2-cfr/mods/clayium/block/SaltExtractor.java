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
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.tile.TileSaltExtractor;
import mods.clayium.util.UtilLocale;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class SaltExtractor
extends ClayNoRecipeMachines {
    public SaltExtractor(int tier) {
        super(null, "clayium:saltextractor", tier, TileSaltExtractor.class, 2);
        this.guiId = 11;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_energy");
        this.registerExtractIcons(par1IconRegister, "export");
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.SaltExtractor");
        ret.addAll(super.getTooltip(itemStack));
        return ret;
    }
}

