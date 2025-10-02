package com.vwth.challenge.application;

import com.vwth.challenge.domain.Command;
import com.vwth.challenge.domain.FactoryFloor;
import com.vwth.challenge.domain.Robot;

import java.util.List;

public class SimulationService {

    private final FactoryFloor floor;

    public SimulationService(FactoryFloor floor) {
        this.floor = floor;
    }

    /**
     * Run a sequence of commands for a single robot.
     * Returns true if all commands executed successfully, false if any failed.
     */
    public boolean runCommands(Robot robot, List<Command> commands) {
        boolean success = true;
        for (Command cmd : commands) {
            boolean executed = robot.executeCommand(cmd, floor);
            if (!executed) {
                success = false; // some command was blocked
            }
        }
        return success;
    }

    /**
     * Run commands for multiple robots, in order.
     * Each robot executes its sequence fully before the next one starts.
     */
    public void runMultipleRobots(List<RobotInstructions> instructions) {
        for (RobotInstructions ri : instructions) {
            runCommands(ri.robot(), ri.commands());
        }
    }

    /**
     * Helper record to group a robot with its command sequence.
     */
    public record RobotInstructions(Robot robot, List<Command> commands) {
    }
}
