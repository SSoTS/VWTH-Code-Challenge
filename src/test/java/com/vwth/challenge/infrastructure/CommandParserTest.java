package com.vwth.challenge.infrastructure;

import com.vwth.challenge.domain.Command;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void testParseValidString() {
        CommandParser parser = new CommandParser();
        List<Command> commands = parser.parse("LRM");

        assertEquals(3, commands.size());
        assertEquals(Command.LEFT, commands.get(0));
        assertEquals(Command.RIGHT, commands.get(1));
        assertEquals(Command.MOVE, commands.get(2));
    }

    @Test
    void testInvalidCommandThrowsException() {
        CommandParser parser = new CommandParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("LX"));
    }
}
