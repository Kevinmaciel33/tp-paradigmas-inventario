package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissingElementsQueryTest {

    private MissingElementsQuery query;
    private List<Library> libraries;

    @BeforeEach
    void setUp() {
        RecipeLoader recipeLoader = new RecipeLoader();
        RecipeBook recipeBook = recipeLoader.loadFile();
        libraries = recipeBook.libraries;

        query = new MissingElementsQuery();
    }

    @Test
    void testFindExistingRecipe() {
        Element agua = new Element("AGUA", Classification.WATER);
        Recipe result = query.run(agua, libraries);

        assertNotNull(result, "Debería encontrar la receta para 'AGUA'");
        assertEquals("AGUA", result.give().name(), "La receta encontrada debería ser para 'AGUA'");
        assertEquals(2.0f, result.time(), "El tiempo de la receta debería ser 2.0");
        assertEquals(3, result.ingredients().size(), "La receta debería tener 3 ingredientes");
        assertTrue(result.ingredients().contains(new Element("H", Classification.ALL)), "Debería contener H");
        assertTrue(result.ingredients().contains(new Element("O", Classification.ALL)), "Debería contener O");
    }

    @Test
    void testRecipeDoesNotExist() {
        Element elementoNoExistente = new Element("ELEMENTO_FAKE", Classification.ALL);

        Recipe result = query.run(elementoNoExistente, libraries);

        assertNull(result, "No debería encontrar una receta para un elemento no existente");
    }

    @Test
    void testCaseInsensitivityOfElementName() {
        Element aguaLowerCase = new Element("agua", Classification.ALL);
        Recipe result = query.run(aguaLowerCase, libraries);

        assertNotNull(result, "Debería encontrar la receta para 'agua' sin importar las minúsculas");
        assertEquals("AGUA", result.give().name(), "La receta encontrada debería ser para 'AGUA' (nombre en mayúsculas)");
    }
}