package dev.clayium.clayium.registry;

public enum MachineHullTier {
    RAW_CLAY("raw_clay_machine_hull"),
    CLAY("clay_machine_hull");

    private final String id;

    MachineHullTier(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }
}
