package dev.clayium.clayium.registry;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Clayium.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Clayium.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ClayWorkTableRecipe>> CLAY_WORK_TABLE_SERIALIZER =
            RECIPE_SERIALIZERS.register("clay_work_table", () -> new RecipeSerializer<>(ClayWorkTableRecipe.CODEC, ClayWorkTableRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ClayWorkTableRecipe>> CLAY_WORK_TABLE_TYPE =
            RECIPE_TYPES.register("clay_work_table", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(Clayium.MOD_ID, "clay_work_table")));

    private ClayiumRecipes() {
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(CLAY_WORK_TABLE_TYPE.get());
    }
}
