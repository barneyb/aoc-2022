package com.barneyb.aoc.util;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor
public class CharIterator implements Iterable<Character>, Iterator<Character> {

    private int i;
    private final String str;

    @Override
    public Iterator<Character> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return i < str.length();
    }

    @Override
    public Character next() {
        return str.charAt(i++);
    }

}
