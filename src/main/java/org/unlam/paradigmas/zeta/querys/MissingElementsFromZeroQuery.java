package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.models.MissingBasicIngredients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissingElementsFromZeroQuery implements Query<MissingBasicIngredients> {
	
    private Inventory inventory;

    public MissingElementsFromZeroQuery(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public MissingBasicIngredients run(Element element, List<Library> libraries) {
    	
        String elementName = element.name();
        
        Recipe originalRecipe = QueryUtils.findRecipe(element, libraries);
        if (originalRecipe == null) {
            throw new IllegalArgumentException("No recipe found to craft the element");
        }

        //Se obtienen todos los elementos basicos de la receta 
        Map<String, Long> requiredElements = QueryUtils.countAllBasicElements(originalRecipe, libraries);
        
        //Se crea un Map donde se guardaran todos los elementos basicos que faltan
        Map<String, Integer> missingElements = new HashMap<>();
        for (Map.Entry<String, Long> entry : requiredElements.entrySet()) {
            String elementNameKey = entry.getKey();
            Long required = entry.getValue();
            int available = inventory.numberOf(new Element(elementNameKey));
            
            if (available < required) {
                int missing = (int) (required - available);
                missingElements.put(elementNameKey, missing);
            }
        }

        return new MissingBasicIngredients(elementName, missingElements);
    }
} 