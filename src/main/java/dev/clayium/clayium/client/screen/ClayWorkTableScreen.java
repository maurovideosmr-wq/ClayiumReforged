package dev.clayium.clayium.client.screen;

import com.lowdragmc.lowdraglib2.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib2.gui.texture.SpriteTexture;
import com.lowdragmc.lowdraglib2.gui.ui.rendering.GUIContext;
import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.menu.ClayWorkTableMenu;
import dev.clayium.clayium.menu.ClayWorkTableOperations;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ClayWorkTableScreen extends AbstractContainerScreen<ClayWorkTableMenu> {
    private static final int GUI_WIDTH = 176;
    private static final int GUI_HEIGHT = 166;

    private static final int PANEL_COLOR = 0xFFC6C6C6;
    private static final int PANEL_EDGE_DARK = 0xFF2A2A2A;
    private static final int PANEL_EDGE_LIGHT = 0xFFE8E8E8;
    private static final int PANEL_EDGE_SHADOW = 0xFF747474;
    private static final int SLOT_COLOR = 0xFF8A8A8A;
    private static final int SLOT_EDGE_DARK = 0xFF3F3F3F;
    private static final int SLOT_EDGE_LIGHT = 0xFFE2E2E2;
    private static final int GHOST_OVERLAY = 0x66FFFFFF;
    private static final int GHOST_SLOT_OUTLINE = 0xB0FFD84D;

    private static final int ARROW_X = 48;
    private static final int ARROW_Y = 29;
    private static final int ARROW_WIDTH = 80;
    private static final int ARROW_HEIGHT = 16;

    private static final int BUTTON_X = 40;
    private static final int BUTTON_Y = 52;
    private static final int BUTTON_SIZE = 16;
    private static final int BUTTON_COUNT = 6;
    private static final int BUTTON_DISABLED_ROW = 0;
    private static final int BUTTON_AVAILABLE_ROW = 1;
    private static final int BUTTON_HOVER_ROW = 2;

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(Clayium.MOD_ID, "textures/gui/container/clay_work_table.png");
    private static final IGuiTexture ARROW_PROGRESS = sprite(0, 0, ARROW_WIDTH, ARROW_HEIGHT);
    private static final IGuiTexture ARROW_BACKGROUND = sprite(0, 16, ARROW_WIDTH, ARROW_HEIGHT);
    private static final IGuiTexture[][] BUTTON_TEXTURES = buttonTextures();
    private static final Component[] BUTTON_TOOLTIPS = {
            Component.translatable("container.clayium.clay_work_table.action.hand_knead"),
            Component.translatable("container.clayium.clay_work_table.action.hand_press"),
            Component.translatable("container.clayium.clay_work_table.action.rolling_pin"),
            Component.translatable("container.clayium.clay_work_table.action.cut_plate"),
            Component.translatable("container.clayium.clay_work_table.action.cut_round"),
            Component.translatable("container.clayium.clay_work_table.action.cut_strips")
    };

    public ClayWorkTableScreen(ClayWorkTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_WIDTH, GUI_HEIGHT);
        this.titleLabelX = 6;
        this.titleLabelY = 5;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 72;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int left = this.leftPos;
        int top = this.topPos;
        GUIContext context = GUIContext.of(graphics, mouseX, mouseY, partialTick);

        drawPanel(graphics, left, top, this.imageWidth, this.imageHeight);

        drawSlot(graphics, left + 12, top + 25, 26, 26);
        drawSlot(graphics, left + 79, top + 16, 18, 18);
        drawSlot(graphics, left + 138, top + 25, 26, 26);
        drawSlot(graphics, left + 142, top + 54, 18, 18);
        drawPlayerInventorySlots(graphics, left, top);

        context.drawTexture(ARROW_BACKGROUND, left + ARROW_X, top + ARROW_Y, ARROW_WIDTH, ARROW_HEIGHT);
        int progressWidth = this.menu.getCookProgressScaled(ARROW_WIDTH);
        if (progressWidth > 0) {
            graphics.enableScissor(left + ARROW_X, top + ARROW_Y, left + ARROW_X + progressWidth, top + ARROW_Y + ARROW_HEIGHT);
            context.drawTexture(ARROW_PROGRESS, left + ARROW_X, top + ARROW_Y, ARROW_WIDTH, ARROW_HEIGHT);
            graphics.disableScissor();
        }

        int hoveredButton = this.getButtonAt(mouseX, mouseY);
        for (int buttonId = 1; buttonId <= BUTTON_COUNT; buttonId++) {
            context.drawTexture(this.getButtonTexture(buttonId, hoveredButton), left + buttonX(buttonId), top + BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE);
        }
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractContents(graphics, mouseX, mouseY, partialTick);
        this.drawGhostOutputs(graphics, mouseX, mouseY);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        int buttonId = this.getButtonAt(mouseX, mouseY);
        if (buttonId > 0) {
            List<ItemStack> outputs = this.menu.getPreviewOutputs(buttonId);
            if (outputs.isEmpty()) {
                graphics.setTooltipForNextFrame(this.font, BUTTON_TOOLTIPS[buttonId - 1], mouseX, mouseY);
            } else {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(BUTTON_TOOLTIPS[buttonId - 1]);
                for (ItemStack output : outputs) {
                    tooltip.add(Component.literal("-> ").append(output.getHoverName()));
                }
                graphics.setComponentTooltipForNextFrame(this.font, tooltip, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            int buttonId = this.getButtonAt(event.x(), event.y());
            if (buttonId > 0) {
                if (this.menu.canProcessButton(buttonId)) {
                    this.sendWorkButton(buttonId);
                    if (this.minecraft != null) {
                        AbstractWidget.playButtonClickSound(this.minecraft.getSoundManager());
                    }
                }
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    private IGuiTexture getButtonTexture(int buttonId, int hoveredButton) {
        int row;
        if (this.menu.canProcessButton(buttonId)) {
            row = hoveredButton == buttonId ? BUTTON_HOVER_ROW : BUTTON_AVAILABLE_ROW;
        } else {
            row = BUTTON_DISABLED_ROW;
        }
        return BUTTON_TEXTURES[row][buttonId - 1];
    }

    private int getButtonAt(double mouseX, double mouseY) {
        int localX = (int) mouseX - this.leftPos;
        int localY = (int) mouseY - this.topPos;
        if (localY < BUTTON_Y || localY >= BUTTON_Y + BUTTON_SIZE) {
            return 0;
        }
        if (localX < BUTTON_X || localX >= BUTTON_X + BUTTON_COUNT * BUTTON_SIZE) {
            return 0;
        }
        return (localX - BUTTON_X) / BUTTON_SIZE + 1;
    }

    private void sendWorkButton(int buttonId) {
        if (this.minecraft != null && this.minecraft.gameMode != null) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }
    }

    private static int buttonX(int buttonId) {
        return BUTTON_X + (buttonId - 1) * BUTTON_SIZE;
    }

    private void drawGhostOutputs(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int buttonId = this.getButtonAt(mouseX, mouseY);
        if (buttonId <= 0 || !this.menu.canProcessButton(buttonId)) {
            return;
        }
        List<ItemStack> outputs = this.menu.getPreviewOutputs(buttonId);
        for (int index = 0; index < outputs.size() && index < ClayWorkTableOperations.OUTPUT_SLOT_COUNT; index++) {
            ItemStack output = outputs.get(index);
            Slot slot = this.menu.getSlot(ClayWorkTableOperations.FIRST_OUTPUT_SLOT + index);
            int x = this.leftPos + slot.x;
            int y = this.topPos + slot.y;
            if (slot.hasItem()) {
                graphics.outline(x - 1, y - 1, 18, 18, GHOST_SLOT_OUTLINE);
            } else {
                graphics.fakeItem(output, x, y);
                graphics.fill(x, y, x + 16, y + 16, GHOST_OVERLAY);
                graphics.itemDecorations(this.font, output, x, y);
            }
        }
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

    private static void drawPlayerInventorySlots(GuiGraphicsExtractor graphics, int left, int top) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                drawSlot(graphics, left + 7 + column * 18, top + 83 + row * 18, 18, 18);
            }
        }
        for (int column = 0; column < 9; column++) {
            drawSlot(graphics, left + 7 + column * 18, top + 141, 18, 18);
        }
    }

    private static IGuiTexture[][] buttonTextures() {
        IGuiTexture[][] textures = new IGuiTexture[3][BUTTON_COUNT];
        for (int row = 0; row < textures.length; row++) {
            for (int column = 0; column < BUTTON_COUNT; column++) {
                textures[row][column] = sprite(column * BUTTON_SIZE, 32 + row * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
        }
        return textures;
    }

    private static IGuiTexture sprite(int x, int y, int width, int height) {
        return SpriteTexture.of(GUI_TEXTURE).setSprite(x, y, width, height);
    }
}
