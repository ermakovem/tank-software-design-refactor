package ru.mipt.bit.platformer.actionGenerators.actions;

import ru.mipt.bit.platformer.actionGenerators.Action;

public class ToggleAction implements Action<Toggleable> {
    @Override
    public void apply(Toggleable toggleable) {
        toggleable.toggle();
    }

    @Override
    public Class<?> getRequiredInterface() {
        return Toggleable.class;
    }
}
