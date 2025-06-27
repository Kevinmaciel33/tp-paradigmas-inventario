package org.unlam.paradigmas.zeta;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.MultiRecipe;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testCreationOfElementsWithoutMock() {
        InventoryLoader iLoader = new InventoryLoader();
        RecipeLoader rLoader = new RecipeLoader();

        Inventory inventory = iLoader.loadFile();
        RecipeBook r = rLoader.loadFile();
        Worker w = new Worker(inventory, r);

        MultiRecipe multiRecipe = (MultiRecipe) w.query(QueryEnum.ELEMENTS, new Element("AGUA"));

        Recipe rr = null;
        for (Map.Entry<String, Recipe> library : multiRecipe.libraries) {
            rr = library.getValue();
        }

        w.create(new Element("AGUA"), rr);
        assertTrue(inventory.hasElement(new Element("AGUA")));
    }

    @Test
    void testCreationOfElementWithInvalidRecipe() {
        InventoryLoader iLoader = new InventoryLoader();
        RecipeLoader rLoader = new RecipeLoader();

        Inventory inventory = iLoader.loadFile();
        RecipeBook r = rLoader.loadFile();
        Worker w = new Worker(inventory, r);

        MultiRecipe multiRecipe = (MultiRecipe) w.query(QueryEnum.ELEMENTS, new Element("AGUA"));

        assertThrows(IllegalArgumentException.class, () -> w.create(new Element("ALGO_QUE_NO_EXISTE"), null));
    }
}
