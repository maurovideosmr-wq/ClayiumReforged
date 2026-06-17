package dev.clayium.clayium.registry;

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
    LARGE_BALL("large_ball"),
    LARGE_PLATE("large_plate"),
    GRINDING_HEAD("grinding_head"),
    BEARING("bearing"),
    SPINDLE("spindle"),
    CUTTING_HEAD("cutting_head"),
    WATER_WHEEL("water_wheel"),
    BLOCK("block"),
    BALL("ball"),
    DUST("dust"),
    INGOT("ingot"),
    GEM("gem"),
    CRYSTAL("crystal");

    private final String idSuffix;

    ClayPartType(String idSuffix) {
        this.idSuffix = idSuffix;
    }

    public String idSuffix() {
        return this.idSuffix;
    }

    public Set<ClayMaterial> materials() {
        return ClayiumContentCatalog.registeredMaterialsFor(this);
    }

    public boolean isAvailableFor(ClayMaterial material) {
        return ClayiumContentCatalog.materialForm(material, this)
                .filter(ClayiumContentCatalog.MaterialFormSpec::registersItem)
                .isPresent();
    }

    public String translation(ClayMaterial material) {
        return switch (this) {
            case SHORT_STICK -> "Short " + material.translation() + " Stick";
            case SMALL_RING -> "Small " + material.translation() + " Ring";
            case SMALL_DISC -> "Small " + material.translation() + " Disc";
            case LARGE_BALL -> "Large " + material.translation() + " Ball";
            case LARGE_PLATE -> "Large " + material.translation() + " Plate";
            case GRINDING_HEAD -> material.translation() + " Grinding Head";
            case CUTTING_HEAD -> material.translation() + " Cutting Head";
            case WATER_WHEEL -> material.translation() + " Water Wheel";
            default -> material.translation() + " " + titleCase(this.idSuffix);
        };
    }

    private static String titleCase(String value) {
        StringBuilder builder = new StringBuilder();
        boolean capitalizeNext = true;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '_') {
                builder.append(' ');
                capitalizeNext = true;
                continue;
            }
            builder.append(capitalizeNext ? Character.toUpperCase(c) : c);
            capitalizeNext = false;
        }
        return builder.toString();
    }
}
