package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryUtils {
    
    public static List<Recipe> findRecipe(Element element, List<Library> libraries) {
    	
        List<Recipe> recipes = new ArrayList<>();
        
        for (Library library : libraries) {
            //Se busca en todas las mesas de crafteo
            for (Recipe recipe : library.recipes()) {
                if (recipe.give().name().equals(element.name())) {
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    //Retorna un set con los elementos basicos de una receta 
    public static Set<String> getBasicElements(List<Library> libraries) {
    	
        Set<String> allElements = new HashSet<>();
        Set<String> recipeResults = new HashSet<>();

        for (Library library : libraries) {
            for (Recipe recipe : library.recipes()) {
                recipeResults.add(recipe.give().name());
                for (Element element : recipe.ingredients()) {
                    allElements.add(element.name());
                }
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
                List<Recipe> nestedRecipes = findRecipe(ingredient, libraries);
                if (!nestedRecipes.isEmpty()) {
                    Recipe nestedRecipe = findBaseRecipe(ingredient, libraries);
                    if (nestedRecipe != null) {
                        count += countBasicElement(nestedRecipe, elementName, libraries);
                    } else {
                        // Si no hay receta base, usamos la primera encontrada
                        count += countBasicElement(nestedRecipes.get(0), elementName, libraries);
                    }
                }
            }
        }

        return count;
    }
    
    //Se busca los ingredientes en la mesa BASE. Si no se encuentra, se busca en las otras mesas.
    public static Recipe findBaseRecipe(Element element, List<Library> libraries) {
        for (Library library : libraries) {
            if (library.originTable().equals("BASE")) {
                for (Recipe recipe : library.recipes()) {
                    if (recipe.give().name().equals(element.name())) {
                        return recipe;
                    }
                }
            }
        }
        
        //Si no se encuentra en BASE, buscaremos en otras mesas. Ya que podria pasar que la receta este compuesta por un 
        //... ingrediente UNICO que solo puede ser obtenido en otra mesa.
        for (Library library : libraries) {
            if (!library.originTable().equals("BASE")) {
                for (Recipe recipe : library.recipes()) {
                    if (recipe.give().name().equals(element.name())) {
                        return recipe;
                    }
                }
            }
        }
        
        return null;
    }
} 