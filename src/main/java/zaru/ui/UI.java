package zaru.ui;

import java.util.Scanner;

import zaru.task.TaskList;

/** Handles console input and output for the chatbot. */
public class UI {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final Scanner scanner = new Scanner(System.in);

    /** Prints the chatbot banner and introductory prompt. */
    public static void printWelcomeMessage() {
        System.out.println(LINE_SEPARATOR);
        String banner = """
                 _____                  \s
                |__  /__ _ _ __ _   _   \s
                  / // _` | '__| | | |  \s
                 / /| (_| | |  | |_| |  \s
                /____\\__,_|_|   \\__,_|  \s
                """;
        sendMessage(banner + "Hello! I'm Zaru.\nWhat can I do for you?");
    }

    /** Prints a chatbot message followed by the response separator. */
    public static void sendMessage(String message) {
        System.out.println(message + "\n" + LINE_SEPARATOR);
    }

    /**
     * Prints an error message using the chatbot's error format.
     *
     * @param error Error text to display.
     */
    public static void sendError(String error) {
        sendMessage("Oopsies! I've encountered an error:\n\n    " + error + "\n");
    }

    /**
     * Prints confirmation for the most recently added task.
     *
     * @param tasks Task list containing the newly added task.
     */
    public static void printAddTaskMessage(TaskList tasks) {
        int n = tasks.size();
        UI.sendMessage("Oki! Adding this task:\n   %s\nYou now have %d task%s!".formatted(
            tasks.getTaskString(n),
            n,
            n == 1 ? "" : "s"));
    }

    /** Reads one command from standard input and prints the response separator. */
    public static String retrieveMessage() {
        String msg = scanner.nextLine();
        System.out.println(LINE_SEPARATOR);
        return msg.trim();
    }
}
