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
import mods.clayium.block.tile.TileMultitrackBuffer;
import mods.clayium.util.UtilLocale;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class MultitrackBuffer
extends ClayNoRecipeMachines {
    public MultitrackBuffer(int tier) {
        this(tier, TileMultitrackBuffer.class);
    }

    public MultitrackBuffer(int tier, Class<? extends TileMultitrackBuffer> tileClass) {
        super(null, "", tier, tileClass, 2);
        this.guiId = 31;
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.MultitrackBuffer");
        ret.addAll(super.getTooltip(itemStack));
        return ret;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_m0", "import_m1", "import_m2", "import_m3", "import_m4", "import_m5", "import_m6");
        this.registerExtractIcons(par1IconRegister, "export_m0", "export_m1", "export_m2", "export_m3", "export_m4", "export_m5", "export_m6");
    }
}

