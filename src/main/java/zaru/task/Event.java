package zaru.task;

import java.time.LocalDateTime;

import zaru.exception.ZaruException;
import zaru.parser.DateTimeParser;

/** Represents a task that occurs between a specified start and end time. */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an incomplete event from user-entered time text.
     *
     * @param title Task description.
     * @param from Event start time text.
     * @param to Event end time text.
     * @throws ZaruException If either time text is invalid.
     */
    public Event(String title, String from, String to) throws ZaruException {
        this(title, false, from, to);
    }

    /**
     * Creates an event from user-entered time text.
     *
     * @param title Task description.
     * @param isCompleted Whether the task is already complete.
     * @param from Event start time text.
     * @param to Event end time text.
     * @throws ZaruException If either time text is invalid.
     */
    public Event(String title, boolean isCompleted, String from, String to) throws ZaruException {
        this(title, isCompleted, DateTimeParser.parse(from), DateTimeParser.parse(to));
    }

    /**
     * Creates an incomplete event from parsed times.
     *
     * @param title Task description.
     * @param from Event start time.
     * @param to Event end time.
     */
    public Event(String title, LocalDateTime from, LocalDateTime to) {
        this(title, false, from, to);
    }

    /**
     * Creates an event from parsed times.
     *
     * @param title Task description.
     * @param isCompleted Whether the task is already complete.
     * @param from Event start time.
     * @param to Event end time.
     */
    public Event(String title, boolean isCompleted, LocalDateTime from, LocalDateTime to) {
        super(title, isCompleted);
        assert from != null : "Event start time should have been parsed before construction.";
        assert to != null : "Event end time should have been parsed before construction.";

        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event start time.
     *
     * @return Event start time.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the event end time.
     *
     * @return Event end time.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the task text with its event times displayed.
     *
     * @return Formatted event task text.
     */
    @Override
    public String toString() {
        return "[E]%s (from: %s) (to: %s)".formatted(
                super.toString(), DateTimeParser.format(from), DateTimeParser.format(to));
    }
}
