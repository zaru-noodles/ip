import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Entry point for the Zaru chatbot application. */
public class Zaru {
    private static final String LINE_SEPARATOR = "____________________________________________________________";

    private static final TaskList tasks = new TaskList();

    /** Starts the chatbot, reads commands, and ends when the user enters {@code bye}. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(LINE_SEPARATOR);

        String banner = " _____                   \n"
                + "|__  /__ _ _ __ _   _    \n"
                + "  / // _` | '__| | | |   \n"
                + " / /| (_| | |  | |_| |   \n"
                + "/____\\__,_|_|   \\__,_|   \n";
        printMessage(banner + "Hello! I'm Zaru.\nWhat can I do for you?");

        while (true) {
            String message = inputMessage(scanner);
            processMessage(message);
            if (message.equals("bye")) {
                break;
            }
        }
    }

    /** Reads one command from standard input and prints the response separator. */
    private static String inputMessage(Scanner scanner) {
        String msg = scanner.nextLine();
        System.out.println(LINE_SEPARATOR);
        return msg;
    }

    /** Processes a command by adding a task, listing tasks, or ending the session. */
    private static void processMessage(String message) {
        message = message.trim();
        String[] words = message.split(" ");
        String command = words[0];
        String[] args = Arrays.copyOfRange(words, 1, words.length);

        switch (command.toLowerCase()) {
        case "bye" -> printMessage("Bye. Hope to see you again soon!");
        case "list" -> printMessage(tasks.toString());
        case "mark" -> {
            int i = Integer.parseInt(args[0]);
            tasks.complete(i);
            printMessage("Meow! I've marked that task as done!\n%s".formatted(tasks.getTaskString(i)));
        }
        default -> {
            tasks.add(new Task(message));
            printMessage("added: " + message);
        }
        }
    }

    /** Prints a chatbot message followed by the response separator. */
    private static void printMessage(String message) {
        System.out.println(message + "\n" + LINE_SEPARATOR);
    }
}
