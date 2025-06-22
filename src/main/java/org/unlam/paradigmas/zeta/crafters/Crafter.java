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
        final boolean allType = type() == Classification.ALL;
        final boolean typeMatch =  type() == element.type();
        final boolean hasCatalyst = hasCatalyst(inventory);

        return allType && typeMatch && hasCatalyst;
    }

    protected Element catalyst() {
        return new Element("CATALIZADOR_" + type().name(), type());
    }
    
    private boolean hasCatalyst(Inventory inventory) {
        if (type() == Classification.ALL) {
            return true;
        }
        Element catalyst = catalyst();
        return inventory.hasElement(catalyst);
    }


    public void craft(Element element, Inventory inventory, Recipe recipe) {
        for (Element ingredient : recipe.ingredients()) {
            inventory.remove(ingredient);
        }

        if (type() != Classification.ALL) {
            inventory.remove(catalyst());
        }

        inventory.add(element, 1);
    }

}

