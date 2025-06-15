package org.example.querys;

import org.example.models.Element;
import org.example.models.Library;
import org.example.models.Recipe;

import java.util.List;

public interface Query {
    Recipe run(Element e, List<Library> l);
}
