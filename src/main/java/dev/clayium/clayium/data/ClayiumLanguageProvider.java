package dev.clayium.clayium.data;

import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import java.util.Map;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ClayiumLanguageProvider extends LanguageProvider {
    private static final Map<String, String> ACTION_NAMES = Map.of(
            "hand_knead", "Hand knead",
            "hand_press", "Hand press",
            "rolling_pin", "Rolling pin",
            "cut_plate", "Cut plate",
            "cut_round", "Cut round",
            "cut_strips", "Cut strips"
    );
    private static final Map<String, String> ITEM_TOOLTIPS = Map.of(
            "clay_shovel", "Can dig Clay Ores and increase drops.",
            "clay_pickaxe", "Can harvest Clay Ores so fast and increase drops.",
            "clay_wrench", "Rotates machines."
    );

    public ClayiumLanguageProvider(PackOutput output) {
        super(output, Clayium.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.clayium.main", "Clayium Reforged");
        this.add("jei.clayium.category.clay_work_table", "Clay Work Table");
        this.add("container.clayium.clay_work_table", "Clay Work Table");
        ACTION_NAMES.forEach((id, name) -> this.add("container.clayium.clay_work_table.action." + id, name));
        ITEM_TOOLTIPS.forEach((id, tooltip) -> this.add("item.clayium." + id + ".tooltip", tooltip));

        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            this.add("block.clayium." + spec.id(), spec.translation());
        }
        for (ClayiumContentCatalog.SimpleItemSpec spec : ClayiumContentCatalog.simpleItems()) {
            this.add("item.clayium." + spec.id(), spec.translation());
        }
        for (ClayiumContentCatalog.MaterialFormSpec spec : ClayiumContentCatalog.registeredMaterialItems()) {
            this.add("item.clayium." + spec.id(), spec.translation());
        }
    }
}
