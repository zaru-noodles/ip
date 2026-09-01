package zaru.parser;

/** Stores a chatbot response and whether it represents a status or an error. */
public class Response {
    /** Identifies the category of a chatbot response. */
    public enum ResponseType {
        STATUS,
        ERROR
    }

    private final String text;
    private final ResponseType type;

    /**
     * Creates a response with the given text and category.
     *
     * @param text Response text.
     * @param type Response category.
     */
    public Response(String text, ResponseType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Creates a status response with the given text.
     *
     * @param text Response text.
     */
    public Response(String text) {
        this(text, ResponseType.STATUS);
    }

    /**
     * Returns the response text.
     *
     * @return Response text.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the response category.
     *
     * @return Response category.
     */
    public ResponseType getType() {
        return type;
    }

}
