package org.example.querys;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

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
