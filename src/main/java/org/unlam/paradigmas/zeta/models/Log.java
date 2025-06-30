package org.unlam.paradigmas.zeta.models;

import java.time.LocalDateTime;

public class Log {
    public final LocalDateTime time;
    public final Recipe recipe;

    public Log(LocalDateTime time, Recipe recipe) {
        this.time = time;
        this.recipe = recipe;
    }
}
