package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Clayium.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.clayium.main"))
            .icon(() -> ClayiumItems.DENSE_CLAY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ClayiumItems.CREATIVE_BLOCKS.forEach(item -> output.accept(item.get()));
                ClayiumItems.CREATIVE_TOOLS.forEach(item -> output.accept(item.get()));
                ClayiumItems.CREATIVE_PARTS.forEach(item -> output.accept(item.get()));
            })
            .build());

    private ClayiumCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
