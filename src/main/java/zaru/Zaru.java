package zaru;

import java.nio.file.Path;

import zaru.command.ByeCommand;
import zaru.command.Command;
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
                Command cmd = Parser.parseMessage(message);
                cmd.execute(tasks);

                if (cmd instanceof ByeCommand) {
                    break;
                }
            } catch (ZaruException e) {
                UI.sendError(e.getMessage());
            }
        }
    }
}
