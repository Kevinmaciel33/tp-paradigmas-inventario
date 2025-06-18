package org.example.loaders;

import org.example.Inventory;
import org.example.RecipeBook;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeLoaderTest {
    Loader<RecipeBook> loader = new RecipeLoader();

    @Test
    void checkRecipeBookLoaderFromTestResource() {
        loader.loadFile();
        RecipeBook r = RecipeLoader.getData();

        //Este test fallaria si se agregan mas recetas..
        //Por lo que entiendo cada receta se carga en un map y no respeta 
        //..el indice de insercion
        /* assertEquals("BASE", r.libraries.get(1).originTable());
        assertEquals("AGUITA", r.libraries.get(1).recipe().give().name());
        assertEquals(3, r.libraries.get(1).recipe().ingredients().size());

        assertEquals("NUEVA_TABLA", r.libraries.get(0).originTable());
        assertEquals("AGUITA", r.libraries.get(0).recipe().give().name());
        assertEquals(3, r.libraries.get(0).recipe().ingredients().size()); */

        
        //Mueestro todas las recetas cargadas en el libro de recetas
        int count = 0;

        for (var library : r.libraries) {
            Recipe recipe = library.recipe();
            assertTrue(recipe.ingredients().size() > 0, "La receta debe tener al menos un ingrediente");
            assertTrue(recipe.give().name().length() > 0, "El nombre del producto debe ser no vacio");

            System.out.println("Receta: " + recipe.give().name());
            System.out.println("Tipo: " + recipe.give().type());
            System.out.println("Ingredientes:");
            for (Element ingredient : recipe.ingredients()) {
                System.out.println("- " + ingredient.name() + " (" + ingredient.type() + ")");
            }
            System.out.println("------------------------------");
            count++;
        }
        
        assertEquals(19, count,
                "El numero de recetas cargadas debe ser 15, pero se encontraron " + count);

    }
}
