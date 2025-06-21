package org.example.querys;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ElementCounter {	
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