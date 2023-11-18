package ru.mipt.bit.platformer.actionGenerators.predicates;

import java.util.Random;
import java.util.function.Predicate;

import static java.lang.Math.abs;

public class RandomPredicate implements Predicate<Object> {
    private final int invChance;
    private final Random random = new Random();
    public RandomPredicate(float chance) {
        this.invChance = (int) (1 / chance);
    }
    @Override
    public boolean test(Object object) {
        return abs(random.nextInt()) % invChance == 0;
    }
}
