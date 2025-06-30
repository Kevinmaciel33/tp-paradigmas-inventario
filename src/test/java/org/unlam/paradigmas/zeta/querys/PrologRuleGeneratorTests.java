package java.org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Recipe;
import org.unlam.paradigmas.zeta.RecipeBook;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrologRuleGeneratorTests {
    @Test
    public void TestCanCreate() {
        // Crear motor Prolog
        Projog engine = new Projog();

        // Cargar archivo con reglas y hechos
        engine.consultFile(new File("src/base.pl"));

        // Ejecutar consulta
        QueryResult result = engine.executeQuery("puedo_crear(agua).");

        // Verificar si hay al menos una solución
        boolean canBeCreated = result.next();

        // Afirmar que se puede crear el agua
        assertEquals(true, canBeCreated);
    }
    
}
