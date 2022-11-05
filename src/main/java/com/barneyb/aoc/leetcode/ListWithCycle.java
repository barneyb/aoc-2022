package com.barneyb.aoc.leetcode;

import lombok.AllArgsConstructor;
import lombok.val;

import static com.barneyb.aoc.util.With.with;

public class ListWithCycle {

    @SafeVarargs
    public static <E> Node<E> of(E... values) {
        Node<E> head = null;
        Node<E> curr = null;
        for (val v : values) {
            if (curr == null) {
                curr = head = new Node<>(v);
            } else {
                curr.next = new Node<>(v);
                curr = curr.next;
            }
        }
        return head;
    }

    @AllArgsConstructor
    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }
    }

    static <E> Node<E> tail(Node<E> head) {
        if (head == null) return head;
        var prev = head;
        for (var curr = head.next; curr != null; prev = curr, curr = curr.next)
            ;
        return prev;
    }

    static boolean hasCycle(Node<?> head) {
        if (head == null) return false;
        var slow = head;
        var fast = head.next;
        int n = 0;
        while (fast != null) {
            n++;
            slow = slow.next;
            fast = fast.next;
            if (fast == null) {
                break;
            }
            if (slow == fast) {
                throw new UnsupportedOperationException("?");
            }
            fast = fast.next;
            if (slow == fast) {
                return true;
            }
        }
        return false; // reached end
    }

    public static void main(String[] args) {
        val one = of(1, 2, 3, 4, 5, 6);
        System.out.println(hasCycle(one));
        with(one.next.next, three ->
                tail(three).next = three
        );
        System.out.println(hasCycle(one));
        val zero = of(0, 1, 2, 3, 4, 5, 6);
        System.out.println(hasCycle(zero));
        with(zero.next.next.next.next, four ->
                tail(four).next = four
        );
        System.out.println(hasCycle(zero));
    }

}
