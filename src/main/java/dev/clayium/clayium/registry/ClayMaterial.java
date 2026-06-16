package dev.clayium.clayium.registry;

public enum ClayMaterial {
    CLAY("clay"),
    DENSE_CLAY("dense_clay");

    private final String idPrefix;

    ClayMaterial(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public String idPrefix() {
        return this.idPrefix;
    }

    public String itemId(ClayPartType partType) {
        return this.idPrefix + "_" + partType.idSuffix();
    }
}
