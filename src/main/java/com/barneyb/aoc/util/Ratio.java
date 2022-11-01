package com.barneyb.aoc.util;

import lombok.Value;

@Value
public class Ratio {
    int numerator;
    int denominator;

    public Ratio sum(Ratio other) {
        return denominator == other.denominator ?
                new Ratio(
                        numerator + other.numerator,
                        denominator) :
                new Ratio(
                        numerator * other.denominator + other.numerator * denominator,
                        denominator * other.denominator)
                        .reduce();
    }

    public Ratio reduce() {
        var limit = Math.min(Math.abs(numerator), Math.abs(denominator));
        var n = numerator;
        var d = denominator;
        for (int i = 2; i <= limit; i++) {
            while (n % i == 0 && d % i == 0) {
                n /= i;
                d /= i;
                limit /= i;
            }
        }
        return n == numerator ? this : new Ratio(n, d);
    }

    /**
     * Two non-equal ratios are equivalent if they reduce to the same ratio.
     */
    public boolean equivalentTo(Ratio o) {
        if (this == o) return true;
        if (numerator == o.numerator) return equals(o);
        return reduce().equals(o.reduce());
    }

}
