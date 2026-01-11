package com.barneyb.aoc.util;

import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Function;

class AocdInputSupplier implements Function<Class<?>, String> {

    @Override
    public String apply(Class<?> clazz) {
        String[] parts = clazz.getPackageName()
                .split("\\.");
        int year = Integer.parseInt(parts[parts.length - 2].substring(3));
        int day = Integer.parseInt(parts[parts.length - 1].substring(3));
        return createString(year, day);
    }

    @SneakyThrows
    private static String createString(int year, int day) {
        try (var baos = new ByteArrayOutputStream()) {
            try (var in = aocd(year, day);
                 var out = new BufferedOutputStream(baos)) {
                in.transferTo(out);
            }
            return baos.toString();
        }
    }

    @SneakyThrows
    private static InputStream aocd(int year, int day) {
        var proc = new ProcessBuilder("aocd", "" + year, "" + day)
                .start();
        if (proc.waitFor() != 0) {
            throw new RuntimeException("Failed to get input from aocd. Is your token expired (or missing)?");
        }
        return proc.getInputStream();
    }

}
