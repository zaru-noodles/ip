package zaru.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import zaru.command.ByeCommand;
import zaru.command.Command;
import zaru.command.DeadlineCommand;
import zaru.command.DeleteCommand;
import zaru.command.EventCommand;
import zaru.command.ListCommand;
import zaru.command.MarkCommand;
import zaru.command.TodoCommand;
import zaru.command.UnmarkCommand;
import zaru.exception.ZaruException;

/** Tests parsing user input into executable commands. */
public class ParserTest {
    @Test
    public void parseMessage_supportedCommands_returnsMatchingCommand() throws ZaruException {
        assertInstanceOf(ByeCommand.class, Parser.parseMessage("bye"));
        assertInstanceOf(ListCommand.class, Parser.parseMessage("list"));
        assertInstanceOf(MarkCommand.class, Parser.parseMessage("mark 1"));
        assertInstanceOf(UnmarkCommand.class, Parser.parseMessage("unmark 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parseMessage("delete 1"));
        assertInstanceOf(TodoCommand.class, Parser.parseMessage("todo read book"));
        assertInstanceOf(DeadlineCommand.class, Parser.parseMessage("deadline submit report /by 2026-12-10"));
        assertInstanceOf(EventCommand.class,
                Parser.parseMessage("event project meeting /from 2026-12-10 1000 /to 2026-12-10 1100"));
    }

    @Test
    public void parseMessage_supportedCommand_returnsMatchingCommandName() throws ZaruException {
        Command command = Parser.parseMessage("  TODO read book  ");

        assertEquals("todo", command.getCommand());
    }

    @Test
    public void parseMessage_blankInput_exceptionThrown() {
        ZaruException exception = assertThrows(ZaruException.class, () -> Parser.parseMessage("   "));

        assertEquals("Pwease enter a command!", exception.getMessage());
    }

    @Test
    public void parseMessage_missingKeyValue_exceptionThrown() {
        ZaruException exception = assertThrows(ZaruException.class,
                () -> Parser.parseMessage("deadline submit report /by"));

        assertEquals("Please provide a value after /by.", exception.getMessage());
    }

    @Test
    public void parseMessage_unknownCommand_exceptionThrown() {
        ZaruException exception = assertThrows(ZaruException.class,
                () -> Parser.parseMessage("dance"));

        assertEquals("Sorry, I don't know what that means ;w;", exception.getMessage());
    }
}
