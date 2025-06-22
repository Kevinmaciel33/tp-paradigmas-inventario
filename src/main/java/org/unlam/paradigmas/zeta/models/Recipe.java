package org.unlam.paradigmas.zeta.models;

import java.util.List;

public record Recipe(Element give, float time, List<Element> ingredients) {
}
