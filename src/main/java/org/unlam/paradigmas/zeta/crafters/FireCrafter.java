package org.unlam.paradigmas.zeta.crafters;


import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

/**
 * Catalizador para tipo fuego
 * Doble de material
 * Mitad de tiempo
 */
public class FireCrafter extends Crafter {
	@Override
    public Classification type() {
        return Classification.FIRE;
    }
	
	@Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {
        if (!shouldApply(inventory, element)) {
            throw new IllegalArgumentException("Este catalizador solo aplica a recetas de tipo " + type());
        }


        for (Element ingredient : recipe.ingredients()) {
            inventory.remove(ingredient);
        }

        Element catalyst = catalyst();
        inventory.remove(catalyst);

        float lessTime = recipe.time() / 2;
        System.out.println(element+"*2 creado con el catalizador de gas en "+lessTime+"ms");
        inventory.add(element, 2);
    }
	
}
