package org.unlam.paradigmas.zeta;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    @Test
    void addToInventory() {
        Inventory i = new Inventory(new HashMap<>());
        Element e = new Element("H", Classification.ALL);
        i.add(e, 1);
        i.add(e, 1);
        i.add(e, 1);
        i.add(e, 1);

        assertTrue(i.hasElement(e));
        assertEquals(4, i.numberOf(e));
    }

    @Test
    void addAndRemoveToInventory() {
        Inventory i = new Inventory(new HashMap<>());
        Element e = new Element("H", Classification.ALL);
        i.add(e, 4);
        i.remove(e);
        i.remove(e);
        i.remove(e);
        i.remove(e);

        assertEquals(0, i.numberOf(e));
    }

    @Test
    void addAndRemoveMoreElementsThenTheAvailable() {
        Inventory i = new Inventory(new HashMap<>());
        Element e = new Element("H", Classification.ALL);
        i.add(e, 1);
        i.add(e, 1);
        i.add(e, 1);
        i.add(e, 1);
        i.remove(e, 5);

        assertEquals(0, i.numberOf(e));
    }

    @Test
    void testHasElementWhenInventoryIsEmpty() {
        Inventory i = new Inventory(new HashMap<>());
        Element e = new Element("H", Classification.ALL);
        assertFalse(i.hasElement(e));
    }

    @Test
    void whenTryToRemoveInAnEmptyInventoryShouldStillBeEmpty() {
        Inventory i = new Inventory(new HashMap<>());
        Element e = new Element("H", Classification.ALL);
        i.remove(e);

        assertEquals(0, i.numberOf(e));
    }

    @Test
    void whenUseNullAsKeyToRemoveElements() {
        Inventory i = new Inventory(new HashMap<>());
        i.remove(null);

        assertEquals(0, i.numberOf(null));
    }

    @Test
    void whenUseNullAsKeyToAddElements() {
        Inventory i = new Inventory(new HashMap<>());
        i.add(null, 10);

        assertFalse(i.hasElement(null));
    }
}
