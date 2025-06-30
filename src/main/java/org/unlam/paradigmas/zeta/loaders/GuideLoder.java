package org.unlam.paradigmas.zeta.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Guide;

import java.io.InputStream;
import java.util.*;


public class GuideLoder implements Loader<Guide> {
    private static final String PATH = "guide.json"; // cambiado

    public Guide loadFile() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ObjectMapper mapper = new ObjectMapper();

        try(InputStream stream = cl.getResourceAsStream(PATH)) {
            return mapper.readValue(stream, Guide.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el inventario JSON", e);
        }
    }
}
