package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.Inventory;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.MissingBasicIngredients;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
    void testAguaConTodosLosIngredientes() {
    	
        Element agua = new Element("AGUA", Classification.ALL);
        
        MissingBasicIngredients result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.getElementName(), "Debería ser la receta de agua");
        
        assertFalse(result.hasMissingElements(), "No debería faltar ningún elemento");
        assertTrue(result.getMissingElements().isEmpty(), "La lista de elementos faltantes debería estar vacía");
    }
    
    @Test
    void testDioxidoCarbonoFaltaUnAtomoOxigeno() {
        Element dioxidoDeCarbono = new Element("DIOXIDO_CARBONO", Classification.ALL);
        
        MissingBasicIngredients result = query.run(dioxidoDeCarbono, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        assertEquals("DIOXIDO_CARBONO", result.getElementName(), "Debería ser la receta de dioxido de carbono");
        
        Map<String, Integer> missingElements = result.getMissingElements();
        assertEquals(1, missingElements.get("O"), "Debería faltar exactamente 1 átomo de oxígeno");
        assertEquals(1, missingElements.size(), "Debería faltar exactamente 1 tipos de elemento");
    }
    
    @Test
    void testAcidoCarbonicoFaltanDosAtomosDeOxigeno() {
        Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);
        
        MissingBasicIngredients result = query.run(acidoCarbonico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido carbonico");
        assertEquals("ACIDO_CARBONICO", result.getElementName(), "Debería ser la receta de acido carbonico");
        
        Map<String, Integer> missingElements = result.getMissingElements();
        assertEquals(2, missingElements.get("O"), "Debería faltar exactamente 2 átomos de oxígeno");
        assertEquals(1, missingElements.size(), "Debería faltar exactamente 1 tipos de elemento");
    }
    
    @Test
    void testAcidoSulfuricoConcentradoFaltanElementos() {
        Element acidoSulfurico = new Element("ACIDO_SULFURICO_CONCENTRADO", Classification.ALL);
        
        MissingBasicIngredients result = query.run(acidoSulfurico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido sulfurico concentrado");
        assertEquals("ACIDO_SULFURICO_CONCENTRADO", result.getElementName(), "Debería ser la receta de acido sulfurico concentrado");
        
        Map<String, Integer> missingElements = result.getMissingElements();
        assertEquals(2, missingElements.get("H"), "Debería faltar exactamente 2 átomos de hidrógeno");
        assertEquals(5, missingElements.get("O"), "Debería faltar exactamente 5 átomos de oxígeno");
        assertEquals(2, missingElements.size(), "Debería faltar exactamente 2 tipos de elementos");
    }
    
    @Test
    void testSulfatoHierroFaltanTresDeOxigeno() {
        Element sulfatoHierro = new Element("SULFATO_HIERRO", Classification.ALL);
        
        MissingBasicIngredients result = query.run(sulfatoHierro, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de sulfato de hierro");
        assertEquals("SULFATO_HIERRO", result.getElementName(), "Debería ser la receta de sulfato de hierro");
        
        Map<String, Integer> missingElements = result.getMissingElements();
        assertEquals(3, missingElements.get("O"), "Debería faltar exactamente 3 átomos de oxígeno");
        assertEquals(1, missingElements.size(), "Debería faltar exactamente 1 tipos de elemento");
    }
    
    @Test
    void testExplosivoUltimoNivel() {
        Element explosivoUltimoNivel = new Element("EXPLOSIVO_ULTIMO_NIVEL", Classification.ALL);
        
        MissingBasicIngredients result = query.run(explosivoUltimoNivel, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de explosivo último nivel");
        assertEquals("EXPLOSIVO_ULTIMO_NIVEL", result.getElementName(), "Debería ser la receta de explosivo último nivel");
        
        Map<String, Integer> missingElements = result.getMissingElements();
        assertEquals(13, missingElements.get("O"), "Debería faltar exactamente 13 átomos de oxígeno");
        assertEquals(9, missingElements.get("H"), "Debería faltar exactamente 9 átomos de hidrógeno");
        
    }
} 