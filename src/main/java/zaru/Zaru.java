package zaru;

import java.nio.file.Path;

import zaru.exception.ZaruException;
import zaru.parser.Parser;
import zaru.storage.Storage;
import zaru.task.Deadline;
import zaru.task.Event;
import zaru.task.TaskList;
import zaru.task.ToDo;
import zaru.ui.UI;

/** Entry point for the zaru.Zaru chatbot application. */
public class Zaru {
    private static Storage storage = new Storage(Path.of("data", "zaru.txt"));
    private static TaskList tasks = new TaskList(storage);

    /** Starts the chatbot, reads commands, and ends when the user enters {@code bye}. */
    public static void main(String[] args) {
        UI.printWelcomeMessage();

        try {
            tasks.loadFromStorage();
        } catch (ZaruException e) {
            UI.sendError("Error loading tasks from storage: %s".formatted(e.getMessage()));
        }

        while (true) {
            String message = UI.retrieveMessage();
            try {
                processMessage(message);
            } catch (ZaruException e) {
                UI.sendError(e.getMessage());
            }
            if (message.equalsIgnoreCase("bye")) {
                break;
            }
        }
    }

    /** Processes a command by adding a task, listing tasks, or ending the session. */
    private static void processMessage(String message) throws ZaruException {
        Parser.ParsedMessage data = Parser.parseMessage(message);

        switch (data.command) {
        case "bye" -> UI.sendMessage("Bye. Hope to see you again soon!");
        case "list" -> {
            if (tasks.size() == 0) {
                UI.sendMessage("You have no tasks!");
            } else {
                UI.sendMessage("Here are your tasks:\n%s".formatted(tasks));
            }
        }
        case "mark" -> {
            int i = parseNumber(data.arg);
            validateTaskNumber(i);
            tasks.complete(i);
            UI.sendMessage("Meow! I've marked that task as done!\n%s".formatted(tasks.getTaskString(i)));
        }
        case "unmark" -> {
            int i = parseNumber(data.arg);
            validateTaskNumber(i);
            tasks.uncomplete(i);
            UI.sendMessage("I've unmarked that task!\n%s".formatted(tasks.getTaskString(i)));
        }
        case "delete" -> {
            int i = parseNumber(data.arg);
            validateTaskNumber(i);
            String tmp = tasks.getTaskString(i);
            tasks.delete(i);
            UI.sendMessage("I've deleted that task!\n%s".formatted(tmp));
        }
        case "todo" -> {
            validateNonEmpty(data.arg, "The description of a todo cannot be empty.");
            tasks.add(new ToDo(data.arg));
            UI.printAddTaskMessage(tasks);
        }
        case "deadline" -> {
            validateNonEmpty(data.arg, "The description of a deadline cannot be empty.");
            validateNonEmpty(data.keyArgs.get("by"), "Please provide a deadline date using /by.");
            tasks.add(new Deadline(data.arg, data.keyArgs.get("by")));
            UI.printAddTaskMessage(tasks);
        }
        case "event" -> {
            validateNonEmpty(data.arg, "The description of an event cannot be empty.");
            validateNonEmpty(data.keyArgs.get("from"), "Please provide an event start time using /from.");
            validateNonEmpty(data.keyArgs.get("to"), "Please provide an event end time using /to.");
            tasks.add(new Event(data.arg, data.keyArgs.get("from"), data.keyArgs.get("to")));
            UI.printAddTaskMessage(tasks);
        }
        default -> throw new ZaruException("Sorry, I don't know what that means ;w;");
        }
    }

    /**
     * Converts a user-provided number into an integer.
     *
     * @param num Text entered after a mark or unmark command.
     * @return The parsed task number.
     * @throws ZaruException If the text is missing or is not a whole number.
     */
    private static int parseNumber(String num) throws ZaruException {
        validateNonEmpty(num, "Please provide a number.");
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new ZaruException("Number %s must be a valid number.".formatted(num));
        }
    }

    /**
     * Checks whether a task number refers to an existing task.
     *
     * @param taskNumber One-based task number entered by the user.
     * @throws ZaruException If the number is outside the task list.
     */
    private static void validateTaskNumber(int taskNumber) throws ZaruException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ZaruException("zaru.task.Task number must be between 1 and %d!".formatted(tasks.size()));
        }
    }

    /**
     * Checks whether required command text is present.
     *
     * @param text Text to check.
     * @param errorMessage Message to show if the text is missing.
     * @throws ZaruException If the text is null or blank.
     */
    private static void validateNonEmpty(String text, String errorMessage) throws ZaruException {
        if (text == null || text.isBlank()) {
            throw new ZaruException(errorMessage);
        }
    }
}
