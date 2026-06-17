package dev.clayium.clayium.registry;

public enum ClayMaterial {
    CLAY("clay", "Clay"),
    DENSE_CLAY("dense_clay", "Dense Clay"),
    INDUSTRIAL_CLAY("industrial_clay", "Industrial Clay"),
    ADVANCED_INDUSTRIAL_CLAY("advanced_industrial_clay", "Advanced Industrial Clay"),
    ENERGIZED_CLAY("energized_clay", "Energized Clay");

    private final String idPrefix;
    private final String translation;

    ClayMaterial(String idPrefix, String translation) {
        this.idPrefix = idPrefix;
        this.translation = translation;
    }

    public String idPrefix() {
        return this.idPrefix;
    }

    public String translation() {
        return this.translation;
    }

    public String itemId(ClayPartType partType) {
        return this.idPrefix + "_" + partType.idSuffix();
    }
}
