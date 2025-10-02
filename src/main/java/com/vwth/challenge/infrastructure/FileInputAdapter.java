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
        List<String> lines = Files.readAllLines(Path.of(filePath));
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Input file is empty");
        }

        // First line = grid size
        String[] size = lines.get(0).split(" ");
        int width = Integer.parseInt(size[0]);
        int height = Integer.parseInt(size[1]);
        FactoryFloor floor = new FactoryFloor(width, height);

        List<RobotCommands> robotCommands = new ArrayList<>();

        for (int i = 1; i < lines.size(); i += 2) {
            String[] position = lines.get(i).split(" ");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            Direction direction = parseDirection(position[2]);

            Robot robot = new Robot((i + 1) / 2, x, y, direction);
            floor.addRobot(robot);

            String commandLine = lines.get(i + 1).trim();
            List<Command> commands = parser.parse(commandLine);

            robotCommands.add(new RobotCommands(robot, commands));
        }

        return new SimulationInput(floor, robotCommands);
    }

    private Direction parseDirection(String dir) {
        return switch (dir) {
            case "N" -> Direction.NORTH;
            case "E" -> Direction.EAST;
            case "S" -> Direction.SOUTH;
            case "W" -> Direction.WEST;
            default -> throw new IllegalArgumentException("Unknown direction: " + dir);
        };
    }

    // DTOs to pass parsed data back to application
    public record SimulationInput(FactoryFloor floor, List<RobotCommands> robotCommands) {
    }

    public record RobotCommands(Robot robot, List<Command> commands) {
    }
}
