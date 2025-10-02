package com.vwth.challenge.application;

import com.vwth.challenge.domain.*;
import com.vwth.challenge.infrastructure.CommandParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationServiceTest {

    @Test
    void testSingleRobotSimulation() {
        FactoryFloor floor = new FactoryFloor(5, 5);
        Robot robot = new Robot(1, 0, 0, Direction.NORTH);
        floor.addRobot(robot);

        CommandParser parser = new CommandParser();
        List<Command> commands = parser.parse("MRM");

        SimulationService service = new SimulationService(floor);
        boolean success = service.runCommands(robot, commands);

        assertTrue(success);
        assertEquals(1, robot.getX()); // moved right once
        assertEquals(1, robot.getY()); // moved north once
        assertEquals(Direction.EAST, robot.getDirection());
    }

    @Test
    void testMultipleRobotsSimulation() {
        FactoryFloor floor = new FactoryFloor(5, 5);

        Robot r1 = new Robot(1, 0, 0, Direction.NORTH);
        Robot r2 = new Robot(2, 2, 2, Direction.WEST);

        floor.addRobot(r1);
        floor.addRobot(r2);

        CommandParser parser = new CommandParser();
        List<Command> r1Commands = parser.parse("MRM");
        List<Command> r2Commands = parser.parse("LMM");

        SimulationService service = new SimulationService(floor);
        service.runMultipleRobots(List.of(
                new SimulationService.RobotInstructions(r1, r1Commands),
                new SimulationService.RobotInstructions(r2, r2Commands)));

        assertEquals(1, r1.getX());
        assertEquals(1, r1.getY());
        assertEquals(Direction.EAST, r1.getDirection());

        assertEquals(2, r2.getX()); // moved west twice
        assertEquals(0, r2.getY());
        assertEquals(Direction.SOUTH, r2.getDirection()); // after 'L'
    }
}
