package org.unlam.paradigmas.zeta.crafters;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

/**
 * Catalizador para tipo ácido
 * Doble de material
 */
public class AcidoCrafter extends Crafter {
    @Override
    public Classification type() {
        return Classification.ACIDO;
    }

    @Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {

        for (Element ingredient : recipe.ingredients()) {
            inventory.remove(ingredient);
        }

        Element catalyst = catalyst();
        inventory.remove(catalyst);

        inventory.add(element, 2);
    }
}

