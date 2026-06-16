package dev.clayium.clayium.recipe;

import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum ClayWorkTableAction implements StringRepresentable {
    HAND_KNEAD(1, "hand_knead"),
    HAND_PRESS(2, "hand_press"),
    ROLLING_PIN(3, "rolling_pin"),
    CUT_PLATE(4, "cut_plate"),
    CUT_ROUND(5, "cut_round"),
    CUT_STRIPS(6, "cut_strips");

    public static final StringRepresentable.EnumCodec<ClayWorkTableAction> CODEC = StringRepresentable.fromEnum(ClayWorkTableAction::values);
    public static final StreamCodec<ByteBuf, ClayWorkTableAction> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(
            ClayWorkTableAction::byButtonId,
            ClayWorkTableAction::buttonId
    );

    private final int buttonId;
    private final String serializedName;

    ClayWorkTableAction(int buttonId, String serializedName) {
        this.buttonId = buttonId;
        this.serializedName = serializedName;
    }

    public int buttonId() {
        return this.buttonId;
    }

    @Override
    public String getSerializedName() {
        return this.serializedName;
    }

    public static ClayWorkTableAction byButtonId(int buttonId) {
        return Arrays.stream(values())
                .filter(action -> action.buttonId == buttonId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Clay Work Table button id: " + buttonId));
    }
}
