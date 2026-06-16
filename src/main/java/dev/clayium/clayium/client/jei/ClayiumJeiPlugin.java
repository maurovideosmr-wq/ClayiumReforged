package dev.clayium.clayium.client.jei;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.client.screen.ClayWorkTableScreen;
import dev.clayium.clayium.registry.ClayiumItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;

@JeiPlugin
public final class ClayiumJeiPlugin implements IModPlugin {
    static final IRecipeType<ClayWorkTableJeiRecipe> CLAY_WORK_TABLE = IRecipeType.create(
            Clayium.MOD_ID,
            "clay_work_table",
            ClayWorkTableJeiRecipe.class
    );

    private static final Identifier PLUGIN_UID = Identifier.fromNamespaceAndPath(Clayium.MOD_ID, "jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ClayWorkTableJeiCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CLAY_WORK_TABLE, ClayWorkTableJeiRecipe.all());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(CLAY_WORK_TABLE, ClayiumItems.CLAY_WORK_TABLE.get());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ClayWorkTableScreen.class, 48, 29, 80, 16, CLAY_WORK_TABLE);
    }
}
