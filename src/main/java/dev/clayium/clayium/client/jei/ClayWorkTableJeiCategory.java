package dev.clayium.clayium.client.jei;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayiumItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

final class ClayWorkTableJeiCategory implements IRecipeCategory<ClayWorkTableJeiRecipe> {
    private static final int WIDTH = 176;
    private static final int HEIGHT = 62;
    private static final int TEXTURE_WIDTH = 96;
    private static final int TEXTURE_HEIGHT = 80;

    private static final int PANEL_COLOR = 0xFFC6C6C6;
    private static final int PANEL_EDGE_DARK = 0xFF2A2A2A;
    private static final int PANEL_EDGE_LIGHT = 0xFFE8E8E8;
    private static final int PANEL_EDGE_SHADOW = 0xFF747474;
    private static final int SLOT_COLOR = 0xFF8A8A8A;
    private static final int SLOT_EDGE_DARK = 0xFF3F3F3F;
    private static final int SLOT_EDGE_LIGHT = 0xFFE2E2E2;
    private static final int TEXT_COLOR = 0xFF2A2A2A;

    private static final int INPUT_SLOT_X = 17;
    private static final int INPUT_SLOT_Y = 16;
    private static final int TOOL_SLOT_X = 80;
    private static final int TOOL_SLOT_Y = 3;
    private static final int OUTPUT_SLOT_X = 143;
    private static final int OUTPUT_SLOT_Y = 16;
    private static final int BYPRODUCT_SLOT_X = 143;
    private static final int BYPRODUCT_SLOT_Y = 41;

    private static final int INPUT_FRAME_X = 12;
    private static final int INPUT_FRAME_Y = 11;
    private static final int LARGE_FRAME_SIZE = 26;
    private static final int TOOL_FRAME_X = 79;
    private static final int TOOL_FRAME_Y = 2;
    private static final int OUTPUT_FRAME_X = 138;
    private static final int OUTPUT_FRAME_Y = 11;
    private static final int BYPRODUCT_FRAME_X = 142;
    private static final int BYPRODUCT_FRAME_Y = 40;
    private static final int SMALL_FRAME_SIZE = 18;

    private static final int ARROW_X = 48;
    private static final int ARROW_Y = 18;
    private static final int ARROW_WIDTH = 80;
    private static final int ARROW_HEIGHT = 16;

    private static final int BUTTON_X = 40;
    private static final int BUTTON_Y = 42;
    private static final int BUTTON_LABEL_Y = 33;
    private static final int BUTTON_SIZE = 16;
    private static final int BUTTON_COUNT = 6;
    private static final int BUTTON_DISABLED_ROW = 0;
    private static final int BUTTON_AVAILABLE_ROW = 1;

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(Clayium.MOD_ID, "textures/gui/container/clay_work_table.png");
    private static final Component TITLE = Component.translatable("jei.clayium.category.clay_work_table");

    private final IDrawable icon;
    private final IDrawableStatic arrowBackground;
    private final IDrawableAnimated arrowProgress;
    private final IDrawableStatic[][] buttons;

    ClayWorkTableJeiCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ClayiumItems.CLAY_WORK_TABLE.get()));
        this.arrowBackground = drawable(guiHelper, 0, 16, ARROW_WIDTH, ARROW_HEIGHT);
        IDrawableStatic arrowProgressTexture = drawable(guiHelper, 0, 0, ARROW_WIDTH, ARROW_HEIGHT);
        this.arrowProgress = guiHelper.createAnimatedDrawable(arrowProgressTexture, 40, StartDirection.LEFT, false);
        this.buttons = createButtons(guiHelper);
    }

    @Override
    public IRecipeType<ClayWorkTableJeiRecipe> getRecipeType() {
        return ClayiumJeiPlugin.CLAY_WORK_TABLE;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ClayWorkTableJeiRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, INPUT_SLOT_X, INPUT_SLOT_Y)
                .setSlotName("input")
                .addItemStacks(recipe.inputs());
        if (!recipe.tools().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, TOOL_SLOT_X, TOOL_SLOT_Y)
                    .setSlotName("tool")
                    .addItemStacks(recipe.tools());
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_SLOT_X, OUTPUT_SLOT_Y)
                .setSlotName("output")
                .add(recipe.mainOutput());
        if (recipe.hasByproductOutput()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, BYPRODUCT_SLOT_X, BYPRODUCT_SLOT_Y)
                    .setSlotName("byproduct")
                    .add(recipe.byproductOutput());
        }
    }

    @Override
    public void draw(ClayWorkTableJeiRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor graphics, double mouseX, double mouseY) {
        drawPanel(graphics, 0, 0, WIDTH, HEIGHT);
        drawSlot(graphics, INPUT_FRAME_X, INPUT_FRAME_Y, LARGE_FRAME_SIZE, LARGE_FRAME_SIZE);
        drawSlot(graphics, TOOL_FRAME_X, TOOL_FRAME_Y, SMALL_FRAME_SIZE, SMALL_FRAME_SIZE);
        drawSlot(graphics, OUTPUT_FRAME_X, OUTPUT_FRAME_Y, LARGE_FRAME_SIZE, LARGE_FRAME_SIZE);
        drawSlot(graphics, BYPRODUCT_FRAME_X, BYPRODUCT_FRAME_Y, SMALL_FRAME_SIZE, SMALL_FRAME_SIZE);

        this.arrowBackground.draw(graphics, ARROW_X, ARROW_Y);
        this.arrowProgress.draw(graphics, ARROW_X, ARROW_Y);
        for (int buttonId = 1; buttonId <= BUTTON_COUNT; buttonId++) {
            int row = buttonId == recipe.buttonId() ? BUTTON_AVAILABLE_ROW : BUTTON_DISABLED_ROW;
            this.buttons[row][buttonId - 1].draw(graphics, buttonX(buttonId), BUTTON_Y);
        }

        Font font = Minecraft.getInstance().font;
        String workTicks = Integer.toString(recipe.workTicks());
        int textX = buttonX(recipe.buttonId()) + BUTTON_SIZE / 2 - font.width(workTicks) / 2;
        graphics.text(font, workTicks, textX, BUTTON_LABEL_Y, TEXT_COLOR, false);
    }

    @Override
    public Identifier getIdentifier(ClayWorkTableJeiRecipe recipe) {
        return recipe.id();
    }

    @Override
    public boolean needsRecipeBorder() {
        return false;
    }

    private static IDrawableStatic[][] createButtons(IGuiHelper guiHelper) {
        IDrawableStatic[][] result = new IDrawableStatic[2][BUTTON_COUNT];
        for (int row = 0; row < result.length; row++) {
            for (int column = 0; column < BUTTON_COUNT; column++) {
                result[row][column] = drawable(guiHelper, column * BUTTON_SIZE, 32 + row * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
        }
        return result;
    }

    private static IDrawableStatic drawable(IGuiHelper guiHelper, int x, int y, int width, int height) {
        return guiHelper.drawableBuilder(GUI_TEXTURE, x, y, width, height)
                .setTextureSize(TEXTURE_WIDTH, TEXTURE_HEIGHT)
                .build();
    }

    private static int buttonX(int buttonId) {
        return BUTTON_X + (buttonId - 1) * BUTTON_SIZE;
    }

    private static void drawPanel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, PANEL_COLOR);
        graphics.outline(x, y, width, height, PANEL_EDGE_DARK);
        graphics.fill(x + 1, y + 1, x + width - 1, y + 2, PANEL_EDGE_LIGHT);
        graphics.fill(x + 1, y + 1, x + 2, y + height - 1, PANEL_EDGE_LIGHT);
        graphics.fill(x + 1, y + height - 2, x + width - 1, y + height - 1, PANEL_EDGE_SHADOW);
        graphics.fill(x + width - 2, y + 1, x + width - 1, y + height - 1, PANEL_EDGE_SHADOW);
    }

    private static void drawSlot(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, SLOT_EDGE_LIGHT);
        graphics.fill(x, y, x + width - 1, y + height - 1, SLOT_EDGE_DARK);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, SLOT_COLOR);
        graphics.fill(x + width - 1, y + 1, x + width, y + height, SLOT_EDGE_LIGHT);
        graphics.fill(x + 1, y + height - 1, x + width, y + height, SLOT_EDGE_LIGHT);
    }
}
