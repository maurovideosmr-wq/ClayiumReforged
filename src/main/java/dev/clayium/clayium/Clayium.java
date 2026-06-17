package dev.clayium.clayium;

import com.mojang.logging.LogUtils;
import dev.clayium.clayium.data.ClayiumDataGenerators;
import dev.clayium.clayium.gametest.ClayiumGameTests;
import dev.clayium.clayium.registry.ClayiumBlockEntities;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumCreativeTabs;
import dev.clayium.clayium.registry.ClayiumItems;
import dev.clayium.clayium.registry.ClayiumMenus;
import dev.clayium.clayium.registry.ClayiumRecipes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Clayium.MOD_ID)
public final class Clayium {
    public static final String MOD_ID = "clayium";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Clayium(IEventBus modEventBus) {
        ClayiumBlocks.register(modEventBus);
        ClayiumItems.register(modEventBus);
        ClayiumBlockEntities.register(modEventBus);
        ClayiumMenus.register(modEventBus);
        ClayiumRecipes.register(modEventBus);
        ClayiumCreativeTabs.register(modEventBus);
        ClayiumGameTests.register(modEventBus);
        modEventBus.addListener(ClayiumDataGenerators::gatherServerData);
        modEventBus.addListener(ClayiumDataGenerators::gatherClientData);
        NeoForge.EVENT_BUS.addListener(ClayiumRecipes::onDatapackSync);
    }
}
