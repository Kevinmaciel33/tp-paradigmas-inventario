package org.example.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.RecipeBook;
import org.example.enums.Classification;
import org.example.models.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeLoader implements Loader<RecipeBook> {

    private static final String PATH = "recipes.json";
    private static final List<Library> libraries = new ArrayList<>();

    public void loadFile() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);

        ObjectMapper mapper = new ObjectMapper();
        try {
            RecipeJson[] recetas = mapper.readValue(stream, RecipeJson[].class);

            for (RecipeJson r : recetas) {
                Classification type = Classification.valueOf(r.type.toUpperCase(Locale.ROOT));
                Element product = new Element(r.give.toUpperCase(Locale.ROOT), type);

                List<Element> ingredients = new ArrayList<>();
                for (String ing : r.elements) {
                    ingredients.add(new Element(ing.toUpperCase(Locale.ROOT), Classification.ALL));
                }

                Recipe recipe = new Recipe(product, r.time, ingredients);
                //recipe.mostrarReceta(product, ingredients); // mostrar receta

                libraries.add(new Library(r.table.toUpperCase(Locale.ROOT), recipe));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

    public static RecipeBook getData() {
        return new RecipeBook(libraries);
    }
}