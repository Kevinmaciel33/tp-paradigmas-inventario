package org.example.models;

import java.util.List;

public record Recipe(float time, Element give, List<Element> ingredients) {
}
