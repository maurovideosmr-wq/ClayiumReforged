package dev.clayium.clayium.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.registry.ClayiumRecipes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public final class ClayWorkTableRecipe implements Recipe<ClayWorkTableRecipeInput> {
    public static final MapCodec<ClayWorkTableRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(ClayWorkTableRecipe::commonInfo),
            ClayWorkTableAction.CODEC.fieldOf("operation").forGetter(ClayWorkTableRecipe::action),
            Ingredient.CODEC.fieldOf("input").forGetter(ClayWorkTableRecipe::input),
            Codec.intRange(1, 64).optionalFieldOf("input_count", 1).forGetter(ClayWorkTableRecipe::inputCount),
            ClayWorkTableToolRequirement.CODEC.optionalFieldOf("tool", ClayWorkTableToolRequirement.NONE).forGetter(ClayWorkTableRecipe::toolRequirement),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("clicks").forGetter(ClayWorkTableRecipe::workTicks),
            ItemStackTemplate.CODEC.listOf(1, ClayWorkTableOperations.OUTPUT_SLOT_COUNT).fieldOf("outputs").forGetter(ClayWorkTableRecipe::outputs)
    ).apply(instance, ClayWorkTableRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClayWorkTableRecipe> STREAM_CODEC = StreamCodec.of(
            ClayWorkTableRecipe::encode,
            ClayWorkTableRecipe::decode
    );
    private static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStackTemplate>> OUTPUTS_STREAM_CODEC =
            ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list(ClayWorkTableOperations.OUTPUT_SLOT_COUNT));

    private final Recipe.CommonInfo commonInfo;
    private final ClayWorkTableAction action;
    private final Ingredient input;
    private final int inputCount;
    private final ClayWorkTableToolRequirement toolRequirement;
    private final int workTicks;
    private final List<ItemStackTemplate> outputs;
    private final PlacementInfo placementInfo;

    public ClayWorkTableRecipe(
            Recipe.CommonInfo commonInfo,
            ClayWorkTableAction action,
            Ingredient input,
            int inputCount,
            ClayWorkTableToolRequirement toolRequirement,
            int workTicks,
            List<ItemStackTemplate> outputs
    ) {
        if (outputs.isEmpty() || outputs.size() > ClayWorkTableOperations.OUTPUT_SLOT_COUNT) {
            throw new IllegalArgumentException("Clay Work Table recipes need 1.." + ClayWorkTableOperations.OUTPUT_SLOT_COUNT + " outputs");
        }
        this.commonInfo = commonInfo;
        this.action = action;
        this.input = input;
        this.inputCount = inputCount;
        this.toolRequirement = toolRequirement;
        this.workTicks = workTicks;
        this.outputs = List.copyOf(outputs);
        this.placementInfo = PlacementInfo.create(input);
    }

    public Recipe.CommonInfo commonInfo() {
        return this.commonInfo;
    }

    public ClayWorkTableAction action() {
        return this.action;
    }

    public Ingredient input() {
        return this.input;
    }

    public int inputCount() {
        return this.inputCount;
    }

    public ClayWorkTableToolRequirement toolRequirement() {
        return this.toolRequirement;
    }

    public int workTicks() {
        return this.workTicks;
    }

    public List<ItemStackTemplate> outputs() {
        return this.outputs;
    }

    @Override
    public boolean matches(ClayWorkTableRecipeInput input, Level level) {
        return this.action == input.action()
                && this.input.test(input.inputStack())
                && input.inputStack().getCount() >= this.inputCount
                && this.toolRequirement.matches(input.toolStack());
    }

    @Override
    public ItemStack assemble(ClayWorkTableRecipeInput input) {
        return this.outputs.getFirst().create();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<ClayWorkTableRecipeInput>> getSerializer() {
        return ClayiumRecipes.CLAY_WORK_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<ClayWorkTableRecipeInput>> getType() {
        return ClayiumRecipes.CLAY_WORK_TABLE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public List<ItemStack> createOutputs() {
        List<ItemStack> stacks = new ArrayList<>(this.outputs.size());
        for (ItemStackTemplate output : this.outputs) {
            stacks.add(output.create());
        }
        return stacks;
    }

    private static ClayWorkTableRecipe decode(RegistryFriendlyByteBuf input) {
        return new ClayWorkTableRecipe(
                Recipe.CommonInfo.STREAM_CODEC.decode(input),
                ClayWorkTableAction.STREAM_CODEC.decode(input),
                Ingredient.CONTENTS_STREAM_CODEC.decode(input),
                ByteBufCodecs.VAR_INT.decode(input),
                ClayWorkTableToolRequirement.STREAM_CODEC.decode(input),
                ByteBufCodecs.VAR_INT.decode(input),
                OUTPUTS_STREAM_CODEC.decode(input)
        );
    }

    private static void encode(RegistryFriendlyByteBuf output, ClayWorkTableRecipe recipe) {
        Recipe.CommonInfo.STREAM_CODEC.encode(output, recipe.commonInfo);
        ClayWorkTableAction.STREAM_CODEC.encode(output, recipe.action);
        Ingredient.CONTENTS_STREAM_CODEC.encode(output, recipe.input);
        ByteBufCodecs.VAR_INT.encode(output, recipe.inputCount);
        ClayWorkTableToolRequirement.STREAM_CODEC.encode(output, recipe.toolRequirement);
        ByteBufCodecs.VAR_INT.encode(output, recipe.workTicks);
        OUTPUTS_STREAM_CODEC.encode(output, recipe.outputs);
    }
}
