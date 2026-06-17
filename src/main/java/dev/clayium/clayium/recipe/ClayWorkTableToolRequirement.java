package dev.clayium.clayium.recipe;

import dev.clayium.clayium.registry.ClayiumItems;
import dev.clayium.clayium.registry.ClayiumTags;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public enum ClayWorkTableToolRequirement implements StringRepresentable {
    NONE("none"),
    ROLLING_PIN("rolling_pin"),
    SLICER_OR_SPATULA("slicer_or_spatula"),
    SPATULA("spatula");

    public static final StringRepresentable.EnumCodec<ClayWorkTableToolRequirement> CODEC = StringRepresentable.fromEnum(ClayWorkTableToolRequirement::values);
    public static final StreamCodec<ByteBuf, ClayWorkTableToolRequirement> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(
            ClayWorkTableToolRequirement::byOrdinal,
            ClayWorkTableToolRequirement::ordinal
    );

    private final String serializedName;

    ClayWorkTableToolRequirement(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return this.serializedName;
    }

    public boolean matches(ItemStack stack) {
        return switch (this) {
            case NONE -> true;
            case ROLLING_PIN -> stack.is(ClayiumTags.Items.CLAY_ROLLING_PINS);
            case SLICER_OR_SPATULA -> stack.is(ClayiumTags.Items.CLAY_SLICERS) || stack.is(ClayiumTags.Items.CLAY_SPATULAS);
            case SPATULA -> stack.is(ClayiumTags.Items.CLAY_SPATULAS);
        };
    }

    public List<ItemLike> displayTools() {
        return switch (this) {
            case NONE -> List.of();
            case ROLLING_PIN -> List.of(ClayiumItems.CLAY_ROLLING_PIN.get());
            case SLICER_OR_SPATULA -> List.of(ClayiumItems.CLAY_SLICER.get(), ClayiumItems.CLAY_SPATULA.get());
            case SPATULA -> List.of(ClayiumItems.CLAY_SPATULA.get());
        };
    }

    private static ClayWorkTableToolRequirement byOrdinal(int ordinal) {
        ClayWorkTableToolRequirement[] values = values();
        if (ordinal < 0 || ordinal >= values.length) {
            throw new IllegalArgumentException("Unknown Clay Work Table tool requirement ordinal: " + ordinal);
        }
        return values[ordinal];
    }
}
