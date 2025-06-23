package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.models.Library;

import java.util.List;

public class RecipeBook {
    private final List<Library> libraries;

    public RecipeBook(List<Library> libraries) {
        this.libraries = libraries;
    }

    public List<Library> getLibraries() {
        return libraries;
    }
}
