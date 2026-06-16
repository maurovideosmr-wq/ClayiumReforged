/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block;

import java.util.List;
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.tile.TileClayBuffer;
import mods.clayium.util.UtilLocale;
import net.minecraft.item.ItemStack;

public class ClayBuffer
extends ClayNoRecipeMachines {
    public ClayBuffer(int tier) {
        this(tier, TileClayBuffer.class);
    }

    public ClayBuffer(int tier, Class<? extends TileClayBuffer> tileClass) {
        super(null, "", tier, tileClass, 2);
        this.guiId = 11;
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.Buffer");
        ret.addAll(super.getTooltip(itemStack));
        return ret;
    }
}

