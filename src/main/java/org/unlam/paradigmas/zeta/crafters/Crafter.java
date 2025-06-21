package org.unlam.paradigmas.zeta.crafters;

import org.unlam.paradigmas.zeta.models.Queryable;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

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
