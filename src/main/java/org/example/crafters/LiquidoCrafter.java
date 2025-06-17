package org.example.crafters;

import org.example.models.Element;
import org.example.models.Recipe;
import org.example.Inventory;
import org.example.enums.Classification;
//Catalizador para tipo liquido
public class LiquidoCrafter extends Crafter {
 @Override
 public Classification catalystType() {
     return Classification.LIQUIDO;
 }

 @Override
 public void craft(Element element, Inventory inventory, Recipe recipe) {
     float tiempoReducido = recipe.time() / 2; // ejemplo: reduce tiempo a la mitad
     System.out.println("Crafteando " + element.name() + " con catalizador liquido. Tiempo reducido: " + tiempoReducido);

     for (Element ingrediente : recipe.ingredients()) {
         inventory.remove(ingrediente);
     }

     Element catalyst = new Element("CATALIZADOR_LIQUIDO", Classification.LIQUIDO);
     inventory.remove(catalyst);

     inventory.add(element, 1);
 }
}

