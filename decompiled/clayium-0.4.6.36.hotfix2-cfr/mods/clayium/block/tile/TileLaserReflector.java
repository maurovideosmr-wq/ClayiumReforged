/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.AxisAlignedBB
 */
package mods.clayium.block.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.laser.ClayLaserManager;
import mods.clayium.block.laser.IClayLaserMachine;
import mods.clayium.block.laser.IClayLaserManager;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.util.UtilDirection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileLaserReflector
extends TileClayContainer
implements IClayLaserMachine,
IClayLaserManager {
    protected int machineConsumingEnergy;
    protected ClayLaser machineClayLaser;
    protected List<ClayLaser> listIrradiatedClayLasers = new ArrayList<ClayLaser>();
    protected List<Long> irradiatedTime = new ArrayList<Long>();
    protected ClayLaserManager manager;
    @SideOnly(value=Side.CLIENT)
    private AxisAlignedBB boundingBox;

    public TileLaserReflector() {
        this.insertRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = false;
        this.autoExtract = false;
        this.containerItemStacks = new ItemStack[1];
        this.slotsDrop = new int[0];
        this.manager = new ClayLaserManager();
    }

    @Override
    public boolean irradiateClayLaser(ClayLaser laser, UtilDirection direction) {
        if (laser == null) {
            return false;
        }
        long time = this.field_145850_b.func_72912_H().func_82573_f();
        this.irradiatedTime.add(time);
        this.listIrradiatedClayLasers.add(laser);
        return true;
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        long time = this.field_145850_b.func_72912_H().func_82573_f();
        boolean flag = false;
        int num = 0;
        for (int i = 0; i < this.listIrradiatedClayLasers.size(); ++i) {
            if (this.irradiatedTime.get(i) == time) continue;
            ++num;
        }
        ClayLaser[] array = new ClayLaser[num];
        ArrayList<ClayLaser> newListIrradiatedClayLasers = new ArrayList<ClayLaser>();
        ArrayList<Long> newIrradiatedTime = new ArrayList<Long>();
        int j = 0;
        for (int i = 0; i < this.listIrradiatedClayLasers.size(); ++i) {
            if (this.irradiatedTime.get(i) != time) {
                array[j] = this.listIrradiatedClayLasers.get(i);
                ++j;
                flag = true;
                continue;
            }
            newListIrradiatedClayLasers.add(this.listIrradiatedClayLasers.get(i));
            newIrradiatedTime.add(this.irradiatedTime.get(i));
        }
        if (!flag) {
            this.machineClayLaser = null;
        } else {
            this.machineClayLaser = ClayLaser.mergeClayLasers(array);
            ++this.machineClayLaser.age;
            this.listIrradiatedClayLasers = newListIrradiatedClayLasers;
            this.irradiatedTime = newIrradiatedTime;
        }
        this.manager.set(this.func_145831_w(), this.field_145851_c, this.field_145848_d, this.field_145849_e, UtilDirection.getOrientation(this.func_145832_p()));
        this.manager.clayLaser = this.machineClayLaser;
        this.manager.update(flag);
    }

    @Override
    public boolean isUsable(ItemStack itemStack, EntityPlayer player, int direction, float hitX, float hitY, float hitZ) {
        return this.getItemUseMode(itemStack, player) == 20;
    }

    @Override
    public int getItemUseMode(ItemStack itemStack, EntityPlayer player) {
        int res = super.getItemUseMode(itemStack, player);
        return res == 2 ? 20 : res;
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        super.func_145839_a(tagCompound);
        this.manager.readFromNBT(tagCompound.func_74775_l("ClayEnergyManager"));
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        super.func_145841_b(tagCompound);
        NBTTagCompound tagCompound1 = new NBTTagCompound();
        this.manager.writeToNBT(tagCompound1);
        tagCompound.func_74782_a("ClayEnergyManager", (NBTBase)tagCompound1);
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public ClayLaser getClayLaser() {
        return this.manager == null ? null : this.manager.clayLaser;
    }

    @Override
    public UtilDirection getDirection() {
        return this.manager == null ? null : this.manager.getDirection();
    }

    @Override
    public int getLaserLength() {
        return this.manager == null ? 0 : this.manager.getLaserLength();
    }

    @Override
    public int[] getTargetCoord() {
        return this.manager == null ? null : this.manager.getTargetCoord();
    }

    @Override
    public boolean hasTarget() {
        return this.manager == null ? false : this.manager.hasTarget();
    }

    @Override
    public boolean isIrradiating() {
        return this.manager == null ? false : this.manager.isIrradiating();
    }

    @SideOnly(value=Side.CLIENT)
    public double func_145833_n() {
        return Double.POSITIVE_INFINITY;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        UtilDirection direction = this.getDirection();
        if (direction != null) {
            if (this.boundingBox == null) {
                this.boundingBox = super.getRenderBoundingBox().func_72329_c();
            }
            int l = this.getLaserLength();
            this.boundingBox.field_72336_d = (double)Math.max(this.field_145851_c, this.field_145851_c + direction.offsetX * l) + 1.0;
            this.boundingBox.field_72340_a = Math.min(this.field_145851_c, this.field_145851_c + direction.offsetX * l);
            this.boundingBox.field_72337_e = (double)Math.max(this.field_145848_d, this.field_145848_d + direction.offsetY * l) + 1.0;
            this.boundingBox.field_72338_b = Math.min(this.field_145848_d, this.field_145848_d + direction.offsetY * l);
            this.boundingBox.field_72334_f = (double)Math.max(this.field_145849_e, this.field_145849_e + direction.offsetZ * l) + 1.0;
            this.boundingBox.field_72339_c = Math.min(this.field_145849_e, this.field_145849_e + direction.offsetZ * l);
            return this.boundingBox;
        }
        return super.getRenderBoundingBox();
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}

