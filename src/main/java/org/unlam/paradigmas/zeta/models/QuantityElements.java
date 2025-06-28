package org.unlam.paradigmas.zeta.models;

public class QuantityElements implements Queryable {
    public final int number;
    public final float time;

    public QuantityElements(int number, float time) {
        this.number = number;
        this.time = time;
    }

    @Override
    public String show() {
        return "Se pueden crear: "+number+" elementos en un total de "+ time + "ms";
    }
}
