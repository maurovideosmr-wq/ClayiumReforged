package dev.clayium.clayium.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeResourceFormatTest {
    @Test
    void furnaceRecipesUseMinecraft26StringIngredients() throws IOException {
        assertUsesStringIngredient("clay_rolling_pin_from_smelting.json", "clayium:raw_clay_rolling_pin");
        assertUsesStringIngredient("clay_slicer_from_smelting.json", "clayium:raw_clay_slicer");
        assertUsesStringIngredient("clay_spatula_from_smelting.json", "clayium:raw_clay_spatula");
        assertUsesStringIngredient("clay_machine_hull_from_smelting.json", "clayium:raw_clay_machine_hull");
    }

    private static void assertUsesStringIngredient(String fileName, String ingredientId) throws IOException {
        URL resource = RecipeResourceFormatTest.class.getClassLoader().getResource("data/clayium/recipe/" + fileName);
        assertNotNull(resource, fileName);
        String json;
        try (InputStream stream = resource.openStream()) {
            json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }

        assertTrue(json.contains("\"ingredient\": \"" + ingredientId + "\""));
        assertFalse(json.contains("\"ingredient\": {"));
    }
}
