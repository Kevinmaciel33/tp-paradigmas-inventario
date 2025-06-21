package org.unlam.paradigmas.zeta;


import org.unlam.paradigmas.zeta.crafters.BaseCrafter;
import org.unlam.paradigmas.zeta.crafters.Crafter;
import org.unlam.paradigmas.zeta.enums.Classification;
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

    public void create(Element element) throws RuntimeException {
        for ( Crafter crafter : this.crafters ) {
            if ( crafter.type() == Classification.ALL || element.type() == crafter.type() ) {
                Recipe r = this.elementsQuery.run(element, this.recipeBook.getLibraries());
                crafter.craft(element, this.inventory, r);

                break;
            }
        }
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
