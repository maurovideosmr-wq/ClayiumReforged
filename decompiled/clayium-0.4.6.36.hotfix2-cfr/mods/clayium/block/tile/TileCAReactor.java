/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagIntArray
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.CBlocks;
import mods.clayium.block.ClayInterface;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.RedstoneInterface;
import mods.clayium.block.tile.ISynchronizedInterface;
import mods.clayium.block.tile.TileMultiblockMachines;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.UtilTransfer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;

public class TileCAReactor
extends TileMultiblockMachines {
    public int reactorTier = 0;
    public double reactorRank = 0.0;
    public int reactorHullNum = 0;
    public int reactorHullMaxRank = 0;
    public static double efficiency = 0.2;
    public static double efficiencyBasePerNum = 1.02;
    public static double efficiencyBasePerRank = 7.5;
    public static double energyBase = 1.01;
    public static int reactorHullMinNum = 50;
    public List<int[]> coilCoords;
    public List<int[]> hullCoords;
    public static final int[][] offsets = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};
    public static final int[][] offsetsd = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}, {1, 1, 0}, {1, -1, 0}, {-1, 1, 0}, {-1, -1, 0}, {1, 0, 1}, {1, 0, -1}, {-1, 0, 1}, {-1, 0, -1}, {0, 1, 1}, {0, 1, -1}, {0, -1, 1}, {0, -1, -1}};
    public static int maxLength = 128;
    public String errorMessage = "";

    public double getEfficiency() {
        return efficiency * Math.pow(efficiencyBasePerNum, this.reactorHullNum) * Math.pow(efficiencyBasePerRank, this.reactorRank);
    }

    @Override
    public boolean isConstructed() {
        this.errorMessage = "";
        this.reactorHullNum = 0;
        this.reactorRank = 0.0;
        int xx0 = 0;
        int yy0 = 0;
        int zz0 = 0;
        int xx1 = 0;
        int yy1 = 0;
        int zz1 = 0;
        boolean res = true;
        if (this.coilCoords == null || this.coilCoords.size() == 0) {
            if (this.coilCoords == null) {
                this.coilCoords = new ArrayList<int[]>();
            }
            xx0 = 0;
            yy0 = 0;
            zz0 = 0;
            xx1 = 0;
            yy1 = 0;
            zz1 = 0;
            Object offsets0 = new int[][]{{0, 0, 1}};
            int i = 0;
            while (true) {
                Object offset1 = null;
                int count = 0;
                for (int[] offset : offsets0) {
                    if (this.getBlock(xx1 + offset[0], yy1 + offset[1], zz1 + offset[2]) != CBlocks.blockCAReactorCoil) continue;
                    if (xx0 != xx1 + offset[0] || yy0 != yy1 + offset[1] || zz0 != zz1 + offset[2]) {
                        offset1 = offset;
                    }
                    ++count;
                }
                if (count > 2 || offset1 == null || i == maxLength + 1) {
                    int[] rel = this.getRelativeCoord(xx1, yy1, zz1);
                    this.errorMessage = "Coil [" + rel[0] + "," + rel[1] + "," + rel[2] + "] is invalid.";
                    if (UtilLocale.canLocalize("message.CAReactor.invalidCoil")) {
                        this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.invalidCoil", rel[0], rel[1], rel[2]);
                    }
                    if (i == maxLength + 1) {
                        this.errorMessage = "The number of coils must not be higher than " + maxLength + ".";
                        if (UtilLocale.canLocalize("message.CAReactor.tooManyCoils")) {
                            this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.tooManyCoils", maxLength);
                        }
                    }
                    res = false;
                    this.coilCoords = null;
                    break;
                }
                xx0 = xx1;
                yy0 = yy1;
                zz0 = zz1;
                xx1 += offset1[0];
                yy1 += offset1[1];
                zz1 += offset1[2];
                if (xx0 != 0 || yy0 != 0 || zz0 != 0) {
                    this.coilCoords.add(new int[]{xx0, yy0, zz0});
                }
                offsets0 = offsetsd;
                if (this.coilCoords.size() != 0 && this.coilCoords.get(0)[0] == xx1 && this.coilCoords.get(0)[1] == yy1 && this.coilCoords.get(0)[2] == zz1) break;
                ++i;
            }
        }
        if (this.coilCoords != null && this.coilCoords.size() != 0) {
            int minTier = 99;
            for (int[] coilCoord : this.coilCoords) {
                Block block = this.getBlock(coilCoord[0], coilCoord[1], coilCoord[2]);
                int meta = this.getBlockMetadata(coilCoord[0], coilCoord[1], coilCoord[2]);
                if (block == CBlocks.blockCAReactorCoil) {
                    String id = CBlocks.blockCAReactorCoil.getBlockName(meta);
                    if (id == "antimatter") {
                        minTier = Math.min(minTier, 10);
                    } else if (id == "pureantimatter") {
                        minTier = Math.min(minTier, 11);
                    } else if (id == "oec") {
                        minTier = Math.min(minTier, 12);
                    } else if (id == "opa") {
                        minTier = Math.min(minTier, 13);
                    }
                    if (minTier >= this.reactorTier) continue;
                    int[] rel = this.getRelativeCoord(coilCoord[0], coilCoord[1], coilCoord[2]);
                    this.errorMessage = "Coil [" + rel[0] + "," + rel[1] + "," + rel[2] + "]'s tier is insufficient.";
                    if (UtilLocale.canLocalize("message.CAReactor.insufficientTierCoil")) {
                        this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.insufficientTierCoil", rel[0], rel[1], rel[2]);
                    }
                    res = false;
                    break;
                }
                int[] rel = this.getRelativeCoord(coilCoord[0], coilCoord[1], coilCoord[2]);
                this.errorMessage = "Coil [" + rel[0] + "," + rel[1] + "," + rel[2] + "] is invalid.";
                if (UtilLocale.canLocalize("message.CAReactor.invalidCoil")) {
                    this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.invalidCoil", rel[0], rel[1], rel[2]);
                }
                res = false;
                this.coilCoords = null;
                break;
            }
            if (this.coilCoords != null && this.coilCoords.size() != 0 && res) {
                if (this.hullCoords == null || this.hullCoords.size() == 0) {
                    if (this.hullCoords == null) {
                        this.hullCoords = new ArrayList<int[]>();
                    }
                    block3: for (int[] coilCoord : this.coilCoords) {
                        for (int[] offset : offsets) {
                            Block block = this.getBlock(coilCoord[0] + offset[0], coilCoord[1] + offset[1], coilCoord[2] + offset[2]);
                            if (block == CBlocks.blockCAReactorHull || block instanceof ClayInterface || block instanceof RedstoneInterface) {
                                boolean flag = true;
                                for (int[] hullCoord : this.hullCoords) {
                                    if (hullCoord[0] != coilCoord[0] + offset[0] || hullCoord[1] != coilCoord[1] + offset[1] || hullCoord[2] != coilCoord[2] + offset[2]) continue;
                                    flag = false;
                                }
                                if (!flag) continue;
                                this.hullCoords.add(new int[]{coilCoord[0] + offset[0], coilCoord[1] + offset[1], coilCoord[2] + offset[2]});
                                continue;
                            }
                            if (block == CBlocks.blockCAReactorCoil || coilCoord[0] + offset[0] == 0 && coilCoord[1] + offset[1] == 0 && coilCoord[2] + offset[2] == 0) continue;
                            int[] rel = this.getRelativeCoord(coilCoord[0] + offset[0], coilCoord[1] + offset[1], coilCoord[2] + offset[2]);
                            this.errorMessage = "Hull [" + rel[0] + "," + rel[1] + "," + rel[2] + "] is invalid.";
                            if (UtilLocale.canLocalize("message.CAReactor.invalidHull")) {
                                this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.invalidHull", rel[0], rel[1], rel[2]);
                            }
                            res = false;
                            this.hullCoords = null;
                            break block3;
                        }
                    }
                }
                if (this.hullCoords != null && this.hullCoords.size() != 0) {
                    int numberOfHulls = 0;
                    int hullRankSum = 0;
                    for (int[] hullCoord : this.hullCoords) {
                        int[] rel;
                        Block block = this.getBlock(hullCoord[0], hullCoord[1], hullCoord[2]);
                        int meta = this.getBlockMetadata(hullCoord[0], hullCoord[1], hullCoord[2]);
                        if (block == CBlocks.blockCAReactorHull) {
                            ++numberOfHulls;
                            hullRankSum += meta;
                            if (meta <= this.reactorHullMaxRank) continue;
                            rel = this.getRelativeCoord(hullCoord[0], hullCoord[1], hullCoord[2]);
                            this.errorMessage = "Hull [" + rel[0] + "," + rel[1] + "," + rel[2] + "]'s tier is too high.";
                            if (UtilLocale.canLocalize("message.CAReactor.tooHighTierHull")) {
                                this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.tooHighTierHull", rel[0], rel[1], rel[2]);
                            }
                            res = false;
                            break;
                        }
                        if (block instanceof ClayInterface || block instanceof RedstoneInterface) {
                            int[] coord = this.getRelativeCoord(0, 0, 0);
                            int[] rel2 = this.getRelativeCoord(hullCoord[0], hullCoord[1], hullCoord[2]);
                            int tier = ((ITieredBlock)block).getTier((IBlockAccess)this.field_145850_b, rel2[0], rel2[1], rel2[2]);
                            if (tier < this.reactorTier) {
                                this.errorMessage = "Interface [" + rel2[0] + "," + rel2[1] + "," + rel2[2] + "]'s tier is insufficient.";
                                if (UtilLocale.canLocalize("message.CAReactor.insufficientTierInterface")) {
                                    this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.insufficientTierInterface", rel2[0], rel2[1], rel2[2]);
                                }
                            }
                            ((ISynchronizedInterface)this.getTileEntity(hullCoord[0], hullCoord[1], hullCoord[2])).setCoreBlockCoord(coord[0] - rel2[0], coord[1] - rel2[1], coord[2] - rel2[2]);
                            continue;
                        }
                        rel = this.getRelativeCoord(hullCoord[0], hullCoord[1], hullCoord[2]);
                        this.errorMessage = "Hull [" + rel[0] + "," + rel[1] + "," + rel[2] + "] is invalid.";
                        if (UtilLocale.canLocalize("message.CAReactor.invalidHull")) {
                            this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.invalidHull", rel[0], rel[1], rel[2]);
                        }
                        res = false;
                        this.hullCoords = null;
                        break;
                    }
                    if (numberOfHulls < reactorHullMinNum && res) {
                        this.errorMessage = "Reactor size(" + numberOfHulls + ") is less than minimum size(" + reactorHullMinNum + ").";
                        if (UtilLocale.canLocalize("message.CAReactor.invalidReactorSize")) {
                            this.errorMessage = UtilLocale.localizeAndFormat("message.CAReactor.invalidReactorSize", numberOfHulls, reactorHullMinNum);
                        }
                        res = false;
                    }
                    if (res) {
                        this.reactorHullNum = numberOfHulls;
                        this.reactorRank = (double)hullRankSum / (double)numberOfHulls;
                    }
                }
            }
        }
        return res;
    }

    @Override
    protected void onConstruction() {
        this.setRenderSyncFlag();
    }

    @Override
    protected void onDestruction() {
        this.setRenderSyncFlag();
        this.machineCraftTime = 0L;
        this.containerItemStacks[2] = null;
    }

    @Override
    public void initParamsByTier(int tier) {
        super.initParamsByTier(tier);
        this.reactorTier = tier;
        switch (tier) {
            case 10: {
                this.reactorHullMaxRank = 1;
                break;
            }
            case 11: {
                this.reactorHullMaxRank = 5;
                break;
            }
            default: {
                this.reactorHullMaxRank = 9;
            }
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        int i;
        NBTTagList tagList;
        super.func_145839_a(tagCompound);
        this.reactorTier = tagCompound.func_74765_d("ReactorTier");
        this.reactorRank = tagCompound.func_74769_h("ReactorRank");
        this.reactorHullNum = tagCompound.func_74765_d("ReactorHullNum");
        if (tagCompound.func_74767_n("HasCoilCoords")) {
            tagList = tagCompound.func_150295_c("CoilCoords", 11);
            this.coilCoords = new ArrayList<int[]>();
            for (i = 0; i < tagList.func_74745_c(); ++i) {
                this.coilCoords.add(tagList.func_150306_c(i));
            }
        } else {
            this.coilCoords = null;
        }
        if (tagCompound.func_74767_n("HasHullCoords")) {
            tagList = tagCompound.func_150295_c("HullCoords", 11);
            this.hullCoords = new ArrayList<int[]>();
            for (i = 0; i < tagList.func_74745_c(); ++i) {
                this.hullCoords.add(tagList.func_150306_c(i));
            }
        } else {
            this.hullCoords = null;
        }
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        int i;
        NBTTagList tagList;
        super.func_145841_b(tagCompound);
        tagCompound.func_74777_a("ReactorTier", (short)this.reactorTier);
        tagCompound.func_74780_a("ReactorRank", this.reactorRank);
        tagCompound.func_74777_a("ReactorHullNum", (short)this.reactorHullNum);
        tagCompound.func_74757_a("HasCoilCoords", this.coilCoords != null);
        if (this.coilCoords != null) {
            tagList = new NBTTagList();
            for (i = 0; i < this.coilCoords.size(); ++i) {
                tagList.func_74742_a((NBTBase)new NBTTagIntArray(this.coilCoords.get(i)));
            }
            tagCompound.func_74782_a("CoilCoords", (NBTBase)tagList);
        }
        tagCompound.func_74757_a("HasHullCoords", this.hullCoords != null);
        if (this.hullCoords != null) {
            tagList = new NBTTagList();
            for (i = 0; i < this.hullCoords.size(); ++i) {
                tagList.func_74742_a((NBTBase)new NBTTagIntArray(this.hullCoords.get(i)));
            }
            tagCompound.func_74782_a("HullCoords", (NBTBase)tagList);
        }
    }

    @Override
    public boolean canOverclock() {
        return this.reactorTier >= 13;
    }

    @Override
    public void pushButton(EntityPlayer player, int action) {
        if (action == 1 && !player.func_130014_f_().field_72995_K) {
            player.func_145747_a((IChatComponent)new ChatComponentText(this.errorMessage));
        }
    }

    @Override
    public boolean canProceedCraftWhenConstructed() {
        if (this.containerItemStacks[2] != null) {
            return true;
        }
        int rank = Math.min((int)this.reactorRank, 8);
        return UtilItemStack.areTypeEqual(this.containerItemStacks[0], CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)) && UtilTransfer.canProduceItemStack(CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[rank], CMaterials.GEM), this.containerItemStacks, 1, this.func_70297_j_()) >= 1;
    }

    @Override
    public void proceedCraft() {
        if (this.containerItemStacks[2] == null) {
            this.machineConsumingEnergy = (long)((float)this.recipe.getEnergy(this.containerItemStacks[0], this.baseTier) * this.multConsumingEnergy);
            this.machineConsumingEnergy = (long)((double)this.machineConsumingEnergy * this.getConsumingEnergyBaseMultiplier());
        }
        if (this.consumeClayEnergy(this.machineConsumingEnergy)) {
            if (this.containerItemStacks[2] == null) {
                this.machineTimeToCraft = (long)((float)(1000L * this.recipe.getTime(this.containerItemStacks[0], this.baseTier)) * this.multCraftTime);
                int rank = this.getPureAntimatterRank();
                this.machineTimeToCraft = (long)((double)this.machineTimeToCraft * this.getCraftTimePureAntimatterMultiplier());
                this.containerItemStacks[2] = this.getResultPureAntimatter();
                if (--this.containerItemStacks[0].field_77994_a <= 0) {
                    this.containerItemStacks[0] = null;
                }
            }
            this.machineCraftTime = (long)((double)this.machineCraftTime + 1000.0 * this.getEfficiency());
            this.isDoingWork = true;
            if (this.machineCraftTime >= this.machineTimeToCraft) {
                this.machineCraftTime = 0L;
                this.machineConsumingEnergy = 0L;
                UtilTransfer.produceItemStack(this.containerItemStacks[2], this.containerItemStacks, 1, this.func_70297_j_());
                this.containerItemStacks[2] = null;
                if (this.externalControlState > 0) {
                    --this.externalControlState;
                    if (this.externalControlState == 0) {
                        this.externalControlState = -1;
                    }
                }
            }
        }
    }

    public int getPureAntimatterRank() {
        return Math.min((int)this.reactorRank, 8);
    }

    public double getCraftTimeTotalMultiplier() {
        return this.getCraftTimePureAntimatterMultiplier() * (double)((long)this.getCraftTimeBaseMultiplier()) / this.getEfficiency();
    }

    public double getCraftTimePureAntimatterMultiplier() {
        return Math.pow(9.0, this.getPureAntimatterRank());
    }

    public float getCraftTimeBaseMultiplier() {
        return 1.0f;
    }

    public double getConsumingEnergyBaseMultiplier() {
        return Math.pow(energyBase, (double)this.reactorHullNum * this.reactorRank);
    }

    public double getConsumingEnergyTotalMultiplier() {
        return this.getConsumingEnergyBaseMultiplier() * (double)this.multConsumingEnergy;
    }

    public ItemStack getResultPureAntimatter() {
        int rank = this.getPureAntimatterRank();
        return rank >= 0 && rank < CMaterials.COMPRESSED_PURE_ANTIMATTER.length ? CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[rank], CMaterials.GEM) : null;
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }
}

