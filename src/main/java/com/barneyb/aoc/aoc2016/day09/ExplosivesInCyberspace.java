package com.barneyb.aoc.aoc2016.day09;

import com.barneyb.aoc.util.Solver;
import lombok.val;

public class ExplosivesInCyberspace {

    static long decompress(String input) {
        var result = 0;
        char[] arr = input.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c == '(') {
                val ix = indexOf(arr, 'x', i + 1);
                val ip = indexOf(arr, ')', ix + 1);
                val len = Integer.parseInt(input, i + 1, ix, 10);
                val times = Integer.parseInt(input, ix + 1, ip, 10);
                result += len * times;
                i = ip + len;
            } else if (!Character.isWhitespace(c)) {
                result += 1;
            }
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
        Solver.execute(ExplosivesInCyberspace::decompress);
    }

}
