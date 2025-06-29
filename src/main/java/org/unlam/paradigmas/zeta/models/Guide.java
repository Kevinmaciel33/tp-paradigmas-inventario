package org.unlam.paradigmas.zeta.models;

import java.util.List;

public class Guide {
    public record Page (String name, String description) {}
    private List<Page> base;
    private List<Page> create;

    public Guide() {}
    public Guide(List<Page> book, List<Page> creation) {
        this.base = book;
        this.create = creation;
    }

    public List<Page> getBase() {
        return base;
    }

    public List<Page> getCreate() {
        return create;
    }
}
