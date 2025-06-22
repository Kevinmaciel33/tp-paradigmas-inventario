package org.unlam.paradigmas.zeta.models;

import org.unlam.paradigmas.zeta.enums.Classification;

import java.util.Objects;

public record Element(String name, Classification type) {

    @Override
    public boolean equals(Object o) {
        if (o instanceof Element e) {
            return name.equals(e.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

	@Override
	public String toString() {
		return name + " | " + "del tipo " + type;
	}
}
