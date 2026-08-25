package zaru.parser;

import java.util.HashMap;

import zaru.command.ByeCommand;
import zaru.command.Command;
import zaru.command.DeadlineCommand;
import zaru.command.DeleteCommand;
import zaru.command.EventCommand;
import zaru.command.FindCommand;
import zaru.command.ListCommand;
import zaru.command.MarkCommand;
import zaru.command.TodoCommand;
import zaru.command.UnmarkCommand;
import zaru.exception.ZaruException;

/**
 * Parses raw user input into an executable command.
 */
public class Parser {
    /**
     * Parses a raw command line into an executable command.
     *
     * @param message Raw user input.
     * @return Command represented by the input.
     * @throws ZaruException If the user input is empty or contains a malformed keyed argument.
     */
    public static Command parseMessage(String message) throws ZaruException {
        message = message.trim();
        if (message.isEmpty()) {
            throw new ZaruException("Pwease enter a command!");
        }

        String[] blocks = message.split("\\s*/\\s*");

        String command;
        String arg;
        int i = blocks[0].indexOf(' ');
        if (i == -1) {
            command = blocks[0].toLowerCase();
            arg = "";
        } else {
            command = blocks[0].substring(0, i).toLowerCase();
            arg = blocks[0].substring(i + 1);
        }

        HashMap<String, String> keyArgs = new HashMap<>();
        for (int j = 1; j < blocks.length; j++) {
            i = blocks[j].indexOf(' ');
            if (i == -1) {
                throw new ZaruException("Please provide a value after /%s.".formatted(blocks[j]));
            }
            String key = blocks[j].substring(0, i);
            String value = blocks[j].substring(i + 1);
            keyArgs.put(key, value);
        }

        return switch (command) {
            case "bye" -> new ByeCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(arg);
            case "unmark" -> new UnmarkCommand(arg);
            case "delete" -> new DeleteCommand(arg);
            case "todo" -> new TodoCommand(arg);
            case "deadline" -> new DeadlineCommand(arg, keyArgs.get("by"));
            case "event" -> new EventCommand(arg, keyArgs.get("from"), keyArgs.get("to"));
            case "find" -> new FindCommand(arg);
            default -> throw new ZaruException("Sorry, I don't know what that means ;w;");
        };
    }
}
