package org.unlam.paradigmas.zeta.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventorySaver {

    public static void saveToFile(Inventory inventory, String path) {
        List<InventoryJson> output = new ArrayList<>();

        for (Map.Entry<Element, Integer> entry : inventory.getStock().entrySet()) {
            InventoryJson record = new InventoryJson();
            record.name = entry.getKey().name();
            record.type = entry.getKey().type().name();
            record.quantity = entry.getValue();

            output.add(record);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // para pretty print
            mapper.writeValue(new File(path), output);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar inventario como JSON.", e);
        }
    }
}
