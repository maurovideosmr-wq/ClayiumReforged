package dev.clayium.clayium.menu;

import dev.clayium.clayium.registry.ClayiumItems;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static ClayWorkTableOperations.Operation findByInputAndButton(Object input, int buttonId) {
        return ClayWorkTableOperations.operations().stream()
                .filter(operation -> operation.input() == input && operation.buttonId() == buttonId)
                .findFirst()
                .orElseThrow();
    }
}
