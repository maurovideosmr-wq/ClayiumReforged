package dev.clayium.clayium.menu;

import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumMenus;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ClayWorkTableMenu extends AbstractContainerMenu {
    private static final int PLAYER_INVENTORY_START = ClayWorkTableOperations.SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 27;
    private static final int HOTBAR_END = PLAYER_INVENTORY_END + 9;

    private final Container table;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public ClayWorkTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ClayWorkTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        this(containerId, playerInventory, access, new SimpleContainer(ClayWorkTableOperations.SLOT_COUNT), new SimpleContainerData(2));
    }

    public ClayWorkTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, Container table, ContainerData data) {
        super(ClayiumMenus.CLAY_WORK_TABLE.get(), containerId);
        checkContainerSize(table, ClayWorkTableOperations.SLOT_COUNT);
        checkContainerDataCount(data, 2);
        this.table = table;
        this.data = data;
        this.access = access;

        this.addSlot(new Slot(table, ClayWorkTableOperations.INPUT_SLOT, 44, 35));
        this.addSlot(new ToolSlot(table, ClayWorkTableOperations.TOOL_SLOT, 44, 57));
        this.addSlot(new OutputSlot(table, ClayWorkTableOperations.FIRST_OUTPUT_SLOT, 116, 26));
        this.addSlot(new OutputSlot(table, ClayWorkTableOperations.FIRST_OUTPUT_SLOT + 1, 134, 26));
        this.addSlot(new OutputSlot(table, ClayWorkTableOperations.FIRST_OUTPUT_SLOT + 2, 152, 26));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 94 + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 152));
        }

        this.addDataSlots(data);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id < 1 || id > 6) {
            return false;
        }
        if (!player.level().isClientSide()) {
            this.processButton(id);
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (index < PLAYER_INVENTORY_START) {
            if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_START, HOTBAR_END, true)) {
                return ItemStack.EMPTY;
            }
        } else if (ClayWorkTableOperations.isTool(stack)) {
            if (!this.moveItemStackTo(stack, ClayWorkTableOperations.TOOL_SLOT, ClayWorkTableOperations.TOOL_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (ClayWorkTableOperations.isKnownInput(stack)) {
            if (!this.moveItemStackTo(stack, ClayWorkTableOperations.INPUT_SLOT, ClayWorkTableOperations.INPUT_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < PLAYER_INVENTORY_END) {
            if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ClayiumBlocks.CLAY_WORK_TABLE.get());
    }

    public int getLastWorkTicks() {
        return this.data.get(0);
    }

    public int getLastButtonId() {
        return this.data.get(1);
    }

    private void processButton(int buttonId) {
        ItemStack input = this.table.getItem(ClayWorkTableOperations.INPUT_SLOT);
        ItemStack tool = this.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        ClayWorkTableOperations.find(buttonId, input, tool)
                .filter(operation -> this.canFitOutputs(operation.createOutputs()))
                .ifPresent(operation -> {
                    input.shrink(operation.inputCount());
                    this.addOutputs(operation.createOutputs());
                    this.data.set(0, operation.workTicks());
                    this.data.set(1, buttonId);
                    this.table.setChanged();
                    this.broadcastChanges();
                });
    }

    private boolean canFitOutputs(List<ItemStack> outputs) {
        ItemStack[] simulated = new ItemStack[ClayWorkTableOperations.OUTPUT_SLOT_COUNT];
        for (int index = 0; index < simulated.length; index++) {
            simulated[index] = this.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT + index).copy();
        }

        for (ItemStack output : outputs) {
            ItemStack remaining = output.copy();
            for (int index = 0; index < simulated.length; index++) {
                ItemStack slotStack = simulated[index];
                if (slotStack.isEmpty()) {
                    simulated[index] = remaining.copy();
                    remaining.setCount(0);
                    break;
                }
                if (ItemStack.isSameItemSameComponents(slotStack, remaining) && slotStack.getCount() < slotStack.getMaxStackSize()) {
                    int move = Math.min(remaining.getCount(), slotStack.getMaxStackSize() - slotStack.getCount());
                    slotStack.grow(move);
                    remaining.shrink(move);
                    if (remaining.isEmpty()) {
                        break;
                    }
                }
            }
            if (!remaining.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void addOutputs(List<ItemStack> outputs) {
        for (ItemStack output : outputs) {
            ItemStack remaining = output.copy();
            for (int slot = ClayWorkTableOperations.FIRST_OUTPUT_SLOT; slot < ClayWorkTableOperations.SLOT_COUNT; slot++) {
                ItemStack existing = this.table.getItem(slot);
                if (existing.isEmpty()) {
                    this.table.setItem(slot, remaining.copy());
                    remaining.setCount(0);
                    break;
                }
                if (ItemStack.isSameItemSameComponents(existing, remaining) && existing.getCount() < existing.getMaxStackSize()) {
                    int move = Math.min(remaining.getCount(), existing.getMaxStackSize() - existing.getCount());
                    existing.grow(move);
                    remaining.shrink(move);
                    this.table.setChanged();
                    if (remaining.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    private static class ToolSlot extends Slot {
        ToolSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return ClayWorkTableOperations.isTool(stack);
        }
    }

    private static class OutputSlot extends Slot {
        OutputSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
