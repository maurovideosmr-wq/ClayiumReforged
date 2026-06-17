package dev.clayium.clayium.gametest;

import com.mojang.serialization.MapCodec;
import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.block.entity.ClayWorkTableBlockEntity;
import dev.clayium.clayium.item.ClayCraftingToolItem;
import dev.clayium.clayium.menu.ClayWorkTableMenu;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import dev.clayium.clayium.recipe.ClayWorkTableRecipeCache;
import dev.clayium.clayium.registry.ClayMaterial;
import dev.clayium.clayium.registry.ClayPartType;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import dev.clayium.clayium.registry.ClayiumItems;
import dev.clayium.clayium.registry.ClayiumTags;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ClayiumGameTests {
    private static final DeferredRegister<MapCodec<? extends GameTestInstance>> TEST_INSTANCE_TYPES =
            DeferredRegister.create(BuiltInRegistries.TEST_INSTANCE_TYPE, Clayium.MOD_ID);

    private static final List<TestDefinition> TESTS = List.of(
            test("phase1/registry_surface", 40, ClayiumGameTests::registrySurface),
            test("phase1/work_table_recipe_data", 40, ClayiumGameTests::workTableRecipeData),
            test("phase1/work_table_menu_processing", 80, ClayiumGameTests::workTableMenuProcessing),
            test("phase1/crafting_and_smelting_recipes", 40, ClayiumGameTests::craftingAndSmeltingRecipes),
            test("phase1/block_drops_and_harvest", 120, ClayiumGameTests::blockDropsAndHarvest),
            test("phase2/clay_tool_behavior", 40, ClayiumGameTests::clayToolBehavior)
    );
    private static final Map<Identifier, Consumer<GameTestHelper>> FUNCTIONS = TESTS.stream()
            .collect(Collectors.toUnmodifiableMap(TestDefinition::id, TestDefinition::function));

    @SuppressWarnings("unused")
    private static final DeferredHolder<MapCodec<? extends GameTestInstance>, MapCodec<ClayiumGameTestInstance>> DIRECT_INSTANCE_TYPE =
            TEST_INSTANCE_TYPES.register("direct", () -> ClayiumGameTestInstance.CODEC);

    private ClayiumGameTests() {
    }

    public static void register(IEventBus eventBus) {
        TEST_INSTANCE_TYPES.register(eventBus);
        eventBus.addListener(ClayiumGameTests::registerTests);
    }

    static void run(Identifier functionId, GameTestHelper helper) {
        Consumer<GameTestHelper> function = FUNCTIONS.get(functionId);
        if (function == null) {
            helper.fail("Missing Clayium GameTest function " + functionId);
            return;
        }
        function.accept(helper);
    }

    private static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(id("gametest/default"));
        for (TestDefinition test : TESTS) {
            event.registerTest(test.id(), new ClayiumGameTestInstance(test.id(), testData(environment, test.maxTicks())));
        }
    }

    private static TestData<Holder<TestEnvironmentDefinition<?>>> testData(Holder<TestEnvironmentDefinition<?>> environment, int maxTicks) {
        return new TestData<>(
                environment,
                Identifier.withDefaultNamespace("empty"),
                maxTicks,
                0,
                true,
                Rotation.NONE,
                false,
                1,
                1,
                false,
                4
        );
    }

    private static void registrySurface(GameTestHelper helper) {
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            assertBlockItem(helper, spec.id(), ClayiumBlocks.catalogBlock(spec.id()).get(), ClayiumItems.blockItem(spec.id()).get());
        }
        for (ClayiumContentCatalog.SimpleItemSpec spec : ClayiumContentCatalog.simpleItems()) {
            assertRegisteredItem(helper, spec.id(), ClayiumItems.simpleItem(spec.id()).get());
        }
        helper.assertValueEqual(ClayiumItems.CREATIVE_BLOCKS.size(), ClayiumContentCatalog.blocks().size(), "creative block count");
        helper.assertValueEqual(ClayiumItems.CREATIVE_TOOLS.size(), creativeCount(ClayiumContentCatalog.CreativeCategory.TOOLS), "creative tool count");
        helper.assertValueEqual(ClayiumItems.CREATIVE_PROGRESSION.size(), creativeCount(ClayiumContentCatalog.CreativeCategory.PROGRESSION), "creative progression count");

        int expectedPartCount = 0;
        for (ClayPartType partType : ClayPartType.values()) {
            expectedPartCount += partType.materials().size();
            for (ClayMaterial material : ClayMaterial.values()) {
                if (partType.isAvailableFor(material)) {
                    assertRegisteredItem(helper, material.itemId(partType), ClayiumItems.PARTS.get(material, partType).get());
                }
            }
        }
        helper.assertValueEqual(ClayiumItems.PARTS.items().size(), expectedPartCount, "Phase 1 material part count");
        helper.assertFalse(ClayiumItems.PARTS.contains(ClayMaterial.DENSE_CLAY, ClayPartType.LARGE_BALL), "Dense clay large ball should stay absent");
        helper.assertFalse(ClayiumItems.PARTS.contains(ClayMaterial.ENERGIZED_CLAY, ClayPartType.PLATE), "Energized clay plate should stay absent");
        helper.succeed();
    }

    private static void workTableRecipeData(GameTestHelper helper) {
        List<RecipeHolder<ClayWorkTableRecipe>> recipes = ClayWorkTableRecipeCache.recipes(helper.getLevel());
        helper.assertValueEqual(recipes.size(), ClayWorkTableOperations.operations().size(), "Clay Work Table recipe count");

        for (ClayWorkTableOperations.Operation operation : ClayWorkTableOperations.operations()) {
            ItemStack input = new ItemStack(operation.input().get(), operation.inputCount());
            ItemStack tool = displayTool(operation);
            ClayWorkTableRecipe recipe = ClayWorkTableRecipeCache.findBest(helper.getLevel(), operation.action(), input, tool)
                    .map(RecipeHolder::value)
                    .orElseThrow(() -> helper.assertionException("Missing Clay Work Table recipe for " + operation.defaultRecipePath()));

            helper.assertValueEqual(recipe.inputCount(), operation.inputCount(), operation.defaultRecipePath() + " input count");
            helper.assertValueEqual(recipe.workTicks(), operation.workTicks(), operation.defaultRecipePath() + " click count");
            helper.assertValueEqual(recipe.toolRequirement(), operation.toolRequirement(), operation.defaultRecipePath() + " tool");
            assertOutputs(helper, operation, recipe.createOutputs());
        }
        helper.succeed();
    }

    private static void workTableMenuProcessing(GameTestHelper helper) {
        MenuFixture singleItem = menuFixture(helper);
        singleItem.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL));
        singleItem.menu.clickMenuButton(singleItem.player, 1);
        assertStack(helper, singleItem.table.getItem(ClayWorkTableOperations.INPUT_SLOT), Items.AIR, 0, "single input slot after start");
        assertStack(helper, singleItem.table.getItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT), Items.CLAY_BALL, 1, "hidden work input after start");
        helper.assertTrue(singleItem.menu.canProcessButton(1), "single reserved input should still allow more clicks");
        click(singleItem, 1, 3);
        assertStack(helper, singleItem.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), ClayiumItems.CLAY_STICK.get(), 1, "clay ball hand knead output");
        helper.assertValueEqual(singleItem.menu.getCookingMethod(), 0, "completed recipe method reset");
        assertStack(helper, singleItem.table.getItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT), Items.AIR, 0, "hidden work input after completion");

        MenuFixture locked = menuFixture(helper);
        locked.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL));
        locked.table.setItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT, new ItemStack(Items.DIRT));
        helper.assertFalse(locked.menu.canProcessButton(1), "different output item should lock the operation");
        locked.menu.clickMenuButton(locked.player, 1);
        assertStack(helper, locked.table.getItem(ClayWorkTableOperations.INPUT_SLOT), Items.CLAY_BALL, 1, "locked input should remain");
        assertStack(helper, locked.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), Items.DIRT, 1, "locked output should remain");

        MenuFixture stacked = menuFixture(helper);
        stacked.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL));
        stacked.table.setItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT, new ItemStack(ClayiumItems.CLAY_STICK.get(), 63));
        click(stacked, 1, 4);
        assertStack(helper, stacked.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), ClayiumItems.CLAY_STICK.get(), 64, "same output should stack");

        MenuFixture largestInput = menuFixture(helper);
        largestInput.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_PLATE.get(), 6));
        largestInput.table.setItem(ClayWorkTableOperations.TOOL_SLOT, new ItemStack(ClayiumItems.CLAY_ROLLING_PIN.get()));
        click(largestInput, 3, 10);
        assertStack(helper, largestInput.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), ClayiumItems.CLAY_LARGE_PLATE.get(), 1, "largest matching rolling-pin recipe output");
        assertStack(helper, largestInput.table.getItem(ClayWorkTableOperations.INPUT_SLOT), Items.AIR, 0, "largest matching recipe consumes six plates");

        MenuFixture toolWear = menuFixture(helper);
        toolWear.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_DISC.get()));
        toolWear.table.setItem(ClayWorkTableOperations.TOOL_SLOT, new ItemStack(ClayiumItems.CLAY_SLICER.get()));
        toolWear.menu.clickMenuButton(toolWear.player, 4);
        ItemStack wornSlicer = toolWear.table.getItem(ClayWorkTableOperations.TOOL_SLOT);
        assertStack(helper, wornSlicer, ClayiumItems.CLAY_SLICER.get(), 1, "valid slicer click should keep tool item");
        helper.assertValueEqual(wornSlicer.getDamageValue(), 1, "valid slicer click should consume one durability");
        helper.assertValueEqual(toolWear.menu.getCookingMethod(), 4, "slicer recipe should stay in progress");

        MenuFixture toolBreaksOnFinalClick = menuFixture(helper);
        ItemStack exhaustedRollingPin = new ItemStack(ClayiumItems.CLAY_ROLLING_PIN.get());
        exhaustedRollingPin.setDamageValue(exhaustedRollingPin.getMaxDamage());
        toolBreaksOnFinalClick.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_PLATE.get()));
        toolBreaksOnFinalClick.table.setItem(ClayWorkTableOperations.TOOL_SLOT, exhaustedRollingPin);
        toolBreaksOnFinalClick.menu.clickMenuButton(toolBreaksOnFinalClick.player, 3);
        assertStack(helper, toolBreaksOnFinalClick.table.getItem(ClayWorkTableOperations.TOOL_SLOT), Items.CLAY_BALL, 4, "broken rolling pin should return clay balls");
        assertStack(helper, toolBreaksOnFinalClick.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), ClayiumItems.CLAY_BLADE.get(), 1, "breaking final tool use should still craft main output");
        assertStack(helper, toolBreaksOnFinalClick.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT + 1), Items.CLAY_BALL, 2, "breaking final tool use should still craft byproduct");
        helper.assertValueEqual(toolBreaksOnFinalClick.menu.getCookingMethod(), 0, "breaking final tool use should complete recipe");

        MenuFixture differentActionWhileWorking = menuFixture(helper);
        differentActionWhileWorking.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL));
        differentActionWhileWorking.menu.clickMenuButton(differentActionWhileWorking.player, 1);
        differentActionWhileWorking.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_LARGE_BALL.get()));
        helper.assertFalse(differentActionWhileWorking.menu.canProcessButton(2), "different action should lock while input is reserved");
        differentActionWhileWorking.menu.clickMenuButton(differentActionWhileWorking.player, 2);
        helper.assertValueEqual(differentActionWhileWorking.menu.getCookingMethod(), 1, "different action should not replace method");
        helper.assertValueEqual(differentActionWhileWorking.menu.getCookTime(), 1, "different action should not advance or reset progress");
        assertStack(helper, differentActionWhileWorking.table.getItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT), Items.CLAY_BALL, 1, "different action should keep hidden input");
        assertStack(helper, differentActionWhileWorking.table.getItem(ClayWorkTableOperations.INPUT_SLOT), ClayiumItems.CLAY_LARGE_BALL.get(), 1, "different action should keep visible input");
        click(differentActionWhileWorking, 1, 3);
        assertStack(helper, differentActionWhileWorking.table.getItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT), ClayiumItems.CLAY_STICK.get(), 1, "locked work should still complete original recipe");

        MenuFixture sameActionWhileWorking = menuFixture(helper);
        sameActionWhileWorking.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL));
        sameActionWhileWorking.menu.clickMenuButton(sameActionWhileWorking.player, 1);
        sameActionWhileWorking.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_PLATE.get(), 3));
        sameActionWhileWorking.menu.clickMenuButton(sameActionWhileWorking.player, 1);
        helper.assertValueEqual(sameActionWhileWorking.menu.getCookingMethod(), 1, "same action should continue reserved recipe");
        helper.assertValueEqual(sameActionWhileWorking.menu.getCookTime(), 2, "same action should advance reserved recipe");
        assertStack(helper, sameActionWhileWorking.table.getItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT), Items.CLAY_BALL, 1, "same action should keep hidden input");
        assertStack(helper, sameActionWhileWorking.table.getItem(ClayWorkTableOperations.INPUT_SLOT), ClayiumItems.CLAY_PLATE.get(), 3, "same action should not consume visible replacement input");

        MenuFixture sameInputDifferentAction = menuFixture(helper);
        sameInputDifferentAction.table.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_DISC.get(), 2));
        sameInputDifferentAction.table.setItem(ClayWorkTableOperations.TOOL_SLOT, new ItemStack(ClayiumItems.CLAY_ROLLING_PIN.get()));
        sameInputDifferentAction.menu.clickMenuButton(sameInputDifferentAction.player, 2);
        helper.assertFalse(sameInputDifferentAction.menu.canProcessButton(3), "same input with another valid action should stay locked to current work");
        sameInputDifferentAction.menu.clickMenuButton(sameInputDifferentAction.player, 3);
        helper.assertValueEqual(sameInputDifferentAction.menu.getCookingMethod(), 2, "same input different action should not replace method");
        helper.assertValueEqual(sameInputDifferentAction.menu.getCookTime(), 1, "same input different action should not advance or reset progress");
        assertStack(helper, sameInputDifferentAction.table.getItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT), ClayiumItems.CLAY_DISC.get(), 1, "same input different action should keep hidden input");
        assertStack(helper, sameInputDifferentAction.table.getItem(ClayWorkTableOperations.INPUT_SLOT), ClayiumItems.CLAY_DISC.get(), 1, "same input different action should keep visible remainder");
        helper.succeed();
    }

    private static void craftingAndSmeltingRecipes(GameTestHelper helper) {
        assertCraftingRecipe(helper, "dense_clay", ClayiumItems.DENSE_CLAY.get(), 1);
        assertCraftingRecipe(helper, "clay_from_dense_clay", Items.CLAY, 9);
        assertCraftingRecipe(helper, "clay_work_table", ClayiumItems.CLAY_WORK_TABLE.get(), 1);
        assertCraftingRecipe(helper, "clay_large_ball", ClayiumItems.CLAY_LARGE_BALL.get(), 1);
        assertCraftingRecipe(helper, "clay_large_plate", ClayiumItems.CLAY_LARGE_PLATE.get(), 1);
        assertCraftingRecipe(helper, "clay_gear", ClayiumItems.CLAY_GEAR.get(), 1);
        assertCraftingRecipe(helper, "dense_clay_gear", ClayiumItems.DENSE_CLAY_GEAR.get(), 1);
        assertCraftingRecipe(helper, "clay_pipe_from_plate", ClayiumItems.CLAY_PIPE.get(), 1);
        assertCraftingRecipe(helper, "clay_ring_from_cylinder", ClayiumItems.CLAY_RING.get(), 1);
        assertCraftingRecipe(helper, "clay_short_stick_from_small_ring", ClayiumItems.CLAY_SHORT_STICK.get(), 1);
        assertCraftingRecipe(helper, "clay_short_stick_from_stick", ClayiumItems.CLAY_SHORT_STICK.get(), 2);
        assertCraftingRecipe(helper, "clay_small_ring_from_short_stick", ClayiumItems.CLAY_SMALL_RING.get(), 1);
        assertCraftingRecipe(helper, "raw_clay_rolling_pin", ClayiumItems.RAW_CLAY_ROLLING_PIN.get(), 1);
        assertCraftingRecipe(helper, "raw_clay_spatula", ClayiumItems.RAW_CLAY_SPATULA.get(), 1);
        assertCraftingRecipe(helper, "raw_clay_machine_hull", ClayiumItems.RAW_CLAY_MACHINE_HULL.get(), 1);
        assertCraftingRecipe(helper, "compressed_clay", ClayiumItems.COMPRESSED_CLAY.get(), 1);
        assertCraftingRecipe(helper, "dense_clay_from_compressed_clay", ClayiumItems.DENSE_CLAY.get(), 9);
        assertCraftingRecipe(helper, "industrial_clay", ClayiumItems.INDUSTRIAL_CLAY.get(), 1);
        assertCraftingRecipe(helper, "compressed_clay_from_industrial_clay", ClayiumItems.COMPRESSED_CLAY.get(), 9);
        assertCraftingRecipe(helper, "advanced_industrial_clay", ClayiumItems.ADVANCED_INDUSTRIAL_CLAY.get(), 1);
        assertCraftingRecipe(helper, "industrial_clay_from_advanced_industrial_clay", ClayiumItems.INDUSTRIAL_CLAY.get(), 9);
        assertCraftingRecipe(helper, "clay_shovel", ClayiumItems.CLAY_SHOVEL.get(), 1);
        assertCraftingRecipe(helper, "clay_pickaxe", ClayiumItems.CLAY_PICKAXE.get(), 1);
        assertCraftingRecipe(helper, "clay_wrench", ClayiumItems.CLAY_WRENCH.get(), 1);

        assertSmeltingRecipe(helper, "clay_rolling_pin_from_smelting", ClayiumItems.CLAY_ROLLING_PIN.get());
        assertSmeltingRecipe(helper, "clay_slicer_from_smelting", ClayiumItems.CLAY_SLICER.get());
        assertSmeltingRecipe(helper, "clay_spatula_from_smelting", ClayiumItems.CLAY_SPATULA.get());
        assertSmeltingRecipe(helper, "clay_machine_hull_from_smelting", ClayiumItems.CLAY_MACHINE_HULL.get());
        assertNoRecipe(helper, "dense_clay_machine_hull");
        assertNoRecipe(helper, "simple_machine_hull");
        assertNoRecipe(helper, "basic_machine_hull");
        assertNoRecipe(helper, "clay_circuit");
        assertNoRecipe(helper, "simple_circuit");
        assertNoRecipe(helper, "basic_circuit");
        helper.succeed();
    }

    private static void blockDropsAndHarvest(GameTestHelper helper) {
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            Block block = ClayiumBlocks.catalogBlock(spec.id()).get();
            assertBlockDropsSelf(helper, block, ClayiumItems.blockItem(spec.id()).get());
            switch (spec.harvestTool()) {
                case NONE -> helper.assertFalse(block.defaultBlockState().requiresCorrectToolForDrops(), spec.id() + " should not force a harvest tool");
                case SHOVEL -> assertToolHarvest(helper, block.defaultBlockState(), BlockTags.MINEABLE_WITH_SHOVEL, Items.WOODEN_SHOVEL, spec.id());
                case PICKAXE -> assertToolHarvest(helper, block.defaultBlockState(), BlockTags.MINEABLE_WITH_PICKAXE, Items.WOODEN_PICKAXE, spec.id());
            }
        }
        helper.assertFalse(ClayiumBlocks.CLAY_WORK_TABLE.get().defaultBlockState().requiresCorrectToolForDrops(), "Clay Work Table should not force a harvest tool");

        ServerLevel level = helper.getLevel();
        BlockPos relativePos = new BlockPos(1, 1, 1);
        BlockPos pos = helper.absolutePos(relativePos);
        level.setBlock(pos, ClayiumBlocks.CLAY_WORK_TABLE.get().defaultBlockState(), Block.UPDATE_ALL);
        ClayWorkTableBlockEntity blockEntity = helper.getBlockEntity(relativePos, ClayWorkTableBlockEntity.class);
        blockEntity.setItem(ClayWorkTableOperations.INPUT_SLOT, new ItemStack(Items.CLAY_BALL, 3));
        blockEntity.setItem(ClayWorkTableOperations.TOOL_SLOT, new ItemStack(ClayiumItems.CLAY_ROLLING_PIN.get()));
        blockEntity.setItem(ClayWorkTableOperations.FIRST_OUTPUT_SLOT, new ItemStack(ClayiumItems.CLAY_STICK.get(), 2));
        blockEntity.setItem(ClayWorkTableOperations.INTERNAL_INPUT_SLOT, new ItemStack(ClayiumItems.CLAY_PLATE.get()));

        level.destroyBlock(pos, true);
        helper.succeedWhen(() -> {
            List<ItemEntity> drops = level.getEntities(EntityType.ITEM, new AABB(pos).inflate(2.0), Entity::isAlive);
            assertDropped(helper, drops, ClayiumItems.CLAY_WORK_TABLE.get(), 1, "Clay Work Table self drop");
            assertDropped(helper, drops, Items.CLAY_BALL, 3, "Clay Work Table input drop");
            assertDropped(helper, drops, ClayiumItems.CLAY_ROLLING_PIN.get(), 1, "Clay Work Table tool drop");
            assertDropped(helper, drops, ClayiumItems.CLAY_STICK.get(), 2, "Clay Work Table output drop");
            assertDropped(helper, drops, ClayiumItems.CLAY_PLATE.get(), 1, "Clay Work Table hidden in-progress input drop");
        });
    }

    private static void assertCraftingRecipe(GameTestHelper helper, String path, Item expectedItem, int expectedCount) {
        Recipe<?> recipe = recipe(helper, path);
        if (!(recipe instanceof CraftingRecipe craftingRecipe)) {
            helper.fail(path + " should be a crafting recipe");
            return;
        }
        assertStack(helper, craftingRecipe.assemble(CraftingInput.EMPTY), expectedItem, expectedCount, path);
    }

    private static void assertSmeltingRecipe(GameTestHelper helper, String path, Item expectedItem) {
        Recipe<?> recipe = recipe(helper, path);
        if (!(recipe instanceof SmeltingRecipe smeltingRecipe)) {
            helper.fail(path + " should be a smelting recipe");
            return;
        }
        assertStack(helper, smeltingRecipe.assemble(new SingleRecipeInput(ItemStack.EMPTY)), expectedItem, 1, path);
        helper.assertValueEqual(smeltingRecipe.cookingTime(), 200, path + " cooking time");
    }

    private static void assertNoRecipe(GameTestHelper helper, String path) {
        Optional<RecipeHolder<?>> holder = helper.getLevel().recipeAccess().byKey(ResourceKey.create(Registries.RECIPE, id(path)));
        helper.assertTrue(holder.isEmpty(), "clayium:" + path + " should stay registered-only without a recipe");
    }

    private static Recipe<?> recipe(GameTestHelper helper, String path) {
        Optional<RecipeHolder<?>> holder = helper.getLevel().recipeAccess().byKey(ResourceKey.create(Registries.RECIPE, id(path)));
        if (holder.isEmpty()) {
            helper.fail("Missing recipe clayium:" + path);
            throw helper.assertionException("Missing recipe clayium:" + path);
        }
        return holder.get().value();
    }

    private static void assertOutputs(GameTestHelper helper, ClayWorkTableOperations.Operation operation, List<ItemStack> outputs) {
        helper.assertValueEqual(outputs.size(), operation.outputs().size(), operation.defaultRecipePath() + " output count");
        for (int index = 0; index < outputs.size(); index++) {
            ClayWorkTableOperations.Output expected = operation.outputs().get(index);
            assertStack(
                    helper,
                    outputs.get(index),
                    expected.item().get().asItem(),
                    expected.count(),
                    operation.defaultRecipePath() + " output " + index
            );
        }
    }

    private static ItemStack displayTool(ClayWorkTableOperations.Operation operation) {
        List<ItemLike> tools = operation.toolRequirement().displayTools();
        if (tools.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(tools.getFirst());
    }

    private static void assertBlockItem(GameTestHelper helper, String path, Block block, Item item) {
        assertRegisteredBlock(helper, path, block);
        assertRegisteredItem(helper, path, item);
        helper.assertValueEqual(block.asItem(), item, path + " block item");
    }

    private static void assertRegisteredBlock(GameTestHelper helper, String path, Block block) {
        Identifier key = BuiltInRegistries.BLOCK.getKey(block);
        helper.assertValueEqual(key, id(path), path + " block id");
    }

    private static void assertRegisteredItem(GameTestHelper helper, String path, Item item) {
        Identifier key = BuiltInRegistries.ITEM.getKey(item);
        helper.assertValueEqual(key, id(path), path + " item id");
    }

    private static int creativeCount(ClayiumContentCatalog.CreativeCategory category) {
        return (int) ClayiumContentCatalog.simpleItems().stream()
                .filter(spec -> spec.creativeCategory() == category)
                .count();
    }

    private static void assertBlockDropsSelf(GameTestHelper helper, Block block, Item expectedItem) {
        List<ItemStack> drops = Block.getDrops(block.defaultBlockState(), helper.getLevel(), BlockPos.ZERO, null);
        int count = drops.stream()
                .filter(stack -> stack.is(expectedItem))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertValueEqual(count, 1, BuiltInRegistries.BLOCK.getKey(block) + " self drop");
    }

    private static void assertToolHarvest(GameTestHelper helper, BlockState state, TagKey<Block> mineableTag, Item tool, String name) {
        helper.assertTrue(state.requiresCorrectToolForDrops(), name + " should require its matching tool for drops");
        helper.assertTrue(state.is(mineableTag), name + " should be in " + mineableTag.location());
        helper.assertFalse(state.is(BlockTags.NEEDS_STONE_TOOL), name + " should not need stone tier");
        helper.assertFalse(state.is(BlockTags.NEEDS_IRON_TOOL), name + " should not need iron tier");
        helper.assertFalse(state.is(BlockTags.NEEDS_DIAMOND_TOOL), name + " should not need diamond tier");
        helper.assertFalse(state.is(BlockTags.INCORRECT_FOR_WOODEN_TOOL), name + " should allow wooden-tier tools");
        helper.assertTrue(new ItemStack(tool).isCorrectToolForDrops(state), name + " should be harvestable with " + BuiltInRegistries.ITEM.getKey(tool));
    }

    private static void clayToolBehavior(GameTestHelper helper) {
        ItemStack shovel = new ItemStack(ClayiumItems.CLAY_SHOVEL.get());
        ItemStack pickaxe = new ItemStack(ClayiumItems.CLAY_PICKAXE.get());
        ItemStack wrench = new ItemStack(ClayiumItems.CLAY_WRENCH.get());
        ItemStack rollingPin = new ItemStack(ClayiumItems.CLAY_ROLLING_PIN.get());
        ItemStack slicer = new ItemStack(ClayiumItems.CLAY_SLICER.get());
        ItemStack spatula = new ItemStack(ClayiumItems.CLAY_SPATULA.get());
        ItemStack rawRollingPin = new ItemStack(ClayiumItems.RAW_CLAY_ROLLING_PIN.get());

        helper.assertValueEqual(shovel.getMaxDamage(), 500, "Clay Shovel durability");
        helper.assertTrue(shovel.get(DataComponents.TOOL) != null, "Clay Shovel should have a tool component");
        helper.assertTrue(shovel.is(ItemTags.SHOVELS), "Clay Shovel should be a vanilla shovel");
        helper.assertTrue(shovel.is(ItemTags.MINING_ENCHANTABLE), "Clay Shovel should accept mining enchantments");
        helper.assertTrue(shovel.is(ItemTags.DURABILITY_ENCHANTABLE), "Clay Shovel should accept durability enchantments");
        helper.assertTrue(shovel.is(ClayiumTags.Items.CLAY_TOOLS), "Clay Shovel should be a Clayium clay tool");
        helper.assertTrue(shovel.is(ClayiumTags.Items.CLAY_MINING_TOOLS), "Clay Shovel should be a Clayium mining tool");
        assertDestroySpeed(helper, ClayiumItems.CLAY_SHOVEL.get(), shovel, Blocks.CLAY.defaultBlockState(), 32.0F, "Clay Shovel vanilla clay speed");
        assertDestroySpeed(helper, ClayiumItems.CLAY_SHOVEL.get(), shovel, ClayiumBlocks.DENSE_CLAY.get().defaultBlockState(), 32.0F, "Clay Shovel dense clay speed");
        helper.assertTrue(shovel.isCorrectToolForDrops(ClayiumBlocks.DENSE_CLAY.get().defaultBlockState()), "Clay Shovel should harvest Clayium shovel blocks");

        helper.assertValueEqual(pickaxe.getMaxDamage(), 500, "Clay Pickaxe durability");
        helper.assertTrue(pickaxe.get(DataComponents.TOOL) != null, "Clay Pickaxe should have a tool component");
        helper.assertTrue(pickaxe.is(ItemTags.PICKAXES), "Clay Pickaxe should be a vanilla pickaxe");
        helper.assertTrue(pickaxe.is(ItemTags.MINING_ENCHANTABLE), "Clay Pickaxe should accept mining enchantments");
        helper.assertTrue(pickaxe.is(ItemTags.DURABILITY_ENCHANTABLE), "Clay Pickaxe should accept durability enchantments");
        helper.assertTrue(pickaxe.is(ClayiumTags.Items.CLAY_TOOLS), "Clay Pickaxe should be a Clayium clay tool");
        helper.assertTrue(pickaxe.is(ClayiumTags.Items.CLAY_MINING_TOOLS), "Clay Pickaxe should be a Clayium mining tool");
        assertDestroySpeed(helper, ClayiumItems.CLAY_PICKAXE.get(), pickaxe, ClayiumBlocks.CLAY_MACHINE_HULL.get().defaultBlockState(), 4.0F, "Clay Pickaxe machine hull speed");
        helper.assertTrue(pickaxe.isCorrectToolForDrops(ClayiumBlocks.CLAY_MACHINE_HULL.get().defaultBlockState()), "Clay Pickaxe should harvest Clayium machine hulls");

        helper.assertValueEqual(wrench.getMaxStackSize(), 1, "Clay Wrench max stack size");
        helper.assertTrue(wrench.is(ClayiumTags.Items.CLAY_TOOLS), "Clay Wrench should be a Clayium clay tool");
        helper.assertTrue(wrench.is(ClayiumTags.Items.CLAY_MACHINE_TOOLS), "Clay Wrench should be a Clayium machine tool");
        helper.assertTrue(wrench.is(ClayiumTags.Items.CLAY_WRENCHES), "Clay Wrench should be a Clayium wrench");

        assertCraftingToolDurability(helper, rollingPin, 60, 4, "Clay Rolling Pin");
        assertCraftingToolDurability(helper, slicer, 60, 3, "Clay Slicer");
        assertCraftingToolDurability(helper, spatula, 36, 2, "Clay Spatula");
        helper.assertTrue(rollingPin.is(ClayiumTags.Items.CLAY_WORK_TABLE_TOOLS), "Clay Rolling Pin should be a Work Table tool");
        helper.assertTrue(rollingPin.is(ClayiumTags.Items.CLAY_ROLLING_PINS), "Clay Rolling Pin should be tagged as a rolling pin");
        helper.assertValueEqual(rawRollingPin.getMaxStackSize(), 64, "Raw Clay Rolling Pin should keep old stack size");
        helper.assertFalse(rawRollingPin.isDamageableItem(), "Raw Clay Rolling Pin should not be durable");
        helper.assertTrue(rawRollingPin.is(ClayiumTags.Items.RAW_CLAY_TOOLS), "Raw Clay Rolling Pin should be a raw clay tool");
        helper.assertFalse(rawRollingPin.is(ClayiumTags.Items.CLAY_WORK_TABLE_TOOLS), "Raw Clay Rolling Pin should not satisfy Work Table tool recipes");

        ItemStack clayPlate = new ItemStack(ClayiumItems.CLAY_PLATE.get());
        ItemStack denseClayPlate = new ItemStack(ClayiumItems.DENSE_CLAY_PLATE.get());
        helper.assertTrue(clayPlate.is(ClayiumTags.Items.CLAY_TOOL_REPAIR_MATERIALS), "Clay Plate should repair Clayium tools");
        helper.assertTrue(denseClayPlate.is(ClayiumTags.Items.CLAY_TOOL_REPAIR_MATERIALS), "Dense Clay Plate should repair Clayium tools");
        helper.succeed();
    }

    private static void assertDestroySpeed(GameTestHelper helper, Item item, ItemStack stack, BlockState state, float expected, String name) {
        float actual = item.getDestroySpeed(stack, state);
        helper.assertTrue(Math.abs(actual - expected) < 0.001F, name + " expected " + expected + " got " + actual);
    }

    private static void assertCraftingToolDurability(GameTestHelper helper, ItemStack stack, int durability, int brokenClayBallCount, String name) {
        helper.assertValueEqual(stack.getMaxStackSize(), 1, name + " max stack size");
        helper.assertValueEqual(stack.getMaxDamage(), durability, name + " durability");
        if (!(stack.getItem() instanceof ClayCraftingToolItem craftingTool)) {
            helper.fail(name + " should use ClayCraftingToolItem");
            return;
        }
        helper.assertValueEqual(craftingTool.brokenClayBallCount(), brokenClayBallCount, name + " broken clay ball count");
        ItemStack damaged = craftingTool.getAfterWorkTableUse(stack);
        assertStack(helper, damaged, stack.getItem(), 1, name + " first use keeps tool");
        helper.assertValueEqual(damaged.getDamageValue(), 1, name + " first use damage");

        ItemStack exhausted = new ItemStack(stack.getItem());
        exhausted.setDamageValue(durability);
        assertStack(helper, craftingTool.getAfterWorkTableUse(exhausted), Items.CLAY_BALL, brokenClayBallCount, name + " broken remainder");
    }

    private static void assertDropped(GameTestHelper helper, List<ItemEntity> drops, Item expectedItem, int expectedCount, String name) {
        int actual = drops.stream()
                .map(ItemEntity::getItem)
                .filter(stack -> stack.is(expectedItem))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertValueEqual(actual, expectedCount, name);
    }

    private static void assertStack(GameTestHelper helper, ItemStack stack, Item expectedItem, int expectedCount, String name) {
        if (expectedCount == 0) {
            helper.assertTrue(stack.isEmpty(), name + " should be empty");
            return;
        }
        helper.assertTrue(stack.is(expectedItem), name + " expected " + BuiltInRegistries.ITEM.getKey(expectedItem) + " got " + stack);
        helper.assertValueEqual(stack.getCount(), expectedCount, name + " count");
    }

    private static MenuFixture menuFixture(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Inventory inventory = player.getInventory();
        SimpleContainer table = new SimpleContainer(ClayWorkTableOperations.SLOT_COUNT);
        SimpleContainerData data = new SimpleContainerData(ClayWorkTableMenu.DATA_COUNT);
        ClayWorkTableMenu menu = new ClayWorkTableMenu(0, inventory, ContainerLevelAccess.NULL, table, data);
        return new MenuFixture(player, table, data, menu);
    }

    private static void click(MenuFixture fixture, int buttonId, int count) {
        for (int i = 0; i < count; i++) {
            fixture.menu.clickMenuButton(fixture.player, buttonId);
        }
    }

    private static TestDefinition test(String path, int maxTicks, Consumer<GameTestHelper> function) {
        return new TestDefinition(id(path), maxTicks, function);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Clayium.MOD_ID, path);
    }

    private record MenuFixture(
            Player player,
            Container table,
            ContainerData data,
            ClayWorkTableMenu menu
    ) {
    }

    private record TestDefinition(
            Identifier id,
            int maxTicks,
            Consumer<GameTestHelper> function
    ) {
    }
}
