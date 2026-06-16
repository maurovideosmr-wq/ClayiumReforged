/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraftforge.common.util.ForgeDirection
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.IMultitrackInventory;
import mods.clayium.block.tile.TileClayContainerTiered;
import mods.clayium.item.filter.ItemFilterTemp;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilTransfer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMultitrackBuffer
extends TileClayContainerTiered
implements IMultitrackInventory {
    public int[][] tracks;
    public int[] slot2track;
    public int[] allSlots;
    public static final int filterPos = 54;
    protected MultitrackSelector selector = new MultitrackSelector();

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, -1, -1, 0, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = true;
        this.containerItemStacks = new ItemStack[60];
        this.clayEnergySlot = -1;
    }

    @Override
    public void initParamsByTier(int tier) {
        int i;
        this.setDefaultTransportationParamsByTier(tier, TileClayContainerTiered.ParamMode.BUFFER);
        int trackNum = 6;
        int trackInvSize = 9;
        switch (tier) {
            case 4: {
                trackNum = 2;
                trackInvSize = 1;
                break;
            }
            case 5: {
                trackNum = 3;
                trackInvSize = 2;
                break;
            }
            case 6: {
                trackNum = 4;
                trackInvSize = 4;
                break;
            }
            case 7: {
                trackNum = 5;
                trackInvSize = 4;
                break;
            }
            case 8: {
                trackNum = 6;
                trackInvSize = 6;
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                trackNum = 6;
                trackInvSize = 9;
            }
        }
        int slotNum = trackNum * trackInvSize;
        int[] slots = new int[slotNum];
        int[] slots2 = new int[slotNum];
        for (i = 0; i < slots.length; ++i) {
            slots[i] = i;
            slots2[i] = slots.length - i - 1;
        }
        this.listSlotsInsert.add(slots);
        this.listSlotsExtract.add(slots2);
        this.slotsDrop = slots;
        this.allSlots = slots;
        this.tracks = new int[trackNum][];
        this.slot2track = new int[slotNum];
        for (i = 0; i < this.slot2track.length; ++i) {
            this.slot2track[i] = -1;
        }
        for (int t = 0; t < trackNum; ++t) {
            slots = new int[trackInvSize];
            slots2 = new int[trackInvSize];
            for (int i2 = 0; i2 < slots.length; ++i2) {
                slots[i2] = i2 + t * trackInvSize;
                slots2[i2] = slots.length - i2 - 1 + t * trackInvSize;
                this.slot2track[i2 + t * trackInvSize] = t;
            }
            this.listSlotsInsert.add(slots);
            this.listSlotsExtract.add(slots2);
            this.tracks[t] = slots;
        }
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return this.isItemValidForTrack(this.getTrack(slot), itemstack);
    }

    public boolean isItemValidForTrack(int track, ItemStack itemstack) {
        ItemStack filter;
        if (track < 0) {
            return true;
        }
        ItemStack itemStack = filter = track + 54 < this.containerItemStacks.length ? this.containerItemStacks[track + 54] : null;
        if (filter == null) {
            return true;
        }
        return ItemFilterTemp.isFilter(filter) && ItemFilterTemp.match(filter, itemstack) || ItemFilterTemp.matchBetweenItemstacks(filter, itemstack, false);
    }

    public int getTrack(int slot) {
        if (this.slot2track == null || slot < 0 || this.slot2track.length <= slot) {
            return -1;
        }
        return this.slot2track[slot];
    }

    @Override
    public int[] func_94128_d(int side) {
        return this.allSlots;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side, int track) {
        return this.tracks != null && track >= 0 && track < this.tracks.length ? this.tracks[track] : this.func_94128_d(side);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side, int track) {
        return (track < 0 || this.getTrack(slot) == track) && this.func_102007_a(slot, itemstack, side);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side, int track) {
        return (track < 0 || this.getTrack(slot) == track) && this.func_102008_b(slot, itemstack, side);
    }

    @Override
    public boolean canInsertItemUnsafe(int slot, ItemStack itemstack, int route) {
        if (route == -1) {
            return this.func_94041_b(slot, itemstack);
        }
        return super.canInsertItemUnsafe(slot, itemstack, route);
    }

    @Override
    public boolean canExtractItemUnsafe(int slot, ItemStack itemstack, int route) {
        if (route == -1) {
            return true;
        }
        return super.canInsertItemUnsafe(slot, itemstack, route);
    }

    @Override
    public void doAutoExtractFromSide(int side, int front) {
        int route = this.insertRoutes[side];
        for (int k = 0; k < this.tracks.length; ++k) {
            if (route != 0 && route - 1 != k) continue;
            this.selector.track = k;
            UtilTransfer.extract((TileEntity)this, this.tracks[k], UtilDirection.getOrientation(front), UtilDirection.getSide(side), this.maxAutoExtract != null && route < this.maxAutoExtract.length && this.maxAutoExtract[route] >= 0 ? this.maxAutoExtract[route] : this.maxAutoExtractDefault, this.selector);
        }
    }

    @Override
    public void doAutoInsertToSide(int side, int front) {
        int route = this.extractRoutes[side];
        for (int k = 0; k < this.tracks.length; ++k) {
            if (route != 0 && route - 1 != k) continue;
            this.selector.track = k;
            UtilTransfer.insert((TileEntity)this, this.tracks[k], UtilDirection.getOrientation(front), UtilDirection.getSide(side), this.maxAutoInsert != null && route < this.maxAutoInsert.length && this.maxAutoInsert[route] >= 0 ? this.maxAutoInsert[route] : this.maxAutoInsertDefault, this.selector);
        }
    }

    public static class MultitrackSelector
    extends UtilTransfer.InventorySelector {
        public int track = -1;

        @Override
        public int[] getSlotToInsertTo(ForgeDirection direction) {
            if (this.selected == null) {
                return null;
            }
            if (this.selected instanceof IMultitrackInventory) {
                return ((IMultitrackInventory)this.selected).getAccessibleSlotsFromSide(direction.getOpposite().ordinal(), this.track);
            }
            return super.getSlotToExtractFrom(direction);
        }

        @Override
        public int[] getSlotToExtractFrom(ForgeDirection direction) {
            if (this.selected == null) {
                return null;
            }
            if (this.selected instanceof IMultitrackInventory) {
                return ((IMultitrackInventory)this.selected).getAccessibleSlotsFromSide(direction.getOpposite().ordinal(), this.track);
            }
            return super.getSlotToExtractFrom(direction);
        }
    }
}

