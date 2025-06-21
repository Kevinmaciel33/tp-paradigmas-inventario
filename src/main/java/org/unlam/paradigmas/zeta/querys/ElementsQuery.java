package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

public class ElementsQuery implements Query<Recipe> {

    @Override
    public Recipe run(Element e, List<Library> l) {
        if ( e == null ) {
            throw new RuntimeException("Could not create the element");
        }

        for ( Library lb : l ) {
            if ( e.equals(lb.recipe().give()) ) {
                return lb.recipe();
            }
        }

        throw new RuntimeException("Could not create the element");
    }
}
