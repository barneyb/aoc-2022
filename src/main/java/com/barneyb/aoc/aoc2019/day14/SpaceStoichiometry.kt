package com.barneyb.aoc.aoc2019.day14

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.benchmark(
        ::parse,
        { requiredOreForFuel(it, 1) },
        { fuelFromOre(it, ONE_TRILLION) },
//        { fuelFromOre2(it, ONE_TRILLION) },
    )
}

internal const val ONE_TRILLION = 1_000_000_000_000

internal const val ELEMENT_ORE = "ORE"
internal const val ELEMENT_FUEL = "FUEL"

internal typealias Element = String
internal typealias Recipes = Map<Element, Reaction>

internal data class Reactant(
    val quantity: Long,
    val element: Element,
) {
    companion object {

        fun parse(str: CharSequence): Reactant {
            // 1 FUEL
            val idx = str.indexOf(" ")
            return Reactant(
                str.substring(0, idx).toLong(),
                str.substring(idx + 1, str.length),
            )
        }
    }
}

internal data class Reaction(
    val ingredients: Collection<Reactant>,
    val result: Reactant,
) {
    companion object {
        fun parse(str: CharSequence): Reaction {
            // 7 A, 1 E => 1 FUEL
            val idx = str.indexOf("=>")
            return Reaction(
                str.subSequence(0, idx)
                    .split(",")
                    .map(String::trim)
                    .map(Reactant::parse),
                Reactant.parse(str.subSequence(idx + 3, str.length)),
            )
        }
    }
}

internal fun parse(input: String): Recipes =
    input.trim()
        .lines()
        .map(Reaction::parse)
        .associateBy { it.result.element }

internal fun requiredOreForFuel(recipes: Recipes, neededFuel: Long): Long {
    val pool = mutableMapOf<Element, Long>()
    val needed = mutableMapOf(ELEMENT_FUEL to neededFuel)
    while (true) {
        val el = needed.keys.firstOrNull { it != ELEMENT_ORE }
            ?: return needed[ELEMENT_ORE]!! // done!
        var toMake = needed.remove(el)!!
        if (pool.containsKey(el)) {
            val avail = pool.remove(el)!!
            if (avail >= toMake) {
                pool[el] = avail - toMake
                continue
            } else {
                toMake -= avail
            }
        }
        val recipe = recipes[el]!!
        var copies = toMake / recipe.result.quantity
        if (toMake % recipe.result.quantity != 0L) copies++
        val extra = recipe.result.quantity * copies - toMake
        recipe.ingredients.forEach {
            needed.merge(it.element, it.quantity * copies, Long::plus)
        }
        pool.merge(el, extra, Long::plus)
    }
}

internal fun fuelFromOre(recipes: Recipes, availOre: Long): Long {
    var fuel = 1L
    var ore = requiredOreForFuel(recipes, fuel)
    while (true) {
        val inc = (availOre - ore) * fuel / ore
        if (inc == 0L) break
        fuel += inc
        ore = requiredOreForFuel(recipes, fuel)
    }
    return fuel
}

@Suppress("unused") // play, not utility
internal fun fuelFromOre2(recipes: Recipes, availOre: Long): Long {
    // this is safe, since efficiency can only go UP w/ scale
    var lo = availOre / requiredOreForFuel(recipes, 1)
    var hi = availOre
    var mid: Long
    var ore: Long
    while (true) {
        mid = (lo + hi) / 2
        if (mid == lo) break
        ore = requiredOreForFuel(recipes, mid)
        if (ore < availOre) lo = mid
        else if (ore > availOre) hi = mid
    }
    return lo
}
