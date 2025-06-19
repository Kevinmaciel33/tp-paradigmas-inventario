package org.example.crafters;

import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;


/**
 * Base to create element
 * Not apply any special interaction with the inventory or the produced element
 */
public abstract class Crafter {
    public abstract Classification catalystType();

    public boolean isApplicable(Inventory inventory, Element element) {
        // Verificamos que el tipo del elemento coincida con el tipo de catalizador de este crafter
        boolean typeMatch = catalystType() == Classification.ALL || catalystType() == element.type();
        
        // Verificamos que si el crafter requiere catalizador, el inventario lo tenga
        boolean hasCatalyst = catalystType() == Classification.ALL || inventory.hasElement(catalyst());

        return typeMatch && hasCatalyst;
    }


    protected Element catalyst() {
        return new Element("CATALIZADOR_" + catalystType().name(), catalystType());
    }
    
    public boolean hasCatalyst(Inventory inventory) {
        if (catalystType() == Classification.ALL) {
            // No requiere catalizador específico
            return true;
        }
        Element catalyst = catalyst();
        return inventory.hasElement(catalyst);
    }


    public void craft(Element element, Inventory inventory, Recipe recipe) {
        for (Element ingrediente : recipe.ingredients()) {
            inventory.remove(ingrediente);
        }

        if (catalystType() != Classification.ALL) {
            inventory.remove(catalyst());
        }

        inventory.add(element, 1);
    }

}

