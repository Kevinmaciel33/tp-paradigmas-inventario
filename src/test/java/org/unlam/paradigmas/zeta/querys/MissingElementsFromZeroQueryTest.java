package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.RecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissingElementsFromZeroQueryTest {

    private MissingElementsFromZeroQuery query;
    private List<Library> libraries;
    private Inventory inventory;

    @BeforeEach
    void setUp() {

        InventoryLoader inventoryLoader = new InventoryLoader();
        inventory = inventoryLoader.loadFile();

        RecipeLoader recipeLoader = new RecipeLoader();
        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.getLibraries();

        query = new MissingElementsFromZeroQuery(inventory);
    }

    @Test
    void testAguaSePuedeCraftear() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("AGUA"), libraries);
        
        assertNotNull(result);
        assertTrue(result.getMissingElements().isEmpty());
    }

    @Test
    void testAcidoCarbonicoFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("ACIDO_CARBONICO"), libraries);
        
        assertNotNull(result);
        assertEquals(1, result.getMissingElements().size());
        
        assertTrue(result.getMissingElements().containsKey("O"));
        assertEquals(2, result.getMissingElements().get("O"));
        
        assertFalse(result.getMissingElements().containsKey("H"));
        assertFalse(result.getMissingElements().containsKey("C"));
    }

    @Test
    void testBicarbonatoFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("BICARBONATO"), libraries);
        
        assertNotNull(result);
        assertEquals(1, result.getMissingElements().size());
        
        assertTrue(result.getMissingElements().containsKey("O"));
        assertEquals(2, result.getMissingElements().get("O"));
        
        assertFalse(result.getMissingElements().containsKey("H"));
        assertFalse(result.getMissingElements().containsKey("C"));
        assertFalse(result.getMissingElements().containsKey("NA"));
    }

    @Test
    void testAcidoSulfuricoConcentradoFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("ACIDO_SULFURICO_CONCENTRADO"), libraries);
        
        assertNotNull(result);
        assertEquals(2, result.getMissingElements().size());
        
        assertEquals(2, result.getMissingElements().get("H"));
        assertEquals(5, result.getMissingElements().get("O"));
    }

    @Test
    void testSulfuricoConcentradoFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("ACIDO_SULFURICO_CONCENTRADO"), libraries);
        
        assertNotNull(result);
        assertTrue(result.getMissingElements().containsKey("H"));
        assertTrue(result.getMissingElements().containsKey("O"));
        assertFalse(result.getMissingElements().containsKey("S"));
        
        assertEquals(2, result.getMissingElements().get("H"));
        assertEquals(5, result.getMissingElements().get("O"));
        
        assertEquals(2, result.getMissingElements().size());
        
        String message = result.toString();
        assertTrue(message.contains("Para craftear ACIDO_SULFURICO_CONCENTRADO desde cero te faltan:"));
        assertTrue(message.contains("H: 2"));
        assertTrue(message.contains("O: 5"));
    }

    @Test
    void testExplosivoUltimoNivelFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("EXPLOSIVO_ULTIMO_NIVEL"), libraries);
        
        assertNotNull(result);
        assertEquals(2, result.getMissingElements().size());
        
        assertEquals(9, result.getMissingElements().get("H"));
        assertEquals(13, result.getMissingElements().get("O"));
    }

    @Test
    void testSulfatoHierroFaltanElementos() {
        
        MissingElementsFromZeroQuery result = query.run(new Element("SULFATO_HIERRO"), libraries);
        
        assertNotNull(result);
        assertEquals(1, result.getMissingElements().size());
        
        assertTrue(result.getMissingElements().containsKey("O"));
        assertEquals(3, result.getMissingElements().get("O"));
        
        assertFalse(result.getMissingElements().containsKey("H"));
        assertFalse(result.getMissingElements().containsKey("S"));
        assertFalse(result.getMissingElements().containsKey("FE"));
        
        String message = result.toString();
        assertTrue(message.contains("Para craftear SULFATO_HIERRO desde cero te faltan:"));
        assertTrue(message.contains("O: 3"));
    }
} 