package org.unlam.paradigmas.zeta.querys;


import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

public class ElementsQuery implements Query<Recipe> {

    @Override
    public Recipe run(Element e, List<Library> l) throws IllegalArgumentException {
        if ( e == null ) {
            throw new IllegalArgumentException("Could not create the element");
        }

        for ( Library lb : l ) {
            if ( e.equals(lb.recipe().give()) ) {
                return lb.recipe();
            }
        }

        throw new IllegalArgumentException("Could not create the element");
    }
}
