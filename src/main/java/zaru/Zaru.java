package zaru;

import java.nio.file.Path;

import zaru.command.ByeCommand;
import zaru.command.Command;
import zaru.exception.ZaruException;
import zaru.parser.Parser;
import zaru.storage.Storage;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Entry point for the zaru.Zaru chatbot application. */
public class Zaru {
    private static Storage storage = new Storage(Path.of("data", "zaru.txt"));
    private static TaskList tasks = new TaskList(storage);

    /**
     * Starts the chatbot, reads commands, and ends when the user enters {@code bye}.
     *
     * @param args Command-line arguments, which are not used.
     */
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

    public String getResponse(String input) {
        return "TODO";
    }
}
