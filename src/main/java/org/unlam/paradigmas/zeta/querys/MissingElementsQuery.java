package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.MissingBasicIngredients;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissingElementsQuery implements Query<MissingBasicIngredients> {

    private final Inventory inventory;

    public MissingElementsQuery(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public MissingBasicIngredients run(Element element, List<Library> libraries) {
        String elementName = element.name();
        List<Recipe> recipes = QueryUtils.findRecipe(element, libraries);
        if (recipes.isEmpty()) {
            throw new IllegalArgumentException("No recipe found to craft the element");
        }

        List<Map<String, Integer>> missingElementsList = new ArrayList<>();

        for (Recipe recipe : recipes) {
            Map<String, Integer> requiredElements = QueryUtils.countFirstLevelIngredients(recipe);

            Map<String, Integer> missingElements = new HashMap<>();
            for (Map.Entry<String, Integer> entry : requiredElements.entrySet()) {
                String elementNameKey = entry.getKey();
                int required = entry.getValue();
                int available = inventory.numberOf(new Element(elementNameKey));
                if (available < required) {
                    int missing = required - available;
                    missingElements.put(elementNameKey, missing);
                }
            }
            missingElementsList.add(missingElements);
        }
        return new MissingBasicIngredients(elementName, missingElementsList, recipes, libraries);
    }
}
