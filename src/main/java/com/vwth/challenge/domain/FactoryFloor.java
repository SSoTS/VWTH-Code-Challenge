package com.vwth.challenge.domain;

import java.util.HashMap;
import java.util.Map;

public class FactoryFloor {
    private final int width;
    private final int height;
    private final Map<Integer, Robot> robots = new HashMap<>();

    public FactoryFloor(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean addRobot(Robot robot) {
        if (isInside(robot.getX(), robot.getY()) && !isOccupied(robot.getX(), robot.getY())) {
            robots.put(robot.getId(), robot);
            return true;
        }
        return false; // cannot place robot here
    }

    public Robot getRobotById(int id) {
        return robots.get(id);
    }

    /**
     * Referee method: checks if robot can move forward
     */
    public boolean tryMove(Robot robot) {
        int newX = robot.getX();
        int newY = robot.getY();

        switch (robot.getDirection()) {
            case NORTH -> newY++;
            case EAST -> newX++;
            case SOUTH -> newY--;
            case WEST -> newX--;
        }

        if (isInside(newX, newY) && !isOccupied(newX, newY)) {
            robot.moveForward(); // robot updates itself
            return true;
        }
        return false; // blocked by wall or another robot
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean isOccupied(int x, int y) {
        return robots.values().stream()
                .anyMatch(r -> r.getX() == x && r.getY() == y);
    }
}
