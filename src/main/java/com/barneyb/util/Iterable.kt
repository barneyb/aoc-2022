package com.barneyb.util

import java.util.*

fun <E> iterableEquals(one: Iterable<E>, another: Iterable<E>): Boolean {
    val a = one.iterator()
    val b = another.iterator()
    while (a.hasNext()) {
        if (a.next() != b.next()) {
            return false
        }
    }
    return true
}

fun iterableHashCode(obj: Iterable<*>) =
    obj.fold(0) { hc, it ->
        hc * 31 + Objects.hashCode(it)
    }
