package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

public class MissingElementsQuery implements Query {

    @Override
    public Recipe run(Element targetElement, List<Library> libraries) {
        for (Library library : libraries) {
            for (Recipe recipe : library.recipes()) {
                if (recipe != null && recipe.give().equals(targetElement)) {
                    return recipe;
                }
            }
        }
        return null;
    }
}
