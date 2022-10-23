package com.barneyb.aoc.aoc2015.day15;

import lombok.Value;
import lombok.val;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

@Value
class Ingredient {

    public static int parses, constructs, sums, times;

    public static final Ingredient NOTHING = new Ingredient(new int[]{0, 0, 0, 0, 0});

    private static final Pattern RE_SPLITTER = Pattern.compile("[,: ]+[a-z ]+");

    static Ingredient parse(String spec) {
        parses++;
        val s = new Scanner(spec.trim())
                .useDelimiter(RE_SPLITTER);
        s.next(); // ignore name
        return new Ingredient(new int[]{
                s.nextInt(),
                s.nextInt(),
                s.nextInt(),
                s.nextInt(),
                s.nextInt()
        });
    }

    int[] properties;
    int score;

    Ingredient(int[] properties) {
        constructs++;
        this.properties = Arrays.copyOf(properties, properties.length);
        var score = 1;
        for (var i = 0; i < 4; i++) {
            val p = this.properties[i];
            if (p <= 0) {
                score = 0;
                break;
            }
            score *= p;
        }
        this.score = score;
    }

    Ingredient sum(Ingredient other) {
        sums++;
        val props = Arrays.copyOf(this.properties, this.properties.length);
        for (var i = 0; i < props.length; i++) {
            props[i] += other.properties[i];
        }
        return new Ingredient(props);
    }

    Ingredient times(int n) {
        times++;
        val props = Arrays.copyOf(this.properties, this.properties.length);
        for (var i = 0; i < props.length; i++) {
            props[i] *= n;
        }
        return new Ingredient(props);
    }
}
