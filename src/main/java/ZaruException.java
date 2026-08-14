/**
 * Represents errors caused by invalid user commands in the Zaru chatbot.
 */
public class ZaruException extends Exception {
    /**
     * Creates a Zaru-specific exception with a message that can be shown to the user.
     *
     * @param message Explanation of what went wrong.
     */
    public ZaruException(String message) {
        super(message);
    }
}
