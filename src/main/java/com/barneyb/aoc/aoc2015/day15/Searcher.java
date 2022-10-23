package com.barneyb.aoc.aoc2015.day15;

import com.barneyb.aoc.util.Pair;
import lombok.Getter;
import lombok.val;

import java.util.List;

/**
 * API:
 *
 * <pre>
 * {@link #Searcher(List) Searcher(List&lt;Ingredient&gt;)}
 * Ingredient {@link #getBestIngredient()}
 * Ingredient {@link #getBestCalorieConstrainedIngredient()}
 * </pre>
 */
class Searcher {

    @Getter
    private final Ingredient bestIngredient;

    @Getter
    private final Ingredient bestCalorieConstrainedIngredient;

    Searcher(List<Ingredient> ingredients) {
        val pair = findBest(Ingredient.NOTHING, ingredients, 100);
        bestIngredient = pair.getFirst();
        bestCalorieConstrainedIngredient = pair.getSecond();
    }

    private Pair<Ingredient, Ingredient> findBest(Ingredient basis, List<Ingredient> ingredients, int picking) {
        val candidate = ingredients.get(0);
        if (ingredients.size() == 1) {
            val ing = basis.sum(candidate.times(picking));
            return Pair.of(ing, ing);
        }
        Ingredient best = null, bestcc = null;
        for (var i = 0; i < picking; i++) {
            val pair = findBest(
                    basis.sum(candidate.times(i)),
                    ingredients.subList(1, ingredients.size()),
                    picking - i);
            var ing = pair.getFirst();
            if (best == null || best.getScore() < ing.getScore()) {
                best = ing;
            }
            ing = pair.getSecond();
            if (ing == null || ing.getCalories() != 500) continue;
            if (bestcc == null || bestcc.getScore() < ing.getScore()) {
                bestcc = ing;
            }

        }
        return Pair.of(best, bestcc);
    }
}
