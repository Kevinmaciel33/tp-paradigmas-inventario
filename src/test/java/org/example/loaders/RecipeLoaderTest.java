package org.example.loaders;

import org.example.Inventory;
import org.example.RecipeBook;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeLoaderTest {
    Loader<RecipeBook> loader = new RecipeLoader();

    @Test
    void checkRecipeBookLoaderFromTestResource() {
        loader.loadFile();
        RecipeBook r = RecipeLoader.getData();
        assertEquals("BASE", r.libraries.get(1).originTable());
        assertEquals("AGUITA", r.libraries.get(1).recipe().give().name());
        assertEquals(3, r.libraries.get(1).recipe().ingredients().size());

        assertEquals("NUEVA_TABLA", r.libraries.get(0).originTable());
        assertEquals("AGUITA", r.libraries.get(0).recipe().give().name());
        assertEquals(3, r.libraries.get(0).recipe().ingredients().size());

    }
}
