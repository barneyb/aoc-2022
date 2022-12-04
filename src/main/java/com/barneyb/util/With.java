package com.barneyb.util;

import java.util.function.Function;

public final class With {

    private With() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T, R> R with(T obj, Function<T, R> work) {
        return work.apply(obj);
    }

}
