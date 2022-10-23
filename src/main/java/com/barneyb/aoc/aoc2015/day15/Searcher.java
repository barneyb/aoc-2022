package com.barneyb.aoc.aoc2015.day15;

import lombok.Getter;
import lombok.val;

import java.util.List;

/**
 * API:
 *
 * <pre>
 * {@link #Searcher(List) Searcher(List&lt;Ingredient&gt;)}
 * Ingredient {@link #getBestIngredient()}
 * </pre>
 */
class Searcher {

    @Getter
    private final Ingredient bestIngredient;

    Searcher(List<Ingredient> ingredients) {
        bestIngredient = findBest(Ingredient.NOTHING, ingredients, 100);
    }

    private Ingredient findBest(Ingredient basis, List<Ingredient> ingredients, int picking) {
        val candidate = ingredients.get(0);
        if (ingredients.size() == 1) {
            return basis.sum(candidate.times(picking));
        }
        Ingredient best = null;
        for (var i = 0; i < picking; i++) {
            val ing = findBest(
                    basis.sum(candidate.times(i)),
                    ingredients.subList(1, ingredients.size()),
                    picking - i);
            if (best == null || best.getScore() < ing.getScore()) {
                best = ing;
            }
        }
        return best;
    }
}
