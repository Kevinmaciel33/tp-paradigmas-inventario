package org.unlam.paradigmas.zeta.models;

import java.util.List;

public class QuantityElements implements Queryable {
    public final int number;

    public QuantityElements(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return  "QuantityElements [number=" + number + "]";
    }
}
