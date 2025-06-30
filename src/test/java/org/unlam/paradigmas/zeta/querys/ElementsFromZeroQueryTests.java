package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.RecipeTree;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

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
        
        List<Recipe> recipes = result.getRecipes();
        assertEquals(2, recipes.size(), "Debería encontrar exactamente 2 recetas de agua");
        
        //agua "alternativa" esta compuesta por ["h", "o"]
        Recipe recipeAlternativa = recipes.get(0);
        assertEquals(2, recipeAlternativa.ingredients().size(), "La primera receta debería tener 2 ingredientes");
        
        Map<String, Long> basicElementsAlternativa = QueryUtils.countAllBasicElements(recipeAlternativa, libraries);
        assertEquals(1, basicElementsAlternativa.get("H"), "La receta alternativa debería tener exactamente 1 átomo de hidrógeno");
        assertEquals(1, basicElementsAlternativa.get("O"), "La receta alternativa debería tener exactamente 1 átomo de oxígeno");
        
        //agua "base" esta compuesta por ["h", "h", "o"]
        Recipe recipeBase = recipes.get(1);
        assertEquals(3, recipeBase.ingredients().size(), "La segunda receta debería tener 3 ingredientes");
        
        Map<String, Long> basicElementsBase = QueryUtils.countAllBasicElements(recipeBase, libraries);
        assertEquals(2, basicElementsBase.get("H"), "La receta base debería tener exactamente 2 átomos de hidrógeno");
        assertEquals(1, basicElementsBase.get("O"), "La receta base debería tener exactamente 1 átomo de oxígeno");
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

        List<Recipe> recipes = result.getRecipes();
        assertEquals(2, recipes.size(), "Debería encontrar exactamente 2 recetas de acido carbonico");
        
        //acido_carbonico "otra-tabla" esta compuesta por ["agua"]
        Recipe recipeOtraTabla = recipes.get(0);
        assertEquals(1, recipeOtraTabla.ingredients().size(), "La primera receta debería tener 1 ingrediente");
        
        Map<String, Long> basicElementsOtraTabla = QueryUtils.countAllBasicElements(recipeOtraTabla, libraries);
        assertEquals(2, basicElementsOtraTabla.get("H"), "La receta otra-tabla debería tener exactamente 2 átomos de hidrógeno");
        assertEquals(1, basicElementsOtraTabla.get("O"), "La receta otra-tabla debería tener exactamente 1 átomo de oxígeno");
        
        //acido_carbonico "base" esta compuesta por ["agua","dioxido_carbono"]
        Recipe recipeBase = recipes.get(1);
        assertEquals(2, recipeBase.ingredients().size(), "La segunda receta debería tener 2 ingredientes");
        
        Map<String, Long> basicElementsBase = QueryUtils.countAllBasicElements(recipeBase, libraries);
        assertEquals(2, basicElementsBase.get("H"), "La receta base debería tener exactamente 2 átomos de hidrógeno");
        assertEquals(3, basicElementsBase.get("O"), "La receta base debería tener exactamente 3 átomos de oxígeno");
        assertEquals(1, basicElementsBase.get("C"), "La receta base debería tener exactamente 1 átomo de carbono");
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
    
    @Test
    void testAcidoRadioactivo() {
        Element acidoRadioactivo = new Element("ACIDO_RADIOACTIVO", Classification.ALL);
        
        RecipeTree result = query.run(acidoRadioactivo, libraries);
        
        assertNotNull(result, "Debería encontrar la receta de acido radioactivo");
        assertEquals("ACIDO_RADIOACTIVO", result.getElementName(), "Debería ser la receta de acido radioactivo");

        List<Recipe> recipes = result.getRecipes();
        assertEquals(1, recipes.size(), "Debería encontrar exactamente 1 receta de acido radioactivo");
        
        Recipe recipe = recipes.get(0);
        assertEquals(2, recipe.ingredients().size(), "La receta debería tener 2 ingrediente");
        
        Element ingrediente = recipe.ingredients().get(0);
        System.out.println("Ingrediente usado: " + ingrediente.name());
        
        Map<String, Long> basicElements = QueryUtils.countAllBasicElements(recipe, libraries);
        System.out.println("Elementos básicos encontrados: " + basicElements);
        
    }
    
    @Test
    void testElementoInexistente() {
        Element elementoInexistente = new Element("ELEMENTO_INEXISTENTE", Classification.ALL);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> query.run(elementoInexistente, libraries),
            "Debería lanzar IllegalArgumentException cuando no encuentra recetas"
        );
    }
} 