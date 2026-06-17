package dev.clayium.clayium.data;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ClayiumDataGenerators {
    private ClayiumDataGenerators() {
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        addServerProviders(event);
    }

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ClayiumClientResourceProvider::new);
        event.createProvider(ClayiumLanguageProvider::new);
        addServerProviders(event);
    }

    private static void addServerProviders(GatherDataEvent event) {
        event.createProvider(ClayiumRecipeProvider.Runner::new);
        event.createProvider(ClayiumLootTableProvider::new);
        event.createProvider(ClayiumBlockTagsProvider::new);
        event.createProvider(ClayiumItemTagsProvider::new);
    }
}
