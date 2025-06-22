package org.example.models;

import java.util.List;

public record Recipe(Element give, float time, List<Element> ingredients) implements Queryable {
}
