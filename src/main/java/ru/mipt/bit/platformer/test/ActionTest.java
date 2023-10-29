package ru.mipt.bit.platformer.test;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.logic.objects.Obstacle;
import ru.mipt.bit.platformer.logic.objects.Tank;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    @Test
    void testApplyAction() {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f, true);
        Action a = MoveAction.UP;
        Action b = MoveAction.LEFT;

        //first has to apply, not second
        a.apply(tank);
        b.apply(tank);

        assertEquals(new GridPoint2(0, 1), tank.getDestinationCoordinates());

        tank.updateState(2f);
        b.apply(tank);

        assertEquals(new GridPoint2(-1 , 1), tank.getDestinationCoordinates());
    }

    @Test
    void testActionsOnWrongObejcts() {
        GridPoint2 initCoords = new GridPoint2(2, 0);
        Obstacle tree = new Obstacle(initCoords);
        Action a = MoveAction.UP;

        //has to not apply
        a.apply(tree);

        assertEquals(initCoords, tree.getDestinationCoordinates());
    }
}