package com.barneyb.aoc.aoc2015.day15;

import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.val;

import java.util.List;
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
        highestTotalScore = scoreOfBest(Ingredient.NOTHING, ingredients, 100);
        System.out.printf("parses: %d, constructs: %d, sums: %d, times: %d%n",
                Ingredient.parses, Ingredient.constructs, Ingredient.sums, Ingredient.times);
    }

    private int scoreOfBest(Ingredient basis, List<Ingredient> ingredients, int picking) {
        if (ingredients.size() == 1) {
            return basis.sum(ingredients.get(0).times(picking)).getScore();
        }
        int best = -1;
        for (var i = 0; i < picking; i++) {
            val ing = basis.sum(ingredients.get(0).times(i));
            val score = scoreOfBest(ing, ingredients.subList(1, ingredients.size()), picking - i);
            if (best < score) {
                best = score;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Solver.execute(ScienceForHungryPeople.class,
                ScienceForHungryPeople::getHighestTotalScore);
    }

}
