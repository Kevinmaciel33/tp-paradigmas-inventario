package org.example.querys;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class QueryElementsFromZero implements Query {
	@Override
    public Recipe run(Element element, List<Library> libraries) {
        // Buscar la receta 
		Recipe recipe = null;
		for (Library l : libraries) {
		    if (l.recipe().give().name().equals(element.name())) {
		        recipe = l.recipe();
		        break;
		    }
		}

        if (recipe != null) {
            System.out.println("\nArbol de ingredientes de\n" + element.name() + ":");

            processRecipe(recipe, libraries, "    ");
            // Usar ElementCounter para mostrar el conteo de elementos básicos
            countAllBasicElements(recipe, libraries);
            return recipe;
        }

        System.out.println("No se encontró receta para: " + element.name());
        return null;
    }

    private void processRecipe(Recipe recipe, List<Library> libraries, String indent) {
        List<Element> ingredients = recipe.ingredients();

        for (int i = 0; i < ingredients.size(); i++) {
            Element ingredient = ingredients.get(i);
            boolean isLast = i == ingredients.size() - 1;
           
            // Si el ingrediente de un elemento es a su vez un elemento crafteable se hace
            // ...el procesamiento de ese elemento y su respectiva receta
            Recipe craftableIngredient = null;
            for (Library l : libraries) {
                if (l.recipe().give().name().equals(ingredient.name())) {
                    craftableIngredient = l.recipe();
                    break;
                }
            }

            System.out.println(indent + (isLast ? "└── " : "├── ") + ingredient.name());
            
            // Si el ingrediente es a su vez una receta, procesamos recursivamente
            if (craftableIngredient != null) {
                String nextIndent = indent + (isLast ? "    " : "│   ");
                processRecipe(craftableIngredient, libraries, nextIndent);
            }
        }
    }

    //Cuenta y muestra el total de cada elemento basico que hay en una receta
	public void countAllBasicElements(Recipe recipe, List<Library> libraries) {
		Set<String> basicElements = getBasicElements(libraries);
		
		System.out.println("\nElementos básicos necesarios:");
		for (String elementName : basicElements) {
			long count = countBasicElement(recipe, elementName, libraries);
			if (count > 0) {
				System.out.println("  " + elementName + ": " + count);
			}
		}
	}
    
	//Cuenta todos los elementos de un tipo en una receta
	//Se le envia una receta, un elemento basico como H , y la lista de recetas
    public long countBasicElement(Recipe recipe, String elementName, List<Library> libraries) {
        long count = 0;

        for (Element ingredient : recipe.ingredients()) {
            if (ingredient.name().equals(elementName)) {
                count++;
            } else {
            	Recipe nestedRecipe = null;
            	for (Library l : libraries) {
            	    if (l.recipe().give().name().equals(ingredient.name())) {
            	        nestedRecipe = l.recipe();
            	        break;
            	    }
            	}

                if (nestedRecipe != null) {
                    count += countBasicElement(nestedRecipe, elementName, libraries);
                }
            }
        }

        return count;
    }
  
    //Arma un set de todos los elementos basicos que hay en una receta
    private Set<String> getBasicElements(List<Library> libraries) {
        Set<String> allElements = new HashSet<>();
        Set<String> recipeResults = new HashSet<>();

        for (Library library : libraries) {
            Recipe recipe = library.recipe();
            recipeResults.add(recipe.give().name());
            for (Element element : recipe.ingredients()) {
                allElements.add(element.name());
            }
        }

        allElements.removeAll(recipeResults);
        return allElements;
    }
}
