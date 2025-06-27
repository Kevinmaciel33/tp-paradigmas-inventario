package org.unlam.paradigmas.zeta;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class WorkerTest {

    Inventory inventory;
    RecipeBook recipeBook;
    Worker w;
    private static Recipe RECIPE;

    @BeforeAll
    public static void setUp() {
        RECIPE = new Recipe(
            new Element("C"),
            15,
            List.of(
                new Element("H"),
                new Element("H"),
                new Element("H")
            )
        );
    }

    @BeforeEach
    public void setup() {
        this.inventory = mock(Inventory.class);
        this.recipeBook = mock(RecipeBook.class);
        w = new Worker(inventory, recipeBook);
    }

    @Test
    public void whenCraftElementShouldCheckInventoryAndAddElement() {
        when(this.inventory.hasElement(any())).thenReturn(true);

        w.create(new Element("C"), RECIPE);

        verify(this.inventory, times(1)).add(eq(new Element("C")), eq(1));
    }

    @Test
    public void whenCraftElementWithoutAllIngredientsShouldNotCreateTheElement() {
        when(this.inventory.hasElement(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> w.create(new Element("C"), RECIPE));

        verify(this.inventory, never()).add(eq(new Element("C")), eq(1));
    }

    @Test
    public void whenCraftElementAndNoRecipeIsFoundShouldNotAddElementsToInventory() {
        when(this.recipeBook.getLibraries()).thenReturn(
            List.of()
        );

        verify(this.inventory, never()).add(any(Element.class), anyInt());
        assertThrows(IllegalArgumentException.class, () -> w.create(new Element("H"), RECIPE));
    }

    @Test
    public void whenCraftElementIsNull() {

        verify(this.inventory, never()).add(any(Element.class), anyInt());
        assertThrows(IllegalArgumentException.class, () -> w.create(null, RECIPE));
    }

    @Test
    public void whenCraftMultiRecipesSelectingTheTable() {

        when(this.inventory.hasElement(any())).thenReturn(true);

        w.create(new Element("C"), new Recipe(
                new Element("C"),
                15,
                List.of(
                    new Element("H"),
                    new Element("H"),
                    new Element("H")
                )
            )
        );

        verify(this.inventory).add(any(Element.class), anyInt());
        verify(this.inventory, times(3)).remove(any(Element.class));

    }

    @Test
    public void whenCraftMultiRecipesSelectingAlternativeTable() {

        when(this.inventory.hasElement(any())).thenReturn(true);

        w.create(new Element("C"), new Recipe(
                new Element("C"),
                10,
                List.of(
                    new Element("H"),
                    new Element("H")
                )
            )
        );

        verify(this.inventory).add(any(Element.class), anyInt());
        verify(this.inventory, times(2)).remove(any(Element.class));
    }
}