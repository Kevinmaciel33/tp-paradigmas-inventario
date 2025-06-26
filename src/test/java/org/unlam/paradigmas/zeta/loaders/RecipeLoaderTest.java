package org.unlam.paradigmas.zeta.loaders;

import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeLoaderTest {
    Loader<RecipeBook> loader = new RecipeLoader();

    @Test
    void verifyLoadedRecipesCountAndIntegrity() {
        loader.loadFile();
        RecipeBook r = RecipeLoader.getData();
        assertEquals(16, r.getLibraries().size(), "Se esperaban 16 recetas cargadas");

        for (var library : r.getLibraries()) {
            Recipe recipe = library.recipe();
            assertFalse(recipe.ingredients().isEmpty(), "La receta debe tener al menos un ingrediente");
            assertFalse(recipe.give().name().isEmpty(), "Debe tener un nombre");
        }
    }
}
