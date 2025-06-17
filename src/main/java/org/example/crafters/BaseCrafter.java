package org.example.crafters;

import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;

public class BaseCrafter extends Crafter {
	
	@Override
    public Classification catalystType() {
        return Classification.ALL; 
    }

    @Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {

        // Consumir ingredientes normales (no catalizadores)
        for (Element ingrediente : recipe.ingredients()) {
            inventory.remove(ingrediente);
        }

        // Añadir el producto final al inventario
        inventory.add(element, 1);
    }

}
