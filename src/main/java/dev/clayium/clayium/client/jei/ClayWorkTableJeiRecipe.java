package dev.clayium.clayium.client.jei;

import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.registry.ClayiumItems;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

record ClayWorkTableJeiRecipe(
        String id,
        ItemStack input,
        List<ItemStack> tools,
        List<ItemStack> outputs,
        int buttonId,
        int workTicks
) {
    static List<ClayWorkTableJeiRecipe> all() {
        return ClayWorkTableOperations.operations().stream()
                .sorted(Comparator
                        .comparingInt(ClayWorkTableOperations.Operation::buttonId)
                        .thenComparing(operation -> itemKey(operation.input().get()))
                        .thenComparingInt(ClayWorkTableOperations.Operation::inputCount))
                .map(ClayWorkTableJeiRecipe::from)
                .toList();
    }

    private static ClayWorkTableJeiRecipe from(ClayWorkTableOperations.Operation operation) {
        ItemLike input = operation.input().get();
        return new ClayWorkTableJeiRecipe(
                operation.buttonId() + "/" + itemKey(input) + "/" + operation.inputCount(),
                stack(input, operation.inputCount()),
                toolStacks(operation.toolRequirement()),
                operation.outputs().stream()
                        .map(output -> stack(output.item().get(), output.count()))
                        .toList(),
                operation.buttonId(),
                operation.workTicks()
        );
    }

    ItemStack mainOutput() {
        return this.outputs.getFirst();
    }

    ItemStack byproductOutput() {
        return this.outputs.size() > 1 ? this.outputs.get(1) : ItemStack.EMPTY;
    }

    boolean hasByproductOutput() {
        return !this.byproductOutput().isEmpty();
    }

    private static List<ItemStack> toolStacks(ClayWorkTableOperations.ToolRequirement toolRequirement) {
        return switch (toolRequirement) {
            case NONE -> List.of();
            case ROLLING_PIN -> List.of(stack(ClayiumItems.CLAY_ROLLING_PIN.get(), 1));
            case SLICER_OR_SPATULA -> List.of(
                    stack(ClayiumItems.CLAY_SLICER.get(), 1),
                    stack(ClayiumItems.CLAY_SPATULA.get(), 1)
            );
            case SPATULA -> List.of(stack(ClayiumItems.CLAY_SPATULA.get(), 1));
        };
    }

    private static ItemStack stack(ItemLike item, int count) {
        return new ItemStack(item.asItem(), count);
    }

    private static String itemKey(ItemLike item) {
        var key = BuiltInRegistries.ITEM.getKey(item.asItem());
        return key.getNamespace() + "/" + key.getPath();
    }
}
