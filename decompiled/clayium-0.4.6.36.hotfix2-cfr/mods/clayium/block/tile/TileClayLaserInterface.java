/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.DimensionManager
 */
package mods.clayium.block.tile;

import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.laser.IClayLaserMachine;
import mods.clayium.block.tile.IGeneralInterface;
import mods.clayium.block.tile.ISynchronizedInterface;
import mods.clayium.block.tile.TileClayContainerTiered;
import mods.clayium.item.CItems;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class TileClayLaserInterface
extends TileClayContainerTiered
implements IClayLaserMachine,
ISynchronizedInterface {
    protected int coreBlockX = 0;
    protected int coreBlockY = 0;
    protected int coreBlockZ = 0;
    protected boolean synced = false;
    protected boolean loop = false;
    public boolean acceptCoordChanger = false;
    protected boolean interDimensional = false;
    protected int dimentionId = 0;

    @Override
    public void initParams() {
        super.initParams();
        this.containerItemStacks = new ItemStack[1];
        this.insertRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.slotsDrop = new int[]{0};
        this.autoInsert = false;
        this.autoExtract = false;
    }

    @Override
    public boolean setCoreBlockCoord(int x, int y, int z) {
        this.setSyncFlag();
        this.coreBlockX = x;
        this.coreBlockY = y;
        this.coreBlockZ = z;
        return this.syncCoreBlock();
    }

    @Override
    public int getCoreBlockXCoord() {
        return this.coreBlockX;
    }

    @Override
    public int getCoreBlockYCoord() {
        return this.coreBlockY;
    }

    @Override
    public int getCoreBlockZCoord() {
        return this.coreBlockZ;
    }

    protected boolean syncCoreBlock() {
        return this.syncCoreBlock(this.coreBlockX, this.coreBlockY, this.coreBlockZ);
    }

    @Override
    public boolean isSynced() {
        return this.synced;
    }

    protected boolean syncCoreBlock(int x, int y, int z) {
        TileEntity te;
        int coreX = this.field_145851_c + x;
        int coreY = this.field_145848_d + y;
        int coreZ = this.field_145849_e + z;
        if (this.getCoreWorld() != null && (te = UtilBuilder.safeGetTileEntity((IBlockAccess)this.getCoreWorld(), coreX, coreY, coreZ)) instanceof IClayLaserMachine && (x != 0 || y != 0 || z != 0)) {
            this.synced = true;
            return true;
        }
        this.coreBlockZ = 0;
        this.coreBlockY = 0;
        this.coreBlockX = 0;
        this.synced = false;
        return false;
    }

    @Override
    public void func_145845_h() {
        this.syncCoreBlock();
        super.func_145845_h();
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        this.syncCoreBlock();
        this.readCoordFromNBT(tagCompound);
        this.coreBlockX = tagCompound.func_74762_e("CoreBlockX");
        this.coreBlockY = tagCompound.func_74762_e("CoreBlockY");
        this.coreBlockZ = tagCompound.func_74762_e("CoreBlockZ");
        this.acceptCoordChanger = tagCompound.func_74767_n("AcceptCoordChanger");
        this.interDimensional = tagCompound.func_74767_n("InterDimensional");
        this.dimentionId = tagCompound.func_74762_e("CoreBlockDimentionId");
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        this.syncCoreBlock();
        this.writeCoordToNBT(tagCompound);
        tagCompound.func_74768_a("CoreBlockX", this.coreBlockX);
        tagCompound.func_74768_a("CoreBlockY", this.coreBlockY);
        tagCompound.func_74768_a("CoreBlockZ", this.coreBlockZ);
        tagCompound.func_74757_a("AcceptCoordChanger", this.acceptCoordChanger);
        tagCompound.func_74757_a("InterDimensional", this.interDimensional);
        tagCompound.func_74768_a("CoreBlockDimentionId", this.dimentionId);
    }

    @Override
    public int getItemUseMode(ItemStack itemStack, EntityPlayer player) {
        if (itemStack == null) {
            return -1;
        }
        if (UtilItemStack.areItemDamageEqual(itemStack, CItems.itemMisc.get("SynchronousParts"))) {
            return 91;
        }
        return super.getItemUseMode(itemStack, player);
    }

    @Override
    public boolean isUsable(ItemStack itemStack, EntityPlayer player, int direction, float hitX, float hitY, float hitZ) {
        if (this.getItemUseMode(itemStack, player) == 90 || this.getItemUseMode(itemStack, player) == 91) {
            return true;
        }
        return this.syncCoreBlock() && super.isUsable(itemStack, player, direction, hitX, hitY, hitZ);
    }

    @Override
    public void useItemFromSide(ItemStack itemStack, EntityPlayer player, int side, int mode) {
        if (mode == 91 && !this.acceptCoordChanger) {
            this.acceptCoordChanger = true;
            if (--player.func_71045_bC().field_77994_a == 0) {
                player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
            }
        }
        if (mode == 90 && itemStack.func_77942_o() && this.acceptCoordChanger) {
            NBTTagCompound tag = itemStack.func_77978_p();
            int cx = tag.func_74762_e("CoordMemoryX");
            int cy = tag.func_74762_e("CoordMemoryY");
            int cz = tag.func_74762_e("CoordMemoryZ");
            int cd = tag.func_74762_e("CoordMemoryDimID");
            if (this.setCoreBlockDimension(cd) && this.setCoreBlockCoord(cx - this.field_145851_c, cy - this.field_145848_d, cz - this.field_145849_e)) {
                player.func_145747_a((IChatComponent)new ChatComponentText("Set Core block Coord (" + cx + "," + cy + "," + cz + ") in dim " + cd));
            }
        } else if (this.syncCoreBlock()) {
            super.useItemFromSide(itemStack, player, side, mode);
        }
        this.setSyncFlag();
    }

    @Override
    public boolean consumeClayEnergy(long energy) {
        return false;
    }

    @Override
    public boolean produceClayEnergy() {
        return false;
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public void initParamsByTier(int tier) {
    }

    @Override
    public boolean irradiateClayLaser(ClayLaser laser, UtilDirection direction) {
        if (this.syncCoreBlock()) {
            IClayLaserMachine te = (IClayLaserMachine)UtilBuilder.safeGetTileEntity((IBlockAccess)this.func_145831_w(), this.field_145851_c + this.coreBlockX, this.field_145848_d + this.coreBlockY, this.field_145849_e + this.coreBlockZ);
            return te.irradiateClayLaser(laser, direction);
        }
        return false;
    }

    @Override
    public void setSyncFlag() {
        if (!this.field_145850_b.field_72995_K && !this.loop) {
            TileEntity te;
            int coreX = this.field_145851_c + this.coreBlockX;
            int coreY = this.field_145848_d + this.coreBlockY;
            int coreZ = this.field_145849_e + this.coreBlockZ;
            if (this.getCoreWorld() != null && (te = UtilBuilder.safeGetTileEntity((IBlockAccess)this.getCoreWorld(), coreX, coreY, coreZ)) instanceof IGeneralInterface && te != this) {
                this.loop = true;
                ((IGeneralInterface)te).setSyncFlag();
                this.loop = false;
            }
        }
        super.setSyncFlag();
    }

    @Override
    public void setInstantSyncFlag() {
        if (!this.field_145850_b.field_72995_K && !this.loop) {
            TileEntity te;
            int coreX = this.field_145851_c + this.coreBlockX;
            int coreY = this.field_145848_d + this.coreBlockY;
            int coreZ = this.field_145849_e + this.coreBlockZ;
            if (this.getCoreWorld() != null && (te = UtilBuilder.safeGetTileEntity((IBlockAccess)this.getCoreWorld(), coreX, coreY, coreZ)) instanceof IGeneralInterface && te != this) {
                this.loop = true;
                ((IGeneralInterface)te).setInstantSyncFlag();
                this.loop = false;
            }
        }
        super.setInstantSyncFlag();
    }

    @Override
    public World getCoreWorld() {
        if (this.interDimensional) {
            return DimensionManager.getWorld((int)this.dimentionId);
        }
        return this.field_145850_b;
    }

    @Override
    public boolean setCoreBlockDimension(int d) {
        this.dimentionId = d;
        this.interDimensional = true;
        if (this.field_145850_b != null && this.field_145850_b.field_73011_w.field_76574_g == d) {
            this.interDimensional = false;
            return true;
        }
        if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
            if (this.getCoreWorld() == null) {
                this.interDimensional = false;
                return false;
            }
            if (this.getCoreWorld() == this.field_145850_b) {
                this.interDimensional = false;
                return true;
            }
        }
        return true;
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public boolean acceptCoordChanger() {
        return this.acceptCoordChanger;
    }
}

