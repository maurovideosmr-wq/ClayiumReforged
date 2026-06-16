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
import mods.clayium.util.UtilItemStack;
import net.minecraft.item.ItemStack;

public class ItemFilterOreDict
extends ItemFilterString {
    @Override
    public boolean filterStringMatch(String filterString, ItemStack itemstack) {
        String[] orenames;
        String filter = filterString;
        for (String orename : orenames = UtilItemStack.getOreNames(itemstack)) {
            try {
                Pattern pattern = Pattern.compile(filter);
                Matcher matcher = pattern.matcher(orename);
                if (!matcher.find()) continue;
                return true;
            }
            catch (PatternSyntaxException e) {
                ClayiumCore.logger.error("Illegal Pattern! \n" + e.getMessage());
                return false;
            }
        }
        return false;
    }
}

