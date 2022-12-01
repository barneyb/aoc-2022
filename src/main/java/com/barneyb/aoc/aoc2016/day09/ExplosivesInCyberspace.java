package com.barneyb.aoc.aoc2016.day09;

import com.barneyb.aoc.util.Solver;
import lombok.val;

import java.util.function.Function;

public class ExplosivesInCyberspace {

    static long len(String input) {
        char[] arr = input.toCharArray();
        return len(arr, 0, arr.length, Version.ONE);
    }

    static long len2(String input) {
        char[] arr = input.toCharArray();
        return len(arr, 0, arr.length, Version.TWO);
    }

    private static long len(char[] arr, int start, int end, Version version) {
        long result = 0;
        for (int i = start; i < end; i++) {
            char c = arr[i];
            if (c == '(') {
                val ix = indexOf(arr, 'x', i + 1);
                val ip = indexOf(arr, ')', ix + 1);
                val len = parseInt(arr, i + 1, ix);
                val expanded = version == Version.TWO
                        ? len(arr, ip + 1, ip + 1 + len, version)
                        : len;
                val times = parseInt(arr, ix + 1, ip);
                result += expanded * times;
                i = ip + len;
            } else if (!Character.isWhitespace(c)) {
                result += 1;
            }
        }
        return result;
    }

    private static int parseInt(char[] arr, int start, int end) {
        var result = 0;
        for (int i = start; i < end; i++) {
            result *= 10;
            result += arr[i] - '0';
        }
        return result;
    }

    private static int indexOf(char[] arr, char c, int start) {
        var result = -1;
        for (int i = start; i < arr.length; i++) {
            if (arr[i] == c) {
                return i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solver.execute(
                Function.identity(),
                ExplosivesInCyberspace::len,
                ExplosivesInCyberspace::len2);
    }

}
