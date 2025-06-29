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

import static org.unlam.paradigmas.zeta.Constants.FILE_INVENTORY_OUT;

public class InventorySaver {

    public static void saveToFile(Inventory inventory) {
        List<InventoryJson> output = new ArrayList<>();

        for (Map.Entry<Element, Integer> entry : inventory.getStock().entrySet()) {
            if ( entry.getValue() == 0 ) {
                continue;
            }

            InventoryJson record = new InventoryJson();
            record.name = entry.getKey().name();
            record.type = entry.getKey().type().name();
            record.quantity = entry.getValue();

            output.add(record);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            mapper.writeValue(new File(FILE_INVENTORY_OUT), output);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar inventario como JSON.", e);
        }
    }
}
