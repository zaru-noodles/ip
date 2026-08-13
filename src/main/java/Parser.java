import java.util.HashMap;

public class Parser {
    public static class ParsedMessage {
        public String command;
        public String arg;
        public HashMap<String, String> keyArgs;

        public ParsedMessage(String command, String arg) {
            this.command = command.toLowerCase();
            this.arg = arg;
            this.keyArgs = new HashMap<>();
        }

        public void addKeyArg(String key, String value) {
            keyArgs.put(key, value);
        }
    }

    public static ParsedMessage parseMessage(String message) {
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
            String key = blocks[j].substring(0, i);
            String value = blocks[j].substring(i + 1);
            res.addKeyArg(key, value);
        }

        return res;
    }
}
