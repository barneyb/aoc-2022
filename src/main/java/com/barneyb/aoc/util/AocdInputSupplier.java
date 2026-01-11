package com.barneyb.aoc.util;

import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Function;

class AocdInputSupplier implements Function<Class<?>, String> {

    private static final String FILE_SYS_PROP = "aoc_all_file";

    @Override
    @SneakyThrows
    public String apply(Class<?> clazz) {
        String filename = System.getProperty(FILE_SYS_PROP);
        if (filename != null) {
            try (var in = new FileInputStream(filename)) {
                return createString(in);
            }
        }
        String[] parts = clazz.getPackageName()
                .split("\\.");
        int year = Integer.parseInt(parts[parts.length - 2].substring(3));
        int day = Integer.parseInt(parts[parts.length - 1].substring(3));
        try (var in = aocd(year, day)) {
            return createString(in);
        }
    }

    @SneakyThrows
    private static String createString(InputStream in) {
        try (var baos = new ByteArrayOutputStream()) {
            try (var out = new BufferedOutputStream(baos)) {
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
