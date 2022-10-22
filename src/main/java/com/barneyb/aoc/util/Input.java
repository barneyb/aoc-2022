package com.barneyb.aoc.util;

import java.io.*;

public final class Input {

    private Input() {
        throw new UnsupportedOperationException("really?");
    }

    public static String forProblem(Class<?> clazz) {
        var res = clazz.getResource("input.txt");
        if (res == null) {
            res = clazz.getResource(clazz.getSimpleName() + ".txt");
        }
        if (res == null) {
            throw new RuntimeException("No input for " + clazz.getName() + " was found");
        }
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                ((InputStream) res.getContent()))) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return sb.toString();
    }

}
