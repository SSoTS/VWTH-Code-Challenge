package com.vwth.challenge.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotCommandTest {

    @Test
    void testExecuteCommands() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot robot = new Robot(1, 0, 0, Direction.NORTH);
        floor.addRobot(robot);

        robot.executeCommand(Command.MOVE, floor);   // (0,1)
        robot.executeCommand(Command.RIGHT, floor);  // EAST
        robot.executeCommand(Command.MOVE, floor);   // (1,1)

        assertEquals(1, robot.getX());
        assertEquals(1, robot.getY());
        assertEquals(Direction.EAST, robot.getDirection());
    }
}
