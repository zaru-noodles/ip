import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Entry point for the Zaru chatbot application. */
public class Zaru {
    private static final String LINE_SEPARATOR = "____________________________________________________________";

    private static final List<String> tasks = new ArrayList<>();

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
        switch (message) {
        case "bye" -> printMessage("Bye. Hope to see you again soon!");
        case "list" -> printTasks();
        default -> {
            tasks.add(message);
            printMessage("added: " + message);
        }
        }
    }

    /** Prints a chatbot message followed by the response separator. */
    private static void printMessage(String message) {
        System.out.println(message + "\n" + LINE_SEPARATOR);
    }

    /** Prints all stored tasks in the order in which they were entered. */
    private static void printTasks() {
        if (tasks.isEmpty()) {
            printMessage("There are no tasks yet.");
            return;
        }

        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append(i + 1).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                taskList.append("\n");
            }
        }
        printMessage(taskList.toString());
    }
}
