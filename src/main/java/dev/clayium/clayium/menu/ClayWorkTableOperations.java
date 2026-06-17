package dev.clayium.clayium.menu;

import dev.clayium.clayium.recipe.ClayWorkTableAction;
import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import dev.clayium.clayium.recipe.ClayWorkTableToolRequirement;
import dev.clayium.clayium.registry.ClayiumItems;
import dev.clayium.clayium.registry.ClayiumTags;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public final class ClayWorkTableOperations {
    public static final int INPUT_SLOT = 0;
    public static final int TOOL_SLOT = 1;
    public static final int FIRST_OUTPUT_SLOT = 2;
    public static final int OUTPUT_SLOT_COUNT = 2;
    public static final int VISIBLE_SLOT_COUNT = 4;
    public static final int INTERNAL_INPUT_SLOT = 4;
    public static final int SLOT_COUNT = 5;

    private static final List<Operation> OPERATIONS = List.of(
            op(ClayWorkTableAction.HAND_KNEAD, () -> Items.CLAY_BALL, 1, ClayWorkTableToolRequirement.NONE, 4, out(ClayiumItems.CLAY_STICK, 1)),
            op(ClayWorkTableAction.HAND_PRESS, ClayiumItems.CLAY_LARGE_BALL, 1, ClayWorkTableToolRequirement.NONE, 30, out(ClayiumItems.CLAY_DISC, 1)),
            op(ClayWorkTableAction.ROLLING_PIN, ClayiumItems.CLAY_LARGE_BALL, 1, ClayWorkTableToolRequirement.ROLLING_PIN, 4, out(ClayiumItems.CLAY_DISC, 1), out(() -> Items.CLAY_BALL, 2)),
            op(ClayWorkTableAction.HAND_KNEAD, ClayiumItems.CLAY_LARGE_BALL, 1, ClayWorkTableToolRequirement.NONE, 4, out(ClayiumItems.CLAY_CYLINDER, 1)),
            op(ClayWorkTableAction.HAND_PRESS, ClayiumItems.CLAY_PLATE, 1, ClayWorkTableToolRequirement.NONE, 10, out(ClayiumItems.CLAY_BLADE, 1)),
            op(ClayWorkTableAction.ROLLING_PIN, ClayiumItems.CLAY_PLATE, 1, ClayWorkTableToolRequirement.ROLLING_PIN, 1, out(ClayiumItems.CLAY_BLADE, 1), out(() -> Items.CLAY_BALL, 2)),
            op(ClayWorkTableAction.CUT_STRIPS, ClayiumItems.CLAY_PLATE, 1, ClayWorkTableToolRequirement.SLICER_OR_SPATULA, 3, out(ClayiumItems.CLAY_STICK, 4)),
            op(ClayWorkTableAction.CUT_PLATE, ClayiumItems.CLAY_DISC, 1, ClayWorkTableToolRequirement.SLICER_OR_SPATULA, 4, out(ClayiumItems.CLAY_PLATE, 1), out(() -> Items.CLAY_BALL, 2)),
            op(ClayWorkTableAction.CUT_ROUND, ClayiumItems.CLAY_DISC, 1, ClayWorkTableToolRequirement.SPATULA, 2, out(ClayiumItems.CLAY_RING, 1), out(ClayiumItems.CLAY_SMALL_DISC, 1)),
            op(ClayWorkTableAction.CUT_ROUND, ClayiumItems.CLAY_SMALL_DISC, 1, ClayWorkTableToolRequirement.SPATULA, 1, out(ClayiumItems.CLAY_SMALL_RING, 1), out(ClayiumItems.CLAY_SHORT_STICK, 1)),
            op(ClayWorkTableAction.HAND_KNEAD, ClayiumItems.CLAY_CYLINDER, 1, ClayWorkTableToolRequirement.NONE, 3, out(ClayiumItems.CLAY_NEEDLE, 1)),
            op(ClayWorkTableAction.CUT_STRIPS, ClayiumItems.CLAY_CYLINDER, 1, ClayWorkTableToolRequirement.SLICER_OR_SPATULA, 7, out(ClayiumItems.CLAY_SMALL_DISC, 8)),
            op(ClayWorkTableAction.HAND_PRESS, ClayiumItems.CLAY_DISC, 1, ClayWorkTableToolRequirement.NONE, 15, out(ClayiumItems.RAW_CLAY_SLICER, 1)),
            op(ClayWorkTableAction.ROLLING_PIN, ClayiumItems.CLAY_DISC, 1, ClayWorkTableToolRequirement.ROLLING_PIN, 2, out(ClayiumItems.RAW_CLAY_SLICER, 1)),
            op(ClayWorkTableAction.ROLLING_PIN, ClayiumItems.CLAY_PLATE, 6, ClayWorkTableToolRequirement.ROLLING_PIN, 10, out(ClayiumItems.CLAY_LARGE_PLATE, 1)),
            op(ClayWorkTableAction.HAND_KNEAD, ClayiumItems.CLAY_PLATE, 3, ClayWorkTableToolRequirement.NONE, 40, out(ClayiumItems.CLAY_LARGE_BALL, 1))
    );

    private ClayWorkTableOperations() {
    }

    public static List<Operation> operations() {
        return OPERATIONS;
    }

    public static boolean isTool(ItemStack stack) {
        return stack.is(ClayiumTags.Items.CLAY_WORK_TABLE_TOOLS);
    }

    public static boolean canUseToolForButton(int buttonId, ItemStack tool) {
        return switch (buttonId) {
            case 1, 2 -> true;
            case 3 -> ClayWorkTableToolRequirement.ROLLING_PIN.matches(tool);
            case 4, 6 -> ClayWorkTableToolRequirement.SLICER_OR_SPATULA.matches(tool);
            case 5 -> ClayWorkTableToolRequirement.SPATULA.matches(tool);
            default -> false;
        };
    }

    public static boolean isKnownInput(ItemStack stack) {
        return OPERATIONS.stream().anyMatch(operation -> stack.is(operation.input.get().asItem()));
    }

    private static Operation op(ClayWorkTableAction action, Supplier<? extends ItemLike> input, int inputCount, ClayWorkTableToolRequirement toolRequirement, int workTicks, Output... outputs) {
        return new Operation(action, input, inputCount, toolRequirement, workTicks, List.of(outputs));
    }

    private static Output out(Supplier<? extends ItemLike> item, int count) {
        return new Output(item, count);
    }

    public record Operation(
            ClayWorkTableAction action,
            Supplier<? extends ItemLike> input,
            int inputCount,
            ClayWorkTableToolRequirement toolRequirement,
            int workTicks,
            List<Output> outputs
    ) {
        public int buttonId() {
            return this.action.buttonId();
        }

        public List<ItemStackTemplate> createOutputTemplates() {
            return this.outputs.stream()
                    .map(Output::createTemplate)
                    .toList();
        }

        public ClayWorkTableRecipe toRecipe() {
            return new ClayWorkTableRecipe(
                    new Recipe.CommonInfo(false),
                    this.action,
                    Ingredient.of(this.input.get()),
                    this.inputCount,
                    this.toolRequirement,
                    this.workTicks,
                    this.createOutputTemplates()
            );
        }

        public String defaultRecipePath() {
            return "clay_work_table/" + this.action.getSerializedName()
                    + "/" + itemPath(this.input.get().asItem())
                    + "_x" + this.inputCount
                    + "_to_" + itemPath(this.outputs.getFirst().item().get().asItem());
        }
    }

    public record Output(Supplier<? extends ItemLike> item, int count) {
        ItemStackTemplate createTemplate() {
            return new ItemStackTemplate(this.item.get().asItem(), this.count);
        }
    }

    private static String itemPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }
}
