package org.unlam.paradigmas.zeta.models;

import java.util.List;

public record Recipe(Element give, float time, List<Element> ingredients) {
	public void mostrarReceta(Element give, List<Element> ingredients) {
		System.out.println("El elemento " + give + " se craftea con: ");
		for(Element e : ingredients) {
			System.out.println(e);
		}
	}

}