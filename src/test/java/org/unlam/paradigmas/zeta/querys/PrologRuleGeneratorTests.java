package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.unlam.paradigmas.zeta.loaders.InventoryJson;
import org.unlam.paradigmas.zeta.loaders.RecipeJson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PrologRuleGeneratorTests {
    
    private Projog engine;
    private static final String TEST_PROLOG_FILE = "src/test_prolog_rules.pl";
    private InventoryJson[] testInventory;
    private RecipeJson[] testRecipes;
    
    @BeforeEach
    void setUp() throws IOException {
        testInventory = loadInventoryFromFile("src/test/resources/inventory.json");
        testRecipes = loadRecipesFromFile("src/test/resources/recipes.json");
        
        PrologRuleGenerator.writeRulesInventoryToFile(testInventory, TEST_PROLOG_FILE, false);
        PrologRuleGenerator.writeRulesRecipeToFile(testRecipes, TEST_PROLOG_FILE, true);
        PrologRuleGenerator.writeCraftingRulesToFile(TEST_PROLOG_FILE, true);
        
        engine = new Projog();
        engine.consultFile(new File(TEST_PROLOG_FILE));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_PROLOG_FILE));
    }

    private InventoryJson[] loadInventoryFromFile(String path) throws IOException {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return mapper.readValue(new java.io.File(path), InventoryJson[].class);
    }
    
    private RecipeJson[] loadRecipesFromFile(String path) throws IOException {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return mapper.readValue(new java.io.File(path), RecipeJson[].class);
    }

    @Test
    void testCanCreate() {
        QueryResult result = engine.executeQuery("puedo_crear(agua).");
        assertTrue(result.next());
    }
    
    @Test
    void testCannotCreateWhenInsufficientResources() {
        QueryResult result = engine.executeQuery("puedo_crear(acido_carbonico).");
        //Teniendo en cuenta el inventario actual, se puede crear con la receta alternativa de acido carbonico
        assertTrue(result.next());
    }
    
    @Test
    void testCanCreateWithExactResources() {
        QueryResult result = engine.executeQuery("puedo_crear(sal).");
        assertTrue(result.next());
    }
    
    @Test
    void testCanCreateAguaSalada() {
        QueryResult result = engine.executeQuery("puedo_crear(agua_salada).");
        assertTrue(result.next());
    }
       
    @Test
    void testCannotCreateOxidoAluminio() {
        QueryResult result = engine.executeQuery("puedo_crear(oxido_aluminio).");
        assertFalse(result.next());
    }
    
    @Test
    void testInventoryElementTypes() {
        QueryResult result = engine.executeQuery("tengo(Elemento, Cantidad), Elemento = h.");
        assertTrue(result.next());
    }

    @Test
    void testCannotCreatePlutonio() {
        QueryResult result = engine.executeQuery("puedo_crear(plutonio).");
        assertFalse(result.next());
    }           
}
