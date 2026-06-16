package dev.clayium.clayium.menu;

import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumMenus;
import java.util.List;
import java.util.Optional;
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
    public static final int DATA_COUNT = 3;

    private static final int DATA_COOK_TIME = 0;
    private static final int DATA_TIME_TO_COOK = 1;
    private static final int DATA_COOKING_METHOD = 2;
    private static final int PLAYER_INVENTORY_START = ClayWorkTableOperations.VISIBLE_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 27;
    private static final int HOTBAR_END = PLAYER_INVENTORY_END + 9;

    private final Container table;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public ClayWorkTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ClayWorkTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        this(containerId, playerInventory, access, new SimpleContainer(ClayWorkTableOperations.SLOT_COUNT), new SimpleContainerData(DATA_COUNT));
    }

    public ClayWorkTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, Container table, ContainerData data) {
        super(ClayiumMenus.CLAY_WORK_TABLE.get(), containerId);
        checkContainerSize(table, ClayWorkTableOperations.SLOT_COUNT);
        checkContainerDataCount(data, DATA_COUNT);
        this.table = table;
        this.data = data;
        this.access = access;

        this.addSlot(new Slot(table, ClayWorkTableOperations.INPUT_SLOT, 17, 30));
        this.addSlot(new ToolSlot(table, ClayWorkTableOperations.TOOL_SLOT, 80, 17));
        this.addSlot(new OutputSlot(table, ClayWorkTableOperations.FIRST_OUTPUT_SLOT, 143, 30));
        this.addSlot(new OutputSlot(table, ClayWorkTableOperations.FIRST_OUTPUT_SLOT + 1, 143, 55));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }

        this.addDataSlots(data);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id < 1 || id > 6) {
            return false;
        }
        if (!player.level().isClientSide()) {
            this.pushButton(id);
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

    public int getCookTime() {
        return this.data.get(DATA_COOK_TIME);
    }

    public int getTimeToCook() {
        return this.data.get(DATA_TIME_TO_COOK);
    }

    public int getCookingMethod() {
        return this.data.get(DATA_COOKING_METHOD);
    }

    public int getCookProgressScaled(int pixels) {
        int timeToCook = this.getTimeToCook();
        if (timeToCook == 0 || this.getCookingMethod() == 0) {
            return 0;
        }
        return this.getCookTime() * pixels / timeToCook;
    }

    public boolean canProcessButton(int buttonId) {
        return this.getButtonState(buttonId) != 0;
    }

    public int getButtonState(int buttonId) {
        return this.getButtonState(buttonId, true);
    }

    private int getServerButtonState(int buttonId) {
        return this.getButtonState(buttonId, false);
    }

    private int getButtonState(int buttonId, boolean allowSyncedContinuationFallback) {
        int cookingMethod = this.getCookingMethod();
        if (cookingMethod != 0 && cookingMethod == buttonId) {
            if (this.findProcessableOperation(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT).isPresent()) {
                return 1;
            }
            if (allowSyncedContinuationFallback && this.getTimeToCook() > 0 && ClayWorkTableOperations.canUseToolForButton(buttonId, this.table.getItem(ClayWorkTableOperations.TOOL_SLOT))) {
                return 1;
            }
        }
        Optional<ClayWorkTableOperations.Operation> inputOperation = this.findProcessableOperation(buttonId, ClayWorkTableOperations.INPUT_SLOT);
        if (inputOperation.isPresent()) {
            return cookingMethod == 0 ? 1 : 2;
        }
        return 0;
    }

    public int getWorkTicksForButton(int buttonId) {
        return this.findPreviewOperation(buttonId)
                .map(ClayWorkTableOperations.Operation::workTicks)
                .orElse(0);
    }

    public List<ItemStack> getPreviewOutputs(int buttonId) {
        if (this.getButtonState(buttonId) != 1) {
            return List.of();
        }
        return this.findPreviewOperation(buttonId)
                .filter(operation -> this.canFitOutputs(operation.createOutputs()))
                .map(ClayWorkTableOperations.Operation::createOutputs)
                .orElse(List.of());
    }

    private void pushButton(int buttonId) {
        int buttonState = this.getServerButtonState(buttonId);
        if (buttonState == 0) {
            return;
        }
        if (buttonState == 2) {
            this.clearWorkState();
            this.table.setChanged();
            this.broadcastChanges();
            return;
        }
        if (this.getCookingMethod() == 0 && !this.startWork(buttonId)) {
            return;
        }

        this.data.set(DATA_COOK_TIME, this.getCookTime() + 1);
        if (this.getCookTime() >= this.getTimeToCook()) {
            this.completeWork(buttonId);
        }
        this.table.setChanged();
        this.broadcastChanges();
    }

    private boolean startWork(int buttonId) {
        Optional<ClayWorkTableOperations.Operation> operation = this.findProcessableOperation(buttonId, ClayWorkTableOperations.INPUT_SLOT);
        if (operation.isEmpty()) {
            return false;
        }
        ItemStack input = this.table.getItem(ClayWorkTableOperations.INPUT_SLOT);
        ItemStack reservedInput = input.split(operation.get().inputCount());
        if (input.isEmpty()) {
            this.table.setItem(ClayWorkTableOperations.INPUT_SLOT, ItemStack.EMPTY);
        }
        this.table.setItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT, reservedInput);
        this.data.set(DATA_COOK_TIME, 0);
        this.data.set(DATA_TIME_TO_COOK, operation.get().workTicks());
        this.data.set(DATA_COOKING_METHOD, buttonId);
        return true;
    }

    private void completeWork(int buttonId) {
        this.findProcessableOperation(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT)
                .map(ClayWorkTableOperations.Operation::createOutputs)
                .filter(this::canFitOutputs)
                .ifPresent(this::addOutputs);
        this.clearWorkState();
    }

    private void clearWorkState() {
        this.data.set(DATA_COOK_TIME, 0);
        this.data.set(DATA_TIME_TO_COOK, 0);
        this.data.set(DATA_COOKING_METHOD, 0);
        this.table.setItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT, ItemStack.EMPTY);
    }

    private Optional<ClayWorkTableOperations.Operation> findPreviewOperation(int buttonId) {
        if (this.getCookingMethod() == buttonId) {
            Optional<ClayWorkTableOperations.Operation> internalOperation = this.findProcessableOperation(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT);
            if (internalOperation.isPresent()) {
                return internalOperation;
            }
        }
        return this.findProcessableOperation(buttonId, ClayWorkTableOperations.INPUT_SLOT);
    }

    private Optional<ClayWorkTableOperations.Operation> findProcessableOperation(int buttonId, int inputSlot) {
        ItemStack input = this.table.getItem(inputSlot);
        ItemStack tool = this.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        return ClayWorkTableOperations.find(buttonId, input, tool)
                .filter(operation -> this.canFitOutputs(operation.createOutputs()));
    }

    private boolean canFitOutputs(List<ItemStack> outputs) {
        if (outputs.size() > ClayWorkTableOperations.OUTPUT_SLOT_COUNT) {
            return false;
        }
        for (int index = 0; index < outputs.size(); index++) {
            if (!this.canFitOutput(ClayWorkTableOperations.FIRST_OUTPUT_SLOT + index, outputs.get(index))) {
                return false;
            }
        }
        return true;
    }

    private boolean canFitOutput(int slot, ItemStack output) {
        if (output.isEmpty()) {
            return true;
        }
        ItemStack existing = this.table.getItem(slot);
        int maxStackSize = Math.min(this.table.getMaxStackSize(), output.getMaxStackSize());
        if (existing.isEmpty()) {
            return output.getCount() <= maxStackSize;
        }
        return ItemStack.isSameItemSameComponents(existing, output)
                && existing.getCount() + output.getCount() <= Math.min(maxStackSize, existing.getMaxStackSize());
    }

    private void addOutputs(List<ItemStack> outputs) {
        for (int index = 0; index < outputs.size(); index++) {
            ItemStack output = outputs.get(index);
            int slot = ClayWorkTableOperations.FIRST_OUTPUT_SLOT + index;
            ItemStack existing = this.table.getItem(slot);
            if (existing.isEmpty()) {
                this.table.setItem(slot, output.copy());
            } else {
                existing.grow(output.getCount());
                this.table.setChanged();
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
