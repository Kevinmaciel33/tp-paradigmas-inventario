package org.unlam.paradigmas.zeta.loaders;

import org.unlam.paradigmas.zeta.RecipeBook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeLoaderTest {
    Loader<RecipeBook> loader = new RecipeLoader();

    @Test
    void checkRecipeBookLoaderFromTestResource() {
        loader.loadFile();
        RecipeBook r = RecipeLoader.getData();
        assertEquals("BASE", r.getLibraries().get(1).originTable());
        assertEquals("DIOXIDO_CARBONO", r.getLibraries().get(1).recipe().give().name());
        assertEquals(3, r.getLibraries().get(1).recipe().ingredients().size());

        assertEquals("BASE", r.getLibraries().get(0).originTable());
        assertEquals("AGUITA", r.getLibraries().get(0).recipe().give().name());
        assertEquals(3, r.getLibraries().get(0).recipe().ingredients().size());
    }
}
