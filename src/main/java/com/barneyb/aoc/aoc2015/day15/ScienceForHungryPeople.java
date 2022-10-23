package com.barneyb.aoc.aoc2015.day15;

import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.val;

import java.util.stream.Collectors;

/**
 * Solver for AoC 2015 day 15. API:
 *
 * <pre>
 * {@link #ScienceForHungryPeople(String)}
 * int {@link #getHighestTotalScore()}
 * </pre>
 */
public class ScienceForHungryPeople {

    @Getter
    private final int highestTotalScore;

    public ScienceForHungryPeople(String input) {
        val ingredients = input.trim()
                .lines()
                .map(Ingredient::parse)
                .collect(Collectors.toList());
        highestTotalScore = new Searcher(ingredients)
                .getBestIngredient()
                .getScore();
        System.out.printf("constructs: %,d, adds: %,d, mults: %,d%n",
                Ingredient.constructs, Ingredient.sums, Ingredient.times);
    }

    public static void main(String[] args) {
        Solver.execute(ScienceForHungryPeople.class,
                ScienceForHungryPeople::getHighestTotalScore);
    }

}
