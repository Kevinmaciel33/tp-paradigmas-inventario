package org.unlam.paradigmas.zeta.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.querys.PrologRuleGenerator;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeLoader implements Loader<RecipeBook> {

    private static final String PATH = "recipes.json";
    private final List<Library> libraries = new ArrayList<>();

    public RecipeBook loadFile() {
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
                    ingredients.add(new Element(ing.toUpperCase(Locale.ROOT)));
                }

                Recipe recipe = new Recipe(product, r.time, ingredients);
                //recipe.mostrarReceta(product, ingredients); // mostrar receta

                libraries.add(new Library(r.table.toUpperCase(Locale.ROOT), recipe));
            }

            PrologRuleGenerator.writeRulesRecipeToFile(recetas, "src/base.pl", true);  //Realiza las reglas prolog
            
            return new RecipeBook(libraries);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }
}
