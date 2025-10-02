package com.vwth.challenge.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void testRotateLeft() {
        Robot robot = new Robot(1, 0, 0, Direction.NORTH);
        robot.rotateLeft();
        assertEquals(Direction.WEST, robot.getDirection());
    }

    @Test
    void testRotateRight() {
        Robot robot = new Robot(1, 0, 0, Direction.NORTH);
        robot.rotateRight();
        assertEquals(Direction.EAST, robot.getDirection());
    }

    @Test
    void testMoveForward() {
        Robot robot = new Robot(1, 0, 0, Direction.NORTH);
        robot.moveForward();
        assertEquals(0, robot.getX());
        assertEquals(1, robot.getY());
    }
}
