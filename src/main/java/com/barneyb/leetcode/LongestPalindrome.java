package com.barneyb.leetcode;

/*
Given a string s which consists of lowercase or uppercase letters,
return the length of the longest palindrome that can be built with those letters.

Letters are case sensitive, for example, "Aa" is not considered a palindrome here.

Example 1:

Input: s = "abccccdd"
Output: 7
Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.

Example 2:

Input: s = "a"
Output: 1
Explanation: The longest palindrome that can be built is "a", whose length is 1.
*/

import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import static com.barneyb.aoc.util.Input.asString;
import static com.barneyb.util.Timing.benchNanos;

public class LongestPalindrome {

    private static String stringOfLetters(int length) {
        var r = new Random();
        var sb = new StringBuilder();
        for (var i = 0; i < length; i++) {
            sb.append((char) ((r.nextBoolean() ? 'A' : 'a') + r.nextInt(26)));
        }
        return sb.toString();
    }

    public static int barney(String str) {
        int pairCount = 0;
        int singleCount = 0;
        boolean[] singles = new boolean[64];
        for (char c : str.toCharArray()) {
            int idx = c - 'A';
            singles[idx] = !singles[idx];
            if (singles[idx]) {
                singleCount++;
            } else {
                pairCount++;
                singleCount--;
            }
        }
        return pairCount * 2 + (singleCount != 0 ? 1 : 0);
    }

    public static int barneyWithArrayCopy(String str) {
        int pairCount = 0;
        int singleCount = 0;
        boolean[] singles = new boolean[64];
        for (char c : str.toCharArray()) {
            int idx = c - 'A';
            singles[idx] = !singles[idx];
            if (singles[idx]) {
                singleCount++;
            } else {
                pairCount++;
                singleCount--;
            }
        }
        return pairCount * 2 + (singleCount != 0 ? 1 : 0);
    }

    private static int brenna(String str) {
        int maxLength = 0;
        boolean hasMiddle = false;
        Map<Character, Integer> hist = new HashMap<>();
        for (char c : str.toCharArray()) {
            hist.put(c, hist.getOrDefault(c, 0) + 1);
        }
        // if all the letters are the same, just return the total length
        if (hist.keySet().size() == 1) {
            return str.length();
        }

        for (Integer count : hist.values()) {
            if (count % 2 == 0) {
                maxLength += count;
            } else {
                hasMiddle = true;
                maxLength += count - 1;
            }
        }
        if (hasMiddle) {
            return maxLength + 1;
        } else {
            return maxLength;
        }
    }

    /**
     * <pre>
     * = barney =======================================================================
     * abccccdd in ~155 ns (~19.38n)
     * 1,000 letters in ~5,640 ns (~5.64n)
     * 10,000 letters in ~77,533 ns (~7.75n)
     * n=29,592 in ~257,007 ns (~8.69n)
     * failed 'hamlet_act1': Index -33 out of bounds for length 64
     * n=149,633 in ~1,069,022 ns (~7.14n)
     * failed 'hamlet': Index 65214 out of bounds for length 64
     * n=1,197,444 in ~8,089,382 ns (~6.76n)
     * failed 'ulysses': null
     * ================================================================================
     * = barney (array copy) ==========================================================
     * abccccdd in ~153 ns (~19.13n)
     * 1,000 letters in ~1,435 ns (~1.44n)
     * 10,000 letters in ~35,721 ns (~3.57n)
     * n=29,592 in ~141,626 ns (~4.79n)
     * failed 'hamlet_act1': Index -33 out of bounds for length 64
     * n=149,633 in ~696,330 ns (~4.65n)
     * failed 'hamlet': Index 65214 out of bounds for length 64
     * n=1,197,444 in ~5,680,029 ns (~4.74n)
     * failed 'ulysses': null
     * ================================================================================
     * = brenna =======================================================================
     * abccccdd in ~363 ns (~45.38n)
     * 1,000 letters in ~14,412 ns (~14.41n)
     * 10,000 letters in ~141,717 ns (~14.17n)
     * n=29,592 in ~435,521 ns (~14.72n)
     * hamlet_act1 in ~619,118 ns (~15.89n)
     * n=149,633 in ~2,167,142 ns (~14.48n)
     * hamlet in ~2,990,763 ns (~14.82n)
     * n=1,197,444 in ~17,052,630 ns (~14.24n)
     * ulysses in ~22,926,902 ns (~14.58n)
     * ================================================================================
     * </pre>
     */
    public static void main(String[] args) {
        whosit("barney", LongestPalindrome::barney);
        whosit("barney (array copy)", LongestPalindrome::barneyWithArrayCopy);
        whosit("brenna", LongestPalindrome::brenna);
    }

    private static void whosit(String label, Function<String, Integer> solution) {
        val thousand = asString("1000_letters.txt").trim();
        val tenThousand = asString("10000_letters.txt").trim();
        val hamlet_act1 = asString("hamlet_act1.txt");
        val hamlet = asString("hamlet.txt");
        val ulysses = asString("ulysses.txt");

        System.out.printf("= %s =%s%n", label, "=".repeat(Math.max(0, 80 - label.length() - 4)));
        Assert.equal(7, solution.apply("abccccdd"));
        Assert.equal(1, solution.apply("a"));
        Assert.equal(0, solution.apply(""));
        thinger(solution, "abccccdd", "abccccdd", 7, 100000);
        thinger(solution, "1,000 letters", thousand, 969, 10000);
        thinger(solution, "10,000 letters", tenThousand, 9975, 1000);
        thinger(solution, hamlet_act1, 29569, 1000);
        thinger(solution, "hamlet_act1", hamlet_act1, 38935, 1000);
        thinger(solution, hamlet, 149613, 1000);
        thinger(solution, "hamlet", hamlet, 201815, 1000);
        thinger(solution, ulysses, 1197415, 100);
        thinger(solution, "ulysses", ulysses, 1572199, 100);
        System.out.println("=".repeat(80));
    }

    private static void thinger(Function<String, Integer> solution, String str, int expected, int iterations) {
        str = str.replaceAll("[^a-zA-Z]+", "");
        thinger(solution, String.format("n=%,d", str.length()), str, expected, iterations);
    }

    private static void thinger(Function<String, Integer> solution, String label, String str, int expected, int iterations) {
        try {
            Assert.equal(expected, solution.apply(str));
        } catch (Exception e) {
            System.out.printf("failed '%s': %s%n", label, e.getMessage());
            return;
        }
        val r = benchNanos(iterations, () ->
                solution.apply(str));
        System.out.printf("%s in ~%,d ns (~%.2fn)%n", label, r.getSecond(), 1.0 * r.getSecond() / str.length());
    }
}
