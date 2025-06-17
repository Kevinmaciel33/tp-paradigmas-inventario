package org.example.crafters;

import org.example.enums.Classification;
import org.example.models.Element;
import org.example.models.Recipe;
import org.example.Inventory;

public class FireCrafter extends Crafter{
	@Override
    public Classification catalystType() {
        return Classification.FIRE;  // Este crafter solo aplica a recetas tipo FIRE
    }
	
	@Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {
        // Reducir el tiempo a la mitad (solo como ejemplo)
        float tiempoReducido = recipe.time() / 2;
        System.out.println("Crafteando " + element.name() + " con tiempo reducido: " + tiempoReducido);

        // Consumir ingredientes normales (no catalizadores)
        for (Element ingrediente : recipe.ingredients()) {
            inventory.remove(ingrediente);
        }

        // Consumir un catalizador
        Element catalyst = catalyst();
        inventory.remove(catalyst);

        // Añadir el producto final al inventario
        inventory.add(element, 1);
    }
	
}
