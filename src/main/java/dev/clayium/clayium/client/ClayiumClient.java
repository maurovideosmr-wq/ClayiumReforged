package dev.clayium.clayium.client;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.client.screen.ClayWorkTableScreen;
import dev.clayium.clayium.registry.ClayiumMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Clayium.MOD_ID, dist = Dist.CLIENT)
public final class ClayiumClient {
    public ClayiumClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerScreens);
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ClayiumMenus.CLAY_WORK_TABLE.get(), ClayWorkTableScreen::new);
    }
}
