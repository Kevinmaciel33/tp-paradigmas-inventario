package org.example.crafters;

import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;

import java.util.List;

/**
 * Base to create element
 * Not apply any special interaction with the inventory or the produced element
 */
public abstract class Crafter {
    public final Classification type = Classification.ALL;

    public void craft(Element element, Inventory inventory, Recipe recipe) {

        for ( Element e : recipe.ingredients() ) {
            inventory.remove(e);
        }

        inventory.add(element, 1);
    }
}
