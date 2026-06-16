package dev.clayium.clayium.data;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ClayiumDataGenerators {
    private ClayiumDataGenerators() {
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        event.createProvider(ClayiumRecipeProvider.Runner::new);
        event.createProvider(ClayiumLootTableProvider::new);
        event.createProvider(ClayiumBlockTagsProvider::new);
    }
}
