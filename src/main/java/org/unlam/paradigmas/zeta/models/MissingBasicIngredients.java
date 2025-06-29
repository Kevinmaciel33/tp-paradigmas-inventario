package org.unlam.paradigmas.zeta.models;

import java.util.Map;

public class MissingBasicIngredients implements Queryable {
    private final String elementName;
    private final Map<String, Integer> missingElements;

    public MissingBasicIngredients(String elementName, Map<String, Integer> missingElements) {
        this.elementName = elementName;
        this.missingElements = missingElements;
    }

    public String getElementName() {
        return elementName;
    }

    public Map<String, Integer> getMissingElements() {
        return missingElements;
    }

    public boolean hasMissingElements() {
        return !missingElements.isEmpty();
    }

    @Override
    public String show() {
    	
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