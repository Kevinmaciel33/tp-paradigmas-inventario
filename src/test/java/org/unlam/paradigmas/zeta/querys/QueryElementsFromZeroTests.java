package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryElementsFromZeroTests {

    private QueryElementsFromZero query;
    private List<Library> libraries;

    @BeforeEach
    void setUp() {

        RecipeLoader recipeLoader = new RecipeLoader();

        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.getLibraries();

        query = new QueryElementsFromZero();
    }

    @Test
    void testAgua() {

        Element agua = new Element("AGUA", Classification.ALL);
        
        Recipe result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.give().name(), "Debería ser la receta de agua");

        assertEquals(3, result.ingredients().size(), "La receta de agua debería tener 3 ingredientes");
        
        long countO = query.countBasicElement(result, "O", libraries);
        long countH = query.countBasicElement(result, "H", libraries);
        
        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(1, countO, "Debería haber exactamente 1 átomo de oxígeno");
    }
    
    @Test
    void TestDioxidoCarbono() {
    	
    	Element dioxidoDeCarbono = new Element("DIOXIDO_CARBONO", Classification.ALL);

        Recipe result = query.run(dioxidoDeCarbono, libraries);

        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        assertEquals("DIOXIDO_CARBONO", result.give().name(), "Debería ser la receta de dioxido de carbono");

        long countO = query.countBasicElement(result, "O", libraries);
        long countC = query.countBasicElement(result, "C", libraries);
        
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
        assertEquals(2, countO, "Debería haber exactamente 2 átomos de oxígeno");
    }
    
    @Test
    void TestAcidoCarbonico() {
    	Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);

        Recipe result = query.run(acidoCarbonico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido carbonico");
        assertEquals("ACIDO_CARBONICO", result.give().name(), "Debería ser la receta de acido carbonico");

        long countH = query.countBasicElement(result, "H", libraries);
        long countO = query.countBasicElement(result, "O", libraries);
        long countC = query.countBasicElement(result, "C", libraries);

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

        Recipe result = query.run(elemAmoniaco, libraries);

        assertNotNull(result, "Debería encontrar la receta de elemento ficticio");
        assertEquals("AMONIACO", result.give().name(), "Debería ser la receta de elemento amoniaco");
    }
}