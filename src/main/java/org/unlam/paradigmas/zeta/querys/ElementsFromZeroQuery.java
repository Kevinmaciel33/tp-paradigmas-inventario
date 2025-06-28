package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.models.Queryable;

import java.util.List;
import java.util.Map;

public class ElementsFromZeroQuery implements Query<ElementsFromZeroQuery>, Queryable {
	private Recipe recipe;
	private String elementName;
	private boolean hasRecipe;
	private List<Library> libraries;

	@Override
    public ElementsFromZeroQuery run(Element element, List<Library> libraries) throws IllegalArgumentException {
        this.elementName = element.name();
        this.libraries = libraries;
        
		this.recipe = QueryUtils.findRecipe(element, libraries);

        if (this.recipe != null) {
            this.hasRecipe = true;
            return this;
        }

        this.hasRecipe = false;
        throw new IllegalArgumentException("No recipe found to craft the element");
    }

    //Cuenta y muestra el total de cada elemento basico que hay en una receta
	public void countAllBasicElements(Recipe recipe, List<Library> libraries) {
		Map<String, Long> elementCounts = QueryUtils.countAllBasicElements(recipe, libraries);
		
		System.out.println("\nElementos básicos necesarios:");
		for (Map.Entry<String, Long> entry : elementCounts.entrySet()) {
			System.out.println("  " + entry.getKey() + ": " + entry.getValue());
		}
	}

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        if (!hasRecipe) {
            return "No se encontró receta para: " + elementName;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("Receta para craftear ").append(elementName).append(" desde cero:\n");
        
        message.append("\nÁrbol de ingredientes:\n");
        processRecipeToString(recipe, "", true, message);
        
        //Mostrar conteo de elementos básicos
        Map<String, Long> elementCounts = QueryUtils.countAllBasicElements(recipe, libraries);
        message.append("\nElementos básicos necesarios:\n");
        for (Map.Entry<String, Long> entry : elementCounts.entrySet()) {
            message.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return message.toString().trim();
    }

    private void processRecipeToString(Recipe recipe, String indent, boolean isLast, StringBuilder message) {
        List<Element> ingredients = recipe.ingredients();

        for (int i = 0; i < ingredients.size(); i++) {
            Element ingredient = ingredients.get(i);
            boolean isLastIngredient = i == ingredients.size() - 1;
           
            message.append(indent).append(isLastIngredient ? "└── " : "├── ").append(ingredient.name()).append("\n");
            
            // Si el ingrediente es a su vez una receta, procesamos recursivamente
            Recipe craftableIngredient = QueryUtils.findRecipe(ingredient, libraries);
            if (craftableIngredient != null) {
                String nextIndent = indent + (isLastIngredient ? "    " : "│   ");
                processRecipeToString(craftableIngredient, nextIndent, isLastIngredient, message);
            }
        }
    }
} 