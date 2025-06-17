package org.example;


import org.example.crafters.*;
import org.example.enums.Classification;
import org.example.enums.QueryEnum;
import org.example.models.Element;
import org.example.models.Recipe;

import java.util.List;

public class Worker {
    private final Inventory inventory;
    private final RecipeBook recipeBook;
    private final List<Crafter> crafters = List.of(
        new BaseCrafter(),// esto podria ser un parametro en el constructor o leerse de una config
        new FireCrafter(),
        new LiquidoCrafter(),
        new AcidoCrafter(),
        new GasCrafter(),
        new MineralCrafter(),
        new MetalCrafter()
    );

    public Worker(Inventory i, RecipeBook r) {
        this.inventory = i;
        this.recipeBook = r;
    }

    public void create(Element element) {
        Recipe recipe = QueryEnum.ELEMENTS.query.run(element, this.recipeBook.libraries);

        for (Crafter crafter : crafters) {
            if (crafter.isApplicable(inventory, element)) {
                crafter.craft(element, inventory, recipe);
                return;
            }
        }


        throw new RuntimeException("No hay crafter aplicable para el elemento: " + element.name());
    }
    

    public void query(String input, Element e) throws IllegalArgumentException {
        if ( input == null || input.isEmpty() ) {
            throw new IllegalArgumentException("[Error] input null or empty");
        }

        QueryEnum query = QueryEnum.valueOf(input);
        query.query.run(e, this.recipeBook.libraries);
    }
    
    
}
