package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.crafters.*;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Queryable;
import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.querys.ElementsQuery;
import org.unlam.paradigmas.zeta.querys.HowManyCreateQuery;
import org.unlam.paradigmas.zeta.querys.Query;
import org.unlam.paradigmas.zeta.querys.QueryElementsFromZero;

import java.util.*;

import static org.unlam.paradigmas.zeta.enums.QueryEnum.*;

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
    private final EnumMap<QueryEnum, Query<?>> querys;

    public Worker(Inventory i, RecipeBook r) {
        this.inventory = i;
        this.recipeBook = r;

        this.querys = new EnumMap<>(
            Map.of(
                ELEMENTS, new ElementsQuery(),
                ELEMENTS_FROM_ZERO, new QueryElementsFromZero(),
                MISSING_ELEMENTS, new QueryElementsFromZero(),
                MISSING_ELEMENTS_FROM_ZERO, new QueryElementsFromZero(),
                HOW_MANY_ELEMENTS, new HowManyCreateQuery(inventory)
            )
        );
    }

    public void create(Element element) throws RuntimeException {
        Recipe recipe = (Recipe) this.querys.get(ELEMENTS).run(element, this.recipeBook.getLibraries());

        for (Crafter crafter : crafters) {
            if (crafter.shouldApply(inventory, element)) {
                crafter.craft(element, inventory, recipe);
                //TODO: add to history list
                return;
            }
        }

        //nunca se tendria que ejecutar esto
        throw new RuntimeException("No hay crafter aplicable para el elemento");
    }

    public Queryable query(QueryEnum input, Element e) throws RuntimeException {
        return this.querys.get(input).run(e, this.recipeBook.getLibraries());
    }
}
