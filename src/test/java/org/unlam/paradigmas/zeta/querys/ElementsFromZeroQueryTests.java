package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.RecipeTree;
import org.unlam.paradigmas.zeta.models.Library;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ElementsFromZeroQueryTests {

    private ElementsFromZeroQuery query;
    private List<Library> libraries;

    @BeforeEach
    void setUp() {
        query = new ElementsFromZeroQuery();
        
        RecipeLoader recipeLoader = new RecipeLoader();
        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.getLibraries();
    }

    @Test
    void testAgua() {

        Element agua = new Element("AGUA", Classification.ALL);
        
        RecipeTree result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.getElementName(), "Debería ser la receta de agua");
        assertEquals("AGUA", result.getRecipe().give().name(), "Debería ser la receta de agua");

        assertEquals(3, result.getRecipe().ingredients().size(), "La receta de agua debería tener 3 ingredientes");
        
        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(2, basicElements.get("H"), "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(1, basicElements.get("O"), "Debería haber exactamente 1 átomo de oxígeno");
    }
    
    @Test
    void TestDioxidoCarbono() {
    	
    	Element dioxidoDeCarbono = new Element("DIOXIDO_CARBONO", Classification.ALL);

        RecipeTree result = query.run(dioxidoDeCarbono, libraries);

        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        assertEquals("DIOXIDO_CARBONO", result.getElementName(), "Debería ser la receta de dioxido de carbono");

        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(1, basicElements.get("C"), "Debería haber exactamente 1 átomo de carbono");
        assertEquals(2, basicElements.get("O"), "Debería haber exactamente 2 átomos de oxígeno");
    }
    
    @Test
    void TestAcidoCarbonico() {
    	
    	Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);

        RecipeTree result = query.run(acidoCarbonico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido carbonico");
        assertEquals("ACIDO_CARBONICO", result.getElementName(), "Debería ser la receta de acido carbonico");

        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(2, basicElements.get("H"), "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(3, basicElements.get("O"), "Debería haber exactamente 3 átomos de oxígeno");
        assertEquals(1, basicElements.get("C"), "Debería haber exactamente 1 átomo de carbono");
    }
    
    @Test
    void testAmoniaco() {
        Element amoniaco = new Element("AMONIACO", Classification.ALL);
        
        RecipeTree result = query.run(amoniaco, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de amoniaco");
        assertEquals("AMONIACO", result.getElementName(), "Debería ser la receta de amoniaco");
        
        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(3, basicElements.get("H"), "Debería haber exactamente 3 átomos de hidrógeno");
        assertEquals(1, basicElements.get("N"), "Debería haber exactamente 1 átomo de nitrógeno");
        assertEquals(2, basicElements.size(), "Debería haber exactamente 2 tipos de elementos básicos");
    }
    
    @Test
    void testAguaOxigenada() {
        Element aguaOxigenada = new Element("AGUA_OXIGENADA", Classification.ALL);
        
        RecipeTree result = query.run(aguaOxigenada, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua oxigenada");
        assertEquals("AGUA_OXIGENADA", result.getElementName(), "Debería ser la receta de agua oxigenada");
        
        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(2, basicElements.get("H"), "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(2, basicElements.get("O"), "Debería haber exactamente 2 átomos de oxígeno");
        assertEquals(2, basicElements.size(), "Debería haber exactamente 2 tipos de elementos básicos");
    }
    
    @Test
    void testMezclaExplosiva() {
        Element mezclaExplosiva = new Element("MEZCLA_EXPLOSIVA", Classification.ALL);
        
        RecipeTree result = query.run(mezclaExplosiva, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de mezcla explosiva");
        assertEquals("MEZCLA_EXPLOSIVA", result.getElementName(), "Debería ser la receta de mezcla explosiva");
        
        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(6, basicElements.get("H"), "Debería haber exactamente 6 átomos de hidrógeno");
        assertEquals(4, basicElements.get("O"), "Debería haber exactamente 4 átomo de oxígeno");
        assertEquals(3, basicElements.size(), "Debería haber exactamente 3 tipos de elementos básicos");
    }
    
    @Test
    void testExplosivoUltimoNivel() {
        Element explosivoUltimoNivel = new Element("EXPLOSIVO_ULTIMO_NIVEL", Classification.ALL);
        
        RecipeTree result = query.run(explosivoUltimoNivel, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de explosivo último nivel");
        assertEquals("EXPLOSIVO_ULTIMO_NIVEL", result.getElementName(), "Debería ser la receta de explosivo último nivel");
        
        Map<String, Long> basicElements = result.getBasicElementsCount();
        assertEquals(11, basicElements.get("H"), "Debería haber exactamente 11 átomos de hidrógeno");
        assertEquals(14, basicElements.get("O"), "Debería haber exactamente 14 átomo de oxígeno");
        assertEquals(1, basicElements.get("C"), "Debería haber exactamente 1 átomo de carbono");
        assertEquals(5, basicElements.size(), "Debería haber exactamente 5 tipos de elementos básicos");
    }
} 