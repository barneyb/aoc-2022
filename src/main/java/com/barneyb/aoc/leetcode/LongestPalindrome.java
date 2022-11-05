package com.barneyb.aoc.leetcode;

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

import com.barneyb.aoc.util.Chars;

public class LongestPalindrome {

    public static int solution(String str) {
        int pairCount = 0;
        int singleCount = 0;
        boolean[] singles = new boolean[64];
        for (char c : new Chars(str)) {
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

    public static void main(String[] args) {
        Assert.equal(7, solution("abccccdd"));
        Assert.equal(1, solution("a"));
        Assert.equal(0, solution(""));
        Assert.equal(7, solution("cdcbcdca"));
    }
}
