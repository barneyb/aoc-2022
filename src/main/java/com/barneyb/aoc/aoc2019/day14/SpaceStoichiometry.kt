package com.barneyb.aoc.aoc2019.day14

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        { requiredOre(it, Reactant(1, ELEMENT_FUEL)) },
    )
}

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

internal fun requiredOre(recipes: Recipes, goal: Reactant): Long {
    val pool = mutableMapOf<Element, Long>()
    val needed = mutableMapOf(goal.element to goal.quantity)
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
