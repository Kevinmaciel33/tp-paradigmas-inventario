package org.example.querys;

import org.example.models.Queryable;
import org.example.models.Element;
import org.example.models.Library;

import java.util.List;

public interface Query<T extends Queryable> {
    T run(Element e, List<Library> l);
}
