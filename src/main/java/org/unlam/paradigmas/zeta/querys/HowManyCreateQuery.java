package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.QuantityElements;
import org.unlam.paradigmas.zeta.models.Recipe;

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
            for ( Recipe r : lb.recipes()) {
                if (e.equals(r.give())) {
                    Map<String, Integer> amountRecipe = new HashMap<>();

                    for (Element element : r.ingredients()) {
                        amountRecipe.put(element.name(), amountRecipe.getOrDefault(element.name(), 0) + 1);
                    }

                    for (String element : amountRecipe.keySet()) {
                        final int necessary = amountRecipe.get(element);
                        final int a = inventory.numberOf(new Element(element)) / necessary;
                        quantity = Math.min(quantity, a);
                    }
                }
            }
        }

        return new QuantityElements(quantity == Integer.MAX_VALUE ? 0 : quantity);
    }
}
