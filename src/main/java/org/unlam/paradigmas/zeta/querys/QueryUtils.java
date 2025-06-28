package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryUtils {
    
    //Busca una receta, si no la encuentra devuelve null
    public static Recipe findRecipe(Element element, List<Library> libraries) {
        for (Library library : libraries) {
            if (library.recipe().give().name().equals(element.name())) {
                return library.recipe();
            }
        }
        return null;
    }

    //Retorna un set con los elementos basicos de una receta 
    public static Set<String> getBasicElements(List<Library> libraries) {
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

    //Cuenta todos los elementos basicos en una receta
    public static Map<String, Long> countAllBasicElements(Recipe recipe, List<Library> libraries) {
    	
        Set<String> basicElements = getBasicElements(libraries);
        Map<String, Long> elementCounts = new HashMap<>();
        
        for (String elementName : basicElements) {
            long count = countBasicElement(recipe, elementName, libraries);
            if (count > 0) {
                elementCounts.put(elementName, count);
            }
        }
        
        return elementCounts;
    }

    //Cuenta todas las apariciones de un determinado elemento basico en una receta
    public static long countBasicElement(Recipe recipe, String elementName, List<Library> libraries) {
    	
        long count = 0;

        for (Element ingredient : recipe.ingredients()) {
            if (ingredient.name().equals(elementName)) {
                count++;
            } else {
                Recipe nestedRecipe = findRecipe(ingredient, libraries);
                if (nestedRecipe != null) {
                    count += countBasicElement(nestedRecipe, elementName, libraries);
                }
            }
        }

        return count;
    }
} 