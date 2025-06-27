package org.unlam.paradigmas.zeta.loaders;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class InventoryLoader implements Loader<Inventory> {
    private static final String PATH = "inventory.json"; // cambiado
    private final Map<Element, Integer> data = new HashMap<>();

    public Inventory loadFile() {
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

            return new Inventory(data);

        } catch (Exception e) {
            throw new RuntimeException("Error al leer el inventario JSON", e);
        }
    }
}
