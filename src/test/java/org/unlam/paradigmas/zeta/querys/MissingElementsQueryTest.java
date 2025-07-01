package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.MissingBasicIngredients;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.Inventory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MissingElementsQueryTest {

    private MissingElementsQuery query;
    private List<Library> libraries;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        InventoryLoader inventoryLoader = new InventoryLoader();
        inventory = inventoryLoader.loadFile();

        RecipeLoader recipeLoader = new RecipeLoader();
        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.getLibraries();

        query = new MissingElementsQuery(inventory);
    }

    @Test
    void waterWithAllIngredients() {
    	
        Element agua = new Element("AGUA", Classification.ALL);
        
        MissingBasicIngredients result = query.run(agua, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de agua");
        assertEquals("AGUA", result.getElementName(), "Debería ser la receta de agua");
        
        List<Map<String, Integer>> missingElementsList = result.getMissingElementsList();
        assertEquals(2, missingElementsList.size(), "Debería encontrar 2 recetas de agua");
        
        for (Map<String, Integer> missingElements : missingElementsList) {
            assertTrue(missingElements.isEmpty(), "No debería faltar ningún elemento en ninguna receta");
        }
    }

    @Test
    void testRecipeDoesNotExist() {
        Element elementoNoExistente = new Element("ELEMENTO_FAKE", Classification.ALL);
        assertThrows(IllegalArgumentException.class, () -> query.run(elementoNoExistente, libraries));
    }


    @Test
    void dioxidoCarbonoWithNoIngredients() {
        Element dioxidoDeCarbono = new Element("DIOXIDO_CARBONO", Classification.ALL);
        MissingBasicIngredients result = query.run(dioxidoDeCarbono, libraries);
        assertNotNull(result, "Debería encontrar la receta de dioxido de carbono");
        Map<String, Integer> missing = result.getMissingElementsList().get(0);
        assertEquals(1, missing.get("O"), "Debería faltar un átomo de oxigeno");
    }

    @Test
    void acidoCarnicoWithNoIngredientsForTwoRecipes() {
        Element acidoCarbonico = new Element("ACIDO_CARBONICO", Classification.ALL);
        MissingBasicIngredients result = query.run(acidoCarbonico, libraries);
       
        List<Map<String, Integer>> missingElementsList = result.getMissingElementsList();
        assertEquals(2, missingElementsList.size(), "Debería encontrar 2 recetas de acido carbonico");
        
        Map<String, Integer> firstRecipe = missingElementsList.get(0);
        assertEquals(1, firstRecipe.get("AGUA"),"Debería faltar agua");
        
        Map<String, Integer> secondRecipe = missingElementsList.get(1);
        assertEquals(1, secondRecipe.get("AGUA"), "Debería faltar agua");
        assertEquals(1, secondRecipe.get("DIOXIDO_CARBONO"), "Debería faltar dioxido de carbono");
    }

    @Test
    void acidosulfuricoWithNoIngredients() {
        Element acidoSulfurico = new Element("ACIDO_SULFURICO_CONCENTRADO", Classification.ALL);
        MissingBasicIngredients result = query.run(acidoSulfurico, libraries);
        assertNotNull(result, "Debería encontrar la receta de acido sulfurico concentrado");
        Map<String, Integer> missing = result.getMissingElementsList().get(0);
        assertEquals(1, missing.get("SULFURICO"), "Debería faltar sulfurico");
        assertEquals(1, missing.get("AGUA_OXIGENADA"), "Debería faltar agua oxigenada");
    }

    @Test
    void acidoRadioactivoWithNoIngredients() {
        Element acidoRadioactivo = new Element("PLUTONIO", Classification.ALL);
        MissingBasicIngredients result = query.run(acidoRadioactivo, libraries);
        assertNotNull(result, "Debería encontrar la receta de acido radioactivo");
        Map<String, Integer> missing = result.getMissingElementsList().get(0);
        assertEquals(1, missing.get("ACIDO_SUPER_CONCENTRADO"), "Debería faltar acido super concentrado");
        assertEquals(1, missing.get("SULFATO_HIERRO"), "Debería faltar sulfato_hierro");
    }
}