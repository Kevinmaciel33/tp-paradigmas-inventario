package org.example.models;

import org.example.enums.Classification;

import java.util.Objects;

public record Element(String name, Classification type) {

    public Element(String name) {
        this(name, Classification.ALL);
    }

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
}
