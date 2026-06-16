/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTankInfo
 *  net.minecraftforge.fluids.IFluidHandler
 */
package mods.clayium.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mods.clayium.block.tile.IInventoryFlexibleStackLimit;
import mods.clayium.item.CItems;
import mods.clayium.item.ItemCapsule;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class UtilTransfer {
    public static IInventorySelector defaultSelector = new InventorySelector();
    private static ItemCapsule[] capsules = CItems.itemsCapsule;
    private static int[] capacities = new int[]{1000, 125, 25, 5, 1};
    private static ItemCapsule[] aCapsules = CItems.itemsCapsule;
    private static Map<ItemCapsule, Integer> capacityMap;
    private static Map<Integer, ItemCapsule> capsuleMap;
    private static ItemCapsule[] sortedCapsules;
    private static int[] sortedCapacities;
    private static int[] sortedStackLimits;

    public static int insert(TileEntity from, int[] fromSlots, ForgeDirection direction, int maxTransfer, Object ... metadata) {
        IInventorySelector selector = defaultSelector;
        for (Object obj : metadata) {
            if (!(obj instanceof IInventorySelector)) continue;
            selector = (IInventorySelector)obj;
        }
        if (!selector.selectInventoryToInsertTo(from, direction)) {
            return maxTransfer;
        }
        int fromSide = direction.ordinal();
        int toSide = direction.getOpposite().ordinal();
        IInventory to = selector.getSelectedInventory();
        int[] toSlots = selector.getSlotToInsertTo(direction);
        return UtilTransfer.transfer((IInventory)from, to, fromSlots, toSlots, fromSide, toSide, maxTransfer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int transfer(IInventory from, IInventory to, int[] fromSlots, int[] toSlots, int fromSide, int toSide, int maxTransfer) {
        int oldTransfer = maxTransfer;
        ISidedInventory fromSided = from instanceof ISidedInventory ? (ISidedInventory)from : null;
        ISidedInventory toSided = to instanceof ISidedInventory ? (ISidedInventory)to : null;
        try {
            block5: for (int fromSlot : fromSlots) {
                int stackLimit;
                ItemStack toItem;
                ItemStack fromItem = from.func_70301_a(fromSlot);
                if (fromItem == null || fromItem.field_77994_a <= 0 || fromSided != null && !fromSided.func_102008_b(fromSlot, fromItem, fromSide)) continue;
                if (fromItem.func_77985_e()) {
                    for (int toSlot : toSlots) {
                        toItem = to.func_70301_a(toSlot);
                        if (toItem == null || toItem.field_77994_a <= 0 || toSided != null && !toSided.func_102007_a(toSlot, fromItem, toSide) || !fromItem.func_77969_a(toItem) || !ItemStack.func_77970_a((ItemStack)toItem, (ItemStack)fromItem)) continue;
                        stackLimit = to instanceof IInventoryFlexibleStackLimit ? ((IInventoryFlexibleStackLimit)to).getInventoryStackLimit(toSlot) : to.func_70297_j_();
                        int maxSize = Math.min(toItem.func_77976_d(), stackLimit);
                        int maxMove = Math.max(Math.min(maxSize - toItem.field_77994_a, Math.min(maxTransfer, fromItem.field_77994_a)), 0);
                        toItem.field_77994_a += maxMove;
                        maxTransfer -= maxMove;
                        fromItem.field_77994_a -= maxMove;
                        if (fromItem.field_77994_a == 0) {
                            from.func_70299_a(fromSlot, null);
                        }
                        if (maxTransfer == 0) {
                            int n = 0;
                            return n;
                        }
                        if (fromItem.field_77994_a == 0) break;
                    }
                }
                if (fromItem.field_77994_a <= 0) continue;
                for (int toSlot : toSlots) {
                    int stackSize;
                    toItem = to.func_70301_a(toSlot);
                    if (toItem != null || !to.func_94041_b(toSlot, fromItem) || toSided != null && !toSided.func_102007_a(toSlot, fromItem, toSide)) continue;
                    toItem = fromItem.func_77946_l();
                    stackLimit = to instanceof IInventoryFlexibleStackLimit ? ((IInventoryFlexibleStackLimit)to).getInventoryStackLimit(toSlot) : to.func_70297_j_();
                    toItem.field_77994_a = stackSize = Math.min(Math.min(maxTransfer, fromItem.field_77994_a), stackLimit);
                    to.func_70299_a(toSlot, toItem);
                    maxTransfer -= stackSize;
                    fromItem.field_77994_a -= stackSize;
                    if (fromItem.field_77994_a == 0) {
                        from.func_70299_a(fromSlot, null);
                    }
                    if (maxTransfer == 0) {
                        int n = 0;
                        return n;
                    }
                    if (fromItem.field_77994_a == 0) continue block5;
                }
            }
        }
        finally {
            if (oldTransfer != maxTransfer) {
                to.func_70296_d();
                from.func_70296_d();
            }
        }
    }

    public static int insert(TileEntity from, int[] fromSlots, UtilDirection front, UtilDirection side, int maxTransfer, Object ... metadata) {
        ForgeDirection direction = ForgeDirection.getOrientation((int)front.getSide(side).ordinal());
        return UtilTransfer.insert(from, fromSlots, direction, maxTransfer, metadata);
    }

    public static int transfer(TileEntity from, int[] fromSlots, UtilDirection front, UtilDirection side, int maxTransfer, Object ... metadata) {
        return UtilTransfer.insert(from, fromSlots, front, side, maxTransfer, metadata);
    }

    public static int transfer(TileEntity from, int[] fromSlots, ForgeDirection direction, int maxTransfer, Object ... metadata) {
        return UtilTransfer.insert(from, fromSlots, direction, maxTransfer, metadata);
    }

    public static int extract(TileEntity to, int[] toSlots, ForgeDirection direction, int maxTransfer, Object ... metadata) {
        IInventorySelector selector = defaultSelector;
        for (Object obj : metadata) {
            if (!(obj instanceof IInventorySelector)) continue;
            selector = (IInventorySelector)obj;
        }
        if (!selector.selectInventoryToExtractFrom(to, direction)) {
            return maxTransfer;
        }
        int toSide = direction.ordinal();
        int fromSide = direction.getOpposite().ordinal();
        IInventory from = selector.getSelectedInventory();
        int[] fromSlots = selector.getSlotToExtractFrom(direction);
        return UtilTransfer.transfer(from, (IInventory)to, fromSlots, toSlots, fromSide, toSide, maxTransfer);
    }

    public static int extract(TileEntity from, int[] fromSlots, UtilDirection front, UtilDirection side, int maxTransfer, Object ... metadata) {
        ForgeDirection direction = ForgeDirection.getOrientation((int)front.getSide(side).ordinal());
        return UtilTransfer.extract(from, fromSlots, direction, maxTransfer, metadata);
    }

    public static ItemStack produceItemStack(ItemStack itemstack, ItemStack[] inventory, int index, int inventoryStackLimit) {
        if (itemstack == null) {
            return null;
        }
        ItemStack res = itemstack.func_77946_l();
        int stackSize = itemstack.field_77994_a;
        if (inventory[index] == null) {
            inventory[index] = itemstack.func_77946_l();
            inventory[index].field_77994_a = Math.min(itemstack.field_77994_a, inventoryStackLimit);
            res.field_77994_a -= inventory[index].field_77994_a;
        } else if (inventory[index].func_77969_a(itemstack) && ItemStack.func_77970_a((ItemStack)inventory[index], (ItemStack)itemstack)) {
            int a = Math.min(itemstack.field_77994_a, inventory[index].func_77976_d() - inventory[index].field_77994_a);
            inventory[index].field_77994_a += a;
            res.field_77994_a -= a;
        }
        if (res.field_77994_a <= 0) {
            res = null;
        }
        return res;
    }

    public static ItemStack produceItemStack(ItemStack itemstack, ItemStack[] inventory, int i, int j, int inventoryStackLimit) {
        int k;
        if (itemstack == null) {
            return null;
        }
        ItemStack res = itemstack.func_77946_l();
        for (k = i; k < j; ++k) {
            if (inventory[k] == null) continue;
            res = UtilTransfer.produceItemStack(res, inventory, k, inventoryStackLimit);
        }
        for (k = i; k < j; ++k) {
            if (inventory[k] != null) continue;
            res = UtilTransfer.produceItemStack(res, inventory, k, inventoryStackLimit);
        }
        return res;
    }

    public static ItemStack[] produceItemStacks(ItemStack[] itemstacks, ItemStack[] inventory, int i, int j, int inventoryStackLimit) {
        if (itemstacks == null) {
            return null;
        }
        for (int k = 0; k < itemstacks.length; ++k) {
            itemstacks[k] = UtilTransfer.produceItemStack(itemstacks[k], inventory, i, j, inventoryStackLimit);
        }
        return itemstacks;
    }

    public static int canProduceItemStack(ItemStack itemstack, ItemStack[] inventory, int index, int inventoryStackLimit) {
        if (itemstack == null) {
            return 0;
        }
        ItemStack res = itemstack.func_77946_l();
        int stackSize = itemstack.field_77994_a;
        if (inventory[index] == null) {
            return inventoryStackLimit;
        }
        if (inventory[index].func_77969_a(itemstack) && ItemStack.func_77970_a((ItemStack)inventory[index], (ItemStack)itemstack)) {
            return inventory[index].func_77976_d() - inventory[index].field_77994_a;
        }
        return 0;
    }

    public static int canProduceItemStack(ItemStack itemstack, ItemStack[] inventory, int i, int j, int inventoryStackLimit) {
        int rest = 0;
        for (int k = i; k < j; ++k) {
            rest += UtilTransfer.canProduceItemStack(itemstack, inventory, k, inventoryStackLimit);
        }
        return rest;
    }

    public static boolean canProduceItemStacks(ItemStack[] itemstacks, ItemStack[] inventory, int i, int j, int inventoryStackLimit) {
        if (itemstacks == null) {
            return true;
        }
        ItemStack[] copyItemstacks = UtilTransfer.getHardCopy(itemstacks);
        ItemStack[] copyInventory = UtilTransfer.getHardCopy(inventory);
        for (int k = 0; k < copyItemstacks.length; ++k) {
            if (UtilTransfer.produceItemStack(copyItemstacks[k], copyInventory, i, j, inventoryStackLimit) == null) continue;
            return false;
        }
        return true;
    }

    public static ItemStack[] getHardCopy(ItemStack[] itemstacks) {
        if (itemstacks == null) {
            return null;
        }
        ItemStack[] res = new ItemStack[itemstacks.length];
        for (int i = 0; i < res.length; ++i) {
            if (itemstacks[i] == null) continue;
            res[i] = itemstacks[i].func_77946_l();
        }
        return res;
    }

    public static int countItemStack(ItemStack itemstack, ItemStack[] inventory, int i, int j) {
        int res = 0;
        for (int k = i; k < j; ++k) {
            if (inventory[k] == null || !inventory[k].func_77969_a(itemstack) || !ItemStack.func_77970_a((ItemStack)inventory[k], (ItemStack)itemstack)) continue;
            res += inventory[k].field_77994_a;
        }
        return res;
    }

    public static ItemStack consumeItemStack(ItemStack itemstack, ItemStack[] inventory, int i, int j) {
        int stackSize = itemstack.field_77994_a;
        for (int k = i; k < j; ++k) {
            if (inventory[k] == null || !inventory[k].func_77969_a(itemstack) || !ItemStack.func_77970_a((ItemStack)inventory[k], (ItemStack)itemstack)) continue;
            int n = Math.min(stackSize, inventory[k].field_77994_a);
            stackSize -= n;
            inventory[k].field_77994_a -= n;
            if (inventory[k].field_77994_a > 0) continue;
            inventory[k] = null;
        }
        if (stackSize <= 0) {
            return null;
        }
        ItemStack res = itemstack.func_77946_l();
        res.field_77994_a = stackSize;
        return res;
    }

    public static ItemStack[] consumeItemStack(ItemStack[] itemstack, ItemStack[] inventory, int i, int j) {
        ItemStack[] res = new ItemStack[itemstack.length];
        for (int k = 0; k < itemstack.length; ++k) {
            res[k] = UtilTransfer.consumeItemStack(itemstack[k], inventory, i, j);
        }
        return res;
    }

    public static int insertToTank(TileEntity fromTe, int[] fromSlots, ForgeDirection direction, int maxTransfer) {
        int oldTransfer = maxTransfer;
        int fromSide = direction.ordinal();
        int toSide = direction.getOpposite().ordinal();
        ForgeDirection toDirection = direction.getOpposite();
        World world = fromTe.func_145831_w();
        IInventory from = (IInventory)fromTe;
        ISidedInventory fromSided = from instanceof ISidedInventory ? (ISidedInventory)from : null;
        TileEntity te = UtilDirection.getTileEntity((IBlockAccess)world, fromTe.field_145851_c, fromTe.field_145848_d, fromTe.field_145849_e, direction);
        if (!(te instanceof IFluidHandler)) {
            return maxTransfer;
        }
        IFluidHandler to = (IFluidHandler)te;
        Block block = UtilDirection.getBlock((IBlockAccess)world, fromTe.field_145851_c, fromTe.field_145848_d, fromTe.field_145849_e, direction);
        for (int fromSlot : fromSlots) {
            int result;
            ItemCapsule capsule;
            FluidStack fluidStack;
            ItemStack fromItem = from.func_70301_a(fromSlot);
            if (fromItem == null || fromItem.field_77994_a <= 0 || !(fromItem.func_77973_b() instanceof ItemCapsule) || fromSided != null && !fromSided.func_102008_b(fromSlot, fromItem, fromSide) || (fluidStack = (capsule = (ItemCapsule)fromItem.func_77973_b()).getFluidStackFromItemStack(fromItem)) == null) continue;
            int capacity = fluidStack.amount;
            if (fromItem.func_77942_o()) {
                fluidStack.tag = fromItem.func_77978_p();
            }
            if (!to.canFill(toDirection, fluidStack.getFluid()) || !fromItem.func_77985_e() || (result = to.fill(toDirection, fluidStack, false)) < capacity) continue;
            int maxMove = Math.max(Math.min(result / capacity, Math.min(maxTransfer, fromItem.field_77994_a)), 0);
            fluidStack.amount = maxMove * capacity;
            result = to.fill(toDirection, fluidStack, false);
            if (result < maxMove * capacity) continue;
            to.fill(toDirection, fluidStack, true);
            maxTransfer -= maxMove;
            fromItem.field_77994_a -= maxMove;
            if (fromItem.field_77994_a == 0) {
                from.func_70299_a(fromSlot, null);
            }
            if (maxTransfer == 0) {
                return 0;
            }
            if (fromItem.field_77994_a == 0) break;
        }
        if (oldTransfer != maxTransfer) {
            if (to instanceof TileEntity) {
                ((TileEntity)to).func_70296_d();
            }
            from.func_70296_d();
        }
        return maxTransfer;
    }

    public static int insertToTank(TileEntity from, int[] fromSlots, UtilDirection front, UtilDirection side, int maxTransfer) {
        ForgeDirection direction = ForgeDirection.getOrientation((int)front.getSide(side).ordinal());
        return UtilTransfer.insertToTank(from, fromSlots, direction, maxTransfer);
    }

    public static int extractFromTank(TileEntity toTe, int[] toSlots, ForgeDirection direction, int maxTransfer) {
        int oldTransfer = maxTransfer;
        int toSide = direction.ordinal();
        int fromSide = direction.getOpposite().ordinal();
        ForgeDirection fromDirection = direction.getOpposite();
        World world = toTe.func_145831_w();
        IInventory to = (IInventory)toTe;
        ISidedInventory toSided = to instanceof ISidedInventory ? (ISidedInventory)to : null;
        TileEntity te = UtilDirection.getTileEntity((IBlockAccess)world, toTe.field_145851_c, toTe.field_145848_d, toTe.field_145849_e, direction);
        if (!(te instanceof IFluidHandler)) {
            return 0;
        }
        IFluidHandler from = (IFluidHandler)te;
        FluidTankInfo[] infos = from.getTankInfo(fromDirection);
        if (infos == null) {
            return 0;
        }
        for (FluidTankInfo info : infos) {
            FluidStack fluidStack = info.fluid;
            if (fluidStack == null) continue;
            Fluid fluid = fluidStack.getFluid();
            int fluidId = UtilFluid.getFluidID(fluidStack);
            if (!from.canDrain(direction.getOpposite(), fluid)) continue;
            FluidStack toSimulate = fluidStack.copy();
            toSimulate.amount = Integer.MAX_VALUE;
            FluidStack result = from.drain(fromDirection, toSimulate, false);
            int amount = result == null ? fluidStack.amount : result.amount;
            for (int i = 0; i < capsules.length; ++i) {
                if (amount >= capacities[i]) {
                    int stackLimit;
                    ItemStack toItem;
                    ItemStack fromItem = new ItemStack((Item)capsules[i], 1, fluidId);
                    if (fluidStack.tag != null) {
                        fromItem.func_77982_d((NBTTagCompound)fluidStack.tag.func_74737_b());
                    }
                    for (int toSlot : toSlots) {
                        toItem = to.func_70301_a(toSlot);
                        if (toItem == null || toItem.field_77994_a <= 0 || toSided != null && !toSided.func_102007_a(toSlot, fromItem, toSide) || !fromItem.func_77969_a(toItem) || !ItemStack.func_77970_a((ItemStack)toItem, (ItemStack)fromItem)) continue;
                        stackLimit = to instanceof IInventoryFlexibleStackLimit ? ((IInventoryFlexibleStackLimit)to).getInventoryStackLimit(toSlot) : to.func_70297_j_();
                        int maxSize = Math.min(toItem.func_77976_d(), stackLimit);
                        int maxMove = Math.max(Math.min(maxSize - toItem.field_77994_a, Math.min(maxTransfer, amount / capacities[i])), 0);
                        toSimulate.amount = maxMove * capacities[i];
                        result = from.drain(fromDirection, toSimulate, false);
                        if (result == null || result.amount < maxMove * capacities[i]) continue;
                        from.drain(fromDirection, result, true);
                        toItem.field_77994_a += maxMove;
                        amount -= maxMove * capacities[i];
                        if ((maxTransfer -= maxMove) == 0) {
                            return 0;
                        }
                        if (amount < capacities[i]) break;
                    }
                    if (amount >= capacities[i]) {
                        for (int toSlot : toSlots) {
                            toItem = to.func_70301_a(toSlot);
                            if (toItem != null || !to.func_94041_b(toSlot, fromItem) || toSided != null && !toSided.func_102007_a(toSlot, fromItem, toSide)) continue;
                            toItem = fromItem.func_77946_l();
                            stackLimit = to instanceof IInventoryFlexibleStackLimit ? ((IInventoryFlexibleStackLimit)to).getInventoryStackLimit(toSlot) : to.func_70297_j_();
                            int stackSize = Math.min(Math.min(maxTransfer, amount / capacities[i]), stackLimit);
                            toSimulate.amount = stackSize * capacities[i];
                            result = from.drain(fromDirection, toSimulate, false);
                            if (result == null || result.amount < stackSize * capacities[i]) continue;
                            from.drain(fromDirection, result, true);
                            toItem.field_77994_a = stackSize;
                            to.func_70299_a(toSlot, toItem);
                            amount -= stackSize * capacities[i];
                            if ((maxTransfer -= stackSize) == 0) {
                                return 0;
                            }
                            if (amount < capacities[i]) break;
                        }
                    }
                }
                if (oldTransfer == maxTransfer) continue;
                to.func_70296_d();
                if (!(from instanceof TileEntity)) continue;
                ((TileEntity)from).func_70296_d();
            }
        }
        return maxTransfer;
    }

    public static int extractFromTank(TileEntity to, int[] toSlots, UtilDirection front, UtilDirection side, int maxTransfer) {
        ForgeDirection direction = ForgeDirection.getOrientation((int)front.getSide(side).ordinal());
        return UtilTransfer.extractFromTank(to, toSlots, direction, maxTransfer);
    }

    public static void initFluidParams() {
        int i;
        capacityMap = new HashMap<ItemCapsule, Integer>();
        capsuleMap = new HashMap<Integer, ItemCapsule>();
        int[] capacities = new int[aCapsules.length];
        for (i = 0; i < aCapsules.length; ++i) {
            capacities[i] = aCapsules[i].getCapacity();
            capacityMap.put(aCapsules[i], capacities[i]);
            capsuleMap.put(capacities[i], aCapsules[i]);
        }
        Arrays.sort(capacities);
        sortedCapacities = new int[capacities.length];
        for (i = 0; i < sortedCapacities.length; ++i) {
            UtilTransfer.sortedCapacities[i] = capacities[sortedCapacities.length - 1 - i];
        }
        sortedCapsules = new ItemCapsule[sortedCapacities.length];
        sortedStackLimits = new int[sortedCapacities.length];
        for (i = 0; i < sortedCapsules.length; ++i) {
            UtilTransfer.sortedCapsules[i] = capsuleMap.get(sortedCapacities[i]);
            UtilTransfer.sortedStackLimits[i] = sortedCapsules[i].func_77639_j();
        }
    }

    public static void sortFluid(ItemStack[] inventory, int[] slots) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        ArrayList<Integer> emptySlots = new ArrayList<Integer>();
        HashMap capsuleSlots = new HashMap();
        HashMap<CapsuleKey, Integer> amounts = new HashMap<CapsuleKey, Integer>();
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                emptySlots.add(slot);
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule)) continue;
            CapsuleKey key = new CapsuleKey(itemstack);
            ArrayList<Integer> list = (ArrayList<Integer>)capsuleSlots.get(key);
            int amount = 0;
            if (list == null) {
                list = new ArrayList<Integer>();
                capsuleSlots.put(key, list);
            } else {
                amount = (Integer)amounts.get(key);
            }
            list.add(slot);
            amounts.put(key, amount += itemstack.field_77994_a * capacityMap.get(item));
        }
        for (Map.Entry entry : capsuleSlots.entrySet()) {
            CapsuleKey key = (CapsuleKey)entry.getKey();
            int amount = (Integer)amounts.get(key);
            List slots1 = (List)entry.getValue();
            int[] stackSizes = new int[sortedCapsules.length];
            int stackNum = 0;
            for (int i = 0; i < sortedCapsules.length; ++i) {
                stackSizes[i] = amount / sortedCapacities[i];
                amount -= stackSizes[i] * sortedCapacities[i];
                stackNum += (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
            }
            if (stackNum > slots1.size() + emptySlots.size()) continue;
            boolean tagIsNull = key.tag == null;
            int slotsSize = slots1.size();
            int emptySlotsSize = emptySlots.size();
            int slotMax = slotsSize + emptySlotsSize;
            int slot2 = 0;
            ArrayList<Integer> toAdd = new ArrayList<Integer>();
            ArrayList<Integer> toRemove = new ArrayList<Integer>();
            int i = 0;
            for (int sloti = 0; sloti < slotMax; ++sloti) {
                while (i < stackSizes.length && stackSizes[i] == 0) {
                    ++i;
                }
                slot2 = sloti < slotsSize ? ((Integer)slots1.get(sloti)).intValue() : ((Integer)emptySlots.get(sloti - slotsSize)).intValue();
                if (i >= stackSizes.length) {
                    inventory[slot2] = null;
                    if (sloti >= slotsSize) continue;
                    toAdd.add(slot2);
                    continue;
                }
                if (sloti >= slotsSize) {
                    toRemove.add(slot2);
                }
                if (stackSizes[i] > sortedStackLimits[i]) {
                    inventory[slot2] = new ItemStack((Item)sortedCapsules[i], sortedStackLimits[i], key.damage);
                    if (!tagIsNull) {
                        inventory[slot2].func_77982_d(key.tag);
                    }
                    int n = i;
                    stackSizes[n] = stackSizes[n] - sortedStackLimits[i];
                    continue;
                }
                inventory[slot2] = new ItemStack((Item)sortedCapsules[i], stackSizes[i], key.damage);
                if (!tagIsNull) {
                    inventory[slot2].func_77982_d(key.tag);
                }
                ++i;
            }
            emptySlots.addAll(toAdd);
            emptySlots.removeAll(toRemove);
        }
    }

    public static int fillFluid(ItemStack[] inventory, int[] slots, FluidStack resource, boolean doFill) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        if (resource == null) {
            return 0;
        }
        Fluid resourceFluid = resource.getFluid();
        int resourceFluidId = UtilFluid.getFluidID(resourceFluid);
        ArrayList<Integer> emptySlots = new ArrayList<Integer>();
        ArrayList<Integer> capsuleSlots = new ArrayList<Integer>();
        int amount = 0;
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        boolean resourceHasTag = resource.tag != null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                emptySlots.add(slot);
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule) || resourceFluidId != itemstack.func_77960_j() || (resourceHasTag || itemstack.func_77942_o()) && (!resourceHasTag || !resource.tag.equals((Object)itemstack.func_77978_p()))) continue;
            capsuleSlots.add(slot);
            amount += itemstack.field_77994_a * capacityMap.get(item);
        }
        int oldAmount = amount;
        amount += resource.amount;
        int[] stackSizes = new int[sortedCapsules.length];
        int stackNum = 0;
        for (int i = 0; i < sortedCapsules.length; ++i) {
            stackSizes[i] = amount / sortedCapacities[i];
            int stackN = (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
            if (stackN + stackNum > capsuleSlots.size() + emptySlots.size()) {
                stackSizes[i] = (capsuleSlots.size() + emptySlots.size() - stackNum) * sortedStackLimits[i];
                amount -= stackSizes[i] * sortedCapacities[i];
                break;
            }
            amount -= stackSizes[i] * sortedCapacities[i];
            stackNum += (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
        }
        if (!doFill) {
            return resource.amount - amount;
        }
        boolean tagIsNull = resource.tag == null;
        int slotsSize = capsuleSlots.size();
        int emptySlotsSize = emptySlots.size();
        int slotMax = slotsSize + emptySlotsSize;
        int slot2 = 0;
        int i = 0;
        for (int sloti = 0; sloti < slotMax; ++sloti) {
            while (i < stackSizes.length && stackSizes[i] == 0) {
                ++i;
            }
            slot2 = sloti < slotsSize ? ((Integer)capsuleSlots.get(sloti)).intValue() : ((Integer)emptySlots.get(sloti - slotsSize)).intValue();
            if (i >= stackSizes.length) {
                inventory[slot2] = null;
                continue;
            }
            if (stackSizes[i] > sortedStackLimits[i]) {
                inventory[slot2] = new ItemStack((Item)sortedCapsules[i], sortedStackLimits[i], resourceFluidId);
                if (!tagIsNull) {
                    inventory[slot2].func_77982_d(resource.tag);
                }
                int n = i;
                stackSizes[n] = stackSizes[n] - sortedStackLimits[i];
                continue;
            }
            inventory[slot2] = new ItemStack((Item)sortedCapsules[i], stackSizes[i], resourceFluidId);
            if (!tagIsNull) {
                inventory[slot2].func_77982_d(resource.tag);
            }
            ++i;
        }
        return resource.amount - amount;
    }

    public static FluidStack drainFluid(ItemStack[] inventory, int[] slots, FluidStack resource, boolean doDrain) {
        if (resource == null) {
            return null;
        }
        Fluid resourceFluid = resource.getFluid();
        int resourceFluidId = UtilFluid.getFluidID(resourceFluid);
        FluidStack ret = resource.copy();
        ArrayList<Integer> emptySlots = new ArrayList<Integer>();
        ArrayList<Integer> capsuleSlots = new ArrayList<Integer>();
        int amount = 0;
        boolean resourceHasTag = resource.tag != null;
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                emptySlots.add(slot);
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule) || resourceFluidId != itemstack.func_77960_j() || (resourceHasTag || itemstack.func_77942_o()) && (!resourceHasTag || !resource.tag.equals((Object)itemstack.func_77978_p()))) continue;
            capsuleSlots.add(slot);
            amount += itemstack.field_77994_a * capacityMap.get(item);
        }
        int oldAmount = amount;
        ret.amount = Math.min(resource.amount, oldAmount);
        amount -= ret.amount;
        int[] stackSizes = new int[sortedCapsules.length];
        int stackNum = 0;
        for (int i = 0; i < sortedCapsules.length; ++i) {
            stackSizes[i] = amount / sortedCapacities[i];
            int stackN = (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
            if (stackN + stackNum > capsuleSlots.size() + emptySlots.size()) {
                return null;
            }
            amount -= stackSizes[i] * sortedCapacities[i];
            stackNum += (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
        }
        if (!doDrain) {
            return ret;
        }
        int slotsSize = capsuleSlots.size();
        int emptySlotsSize = emptySlots.size();
        int slotMax = slotsSize + emptySlotsSize;
        int slot2 = 0;
        int i = 0;
        for (int sloti = 0; sloti < slotMax; ++sloti) {
            while (i < stackSizes.length && stackSizes[i] == 0) {
                ++i;
            }
            slot2 = sloti < slotsSize ? ((Integer)capsuleSlots.get(sloti)).intValue() : ((Integer)emptySlots.get(sloti - slotsSize)).intValue();
            if (i >= stackSizes.length) {
                inventory[slot2] = null;
                continue;
            }
            if (stackSizes[i] > sortedStackLimits[i]) {
                inventory[slot2] = new ItemStack((Item)sortedCapsules[i], sortedStackLimits[i], resourceFluidId);
                if (resourceHasTag) {
                    inventory[slot2].func_77982_d(resource.tag);
                }
                int n = i;
                stackSizes[n] = stackSizes[n] - sortedStackLimits[i];
                continue;
            }
            inventory[slot2] = new ItemStack((Item)sortedCapsules[i], stackSizes[i], resourceFluidId);
            if (resourceHasTag) {
                inventory[slot2].func_77982_d(resource.tag);
            }
            ++i;
        }
        return ret;
    }

    public static FluidStack drainFluid(ItemStack[] inventory, int[] slots, int maxDrain, boolean doDrain) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        if (maxDrain == 0) {
            return null;
        }
        Fluid resourceFluid = null;
        int resourceFluidId = -1;
        FluidStack ret = null;
        ArrayList<Integer> emptySlots = new ArrayList<Integer>();
        ArrayList<Integer> capsuleSlots = new ArrayList<Integer>();
        int amount = 0;
        boolean resourceHasTag = false;
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                emptySlots.add(slot);
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule)) continue;
            if (ret == null) {
                resourceFluidId = itemstack.func_77960_j();
                resourceFluid = UtilFluid.getFluid(resourceFluidId);
                if (resourceFluid != null) {
                    ret = new FluidStack(resourceFluid, maxDrain);
                    resourceHasTag = itemstack.func_77942_o();
                    if (resourceHasTag) {
                        ret.tag = itemstack.func_77978_p();
                    }
                } else {
                    resourceFluidId = -1;
                }
            }
            if (ret == null || resourceFluidId != itemstack.func_77960_j() || (resourceHasTag || itemstack.func_77942_o()) && (!resourceHasTag || !ret.tag.equals((Object)itemstack.func_77978_p()))) continue;
            capsuleSlots.add(slot);
            amount += itemstack.field_77994_a * capacityMap.get(item);
        }
        if (ret == null) {
            return null;
        }
        int oldAmount = amount;
        ret.amount = Math.min(maxDrain, oldAmount);
        amount -= ret.amount;
        int[] stackSizes = new int[sortedCapsules.length];
        int stackNum = 0;
        for (int i = 0; i < sortedCapsules.length; ++i) {
            stackSizes[i] = amount / sortedCapacities[i];
            int stackN = (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
            if (stackN + stackNum > capsuleSlots.size() + emptySlots.size()) {
                return null;
            }
            amount -= stackSizes[i] * sortedCapacities[i];
            stackNum += (stackSizes[i] + sortedStackLimits[i] - 1) / sortedStackLimits[i];
        }
        if (!doDrain) {
            return ret;
        }
        int slotsSize = capsuleSlots.size();
        int emptySlotsSize = emptySlots.size();
        int slotMax = slotsSize + emptySlotsSize;
        int slot2 = 0;
        int i = 0;
        for (int sloti = 0; sloti < slotMax; ++sloti) {
            while (i < stackSizes.length && stackSizes[i] == 0) {
                ++i;
            }
            slot2 = sloti < slotsSize ? ((Integer)capsuleSlots.get(sloti)).intValue() : ((Integer)emptySlots.get(sloti - slotsSize)).intValue();
            if (i >= stackSizes.length) {
                inventory[slot2] = null;
                continue;
            }
            if (stackSizes[i] > sortedStackLimits[i]) {
                inventory[slot2] = new ItemStack((Item)sortedCapsules[i], sortedStackLimits[i], resourceFluidId);
                if (resourceHasTag) {
                    inventory[slot2].func_77982_d(ret.tag);
                }
                int n = i;
                stackSizes[n] = stackSizes[n] - sortedStackLimits[i];
                continue;
            }
            inventory[slot2] = new ItemStack((Item)sortedCapsules[i], stackSizes[i], resourceFluidId);
            if (resourceHasTag) {
                inventory[slot2].func_77982_d(ret.tag);
            }
            ++i;
        }
        return ret;
    }

    public static boolean canFillFluid(ItemStack[] inventory, int[] slots, Fluid fluid) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        if (fluid == null) {
            return false;
        }
        int amount = 0;
        int slotNum = 0;
        int emptySlotNum = 0;
        int fluidId = UtilFluid.getFluidID(fluid);
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                ++emptySlotNum;
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule) || itemstack.func_77960_j() != fluidId) continue;
            amount += itemstack.field_77994_a * capacityMap.get(item);
            ++slotNum;
        }
        return amount <= (slotNum + emptySlotNum) * sortedCapacities[0] * sortedStackLimits[0];
    }

    public static boolean canDrainFluid(ItemStack[] inventory, int[] slots, Fluid fluid) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        if (fluid == null) {
            return false;
        }
        int fluidId = UtilFluid.getFluidID(fluid);
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null || !(itemstack.func_77973_b() instanceof ItemCapsule) || itemstack.func_77960_j() != fluidId) continue;
            return true;
        }
        return false;
    }

    public static FluidTankInfo[] getTankInfo(ItemStack[] inventory, int[] slots) {
        if (capacityMap == null) {
            UtilTransfer.initFluidParams();
        }
        HashMap<CapsuleKey, Integer> amounts = new HashMap<CapsuleKey, Integer>();
        HashMap<CapsuleKey, Integer> slotNums = new HashMap<CapsuleKey, Integer>();
        int emptySlotNum = 0;
        boolean slotsIsNull = slots == null;
        int iMax = slotsIsNull ? inventory.length : slots.length;
        ItemStack itemstack = null;
        int slot = -1;
        Item item = null;
        for (int i = 0; i < iMax; ++i) {
            if (slotsIsNull) {
                slot = i;
                itemstack = inventory[i];
            } else {
                slot = slots[i];
                itemstack = inventory[slot];
            }
            if (itemstack == null) {
                ++emptySlotNum;
                continue;
            }
            item = itemstack.func_77973_b();
            if (!(item instanceof ItemCapsule)) continue;
            CapsuleKey key = new CapsuleKey(itemstack);
            if (UtilFluid.getFluid(key.damage) == null) continue;
            int amount = 0;
            int slotNum = 0;
            if (amounts.containsKey(key)) {
                amount = (Integer)amounts.get(key);
                slotNum = (Integer)slotNums.get(key);
            }
            amounts.put(key, amount += itemstack.field_77994_a * capacityMap.get(item));
            slotNums.put(key, ++slotNum);
        }
        FluidTankInfo[] ret = null;
        if (amounts.size() == 0) {
            ret = new FluidTankInfo[]{new FluidTankInfo(null, emptySlotNum * sortedCapacities[0] * sortedStackLimits[0])};
            return ret;
        }
        ret = new FluidTankInfo[amounts.size()];
        int i = 0;
        for (Map.Entry entry : amounts.entrySet()) {
            CapsuleKey key = (CapsuleKey)entry.getKey();
            int amount = (Integer)entry.getValue();
            FluidStack fluid = new FluidStack(UtilFluid.getFluid(key.damage), amount);
            if (key.tag != null) {
                fluid.tag = key.tag;
            }
            ret[i] = new FluidTankInfo(fluid, ((Integer)slotNums.get(key) + emptySlotNum) * sortedCapacities[0] * sortedStackLimits[0]);
            ++i;
        }
        return ret;
    }

    public static class CapsuleKey {
        int damage = -1;
        NBTTagCompound tag = null;

        public CapsuleKey(ItemStack itemstack) {
            this.damage = itemstack.func_77960_j();
            this.tag = itemstack.func_77942_o() ? itemstack.func_77978_p() : null;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.damage;
            result = 31 * result + (this.tag == null ? 0 : this.tag.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            CapsuleKey other = (CapsuleKey)obj;
            if (this.damage != other.damage) {
                return false;
            }
            return !(this.tag == null ? other.tag != null : !this.tag.equals((Object)other.tag));
        }
    }

    public static class InventorySelector
    implements IInventorySelector {
        protected IInventory selected = null;

        @Override
        public IInventory getSelectedInventory() {
            return this.selected;
        }

        @Override
        public boolean selectInventoryToInsertTo(TileEntity from, ForgeDirection direction) {
            IInventory to = this.selectInventoryToInsertTo(from.func_145831_w(), from.field_145851_c, from.field_145848_d, from.field_145849_e, direction);
            if (to == null) {
                return false;
            }
            this.selected = to;
            return true;
        }

        public IInventory selectInventoryToInsertTo(World world, int fromX, int fromY, int fromZ, ForgeDirection direction) {
            IInventory chest;
            TileEntity te = UtilDirection.getTileEntity((IBlockAccess)world, fromX, fromY, fromZ, direction);
            if (!(te instanceof IInventory)) {
                return null;
            }
            IInventory to = (IInventory)te;
            Block block = UtilDirection.getBlock((IBlockAccess)world, fromX, fromY, fromZ, direction);
            if (block instanceof BlockChest && (chest = ((BlockChest)block).func_149951_m(world, fromX + direction.offsetX, fromY + direction.offsetY, fromZ + direction.offsetZ)) != null) {
                to = chest;
            }
            return to;
        }

        @Override
        public int[] getSlotToInsertTo(ForgeDirection direction) {
            if (this.selected == null) {
                return null;
            }
            int toSide = direction.getOpposite().ordinal();
            int[] toSlots = null;
            if (!(this.selected instanceof ISidedInventory)) {
                toSlots = new int[this.selected.func_70302_i_()];
                for (int i = 0; i < this.selected.func_70302_i_(); ++i) {
                    toSlots[i] = i;
                }
            } else {
                toSlots = ((ISidedInventory)this.selected).func_94128_d(toSide);
            }
            return toSlots;
        }

        @Override
        public boolean selectInventoryToExtractFrom(TileEntity to, ForgeDirection direction) {
            IInventory from = this.selectInventoryToExtractFrom(to.func_145831_w(), to.field_145851_c, to.field_145848_d, to.field_145849_e, direction);
            if (from == null) {
                return false;
            }
            this.selected = from;
            return true;
        }

        public IInventory selectInventoryToExtractFrom(World world, int toX, int toY, int toZ, ForgeDirection direction) {
            IInventory chest;
            TileEntity te = UtilDirection.getTileEntity((IBlockAccess)world, toX, toY, toZ, direction);
            if (!(te instanceof IInventory)) {
                return null;
            }
            IInventory from = (IInventory)te;
            Block block = UtilDirection.getBlock((IBlockAccess)world, toX, toY, toZ, direction);
            if (block instanceof BlockChest && (chest = ((BlockChest)block).func_149951_m(world, toX + direction.offsetX, toY + direction.offsetY, toZ + direction.offsetZ)) != null) {
                from = chest;
            }
            return from;
        }

        @Override
        public int[] getSlotToExtractFrom(ForgeDirection direction) {
            if (this.selected == null) {
                return null;
            }
            int fromSide = direction.getOpposite().ordinal();
            int[] fromSlots = null;
            if (!(this.selected instanceof ISidedInventory)) {
                fromSlots = new int[this.selected.func_70302_i_()];
                for (int i = 0; i < this.selected.func_70302_i_(); ++i) {
                    fromSlots[i] = i;
                }
            } else {
                fromSlots = ((ISidedInventory)this.selected).func_94128_d(fromSide);
            }
            return fromSlots;
        }
    }

    static interface IInventorySelector {
        public IInventory getSelectedInventory();

        public boolean selectInventoryToInsertTo(TileEntity var1, ForgeDirection var2);

        public int[] getSlotToInsertTo(ForgeDirection var1);

        public boolean selectInventoryToExtractFrom(TileEntity var1, ForgeDirection var2);

        public int[] getSlotToExtractFrom(ForgeDirection var1);
    }
}

