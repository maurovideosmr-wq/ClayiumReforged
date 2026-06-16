/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.filter.ItemFilterString;
import net.minecraft.item.ItemStack;

public class ItemFilterItemDamage
extends ItemFilterString {
    @Override
    public boolean filterStringMatch(String filterString, ItemStack itemstack) {
        String filter = "^" + filterString + "$";
        if (itemstack == null || itemstack.func_77973_b() == null) {
            return false;
        }
        String name = String.valueOf(itemstack.func_77960_j());
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

