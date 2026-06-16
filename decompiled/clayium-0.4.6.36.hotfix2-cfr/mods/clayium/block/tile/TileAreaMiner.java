/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.MaterialLiquid
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.fluids.FluidRegistry
 */
package mods.clayium.block.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.laser.IClayLaserMachine;
import mods.clayium.block.tile.IAxisAlignedBBContainer;
import mods.clayium.block.tile.IAxisAlignedBBProvider;
import mods.clayium.block.tile.IExternalControl;
import mods.clayium.block.tile.TileClayContainerTiered;
import mods.clayium.block.tile.TileClayMarker;
import mods.clayium.item.filter.ItemFilterTemp;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilTransfer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidRegistry;

public class TileAreaMiner
extends TileClayContainerTiered
implements IClayLaserMachine,
IAxisAlignedBBContainer,
IExternalControl {
    protected AxisAlignedBB aabb;
    public int boxState = 0;
    public int inventoryX = 0;
    public int inventoryY = 0;
    public int filterHarvestSlot = 37;
    public int filterFortuneSlot = 38;
    public int filterSilktouchSlot = 39;
    public long laserEnergy = 0L;
    public int miningX = 0;
    public int miningY = 0;
    public int miningZ = 0;
    public int state = -1;
    public long progress = 0L;
    public long energyPerTick = 1000L;
    public int progressPerTick = 100;
    public int progressPerJob = 400;
    public int maxJobCount = 1000;
    public int maxJobCountInLoop = 10;
    public boolean replaceMode = false;
    public boolean areaMode = false;
    public boolean placeFlag = false;

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, 0, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, 0, 0};
        this.autoInsert = true;
        this.autoExtract = true;
        this.clayEnergySlot = 36;
        this.containerItemStacks = new ItemStack[40];
    }

    @Override
    public void initParamsByTier(int tier) {
        int i;
        this.inventoryY = 3;
        this.inventoryX = 3;
        if (tier >= 10) {
            this.inventoryX = 4;
            this.inventoryY = 2;
        }
        switch (tier) {
            case 6: {
                this.maxAutoExtractDefault = 16;
                this.maxAutoInsertDefault = 16;
                this.autoInsertInterval = 2;
                this.autoExtractInterval = 2;
                break;
            }
            case 7: {
                this.maxAutoExtractDefault = 64;
                this.maxAutoInsertDefault = 64;
                this.autoInsertInterval = 1;
                this.autoExtractInterval = 1;
                break;
            }
            case 8: 
            case 9: 
            case 10: {
                this.maxAutoExtractDefault = 64;
                this.maxAutoInsertDefault = 64;
                this.autoInsertInterval = 1;
                this.autoExtractInterval = 1;
                break;
            }
        }
        int slotNum = this.inventoryX * this.inventoryY;
        int[] slots = new int[slotNum];
        this.slotsDrop = new int[slotNum + 1];
        for (i = 0; i < slots.length; ++i) {
            slots[i] = i;
            this.slotsDrop[i] = i;
        }
        this.listSlotsInsert.add(new int[]{this.clayEnergySlot});
        this.listSlotsExtract.add(slots);
        this.slotsDrop[slotNum] = this.clayEnergySlot;
        if (tier >= 7) {
            this.areaMode = true;
        }
        if (tier >= 10) {
            this.replaceMode = true;
        }
        if (!this.areaMode) {
            this.listSlotsInsert.remove(0);
            for (i = 0; i < this.insertRoutes.length; ++i) {
                if (this.insertRoutes[i] != 0) continue;
                this.insertRoutes[i] = -1;
            }
        }
        if (this.replaceMode) {
            slots = new int[slotNum];
            for (i = 0; i < slots.length; ++i) {
                slots[i] = i + slotNum;
            }
            this.listSlotsInsert.add(slots);
            this.slotsDrop = new int[slotNum * 2 + 1];
            for (i = 0; i < slotNum * 2; ++i) {
                this.slotsDrop[i] = i;
            }
            this.slotsDrop[slotNum * 2] = this.clayEnergySlot;
        } else {
            for (i = 0; i < this.insertRoutes.length; ++i) {
                if (this.insertRoutes[i] != 1) continue;
                this.insertRoutes[i] = 0;
            }
        }
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        if (this.getState() == -1) {
            this.initState();
        }
        if (!this.field_145850_b.field_72995_K) {
            if (!this.hasAxisAlignedBB()) {
                this.searchAABBProvider();
            } else {
                this.doWork();
            }
        }
    }

    public void initState() {
        if (this.areaMode) {
            this.setState(0);
        } else {
            this.setState(3);
        }
    }

    public void searchAABBProvider() {
        if (!this.areaMode) {
            UtilDirection back = UtilDirection.getOrientation(this.getFrontDirection()).getOpposite();
            this.setAxisAlignedBB(AxisAlignedBB.func_72330_a((double)(this.field_145851_c + back.offsetX), (double)(this.field_145848_d + back.offsetY), (double)(this.field_145849_e + back.offsetZ), (double)(this.field_145851_c + 1 + back.offsetX), (double)(this.field_145848_d + 1 + back.offsetY), (double)(this.field_145849_e + 1 + back.offsetZ)));
        } else {
            for (int x = this.field_145851_c - 2; x <= this.field_145851_c + 2; ++x) {
                for (int y = this.field_145848_d - 2; y <= this.field_145848_d + 2; ++y) {
                    for (int z = this.field_145849_e - 2; z <= this.field_145849_e + 2; ++z) {
                        TileEntity tile = UtilBuilder.safeGetTileEntity((IBlockAccess)this.field_145850_b, x, y, z);
                        if (!(tile instanceof IAxisAlignedBBProvider) || !((IAxisAlignedBBProvider)tile).hasAxisAlignedBB()) continue;
                        this.setAxisAlignedBB(((IAxisAlignedBBProvider)tile).getAxisAlignedBB());
                        ((IAxisAlignedBBProvider)tile).setAxisAlignedBBToMachine();
                    }
                }
            }
        }
    }

    public void doWork() {
        AxisAlignedBB aabb = this.getAxisAlignedBB();
        block0 : switch (this.getState()) {
            case 0: 
            case 3: {
                this.miningX = (int)Math.floor(aabb.field_72340_a + 0.5);
                this.miningY = (int)Math.floor(aabb.field_72337_e - 0.5);
                this.miningZ = (int)Math.floor(aabb.field_72339_c + 0.5);
                this.setState(this.getState() + 1);
                break;
            }
            case 1: 
            case 4: {
                long c;
                long l = c = this.areaMode ? (long)((double)this.energyPerTick * (1.0 + 4.0 * Math.log10(this.laserEnergy / 1000L + 1L))) : 0L;
                if (this.miningX > (int)Math.floor(aabb.field_72336_d - 0.5)) {
                    this.miningX = (int)Math.floor(aabb.field_72340_a + 0.5);
                    ++this.miningZ;
                }
                if (this.miningZ > (int)Math.floor(aabb.field_72334_f - 0.5)) {
                    this.miningZ = (int)Math.floor(aabb.field_72339_c + 0.5);
                    --this.miningY;
                }
                if (this.miningY < (int)Math.floor(aabb.field_72338_b + 0.5)) {
                    if (this.getState() == 1) {
                        this.setState(2);
                        break;
                    }
                    this.miningX = (int)Math.floor(aabb.field_72340_a + 0.5);
                    this.miningY = (int)Math.floor(aabb.field_72337_e - 0.5);
                    this.miningZ = (int)Math.floor(aabb.field_72339_c + 0.5);
                }
                if (!this.consumeClayEnergy(c)) break;
                this.setSyncFlag();
                this.progress += (long)((double)this.progressPerTick * (1.0 + 4.0 * Math.log10(this.laserEnergy / 1000L + 1L)));
                this.laserEnergy = 0L;
                ItemStack filter = this.containerItemStacks[this.filterHarvestSlot];
                ItemStack filterF = this.containerItemStacks[this.filterFortuneSlot];
                ItemStack filterS = this.containerItemStacks[this.filterSilktouchSlot];
                boolean filterFlag = ItemFilterTemp.isFilter(filter);
                boolean filterFflag = ItemFilterTemp.isFilter(filterF);
                boolean filterSflag = ItemFilterTemp.isFilter(filterS);
                int slotNum = this.inventoryX * this.inventoryY;
                int max = this.getState() == 1 ? this.maxJobCount : this.maxJobCountInLoop;
                for (int count = 0; count < max; ++count) {
                    Block block = this.field_145850_b.func_147439_a(this.miningX, this.miningY, this.miningZ);
                    boolean isFluid = FluidRegistry.lookupFluidForBlock((Block)block) != null || block.func_149688_o() instanceof MaterialLiquid;
                    double hardness = isFluid ? 1.0 : (double)block.func_149712_f(this.field_145850_b, this.miningX, this.miningY, this.miningZ);
                    long i = (long)((double)this.progressPerJob * (0.1 + hardness));
                    if (block == Blocks.field_150350_a) {
                        i = 0L;
                    }
                    i += (long)(this.replaceMode ? 1 : 0);
                    Boolean flag = true;
                    if (filterFlag) {
                        flag = ItemFilterTemp.match(filter, this.field_145850_b, this.miningX, this.miningY, this.miningZ);
                    }
                    if (hardness == -1.0) {
                        flag = false;
                    }
                    if (flag.booleanValue()) {
                        ItemStack[] items;
                        if (i > this.progress) break block0;
                        this.progress -= i;
                        int fortune = 0;
                        boolean silkTouch = false;
                        if (filterFflag && ItemFilterTemp.match(filterF, this.field_145850_b, this.miningX, this.miningY, this.miningZ)) {
                            fortune = 3;
                        }
                        if (filterSflag && ItemFilterTemp.match(filterS, this.field_145850_b, this.miningX, this.miningY, this.miningZ)) {
                            silkTouch = true;
                            fortune = 0;
                        }
                        if (!UtilTransfer.canProduceItemStacks(items = UtilBuilder.getDropsFromBlock(this.field_145850_b, this.miningX, this.miningY, this.miningZ, silkTouch, fortune), this.containerItemStacks, 0, slotNum, this.func_70297_j_())) break block0;
                        UtilBuilder.destroyBlock(this.field_145850_b, this.miningX, this.miningY, this.miningZ, !silkTouch, silkTouch, fortune);
                        UtilTransfer.produceItemStacks(items, this.containerItemStacks, 0, slotNum, this.func_70297_j_());
                        if (this.replaceMode) {
                            this.placeFlag = true;
                        }
                    }
                    if (this.replaceMode && this.placeFlag) {
                        boolean flag2 = false;
                        if (this.field_145850_b.func_147439_a(this.miningX, this.miningY, this.miningZ) == Blocks.field_150350_a) {
                            for (int j = slotNum; j < slotNum * 2; ++j) {
                                ItemStack itemblock = this.containerItemStacks[j];
                                if (itemblock != null && itemblock.func_77973_b() instanceof ItemBlock) {
                                    flag2 = UtilBuilder.placeBlockByItemBlock(itemblock, this.field_145850_b, this.miningX, this.miningY, this.miningZ);
                                    if (itemblock == null || itemblock.field_77994_a == 0) {
                                        this.containerItemStacks[j] = null;
                                    }
                                }
                                if (flag2) break;
                            }
                        }
                        if (!flag2) {
                            this.progress = 0L;
                            break block0;
                        }
                        this.placeFlag = false;
                    }
                    ++this.miningX;
                    if (this.miningX > (int)Math.floor(aabb.field_72336_d - 0.5)) {
                        this.miningX = (int)Math.floor(aabb.field_72340_a + 0.5);
                        ++this.miningZ;
                    }
                    if (this.miningZ > (int)Math.floor(aabb.field_72334_f - 0.5)) {
                        this.miningZ = (int)Math.floor(aabb.field_72339_c + 0.5);
                        --this.miningY;
                    }
                    if (this.miningY < (int)Math.floor(aabb.field_72338_b + 0.5)) {
                        if (this.getState() == 1) {
                            this.setState(2);
                            break block0;
                        }
                        this.miningX = (int)Math.floor(aabb.field_72340_a + 0.5);
                        this.miningY = (int)Math.floor(aabb.field_72337_e - 0.5);
                        this.miningZ = (int)Math.floor(aabb.field_72339_c + 0.5);
                    }
                    if (count != max - 1) continue;
                    this.progress = 0L;
                }
                break;
            }
        }
    }

    @Override
    public void pushButton(EntityPlayer player, int action) {
        switch (action) {
            case 0: {
                this.setState(0);
                break;
            }
            case 1: {
                this.setState(2);
                break;
            }
            case 2: {
                this.setState(3);
                break;
            }
            case 3: {
                int b = this.getBoxState();
                if (++b > 2) {
                    b = 0;
                }
                this.setBoxState(b);
            }
        }
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.setSyncFlag();
        this.state = state;
    }

    public int getBoxState() {
        return this.boxState;
    }

    public void setBoxState(int boxState) {
        this.setSyncFlag();
        this.boxState = boxState;
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public boolean irradiateClayLaser(ClayLaser laser, UtilDirection direction) {
        this.laserEnergy = (long)((double)this.laserEnergy + laser.getEnergy());
        return true;
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        super.func_145839_a(tagCompound);
        this.boxState = tagCompound.func_74771_c("BoxState");
        TileClayMarker.readAxisAlignedBBFromNBT(tagCompound, this);
        this.readWorkdataFromNBT(tagCompound);
    }

    public void readWorkdataFromNBT(NBTTagCompound tagCompound) {
        this.laserEnergy = tagCompound.func_74763_f("LaserEnergy");
        this.miningX = tagCompound.func_74762_e("MiningX");
        this.miningY = tagCompound.func_74762_e("MiningY");
        this.miningZ = tagCompound.func_74762_e("MiningZ");
        this.state = tagCompound.func_74771_c("State");
        this.progress = tagCompound.func_74763_f("Progress");
        this.placeFlag = tagCompound.func_74767_n("PlaceFlag");
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        super.func_145841_b(tagCompound);
        tagCompound.func_74774_a("BoxState", (byte)this.boxState);
        TileClayMarker.writeAxisAlignedBBToNBT(tagCompound, this);
        this.writeWorkdataToNBT(tagCompound);
    }

    public void writeWorkdataToNBT(NBTTagCompound tagCompound) {
        tagCompound.func_74772_a("LaserEnergy", this.laserEnergy);
        tagCompound.func_74768_a("MiningX", this.miningX);
        tagCompound.func_74768_a("MiningY", this.miningY);
        tagCompound.func_74768_a("MiningZ", this.miningZ);
        tagCompound.func_74774_a("State", (byte)this.state);
        tagCompound.func_74772_a("Progress", this.progress);
        tagCompound.func_74757_a("PlaceFlag", this.placeFlag);
    }

    @Override
    public AxisAlignedBB getAxisAlignedBB() {
        return this.aabb;
    }

    @Override
    public void setAxisAlignedBB(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }

    @Override
    public boolean hasAxisAlignedBB() {
        return this.aabb != null;
    }

    @Override
    public int getBoxAppearance() {
        return this.boxState;
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @SideOnly(value=Side.CLIENT)
    public double func_145833_n() {
        return Double.POSITIVE_INFINITY;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        if (slot == this.filterHarvestSlot || slot == this.filterFortuneSlot || slot == this.filterSilktouchSlot) {
            return ItemFilterTemp.isFilter(itemstack);
        }
        return super.func_94041_b(slot, itemstack);
    }

    @Override
    public void doWorkOnce() {
        this.setState(0);
    }

    @Override
    public void startWork() {
        this.setState(4);
    }

    @Override
    public void stopWork() {
        this.setState(2);
    }

    @Override
    public boolean isScheduled() {
        return true;
    }

    @Override
    public boolean isDoingWork() {
        return this.getState() == 1 || this.getState() == 4;
    }
}

