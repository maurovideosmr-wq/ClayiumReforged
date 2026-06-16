/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block.tile;

import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.util.UtilTier;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;

public abstract class TileClayContainerTiered
extends TileClayContainer {
    protected int baseTier = -1;
    private boolean initialized = false;

    public void setBaseTier(int tier) {
        this.baseTier = tier;
        if (!this.initialized && this.baseTier != -1) {
            this.initByTier(this.baseTier);
        }
    }

    public int getTier() {
        return this.baseTier;
    }

    public int getRecipeTier() {
        return this.getTier();
    }

    @Override
    public void func_145845_h() {
        Block block;
        super.func_145845_h();
        if (this.baseTier == -1 && this.field_145850_b != null && (block = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e)) != null && block instanceof ClayContainerTiered) {
            this.setBaseTier(((ClayContainerTiered)block).getTier((IBlockAccess)this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e));
        }
        if (!this.initialized && this.baseTier != -1) {
            this.initByTier(this.baseTier);
            this.setSyncFlag();
        }
    }

    @Override
    public boolean acceptEnergyClay() {
        return UtilTier.acceptEnergyClay(this.baseTier);
    }

    public final void initByTier(int tier) {
        this.initialized = true;
        this.initParamsByTier(tier);
    }

    public void initParamsByTier(int tier) {
    }

    protected void setDefaultTransportationParamsByTier(int tier, ParamMode mode) {
        switch (mode) {
            case MACHINE: {
                UtilTier.TierManager.applyTransportTierManager(this, tier, UtilTier.tierMachineTransport);
                if (UtilTier.canAutoTransfer(this.baseTier)) break;
                this.autoInsert = false;
                this.autoExtract = false;
                break;
            }
            case BUFFER: {
                UtilTier.TierManager.applyTransportTierManager(this, tier, UtilTier.tierBufferTransport);
            }
        }
    }

    @Override
    public void readCoordFromNBT(NBTTagCompound tagCompound) {
        super.readCoordFromNBT(tagCompound);
        if (tagCompound.func_74764_b("BaseTier") && this.baseTier == -1) {
            this.setBaseTier(tagCompound.func_74765_d("BaseTier"));
        }
    }

    @Override
    public void writeCoordToNBT(NBTTagCompound tagCompound) {
        super.writeCoordToNBT(tagCompound);
        tagCompound.func_74777_a("BaseTier", (short)this.baseTier);
    }

    static enum ParamMode {
        MACHINE,
        BUFFER;

    }
}

