package ru.mipt.bit.platformer.test;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.actionGenerators.Action;
import ru.mipt.bit.platformer.actionGenerators.actions.MoveAction;
import ru.mipt.bit.platformer.gameLogic.GameLevel;
import ru.mipt.bit.platformer.gameLogic.objects.Obstacle;
import ru.mipt.bit.platformer.gameLogic.objects.tank.Tank;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLevelandCollisionHandlerTest {
    @Test
    void testCollisionHandler() {
        GameLevel level = new GameLevel();
        GridPoint2 tank1Coord = new GridPoint2(0, 0);
        GridPoint2 tank2Coord = new GridPoint2(1, 0);
        GridPoint2 obstacleCoord = new GridPoint2(0, 1);
        Tank tank1 = new Tank(tank1Coord, 1.0f, true);
        Tank tank2 = new Tank(tank2Coord, 1.0f, true);
        Obstacle tree = new Obstacle(obstacleCoord);
        Action a = MoveAction.UP;
        Action b = MoveAction.RIGHT;
        Action c = MoveAction.DOWN;

        level.add(tank1);
        level.add(tank2);
        level.add(tree);

        //move to occupied point
        a.apply(tank1);
        level.updateState(1.0f);

        assertEquals(tank1.getCoordinates(), tank1Coord);

        //tank2 moves to (1; 1)
        a.apply(tank2);

        assertEquals(tank2.getDestinationCoordinates(), new GridPoint2(1, 1));

        //tank1 moves to tank2 current coordinate
        b.apply(tank1);

        assertEquals(tank1.getDestinationCoordinates(), tank1.getCoordinates());

    }
}