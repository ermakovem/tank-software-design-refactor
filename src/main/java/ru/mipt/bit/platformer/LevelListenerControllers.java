package ru.mipt.bit.platformer;

import org.lwjgl.system.CallbackI;
import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Tank;

import java.util.ArrayList;
import java.util.List;

public class LevelListenerControllers implements LevelListener {
    private final List<InputController> inputControllers = new ArrayList<>();
    private int inputControllerCounter = 0;

    private final List<AIController> aiControllers = new ArrayList<>();
    private int aiControllerCounter = 0;

    public LevelListenerControllers(ArrayList<Controller> controllers) {
        for (Controller controller : controllers) {
            if (controller instanceof InputController) {
                inputControllers.add((InputController) controller);
            }
            if (controller instanceof AIController) {
                aiControllers.add((AIController) controller);
            }
        }
    }

    @Override
    public void addObject(GameObject gameObject) {
        //if gameObject is Player tank -> to inputcontroller
        //if gameObject is AI tank -> to ai controller
        if (gameObject instanceof Tank) {
            if (((Tank) gameObject).isPlayer()) {
                inputControllers.get(inputControllerCounter++).addGameObject(gameObject);
            } else {
                aiControllers.get(aiControllerCounter++).addGameObject(gameObject);
            }
        }
    }
}
