package com.barneyb.aoc.util;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor
public class Chars implements Iterable<Character> {

    private final String str;

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<>() {
            private int i;

            @Override
            public boolean hasNext() {
                return i < str.length();
            }

            @Override
            public Character next() {
                return str.charAt(i++);
            }
        };
    }

}
