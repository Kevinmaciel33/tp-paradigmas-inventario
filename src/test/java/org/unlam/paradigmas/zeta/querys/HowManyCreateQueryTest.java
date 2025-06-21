package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.QuantityElements;

import java.util.List;

import static org.mockito.Mockito.mock;

public class HowManyCreateQueryTest {

    Inventory inventory;

    Query<QuantityElements> howManyCreateQuery;

    @BeforeEach
    public void setup() {
        inventory = mock(Inventory.class);
        howManyCreateQuery = new HowManyCreateQuery(inventory);
    }

    @Test
    void test() {

        QuantityElements q = howManyCreateQuery.run(new Element(""), List.of());
    }
}
