package org.example.crafters;

import org.example.models.Queryable;
import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;

/**
 * Base to create element
 * Not apply any special interaction with the inventory or the produced element
 */
public abstract class Crafter {
    public Classification type() { return Classification.ALL; }

    public void craft(Element element, Inventory inventory, Queryable recipe) {
        if ( recipe == null ) {
            return;
        }

        var r = (Recipe) recipe;

        for ( Element e : r.ingredients()) {
            if ( inventory.hasElement(e) ) {
                inventory.remove(e);
            } else {
                throw new RuntimeException("Could not create element. Missing element " + e.name());
            }
        }

        inventory.add(element, 1);
    }
}
