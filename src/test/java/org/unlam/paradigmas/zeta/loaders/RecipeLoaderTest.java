package org.unlam.paradigmas.zeta.loaders;

import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeLoaderTest {
    Loader<RecipeBook> loader = new RecipeLoader();

    @Test
    void verifyLoadedRecipesCountAndIntegrity() {
        loader.loadFile();
        RecipeBook r = RecipeLoader.getData();
      
        assertEquals(16, r.libraries.size(), "Se esperaban 16 recetas cargadas");

        for (var library : r.libraries) {
            Recipe recipe = library.recipe();
            assertTrue(recipe.ingredients().size() > 0, "La receta debe tener al menos un ingrediente");
            assertTrue(recipe.give().name().length() > 0, "Debe tener un nombre");
        }
    }
}
