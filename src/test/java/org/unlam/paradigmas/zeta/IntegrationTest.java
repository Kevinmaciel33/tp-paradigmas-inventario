package org.unlam.paradigmas.zeta;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    @Test
    void testCreationOfElementsWithoutMock() {
        /*
        InventoryLoader iLoader = new InventoryLoader();
        RecipeLoader rLoader = new RecipeLoader();

        Inventory inventory = iLoader.loadFile();
        RecipeBook r = rLoader.loadFile();
        Worker w = new Worker(inventory, r);

        w.create(new Element("AGUA"));
        assertTrue(inventory.hasElement(new Element("AGUA")));

         */
    }
}
