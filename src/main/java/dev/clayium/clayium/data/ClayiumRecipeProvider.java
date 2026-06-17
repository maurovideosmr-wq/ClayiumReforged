package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public final class ClayiumRecipeProvider extends RecipeProvider {
    private ClayiumRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        this.buildVanillaRecipes();
        for (ClayWorkTableOperations.Operation operation : ClayWorkTableOperations.operations()) {
            this.output.accept(recipeId(operation.defaultRecipePath()), operation.toRecipe(), null);
        }
    }

    private void buildVanillaRecipes() {
        this.shaped(RecipeCategory.MISC, ClayiumBlocks.DENSE_CLAY.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.CLAY)
                .unlockedBy("has_clay", this.has(Items.CLAY))
                .save(this.output, recipeId("dense_clay"));
        this.shapeless(RecipeCategory.MISC, Items.CLAY, 9)
                .requires(ClayiumBlocks.DENSE_CLAY.get())
                .unlockedBy("has_dense_clay", this.has(ClayiumBlocks.DENSE_CLAY.get()))
                .save(this.output, recipeId("clay_from_dense_clay"));
        this.shaped(RecipeCategory.MISC, ClayiumBlocks.CLAY_WORK_TABLE.get())
                .pattern("##")
                .pattern("##")
                .define('#', ClayiumBlocks.DENSE_CLAY.get())
                .unlockedBy("has_dense_clay", this.has(ClayiumBlocks.DENSE_CLAY.get()))
                .save(this.output, recipeId("clay_work_table"));

        this.shapeless(RecipeCategory.MISC, ClayiumItems.CLAY_LARGE_BALL.get())
                .requires(Items.CLAY_BALL, 8)
                .unlockedBy("has_clay_ball", this.has(Items.CLAY_BALL))
                .save(this.output, recipeId("clay_large_ball"));
        this.shaped(RecipeCategory.MISC, ClayiumItems.CLAY_LARGE_PLATE.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ClayiumItems.CLAY_PLATE.get())
                .unlockedBy("has_clay_plate", this.has(ClayiumItems.CLAY_PLATE.get()))
                .save(this.output, recipeId("clay_large_plate"));
        this.ringedPartRecipe("clay_gear", ClayiumItems.CLAY_GEAR.get(), ClayiumItems.CLAY_SHORT_STICK.get(), ClayiumItems.CLAY_SMALL_RING.get());
        this.ringedPartRecipe("dense_clay_gear", ClayiumItems.DENSE_CLAY_GEAR.get(), ClayiumItems.DENSE_CLAY_SHORT_STICK.get(), ClayiumItems.DENSE_CLAY_SMALL_RING.get());
        this.ringedPartRecipe("clay_cutting_head", ClayiumItems.CLAY_CUTTING_HEAD.get(), ClayiumItems.CLAY_BLADE.get(), ClayiumItems.CLAY_RING.get());
        this.ringedPartRecipe("dense_clay_cutting_head", ClayiumItems.DENSE_CLAY_CUTTING_HEAD.get(), ClayiumItems.DENSE_CLAY_BLADE.get(), ClayiumItems.DENSE_CLAY_RING.get());
        this.ringedPartRecipe("clay_bearing", ClayiumItems.CLAY_BEARING.get(), Items.CLAY_BALL, ClayiumItems.CLAY_RING.get());
        this.ringedPartRecipe("dense_clay_bearing", ClayiumItems.DENSE_CLAY_BEARING.get(), Items.CLAY_BALL, ClayiumItems.DENSE_CLAY_RING.get());
        this.spindleRecipe(
                "clay_spindle",
                ClayiumItems.CLAY_SPINDLE.get(),
                ClayiumItems.CLAY_STICK.get(),
                ClayiumItems.CLAY_BEARING.get(),
                ClayiumItems.CLAY_RING.get(),
                ClayiumItems.CLAY_PLATE.get(),
                ClayiumItems.CLAY_SMALL_RING.get()
        );
        this.spindleRecipe(
                "dense_clay_spindle",
                ClayiumItems.DENSE_CLAY_SPINDLE.get(),
                ClayiumItems.DENSE_CLAY_STICK.get(),
                ClayiumItems.DENSE_CLAY_BEARING.get(),
                ClayiumItems.DENSE_CLAY_RING.get(),
                ClayiumItems.DENSE_CLAY_PLATE.get(),
                ClayiumItems.DENSE_CLAY_SMALL_RING.get()
        );
        this.ringedPartRecipe("clay_grinding_head", ClayiumItems.CLAY_GRINDING_HEAD.get(), ClayiumItems.CLAY_NEEDLE.get(), ClayiumItems.CLAY_RING.get());
        this.ringedPartRecipe("dense_clay_grinding_head", ClayiumItems.DENSE_CLAY_GRINDING_HEAD.get(), ClayiumItems.DENSE_CLAY_NEEDLE.get(), ClayiumItems.DENSE_CLAY_RING.get());
        this.waterWheelRecipe("clay_water_wheel", ClayiumItems.CLAY_WATER_WHEEL.get(), ClayiumItems.CLAY_PLATE.get(), ClayiumItems.CLAY_RING.get());
        this.waterWheelRecipe("dense_clay_water_wheel", ClayiumItems.DENSE_CLAY_WATER_WHEEL.get(), ClayiumItems.DENSE_CLAY_PLATE.get(), ClayiumItems.DENSE_CLAY_RING.get());
        this.oneToOneShapeless("clay_pipe_from_plate", ClayiumItems.CLAY_PIPE.get(), ClayiumItems.CLAY_PLATE.get());
        this.oneToOneShapeless("clay_ring_from_cylinder", ClayiumItems.CLAY_RING.get(), ClayiumItems.CLAY_CYLINDER.get());
        this.oneToOneShapeless("clay_short_stick_from_small_ring", ClayiumItems.CLAY_SHORT_STICK.get(), ClayiumItems.CLAY_SMALL_RING.get());
        this.shapeless(RecipeCategory.MISC, ClayiumItems.CLAY_SHORT_STICK.get(), 2)
                .requires(ClayiumItems.CLAY_STICK.get())
                .unlockedBy("has_clay_stick", this.has(ClayiumItems.CLAY_STICK.get()))
                .save(this.output, recipeId("clay_short_stick_from_stick"));
        this.oneToOneShapeless("clay_small_ring_from_short_stick", ClayiumItems.CLAY_SMALL_RING.get(), ClayiumItems.CLAY_SHORT_STICK.get());

        this.shaped(RecipeCategory.MISC, ClayiumItems.RAW_CLAY_ROLLING_PIN.get())
                .pattern("#X#")
                .define('#', ClayiumItems.CLAY_SHORT_STICK.get())
                .define('X', ClayiumItems.CLAY_CYLINDER.get())
                .unlockedBy("has_clay_cylinder", this.has(ClayiumItems.CLAY_CYLINDER.get()))
                .save(this.output, recipeId("raw_clay_rolling_pin"));
        this.shaped(RecipeCategory.MISC, ClayiumItems.RAW_CLAY_SPATULA.get())
                .pattern("#X")
                .define('#', ClayiumItems.CLAY_SHORT_STICK.get())
                .define('X', ClayiumItems.CLAY_BLADE.get())
                .unlockedBy("has_clay_blade", this.has(ClayiumItems.CLAY_BLADE.get()))
                .save(this.output, recipeId("raw_clay_spatula"));
        this.shaped(RecipeCategory.MISC, ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get())
                .pattern("###")
                .pattern("#G#")
                .pattern("###")
                .define('#', ClayiumItems.CLAY_LARGE_PLATE.get())
                .define('G', ClayiumItems.CLAY_GEAR.get())
                .unlockedBy("has_clay_large_plate", this.has(ClayiumItems.CLAY_LARGE_PLATE.get()))
                .save(this.output, recipeId("raw_clay_machine_hull"));

        this.smelting("clay_rolling_pin_from_smelting", ClayiumItems.RAW_CLAY_ROLLING_PIN.get(), ClayiumItems.CLAY_ROLLING_PIN.get());
        this.smelting("clay_slicer_from_smelting", ClayiumItems.RAW_CLAY_SLICER.get(), ClayiumItems.CLAY_SLICER.get());
        this.smelting("clay_spatula_from_smelting", ClayiumItems.RAW_CLAY_SPATULA.get(), ClayiumItems.CLAY_SPATULA.get());
        this.smelting("clay_machine_hull_from_smelting", ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get(), ClayiumBlocks.CLAY_MACHINE_HULL.get());

        this.compressionRecipe("compressed_clay", ClayiumBlocks.COMPRESSED_CLAY.get(), ClayiumBlocks.DENSE_CLAY.get());
        this.decompressionRecipe("dense_clay_from_compressed_clay", ClayiumBlocks.DENSE_CLAY.get(), ClayiumBlocks.COMPRESSED_CLAY.get());
        this.compressionRecipe("industrial_clay", ClayiumBlocks.INDUSTRIAL_CLAY.get(), ClayiumBlocks.COMPRESSED_CLAY.get());
        this.decompressionRecipe("compressed_clay_from_industrial_clay", ClayiumBlocks.COMPRESSED_CLAY.get(), ClayiumBlocks.INDUSTRIAL_CLAY.get());
        this.compressionRecipe("advanced_industrial_clay", ClayiumBlocks.ADVANCED_INDUSTRIAL_CLAY.get(), ClayiumBlocks.INDUSTRIAL_CLAY.get());
        this.decompressionRecipe("industrial_clay_from_advanced_industrial_clay", ClayiumBlocks.INDUSTRIAL_CLAY.get(), ClayiumBlocks.ADVANCED_INDUSTRIAL_CLAY.get());

        this.shaped(RecipeCategory.MISC, ClayiumItems.CLAY_SHOVEL.get())
                .pattern("#")
                .pattern("|")
                .pattern("|")
                .define('#', ClayiumItems.CLAY_PLATE.get())
                .define('|', ClayiumItems.CLAY_STICK.get())
                .unlockedBy("has_clay_plate", this.has(ClayiumItems.CLAY_PLATE.get()))
                .save(this.output, recipeId("clay_shovel"));
        this.shaped(RecipeCategory.MISC, ClayiumItems.CLAY_PICKAXE.get())
                .pattern("###")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ClayiumItems.DENSE_CLAY_PLATE.get())
                .define('|', ClayiumItems.DENSE_CLAY_STICK.get())
                .unlockedBy("has_dense_clay_plate", this.has(ClayiumItems.DENSE_CLAY_PLATE.get()))
                .save(this.output, recipeId("clay_pickaxe"));
        this.shaped(RecipeCategory.MISC, ClayiumItems.CLAY_WRENCH.get())
                .pattern("# #")
                .pattern(" o ")
                .pattern(" | ")
                .define('#', ClayiumItems.DENSE_CLAY_BLADE.get())
                .define('o', ClayiumItems.DENSE_CLAY_SPINDLE.get())
                .define('|', ClayiumItems.DENSE_CLAY_STICK.get())
                .unlockedBy("has_dense_clay_spindle", this.has(ClayiumItems.DENSE_CLAY_SPINDLE.get()))
                .save(this.output, recipeId("clay_wrench"));
    }

    private void ringedPartRecipe(String path, ItemLike result, ItemLike outer, ItemLike center) {
        this.shaped(RecipeCategory.MISC, result)
                .pattern("iii")
                .pattern("ioi")
                .pattern("iii")
                .define('i', outer)
                .define('o', center)
                .unlockedBy("has_" + path, this.has(outer))
                .save(this.output, recipeId(path));
    }

    private void spindleRecipe(String path, ItemLike result, ItemLike stick, ItemLike bearing, ItemLike ring, ItemLike plate, ItemLike smallRing) {
        this.shaped(RecipeCategory.MISC, result)
                .pattern("0#0")
                .pattern("ioO")
                .pattern("0#0")
                .define('i', stick)
                .define('o', bearing)
                .define('O', ring)
                .define('#', plate)
                .define('0', smallRing)
                .unlockedBy("has_" + path, this.has(bearing))
                .save(this.output, recipeId(path));
    }

    private void waterWheelRecipe(String path, ItemLike result, ItemLike plate, ItemLike ring) {
        this.shaped(RecipeCategory.MISC, result)
                .pattern("###")
                .pattern("#o#")
                .pattern("###")
                .define('#', plate)
                .define('o', ring)
                .unlockedBy("has_" + path, this.has(plate))
                .save(this.output, recipeId(path));
    }

    private void oneToOneShapeless(String path, ItemLike result, ItemLike input) {
        this.shapeless(RecipeCategory.MISC, result)
                .requires(input)
                .unlockedBy("has_" + path, this.has(input))
                .save(this.output, recipeId(path));
    }

    private void smelting(String path, ItemLike input, ItemLike result) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, CookingBookCategory.MISC, result, 0.0F, 200)
                .unlockedBy("has_" + path, this.has(input))
                .save(this.output, recipeId(path));
    }

    private void compressionRecipe(String path, ItemLike result, ItemLike input) {
        this.shaped(RecipeCategory.MISC, result)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + path, this.has(input))
                .save(this.output, recipeId(path));
    }

    private void decompressionRecipe(String path, ItemLike result, ItemLike input) {
        this.shapeless(RecipeCategory.MISC, result, 9)
                .requires(input)
                .unlockedBy("has_" + path, this.has(input))
                .save(this.output, recipeId(path));
    }

    private static ResourceKey<Recipe<?>> recipeId(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Clayium.MOD_ID, path));
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
