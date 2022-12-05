package com.barneyb.aoc.util;

import kotlin.reflect.KCallable;
import lombok.val;

import java.io.*;
import java.net.URL;

public final class Input {

    private Input() {
        throw new UnsupportedOperationException("really?");
    }

    public static String forProblem(Class<?> clazz) {
        var res = clazz.getResource("input.txt");
        if (res == null) {
            var name = clazz.getName();
            var pkg = clazz.getPackageName();
            if (!pkg.isEmpty()) {
                name = name.substring(pkg.length() + 1); // for period
            }
            res = clazz.getResource(name + ".txt");
            if (res == null) {
                val idx = name.indexOf('$');
                if (idx >= 0) {
                    name = name.substring(0, idx);
                    res = clazz.getResource(name + ".txt");
                    if (res == null && name.endsWith("Kt")) {
                        name = name.substring(0, name.length() - 2);
                        res = clazz.getResource(name + ".txt");
                    }
                }
            }
        }
        if (res == null) {
            throw new RuntimeException("No input for '" + clazz.getName() + "' was found");
        }
        return asString(res);
    }

    @SuppressWarnings("unused") // useful for playing w/o Solver
    public static String forProblem(KCallable<?> fun) {
        return forProblem(fun.getClass());
    }

    public static String asString(String resourcePath) {
        val res = Thread.currentThread()
                .getContextClassLoader()
                .getResource(resourcePath);
        if (res == null) {
            throw new RuntimeException("No '" + resourcePath + "' was found");
        }
        return asString(res);
    }

    private static String asString(URL res) {
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
