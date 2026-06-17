package dev.clayium.clayium.registry;

public enum MachineHullTier {
    CLAY("clay_machine_hull", "Clay Machine Hull", "clay_machine_hull", ClayiumContentCatalog.ContentStatus.PORTED),
    DENSE_CLAY("dense_clay_machine_hull", "Dense Clay Machine Hull", "dense_clay_machine_hull", ClayiumContentCatalog.ContentStatus.REGISTERED_ONLY),
    SIMPLE("simple_machine_hull", "Simple Machine Hull", "simple_machine_hull", ClayiumContentCatalog.ContentStatus.REGISTERED_ONLY),
    BASIC("basic_machine_hull", "Basic Machine Hull", "basic_machine_hull", ClayiumContentCatalog.ContentStatus.REGISTERED_ONLY);

    private final String id;
    private final String translation;
    private final String texture;
    private final ClayiumContentCatalog.ContentStatus status;

    MachineHullTier(String id, String translation, String texture, ClayiumContentCatalog.ContentStatus status) {
        this.id = id;
        this.translation = translation;
        this.texture = texture;
        this.status = status;
    }

    public String id() {
        return this.id;
    }

    public String translation() {
        return this.translation;
    }

    public String texture() {
        return this.texture;
    }

    public ClayiumContentCatalog.ContentStatus status() {
        return this.status;
    }
}
