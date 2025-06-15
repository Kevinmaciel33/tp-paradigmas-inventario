package org.example;

import org.example.models.Library;

import java.util.List;

public class RecipeBook {
    public final List<Library> libraries;

    public RecipeBook(List<Library> libraries) {
        this.libraries = libraries;
    }
}
