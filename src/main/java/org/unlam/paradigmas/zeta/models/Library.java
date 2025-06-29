package org.unlam.paradigmas.zeta.models;

import java.util.List;

public record Library(String originTable, List<Recipe> recipes) implements Queryable {
    @Override
    public String show() {
        /*
        for (Recipe recipe : recipes) {
            originTable
            recipe.time();
            recipe.ingredients();
        }
         */
        return "";
    }
}
