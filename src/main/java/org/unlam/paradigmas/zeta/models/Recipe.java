package org.unlam.paradigmas.zeta.models;

import java.util.ArrayList;
import java.util.List;

public record Recipe(Element give, float time, List<Element> ingredients) implements Queryable {

	@Override
	public String show() {
		List<String> result = new ArrayList<>();
		for(Element e : ingredients) {
			result.add(e.toString());
		}

		return String.join("\n", result);
	}
}