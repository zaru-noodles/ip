package zaru.task;

import java.time.LocalDateTime;

import zaru.exception.ZaruException;
import zaru.parser.DateTimeParser;

/** Represents a task that must be completed by a specified date and time. */
public class Deadline extends Task {
    private LocalDateTime dueDate;

    /**
     * Creates an incomplete deadline from user-entered date text.
     *
     * @param title Task description.
     * @param dueDate Date text in an accepted date-time format.
     * @throws ZaruException If the date text is invalid.
     */
    public Deadline(String title, String dueDate) throws ZaruException {
        this(title, false, dueDate);
    }

    /**
     * Creates a deadline from user-entered date text.
     *
     * @param title Task description.
     * @param isCompleted Whether the task is already complete.
     * @param dueDate Date text in an accepted date-time format.
     * @throws ZaruException If the date text is invalid.
     */
    public Deadline(String title, boolean isCompleted, String dueDate) throws ZaruException {
        this(title, isCompleted, DateTimeParser.parse(dueDate));
    }

    /**
     * Creates an incomplete deadline from a parsed date and time.
     *
     * @param title Task description.
     * @param dueDate Parsed due date and time.
     */
    public Deadline(String title, LocalDateTime dueDate) {
        this(title, false, dueDate);
    }

    /**
     * Creates a deadline from a parsed date and time.
     *
     * @param title Task description.
     * @param isCompleted Whether the task is already complete.
     * @param dueDate Parsed due date and time.
     */
    public Deadline(String title, boolean isCompleted, LocalDateTime dueDate) {
        super(title, isCompleted);
        assert dueDate != null : "Deadline date should have been parsed before construction.";

        this.dueDate = dueDate;
    }

    /**
     * Returns the deadline date and time.
     *
     * @return Due date and time.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Returns the task text with its deadline displayed.
     *
     * @return Formatted deadline task text.
     */
    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), DateTimeParser.format(dueDate));
    }
}
