package org.example.loaders;

import org.example.Inventory;
import org.example.enums.Classification;
import org.example.models.Element;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.example.Constants.COMMA;
import static org.example.Constants.SLASH;

public class InventoryLoader implements Loader<Inventory> {
    private static final String PATH = "inventory.csv";
    private static Map<Element, Integer> data = new HashMap<>();

    public void loadFile() {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream stream = cl.getResourceAsStream(PATH);
        try(BufferedReader file = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = file.readLine()) != null) {
                if (!line.contains(SLASH)) {
                    String[] columns = line.split(COMMA);
                    data.put(
                        new Element(
                            columns[0].toUpperCase(Locale.ROOT),
                            Classification.valueOf(columns[1].toUpperCase(Locale.ROOT))
                        ),
                        Integer.parseInt(columns[2])
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Inventory getData() {
        return new Inventory(data);
    }
}
