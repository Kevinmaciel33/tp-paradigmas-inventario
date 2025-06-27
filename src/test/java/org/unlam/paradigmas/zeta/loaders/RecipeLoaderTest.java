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
        assertEquals(2, r.getLibraries().size());
        for (var l : r.getLibraries()) {
            assertFalse(l.recipes().isEmpty());
        }

        for (var library : r.getLibraries()) {
            for (var rr : library.recipes()) {
                assertFalse(rr.ingredients().isEmpty(), "La receta debe tener al menos un ingrediente");
                assertFalse(rr.give().name().isEmpty(), "Debe tener un nombre");
            }
        }
    }
}
