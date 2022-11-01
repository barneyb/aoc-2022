package com.barneyb.aoc.util;

import lombok.val;

import java.io.*;

public final class Input {

    private Input() {
        throw new UnsupportedOperationException("really?");
    }

    public static String forProblem(Class<?> clazz) {
        var res = clazz.getResource("input.txt");
        if (res == null) {
            var name = clazz.getSimpleName();
            res = clazz.getResource(name + ".txt");
            if (res == null) {
                val idx = name.indexOf('$');
                if (idx >= 0) {
                    name = name.substring(0, idx);
                    res = clazz.getResource(name + ".txt");
                }
            }
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
