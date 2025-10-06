package com.vwth.challenge.infrastructure;

import com.vwth.challenge.domain.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileInputAdapter {

    private final CommandParser parser = new CommandParser();

    public SimulationInput loadFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath))
                .stream()
                .filter(line -> !line.trim().isEmpty()) // ignore blank lines
                .toList();

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Error: input file is empty.");
        }

        // --- Parse grid size ---
        String[] size = lines.get(0).trim().split("\\s+");
        if (size.length != 2) {
            throw new IllegalArgumentException(
                    "Error: first line must contain two integers for grid size, e.g., '5 5'.");
        }

        int width, height;
        try {
            width = Integer.parseInt(size[0]);
            height = Integer.parseInt(size[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: grid size must be numeric (e.g., '5 5').");
        }

        FactoryFloor floor = new FactoryFloor(width, height);
        List<RobotCommands> robotCommands = new ArrayList<>();

        // --- Parse robots ---
        if ((lines.size() - 1) % 2 != 0) {
            throw new IllegalArgumentException(
                    "Error: each robot must have a position line followed by a command line.");
        }

        for (int i = 1; i < lines.size(); i += 2) {
            String positionLine = lines.get(i).trim();
            String[] position = positionLine.split("\\s+");

            if (position.length != 3) {
                throw new IllegalArgumentException(
                        "Error: invalid robot position format on line " + (i + 1) +
                                ". Expected format: 'X Y D' (e.g., '1 2 N').");
            }

            int x, y;
            Direction direction;
            try {
                x = Integer.parseInt(position[0]);
                y = Integer.parseInt(position[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Error: robot position must use numeric coordinates (e.g., '1 2 N').");
            }

            direction = parseDirection(position[2]);

            Robot robot = new Robot((i + 1) / 2, x, y, direction);
            boolean added = floor.addRobot(robot);
            if (!added) {
                throw new IllegalArgumentException(String.format(
                        "Error: invalid configuration — robot %d cannot be placed at (%d, %d). " +
                                "Position is either occupied or outside the grid.",
                        robot.getId(), x, y));
            }

            if (i + 1 >= lines.size()) {
                throw new IllegalArgumentException("Error: missing command line for robot " + robot.getId());
            }

            String commandLine = lines.get(i + 1).trim();
            if (commandLine.isEmpty()) {
                throw new IllegalArgumentException("Error: robot " + robot.getId() + " has an empty command line.");
            }

            List<Command> commands;
            try {
                commands = parser.parse(commandLine);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Error: invalid command detected for robot " + robot.getId() +
                                ". Commands must be only L, R, or M. (" + e.getMessage() + ")");
            }

            robotCommands.add(new RobotCommands(robot, commands));
        }

        return new SimulationInput(floor, robotCommands);
    }

    private Direction parseDirection(String dir) {
        return switch (dir.toUpperCase()) {
            case "N" -> Direction.NORTH;
            case "E" -> Direction.EAST;
            case "S" -> Direction.SOUTH;
            case "W" -> Direction.WEST;
            default ->
                throw new IllegalArgumentException("Error: unknown direction '" + dir + "'. Must be N, E, S, or W.");
        };
    }

    // DTOs
    public record SimulationInput(FactoryFloor floor, List<RobotCommands> robotCommands) {
    }

    public record RobotCommands(Robot robot, List<Command> commands) {
    }
}
