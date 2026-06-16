/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry$UniqueIdentifier
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.oredict.OreDictionary
 */
package mods.clayium.util.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.ItemPatternItemStack;
import mods.clayium.util.crafting.ItemPatternOreDictionary;
import mods.clayium.util.crafting.OreDictionaryStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Recipes {
    public static Map<String, Recipes> RecipeMap = new HashMap<String, Recipes>();
    @Deprecated
    public Map RecipeList = new HashMap();
    public String recipeid;
    public Map<RecipeCondition, RecipeResult> recipeResultMap = new HashMap<RecipeCondition, RecipeResult>();
    public Map<GameRegistry.UniqueIdentifier, List<RecipeCondition>> simpleConditionMap = new HashMap<GameRegistry.UniqueIdentifier, List<RecipeCondition>>();
    public List<RecipeCondition> complexConditionList = new ArrayList<RecipeCondition>();
    protected boolean refreshed = false;

    public static int getStackSize(Object item) {
        if (item instanceof ItemStack) {
            return ((ItemStack)item).field_77994_a;
        }
        if (item instanceof OreDictionaryStack) {
            return ((OreDictionaryStack)item).stackSize;
        }
        if (item instanceof String) {
            return 1;
        }
        return 0;
    }

    public static ItemStack isSimpleCond(Object[] items) {
        if (items != null && items.length == 1) {
            return Recipes.isSimple(items[0]);
        }
        return null;
    }

    public static ItemStack isSimple(Object item) {
        if (item instanceof ItemStack) {
            return (ItemStack)item;
        }
        if (item instanceof IItemPattern) {
            return ((IItemPattern)item).isSimple();
        }
        return null;
    }

    public static int getStackSize(Object recipe, ItemStack item) {
        if (recipe instanceof IItemPattern) {
            ItemStack[] items;
            if (item == null && (items = ((IItemPattern)recipe).toItemStacks()) != null && items.length >= 1) {
                item = items[0];
            }
            return ((IItemPattern)recipe).getStackSize(item);
        }
        return Recipes.getStackSize(recipe);
    }

    public Iterable<RecipeCondition> getConditionsForObjects(Object[] materials) {
        if (!this.refreshed) {
            this.removeUnavailableRecipes();
        }
        ItemStack[] items = new ItemStack[materials.length];
        for (int i = 0; i < materials.length; ++i) {
            if (!(materials[i] instanceof ItemStack)) {
                return this.getAllConditions();
            }
            items[i] = (ItemStack)materials[i];
        }
        return this.getConditionsToSearch(items);
    }

    public Set<RecipeCondition> getAllConditions() {
        return this.recipeResultMap.keySet();
    }

    public void removeUnavailableRecipes() {
        ArrayList<RecipeCondition> rem = new ArrayList<RecipeCondition>();
        for (RecipeCondition cond : this.recipeResultMap.keySet()) {
            if (cond.isAvailable()) continue;
            rem.add(cond);
        }
        for (RecipeCondition cond : rem) {
            this.removeRecipe(cond);
        }
        this.refreshed = true;
    }

    public List<RecipeCondition> getConditionsToSearch(ItemStack[] itemstacks) {
        ArrayList<RecipeCondition> res = new ArrayList<RecipeCondition>();
        res.addAll(this.complexConditionList);
        for (ItemStack itemstack : itemstacks) {
            List<RecipeCondition> list = this.simpleConditionMap.get(Recipes.getUniqueIdentifier(itemstack));
            if (list == null) continue;
            res.addAll(list);
        }
        return res;
    }

    protected static GameRegistry.UniqueIdentifier getUniqueIdentifier(ItemStack itemstack) {
        if (itemstack == null) {
            return null;
        }
        return UtilItemStack.findUniqueIdentifierFor(itemstack.func_77973_b());
    }

    public Recipes(String recipeid) {
        this.recipeid = recipeid;
        RecipeMap.put(recipeid, this);
    }

    public static Recipes GetRecipes(String recipeid) {
        return RecipeMap.get(recipeid);
    }

    public void addRecipe(Object[] materials, int method, int tier, ItemStack[] results, long energy, long time, boolean check) {
        if ((materials = Recipes.convertRecipeArray(materials)) == null || results == null) {
            ClayiumCore.logger.error("Invalid recipe!");
            ClayiumCore.logger.error("Arrays must not be null.");
            ClayiumCore.logger.error("Recipe ID : " + this.recipeid);
            return;
        }
        for (Object material : materials) {
            if (material != null && (material instanceof ItemStack || material instanceof String || material instanceof OreDictionaryStack || material instanceof IItemPattern)) continue;
            ClayiumCore.logger.error("Invalid recipe! The ingredient array contains an invalid stack.");
            ClayiumCore.logger.error("Recipe ID : " + this.recipeid);
            ClayiumCore.logger.error("Ingredients : " + Arrays.toString(materials));
            ClayiumCore.logger.error("Results : " + Arrays.toString(results));
            return;
        }
        for (ItemStack result : results) {
            if (result != null && result.func_77973_b() != null) continue;
            ClayiumCore.logger.error("Invalid recipe! The result array contains a null stack.");
            ClayiumCore.logger.error("Recipe ID : " + this.recipeid);
            ClayiumCore.logger.error("Ingredients : " + Arrays.toString(materials));
            ClayiumCore.logger.error("Results : " + Arrays.toString(results));
            return;
        }
        try {
            if (check && this.getRecipeConditionFromRecipe(materials, method, tier, false) != null) {
                return;
            }
            this.addRecipeToOldMap(materials, method, tier, results, energy, time);
            this.addRecipeToRecipeMap(materials, method, tier, results, energy, time);
        }
        catch (Exception e) {
            ClayiumCore.logger.error("Failed to register a recipe.");
            ClayiumCore.logger.error("Recipe ID : " + this.recipeid);
            ClayiumCore.logger.error("Ingredients : " + Arrays.toString(materials));
            ClayiumCore.logger.error("Results : " + Arrays.toString(results));
            ClayiumCore.logger.catching((Throwable)e);
        }
    }

    @Deprecated
    protected void addRecipeToOldMap(Object[] materials, int method, int tier, ItemStack[] results, long energy, long time) {
        HashMap<String, Object> keyMap = new HashMap<String, Object>();
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        keyMap.put("Material", Arrays.asList((Object[])materials.clone()));
        keyMap.put("Method", method);
        keyMap.put("Tier", tier);
        valueMap.put("Result", Recipes.Array2ArrayList(results));
        valueMap.put("Energy", energy);
        valueMap.put("Time", time);
        this.RecipeList.put(keyMap, valueMap);
    }

    protected void addRecipeToRecipeMap(Object[] materials, int method, int tier, ItemStack[] results, long energy, long time) {
        this.addRecipe(new RecipeCondition(materials, method, tier), new RecipeResult(results, energy, time));
    }

    public RecipeResult addRecipe(RecipeCondition cond, RecipeResult result) {
        this.addCondition(cond);
        try {
            return this.recipeResultMap.put(cond, result);
        }
        catch (RuntimeException e) {
            ClayiumCore.logger.error("Failed to add a recipe. Trying to remove this broken recipe.");
            try {
                this.removeRecipe(cond);
            }
            catch (RuntimeException e1) {
                ClayiumCore.logger.catching((Throwable)e);
                ClayiumCore.logger.fatal("Failed to remove the broken recipe. It may cause a server crush.");
                throw e1;
            }
            throw e;
        }
    }

    public RecipeResult removeRecipe(RecipeCondition cond) {
        this.removeCondition(cond);
        return this.recipeResultMap.remove(cond);
    }

    public RecipeResult getResult(RecipeCondition cond) {
        return this.recipeResultMap.get(cond);
    }

    protected void addCondition(RecipeCondition cond) {
        if (cond == null || cond.getMaterials() == null) {
            return;
        }
        ItemStack recipe = Recipes.isSimpleCond(cond.getMaterials());
        if (recipe != null) {
            GameRegistry.UniqueIdentifier ui = Recipes.getUniqueIdentifier(recipe);
            List<RecipeCondition> conds = this.simpleConditionMap.get(ui);
            if (conds == null) {
                conds = new ArrayList<RecipeCondition>();
                this.simpleConditionMap.put(ui, conds);
            }
            conds.add(cond);
        } else {
            this.complexConditionList.add(cond);
        }
    }

    protected void removeCondition(RecipeCondition cond) {
        if (cond == null || cond.getMaterials() == null) {
            return;
        }
        ItemStack recipe = Recipes.isSimpleCond(cond.getMaterials());
        if (recipe != null) {
            GameRegistry.UniqueIdentifier ui = Recipes.getUniqueIdentifier(recipe);
            List<RecipeCondition> conds = this.simpleConditionMap.get(ui);
            if (conds == null) {
                conds = new ArrayList<RecipeCondition>();
                this.simpleConditionMap.put(ui, conds);
            }
            conds.remove(cond);
        } else {
            this.complexConditionList.remove(cond);
        }
    }

    public void addRecipe(Object[] materials, int method, int tier, ItemStack[] results, long energy, long time) {
        this.addRecipe(materials, method, tier, results, energy, time, false);
    }

    public static boolean canBeCrafted(ItemStack itemstack, ItemStack itemstack2, boolean sizeCheck) {
        if (itemstack2 == null) {
            return true;
        }
        if (itemstack == null) {
            return false;
        }
        return !(!UtilItemStack.areItemEqual(itemstack2, itemstack) || itemstack2.func_77960_j() != Short.MAX_VALUE && itemstack.func_77960_j() != Short.MAX_VALUE && !UtilItemStack.areDamageEqual(itemstack2, itemstack) || sizeCheck && itemstack2.field_77994_a > itemstack.field_77994_a);
    }

    public static boolean canBeCrafted(ItemStack itemstack, ItemStack itemstack2) {
        return Recipes.canBeCrafted(itemstack, itemstack2, true);
    }

    public static boolean canBeCraftedOD(ItemStack itemstack, Object object, boolean sizeCheck) {
        if (object == null) {
            return true;
        }
        if (itemstack == null) {
            return false;
        }
        if (object instanceof String) {
            if (UtilItemStack.hasOreName(itemstack, (String)object)) {
                return true;
            }
        } else if (object instanceof OreDictionaryStack) {
            if (sizeCheck && ((OreDictionaryStack)object).stackSize > itemstack.field_77994_a) {
                return false;
            }
            if (UtilItemStack.hasOreName(itemstack, ((OreDictionaryStack)object).id)) {
                return true;
            }
        } else {
            if (object instanceof ItemStack) {
                return Recipes.canBeCrafted(itemstack, (ItemStack)object, sizeCheck);
            }
            if (object instanceof IItemPattern) {
                return ((IItemPattern)object).match(itemstack, sizeCheck);
            }
        }
        return false;
    }

    public static boolean canBeCraftedOD(ItemStack itemstack, Object object) {
        return Recipes.canBeCraftedOD(itemstack, object, true);
    }

    public static boolean canBeCraftedODs(Object stackingred, Object recipeingred, boolean sizeCheck) {
        if (recipeingred == null) {
            return true;
        }
        if (stackingred == null) {
            return false;
        }
        if (stackingred instanceof ItemStack) {
            return Recipes.canBeCraftedOD((ItemStack)stackingred, recipeingred, sizeCheck);
        }
        if (stackingred instanceof String || stackingred instanceof OreDictionaryStack) {
            int oreid = stackingred instanceof OreDictionaryStack ? ((OreDictionaryStack)stackingred).id : OreDictionary.getOreID((String)((String)stackingred));
            int stackSize = stackingred instanceof OreDictionaryStack ? ((OreDictionaryStack)stackingred).stackSize : 1;
            for (ItemStack item : OreDictionary.getOres((Integer)oreid)) {
                ItemStack item0 = item.func_77946_l();
                item0.field_77994_a = stackSize;
                if (!Recipes.canBeCraftedOD(item0, recipeingred, sizeCheck)) continue;
                return true;
            }
        }
        if (stackingred instanceof IItemPattern) {
            return ((IItemPattern)stackingred).hasIntersection(Recipes.convert(recipeingred), sizeCheck);
        }
        return false;
    }

    public static IItemPattern convert(Object ingred) {
        if (ingred instanceof ItemStack) {
            return new ItemPatternItemStack((ItemStack)ingred);
        }
        if (ingred instanceof OreDictionaryStack) {
            return new ItemPatternOreDictionary(((OreDictionaryStack)ingred).id, ((OreDictionaryStack)ingred).stackSize);
        }
        if (ingred instanceof String) {
            return new ItemPatternOreDictionary((String)ingred, 1);
        }
        if (ingred instanceof IItemPattern) {
            return (IItemPattern)ingred;
        }
        return null;
    }

    public static IItemPattern[] convertRecipeArray(Object[] ingreds) {
        if (ingreds == null) {
            return null;
        }
        IItemPattern[] patterns = new IItemPattern[ingreds.length];
        for (int i = 0; i < ingreds.length; ++i) {
            patterns[i] = Recipes.convert(ingreds[i]);
        }
        return patterns;
    }

    public static List<ItemStack> Array2ArrayList(ItemStack[] itemstacks) {
        ArrayList<ItemStack> res = new ArrayList<ItemStack>(itemstacks.length);
        for (int i = 0; i < itemstacks.length; ++i) {
            res.add(itemstacks[i]);
        }
        return res;
    }

    public static ItemStack[] ArrayList2Array(List itemstacks) {
        ItemStack[] res = new ItemStack[itemstacks.size()];
        for (int i = 0; i < itemstacks.size(); ++i) {
            res[i] = (ItemStack)itemstacks.get(i);
        }
        return res;
    }

    @Deprecated
    public Map.Entry getResultEntry(List materials, int method, int tier, boolean sizeCheck) {
        Map.Entry entry_ = null;
        int maxStackSize = 0;
        for (Map.Entry entry : this.RecipeList.entrySet()) {
            List materiallist = (List)((Map)entry.getKey()).get("Material");
            boolean flag = true;
            int stackSize = 0;
            if (materiallist.size() != materials.size()) continue;
            for (int i = 0; i < materiallist.size(); ++i) {
                if (!Recipes.canBeCraftedODs(materials.get(i), materiallist.get(i), sizeCheck)) {
                    flag = false;
                    continue;
                }
                if (materiallist.get(i) instanceof ItemStack) {
                    stackSize += ((ItemStack)materiallist.get((int)i)).field_77994_a;
                    continue;
                }
                if (materiallist.get(i) instanceof OreDictionaryStack) {
                    stackSize += ((OreDictionaryStack)materiallist.get((int)i)).stackSize;
                    continue;
                }
                if (!(materiallist.get(i) instanceof String)) continue;
                ++stackSize;
            }
            if (!flag || (Integer)((Map)entry.getKey()).get("Method") != method || (Integer)((Map)entry.getKey()).get("Tier") > tier || stackSize <= maxStackSize) continue;
            entry_ = entry;
            maxStackSize = stackSize;
        }
        if (entry_ == null) {
            return null;
        }
        return entry_;
    }

    public RecipeCondition getRecipeConditionFromRecipe(Object[] materials, int method, int tier, boolean sizeCheck) {
        int maxStackSize = -1;
        RecipeCondition res = null;
        for (RecipeCondition cond : this.getConditionsForObjects(materials)) {
            int s = cond.matchRecipe(materials, method, tier, sizeCheck);
            if (s <= maxStackSize) continue;
            maxStackSize = s;
            res = cond;
        }
        return res;
    }

    public RecipeCondition getRecipeConditionFromRecipe(Object[] materials, int method, int tier) {
        return this.getRecipeConditionFromRecipe(materials, method, tier, true);
    }

    public RecipeCondition getRecipeConditionFromInventory(ItemStack[] materials, int method, int tier, boolean sizeCheck) {
        int maxStackSize = -1;
        RecipeCondition res = null;
        for (RecipeCondition cond : this.getConditionsForObjects(materials)) {
            int s = cond.match(materials, method, tier, sizeCheck);
            if (s <= maxStackSize) continue;
            maxStackSize = s;
            res = cond;
        }
        return res;
    }

    public RecipeCondition getRecipeConditionFromInventory(ItemStack[] materials, int method, int tier) {
        return this.getRecipeConditionFromInventory(materials, method, tier, true);
    }

    public RecipeResult getRecipeResultFromRecipe(Object[] materials, int method, int tier, boolean sizeCheck) {
        return this.getRecipeResult(this.getRecipeConditionFromRecipe(materials, method, tier, sizeCheck));
    }

    public RecipeResult getRecipeResultFromRecipe(Object[] materials, int method, int tier) {
        return this.getRecipeResultFromRecipe(materials, method, tier, true);
    }

    public RecipeResult getRecipeResultFromInventory(ItemStack[] materials, int method, int tier, boolean sizeCheck) {
        return this.getRecipeResult(this.getRecipeConditionFromInventory(materials, method, tier, sizeCheck));
    }

    public RecipeResult getRecipeResultFromInventory(ItemStack[] materials, int method, int tier) {
        return this.getRecipeResultFromInventory(materials, method, tier, true);
    }

    public RecipeResult getRecipeResult(RecipeCondition condition) {
        return condition != null ? this.recipeResultMap.get(condition) : null;
    }

    @Deprecated
    public Map.Entry getResultEntry(List<ItemStack> materials, int method, int tier) {
        return this.getResultEntry(materials, method, tier, true);
    }

    @Deprecated
    protected Map getMaterialMap(List<ItemStack> materials, int method, int tier) {
        Map.Entry entry = this.getResultEntry(materials, method, tier);
        if (entry == null) {
            return null;
        }
        return (Map)entry.getKey();
    }

    @Deprecated
    protected Map getResultMap(List<ItemStack> materials, int method, int tier) {
        Map.Entry entry = this.getResultEntry(materials, method, tier);
        if (entry == null) {
            return null;
        }
        return (Map)entry.getValue();
    }

    public int[] getConsumedStackSize(ItemStack[] materials, int method, int tier) {
        RecipeCondition cond = this.getRecipeConditionFromInventory(materials, method, tier);
        if (cond == null) {
            return null;
        }
        return cond.getStackSizes(materials);
    }

    public int[] getConsumedStackSize(List<ItemStack> materials, int method, int tier) {
        return this.getConsumedStackSize(materials.toArray(new ItemStack[0]), method, tier);
    }

    public static ItemStack[] getResults(RecipeResult result) {
        return result == null ? null : result.getResults();
    }

    public ItemStack[] getResult(ItemStack[] materials, int method, int tier) {
        return Recipes.getResults(this.getRecipeResultFromInventory(materials, method, tier));
    }

    public ItemStack[] getResult(List<ItemStack> materials, int method, int tier) {
        return this.getResult(materials.toArray(new ItemStack[0]), method, tier);
    }

    public static long getEnergy(RecipeResult result) {
        return result == null ? 0L : result.getEnergy();
    }

    public long getEnergy(ItemStack[] materials, int method, int tier) {
        return Recipes.getEnergy(this.getRecipeResultFromInventory(materials, method, tier));
    }

    public long getEnergy(List<ItemStack> materials, int method, int tier) {
        return this.getEnergy(materials.toArray(new ItemStack[0]), method, tier);
    }

    public static long getTime(RecipeResult result) {
        return result == null ? 0L : result.getTime();
    }

    public long getTime(ItemStack[] materials, int method, int tier) {
        return Recipes.getTime(this.getRecipeResultFromInventory(materials, method, tier));
    }

    public long getTime(List<ItemStack> materials, int method, int tier) {
        return this.getTime(materials.toArray(new ItemStack[0]), method, tier);
    }

    public static long getValue(Object obj) {
        if (obj instanceof Long) {
            return (Long)obj;
        }
        if (obj instanceof Integer) {
            return ((Integer)obj).intValue();
        }
        if (obj instanceof Short) {
            return ((Short)obj).shortValue();
        }
        if (obj instanceof Byte) {
            return ((Byte)obj).byteValue();
        }
        if (obj instanceof Double) {
            return (long)((Double)obj).doubleValue();
        }
        if (obj instanceof Float) {
            return (long)((Float)obj).floatValue();
        }
        return 0L;
    }

    @Deprecated
    public boolean hasResult(ItemStack[] materials, int tier) {
        return this.hasResult(Recipes.Array2ArrayList(materials), tier);
    }

    @Deprecated
    public boolean hasResult(List<ItemStack> materials, int tier) {
        Map.Entry entry_ = null;
        int maxStackSize = 0;
        for (Map.Entry entry : this.RecipeList.entrySet()) {
            List materiallist = (List)((Map)entry.getKey()).get("Material");
            boolean flag = true;
            int stackSize = 0;
            if (materiallist.size() > materials.size()) continue;
            for (int i = 0; i < materiallist.size(); ++i) {
                if (!Recipes.canBeCraftedOD(materials.get(i), materiallist.get(i))) {
                    flag = false;
                    continue;
                }
                if (materiallist.get(i) instanceof ItemStack) {
                    stackSize += ((ItemStack)materiallist.get((int)i)).field_77994_a;
                    continue;
                }
                if (materiallist.get(i) instanceof OreDictionaryStack) {
                    stackSize += ((OreDictionaryStack)materiallist.get((int)i)).stackSize;
                    continue;
                }
                if (!(materiallist.get(i) instanceof String)) continue;
                ++stackSize;
            }
            flag = (Integer)((Map)entry.getKey()).get("Tier") <= tier && stackSize > maxStackSize;
            if (!flag) continue;
            entry_ = entry;
            maxStackSize = stackSize;
        }
        return entry_ != null;
    }

    public boolean isCraftable(ItemStack material, int tier) {
        if (material == null) {
            return false;
        }
        int maxStackSize = -1;
        for (RecipeCondition cond : this.getConditionsForObjects(new ItemStack[]{material})) {
            if (!cond.isCraftable(material, tier)) continue;
            return true;
        }
        return false;
    }

    public static class RecipeResult {
        protected ItemStack[] results;
        protected long energy;
        protected long time;

        public ItemStack[] getResults() {
            return this.results;
        }

        public long getEnergy() {
            return this.energy;
        }

        public long getTime() {
            return this.time;
        }

        public RecipeResult(ItemStack[] results, long energy, long time) {
            this.results = results;
            this.energy = energy;
            this.time = time;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (int)(this.energy ^ this.energy >>> 32);
            result = 31 * result + UtilItemStack.getItemStackHashCode(this.results);
            result = 31 * result + (int)(this.time ^ this.time >>> 32);
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            RecipeResult other = (RecipeResult)obj;
            if (this.energy != other.energy) {
                return false;
            }
            if (!UtilItemStack.areStacksEqual(this.results, other.results)) {
                return false;
            }
            return this.time == other.time;
        }
    }

    public static class RecipeCondition {
        protected Object[] materials;
        protected int method;
        protected int tier;

        public Object[] getMaterials() {
            return this.materials;
        }

        public int getMethod() {
            return this.method;
        }

        public int getTier() {
            return this.tier;
        }

        public RecipeCondition(Object[] materials, int method, int tier) {
            this.materials = materials;
            this.method = method;
            this.tier = tier;
        }

        public int match(ItemStack[] itemstacks, int method, int tier, boolean sizeCheck) {
            if (this.materials.length == itemstacks.length) {
                return this.matchLengthUnchecked(itemstacks, method, tier, sizeCheck);
            }
            return -1;
        }

        public int matchRecipe(Object[] itemstacks, int method, int tier, boolean sizeCheck) {
            if (this.materials.length == itemstacks.length) {
                return this.matchRecipeLengthUnchecked(itemstacks, method, tier, sizeCheck);
            }
            return -1;
        }

        protected int matchLengthUnchecked(ItemStack[] itemstacks, int method, int tier, boolean sizeCheck) {
            if (this.method != method || this.tier > tier) {
                return -1;
            }
            int stackSize = 0;
            for (int i = 0; i < itemstacks.length && i < this.materials.length; ++i) {
                if (!Recipes.canBeCraftedOD(itemstacks[i], this.materials[i], sizeCheck)) {
                    return -1;
                }
                stackSize += Recipes.getStackSize(this.materials[i], itemstacks[i]);
            }
            return stackSize;
        }

        protected int matchRecipeLengthUnchecked(Object[] itemstacks, int method, int tier, boolean sizeCheck) {
            if (this.method != method || this.tier > tier) {
                return -1;
            }
            int stackSize = 0;
            for (int i = 0; i < itemstacks.length && i < this.materials.length; ++i) {
                if (!Recipes.canBeCraftedODs(itemstacks[i], this.materials[i], sizeCheck)) {
                    return -1;
                }
                stackSize += Recipes.getStackSize(this.materials[i], Recipes.isSimple(itemstacks[i]));
            }
            return stackSize;
        }

        public boolean isCraftable(ItemStack itemstack, int tier) {
            if (this.tier > tier) {
                return false;
            }
            for (int i = 0; i < this.materials.length; ++i) {
                if (!Recipes.canBeCraftedODs(itemstack, this.materials[i], false)) continue;
                return true;
            }
            return false;
        }

        public int[] getStackSizes(ItemStack[] items) {
            int[] stacksizelist = new int[items.length];
            for (int i = 0; i < items.length && i < this.materials.length; ++i) {
                stacksizelist[i] = Recipes.getStackSize(this.materials[i], items[i]);
            }
            return stacksizelist;
        }

        public boolean isAvailable() {
            if (this.materials == null || this.materials.length == 0) {
                return false;
            }
            for (Object material : this.materials) {
                ArrayList list;
                if (material == null || material instanceof ItemStack) continue;
                if (material instanceof OreDictionaryStack) {
                    list = OreDictionary.getOres((Integer)((OreDictionaryStack)material).id);
                    if (list != null && list.size() != 0) continue;
                    return false;
                }
                if (material instanceof String) {
                    list = OreDictionary.getOres((String)((String)material));
                    if (list != null && list.size() != 0) continue;
                    return false;
                }
                if (material instanceof IItemPattern) {
                    if (((IItemPattern)material).isAvailable()) continue;
                    return false;
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + UtilItemStack.getItemStackHashCode(this.materials);
            result = 31 * result + this.method;
            result = 31 * result + this.tier;
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            RecipeCondition other = (RecipeCondition)obj;
            if (!UtilItemStack.areStacksEqual(this.materials, other.materials)) {
                return false;
            }
            if (this.method != other.method) {
                return false;
            }
            return this.tier == other.tier;
        }
    }
}

