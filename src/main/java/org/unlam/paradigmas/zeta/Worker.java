package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.crafters.*;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Queryable;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.querys.ElementsQuery;
import org.unlam.paradigmas.zeta.querys.HowManyCreateQuery;
import org.unlam.paradigmas.zeta.querys.Query;

import java.util.List;

public class Worker {
    private final Inventory inventory;
    private final RecipeBook recipeBook;
    private final List<Crafter> crafters = List.of(
        new FireCrafter(),
        new LiquidoCrafter(),
        new AcidoCrafter(),
        new GasCrafter(),
        new MineralCrafter(),
        new MetalCrafter(),
        new BaseCrafter()// esto podria ser un parametro en el constructor o leerse de una config
    );

    private final HowManyCreateQuery howManyCreateQuery;
    private final ElementsQuery elementsQuery;

    public Worker(Inventory i, RecipeBook r) {
        this.inventory = i;
        this.recipeBook = r;

        this.howManyCreateQuery = new HowManyCreateQuery(i);
        this.elementsQuery = new ElementsQuery();
    }

    public void create(Element element) {
        Recipe recipe = this.elementsQuery.run(element, this.recipeBook.getLibraries());

        for (Crafter crafter : crafters) {
            if (crafter.shouldApply(inventory, element)) {
                crafter.craft(element, inventory, recipe);
                return;
            }
        }

        throw new RuntimeException("No hay crafter aplicable para el elemento: " + element.name());
    }

    public Queryable query(QueryEnum input, Element e) throws IllegalArgumentException {
        Query query = switch (input) {
            case ELEMENTS_FROM_ZERO -> this.howManyCreateQuery;
            case ELEMENTS -> this.elementsQuery;
            default -> null;
        };

        return query.run(e, this.recipeBook.getLibraries());
    }
}
