package org.example;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class WorkerTest {

    Inventory inventory;
    RecipeBook recipeBook;
    Worker w;
    private static List<Library> LIBRARY;

    @BeforeAll
    public static void setUp() {
        LIBRARY = List.of(
            new Library(
                "base",
                new Recipe(
                    new Element("C"),
                    15,
                    List.of(
                        new Element("H"),
                        new Element("H"),
                        new Element("H")
                    )
                )
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
        when(this.recipeBook.getLibraries()).thenReturn(LIBRARY);
        when(this.inventory.hasElement(any())).thenReturn(true);

        w.create(new Element("C"));

        verify(this.inventory, times(3)).hasElement(any());
        verify(this.inventory, times(1)).add(eq(new Element("C")), eq(1));
    }

    @Test
    public void whenCraftElementWithoutAllIngredientsShouldNotCreateTheElement() {
        when(this.recipeBook.getLibraries()).thenReturn(LIBRARY);
        when(this.inventory.hasElement(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> w.create(new Element("C")));

        verify(this.inventory).hasElement(any());
        verify(this.inventory, never()).add(eq(new Element("C")), eq(1));
    }

    @Test
    public void whenCraftElementAndNoRecipeIsFoundShouldNotAddElementsToInventory() {
        when(this.recipeBook.getLibraries()).thenReturn(
            List.of()
        );

        verify(this.inventory, never()).add(any(Element.class), anyInt());
        assertThrows(RuntimeException.class, () -> w.create(new Element("H")));
    }

    @Test
    public void whenCraftElementIsNull() {
        when(this.recipeBook.getLibraries()).thenReturn(LIBRARY);

        verify(this.inventory, never()).add(any(Element.class), anyInt());
        assertThrows(RuntimeException.class, () -> w.create(null));
    }
}
