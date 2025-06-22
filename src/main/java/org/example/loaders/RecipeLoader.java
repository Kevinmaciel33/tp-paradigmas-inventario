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

                    String mesa = columns[0]; // La clave de la "mesa"
                    String recipeKey = columns[1]; // La clave de la receta dentro de la tabla
                    
                    //Se creara un Map interno por cada tabla en el libro de recetas
                    //Si el tableKey de la receta encontrada no existe, quiere decir que encontro una receta de otra tabla 
                    Map<String, List<String>> recipesForTable = data.get(mesa);
                    if (recipesForTable == null) {
                        recipesForTable = new HashMap<>(); 
                        data.put(mesa, recipesForTable);
                    }
                    
                    // Agregar la nueva receta al mapa de la tabla
                    recipesForTable.put(recipeKey, content);
                }

                Recipe recipe = new Recipe(product, r.time, ingredients);
                recipe.mostrarReceta(product, ingredients); // mostrar receta

                libraries.add(new Library(r.table.toUpperCase(Locale.ROOT), recipe));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

    public static RecipeBook getData() {
        List<Library> libraries = new ArrayList<>(data.size());
        
        //Se recorre cada mesa
        for ( var mesa : data.keySet() ) {
            var columns = data.get(mesa);
            for ( var give : columns.keySet() ) {
                var contents = columns.get(give);

                final float in = Float.parseFloat(contents.get(0));
                final Classification type = Classification.valueOf(contents.get(1).toUpperCase(Locale.ROOT));
                final Element produce = new Element(give.toUpperCase(Locale.ROOT), type);

                List<String> elements = contents.subList(2, contents.size());
                List<Element> with = new ArrayList<>(elements.size());

                for ( var element : elements ) {
                    with.add(new Element(element.toUpperCase(Locale.ROOT), Classification.ALL));
                }

                Recipe recipe = new Recipe(produce, in, with);
                var l = new Library(mesa.toUpperCase(Locale.ROOT), recipe);
                libraries.add(l);
            }
        }

        return new RecipeBook(libraries);
    }
}
