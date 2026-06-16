/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item.filter;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.filter.ItemFilterString;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFilterModID
extends ItemFilterString {
    @Override
    public boolean filterStringMatch(String filterString, ItemStack itemstack) {
        String filter = filterString;
        if (itemstack == null || itemstack.func_77973_b() == null) {
            return false;
        }
        String name = itemstack.func_77973_b() instanceof ItemBlock ? GameRegistry.findUniqueIdentifierFor((Block)((ItemBlock)itemstack.func_77973_b()).field_150939_a).modId : GameRegistry.findUniqueIdentifierFor((Item)itemstack.func_77973_b()).modId;
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

