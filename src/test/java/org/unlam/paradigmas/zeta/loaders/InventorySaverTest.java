package org.unlam.paradigmas.zeta.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventorySaverTest {

    private static final String TEMP_FILE_PATH = "test-inventory.json";

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(new File(TEMP_FILE_PATH).toPath());
    }

    @Test
    void testSaveToFile_createsValidJsonFile() throws Exception {
        // Arrange
        Element iron = new Element("IRON", Classification.METAL);
        Element water = new Element("WATER", Classification.WATER);
        Map<Element, Integer> stock = new HashMap<>();
        stock.put(iron, 3);
        stock.put(water, 7);
        Inventory inventory = new Inventory(stock);

        // Act
        InventorySaver.saveToFile(inventory, TEMP_FILE_PATH);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        InventoryJson[] loaded = mapper.readValue(new File(TEMP_FILE_PATH), InventoryJson[].class);

        assertEquals(2, loaded.length);

        boolean foundIron = false;
        boolean foundWater = false;

        for (InventoryJson item : loaded) {
            if (item.name.equals("IRON")) {
                foundIron = true;
                assertEquals("METAL", item.type);
                assertEquals(3, item.quantity);
            }
            if (item.name.equals("WATER")) {
                foundWater = true;
                assertEquals("WATER", item.type);
                assertEquals(7, item.quantity);
            }
        }

        assertTrue(foundIron, "IRON no encontrado en JSON");
        assertTrue(foundWater, "WATER no encontrado en JSON");
    }
}
