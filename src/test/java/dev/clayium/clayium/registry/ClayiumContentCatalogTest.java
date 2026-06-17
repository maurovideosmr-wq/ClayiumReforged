package dev.clayium.clayium.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClayiumContentCatalogTest {
    @Test
    void materialFormsDeriveTiersFromTheirMaterial() {
        assertEquals(1, ClayMaterial.CLAY.tier());
        assertEquals(2, ClayMaterial.DENSE_CLAY.tier());
        assertEquals(3, ClayMaterial.INDUSTRIAL_CLAY.tier());
        assertEquals(4, ClayMaterial.ADVANCED_INDUSTRIAL_CLAY.tier());
        assertEquals(3, ClayMaterial.ENERGIZED_CLAY.tier());

        assertEquals(1, ClayiumContentCatalog.materialForm(ClayMaterial.CLAY, ClayPartType.PLATE).orElseThrow().tier());
        assertEquals(2, ClayiumContentCatalog.materialForm(ClayMaterial.DENSE_CLAY, ClayPartType.GEAR).orElseThrow().tier());
        assertEquals(3, ClayiumContentCatalog.materialForm(ClayMaterial.INDUSTRIAL_CLAY, ClayPartType.DUST).orElseThrow().tier());
        assertEquals(4, ClayiumContentCatalog.materialForm(ClayMaterial.ADVANCED_INDUSTRIAL_CLAY, ClayPartType.LARGE_PLATE).orElseThrow().tier());
    }

    @Test
    void catalogedBlocksCarryLegacyTierValues() {
        assertEquals(0, ClayiumContentCatalog.block("dense_clay").tier());
        assertEquals(1, ClayiumContentCatalog.block("compressed_clay").tier());
        assertEquals(2, ClayiumContentCatalog.block("industrial_clay").tier());
        assertEquals(3, ClayiumContentCatalog.block("advanced_industrial_clay").tier());
        assertEquals(0, ClayiumContentCatalog.block("clay_work_table").tier());
        assertEquals(1, ClayiumContentCatalog.block("raw_clay_machine_hull").tier());

        assertEquals(1, MachineHullTier.CLAY.tier());
        assertEquals(2, MachineHullTier.DENSE_CLAY.tier());
        assertEquals(3, MachineHullTier.SIMPLE.tier());
        assertEquals(4, MachineHullTier.BASIC.tier());
        assertEquals(MachineHullTier.BASIC.tier(), ClayiumContentCatalog.block("basic_machine_hull").tier());
    }

    @Test
    void simpleItemsCarryCatalogTiersForTooltips() {
        assertEquals(1, ClayiumContentCatalog.simpleItem("clay_shovel").tier());
        assertEquals(1, ClayiumContentCatalog.simpleItem("clay_rolling_pin").tier());
        assertEquals(2, ClayiumContentCatalog.simpleItem("clay_circuit").tier());
        assertEquals(2, ClayiumContentCatalog.simpleItem("clay_circuit_board").tier());
        assertEquals(3, ClayiumContentCatalog.simpleItem("simple_circuit").tier());
        assertEquals(3, ClayiumContentCatalog.simpleItem("cee").tier());
        assertEquals(4, ClayiumContentCatalog.simpleItem("basic_circuit").tier());
        assertEquals(3, ClayiumContentCatalog.simpleItem("advanced_industrial_clay_shard").tier());
    }

    @Test
    void allCatalogRowsHaveDisplayableTiers() {
        assertTrue(ClayiumContentCatalog.blocks().stream().allMatch(spec -> spec.tier() >= 0));
        assertTrue(ClayiumContentCatalog.registeredMaterialItems().stream().allMatch(spec -> spec.tier() >= 0));
        assertTrue(ClayiumContentCatalog.simpleItems().stream().allMatch(spec -> spec.tier() >= 0));
    }
}
