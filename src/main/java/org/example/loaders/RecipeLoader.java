package org.example.loaders;

import org.example.RecipeBook;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static org.example.Constants.COMMA;
import static org.example.Constants.SLASH;

public class RecipeLoader implements Loader<RecipeBook> {

    private static final String PATH = "recipes.csv";
    private static final Map<String, Map<String, List<String>>> data = new HashMap<>();

    public void loadFile() {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);
        try(BufferedReader file = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = file.readLine()) != null) {
                if (!line.contains(SLASH)) {
                    String [] columns = line.split(COMMA);

                    // time, type, ...elements
                    List<String> content = Arrays.asList(columns).subList(2, columns.length);

                    data.put(columns[0], Map.of(columns[1], content));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RecipeBook getData() {
        List<Library> libraries = new ArrayList<>(data.size());

        for ( var mesa : data.keySet() ) {
            Recipe recipe = null;
            var columns = data.get(mesa);
            for ( var give : columns.keySet() ) {
                var contents = columns.get(give);

                final float in = Float.parseFloat(contents.get(0));
                final Classification type = Classification.valueOf(contents.get(1).toUpperCase(Locale.ROOT));
                final Element produce = new Element(give.toUpperCase(Locale.ROOT), type);

                List<String> elements = contents.subList(2, contents.size());
                List<Element> with = new ArrayList<>(elements.size());

                for ( var element : elements ) {
                    // no necesito la clasificacion de los ingredientes, eso me da igual
                    with.add(new Element(element, Classification.ALL));
                }

                recipe = new Recipe(produce, in, with);
                recipe.mostrarReceta(produce, with); //muestra la receta disponible
            }


            var l = new Library(mesa.toUpperCase(Locale.ROOT), recipe);
            libraries.add(l);
        }

        return new RecipeBook(libraries);
    }
}
