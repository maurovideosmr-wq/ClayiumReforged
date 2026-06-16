/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.block.StorageContainer;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.item.CItems;
import mods.clayium.util.UtilItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileStorageContainer
extends TileClayContainer {
    public int extractSlotNum = 4;
    public int insertSlotNum = 4;
    public int maxStorageSize = 65536;
    protected int alternativeStackSize = 0;
    protected ItemStack[] alternativeItemStacks;
    protected ItemStack[] alternativeItemStacksLastTick;

    public TileStorageContainer() {
        this.insertRoutes = new int[]{-1, 0, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = true;
        this.containerItemStacks = new ItemStack[2];
        this.maxAutoExtractDefault = 64;
        this.maxAutoInsertDefault = 64;
        this.autoInsertInterval = 1;
        this.autoExtractInterval = 1;
        this.maxStorageSize = 65536;
        this.alternativeItemStacks = new ItemStack[this.extractSlotNum + this.insertSlotNum + 1];
        this.alternativeItemStacksLastTick = new ItemStack[this.extractSlotNum + this.insertSlotNum + 1];
        this.listSlotsInsert = new ArrayList();
        this.listSlotsExtract = new ArrayList();
        int[] slots = new int[this.extractSlotNum + this.insertSlotNum];
        int[] slots2 = new int[this.extractSlotNum + this.insertSlotNum];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = i;
            slots2[i] = slots.length - i - 1;
        }
        this.listSlotsInsert.add(slots);
        this.listSlotsExtract.add(slots2);
        this.slotsDrop = new int[0];
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        for (int i = 0; i < this.alternativeItemStacks.length; ++i) {
            this.manageAlternativeStorage(i);
        }
        if (this.containerItemStacks[0] != null && this.containerItemStacks[0].field_77994_a == 0) {
            this.containerItemStacks[0] = null;
        }
    }

    @Override
    public int getItemUseMode(ItemStack itemStack, EntityPlayer player) {
        if (UtilItemStack.areItemDamageEqual(itemStack, CItems.itemMisc.get("ClayCore"))) {
            return 99;
        }
        return super.getItemUseMode(itemStack, player);
    }

    @Override
    public void useItemFromSide(ItemStack itemStack, EntityPlayer player, int side, int mode) {
        super.useItemFromSide(itemStack, player, side, mode);
        if (mode == 99 && this.maxStorageSize < Integer.MAX_VALUE) {
            this.setSyncFlag();
            this.maxStorageSize = Integer.MAX_VALUE;
            if (--player.func_71045_bC().field_77994_a == 0) {
                player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
            }
        }
    }

    @Override
    public int func_70302_i_() {
        return this.insertSlotNum + this.extractSlotNum;
    }

    public int getStackSizeInSlot(int slot) {
        if (this.containerItemStacks[0] == null) {
            return 0;
        }
        if (slot < -1 || slot > this.extractSlotNum + this.insertSlotNum) {
            return 0;
        }
        int stackSize = this.containerItemStacks[0].field_77994_a;
        int maxStackSize = this.containerItemStacks[0].func_77976_d();
        int containerMaxStorageSize = this.maxStorageSize - this.alternativeStackSize;
        int extractSlotNum = this.extractSlotNum;
        int insertSlotNum = this.insertSlotNum;
        if (slot == this.extractSlotNum + this.insertSlotNum) {
            return Math.min(stackSize, maxStackSize);
        }
        int[] map = new int[this.extractSlotNum + this.insertSlotNum];
        int p = 0;
        for (int i = 0; i < this.extractSlotNum + this.insertSlotNum; ++i) {
            if (this.alternativeItemStacksLastTick[i] != null) {
                stackSize -= this.alternativeItemStacksLastTick[i].field_77994_a;
                containerMaxStorageSize -= maxStackSize;
                if (i == 0) {
                    containerMaxStorageSize -= this.maxStorageSize - maxStackSize * (this.extractSlotNum + this.insertSlotNum);
                }
                if (i < this.extractSlotNum) {
                    --extractSlotNum;
                } else {
                    --insertSlotNum;
                }
                map[i] = -1;
                continue;
            }
            map[i] = p++;
        }
        return this.getStackSizeInSlot(map[slot], stackSize, containerMaxStorageSize, maxStackSize, extractSlotNum, insertSlotNum);
    }

    public int getStackSizeInSlot(int slot, int containerStackSize, int containerMaxStorageSize, int itemMaxStackSize, int extractSlotNum, int insertSlotNum) {
        int insertAreaStackSize;
        if (this.containerItemStacks[0] == null) {
            return 0;
        }
        if (slot < -1 || slot >= extractSlotNum + insertSlotNum) {
            return 0;
        }
        int stackSize = containerStackSize;
        int restStackSize = containerMaxStorageSize - stackSize;
        int maxStackSize = itemMaxStackSize;
        int filledSlots = stackSize / maxStackSize;
        int n = insertAreaStackSize = insertSlotNum * maxStackSize > restStackSize ? insertSlotNum * maxStackSize - restStackSize : 0;
        if (slot == 0 && extractSlotNum > 0 && filledSlots >= extractSlotNum) {
            return stackSize - insertAreaStackSize - maxStackSize * (extractSlotNum - 1);
        }
        if (slot >= extractSlotNum && slot < extractSlotNum + insertSlotNum) {
            int slot0 = slot - extractSlotNum;
            filledSlots = insertAreaStackSize / maxStackSize;
            return slot0 < filledSlots ? maxStackSize : (slot0 == filledSlots ? insertAreaStackSize - filledSlots * maxStackSize : 0);
        }
        return slot < filledSlots ? maxStackSize : (slot == filledSlots ? stackSize - filledSlots * maxStackSize : 0);
    }

    private void debug(String str) {
    }

    @Override
    public ItemStack func_70301_a(int slot) {
        this.debug("getStackInSlot(" + slot + ")");
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            return super.func_70301_a(1);
        }
        if (this.alternativeItemStacks[slot] == null) {
            int size = this.getStackSizeInSlot(slot);
            if (size == 0) {
                return null;
            }
            ItemStack res = this.containerItemStacks[0].func_77946_l();
            res.field_77994_a = size;
            if (this.field_145850_b.field_72995_K) {
                return res;
            }
            this.alternativeItemStacksLastTick[slot] = res.func_77946_l();
            this.alternativeItemStacks[slot] = res.func_77946_l();
        }
        return this.alternativeItemStacks[slot];
    }

    @Override
    public ItemStack func_70298_a(int slot, int size) {
        this.setSyncFlag();
        this.debug("decrStackSize(" + slot + "," + size + ")");
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            return super.func_70298_a(1, size);
        }
        if (this.alternativeItemStacks[slot] == null) {
            int resSize;
            int stackSize = this.getStackSizeInSlot(slot);
            int n = resSize = size >= stackSize ? stackSize : size;
            if (this.containerItemStacks[0] == null || resSize == 0) {
                return null;
            }
            ItemStack res = this.containerItemStacks[0].func_77979_a(resSize);
            if (this.containerItemStacks[0].field_77994_a == 0) {
                this.containerItemStacks[0] = null;
            }
            return res;
        }
        int stackSize = this.alternativeItemStacks[slot].field_77994_a;
        int resSize = size >= stackSize ? stackSize : size;
        ItemStack res = this.alternativeItemStacks[slot].func_77979_a(resSize);
        return res;
    }

    @Override
    public ItemStack func_70304_b(int slot) {
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            return super.func_70304_b(1);
        }
        return this.func_70298_a(slot, this.getStackSizeInSlot(slot));
    }

    @Override
    public void func_70299_a(int slot, ItemStack itemstack) {
        this.debug("setInventorySlotContents(" + slot + "," + itemstack + "@" + (itemstack == null ? "n" : Integer.valueOf(itemstack.hashCode())) + ")");
        if (slot >= this.extractSlotNum + this.insertSlotNum + 1) {
            super.func_70299_a(1, itemstack);
            return;
        }
        if (this.alternativeItemStacks[slot] != null && this.alternativeItemStacks[slot] == itemstack) {
            return;
        }
        this.setInventorySlotContentsUnsafe(slot, itemstack);
        this.initializealternativeStorage(slot);
    }

    public void manageAlternativeStorage(int slot) {
        if (slot == 0 && this.alternativeStackSize > 0) {
            this.addToStorage(this.alternativeStackSize);
            this.alternativeStackSize = 0;
        }
        if (this.alternativeItemStacks[slot] != null) {
            if (this.containerItemStacks[0] == null) {
                if (this.alternativeItemStacks[slot].field_77994_a != this.alternativeItemStacksLastTick[slot].field_77994_a) {
                    this.setInventorySlotContentsUnsafe(slot, this.alternativeItemStacks[slot]);
                }
            } else {
                this.addToStorage(this.alternativeItemStacks[slot].field_77994_a - this.alternativeItemStacksLastTick[slot].field_77994_a);
            }
            this.initializealternativeStorage(slot);
        }
    }

    public void initializealternativeStorage(int slot) {
        if (this.alternativeItemStacks[slot] != null) {
            this.alternativeItemStacks[slot].field_77994_a = 0;
            this.alternativeItemStacks[slot] = null;
            this.alternativeItemStacksLastTick[slot] = null;
        }
        if (slot == 0 && this.alternativeStackSize > 0) {
            this.addToStorage(this.alternativeStackSize);
            this.alternativeStackSize = 0;
        }
    }

    public void setInventorySlotContentsUnsafe(int slot, ItemStack itemstack) {
        this.setSyncFlag();
        if (this.containerItemStacks[0] == null) {
            if (itemstack != null) {
                this.containerItemStacks[0] = itemstack.func_77946_l();
            }
        } else {
            int setsize = itemstack == null ? 0 : itemstack.field_77994_a;
            int dif = setsize - this.getStackSizeInSlot(slot);
            if (this.alternativeItemStacksLastTick[slot] != null) {
                dif = setsize - this.alternativeItemStacksLastTick[slot].field_77994_a;
                this.alternativeItemStacks[slot].field_77994_a = this.alternativeItemStacksLastTick[slot].field_77994_a;
            }
            if (this.alternativeItemStacks[0] != null && dif > 0) {
                this.alternativeStackSize += dif;
            } else {
                this.addToStorage(dif);
            }
        }
        this.debug("done setInventorySlotContentsUnsafe(" + slot + "," + itemstack + "@" + (itemstack == null ? "n" : Integer.valueOf(itemstack.hashCode())) + ")");
    }

    public void addToStorage(int stackSize) {
        this.containerItemStacks[0].field_77994_a = this.maxStorageSize - this.containerItemStacks[0].field_77994_a < stackSize ? this.maxStorageSize : (this.containerItemStacks[0].field_77994_a += stackSize);
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        return slot >= this.insertSlotNum + this.extractSlotNum + 1 || (this.containerItemStacks[0] == null || UtilItemStack.areTypeEqual(itemstack, this.containerItemStacks[0])) && (this.containerItemStacks[1] == null || this.checkFilterSlot(itemstack, this.containerItemStacks[1]));
    }

    @Override
    public int[] func_94128_d(int side) {
        int[] slots = new int[this.insertSlotNum + this.extractSlotNum];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsertItemUnsafe(int slot, ItemStack itemstack, int route) {
        ItemStack itemInSlot = this.func_70301_a(slot);
        if (itemInSlot != null && itemInSlot.field_77994_a > itemInSlot.func_77976_d()) {
            return false;
        }
        return this.func_94041_b(slot, itemstack);
    }

    @Override
    public boolean canExtractItemUnsafe(int slot, ItemStack itemstack, int route) {
        return true;
    }

    public boolean checkFilterSlot(ItemStack itemstack, ItemStack filter) {
        return UtilItemStack.areTypeEqual(itemstack, filter);
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        super.func_145839_a(tagCompound);
        if (this.containerItemStacks[0] != null && tagCompound.func_74764_b("ItemStackSize")) {
            this.containerItemStacks[0].field_77994_a = tagCompound.func_74762_e("ItemStackSize");
        }
        if (tagCompound.func_74764_b("MaxStorageSize")) {
            this.maxStorageSize = tagCompound.func_74762_e("MaxStorageSize");
        }
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        super.func_145841_b(tagCompound);
        for (int i = 0; i < this.alternativeItemStacks.length; ++i) {
            this.manageAlternativeStorage(i);
        }
        if (this.containerItemStacks[0] != null) {
            tagCompound.func_74768_a("ItemStackSize", this.containerItemStacks[0].field_77994_a);
        } else {
            tagCompound.func_74768_a("ItemStackSize", 0);
        }
        tagCompound.func_74768_a("MaxStorageSize", this.maxStorageSize);
    }

    @Override
    public int[] getSlotsDrop() {
        return new int[0];
    }

    @Override
    public boolean shouldRefresh() {
        return false;
    }

    @Override
    public boolean hasSpecialDrops() {
        return true;
    }

    @Override
    public void setSyncFlag() {
        super.setSyncFlag();
        this.markForWeakUpdate();
    }

    @Override
    public boolean acceptInterfaceSync() {
        return false;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, Block block, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, block, metadata, fortune);
        if (ret != null) {
            for (int i = 0; i < ret.size(); ++i) {
                ItemStack item = ret.get(i);
                ret.set(i, StorageContainer.expandStorage(item, StorageContainer.getStorageSize(item)));
            }
        }
        return ret;
    }
}

