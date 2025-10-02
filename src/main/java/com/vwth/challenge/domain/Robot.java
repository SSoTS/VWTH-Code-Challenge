package com.vwth.challenge.domain;

public class Robot {
    private final int id;
    private int x;
    private int y;
    private Direction direction;

    public Robot(int id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void rotateLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.NORTH;
        }
    }

    public void rotateRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.NORTH;
        }
    }

    public void moveForward() {
        switch (direction) {
            case NORTH -> y += 1;
            case EAST -> x += 1;
            case SOUTH -> y -= 1;
            case WEST -> x -= 1;
        }
    }

    public boolean executeCommand(Command command, FactoryFloor floor) {
        return switch (command) {
            case LEFT -> { rotateLeft(); yield true; }
            case RIGHT -> { rotateRight(); yield true; }
            case MOVE -> floor.tryMove(this); // ask floor to validate move
        };
    }

}
