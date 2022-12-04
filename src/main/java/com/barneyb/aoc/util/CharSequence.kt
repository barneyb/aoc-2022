package com.barneyb.aoc.util

fun CharSequence.toInt() =
    Integer.parseInt(this, 0, length, 10)

fun CharSequence.camelToTitle() =
    this
        // strip kotlin suffixes
        .replaceFirst(Regex("Kt$"), "")
        // last uppercase letter of a run
        .replace(Regex("(\\w)([A-Z][a-z])"), "$1 $2")
        // first uppercase letter of a run
        .replace(Regex("([a-z])([A-Z])"), "$1 $2")
        // stop word capitalization
        .replace(Regex(" (A|And|The|Of) ")) {
            it.value.lowercase()
        }
