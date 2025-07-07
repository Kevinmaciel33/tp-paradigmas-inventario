package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.AllElementsProlog;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class FindAllElementsPrologQuery implements Query<AllElementsProlog> {
    
    @Override
    public AllElementsProlog run(Element element, List<Library> libraries) {
        try {
            Projog engine = new Projog();
            engine.consultFile(new File("src/base.pl"));
            
            List<String> elements = new ArrayList<>();
            QueryResult queryResult = engine.executeQuery("puedo_crear(X).");
            
            while (queryResult.next()) {
                String prologElement = queryResult.getTerm("X").toString();
                elements.add(prologElement);
            }
            
            return new AllElementsProlog(elements);
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la consulta Prolog: " + e.getMessage(), e);
        }
    }
} 