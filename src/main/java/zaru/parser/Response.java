package zaru.parser;

/** Stores a chatbot response and whether it represents a status or an error. */
public class Response {
    /** Identifies the category of a chatbot response. */
    public enum ResponseType {
        STATUS,
        ERROR
    };

    private String text;
    private ResponseType type;

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

    /** Creates an empty status response. */
    public Response() {
        this("", ResponseType.STATUS);
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

    /**
     * Updates the response text.
     *
     * @param text New response text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Updates the response category.
     *
     * @param type New response category.
     */
    public void setType(ResponseType type) {
        this.type = type;
    }
}