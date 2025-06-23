package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

public interface Query {
    Recipe run(Element e, List<Library> l);
}
