package zaru;

import java.nio.file.Path;

import zaru.command.ByeCommand;
import zaru.command.Command;
import zaru.exception.ZaruException;
import zaru.parser.Parser;
import zaru.storage.Storage;
import zaru.task.TaskList;

/** Coordinates storage, command parsing, and task operations for the Zaru chatbot. */
public class Zaru {
    private Storage storage;
    private TaskList tasks;

    /**
     * Creates a chatbot instance and loads any saved tasks.
     */
    public Zaru() {
        storage = new Storage(Path.of("data", "zaru.txt"));
        tasks = new TaskList(storage);

        try {
            tasks.loadFromStorage();
        } catch (ZaruException e) {
            System.out.println("Corrupted save file detected. Reverting to empty list.");
        }
    }

    /**
     * Parses and executes one user command.
     *
     * @param input Raw command entered by the user.
     * @return Response message produced by the command or error handling.
     */
    public String getResponse(String input) {
        String response = "";
        try {
            Command cmd = Parser.parseMessage(input);
            response = cmd.execute(tasks);

            if (cmd instanceof ByeCommand) {
                System.exit(0);
            }

        } catch (ZaruException e) {
            response = e.getMessage();
        }

        return response;
    }
}
