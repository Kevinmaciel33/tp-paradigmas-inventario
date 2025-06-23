package org.unlam.paradigmas.zeta.crafters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AcidoCrafterTests {

    private AcidoCrafter crafter;
    private Inventory inventory;

    private Element elementoFinal;
    private Element ingrediente1;
    private Element ingrediente2;
    private Element catalizador;

    @BeforeEach
    void setUp() {
        crafter = new AcidoCrafter();

        // Ingredientes
        ingrediente1 = new Element("HCl", Classification.ACIDO);
        ingrediente2 = new Element("HClO4", Classification.ACIDO);
        catalizador = crafter.catalyst();

        // Producto final
        elementoFinal = new Element("MezclaÁcida", Classification.ACIDO);

        // Inventario con todos los ingredientes y catalizador
        Map<Element, Integer> stockInicial = new HashMap<>();
        stockInicial.put(ingrediente1, 1);
        stockInicial.put(ingrediente2, 1);
        stockInicial.put(catalizador, 1);

        inventory = new Inventory(stockInicial);
    }

    @Test
    void testTypeDevuelveACIDO() {
        assertEquals(Classification.ACIDO, crafter.type());
    }

    @Test
    void testCraftRemueveIngredientesYCatalizadorYAñadeElementoFinal() {
        Recipe receta = new Recipe(elementoFinal, 0, List.of(ingrediente1, ingrediente2));

        crafter.craft(elementoFinal, inventory, receta);

        // Verificamos que ingredientes y catalizador fueron removidos
        assertEquals(0, inventory.numberOf(ingrediente1), "ingrediente1 no fue removido correctamente");
        assertEquals(0, inventory.numberOf(ingrediente2), "ingrediente2 no fue removido correctamente");
        assertEquals(0, inventory.numberOf(catalizador), "catalizador no fue removido correctamente");

        // Verificamos que el producto final fue añadido con cantidad 2
        assertEquals(2, inventory.numberOf(elementoFinal), "elemento final no fue agregado correctamente");
    }
}
