package dev.clayium.clayium.registry;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum ClayPartType {
    PLATE("plate"),
    STICK("stick"),
    SHORT_STICK("short_stick"),
    RING("ring"),
    SMALL_RING("small_ring"),
    GEAR("gear"),
    BLADE("blade"),
    NEEDLE("needle"),
    DISC("disc"),
    SMALL_DISC("small_disc"),
    CYLINDER("cylinder"),
    PIPE("pipe"),
    LARGE_BALL("large_ball", ClayMaterial.CLAY),
    LARGE_PLATE("large_plate"),
    GRINDING_HEAD("grinding_head"),
    BEARING("bearing"),
    SPINDLE("spindle"),
    CUTTING_HEAD("cutting_head"),
    WATER_WHEEL("water_wheel");

    private final String idSuffix;
    private final Set<ClayMaterial> materials;

    ClayPartType(String idSuffix) {
        this.idSuffix = idSuffix;
        this.materials = Collections.unmodifiableSet(EnumSet.allOf(ClayMaterial.class));
    }

    ClayPartType(String idSuffix, ClayMaterial firstMaterial, ClayMaterial... additionalMaterials) {
        this.idSuffix = idSuffix;
        this.materials = Collections.unmodifiableSet(EnumSet.of(firstMaterial, additionalMaterials));
    }

    public String idSuffix() {
        return this.idSuffix;
    }

    public Set<ClayMaterial> materials() {
        return this.materials;
    }

    public boolean isAvailableFor(ClayMaterial material) {
        return this.materials.contains(material);
    }
}
