package org.unlam.paradigmas.zeta.models;

import java.util.List;

public class QuantityElements implements Queryable {
    public static class Evaluation {
        public final String table;
        public final int number;
        public final float time;

        public Evaluation(String table, int number, float time) {
            this.table = table;
            this.number = number;
            this.time = time;
        }
    }

    List<Evaluation> evaluations;

    public QuantityElements(List<Evaluation> evaluation) {
        this.evaluations = evaluation;
    }

    @Override
    public String show() {
        StringBuilder sb = new StringBuilder();
        for (Evaluation e : evaluations) {
            sb.append("En la mesa ")
                .append(e.table)
                .append(" se pueden crear: ")
                .append(e.number)
                .append(" elementos en un total de ")
                .append(e.number*e.time)
                .append("ms\n");
        }

        return sb.toString();
    }
}
