package com.barneyb.aoc.leetcode;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Stickers {

    private final Hist[] stickers;

    public Stickers(Collection<String> stickers) {
        this.stickers = new Hist[stickers.size()];
        var i = 0;
        for (var s : stickers) {
            this.stickers[i++] = Hist.of(s);
        }
    }

    public int minNeededFor(String message) {
        var needed = Hist.of(message);
        var pool = Hist.empty();
        var stickerCount = 0;
        while (true) {
            if (needed.isEmpty()) return stickerCount; // done
            if (!pool.isEmpty()) needed.remove(pool);
            if (needed.isEmpty()) return stickerCount; // done
            var idx = bestSticker(needed);
            if (idx < 0) return -1; // can't do it
            stickerCount += 1;
            pool.add(needed.remove(stickers[idx]));
        }
    }

    private int bestSticker(Hist needed) {
        var bestIdx = -1;
        var bestOverlap = 0;
        for (var i = 0; i < stickers.length; i++) {
            var o = needed.sizeOfOverlap(stickers[i]);
            if (o <= bestOverlap) continue;
            bestIdx = i;
            bestOverlap = o;
        }
        return bestIdx;
    }

    public static void main(String[] args) {
        var abcxyz = new Stickers(List.of("abc", "xyz"));
        Assert.equal(3, abcxyz.minNeededFor("axx"));
        Assert.equal(3, abcxyz.minNeededFor("xyxyxy"));
        Assert.equal(4, abcxyz.minNeededFor("axyxyxy"));
        Assert.equal(5, abcxyz.minNeededFor("ayxyxyxy"));
        Assert.equal(4, new Stickers(List.of("aybc", "xyz")).minNeededFor("ayxyxyxy"));
        Assert.equal(2, new Stickers(List.of("aybc", "xyyyyyyyyyyz")).minNeededFor("ayyy"));
    }

}

class Hist {

    public static final int COUNT = 26;

    static Hist empty() {
        return new Hist();
    }

    static Hist of(String s) {
        var h = empty();
        s.chars().forEach(c -> h.count((char) c));
        return h;
    }

    private final int[] counts = new int[COUNT];
    private int total;

    void count(char c) {
        inc(c - 'a', 1);
    }

    private void inc(int i, int n) {
        counts[i] += n;
        total += n;
    }

    private void dec(int i, int n) {
        inc(i, -n);
    }

    boolean isEmpty() {
        return total == 0;
    }

    int sizeOfOverlap(Hist other) {
        var overlap = 0;
        for (int i = 0; i < COUNT; i++) {
            overlap += Math.min(counts[i], other.counts[i]);
        }
        return overlap;
    }

    Hist remove(Hist other) {
        var extra = new Hist();
        for (int i = 0; i < COUNT; i++) {
            var o = Math.min(counts[i], other.counts[i]);
            dec(i, o);
            extra.inc(i, o);
        }
        return extra;
    }

    void add(Hist other) {
        for (int i = 0; i < COUNT; i++) {
            assert counts[i] >= other.counts[i];
            inc(i, Math.min(counts[i], other.counts[i]));
        }
    }
}

class Assert {

    static void equal(Object expected, Object actual) {
        ok(Objects.equals(expected, actual), String.format("Expected '%s', but got '%s'", expected, actual));
    }

    static void ok(boolean test) {
        ok(test, "not ok!");
    }

    static void ok(boolean test, String message) {
        if (!test) {
            throw new AssertionError(message);
        }
    }

    static void nope(boolean test) {
        ok(!test, "ok? That's bad.");
    }

    static void nope(boolean test, String message) {
        ok(!test, message);
    }
}
