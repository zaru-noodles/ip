package zaru.parser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
public final class Parser {
    private Parser() {
    }

    /**
     * Parses a raw command line into an executable command.
     *
     * @param message Raw user input.
     * @return Command represented by the input.
     * @throws ZaruException If the user input is empty or contains a malformed keyed argument.
     */
    public static Command parseMessage(String message) throws ZaruException {
        assert message != null : "Message should be supplied by the user interface.";
        String trimmedMessage = message.trim();
        if (trimmedMessage.isEmpty()) {
            throw new ZaruException("Pwease enter a command!");
        }

        String[] inputBlocks = trimmedMessage.split("\\s*/\\s*");

        String command;
        String argument;
        String commandBlock = inputBlocks[0];
        int separatorIndex = commandBlock.indexOf(' ');
        if (separatorIndex == -1) {
            command = commandBlock.toLowerCase(Locale.ROOT);
            argument = "";
        } else {
            command = commandBlock.substring(0, separatorIndex).toLowerCase(Locale.ROOT);
            argument = commandBlock.substring(separatorIndex + 1);
        }

        Map<String, String> keyedArguments = parseKeyedArguments(inputBlocks);
        return createCommand(command, argument, keyedArguments);
    }

    /**
     * Parses slash-prefixed arguments from the input blocks after the command block.
     *
     * @param inputBlocks Command block followed by any keyed argument blocks.
     * @return Keyed arguments mapped to their values.
     * @throws ZaruException If a keyed argument has no value.
     */
    private static Map<String, String> parseKeyedArguments(String[] inputBlocks) throws ZaruException {
        Map<String, String> keyedArguments = new HashMap<>();

        for (int blockIndex = 1; blockIndex < inputBlocks.length; blockIndex++) {
            String keyedArgument = inputBlocks[blockIndex];
            int separatorIndex = keyedArgument.indexOf(' ');
            if (separatorIndex == -1) {
                throw new ZaruException("Please provide a value after /%s.".formatted(keyedArgument));
            }

            String key = keyedArgument.substring(0, separatorIndex);
            String value = keyedArgument.substring(separatorIndex + 1);
            keyedArguments.put(key, value);
        }

        return keyedArguments;
    }

    /**
     * Creates the command represented by a parsed command word and its arguments.
     *
     * @param command Parsed command word.
     * @param argument Unkeyed command argument.
     * @param keyedArguments Slash-prefixed arguments mapped to their values.
     * @return Command represented by the parsed input.
     * @throws ZaruException If the command word is not supported.
     */
    private static Command createCommand(String command, String argument, Map<String, String> keyedArguments)
            throws ZaruException {
        return switch (command) {
            case "bye" -> new ByeCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(argument);
            case "unmark" -> new UnmarkCommand(argument);
            case "delete" -> new DeleteCommand(argument);
            case "todo" -> new TodoCommand(argument);
            case "deadline" -> new DeadlineCommand(argument, keyedArguments.get("by"));
            case "event" -> new EventCommand(
                    argument, keyedArguments.get("from"), keyedArguments.get("to"));
            case "find" -> new FindCommand(argument);
            default -> throw new ZaruException("Sorry, I don't know what that means ;w;");
        };
    }
}
