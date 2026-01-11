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
        return new AocdInputSupplier().apply(clazz);
    }

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
