package org.unlam.paradigmas.zeta.models;

import java.util.List;
import java.util.Map;

public class MissingBasicIngredients implements Queryable {
    private final String elementName;
    private final List<Map<String, Integer>> missingElementsList;
    private final List<Recipe> recipes;
    private final List<Library> libraries;

    public MissingBasicIngredients(String elementName, List<Map<String, Integer>> missingElementsList, List<Recipe> recipes, List<Library> libraries) {
        this.elementName = elementName;
        this.missingElementsList = missingElementsList;
        this.recipes = recipes;
        this.libraries = libraries;
    }

    public String getElementName() {
        return elementName;
    }

    public List<Map<String, Integer>> getMissingElementsList() {
        return missingElementsList;
    }

    @Override
    public String show() {
    	
        if (missingElementsList.isEmpty()) {
            return "No recipes found for " + elementName;
        }

        StringBuilder message = new StringBuilder();
        message.append("Para craftear ").append(elementName).append(" desde cero te faltan:\n\n");

        for (int i = 0; i < missingElementsList.size(); i++) {
            Map<String, Integer> missingElements = missingElementsList.get(i);
            Recipe recipe = recipes.get(i);
            
            message.append("Receta de mesa: ").append(getTableName(recipe)).append("\n");
            
            if (missingElements.isEmpty()) {
                message.append("Tienes todos los elementos necesarios para esta receta.\n");
            } else {
                message.append("Te faltan:\n");
                for (Map.Entry<String, Integer> entry : missingElements.entrySet()) {
                    message.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
            }
            
            if (i < missingElementsList.size() - 1) {
                message.append("\n");
            }
        }
        
        return message.toString().trim();
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