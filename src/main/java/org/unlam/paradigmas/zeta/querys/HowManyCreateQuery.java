package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.QuantityElements;

import java.util.List;

public class HowManyCreateQuery implements Query<QuantityElements> {
    Inventory inventory;

    public HowManyCreateQuery(Inventory inventory) {
        this.inventory = inventory;
    }

    public QuantityElements run(Element e, List<Library> l) {

        int quantity = 0;
        for ( Library lb : l ) {
            if ( e == lb.recipe().give() ) {
                boolean hasAllIngredients = true;
                for ( int i=0; i < lb.recipe().ingredients().size() && hasAllIngredients; ++i ) {
                    Element element = lb.recipe().ingredients().get(i);
                    if ( !this.inventory.hasElement(element) ) {
                        hasAllIngredients = false;
                    }
                }

                if ( hasAllIngredients ) {
                    quantity++;
                }

            }
        }

        return new QuantityElements(quantity);
    }
}
