import java.util.Scanner;

/** Entry point for the Zaru chatbot application. */
public class Zaru {
    private static final String LINE_SEPARATOR = "____________________________________________________________";

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

    private static String inputMessage(Scanner scanner) {
        String msg = scanner.nextLine();
        System.out.println(LINE_SEPARATOR);
        return msg;
    }

    private static void processMessage(String message) {
        message = message.trim();
        if (message.equals("bye")) {
            printMessage("Bye. Hope to see you again soon!");
        } else {
            printMessage(message);
        }
    }

    private static void printMessage(String message) {
        System.out.println(message + "\n" + LINE_SEPARATOR);
    }
}
