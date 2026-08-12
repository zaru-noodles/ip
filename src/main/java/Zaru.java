/** Entry point for the Zaru chatbot application. */
public class Zaru {
    private static final String LINE_SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(LINE_SEPARATOR);

        String banner = " _____                   \n"
                + "|__  /__ _ _ __ _   _    \n"
                + "  / // _` | '__| | | |   \n"
                + " / /| (_| | |  | |_| |   \n"
                + "/____\\__,_|_|   \\__,_|   \n";
        printMessage(banner + "Hello! I'm Zaru.\nWhat can I do for you?");
        printMessage("Bye. Hope to see you again soon!");
    }

    private static void printMessage(String message) {
        System.out.println(message + "\n" + LINE_SEPARATOR);
    }
}
