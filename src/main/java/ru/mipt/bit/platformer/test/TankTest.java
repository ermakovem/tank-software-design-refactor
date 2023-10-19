package ru.mipt.bit.platformer.test;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.logic.objects.Tank;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {
    @Test
    void testTankMoveTo() {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f);
        GridPoint2 targetCoordinate = new GridPoint2(0, 1);
        tank.moveTo(targetCoordinate);

        assertEquals(tank.getDestinationCoordinates(), targetCoordinate);
        assertEquals(tank.getRotation(), 90f);
        assertEquals(tank.getMovementProgress(), 0f);
    }

    @Test
    void testTankUpdateState() {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.5f);
        GridPoint2 targetCoordinate = new GridPoint2(0, 1);
        tank.moveTo(targetCoordinate);

        tank.updateState(0.2f);
        assertEquals(tank.getMovementProgress(), 0.4f);

        tank.updateState(1.0f);
        assertEquals(tank.getCoordinates(), targetCoordinate);
    }
}