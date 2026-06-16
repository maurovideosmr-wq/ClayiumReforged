package dev.clayium.clayium.client.screen;

import dev.clayium.clayium.menu.ClayWorkTableMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ClayWorkTableScreen extends AbstractContainerScreen<ClayWorkTableMenu> {
    public ClayWorkTableScreen(ClayWorkTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 176);
        this.inventoryLabelY = 82;
    }

    @Override
    protected void init() {
        super.init();
        for (int button = 1; button <= 6; button++) {
            int index = button - 1;
            int x = this.leftPos + 75 + (index % 3) * 20;
            int y = this.topPos + 33 + (index / 3) * 22;
            int buttonId = button;
            this.addRenderableWidget(Button.builder(Component.literal(Integer.toString(button)), ignored -> this.sendWorkButton(buttonId))
                    .bounds(x, y, 18, 18)
                    .build());
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int left = this.leftPos;
        int top = this.topPos;
        graphics.fill(left, top, left + this.imageWidth, top + this.imageHeight, 0xFFBFA27C);
        graphics.fill(left + 6, top + 16, left + this.imageWidth - 6, top + 78, 0xFF8F7252);
        graphics.fill(left + 6, top + 90, left + this.imageWidth - 6, top + this.imageHeight - 6, 0xFFE2CFB5);

        int lastButtonId = this.menu.getLastButtonId();
        if (lastButtonId > 0) {
            graphics.text(this.font, Component.translatable("container.clayium.clay_work_table.last_action", lastButtonId, this.menu.getLastWorkTicks()), left + 74, top + 59, 0xFF2B1D12, false);
        }
    }

    private void sendWorkButton(int buttonId) {
        if (this.minecraft != null && this.minecraft.gameMode != null) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }
    }
}
