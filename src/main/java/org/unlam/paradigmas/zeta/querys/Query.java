package org.unlam.paradigmas.zeta.querys;

import org.unlam.paradigmas.zeta.models.Queryable;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;

import java.util.List;

public interface Query<T extends Queryable> {
    T run(Element e, List<Library> l);
}
