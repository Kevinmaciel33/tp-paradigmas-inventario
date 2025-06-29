package org.unlam.paradigmas.zeta.querys;


import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.MultiRecipe;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElementsQuery implements Query<MultiRecipe> {

    @Override
    public MultiRecipe run(Element e, List<Library> l) throws IllegalArgumentException {
        if ( e == null ) {
            throw new IllegalArgumentException("Could not create the element");
        }

        List<Map.Entry<String, Recipe>> multi = new ArrayList<>();

        for ( Library lb : l ) {
            for ( Recipe r : lb.recipes() ) {
                if ( e.equals(r.give()) ) {
                    multi.add(Map.entry(lb.originTable(), r));
                }
            }
        }

        if ( multi.isEmpty() ) {
            throw new IllegalArgumentException("Could not create the element");
        }

        return new MultiRecipe(multi);
    }
}
