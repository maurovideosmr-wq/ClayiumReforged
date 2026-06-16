package dev.clayium.clayium.menu;

import dev.clayium.clayium.registry.ClayiumItems;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

class ClayWorkTableOperationsTest {
    @Test
    void clayBallRecipeRollsIntoClayStickWithoutTool() {
        ClayWorkTableOperations.Operation operation = ClayWorkTableOperations.operations().getFirst();

        assertEquals(1, operation.buttonId());
        assertEquals(1, operation.inputCount());
        assertSame(ClayWorkTableOperations.ToolRequirement.NONE, operation.toolRequirement());
        assertEquals(4, operation.workTicks());
        assertEquals(1, operation.outputs().size());
        assertSame(ClayiumItems.CLAY_STICK, operation.outputs().getFirst().item());
        assertEquals(1, operation.outputs().getFirst().count());
    }

    @Test
    void rollingPinIsRequiredForFastRawSlicerRecipeData() {
        ClayWorkTableOperations.Operation operation = findByInputAndButton(ClayiumItems.CLAY_DISC, 3);

        assertSame(ClayWorkTableOperations.ToolRequirement.ROLLING_PIN, operation.toolRequirement());
        assertSame(ClayiumItems.RAW_CLAY_SLICER, operation.outputs().getFirst().item());
        assertEquals(2, operation.workTicks());
    }

    @Test
    void spatulaIsRequiredForRingPunchingRecipeData() {
        ClayWorkTableOperations.Operation operation = findByInputAndButton(ClayiumItems.CLAY_SMALL_DISC, 5);

        assertSame(ClayWorkTableOperations.ToolRequirement.SPATULA, operation.toolRequirement());
        assertSame(ClayiumItems.CLAY_SMALL_RING, operation.outputs().getFirst().item());
        assertSame(ClayiumItems.CLAY_SHORT_STICK, operation.outputs().get(1).item());
    }

    @Test
    void workTableRecipesFitTheVisibleOutputSlots() {
        assertEquals(2, ClayWorkTableOperations.OUTPUT_SLOT_COUNT);
        assertEquals(4, ClayWorkTableOperations.VISIBLE_SLOT_COUNT);
        assertEquals(5, ClayWorkTableOperations.SLOT_COUNT);
        assertEquals(4, ClayWorkTableOperations.INTERNAL_INPUT_SLOT);
        ClayWorkTableOperations.operations().forEach(operation ->
                assertTrue(operation.outputs().size() <= ClayWorkTableOperations.OUTPUT_SLOT_COUNT));
    }

    @Test
    void findUsesLargestMatchingInputCountForLegacyMetadataRecipes() {
        List<ClayWorkTableOperations.Operation> matchingOperations = ClayWorkTableOperations.operations().stream()
                .filter(operation -> operation.input() == ClayiumItems.CLAY_PLATE && operation.buttonId() == 3)
                .toList();

        ClayWorkTableOperations.Operation operation = ClayWorkTableOperations.findBestMatch(matchingOperations)
                .orElseThrow();

        assertEquals(6, operation.inputCount());
        assertSame(ClayiumItems.CLAY_LARGE_PLATE, operation.outputs().getFirst().item());
    }

    @Test
    void workTableRecipesMatchLegacyClayWorkTableRecipes() {
        Set<RecipeSignature> expected = Set.of(
                recipe(1, "clay_ball", 1, ClayWorkTableOperations.ToolRequirement.NONE, 4, "clay_stick:1"),
                recipe(2, "clay_large_ball", 1, ClayWorkTableOperations.ToolRequirement.NONE, 30, "clay_disc:1"),
                recipe(3, "clay_large_ball", 1, ClayWorkTableOperations.ToolRequirement.ROLLING_PIN, 4, "clay_disc:1", "clay_ball:2"),
                recipe(1, "clay_large_ball", 1, ClayWorkTableOperations.ToolRequirement.NONE, 4, "clay_cylinder:1"),
                recipe(2, "clay_plate", 1, ClayWorkTableOperations.ToolRequirement.NONE, 10, "clay_blade:1"),
                recipe(3, "clay_plate", 1, ClayWorkTableOperations.ToolRequirement.ROLLING_PIN, 1, "clay_blade:1", "clay_ball:2"),
                recipe(6, "clay_plate", 1, ClayWorkTableOperations.ToolRequirement.SLICER_OR_SPATULA, 3, "clay_stick:4"),
                recipe(4, "clay_disc", 1, ClayWorkTableOperations.ToolRequirement.SLICER_OR_SPATULA, 4, "clay_plate:1", "clay_ball:2"),
                recipe(5, "clay_disc", 1, ClayWorkTableOperations.ToolRequirement.SPATULA, 2, "clay_ring:1", "clay_small_disc:1"),
                recipe(5, "clay_small_disc", 1, ClayWorkTableOperations.ToolRequirement.SPATULA, 1, "clay_small_ring:1", "clay_short_stick:1"),
                recipe(1, "clay_cylinder", 1, ClayWorkTableOperations.ToolRequirement.NONE, 3, "clay_needle:1"),
                recipe(6, "clay_cylinder", 1, ClayWorkTableOperations.ToolRequirement.SLICER_OR_SPATULA, 7, "clay_small_disc:8"),
                recipe(2, "clay_disc", 1, ClayWorkTableOperations.ToolRequirement.NONE, 15, "raw_clay_slicer:1"),
                recipe(3, "clay_disc", 1, ClayWorkTableOperations.ToolRequirement.ROLLING_PIN, 2, "raw_clay_slicer:1"),
                recipe(3, "clay_plate", 6, ClayWorkTableOperations.ToolRequirement.ROLLING_PIN, 10, "clay_large_plate:1"),
                recipe(1, "clay_plate", 3, ClayWorkTableOperations.ToolRequirement.NONE, 40, "clay_large_ball:1")
        );
        Set<RecipeSignature> actual = ClayWorkTableOperations.operations().stream()
                .map(ClayWorkTableOperationsTest::signature)
                .collect(Collectors.toSet());

        assertEquals(expected, actual);
    }

    private static ClayWorkTableOperations.Operation findByInputAndButton(Object input, int buttonId) {
        return ClayWorkTableOperations.operations().stream()
                .filter(operation -> operation.input() == input && operation.buttonId() == buttonId)
                .findFirst()
                .orElseThrow();
    }

    private static RecipeSignature signature(ClayWorkTableOperations.Operation operation) {
        return recipe(
                operation.buttonId(),
                itemName(operation.input()),
                operation.inputCount(),
                operation.toolRequirement(),
                operation.workTicks(),
                operation.outputs().stream()
                        .map(output -> itemName(output.item()) + ":" + output.count())
                        .toArray(String[]::new)
        );
    }

    private static RecipeSignature recipe(int buttonId, String input, int inputCount, ClayWorkTableOperations.ToolRequirement toolRequirement, int workTicks, String... outputs) {
        return new RecipeSignature(buttonId, input, inputCount, toolRequirement, workTicks, List.of(outputs));
    }

    private static String itemName(Supplier<? extends ItemLike> item) {
        if (item == ClayiumItems.CLAY_STICK) return "clay_stick";
        if (item == ClayiumItems.CLAY_SHORT_STICK) return "clay_short_stick";
        if (item == ClayiumItems.CLAY_RING) return "clay_ring";
        if (item == ClayiumItems.CLAY_SMALL_RING) return "clay_small_ring";
        if (item == ClayiumItems.CLAY_BLADE) return "clay_blade";
        if (item == ClayiumItems.CLAY_NEEDLE) return "clay_needle";
        if (item == ClayiumItems.CLAY_DISC) return "clay_disc";
        if (item == ClayiumItems.CLAY_SMALL_DISC) return "clay_small_disc";
        if (item == ClayiumItems.CLAY_CYLINDER) return "clay_cylinder";
        if (item == ClayiumItems.CLAY_LARGE_BALL) return "clay_large_ball";
        if (item == ClayiumItems.CLAY_LARGE_PLATE) return "clay_large_plate";
        if (item == ClayiumItems.CLAY_PLATE) return "clay_plate";
        if (item == ClayiumItems.RAW_CLAY_SLICER) return "raw_clay_slicer";
        if (item.get().asItem() == Items.CLAY_BALL) return "clay_ball";
        throw new AssertionError("Unexpected Clay Work Table item supplier: " + item);
    }

    private record RecipeSignature(
            int buttonId,
            String input,
            int inputCount,
            ClayWorkTableOperations.ToolRequirement toolRequirement,
            int workTicks,
            List<String> outputs
    ) {
    }
}
