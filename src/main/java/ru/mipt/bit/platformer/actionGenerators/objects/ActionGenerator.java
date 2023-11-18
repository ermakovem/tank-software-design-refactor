package ru.mipt.bit.platformer.actionGenerators.objects;

public interface ActionGenerator {
    void apply();
    boolean set(Object object);
}
