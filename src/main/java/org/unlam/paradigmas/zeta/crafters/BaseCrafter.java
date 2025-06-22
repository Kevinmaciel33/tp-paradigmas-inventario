package org.unlam.paradigmas.zeta.crafters;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

public class BaseCrafter extends Crafter {
	
	@Override
    public Classification type() {
        return Classification.ALL; 
    }

    @Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {

        for (Element ingredient : recipe.ingredients()) {
            inventory.remove(ingredient);
        }

        inventory.add(element, 1);
    }

}
