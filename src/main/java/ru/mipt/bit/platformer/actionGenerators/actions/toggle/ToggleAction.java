package ru.mipt.bit.platformer.actionGenerators.actions.toggle;

import ru.mipt.bit.platformer.actionGenerators.Action;

public class ToggleAction implements Action<Toggleable> {
    @Override
    public void apply(Toggleable toggleable) {
        toggleable.toggle();
    }

    @Override
    public Class<Toggleable> getRequiredInterface() {
        return Toggleable.class;
    }
}
