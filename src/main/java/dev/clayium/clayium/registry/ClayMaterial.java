package dev.clayium.clayium.registry;

public enum ClayMaterial {
    CLAY("clay", "Clay", 1),
    DENSE_CLAY("dense_clay", "Dense Clay", 2),
    INDUSTRIAL_CLAY("industrial_clay", "Industrial Clay", 3),
    ADVANCED_INDUSTRIAL_CLAY("advanced_industrial_clay", "Advanced Industrial Clay", 4),
    ENERGIZED_CLAY("energized_clay", "Energized Clay", 3);

    private final String idPrefix;
    private final String translation;
    private final int tier;

    ClayMaterial(String idPrefix, String translation, int tier) {
        this.idPrefix = idPrefix;
        this.translation = translation;
        this.tier = tier;
    }

    public String idPrefix() {
        return this.idPrefix;
    }

    public String translation() {
        return this.translation;
    }

    public int tier() {
        return this.tier;
    }

    public String itemId(ClayPartType partType) {
        return this.idPrefix + "_" + partType.idSuffix();
    }
}
