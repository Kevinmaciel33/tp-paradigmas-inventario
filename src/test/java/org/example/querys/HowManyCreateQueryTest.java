package org.example.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Inventory;
import org.example.models.Element;
import org.example.models.Library;
import org.example.models.QuantityElements;
import org.example.models.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HowManyCreateQueryTest {

    Inventory inventory;

    Query<QuantityElements> howManyCreateQuery;

    @BeforeEach
    public void setup() {
        inventory = mock(Inventory.class);
        howManyCreateQuery = new HowManyCreateQuery(inventory);
    }

    @Test
    void whenNoRecipeIsFoundToTheElement() {

        when(inventory.numberOf(eq(new Element("H")))).thenReturn(4);
        when(inventory.numberOf(eq(new Element("O")))).thenReturn(3);

        QuantityElements q = howManyCreateQuery.run(
            new Element("A"),
            List.of(
                new Library(
                    "base",
                    new Recipe(
                        new Element("H"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("O")
                        )
                    )
                )
            )
        );

        assertEquals(0, q.number);
    }

    @Test
    void whenCouldCreateSeveralElements() {

        when(inventory.numberOf(eq(new Element("H")))).thenReturn(4);
        when(inventory.numberOf(eq(new Element("O")))).thenReturn(3);

        QuantityElements q = howManyCreateQuery.run(
            new Element("A"),
            List.of(
                new Library(
                    "base",
                    new Recipe(
                        new Element("A"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("O")
                        )
                    )
                )
            )
        );

        assertEquals(2, q.number);
    }

    @Test
    void whenOnlyCouldCreateOneElement() {

        when(inventory.numberOf(eq(new Element("H")))).thenReturn(4);
        when(inventory.numberOf(eq(new Element("O")))).thenReturn(1);

        QuantityElements q = howManyCreateQuery.run(
            new Element("A"),
            List.of(
                new Library(
                    "base",
                    new Recipe(
                        new Element("A"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("O")
                        )
                    )
                )
            )
        );

        assertEquals(1, q.number);
    }

    @Test
    void whenCouldNotCreateAnyElementWithoutNecessaryAmount() {

        when(inventory.numberOf(eq(new Element("H")))).thenReturn(1);
        when(inventory.numberOf(eq(new Element("O")))).thenReturn(1);

        QuantityElements q = howManyCreateQuery.run(
            new Element("A"),
            List.of(
                new Library(
                    "base",
                    new Recipe(
                        new Element("A"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("O")
                        )
                    )
                )
            )
        );

        assertEquals(0, q.number);
    }

    @Test
    void whenCouldNotCreateAnyElementMissingElement() {

        when(inventory.numberOf(eq(new Element("H")))).thenReturn(4);
        when(inventory.numberOf(eq(new Element("O")))).thenReturn(0);

        QuantityElements q = howManyCreateQuery.run(
            new Element("A"),
            List.of(
                new Library(
                    "base",
                    new Recipe(
                        new Element("A"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("O")
                        )
                    )
                )
            )
        );

        assertEquals(0, q.number);
    }
}
