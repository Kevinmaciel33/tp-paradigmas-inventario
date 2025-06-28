package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.models.Queryable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissingElementsFromZeroQuery implements Query<MissingElementsFromZeroQuery>, Queryable {
	
    private Inventory inventory;
    private Map<String, Integer> missingElements;
    private String elementName;
    private boolean hasRecipe;

    public MissingElementsFromZeroQuery(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public MissingElementsFromZeroQuery run(Element element, List<Library> libraries) {
    	
        this.elementName = element.name();
        
        Recipe originalRecipe = QueryUtils.findRecipe(element, libraries);
        if (originalRecipe == null) {
            throw new IllegalArgumentException("No recipe found to craft the element");
        }

        this.hasRecipe = true;

        //Se obtienen todos los elementos basicos de la receta 
        Map<String, Long> requiredElements = QueryUtils.countAllBasicElements(originalRecipe, libraries);
        
        // Crear mapa de elementos básicos que faltan
        this.missingElements = new HashMap<>();
        for (Map.Entry<String, Long> entry : requiredElements.entrySet()) {
            String elementName = entry.getKey();
            Long required = entry.getValue();
            int available = inventory.numberOf(new Element(elementName));
            
            if (available < required) {
                int missing = (int) (required - available);
                this.missingElements.put(elementName, missing);
            }
        }

        return this;
    }

    public Map<String, Integer> getMissingElements() {
        return missingElements;
    }

    @Override
    public String toString() {
    	
        if (!hasRecipe) {
            return "No se encontró receta para: " + elementName;
        }
        
        if (missingElements.isEmpty()) {
            return "Tienes todos los elementos necesarios para craftear " + elementName + " desde cero.";
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Para craftear ").append(elementName).append(" desde cero te faltan:\n");
            
            for (Map.Entry<String, Integer> entry : missingElements.entrySet()) {
                message.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            
            return message.toString().trim();
        }
    }
} 