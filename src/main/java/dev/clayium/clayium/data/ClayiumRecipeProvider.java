package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

public final class ClayiumRecipeProvider extends RecipeProvider {
    private ClayiumRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (ClayWorkTableOperations.Operation operation : ClayWorkTableOperations.operations()) {
            ResourceKey<Recipe<?>> id = ResourceKey.create(
                    Registries.RECIPE,
                    Identifier.fromNamespaceAndPath(Clayium.MOD_ID, operation.defaultRecipePath())
            );
            this.output.accept(id, operation.toRecipe(), null);
        }
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ClayiumRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Clayium Recipes";
        }
    }
}
