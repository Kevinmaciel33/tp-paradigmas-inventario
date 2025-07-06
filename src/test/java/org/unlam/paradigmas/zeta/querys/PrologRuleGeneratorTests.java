package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.unlam.paradigmas.zeta.loaders.InventoryJson;
import org.unlam.paradigmas.zeta.loaders.RecipeJson;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PrologRuleGeneratorTests {
    
    private Projog engine;
    
    @BeforeEach
    void setUp() throws IOException {
        InventoryLoader inventoryLoader = new InventoryLoader();
        RecipeLoader recipeLoader = new RecipeLoader();
        
        inventoryLoader.loadFile();
        recipeLoader.loadFile();
        
        PrologRuleGenerator.writeCraftingRulesToFile("src/base.pl", true);
        
        engine = new Projog();
        engine.consultFile(new File("src/base.pl"));
    }
    
    @AfterAll
    static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get("src/base.pl"));
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
    void testCannotCreatePlutonio() {
        QueryResult result = engine.executeQuery("puedo_crear(plutonio).");
        assertFalse(result.next());
    }           
}
