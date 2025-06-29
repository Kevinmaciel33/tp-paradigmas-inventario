package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.models.RecipeTree;

import java.util.List;

public class ElementsFromZeroQuery implements Query<RecipeTree> {
	
	@Override
    public RecipeTree run(Element element, List<Library> libraries) throws IllegalArgumentException {
		
        String elementName = element.name();
        
		Recipe recipe = QueryUtils.findRecipe(element, libraries);

        if (recipe == null) {
            throw new IllegalArgumentException("No recipe found to craft the element");
        }

        return new RecipeTree(elementName, recipe, libraries);
    }
} 