import java.util.Scanner;

public class UI {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final Scanner scanner = new Scanner(System.in);

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

    /** Reads one command from standard input and prints the response separator. */
    public static String retrieveMessage() {
        String msg = scanner.nextLine();
        System.out.println(LINE_SEPARATOR);
        return msg.trim();
    }
}
