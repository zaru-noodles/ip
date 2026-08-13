import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Entry point for the Zaru chatbot application. */
public class Zaru {
    private static final TaskList tasks = new TaskList();

    /** Starts the chatbot, reads commands, and ends when the user enters {@code bye}. */
    public static void main(String[] args) {
        UI.printWelcomeMessage();

        while (true) {
            String message = UI.retrieveMessage();
            processMessage(message);
            if (message.equals("bye")) {
                break;
            }
        }
    }

    /** Processes a command by adding a task, listing tasks, or ending the session. */
    private static void processMessage(String message) {
        Parser.ParsedMessage data = Parser.parseMessage(message);

        switch (data.command) {
        case "bye" -> UI.sendMessage("Bye. Hope to see you again soon!");
        case "list" -> UI.sendMessage(tasks.toString());
        case "mark" -> {
            int i = Integer.parseInt(data.arg);
            tasks.complete(i);
            UI.sendMessage("Meow! I've marked that task as done!\n%s".formatted(tasks.getTaskString(i)));
        }
        case "unmark" -> {
            int i = Integer.parseInt(data.arg);
            tasks.uncomplete(i);
            UI.sendMessage("I've unmarked that task!\n%s".formatted(tasks.getTaskString(i)));
        }
        }
    }
}
