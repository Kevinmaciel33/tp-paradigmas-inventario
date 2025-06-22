package org.unlam.paradigmas.zeta;


import org.unlam.paradigmas.zeta.crafters.BaseCrafter;
import org.unlam.paradigmas.zeta.crafters.Crafter;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

public class Worker {
    private final Inventory inventory;
    private final RecipeBook recipeBook;
    private final List<Crafter> crafters = List.of(
        new BaseCrafter()// esto podria ser un parametro en el constructor o leerse de una config
    );

    public Worker(Inventory i, RecipeBook r) {
        this.inventory = i;
        this.recipeBook = r;
    }

    public void create(Element element) throws RuntimeException {
        for(Crafter crafter : crafters) {
            if ( crafter.type() == Classification.ALL || element.type() == crafter.type() ) {
                Recipe r = QueryEnum.ELEMENTS.query.run(element, this.recipeBook.libraries);
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
        query.query.run(e, this.recipeBook.libraries);
    }
}
