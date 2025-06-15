package org.example;


import org.example.crafters.Crafter;
import org.example.enums.Classification;
import org.example.enums.QueryEnum;
import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.util.List;

public class Worker {
    private Inventory inventory;
    private RecipeBook recipeBook;
    private final List<Crafter> crafters = List.of();

    public Worker(Inventory i, RecipeBook r) {
        this.inventory = i;
        this.recipeBook = r;
    }

    public void create(Element element) throws RuntimeException {
        for(Crafter crafter : crafters) {
            if ( crafter.type == Classification.ALL || element.type() == crafter.type ) {
                Recipe r = QueryEnum.ELEMENTS.q.run(element, this.recipeBook.libraries);
                crafter.craft(element, inventory, r);
                break;
            }
        }
    }

    public void query(String input, Element e) throws IllegalArgumentException {
        if ( input == null || input.isEmpty() ) {
            throw new IllegalArgumentException("[Error] input null or empty");
        }

        QueryEnum query = QueryEnum.valueOf(input);
        query.q.run(e, this.recipeBook.libraries);
    }
}
