package org.example.querys;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.util.List;

public class QueryElementsFromZero implements Query{
	@Override
    public Recipe run(Element element, List<Library> libraries) {
        // Buscar la receta 
        Recipe recipe = libraries.stream()
                .filter(l -> l.recipe().give().name().equals(element.name()))
                .map(Library::recipe)
                .findFirst()
                .orElse(null);

        if (recipe != null) {
            //System.out.println(element.name());
            System.out.println("\nArbol de ingredientes de\n" + element.name() + ":");

            processRecipe(recipe, libraries, "    ");
            // Usar ElementCounter para mostrar el conteo de elementos básicos
            ElementCounter counter = new ElementCounter();
            counter.countAllBasicElements(recipe, libraries);
            return recipe;
        }

        System.out.println("No se encontró receta para: " + element.name() + ":(");
        System.out.println("--------------------------------------------------------------");
        return null;
    }

    private void processRecipe(Recipe recipe, List<Library> libraries, String indent) {
        List<Element> ingredients = recipe.ingredients();

        for (int i = 0; i < ingredients.size(); i++) {
            Element ingredient = ingredients.get(i);
            boolean isLast = i == ingredients.size() - 1;
           
            // Si el ingrediente de un elemento es a su vez un elemento crafteable se hace
            // ...el procesamiento de ese elemento y su respectiva receta
            Recipe craftableIngredient = libraries.stream()
                    .filter(l -> l.recipe().give().name().equals(ingredient.name()))
                    .map(Library::recipe)
                    .findFirst()
                    .orElse(null);

            System.out.println(indent + (isLast ? "└── " : "├── ") + ingredient.name());
            
            
            if (craftableIngredient != null) {
                String nextIndent = indent + (isLast ? "    " : "│   ");
                processRecipe(craftableIngredient, libraries, nextIndent);
            }
        }
    }
}
