/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.lib.gui.GuiDraw
 *  codechicken.nei.PositionedStack
 *  codechicken.nei.recipe.TemplateRecipeHandler
 *  codechicken.nei.recipe.TemplateRecipeHandler$CachedRecipe
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.plugin.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mods.clayium.gui.FDText;
import mods.clayium.gui.IFunctionalDrawer;
import mods.clayium.plugin.nei.INEIRecipeEntry;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class NEITemp
extends TemplateRecipeHandler {
    public IFunctionalDrawer<Object> drawerProgressBar = new IFunctionalDrawer<Object>(){

        @Override
        public Object draw(Object param) {
            if (param instanceof INEIRecipeEntry) {
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GuiDraw.changeTexture((String)"clayium:textures/gui/progressbarfurnace.png");
                ((INEIRecipeEntry)param).getHandler().drawProgressBar(71, 21, 0, 0, 24, 17, 40, 0);
            }
            return param;
        }
    };
    public static FDText.FDTextHandler<Object> setterTier = new NEITextSetterTier<Object>();
    public static IFunctionalDrawer<Object> drawerTier = FDText.INSTANCE.getFDText(setterTier, 6, 43, -16777216, -1);
    public static FDText.FDTextHandler<Object> setterEnergy = new NEITextSetterEnergy<Object>();
    public static IFunctionalDrawer<Object> drawerEnergy = FDText.INSTANCE.getFDText(setterEnergy, 6, 52, -16777216, -1);
    private static final StackComparator defaultStackComparator = new StackComparator();

    public List<PositionedStack> generateIngredientPositionedStacks(Object[] ingredients) {
        ArrayList<PositionedStack> inputList = new ArrayList<PositionedStack>();
        for (int i = 0; i < ingredients.length; ++i) {
            inputList.add(NEITemp.generatePermutationsFixed(new PositionedStack((Object)UtilItemStack.object2ItemStacks(ingredients[i]), 52 - i * 17, 21, false)));
        }
        return inputList;
    }

    public List<PositionedStack> generateResultPositionedStacks(ItemStack[] results) {
        ArrayList<PositionedStack> resultList = new ArrayList<PositionedStack>();
        for (int i = 0; i < results.length; ++i) {
            resultList.add(new PositionedStack((Object)results[i], 98 + i * 17, 21));
        }
        return resultList;
    }

    public static PositionedStack generatePermutationsFixed(PositionedStack p) {
        if (p.items == null || p.items.length == 0) {
            return p;
        }
        int stacksize = p.items[0].field_77994_a;
        p.generatePermutations();
        for (ItemStack item : p.items) {
            item.field_77994_a = stacksize;
        }
        return p;
    }

    public static boolean isAvailable(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        for (Object object : objects) {
            ItemStack[] itemstacks = UtilItemStack.object2ItemStacks(object);
            if (itemstacks != null && itemstacks.length != 0) continue;
            return false;
        }
        return true;
    }

    public abstract Comparator<TemplateRecipeHandler.CachedRecipe> getComparator();

    public static StackComparator getStackComparator() {
        return defaultStackComparator;
    }

    public abstract Iterable<INEIRecipeEntry> getMatchedSet();

    public boolean matchForOutputId(String outputId, Object ... results) {
        return outputId == null || outputId.equals(this.getOverlayIdentifier());
    }

    public void loadCraftingRecipes(String outputId, Object ... results) {
        if (this.matchForOutputId(outputId, results)) {
            Iterable<INEIRecipeEntry> set = this.getMatchedSet();
            if (set == null) {
                return;
            }
            for (INEIRecipeEntry entry : this.getMatchedSet()) {
                this.arecipes.add(entry.asCachedRecipe());
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
        Collections.sort(this.arecipes, this.getComparator());
    }

    public void loadCraftingRecipes(ItemStack result) {
        Iterable<INEIRecipeEntry> set = this.getMatchedSet();
        if (set == null) {
            return;
        }
        for (INEIRecipeEntry entry : this.getMatchedSet()) {
            if (!entry.matchForCraftingRecipe(result)) continue;
            this.arecipes.add(entry.asCachedRecipe());
        }
        Collections.sort(this.arecipes, this.getComparator());
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient == null) {
            return;
        }
        Iterable<INEIRecipeEntry> set = this.getMatchedSet();
        if (set == null) {
            return;
        }
        for (INEIRecipeEntry entry : this.getMatchedSet()) {
            if (!entry.matchForUsageRecipe(ingredient)) continue;
            this.arecipes.add(entry.asCachedRecipe());
        }
        Collections.sort(this.arecipes, this.getComparator());
    }

    public void drawExtras(int recipe) {
        TemplateRecipeHandler.CachedRecipe re = (TemplateRecipeHandler.CachedRecipe)this.arecipes.get(recipe);
        if (re instanceof INEIRecipeEntry) {
            ((INEIRecipeEntry)re).drawExtras();
        }
    }

    public void drawProgressBar(int x, int y, int tx, int ty, int w, int h, float completion, int direction) {
        GuiDraw.drawTexturedModalRect((int)x, (int)y, (int)tx, (int)ty, (int)w, (int)h);
        super.drawProgressBar(x, y, tx, ty + h, w, h, completion, direction);
    }

    public String getRecipeName() {
        return I18n.func_135052_a((String)("recipe." + this.getOverlayIdentifier()), (Object[])new Object[0]);
    }

    @Deprecated
    public static void drawTier(int tier) {
        Minecraft.func_71410_x().field_71466_p.func_78276_b("Tier: " + tier, 6, 43, -16777216);
    }

    @Deprecated
    public static void drawEnergy(long energy, long time) {
        Minecraft.func_71410_x().field_71466_p.func_78276_b(UtilLocale.ClayEnergyNumeral(energy) + "CE/t x " + UtilLocale.craftTimeNumeral(time) + "t = " + UtilLocale.ClayEnergyNumeral((double)energy * (double)time) + "CE", 6, 52, -16777216);
    }

    public static class StackComparator
    implements Comparator<List<PositionedStack>> {
        @Override
        public int compare(List<PositionedStack> la, List<PositionedStack> lb) {
            int[] bb;
            if (la == null && lb == null) {
                return 0;
            }
            if (lb == null) {
                return 1;
            }
            if (la == null) {
                return -1;
            }
            if (la.size() > lb.size()) {
                return 1;
            }
            if (lb.size() > la.size()) {
                return -1;
            }
            int[] aa = this.getMaxId(la);
            if (aa[0] > (bb = this.getMaxId(lb))[0]) {
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
            if (aa[2] > bb[2]) {
                return 1;
            }
            if (bb[2] > aa[2]) {
                return -1;
            }
            return 0;
        }

        private int[] getMaxId(List<PositionedStack> a) {
            int id = Integer.MIN_VALUE;
            int damage = Integer.MIN_VALUE;
            int stacksize = Integer.MIN_VALUE;
            for (PositionedStack s : a) {
                int[] m = this.getMin(s.items);
                if (m[0] <= id && (m[0] != id || m[1] <= damage) && (m[0] != id || m[1] != damage || m[2] <= stacksize)) continue;
                id = m[0];
                damage = m[1];
                stacksize = m[2];
            }
            return new int[]{id, damage, stacksize};
        }

        private int[] getMin(ItemStack[] a) {
            int id = Integer.MAX_VALUE;
            int damage = Integer.MAX_VALUE;
            int stacksize = Integer.MAX_VALUE;
            for (ItemStack item : a) {
                int id1 = Item.func_150891_b((Item)item.func_77973_b());
                int damage1 = item.func_77960_j();
                int stacksize1 = item.field_77994_a;
                if (id1 >= id && (id1 != id || damage1 >= damage) && (id1 != id || damage1 != damage || stacksize1 >= stacksize)) continue;
                id = id1;
                damage = damage1;
                stacksize = stacksize1;
            }
            return new int[]{id, damage, stacksize};
        }
    }

    public static class NEIEntryComparator
    implements Comparator<TemplateRecipeHandler.CachedRecipe> {
        @Override
        public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1) {
            if (a1 instanceof INEIEntryTiered && b1 instanceof INEIEntryTiered) {
                int b;
                int a = ((INEIEntryTiered)a1).getTier();
                if (a > (b = ((INEIEntryTiered)b1).getTier())) {
                    return 1;
                }
                if (b > a) {
                    return -1;
                }
            }
            if (a1 instanceof INEIEntryEnergy && b1 instanceof INEIEntryEnergy) {
                long bt;
                double b;
                long ae = ((INEIEntryEnergy)a1).getEnergy();
                long be = ((INEIEntryEnergy)b1).getEnergy();
                long at = ((INEIEntryEnergy)a1).getTime();
                double a = (double)ae * (double)at;
                if (a > (b = (double)be * (double)(bt = ((INEIEntryEnergy)b1).getTime()))) {
                    return 1;
                }
                if (b > a) {
                    return -1;
                }
                if (ae > be) {
                    return 1;
                }
                if (be > ae) {
                    return -1;
                }
                if (at > bt) {
                    return 1;
                }
                if (bt > at) {
                    return -1;
                }
            }
            if (a1 instanceof INEIRecipeEntry && b1 instanceof INEIRecipeEntry) {
                return NEITemp.getStackComparator().compare(((INEIRecipeEntry)a1).getIngredientsToSort(), ((INEIRecipeEntry)b1).getIngredientsToSort());
            }
            return 0;
        }
    }

    public class NEITemplateEntry
    extends NEIEntryWithFD
    implements INEIRecipeEntry {
        protected List<PositionedStack> inputs;
        protected List<PositionedStack> results;
        protected Object[] inputObjects;

        public NEITemplateEntry(Object[] inputs, ItemStack[] results) {
            this(inputs, this$0.generateIngredientPositionedStacks(inputs), this$0.generateResultPositionedStacks(results));
        }

        public NEITemplateEntry(Object[] inputObjects, List<PositionedStack> inputs, List<PositionedStack> results, boolean drawProgressBar) {
            this.inputObjects = inputObjects;
            this.inputs = inputs;
            this.results = results;
            List<IFunctionalDrawer<Object>> list = this.getFDList();
            if (drawProgressBar) {
                list.add(NEITemp.this.drawerProgressBar);
            }
            list.add(drawerTier);
            list.add(drawerEnergy);
        }

        public NEITemplateEntry(Object[] inputObjects, List<PositionedStack> inputs, List<PositionedStack> results) {
            this(inputObjects, inputs, results, true);
        }

        public PositionedStack getResult() {
            return null;
        }

        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(NEITemp.this.cycleticks / 10, this.inputs);
        }

        @Override
        public List<PositionedStack> getIngredientsToSort() {
            return this.inputs;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.results;
        }

        public void computeVisuals() {
            for (PositionedStack p : this.inputs) {
                NEITemp.generatePermutationsFixed(p);
            }
        }

        @Override
        public TemplateRecipeHandler.CachedRecipe asCachedRecipe() {
            return this;
        }

        @Override
        public boolean matchForUsageRecipe(ItemStack ingredient) {
            if (ingredient == null) {
                return false;
            }
            ItemStack material2 = ingredient.func_77946_l();
            material2.field_77994_a = material2.func_77976_d();
            for (Object material1 : this.inputObjects) {
                if (material1 == null || !Recipes.canBeCraftedOD(material2, material1)) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean matchForCraftingRecipe(ItemStack result) {
            for (PositionedStack result0 : this.getOtherStacks()) {
                for (ItemStack result_ : result0.items) {
                    if (!(result_ instanceof ItemStack) || !UtilItemStack.areItemDamageEqualOrDamageable(result_, result) && !UtilItemStack.haveSameOD(result_, result)) continue;
                    return true;
                }
            }
            return false;
        }
    }

    public static interface INEIEntryEnergy {
        public long getEnergy();

        public long getTime();
    }

    public static interface INEIEntryTiered {
        public int getTier();
    }

    public static class NEITextSetterEnergy<T>
    extends FDText.FDTextHandler<T> {
        public NEITextSetterEnergy() {
            this.handleString = true;
        }

        @Override
        public String applyString(T param) {
            long energy = this.getEnergy(param);
            long time = this.getTime(param);
            if (energy < 0L) {
                if (time < 0L) {
                    return "";
                }
                return UtilLocale.craftTimeNumeral(time) + "t";
            }
            if (time < 0L) {
                return UtilLocale.ClayEnergyNumeral(energy) + "CE";
            }
            return UtilLocale.ClayEnergyNumeral(energy) + "CE/t x " + UtilLocale.craftTimeNumeral(time) + "t = " + UtilLocale.ClayEnergyNumeral((double)energy * (double)time) + "CE";
        }

        public long getEnergy(T param) {
            return param instanceof INEIEntryEnergy ? ((INEIEntryEnergy)param).getEnergy() : -1L;
        }

        public long getTime(T param) {
            return param instanceof INEIEntryEnergy ? ((INEIEntryEnergy)param).getTime() : -1L;
        }
    }

    public static class NEITextSetterTier<T>
    extends FDText.FDTextHandler<T> {
        public NEITextSetterTier() {
            this.handleString = true;
        }

        @Override
        public String applyString(T param) {
            int tier = this.getTier(param);
            return tier < 0 ? "" : "Tier: " + tier;
        }

        public int getTier(T param) {
            return param instanceof INEIEntryTiered ? ((INEIEntryTiered)param).getTier() : -1;
        }
    }

    public abstract class NEIEntryWithFD
    extends TemplateRecipeHandler.CachedRecipe
    implements INEIRecipeEntry {
        protected TemplateRecipeHandler handler;
        protected List<IFunctionalDrawer<Object>> fdList;

        public NEIEntryWithFD() {
            super((TemplateRecipeHandler)NEITemp.this);
            this.handler = NEITemp.this;
            this.fdList = new ArrayList<IFunctionalDrawer<Object>>();
        }

        public List<IFunctionalDrawer<Object>> getFDList() {
            return this.fdList;
        }

        public void setFDList(List<IFunctionalDrawer<Object>> fdList) {
            this.fdList = fdList;
        }

        @Override
        public void drawExtras() {
            for (IFunctionalDrawer<Object> fd : this.fdList) {
                fd.draw(this);
            }
        }

        @Override
        public void setHandler(TemplateRecipeHandler handler) {
            this.handler = handler;
        }

        @Override
        public TemplateRecipeHandler getHandler() {
            return this.handler;
        }
    }
}

