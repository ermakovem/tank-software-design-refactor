package ru.mipt.bit.platformer.test;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.actionGenerators.Action;
import ru.mipt.bit.platformer.actionGenerators.actions.move.Direction;
import ru.mipt.bit.platformer.actionGenerators.actions.move.MoveAction;
import ru.mipt.bit.platformer.gameLogic.objects.Obstacle;
import ru.mipt.bit.platformer.gameLogic.objects.tank.Tank;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionTest {
    @Test
    void testApplyAction() {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f);
        Action a = new MoveAction(Direction.UP);
        Action b = new MoveAction(Direction.DOWN);

        //first has to apply, not second
        a.apply(tank);
        b.apply(tank);

        assertEquals(new GridPoint2(0, 1), tank.getDestinationCoordinates());

        tank.updateState(2f);
        b.apply(tank);

        assertEquals(new GridPoint2(-1, 1), tank.getDestinationCoordinates());
    }

    @Test
    void testActionsOnWrongObejcts() {
        GridPoint2 initCoords = new GridPoint2(2, 0);
        Obstacle tree = new Obstacle(initCoords);
        Action a = new MoveAction(Direction.UP);

        //has to not apply
        a.apply(tree);

        assertEquals(initCoords, tree.getDestinationCoordinates());
    }
}