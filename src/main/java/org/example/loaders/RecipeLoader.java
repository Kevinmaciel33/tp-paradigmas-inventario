package org.example.loaders;

import org.example.RecipeBook;
import org.example.models.Library;
import org.example.models.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.Constants.COMMA;

public class RecipeLoader implements Loader<RecipeBook> {

    private static final String PATH = "recipes.csv";

    private static final Map<String, List<String>> data = new HashMap<>();

    public void loadFile() {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);
        try(BufferedReader file = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = file.readLine()) != null) {
                String [] columns = line.split(COMMA);

                List<String> elements = new ArrayList<>();
                for (int i=1;i<columns.length;++i) {
                    elements.add(columns[i]);
                }

                data.put(columns[0], elements);
                //(new Element(columns[0]), Integer.valueOf(columns[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RecipeBook getData() {
        for ( var d : data.keySet() ) {
            var l = new Library(d, new Recipe());
        }

        return new RecipeBook();
    }
}
