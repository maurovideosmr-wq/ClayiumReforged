package dev.clayium.clayium.client;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.client.screen.ClayWorkTableScreen;
import dev.clayium.clayium.recipe.ClayWorkTableRecipeCache;
import dev.clayium.clayium.registry.ClayiumMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Clayium.MOD_ID, dist = Dist.CLIENT)
public final class ClayiumClient {
    public ClayiumClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerScreens);
        NeoForge.EVENT_BUS.addListener(this::onRecipesReceived);
        NeoForge.EVENT_BUS.addListener(this::onLoggingOut);
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ClayiumMenus.CLAY_WORK_TABLE.get(), ClayWorkTableScreen::new);
    }

    private void onRecipesReceived(RecipesReceivedEvent event) {
        ClayWorkTableRecipeCache.updateClientRecipes(event.getRecipeMap());
    }

    private void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        ClayWorkTableRecipeCache.clearClientRecipes();
    }
}
