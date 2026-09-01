package zaru;

import java.nio.file.Path;

import zaru.command.ByeCommand;
import zaru.command.Command;
import zaru.exception.ZaruException;
import zaru.parser.Parser;
import zaru.parser.Response;
import zaru.storage.Storage;
import zaru.task.TaskList;

/** Coordinates storage, command parsing, and task operations for the Zaru chatbot. */
public class Zaru {
    private final TaskList tasks;

    /**
     * Creates a chatbot instance and loads any saved tasks.
     */
    public Zaru() {
        Storage storage = new Storage(Path.of("data", "zaru.txt"));
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
    public Response getResponse(String input) {
        try {
            Command command = Parser.parseMessage(input);
            String responseText = command.execute(tasks);

            if (command instanceof ByeCommand) {
                System.exit(0);
            }

            return new Response(responseText);
        } catch (ZaruException e) {
            return new Response(e.getMessage(), Response.ResponseType.ERROR);
        }
    }
}
