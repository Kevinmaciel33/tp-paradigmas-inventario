package org.example.loaders;

import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;
import org.junit.jupiter.api.BeforeAll;
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
