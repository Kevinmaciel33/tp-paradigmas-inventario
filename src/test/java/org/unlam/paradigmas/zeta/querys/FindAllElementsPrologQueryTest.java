package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.AllElementsProlog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FindAllElementsPrologQueryTest {

    private Projog engine;

    @BeforeEach
    void setUp() throws IOException {
        InventoryLoader inventoryLoader = new InventoryLoader();
        RecipeLoader recipeLoader = new RecipeLoader();
        
        inventoryLoader.loadFile();
        recipeLoader.loadFile();
        
        PrologRuleGenerator.writeCraftingRulesToFile("src/base.pl", true);
        
        Files.copy(Paths.get("src/base.pl"), Paths.get("src/test_base.pl"));
        
        engine = new Projog();
        engine.consultFile(new File("src/test_base.pl"));
    }
    
    @AfterEach
    void detetePlFile() throws IOException {
        Files.deleteIfExists(Paths.get("src/test_base.pl"));
    }

    @Test
    void testFindallPuedoCrear() {
        QueryResult result = engine.executeQuery("puedo_crear(X).");
        
        assertNotNull(result, "El resultado no debería ser null");
        
        int count = 0;
        while (result.next()) {
            String elemento = result.getTerm("X").toString();
            System.out.println("  - " + elemento);
            count++;
        }
        
        //Segun el inventario de test, solo se pueden crear 10 elementos
        assertTrue(count == 10 );
    }

    @Test
    void testFindAllElementsPrologQuery() {
        FindAllElementsPrologQuery query = new FindAllElementsPrologQuery();
        AllElementsProlog result = query.run(null, null);
        
        assertNotNull(result);
        assertTrue(result.getElements().size() > 0);
    }
} 