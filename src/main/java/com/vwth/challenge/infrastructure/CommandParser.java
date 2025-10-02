package com.vwth.challenge.infrastructure;

import com.vwth.challenge.domain.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    /**
     * Parses a string like "LMLMRM" into a list of Commands.
     *
     * @param input string of commands
     * @return list of Command enums
     */
    public List<Command> parse(String input) {
        List<Command> commands = new ArrayList<>();
        for (char c : input.toCharArray()) {
            commands.add(Command.fromChar(c));
        }
        return commands;
    }
}
