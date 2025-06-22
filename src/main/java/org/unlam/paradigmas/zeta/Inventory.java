package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.models.Element;

import java.util.Map;

import static org.unlam.paradigmas.zeta.Constants.NONE;
import static org.unlam.paradigmas.zeta.Constants.SINGLE;

public class Inventory {

    private final Map<Element, Integer> stock;

    public Inventory(Map<Element, Integer> stock) {
        this.stock = stock;
    }

    public boolean hasElement(final Element e) {
        return stock.containsKey(e);
    }

    public int numberOf(final Element e) {
        return stock.getOrDefault(e, NONE);
    }

    public void add(final Element e, int q) {
        stock.put(e, q);
    }

    public void remove(final Element e) {
        this.remove(e, SINGLE);
    }

    public void remove(final Element e, int n) {
        final int i = stock.get(e);
        stock.put(e, i-n);
    }
}
