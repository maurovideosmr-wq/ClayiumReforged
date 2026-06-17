package dev.clayium.clayium.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PartRegistry {
    private final EnumMap<ClayMaterial, EnumMap<ClayPartType, DeferredItem<Item>>> parts;
    private final List<DeferredItem<Item>> items;

    private PartRegistry(EnumMap<ClayMaterial, EnumMap<ClayPartType, DeferredItem<Item>>> parts, List<DeferredItem<Item>> items) {
        this.parts = parts;
        this.items = Collections.unmodifiableList(items);
    }

    static PartRegistry register(DeferredRegister.Items items, Consumer<DeferredItem<Item>> creativeTracker) {
        EnumMap<ClayMaterial, EnumMap<ClayPartType, DeferredItem<Item>>> parts = new EnumMap<>(ClayMaterial.class);
        List<DeferredItem<Item>> registeredItems = new ArrayList<>();
        for (ClayMaterial material : ClayMaterial.values()) {
            parts.put(material, new EnumMap<>(ClayPartType.class));
        }
        for (ClayiumContentCatalog.MaterialFormSpec spec : ClayiumContentCatalog.registeredMaterialItems()) {
            DeferredItem<Item> item = items.registerSimpleItem(spec.id());
            parts.get(spec.material()).put(spec.partType(), item);
            registeredItems.add(item);
            creativeTracker.accept(item);
        }
        return new PartRegistry(parts, registeredItems);
    }

    public DeferredItem<Item> get(ClayMaterial material, ClayPartType partType) {
        DeferredItem<Item> item = this.parts.get(material).get(partType);
        if (item == null) {
            throw new IllegalArgumentException(material + " does not define part " + partType);
        }
        return item;
    }

    public boolean contains(ClayMaterial material, ClayPartType partType) {
        return this.parts.get(material).containsKey(partType);
    }

    public List<DeferredItem<Item>> items() {
        return this.items;
    }
}
