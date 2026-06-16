package dev.clayium.clayium.menu;

import dev.clayium.clayium.registry.ClayiumItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class ClayWorkTableOperations {
    public static final int INPUT_SLOT = 0;
    public static final int TOOL_SLOT = 1;
    public static final int FIRST_OUTPUT_SLOT = 2;
    public static final int OUTPUT_SLOT_COUNT = 3;
    public static final int SLOT_COUNT = 5;

    private static final List<Operation> OPERATIONS = List.of(
            op(1, () -> Items.CLAY_BALL, 1, ToolRequirement.NONE, 4, out(ClayiumItems.CLAY_STICK, 1)),
            op(2, ClayiumItems.CLAY_LARGE_BALL, 1, ToolRequirement.NONE, 30, out(ClayiumItems.CLAY_DISC, 1)),
            op(3, ClayiumItems.CLAY_LARGE_BALL, 1, ToolRequirement.ROLLING_PIN, 4, out(ClayiumItems.CLAY_DISC, 1), out(() -> Items.CLAY_BALL, 2)),
            op(1, ClayiumItems.CLAY_LARGE_BALL, 1, ToolRequirement.NONE, 4, out(ClayiumItems.CLAY_CYLINDER, 1)),
            op(2, ClayiumItems.CLAY_PLATE, 1, ToolRequirement.NONE, 10, out(ClayiumItems.CLAY_BLADE, 1)),
            op(3, ClayiumItems.CLAY_PLATE, 1, ToolRequirement.ROLLING_PIN, 1, out(ClayiumItems.CLAY_BLADE, 1), out(() -> Items.CLAY_BALL, 2)),
            op(6, ClayiumItems.CLAY_PLATE, 1, ToolRequirement.SLICER_OR_SPATULA, 3, out(ClayiumItems.CLAY_STICK, 4)),
            op(4, ClayiumItems.CLAY_DISC, 1, ToolRequirement.SLICER_OR_SPATULA, 4, out(ClayiumItems.CLAY_PLATE, 1), out(() -> Items.CLAY_BALL, 2)),
            op(5, ClayiumItems.CLAY_DISC, 1, ToolRequirement.SPATULA, 2, out(ClayiumItems.CLAY_RING, 1), out(ClayiumItems.CLAY_SMALL_DISC, 1)),
            op(5, ClayiumItems.CLAY_SMALL_DISC, 1, ToolRequirement.SPATULA, 1, out(ClayiumItems.CLAY_SMALL_RING, 1), out(ClayiumItems.CLAY_SHORT_STICK, 1)),
            op(1, ClayiumItems.CLAY_CYLINDER, 1, ToolRequirement.NONE, 3, out(ClayiumItems.CLAY_NEEDLE, 1)),
            op(6, ClayiumItems.CLAY_CYLINDER, 1, ToolRequirement.SLICER_OR_SPATULA, 7, out(ClayiumItems.CLAY_SMALL_DISC, 8)),
            op(2, ClayiumItems.CLAY_DISC, 1, ToolRequirement.NONE, 15, out(ClayiumItems.RAW_CLAY_SLICER, 1)),
            op(3, ClayiumItems.CLAY_DISC, 1, ToolRequirement.ROLLING_PIN, 2, out(ClayiumItems.RAW_CLAY_SLICER, 1)),
            op(3, ClayiumItems.CLAY_PLATE, 6, ToolRequirement.ROLLING_PIN, 10, out(ClayiumItems.CLAY_LARGE_PLATE, 1)),
            op(1, ClayiumItems.CLAY_PLATE, 3, ToolRequirement.NONE, 40, out(ClayiumItems.CLAY_LARGE_BALL, 1))
    );

    private ClayWorkTableOperations() {
    }

    public static Optional<Operation> find(int buttonId, ItemStack input, ItemStack tool) {
        return OPERATIONS.stream()
                .filter(operation -> operation.matches(buttonId, input, tool))
                .findFirst();
    }

    public static List<Operation> operations() {
        return OPERATIONS;
    }

    public static boolean isTool(ItemStack stack) {
        return stack.is(ClayiumItems.CLAY_ROLLING_PIN.get())
                || stack.is(ClayiumItems.CLAY_SLICER.get())
                || stack.is(ClayiumItems.CLAY_SPATULA.get());
    }

    public static boolean isKnownInput(ItemStack stack) {
        return OPERATIONS.stream().anyMatch(operation -> stack.is(operation.input.get().asItem()));
    }

    private static Operation op(int buttonId, Supplier<? extends ItemLike> input, int inputCount, ToolRequirement toolRequirement, int workTicks, Output... outputs) {
        return new Operation(buttonId, input, inputCount, toolRequirement, workTicks, List.of(outputs));
    }

    private static Output out(Supplier<? extends ItemLike> item, int count) {
        return new Output(item, count);
    }

    public record Operation(
            int buttonId,
            Supplier<? extends ItemLike> input,
            int inputCount,
            ToolRequirement toolRequirement,
            int workTicks,
            List<Output> outputs
    ) {
        private boolean matches(int attemptedButtonId, ItemStack inputStack, ItemStack toolStack) {
            return this.buttonId == attemptedButtonId
                    && inputStack.is(this.input.get().asItem())
                    && inputStack.getCount() >= this.inputCount
                    && this.toolRequirement.matches(toolStack);
        }

        List<ItemStack> createOutputs() {
            List<ItemStack> stacks = new ArrayList<>();
            for (Output output : this.outputs) {
                stacks.add(output.createStack());
            }
            return stacks;
        }
    }

    public record Output(Supplier<? extends ItemLike> item, int count) {
        ItemStack createStack() {
            return new ItemStack(this.item.get().asItem(), this.count);
        }
    }

    public enum ToolRequirement {
        NONE,
        ROLLING_PIN,
        SLICER_OR_SPATULA,
        SPATULA;

        private boolean matches(ItemStack stack) {
            return switch (this) {
                case NONE -> true;
                case ROLLING_PIN -> stack.is(ClayiumItems.CLAY_ROLLING_PIN.get());
                case SLICER_OR_SPATULA -> stack.is(ClayiumItems.CLAY_SLICER.get()) || stack.is(ClayiumItems.CLAY_SPATULA.get());
                case SPATULA -> stack.is(ClayiumItems.CLAY_SPATULA.get());
            };
        }
    }
}
