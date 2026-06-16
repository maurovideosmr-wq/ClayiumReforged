/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.resources.I18n
 */
package mods.clayium.gui.client;

import mods.clayium.block.tile.TileClayRFGenerator;
import mods.clayium.gui.client.GuiClayEnergyTemp;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;

public class GuiClayRFGenerator
extends GuiClayEnergyTemp {
    public GuiClayRFGenerator(ContainerTemp container, TileClayRFGenerator tile, Block block) {
        super(container, tile, block);
    }

    @Override
    protected void func_146979_b(int mouseX, int mouseZ) {
        super.func_146979_b(mouseX, mouseZ);
        ContainerTemp container = (ContainerTemp)this.field_147002_h;
        TileClayRFGenerator tileg = (TileClayRFGenerator)this.tile;
        this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.RFGenerator.storage", (Object[])new Object[]{UtilLocale.rfNumeral(tileg.rfStored), UtilLocale.rfNumeral(tileg.maxRfStored)}), 12, 20, 0x404040);
        this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.RFGenerator.convertRate", (Object[])new Object[]{UtilLocale.ClayEnergyNumeral(tileg.ceConsumptionPerTick), UtilLocale.rfNumeral(tileg.rfProductionPerTick)}), 12, 40, 0x404040);
        this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.RFGenerator.output", (Object[])new Object[]{UtilLocale.rfNumeral(tileg.rfOutputPerTick)}), 12, 52, 0x404040);
        if (tileg.overclockExponent == 1.0) {
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.RFGenerator.overclock", (Object[])new Object[]{UtilLocale.CAResonanceNumeral(tileg.overclockTotalFactor)}), 12, 64, 0x404040);
        } else {
            this.field_146289_q.func_78276_b(I18n.func_135052_a((String)"gui.RFGenerator.overclockExponen", (Object[])new Object[]{UtilLocale.CAResonanceNumeral(tileg.overclockTotalFactor), UtilLocale.CAResonanceNumeral(tileg.overclockExponent), UtilLocale.CAResonanceNumeral(Math.pow(tileg.overclockTotalFactor, tileg.overclockExponent))}), 12, 64, 0x404040);
        }
    }
}

