package org.unlam.paradigmas.zeta.crafters;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

/**
 * Base to create element
 * Not apply any special interaction with the inventory or the produced element
 */
public abstract class Crafter {
    public abstract Classification type();

    public boolean shouldApply(Inventory inventory, Element element) {
        // Verificamos que el tipo del elemento coincida con el tipo de catalizador de este crafter
        boolean typeMatch = type() == Classification.ALL || type() == element.type();
        
        // Verificamos que si el crafter requiere catalizador, el inventario lo tenga
        boolean hasCatalyst = type() == Classification.ALL || inventory.hasElement(catalyst());

        return typeMatch && hasCatalyst;
    }


    protected Element catalyst() {
        return new Element("CATALIZADOR_" + type().name(), type());
    }
    
    public boolean hasCatalyst(Inventory inventory) {
        if (type() == Classification.ALL) {
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

        if (type() != Classification.ALL) {
            inventory.remove(catalyst());
        }

        inventory.add(element, 1);
    }

}

