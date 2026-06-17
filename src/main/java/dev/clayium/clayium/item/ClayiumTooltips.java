package dev.clayium.clayium.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class ClayiumTooltips {
    private ClayiumTooltips() {
    }

    public static void appendTier(Consumer<Component> builder, int tier) {
        builder.accept(Component.translatable("tooltip.clayium.tier", tier).withStyle(ChatFormatting.WHITE));
    }
}
