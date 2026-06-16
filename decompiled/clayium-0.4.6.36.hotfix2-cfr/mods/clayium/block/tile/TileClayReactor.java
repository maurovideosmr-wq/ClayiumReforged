/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.block.CBlocks;
import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.laser.IClayLaserMachine;
import mods.clayium.block.tile.ISynchronizedInterface;
import mods.clayium.block.tile.TileClayContainerInterface;
import mods.clayium.block.tile.TileClayLaserInterface;
import mods.clayium.block.tile.TileMultiblockMachines;
import mods.clayium.block.tile.TileRedstoneInterface;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class TileClayReactor
extends TileMultiblockMachines
implements IClayLaserMachine {
    public int recipeTier = 0;
    public ClayLaser irradiatedLaser;
    private Recipes recipe;
    protected int resultSlotNum = 2;

    @Override
    public boolean isConstructed() {
        int sum = 0;
        int num = 0;
        boolean flag = true;
        for (int yy = -1; yy <= 1; ++yy) {
            for (int xx = -1; xx <= 1; ++xx) {
                for (int zz = 0; zz <= 2; ++zz) {
                    int[] coord1;
                    int[] coord;
                    if (xx == 0 && yy == 0 && zz == 0) continue;
                    ++num;
                    int atier = this.getMachineTier(xx, yy, zz);
                    if (atier <= 6) {
                        flag = false;
                    }
                    sum = (int)((double)sum + Math.pow(2.0, 16 - atier));
                    TileEntity te = this.getTileEntity(xx, yy, zz);
                    if (te != null && (te instanceof TileClayContainerInterface || te instanceof TileRedstoneInterface)) {
                        coord = this.getRelativeCoord(0, 0, 0);
                        coord1 = this.getRelativeCoord(xx, yy, zz);
                        ((ISynchronizedInterface)te).setCoreBlockCoord(coord[0] - coord1[0], coord[1] - coord1[1], coord[2] - coord1[2]);
                    }
                    if (yy == 1 && xx == 0 && zz == 1) {
                        if (te != null && te instanceof TileClayLaserInterface) {
                            coord = this.getRelativeCoord(0, 0, 0);
                            coord1 = this.getRelativeCoord(xx, yy, zz);
                            ((TileClayLaserInterface)te).setCoreBlockCoord(coord[0] - coord1[0], coord[1] - coord1[1], coord[2] - coord1[2]);
                            continue;
                        }
                        flag = false;
                        continue;
                    }
                    if (te == null || !(te instanceof TileClayLaserInterface)) continue;
                    flag = false;
                }
            }
        }
        this.recipeTier = Math.max((int)(16.0 - Math.floor(Math.log(sum / num) / Math.log(2.0) + 0.5)), 0);
        return flag;
    }

    @Override
    public boolean acceptEnergyClay() {
        return true;
    }

    @Override
    protected void onConstruction() {
        this.setRenderSyncFlag();
    }

    @Override
    protected void onDestruction() {
        this.setRenderSyncFlag();
        this.machineCraftTime = 0L;
        this.containerItemStacks[5] = null;
        this.containerItemStacks[4] = null;
    }

    protected int getMachineTier(int xx, int yy, int zz) {
        Block block = this.getBlock(xx, yy, zz);
        if (block == CBlocks.blockMachineHull) {
            return this.getBlockMetadata(xx, yy, zz) + 1;
        }
        if (block == CBlocks.blockOverclocker) {
            int[] coord = this.getRelativeCoord(xx, yy, zz);
            return CBlocks.blockOverclocker.getTier((IBlockAccess)this.field_145850_b, coord[0], coord[1], coord[2]);
        }
        TileEntity te = this.getTileEntity(xx, yy, zz);
        if (te != null && te instanceof TileClayContainerInterface) {
            return ((TileClayContainerInterface)te).getTier();
        }
        if (te != null && te instanceof TileRedstoneInterface) {
            return this.getBlockTier(xx, yy, zz);
        }
        if (te != null && te instanceof TileClayLaserInterface) {
            return ((TileClayLaserInterface)te).getTier();
        }
        return -1;
    }

    @Override
    public void initParamsByTier(int tier) {
    }

    @Override
    public int getRecipeTier() {
        return this.recipeTier;
    }

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
        this.listSlotsInsert = new ArrayList();
        this.listSlotsExtract = new ArrayList();
        this.listSlotsInsert.add(new int[]{0});
        this.listSlotsInsert.add(new int[]{1});
        this.listSlotsInsert.add(new int[]{0, 1});
        this.listSlotsInsert.add(new int[]{6});
        this.listSlotsExtract.add(new int[]{2});
        this.listSlotsExtract.add(new int[]{3});
        this.listSlotsExtract.add(new int[]{2, 3});
        this.insertRoutes = new int[]{-1, 2, -1, 3, -1, -1};
        this.maxAutoExtract = new int[]{64, 64, 64, 1};
        this.extractRoutes = new int[]{2, -1, -1, -1, -1, -1};
        this.maxAutoInsert = new int[]{64, 64, 64};
        this.autoInsertInterval = 1;
        this.autoExtractInterval = 1;
        this.slotsDrop = new int[]{0, 1, 2, 3, 6};
        this.autoInsert = true;
        this.autoExtract = true;
    }

    protected boolean canCraft(ItemStack[] materials) {
        int method = 0;
        if (materials == null || this.recipe == null) {
            return false;
        }
        ItemStack[] itemstacks = this.recipe.getResult(materials, method, this.recipeTier);
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
    public boolean canProceedCraftWhenConstructed() {
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
            this.machineConsumingEnergy = (long)((float)this.recipe.getEnergy(itemstacks, method, this.recipeTier) * this.multConsumingEnergy);
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
                this.machineTimeToCraft = (long)((float)this.recipe.getTime(itemstacks, method, this.recipeTier) * this.multCraftTime);
                int[] consumedStackSize = this.recipe.getConsumedStackSize(itemstacks, method, this.recipeTier);
                for (int i2 = 0; i2 < perm.length; ++i2) {
                    this.containerItemStacks[i2 + 4] = this.containerItemStacks[perm[i2]].func_77979_a(consumedStackSize[i2]);
                    if (this.containerItemStacks[perm[i2]].field_77994_a > 0) continue;
                    this.containerItemStacks[perm[i2]] = null;
                }
            }
            if (this.irradiatedLaser != null) {
                this.machineCraftTime += (long)this.irradiatedLaser.getEnergy();
                this.irradiatedLaser = null;
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
                ItemStack[] results = this.recipe.getResult(itemstacks, method, this.recipeTier);
                int[] consumedStackSize = this.recipe.getConsumedStackSize(itemstacks, method, this.recipeTier);
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

    @Override
    public boolean irradiateClayLaser(ClayLaser laser, UtilDirection direction) {
        this.irradiatedLaser = laser;
        this.setSyncFlag();
        return true;
    }
}

