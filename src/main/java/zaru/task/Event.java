package zaru.task;

import java.time.LocalDateTime;

import zaru.parser.DateTimeParser;
import zaru.exception.ZaruException;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String title, String from, String to) throws ZaruException {
        this(title, false, from, to);
    }

    public Event(String title, boolean completed, String from, String to) throws ZaruException {
        this(title, completed, DateTimeParser.parse(from), DateTimeParser.parse(to));
    }

    public Event(String title, LocalDateTime from, LocalDateTime to) {
        this(title, false, from, to);
    }

    public Event(String title, boolean completed, LocalDateTime from, LocalDateTime to) {
        super(title, completed);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]%s (from: %s) (to: %s)".formatted(super.toString(), DateTimeParser.format(from), DateTimeParser.format(to));
    }
}