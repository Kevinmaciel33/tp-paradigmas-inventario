package org.unlam.paradigmas.zeta.models;

import java.util.List;
import java.util.Map;

public class MultiRecipe implements Queryable {
    public final List<Map.Entry<String, Recipe>> libraries;

    public MultiRecipe(List<Map.Entry<String, Recipe>> libraries) {
        this.libraries = libraries;
    }

    @Override
    public String show() {
        if (libraries.isEmpty()) {
            return "No hay recetas";
        }

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Recipe> library : libraries) {
            String table = library.getKey();
            sb.append("En la tabla: ").append(table).append("\n");
            sb.append(library.getValue().show());
        }

        return sb.append("\n").toString();
    }
}
