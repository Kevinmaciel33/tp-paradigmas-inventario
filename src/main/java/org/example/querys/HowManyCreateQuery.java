package org.example.querys;

import org.example.Inventory;
import org.example.models.Element;
import org.example.models.Library;
import org.example.models.QuantityElements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HowManyCreateQuery implements Query<QuantityElements> {
    Inventory inventory;

    public HowManyCreateQuery(Inventory inventory) {
        this.inventory = inventory;
    }

    public QuantityElements run(Element e, List<Library> l) {

        int quantity = Integer.MAX_VALUE;
        for ( Library lb : l ) {
            if ( e.equals(lb.recipe().give()) ) {
                Map<String, Integer> amountRecipe = new HashMap<>();

                for (Element element : lb.recipe().ingredients()) {
                    amountRecipe.put(element.name(), amountRecipe.getOrDefault(element.name(), 0) + 1);
                }

                for (String element : amountRecipe.keySet()) {
                    final int necessary = amountRecipe.get(element);
                    final int a = inventory.numberOf(new Element(element))/necessary;
                    quantity = Math.min(quantity, a);
                }
            }
        }

        return new QuantityElements(quantity == Integer.MAX_VALUE ? 0 : quantity);
    }
}
