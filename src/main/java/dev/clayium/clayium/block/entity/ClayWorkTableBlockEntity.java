package dev.clayium.clayium.block.entity;

import dev.clayium.clayium.menu.ClayWorkTableMenu;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.registry.ClayiumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class ClayWorkTableBlockEntity extends BlockEntity implements Container, MenuProvider {
    private static final Component TITLE = Component.translatable("container.clayium.clay_work_table");
    private static final String TAG_COOK_TIME = "CookTime";
    private static final String TAG_TIME_TO_COOK = "TimeToCook";
    private static final String TAG_COOKING_METHOD = "CookingMethod";

    private final NonNullList<ItemStack> items = NonNullList.withSize(ClayWorkTableOperations.SLOT_COUNT, ItemStack.EMPTY);
    private final ContainerData data = new SimpleContainerData(ClayWorkTableMenu.DATA_COUNT);

    public ClayWorkTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ClayiumBlockEntities.CLAY_WORK_TABLE.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
        this.data.set(0, input.getIntOr(TAG_COOK_TIME, 0));
        this.data.set(1, input.getIntOr(TAG_TIME_TO_COOK, 0));
        this.data.set(2, input.getIntOr(TAG_COOKING_METHOD, 0));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt(TAG_COOK_TIME, this.data.get(0));
        output.putInt(TAG_TIME_TO_COOK, this.data.get(1));
        output.putInt(TAG_COOKING_METHOD, this.data.get(2));
        super.saveAdditional(output);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ClayWorkTableMenu(containerId, playerInventory, ContainerLevelAccess.create(player.level(), this.worldPosition), this, this.data);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(this.items, slot, amount);
        if (!result.isEmpty()) {
            this.setChanged();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        int maxStackSize = Math.min(this.getMaxStackSize(), stack.getMaxStackSize());
        if (stack.getCount() > maxStackSize) {
            stack.setCount(maxStackSize);
        }
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.data.set(0, 0);
        this.data.set(1, 0);
        this.data.set(2, 0);
        this.setChanged();
    }
}
