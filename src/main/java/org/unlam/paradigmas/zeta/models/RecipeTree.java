package org.unlam.paradigmas.zeta.models;

import org.unlam.paradigmas.zeta.querys.QueryUtils;

import java.util.List;
import java.util.Map;

public class RecipeTree implements Queryable {
    private final String elementName;
    private final Recipe recipe;
    private final List<Library> libraries;

    public RecipeTree(String elementName, Recipe recipe, List<Library> libraries) {
        this.elementName = elementName;
        this.recipe = recipe;
        this.libraries = libraries;
    }

    public String getElementName() {
        return elementName;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Map<String, Long> getBasicElementsCount() {
        return QueryUtils.countAllBasicElements(recipe, libraries);
    }

    @Override
    public String show() {
        StringBuilder message = new StringBuilder();
        message.append("Receta para craftear ").append(elementName).append(" desde cero:\n");
        
        message.append("\nÁrbol de ingredientes:\n");
        printRecipeTree(recipe, "", true, message);
        
        //Contador de elementos basicos
        Map<String, Long> basicElementsCount = QueryUtils.countAllBasicElements(recipe, libraries);
        message.append("\nElementos básicos necesarios:\n");
        for (Map.Entry<String, Long> entry : basicElementsCount.entrySet()) {
            message.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
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
            Recipe childRecipe = QueryUtils.findRecipe(ingredient, libraries);
            
            if (childRecipe != null) {
            	//Si el ingrediente tiene una receta en recipes.json, se procede a hacer una busqueda recursiva
                printRecipeTree(childRecipe, nextIndent, isLastIngredient, message);
            } else {
                //Si el ingrediente no tiene receta, entonces es un elemento basico
                message.append(nextIndent).append(isLastIngredient ? "└── " : "├── ")
                       .append(ingredient.name()).append("\n");
            }
        }
    }
} 