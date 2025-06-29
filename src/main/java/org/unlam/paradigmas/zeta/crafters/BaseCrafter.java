package org.unlam.paradigmas.zeta.crafters;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.MissingResourceException;

public class BaseCrafter extends Crafter {
	
	@Override
    public Classification type() {
        return Classification.ALL; 
    }

    @Override
    public boolean shouldApply(Inventory inventory, Element element) {
        return true;
    }

    @Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {

        for (Element ingredient : recipe.ingredients()) {
            if ( inventory.hasElement(ingredient) ) {
                inventory.remove(ingredient);
            } else {
                throw new MissingResourceException("Could not create element. Missing element", element.getClass().toString(), ingredient.name());
            }
        }

        inventory.add(element, 1);
    }

}
