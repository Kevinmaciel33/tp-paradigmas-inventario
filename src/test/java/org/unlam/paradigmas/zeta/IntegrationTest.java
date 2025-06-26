package org.unlam.paradigmas.zeta;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    @Test
    void testCreationOfElementsWithoutMock() {
        InventoryLoader i = new InventoryLoader();
        RecipeLoader r = new RecipeLoader();

        i.loadFile();
        r.loadFile();

        Inventory inventory = InventoryLoader.getData();
        Worker w = new Worker(inventory, RecipeLoader.getData());

        w.create(new Element("AGUA"));
        assertTrue(inventory.hasElement(new Element("AGUA")));
    }
}
