public class Event extends Task {
    private String from;
    private String to;

    public Event(String title, String from, String to) {
        this(title, false, from, to);
    }

    public Event(String title, boolean completed, String from, String to) {
        super(title, completed);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]%s (from: %s) (to: %s)".formatted(super.toString(), from, to);
    }
}