package com.barneyb.aoc.util

fun CharSequence.toInt() =
    Integer.parseInt(this, 0, length, 10)
