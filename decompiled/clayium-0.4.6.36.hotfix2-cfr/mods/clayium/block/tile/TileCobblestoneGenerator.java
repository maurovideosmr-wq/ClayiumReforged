/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.common.util.ForgeDirection
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.block.tile.IExternalControl;
import mods.clayium.block.tile.TileClayBuffer;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilTransfer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCobblestoneGenerator
extends TileClayBuffer
implements IExternalControl {
    public int progress = 0;
    public int progressEfficiency = 5;
    public static int progressMax = 100;
    public int externalControlState = 0;
    public boolean isDoingWork = false;

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, 0, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = false;
    }

    @Override
    public void initParamsByTier(int tier) {
        super.initParamsByTier(tier);
        this.listSlotsInsert = new ArrayList();
        switch (tier) {
            case 1: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(2);
                break;
            }
            case 2: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(5);
                break;
            }
            case 3: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(15);
                break;
            }
            case 4: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(50);
                break;
            }
            case 5: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(200);
                break;
            }
            case 6: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(1000);
                break;
            }
            case 7: {
                this.progressEfficiency = ClayiumCore.multiplyProgressionRateI(8000);
                break;
            }
        }
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean func_102007_a(int slot, ItemStack itemstack, int side) {
        return false;
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        super.func_145839_a(tagCompound);
        this.progress = tagCompound.func_74762_e("Progress");
        this.progressEfficiency = tagCompound.func_74762_e("ProgressEfficiency");
        this.externalControlState = tagCompound.func_74762_e("ExternalControlState");
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        super.func_145841_b(tagCompound);
        tagCompound.func_74768_a("Progress", this.progress);
        tagCompound.func_74768_a("ProgressEfficiency", this.progressEfficiency);
        tagCompound.func_74768_a("ExternalControlState", this.externalControlState);
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        if (!this.field_145850_b.field_72995_K) {
            this.produce();
        }
    }

    public void produce() {
        this.isDoingWork = false;
        if (this.canProduce()) {
            if (this.externalControlState >= 0) {
                this.progress += this.progressEfficiency;
            }
            while (true) {
                if (this.progress < progressMax) break;
                this.setSyncFlag();
                ItemStack cobble = new ItemStack(Blocks.field_150347_e);
                this.progress -= progressMax;
                UtilTransfer.produceItemStack(cobble, this.containerItemStacks, 0, this.inventoryX * this.inventoryY, this.func_70297_j_());
                if (this.externalControlState > 0) {
                    --this.externalControlState;
                    if (this.externalControlState == 0) {
                        this.externalControlState = -1;
                    }
                }
                this.isDoingWork = true;
            }
        }
    }

    public boolean canProduce() {
        ForgeDirection[] sides = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST};
        boolean flag = false;
        boolean flag2 = false;
        for (ForgeDirection side : sides) {
            Block block = this.field_145850_b.func_147439_a(this.field_145851_c + side.offsetX, this.field_145848_d + side.offsetY, this.field_145849_e + side.offsetZ);
            if (block.func_149688_o() == Material.field_151586_h) {
                flag = true;
            }
            if (block.func_149688_o() != Material.field_151587_i) continue;
            flag2 = true;
        }
        return flag && flag2;
    }

    @Override
    public void doWorkOnce() {
        this.externalControlState = this.externalControlState > 0 ? ++this.externalControlState : 1;
    }

    @Override
    public void startWork() {
        this.externalControlState = 0;
    }

    @Override
    public void stopWork() {
        this.externalControlState = -1;
    }

    @Override
    public boolean isScheduled() {
        return this.canProduce();
    }

    @Override
    public boolean isDoingWork() {
        return this.isDoingWork;
    }
}

