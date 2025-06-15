package org.example.enums;

import org.example.querys.Query;

public enum QueryEnum {
    ELEMENTS_FROM_ZERO(null),
    ELEMENTS(null),
    MISSING_ELEMENTS(null),
    HOW_MANY_ELEMENTS(null);

    public final Query query;

    QueryEnum(Query query) {
        this.query = query;
    }
}
