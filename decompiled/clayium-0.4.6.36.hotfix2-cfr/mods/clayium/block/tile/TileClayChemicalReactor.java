/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.item.ItemStack;

public class TileClayChemicalReactor
extends TileClayMachines {
    private Recipes recipe;
    protected int resultSlotNum = 2;

    @Override
    public void refreshRecipe() {
        Recipes recipe = Recipes.GetRecipes(this.recipeId);
        if (recipe != null) {
            this.recipe = recipe;
        }
    }

    @Override
    public void initParams() {
        this.containerItemStacks = new ItemStack[7];
        this.clayEnergySlot = 6;
        this.listSlotsInsert.add(new int[]{0});
        this.listSlotsInsert.add(new int[]{1});
        this.listSlotsInsert.add(new int[]{0, 1});
        this.listSlotsInsert.add(new int[]{6});
        this.listSlotsExtract.add(new int[]{2});
        this.listSlotsExtract.add(new int[]{3});
        this.listSlotsExtract.add(new int[]{2, 3});
        this.insertRoutes = new int[]{-1, 2, -1, 3, -1, -1};
        this.maxAutoExtract = new int[]{-1, -1, -1, 1};
        this.extractRoutes = new int[]{2, -1, -1, -1, -1, -1};
        this.maxAutoInsert = new int[]{-1, -1, -1};
        this.slotsDrop = new int[]{0, 1, 2, 3, 6};
        this.autoInsert = true;
        this.autoExtract = true;
    }

    protected boolean canCraft(ItemStack[] materials) {
        int method = 0;
        if (materials == null || this.recipe == null) {
            return false;
        }
        ItemStack[] itemstacks = this.recipe.getResult(materials, method, this.baseTier);
        if (itemstacks == null) {
            return false;
        }
        for (int i = 0; i < Math.min(this.resultSlotNum, itemstacks.length); ++i) {
            if (this.containerItemStacks[i + 2] == null || itemstacks[i] == null) continue;
            if (!this.containerItemStacks[i + 2].func_77969_a(itemstacks[i])) {
                return false;
            }
            int result = this.containerItemStacks[i + 2].field_77994_a + itemstacks[i].field_77994_a;
            if (result <= this.func_70297_j_() && result <= this.containerItemStacks[i + 2].func_77976_d()) continue;
            return false;
        }
        return true;
    }

    protected int[] getCraftPermutation(ItemStack[] materials) {
        if (this.canCraft(materials)) {
            return new int[]{0, 1};
        }
        if (this.canCraft(new ItemStack[]{materials[1], materials[0]})) {
            return new int[]{1, 0};
        }
        if (this.canCraft(new ItemStack[]{materials[0]})) {
            return new int[]{0};
        }
        if (this.canCraft(new ItemStack[]{materials[1]})) {
            return new int[]{1};
        }
        return null;
    }

    @Override
    public boolean canProceedCraft() {
        if (this.containerItemStacks[4] != null || this.containerItemStacks[5] != null) {
            ItemStack[] itemstacks = new ItemStack[]{this.containerItemStacks[4], this.containerItemStacks[5]};
            return this.getCraftPermutation(itemstacks) != null;
        }
        ItemStack[] itemstacks = new ItemStack[]{this.containerItemStacks[0], this.containerItemStacks[1]};
        return this.getCraftPermutation(itemstacks) != null;
    }

    @Override
    public void proceedCraft() {
        int i;
        ItemStack[] itemstacks;
        int[] perm;
        ItemStack[] mats;
        int method = 0;
        if (this.containerItemStacks[4] == null && this.containerItemStacks[5] == null) {
            mats = new ItemStack[]{this.containerItemStacks[0], this.containerItemStacks[1]};
            perm = this.getCraftPermutation(mats);
            if (perm == null) {
                throw new RuntimeException("Invalid recipe reference : The permutation variable is null!");
            }
            itemstacks = new ItemStack[perm.length];
            for (i = 0; i < perm.length; ++i) {
                itemstacks[i] = mats[perm[i]];
            }
            this.machineConsumingEnergy = (long)((float)this.recipe.getEnergy(itemstacks, method, this.baseTier) * this.multConsumingEnergy);
        }
        if (this.consumeClayEnergy(this.machineConsumingEnergy)) {
            if (this.containerItemStacks[4] == null && this.containerItemStacks[5] == null) {
                mats = new ItemStack[]{this.containerItemStacks[0], this.containerItemStacks[1]};
                perm = this.getCraftPermutation(mats);
                if (perm == null) {
                    throw new RuntimeException("Invalid recipe reference : The permutation variable is null!");
                }
                itemstacks = new ItemStack[perm.length];
                for (i = 0; i < perm.length; ++i) {
                    itemstacks[i] = mats[perm[i]];
                }
                this.machineTimeToCraft = (long)((float)this.recipe.getTime(itemstacks, method, this.baseTier) * this.multCraftTime);
                int[] consumedStackSize = this.recipe.getConsumedStackSize(itemstacks, method, this.baseTier);
                for (int i2 = 0; i2 < perm.length; ++i2) {
                    this.containerItemStacks[i2 + 4] = this.containerItemStacks[perm[i2]].func_77979_a(consumedStackSize[i2]);
                    if (this.containerItemStacks[perm[i2]].field_77994_a > 0) continue;
                    this.containerItemStacks[perm[i2]] = null;
                }
            }
            ++this.machineCraftTime;
            this.isDoingWork = true;
            if (this.machineCraftTime >= this.machineTimeToCraft) {
                int i3;
                mats = new ItemStack[]{this.containerItemStacks[4], this.containerItemStacks[5]};
                perm = this.getCraftPermutation(mats);
                if (perm == null) {
                    throw new RuntimeException("Invalid recipe reference : The permutation variable is null!");
                }
                itemstacks = new ItemStack[perm.length];
                for (i = 0; i < perm.length; ++i) {
                    itemstacks[i] = mats[perm[i]];
                }
                ItemStack[] results = this.recipe.getResult(itemstacks, method, this.baseTier);
                int[] consumedStackSize = this.recipe.getConsumedStackSize(itemstacks, method, this.baseTier);
                this.machineCraftTime = 0L;
                this.machineConsumingEnergy = 0L;
                for (i3 = 0; i3 < Math.min(this.resultSlotNum, results.length); ++i3) {
                    if (this.containerItemStacks[i3 + 2] == null) {
                        this.containerItemStacks[i3 + 2] = results[i3].func_77946_l();
                        continue;
                    }
                    if (this.containerItemStacks[i3 + 2].func_77973_b() != results[i3].func_77973_b()) continue;
                    this.containerItemStacks[i3 + 2].field_77994_a += results[i3].field_77994_a;
                }
                for (i3 = 0; i3 < perm.length; ++i3) {
                    if ((this.containerItemStacks[i3 + 4].field_77994_a -= consumedStackSize[i3]) > 0) continue;
                    this.containerItemStacks[i3 + 4] = null;
                }
                if (this.externalControlState > 0) {
                    --this.externalControlState;
                    if (this.externalControlState == 0) {
                        this.externalControlState = -1;
                    }
                }
            }
        }
    }
}

