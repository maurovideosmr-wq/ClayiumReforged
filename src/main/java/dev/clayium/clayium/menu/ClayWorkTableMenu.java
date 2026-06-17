package dev.clayium.clayium.menu;

import dev.clayium.clayium.recipe.ClayWorkTableAction;
import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import dev.clayium.clayium.recipe.ClayWorkTableRecipeCache;
import dev.clayium.clayium.recipe.ClayWorkTableToolRequirement;
import dev.clayium.clayium.item.ClayCraftingToolItem;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumMenus;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.item.crafting.RecipeHolder;
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
import net.minecraft.world.level.Level;

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
    private final Level level;

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
        this.level = playerInventory.player.level();

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
        } else if (this.isKnownInput(stack)) {
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

    private int getButtonState(int buttonId, boolean allowSyncedContinuationFallback) {
        int cookingMethod = this.getCookingMethod();
        if (cookingMethod != 0) {
            if (cookingMethod != buttonId) {
                return 0;
            }
            if (this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT).isPresent()) {
                return 1;
            }
            if (allowSyncedContinuationFallback
                    && this.getTimeToCook() > 0
                    && ClayWorkTableOperations.canUseToolForButton(buttonId, this.table.getItem(ClayWorkTableOperations.TOOL_SLOT))
                    && this.hasEnoughToolUsesFor(buttonId, this.remainingClicks())) {
                return 1;
            }
            return 0;
        }
        Optional<RecipeHolder<ClayWorkTableRecipe>> inputRecipe = this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INPUT_SLOT);
        if (inputRecipe.isPresent()) {
            return 1;
        }
        return 0;
    }

    public int getWorkTicksForButton(int buttonId) {
        return this.findPreviewRecipe(buttonId)
                .map(holder -> holder.value().workTicks())
                .orElse(0);
    }

    public List<ItemStack> getPreviewOutputs(int buttonId) {
        if (this.getButtonState(buttonId) != 1) {
            return List.of();
        }
        return this.findPreviewRecipe(buttonId)
                .map(RecipeHolder::value)
                .filter(recipe -> this.canFitOutputs(recipe.createOutputs()))
                .map(ClayWorkTableRecipe::createOutputs)
                .orElse(List.of());
    }

    private void pushButton(int buttonId) {
        Optional<RecipeHolder<ClayWorkTableRecipe>> recipe = this.findServerRecipe(buttonId);
        if (recipe.isEmpty()) {
            return;
        }
        if (this.getCookingMethod() == 0 && !this.startWork(buttonId, recipe.get().value())) {
            return;
        }

        this.consumeCraftingToolUse(buttonId);
        this.data.set(DATA_COOK_TIME, this.getCookTime() + 1);
        if (this.getCookTime() >= this.getTimeToCook()) {
            this.completeWork(recipe.get().value());
        }
        this.table.setChanged();
        this.broadcastChanges();
    }

    private boolean startWork(int buttonId, ClayWorkTableRecipe recipe) {
        if (!this.canFitOutputs(recipe.createOutputs())) {
            return false;
        }
        ItemStack input = this.table.getItem(ClayWorkTableOperations.INPUT_SLOT);
        ItemStack reservedInput = input.split(recipe.inputCount());
        if (input.isEmpty()) {
            this.table.setItem(ClayWorkTableOperations.INPUT_SLOT, ItemStack.EMPTY);
        }
        this.table.setItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT, reservedInput);
        this.data.set(DATA_COOK_TIME, 0);
        this.data.set(DATA_TIME_TO_COOK, recipe.workTicks());
        this.data.set(DATA_COOKING_METHOD, buttonId);
        return true;
    }

    private void completeWork(ClayWorkTableRecipe recipe) {
        List<ItemStack> outputs = recipe.createOutputs();
        if (this.canFitOutputs(outputs)) {
            this.addOutputs(outputs);
        }
        this.clearWorkState();
    }

    private void consumeCraftingToolUse(int buttonId) {
        if (buttonId < 3) {
            return;
        }
        ItemStack tool = this.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        if (tool.getItem() instanceof ClayCraftingToolItem craftingTool) {
            this.table.setItem(ClayWorkTableOperations.TOOL_SLOT, craftingTool.getAfterWorkTableUse(tool));
        }
    }

    private void clearWorkState() {
        this.data.set(DATA_COOK_TIME, 0);
        this.data.set(DATA_TIME_TO_COOK, 0);
        this.data.set(DATA_COOKING_METHOD, 0);
        this.table.setItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT, ItemStack.EMPTY);
    }

    private Optional<RecipeHolder<ClayWorkTableRecipe>> findPreviewRecipe(int buttonId) {
        if (this.getCookingMethod() == buttonId) {
            Optional<RecipeHolder<ClayWorkTableRecipe>> internalRecipe = this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT);
            if (internalRecipe.isPresent()) {
                return internalRecipe;
            }
        }
        return this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INPUT_SLOT);
    }

    private Optional<RecipeHolder<ClayWorkTableRecipe>> findServerRecipe(int buttonId) {
        int cookingMethod = this.getCookingMethod();
        if (cookingMethod != 0) {
            if (cookingMethod != buttonId) {
                return Optional.empty();
            }
            return this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INTERNAL_INPUT_SLOT);
        }
        return this.findProcessableRecipe(buttonId, ClayWorkTableOperations.INPUT_SLOT);
    }

    private Optional<RecipeHolder<ClayWorkTableRecipe>> findProcessableRecipe(int buttonId, int inputSlot) {
        ItemStack input = this.table.getItem(inputSlot);
        ItemStack tool = this.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        return ClayWorkTableRecipeCache.findBest(this.level, ClayWorkTableAction.byButtonId(buttonId), input, tool)
                .filter(holder -> this.canFitOutputs(holder.value().createOutputs()))
                .filter(holder -> this.hasEnoughToolUsesFor(buttonId, holder.value()));
    }

    private boolean hasEnoughToolUsesFor(int buttonId, ClayWorkTableRecipe recipe) {
        if (recipe.toolRequirement() == ClayWorkTableToolRequirement.NONE) {
            return true;
        }
        int requiredUses = this.getCookingMethod() == 0 ? recipe.workTicks() : this.remainingClicks();
        return this.hasEnoughToolUsesFor(buttonId, requiredUses);
    }

    private boolean hasEnoughToolUsesFor(int buttonId, int requiredUses) {
        if (buttonId < 3 || requiredUses <= 0) {
            return true;
        }
        ItemStack tool = this.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        if (!(tool.getItem() instanceof ClayCraftingToolItem)) {
            return true;
        }
        return remainingWorkTableUses(tool) >= requiredUses;
    }

    private int remainingClicks() {
        return Math.max(1, this.getTimeToCook() - this.getCookTime());
    }

    private static int remainingWorkTableUses(ItemStack tool) {
        if (!tool.isDamageableItem()) {
            return Integer.MAX_VALUE;
        }
        int maxDamage = tool.getMaxDamage();
        int damage = tool.getDamageValue();
        if (damage >= maxDamage) {
            return 0;
        }
        return maxDamage - damage;
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

    private boolean isKnownInput(ItemStack stack) {
        return ClayWorkTableRecipeCache.isKnownInput(this.level, stack) || ClayWorkTableOperations.isKnownInput(stack);
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
