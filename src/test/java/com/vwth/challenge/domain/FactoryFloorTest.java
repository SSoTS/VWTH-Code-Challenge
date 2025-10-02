package com.vwth.challenge.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryFloorTest {

    @Test
    void testAddRobotInsideBounds() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot r1 = new Robot(1, 0, 0, Direction.NORTH);

        assertTrue(floor.addRobot(r1));
        assertEquals(r1, floor.getRobotById(1));
    }

    @Test
    void testAddRobotOutOfBounds() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot r1 = new Robot(1, 10, 10, Direction.NORTH);

        assertFalse(floor.addRobot(r1)); // outside grid
    }

    @Test
    void testRobotMovesWithinBounds() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot r1 = new Robot(1, 0, 0, Direction.NORTH);

        assertTrue(floor.addRobot(r1));
        assertTrue(r1.executeCommand(Command.MOVE, floor)); // (0,1)

        assertEquals(0, r1.getX());
        assertEquals(1, r1.getY());
    }

    @Test
    void testRobotBlockedByBoundary() {
        FactoryFloor floor = new FactoryFloor(2, 2);
        Robot r1 = new Robot(1, 1, 1, Direction.NORTH);

        assertTrue(floor.addRobot(r1));
        assertFalse(r1.executeCommand(Command.MOVE, floor)); // would move outside
        assertEquals(1, r1.getX());
        assertEquals(1, r1.getY()); // stays in place
    }

    @Test
    void testRobotCollisionPrevention() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot r1 = new Robot(1, 0, 0, Direction.NORTH);
        Robot r2 = new Robot(2, 0, 1, Direction.SOUTH);

        assertTrue(floor.addRobot(r1));
        assertTrue(floor.addRobot(r2));

        // r1 tries to move into r2’s cell
        assertFalse(r1.executeCommand(Command.MOVE, floor));
        assertEquals(0, r1.getX());
        assertEquals(0, r1.getY()); // position unchanged
    }
}
