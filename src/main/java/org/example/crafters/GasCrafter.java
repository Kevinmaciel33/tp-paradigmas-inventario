package org.example.crafters;

import org.example.models.Element;
import org.example.models.Recipe;
import org.example.Inventory;
import org.example.enums.Classification;

public class GasCrafter extends Crafter {

    @Override
    public Classification type() {
        return Classification.GAS;
    }

    @Override
    public void craft(Element element, Inventory inventory, Recipe recipe) {
        // Verificamos que el tipo de receta coincida
        if (element.type() != type()) {
            throw new IllegalArgumentException("Este catalizador solo aplica a recetas de tipo " + type());
        }

        Element catalyst = catalyst(); // Usamos el método de la clase base

        // Verificamos disponibilidad del catalizador
        if (!inventory.hasElement(catalyst)) {
            throw new RuntimeException("Falta el catalizador de gases en el inventario.");
        }

        // Consumimos ingredientes
        for (Element ingrediente : recipe.ingredients()) {
            inventory.remove(ingrediente);
        }

        // Consumimos el catalizador
        inventory.remove(catalyst);

        // Agregamos el resultado con bonificación (doble en este caso)
        inventory.add(element, 2);
    }
}
