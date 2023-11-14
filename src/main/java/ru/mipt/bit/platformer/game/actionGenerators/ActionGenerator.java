package ru.mipt.bit.platformer.game.actionGenerators;

public interface ActionGenerator {
    void apply();
    boolean set(Object object);
}
