package dev.clayium.clayium.gametest;

import com.mojang.serialization.MapCodec;
import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.block.entity.ClayWorkTableBlockEntity;
import dev.clayium.clayium.menu.ClayWorkTableMenu;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import dev.clayium.clayium.recipe.ClayWorkTableRecipeCache;
import dev.clayium.clayium.registry.ClayMaterial;
import dev.clayium.clayium.registry.ClayPartType;
import dev.clayium.clayium.registry.ClayiumBlocks;
import dev.clayium.clayium.registry.ClayiumItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
            test("phase1/block_drops_and_harvest", 120, ClayiumGameTests::blockDropsAndHarvest)
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
        assertBlockItem(helper, "dense_clay", ClayiumBlocks.DENSE_CLAY.get(), ClayiumItems.DENSE_CLAY.get());
        assertBlockItem(helper, "clay_work_table", ClayiumBlocks.CLAY_WORK_TABLE.get(), ClayiumItems.CLAY_WORK_TABLE.get());
        assertBlockItem(helper, "raw_clay_machine_hull", ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get(), ClayiumItems.RAW_CLAY_MACHINE_HULL.get());
        assertBlockItem(helper, "clay_machine_hull", ClayiumBlocks.CLAY_MACHINE_HULL.get(), ClayiumItems.CLAY_MACHINE_HULL.get());
        helper.assertValueEqual(ClayiumItems.CREATIVE_BLOCKS.size(), 4, "Phase 1 creative block count");
        helper.assertValueEqual(ClayiumItems.CREATIVE_TOOLS.size(), 6, "Phase 1 creative tool count");

        assertRegisteredItem(helper, "raw_clay_rolling_pin", ClayiumItems.RAW_CLAY_ROLLING_PIN.get());
        assertRegisteredItem(helper, "raw_clay_slicer", ClayiumItems.RAW_CLAY_SLICER.get());
        assertRegisteredItem(helper, "raw_clay_spatula", ClayiumItems.RAW_CLAY_SPATULA.get());
        assertRegisteredItem(helper, "clay_rolling_pin", ClayiumItems.CLAY_ROLLING_PIN.get());
        assertRegisteredItem(helper, "clay_slicer", ClayiumItems.CLAY_SLICER.get());
        assertRegisteredItem(helper, "clay_spatula", ClayiumItems.CLAY_SPATULA.get());

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

        assertSmeltingRecipe(helper, "clay_rolling_pin_from_smelting", ClayiumItems.CLAY_ROLLING_PIN.get());
        assertSmeltingRecipe(helper, "clay_slicer_from_smelting", ClayiumItems.CLAY_SLICER.get());
        assertSmeltingRecipe(helper, "clay_spatula_from_smelting", ClayiumItems.CLAY_SPATULA.get());
        assertSmeltingRecipe(helper, "clay_machine_hull_from_smelting", ClayiumItems.CLAY_MACHINE_HULL.get());
        helper.succeed();
    }

    private static void blockDropsAndHarvest(GameTestHelper helper) {
        assertBlockDropsSelf(helper, ClayiumBlocks.DENSE_CLAY.get(), ClayiumItems.DENSE_CLAY.get());
        assertBlockDropsSelf(helper, ClayiumBlocks.CLAY_WORK_TABLE.get(), ClayiumItems.CLAY_WORK_TABLE.get());
        assertBlockDropsSelf(helper, ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get(), ClayiumItems.RAW_CLAY_MACHINE_HULL.get());
        assertBlockDropsSelf(helper, ClayiumBlocks.CLAY_MACHINE_HULL.get(), ClayiumItems.CLAY_MACHINE_HULL.get());

        assertToolHarvest(helper, ClayiumBlocks.DENSE_CLAY.get().defaultBlockState(), BlockTags.MINEABLE_WITH_SHOVEL, Items.WOODEN_SHOVEL, "dense clay");
        assertToolHarvest(helper, ClayiumBlocks.RAW_CLAY_MACHINE_HULL.get().defaultBlockState(), BlockTags.MINEABLE_WITH_SHOVEL, Items.WOODEN_SHOVEL, "raw clay machine hull");
        assertToolHarvest(helper, ClayiumBlocks.CLAY_MACHINE_HULL.get().defaultBlockState(), BlockTags.MINEABLE_WITH_PICKAXE, Items.WOODEN_PICKAXE, "clay machine hull");
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
