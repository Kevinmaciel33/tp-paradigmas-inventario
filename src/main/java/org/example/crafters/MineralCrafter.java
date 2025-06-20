package org.example.crafters;

import org.example.models.Element;
import org.example.models.Recipe;
import org.example.Inventory;
import org.example.enums.Classification;
//Catalizador para tipo Mineral
public class MineralCrafter extends Crafter {
 @Override
 public Classification type() {
     return Classification.MINERAL;
 }

 @Override
 public void craft(Element element, Inventory inventory, Recipe recipe) {

     for (Element ingrediente : recipe.ingredients()) {
         inventory.remove(ingrediente);
     }

     Element catalyst = catalyst();
     inventory.remove(catalyst);

     inventory.add(element, 1);
 }
}


