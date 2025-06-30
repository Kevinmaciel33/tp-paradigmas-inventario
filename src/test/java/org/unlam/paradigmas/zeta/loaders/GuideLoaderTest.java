package org.unlam.paradigmas.zeta.loaders;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.models.Guide;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuideLoaderTest {
    Loader<Guide> loader = new GuideLoder();

    @Test
    void checkInventoryOfTestResource() {
        Guide g = loader.loadFile();

        assertEquals(15, g.getBase().size());
        assertEquals(15, g.getBase().size());
    }
}
