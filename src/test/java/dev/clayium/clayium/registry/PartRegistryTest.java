package dev.clayium.clayium.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class PartRegistryTest {
    @Test
    void materialPartsAreRegisteredThroughPartRegistry() {
        long expectedPartCount = 0;
        for (ClayPartType partType : ClayPartType.values()) {
            expectedPartCount += partType.materials().size();
        }

        assertEquals(expectedPartCount, ClayiumItems.PARTS.items().size());
        assertEquals(expectedPartCount, ClayiumItems.CREATIVE_PARTS.size());
    }

    @Test
    void commonPartAliasesResolveToTheCentralRegistry() {
        assertSame(ClayiumItems.CLAY_PLATE, ClayiumItems.PARTS.get(ClayMaterial.CLAY, ClayPartType.PLATE));
        assertSame(ClayiumItems.CLAY_LARGE_BALL, ClayiumItems.PARTS.get(ClayMaterial.CLAY, ClayPartType.LARGE_BALL));
        assertSame(ClayiumItems.DENSE_CLAY_PLATE, ClayiumItems.PARTS.get(ClayMaterial.DENSE_CLAY, ClayPartType.PLATE));
        assertFalse(ClayiumItems.PARTS.contains(ClayMaterial.DENSE_CLAY, ClayPartType.LARGE_BALL));
    }
}
