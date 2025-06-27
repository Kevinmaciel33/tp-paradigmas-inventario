package org.unlam.paradigmas.zeta.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.io.InputStream;
import java.util.*;

public class RecipeLoader implements Loader<RecipeBook> {

    private static final String PATH = "recipes.json";
    private static final List<Library> libraries = new ArrayList<>();

    public void loadFile() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);

        ObjectMapper mapper = new ObjectMapper();
        try {
            RecipeJson[] recetas = mapper.readValue(stream, RecipeJson[].class);
            Map<String, List<Recipe>> mapa = new HashMap<>();

            for (RecipeJson r : recetas) {
                Classification type = Classification.valueOf(r.type.toUpperCase(Locale.ROOT));
                Element product = new Element(r.give.toUpperCase(Locale.ROOT), type);

                List<Element> ingredients = new ArrayList<>();
                for (String ing : r.elements) {
                    ingredients.add(new Element(ing.toUpperCase(Locale.ROOT)));
                }

                Recipe recipe = new Recipe(product, r.time, ingredients);
                var recipes = mapa.getOrDefault(r.table.toUpperCase(Locale.ROOT), new ArrayList<>());
                recipes.add(recipe);
                mapa.put(r.table.toUpperCase(Locale.ROOT), recipes);
            }

            for ( String k : mapa.keySet() ) {
                libraries.add(new Library(k, mapa.get(k)));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

    public static RecipeBook getData() {
        return new RecipeBook(libraries);
    }
}