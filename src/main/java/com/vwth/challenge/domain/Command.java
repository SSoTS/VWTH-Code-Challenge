package com.vwth.challenge.domain;

public enum Command {
    LEFT('L'),
    RIGHT('R'),
    MOVE('M');

    private final char code;

    Command(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    // Factory method: from char → enum
    public static Command fromChar(char c) {
        for (Command cmd : values()) {
            if (cmd.code == c) return cmd;
        }
        throw new IllegalArgumentException("Unknown command: " + c);
    }
}
