import java.util.HashMap;

/**
 * Parses raw user input into a command, main argument, and optional keyed arguments.
 */
public class Parser {
    /**
     * Represents the parsed form of one user command.
     */
    public static class ParsedMessage {
        public String command;
        public String arg;
        public HashMap<String, String> keyArgs;

        /**
         * Creates a parsed message with a command and its main argument.
         *
         * @param command Command word entered by the user.
         * @param arg Text that appears after the command word.
         */
        public ParsedMessage(String command, String arg) {
            this.command = command.toLowerCase();
            this.arg = arg;
            this.keyArgs = new HashMap<>();
        }

        /**
         * Stores an argument introduced by a slash-prefixed keyword.
         *
         * @param key Keyword such as {@code by}, {@code from}, or {@code to}.
         * @param value Text belonging to the keyword.
         */
        public void addKeyArg(String key, String value) {
            keyArgs.put(key, value);
        }
    }

    /**
     * Parses a raw command line into command data.
     *
     * @param message Raw user input.
     * @return Parsed command data.
     * @throws ZaruException If the user input is empty or contains a malformed keyed argument.
     */
    public static ParsedMessage parseMessage(String message) throws ZaruException {
        message = message.trim();
        if (message.isEmpty()) {
            throw new ZaruException("Pwease enter a command!");
        }

        String[] blocks = message.split("\\s*/\\s*");

        ParsedMessage res;
        int i = blocks[0].indexOf(' ');
        if (i == -1) {
            res = new ParsedMessage(blocks[0], "");
        } else {
            res = new ParsedMessage(blocks[0].substring(0, i), blocks[0].substring(i + 1));
        }

        for (int j = 1; j < blocks.length; j++) {
            i = blocks[j].indexOf(' ');
            if (i == -1) {
                throw new ZaruException("Please provide a value after /%s.".formatted(blocks[j]));
            }
            String key = blocks[j].substring(0, i);
            String value = blocks[j].substring(i + 1);
            res.addKeyArg(key, value);
        }

        return res;
    }
}
