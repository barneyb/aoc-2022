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
 * int {@link #getBestScore()}
 * int {@link #getBestCalorieConstrainedScore()}
 * </pre>
 */
public class ScienceForHungryPeople {

    @Getter
    private final int bestScore;

    @Getter
    private final int bestCalorieConstrainedScore;

    public ScienceForHungryPeople(String input) {
        val ingredients = input.trim()
                .lines()
                .map(Ingredient::parse)
                .collect(Collectors.toList());
        val searcher = new Searcher(ingredients);
        bestScore = searcher
                .getBestIngredient()
                .getScore();
        bestCalorieConstrainedScore = searcher
                .getBestCalorieConstrainedIngredient()
                .getScore();
    }

    public static void main(String[] args) {
        Solver.execute(ScienceForHungryPeople::new,
                ScienceForHungryPeople::getBestScore,
                ScienceForHungryPeople::getBestCalorieConstrainedScore);
    }

}
