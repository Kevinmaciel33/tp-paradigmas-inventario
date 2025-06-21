package org.unlam.paradigmas.zeta.loaders;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryLoaderTest {
    Loader<Inventory> loader = new InventoryLoader();

    @Test
    void checkInventoryOfTestResource() {
        loader.loadFile();
        Inventory i = InventoryLoader.getData();

        assertEquals(2, i.numberOf(new Element("H", Classification.WATER)));
        assertTrue(i.hasElement(new Element("H", Classification.WATER)));
        assertTrue(i.hasElement(new Element("O", Classification.WATER)));
        assertTrue(i.hasElement(new Element("H", Classification.ALL)));
    }
}
