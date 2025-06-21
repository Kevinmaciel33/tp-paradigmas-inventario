package org.example.querys;

import org.example.RecipeBook;
import org.example.enums.Classification;
import org.example.loaders.RecipeLoader;
import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryElementsFromZeroTest {

    private QueryElementsFromZero query;
    private ElementCounter elementCounter;
    private List<Library> libraries;

    @BeforeEach
    void setUp() {
        // Cargar archivo de recetas
        RecipeLoader recipeLoader = new RecipeLoader();
        
        //inventoryLoader.loadFile();
        recipeLoader.loadFile();
        
        RecipeBook recipeBook = RecipeLoader.getData();
        libraries = recipeBook.libraries;
        
        //Se crea la consulta 
        query = new QueryElementsFromZero();
        elementCounter = new ElementCounter();
    }

    @Test
    void testAgua() {

        Element agua = new Element("AGUA", Classification.ALL);
        
        Recipe result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.give().name(), "Debería ser la receta de agua");

        assertEquals(3, result.ingredients().size(), "La receta de agua debería tener 3 ingredientes");
        
        long countO = elementCounter.countBasicElement(result, "O", libraries);
        long countH = elementCounter.countBasicElement(result, "H", libraries);
        
        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(1, countO, "Debería haber exactamente 1 átomo de oxígeno");
    }
    
    @Test
    void TestDioxidoCarbono() {
    	
    	Element dioxidoDeCarbono = new Element("DIOXIDO_DE_CARBONO", Classification.ALL);

        Recipe result = query.run(dioxidoDeCarbono, libraries);

        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        assertEquals("DIOXIDO_DE_CARBONO", result.give().name(), "Debería ser la receta de dioxido de carbono");

        long countO = elementCounter.countBasicElement(result, "O", libraries);
        long countC = elementCounter.countBasicElement(result, "C", libraries);
        
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
        assertEquals(2, countO, "Debería haber exactamente 2 átomos de oxígeno");
    }
    
    @Test
    void TestAcidoCarbonico() {
    	Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);

        Recipe result = query.run(acidoCarbonico, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido carbonico");
        assertEquals("ACIDO_CARBONICO", result.give().name(), "Debería ser la receta de acido carbonico");

        long countH = elementCounter.countBasicElement(result, "H", libraries);
        long countO = elementCounter.countBasicElement(result, "O", libraries);
        long countC = elementCounter.countBasicElement(result, "C", libraries);

        assertEquals(2, countH, "Debería haber exactamente 2 átomos de hidrógeno");
        assertEquals(3, countO, "Debería haber exactamente 3 átomos de oxígeno");
        assertEquals(1, countC, "Debería haber exactamente 1 átomo de carbono");
    }
   
    @Test
    void testSuperElemento() {
    	Element elemSuper = new Element("SUPER_ELEMENTO", Classification.ALL);

        Recipe result = query.run(elemSuper, libraries);

        assertNotNull(result, "Debería encontrar la receta de elemento ficticio");
        assertEquals("SUPER_ELEMENTO", result.give().name(), "Debería ser la receta de super elemento");
    }
    
    @Test 
    void testElementoNoExistenteEnReceta() {
    	Element elemNoExistente = new Element("VENENO", Classification.ALL);

        Recipe result = query.run(elemNoExistente, libraries);
        
        assertNull(result, "Deberia ser null");  
    }
    
    @Test 
    void testAmoniaco() {
    	Element elemAmoniaco = new Element("AMONIACO", Classification.ALL);

        Recipe result = query.run(elemAmoniaco, libraries);

        assertNotNull(result, "Debería encontrar la receta de elemento ficticio");
        assertEquals("AMONIACO", result.give().name(), "Debería ser la receta de elemento amoniaco");
    }
}