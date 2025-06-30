package org.unlam.paradigmas.zeta.models;

import org.unlam.paradigmas.zeta.querys.QueryUtils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RecipeTree implements Queryable {
    private final String elementName;
    private final List<Recipe> recipes;
    private final List<Library> libraries;

    public RecipeTree(String elementName, List<Recipe> recipes, List<Library> libraries) {
        this.elementName = elementName;
        this.recipes = recipes;
        this.libraries = libraries;
    }

    public String getElementName() {
        return elementName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Map<String, Long> getBasicElementsCount() {
        if (!recipes.isEmpty()) {
            return QueryUtils.countAllBasicElements(recipes.get(0), libraries);
        }
        return null;
    }

    @Override
    public String show() {
        StringBuilder message = new StringBuilder();
        message.append("Receta para craftear ").append(elementName).append(" desde cero:\n");
        
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            
            message.append("\nReceta de mesa: ").append(getTableName(recipe)).append("\n");
            
            message.append("Árbol de ingredientes:\n");
            printRecipeTree(recipe, "", true, message);
            
            //Contador de elementos basicos
            Map<String, Long> basicElementsCount = QueryUtils.countAllBasicElements(recipe, libraries);
            message.append("\nElementos básicos necesarios:\n");
            for (Map.Entry<String, Long> entry : basicElementsCount.entrySet()) {
                message.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            
            if (i < recipes.size() - 1) {
                message.append("\n");
            }
        }
        
        return message.toString().trim();
    }

    private void printRecipeTree(Recipe recipe, String indent, boolean isLast, StringBuilder message) {
    	
        message.append(indent).append(isLast ? "└── " : "├── ").append(recipe.give().name()).append("\n");
        List<Element> ingredients = recipe.ingredients();
        
        for (int i = 0; i < ingredients.size(); i++) {
        	
            Element ingredient = ingredients.get(i);
            boolean isLastIngredient = i == ingredients.size() - 1;
            String nextIndent = indent + (isLast ? "    " : "│   ");    
            List<Recipe> childRecipes = QueryUtils.findRecipe(ingredient, libraries);
            
            if (!childRecipes.isEmpty()) {
            	//Si el ingrediente tiene una receta en recipes.json, se procede a hacer una busqueda recursiva
                // Buscamos específicamente la receta de la mesa "BASE"
                Recipe childRecipe = QueryUtils.findBaseRecipe(ingredient, libraries);
                if (childRecipe != null) {
                    printRecipeTree(childRecipe, nextIndent, isLastIngredient, message);
                } else {
                    // Si no hay receta base, usamos la primera encontrada
                    printRecipeTree(childRecipes.get(0), nextIndent, isLastIngredient, message);
                }
            } else {
                //Si el ingrediente no tiene receta, entonces es un elemento basico
                message.append(nextIndent).append(isLastIngredient ? "└── " : "├── ")
                       .append(ingredient.name()).append("\n");
            }
        }
    }

    private String getTableName(Recipe recipe) {
        for (Library library : libraries) {
            for (Recipe libRecipe : library.recipes()) {
                if (libRecipe.equals(recipe)) {
                    return library.originTable();
                }
            }
        }
        return "Desconocida";
    }
} 