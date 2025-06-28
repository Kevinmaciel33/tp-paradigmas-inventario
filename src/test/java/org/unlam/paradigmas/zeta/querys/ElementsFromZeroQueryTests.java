package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElementsFromZeroQueryTests {

    private ElementsFromZeroQuery query;
    private List<Library> libraries;

    @BeforeEach
    void setUp() {

        RecipeLoader recipeLoader = new RecipeLoader();

        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.getLibraries();

        query = new ElementsFromZeroQuery();
    }

    @Test
    void testAgua() {

        Element agua = new Element("AGUA", Classification.ALL);
        
        ElementsFromZeroQuery result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.getRecipe().give().name(), "Debería ser la receta de agua");

        assertEquals(3, result.getRecipe().ingredients().size(), "La receta de agua debería tener 3 ingredientes");
        
        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        long countH = QueryUtils.countBasicElement(result.getRecipe(), "H", libraries);
        
        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(1, countO, "Debería haber exactamente 1 átomo de oxígeno");
    }
    
    @Test
    void TestDioxidoCarbono() {
    	
    	Element dioxidoDeCarbono = new Element("DIOXIDO_CARBONO", Classification.ALL);

        ElementsFromZeroQuery result = query.run(dioxidoDeCarbono, libraries);

        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        assertEquals("DIOXIDO_CARBONO", result.getRecipe().give().name(), "Debería ser la receta de dioxido de carbono");

        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        long countC = QueryUtils.countBasicElement(result.getRecipe(), "C", libraries);
        
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
        assertEquals(2, countO, "Debería haber exactamente 2 átomos de oxígeno");
    }
    
    @Test
    void TestAcidoCarbonico() {
    	
    	Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);

        ElementsFromZeroQuery result = query.run(acidoCarbonico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido carbonico");
        assertEquals("ACIDO_CARBONICO", result.getRecipe().give().name(), "Debería ser la receta de acido carbonico");

        long countH = QueryUtils.countBasicElement(result.getRecipe(), "H", libraries);
        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        long countC = QueryUtils.countBasicElement(result.getRecipe(), "C", libraries);

        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(3, countO, "Debería haber exactamente 3 átomos de oxígeno");
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
    }
    
    @Test 
    void testElementoNoExistenteEnReceta() {
    	
    	Element elemNoExistente = new Element("VENENO", Classification.ALL);

    	IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
    	    query.run(elemNoExistente, libraries);
    	});

    	assertEquals("No recipe found to craft the element", ex.getMessage()); 
    }
    
    @Test 
    void testAmoniaco() {
    	
    	Element elemAmoniaco = new Element("AMONIACO", Classification.ALL);

        ElementsFromZeroQuery result = query.run(elemAmoniaco, libraries);

        assertNotNull(result, "Debería encontrar la receta de elemento ficticio");
        assertEquals("AMONIACO", result.getRecipe().give().name(), "Debería ser la receta de elemento amoniaco");
    }
    
    @Test
    void testAguaOxigenada() {
    	
        Element aguaOxigenada = new Element("AGUA_OXIGENADA", Classification.ALL);

        ElementsFromZeroQuery result = query.run(aguaOxigenada, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua oxigenada");
        assertEquals("AGUA_OXIGENADA", result.getRecipe().give().name(), "Debería ser la receta de agua oxigenada");

        assertEquals(4, result.getRecipe().ingredients().size(), "La receta de agua oxigenada debería tener 4 ingredientes");
        
        long countH = QueryUtils.countBasicElement(result.getRecipe(), "H", libraries);
        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        
        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(2, countO, "Debería haber exactamente 2 átomos de oxígeno");
    }
    
    @Test
    void testMezclaExplosiva() {
    	
        Element mezclaExplosiva = new Element("MEZCLA_EXPLOSIVA", Classification.ALL);

        ElementsFromZeroQuery result = query.run(mezclaExplosiva, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de mezcla explosiva");
        assertEquals("MEZCLA_EXPLOSIVA", result.getRecipe().give().name(), "Debería ser la receta de mezcla explosiva");

        assertEquals(2, result.getRecipe().ingredients().size(), "La receta de mezcla explosiva debería tener 2 ingredientes");
        
        long countH = QueryUtils.countBasicElement(result.getRecipe(), "H", libraries);
        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        long countC = QueryUtils.countBasicElement(result.getRecipe(), "C", libraries);
        
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
        assertEquals(6, countH, "Debería haber exactamente 6 átomos de hidrógeno");
        assertEquals(4, countO, "Debería haber exactamente 4 átomos de oxígeno");
    }
    
    @Test
    void testExplosivoUltimoNivel() {
    	
        Element explosivoUltimoNivel = new Element("EXPLOSIVO_ULTIMO_NIVEL", Classification.ALL);

        ElementsFromZeroQuery result = query.run(explosivoUltimoNivel, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de explosivo último nivel");
        assertEquals("EXPLOSIVO_ULTIMO_NIVEL", result.getRecipe().give().name(), "Debería ser la receta de explosivo último nivel");

        assertEquals(2, result.getRecipe().ingredients().size(), "La receta de explosivo último nivel debería tener 2 ingredientes");
        
        long countH = QueryUtils.countBasicElement(result.getRecipe(), "H", libraries);
        long countO = QueryUtils.countBasicElement(result.getRecipe(), "O", libraries);
        long countC = QueryUtils.countBasicElement(result.getRecipe(), "C", libraries);
        long countS = QueryUtils.countBasicElement(result.getRecipe(), "S", libraries);
        long countCl = QueryUtils.countBasicElement(result.getRecipe(), "CL", libraries);
        
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
        assertEquals(11, countH, "Debería haber exactamente 11 átomos de hidrógeno");
        assertEquals(14, countO, "Debería haber exactamente 14 átomos de oxígeno");
        assertEquals(1, countS, "Debería haber exactamente 1 átomo de azufre");
        assertEquals(1, countCl, "Debería haber exactamente 1 átomo de cloro");
    }
} 