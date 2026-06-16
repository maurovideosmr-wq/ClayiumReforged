/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.NEIServerUtils
 *  codechicken.nei.PositionedStack
 *  codechicken.nei.recipe.TemplateRecipeHandler
 *  codechicken.nei.recipe.TemplateRecipeHandler$CachedRecipe
 *  codechicken.nei.recipe.TemplateRecipeHandler$RecipeTransferRect
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.plugin.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import mods.clayium.gui.client.GuiClayWorkTable;
import mods.clayium.item.CItems;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.ClayWorkTableRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NEIClayWorkTable
extends TemplateRecipeHandler {
    protected static ItemStack[][] tools = new ItemStack[][]{null, {null}, {null}, {new ItemStack(CItems.itemClayRollingPin)}, {new ItemStack(CItems.itemClaySlicer), new ItemStack(CItems.itemClaySpatula)}, {new ItemStack(CItems.itemClaySpatula)}, {new ItemStack(CItems.itemClaySlicer), new ItemStack(CItems.itemClaySpatula)}};

    public void loadCraftingRecipes(String outputId, Object ... results) {
        if (outputId == null || outputId.equals(this.getOverlayIdentifier())) {
            for (Map.Entry entry : ClayWorkTableRecipes.smelting().kneadingList.entrySet()) {
                ItemStack input = (ItemStack)((Map)entry.getKey()).get("Material");
                int method = (Integer)((Map)entry.getKey()).get("ButtonId");
                ItemStack result1 = (ItemStack)((Map)entry.getValue()).get("Result");
                ItemStack result2 = (ItemStack)((Map)entry.getValue()).get("Result2");
                int cooktime = (Integer)((Map)entry.getValue()).get("CookTime");
                recipeCacher arecipe = new recipeCacher(input, method, result1, result2, cooktime);
                this.arecipes.add(arecipe);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
        Collections.sort(this.arecipes, new NEIRecipeComparator());
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry entry : ClayWorkTableRecipes.smelting().kneadingList.entrySet()) {
            ItemStack input = (ItemStack)((Map)entry.getKey()).get("Material");
            int method = (Integer)((Map)entry.getKey()).get("ButtonId");
            ItemStack result1 = (ItemStack)((Map)entry.getValue()).get("Result");
            ItemStack result2 = (ItemStack)((Map)entry.getValue()).get("Result2");
            int cooktime = (Integer)((Map)entry.getValue()).get("CookTime");
            if ((!(result1 instanceof ItemStack) || !NEIServerUtils.areStacksSameType((ItemStack)result1, (ItemStack)result)) && (!(result2 instanceof ItemStack) || !NEIServerUtils.areStacksSameType((ItemStack)result2, (ItemStack)result))) continue;
            recipeCacher arecipe = new recipeCacher(input, method, result1, result2, cooktime);
            this.arecipes.add(arecipe);
        }
        Collections.sort(this.arecipes, new NEIRecipeComparator());
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient == null) {
            return;
        }
        for (Map.Entry entry : ClayWorkTableRecipes.smelting().kneadingList.entrySet()) {
            ItemStack input = (ItemStack)((Map)entry.getKey()).get("Material");
            int method = (Integer)((Map)entry.getKey()).get("ButtonId");
            ItemStack result1 = (ItemStack)((Map)entry.getValue()).get("Result");
            ItemStack result2 = (ItemStack)((Map)entry.getValue()).get("Result2");
            int cooktime = (Integer)((Map)entry.getValue()).get("CookTime");
            boolean flag = false;
            if (tools[method] != null) {
                for (ItemStack item : tools[method]) {
                    if (!(item instanceof ItemStack) || !UtilItemStack.areItemDamageEqualOrDamageable(item, ingredient)) continue;
                    flag = true;
                }
            }
            if ((!(input instanceof ItemStack) || !UtilItemStack.areItemDamageEqualOrDamageable(input, ingredient)) && !flag) continue;
            recipeCacher arecipe = new recipeCacher(input, method, result1, result2, cooktime);
            this.arecipes.add(arecipe);
        }
        Collections.sort(this.arecipes, new NEIRecipeComparator());
    }

    public String getOverlayIdentifier() {
        return "ClayWorkTable";
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(69, 26, 28, 12), this.getOverlayIdentifier(), new Object[0]));
    }

    public String getRecipeName() {
        return I18n.func_135052_a((String)("recipe." + this.getOverlayIdentifier()), (Object[])new Object[0]);
    }

    public String getGuiTexture() {
        return "clayium:textures/gui/clayworktable.png";
    }

    public TemplateRecipeHandler newInstance() {
        return new NEIClayWorkTable();
    }

    public void drawExtras(int recipe) {
        int n;
        this.drawProgressBar(43, 18, 176, 0, 80, 16, 40, 0);
        recipeCacher re = (recipeCacher)((Object)this.arecipes.get(recipe));
        Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiClayWorkTable.TEXTURE);
        for (n = 0; n < 6; ++n) {
            int j = n / 5;
            int i = n - j * 5;
            this.drawTexturedModalRect(40 + 16 * n - 5, 41, 176 + 16 * i, 32 + 48 * j, 16, 16);
            if (n != re.method - 1) continue;
            this.drawTexturedModalRect(40 + 16 * n - 5, 41, 176 + 16 * i, 32 + 48 * j + 16, 16, 16);
        }
        n = re.method - 1;
        Minecraft.func_71410_x().field_71466_p.func_78276_b("" + re.time, 40 + 16 * n - 5 + 8 - Minecraft.func_71410_x().field_71466_p.func_78256_a("" + re.time) / 2, 31, -16777216);
    }

    public void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
        double zLevel = 300.0;
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.func_78374_a((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.func_78374_a((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.func_78374_a((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.func_78381_a();
    }

    public class NEIRecipeComparator
    implements Comparator<TemplateRecipeHandler.CachedRecipe> {
        @Override
        public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1) {
            int[] bb;
            if (!(a1 instanceof recipeCacher) || !(b1 instanceof recipeCacher)) {
                return 0;
            }
            recipeCacher a = (recipeCacher)a1;
            recipeCacher b = (recipeCacher)b1;
            if (a.method > b.method) {
                return 1;
            }
            if (b.method > a.method) {
                return -1;
            }
            int[] aa = this.getMaxId(Arrays.asList(a.input));
            if (aa[0] > (bb = this.getMaxId(Arrays.asList(b.input)))[0]) {
                return 1;
            }
            if (bb[0] > aa[0]) {
                return -1;
            }
            if (aa[1] > bb[1]) {
                return 1;
            }
            if (bb[1] > aa[1]) {
                return -1;
            }
            return 0;
        }

        private int[] getMaxId(List<PositionedStack> a) {
            int id = Integer.MIN_VALUE;
            int damage = Integer.MIN_VALUE;
            for (PositionedStack s : a) {
                int[] m = this.getMin(s.items);
                if (m[0] <= id && (m[0] != id || m[1] <= damage)) continue;
                id = m[0];
                damage = m[1];
            }
            return new int[]{id, damage};
        }

        private int[] getMin(ItemStack[] a) {
            int id = Integer.MAX_VALUE;
            int damage = Integer.MAX_VALUE;
            for (ItemStack item : a) {
                int id1 = Item.func_150891_b((Item)item.func_77973_b());
                int damage1 = item.func_77960_j();
                if (id1 >= id && (id1 != id || damage1 >= damage)) continue;
                id = id1;
                damage = damage1;
            }
            return new int[]{id, damage};
        }
    }

    public class recipeCacher
    extends TemplateRecipeHandler.CachedRecipe {
        private PositionedStack input;
        private PositionedStack tool;
        private PositionedStack result;
        private PositionedStack result2;
        public int method;
        public int time;

        public recipeCacher(ItemStack input, int method, ItemStack result, ItemStack result2, int time) {
            super((TemplateRecipeHandler)NEIClayWorkTable.this);
            this.input = new PositionedStack((Object)input, 12, 19);
            this.result = new PositionedStack((Object)result, 138, 19);
            if (result2 != null) {
                this.result2 = new PositionedStack((Object)result2, 138, 44);
            }
            if (method < tools.length && tools[method] != null && tools[method][0] != null) {
                this.tool = new PositionedStack((Object)tools[method], 75, 6);
            }
            this.method = method;
            this.time = time;
        }

        public PositionedStack getResult() {
            return null;
        }

        public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> res = new ArrayList<PositionedStack>();
            res.add(this.input);
            if (this.tool != null) {
                res.add(this.tool);
            }
            return this.getCycledIngredients(NEIClayWorkTable.this.cycleticks / 10, res);
        }

        public List<PositionedStack> getOtherStacks() {
            ArrayList<PositionedStack> res = new ArrayList<PositionedStack>();
            res.add(this.result);
            if (this.result2 != null) {
                res.add(this.result2);
            }
            return res;
        }
    }
}

