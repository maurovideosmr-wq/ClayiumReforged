/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.resources.I18n
 */
package mods.clayium.gui.client;

import mods.clayium.block.tile.TileCAMachines;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.gui.client.GuiTemp;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;

public class GuiClayContainerTemp
extends GuiTemp {
    protected TileClayContainer tile;
    protected Block block;

    public GuiClayContainerTemp(ContainerTemp container, TileClayContainer tile, Block block) {
        super(container);
        this.tile = tile;
        this.block = block;
        this.setGuiTitle(this.tile.func_145818_k_() ? this.tile.func_145825_b() : I18n.func_135052_a((String)this.tile.func_145825_b(), (Object[])new Object[0]));
    }

    @Override
    public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawClayEnergyItem(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    protected void func_146979_b(int mouseX, int mouseZ) {
        super.func_146979_b(mouseX, mouseZ);
        this.drawClayEnergyForegroundLayer();
        this.drawResonanceForegroundLayer();
        this.drawOverclockFactorForegroundLayer();
    }

    protected void drawClayEnergyForegroundLayer() {
    }

    protected void drawClayEnergyItem(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
    }

    protected void drawResonanceForegroundLayer() {
        if (this.tile instanceof TileCAMachines) {
            ContainerTemp container = (ContainerTemp)this.field_147002_h;
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.Common.resonance", (Object[])new Object[]{UtilLocale.CAResonanceNumeral(((TileCAMachines)this.tile).getResonance())}), 80, container.machineGuiSizeY - 12, 0x404040);
        }
    }

    protected void drawOverclockFactorForegroundLayer() {
        if (this.tile instanceof TileClayMachines && ((TileClayMachines)this.tile).overclockTotalFactor != 1.0) {
            ContainerTemp container = (ContainerTemp)this.field_147002_h;
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.Common.overclockFactor", (Object[])new Object[]{UtilLocale.CAResonanceNumeral(((TileClayMachines)this.tile).overclockTotalFactor)}), 80, container.machineGuiSizeY, 0x404040);
        }
    }
}

