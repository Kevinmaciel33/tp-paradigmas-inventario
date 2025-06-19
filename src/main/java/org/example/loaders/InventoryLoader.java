package org.example.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InventoryLoader implements Loader<Inventory> {
    private static final String PATH = "inventory.json"; // cambiado
    private static Map<Element, Integer> data = new HashMap<>();

    public void loadFile() {
        data.clear(); // para evitar acumulación entre ejecuciones de test

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);

        ObjectMapper mapper = new ObjectMapper();
        try {
            InventoryJson[] items = mapper.readValue(stream, InventoryJson[].class);

            for (InventoryJson i : items) {
                Element element = new Element(
                    i.name.toUpperCase(Locale.ROOT),
                    Classification.valueOf(i.type.toUpperCase(Locale.ROOT))
                );
                data.put(element, i.quantity);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer el inventario JSON", e);
        }
    }

    public static Inventory getData() {
        return new Inventory(data);
    }
}
