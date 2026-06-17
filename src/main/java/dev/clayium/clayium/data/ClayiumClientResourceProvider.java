package dev.clayium.clayium.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.clayium.clayium.Clayium;
import dev.clayium.clayium.registry.ClayiumContentCatalog;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public final class ClayiumClientResourceProvider implements DataProvider {
    private final PackOutput.PathProvider blockStates;
    private final PackOutput.PathProvider itemDefinitions;
    private final PackOutput.PathProvider models;

    public ClayiumClientResourceProvider(PackOutput output) {
        this.blockStates = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemDefinitions = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.models = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (ClayiumContentCatalog.BlockSpec spec : ClayiumContentCatalog.blocks()) {
            futures.add(save(output, blockState(spec), this.blockStates.json(id(spec.id()))));
            futures.add(save(output, blockModel(spec), this.models.json(id("block/" + spec.id()))));
            futures.add(save(output, clientItem("clayium:block/" + spec.id()), this.itemDefinitions.json(id(spec.id()))));
        }
        for (ClayiumContentCatalog.SimpleItemSpec spec : ClayiumContentCatalog.simpleItems()) {
            saveGeneratedItem(output, futures, spec.id());
        }
        for (ClayiumContentCatalog.MaterialFormSpec spec : ClayiumContentCatalog.registeredMaterialItems()) {
            saveGeneratedItem(output, futures, spec.id());
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Clayium Client Resources";
    }

    private void saveGeneratedItem(CachedOutput output, List<CompletableFuture<?>> futures, String itemId) {
        futures.add(save(output, generatedItemModel(itemId), this.models.json(id("item/" + itemId))));
        futures.add(save(output, clientItem("clayium:item/" + itemId), this.itemDefinitions.json(id(itemId))));
    }

    private static CompletableFuture<?> save(CachedOutput output, JsonElement json, Path path) {
        return DataProvider.saveStable(output, json, path);
    }

    private static JsonObject blockState(ClayiumContentCatalog.BlockSpec spec) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        JsonObject variant = new JsonObject();
        variant.addProperty("model", "clayium:block/" + spec.id());
        variants.add("", variant);
        root.add("variants", variants);
        return root;
    }

    private static JsonObject blockModel(ClayiumContentCatalog.BlockSpec spec) {
        JsonObject root = new JsonObject();
        JsonObject textures = new JsonObject();
        if (spec.kind() == ClayiumContentCatalog.BlockKind.WORK_TABLE) {
            root.addProperty("parent", "minecraft:block/cube_bottom_top");
            textures.addProperty("bottom", "clayium:block/clay_work_table_side");
            textures.addProperty("side", "clayium:block/clay_work_table_side");
            textures.addProperty("top", "clayium:block/clay_work_table_top");
        } else {
            root.addProperty("parent", "minecraft:block/cube_all");
            textures.addProperty("all", "clayium:block/" + spec.texture());
        }
        root.add("textures", textures);
        return root;
    }

    private static JsonObject generatedItemModel(String itemId) {
        JsonObject root = new JsonObject();
        JsonObject textures = new JsonObject();
        root.addProperty("parent", "minecraft:item/generated");
        textures.addProperty("layer0", "clayium:item/" + itemId);
        root.add("textures", textures);
        return root;
    }

    private static JsonObject clientItem(String model) {
        JsonObject root = new JsonObject();
        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("type", "minecraft:model");
        modelObject.addProperty("model", model);
        root.add("model", modelObject);
        return root;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Clayium.MOD_ID, path);
    }
}
