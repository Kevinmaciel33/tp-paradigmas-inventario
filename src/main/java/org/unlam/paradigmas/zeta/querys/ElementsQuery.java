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
            for ( Recipe r : lb.recipes() ) {
                if (e.equals(r.give())) {
                    return r;
                }
            }
        }

        throw new IllegalArgumentException("Could not create the element");
    }
}
