/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package mods.clayium.item.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.filter.ItemFilterItemDamage;
import net.minecraft.world.World;

public class ItemFilterBlockMetadata
extends ItemFilterItemDamage {
    @Override
    public boolean shouldApplySpecialPatternForBlock(String filterString, World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean filterStringMatch(String filterString, World world, int x, int y, int z) {
        String filter = "^" + filterString + "$";
        if (world == null) {
            return false;
        }
        String name = String.valueOf(world.func_72805_g(x, y, z));
        try {
            Pattern pattern = Pattern.compile(filter);
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        }
        catch (PatternSyntaxException e) {
            ClayiumCore.logger.error("Illegal Pattern! \n" + e.getMessage());
            return false;
        }
    }
}

