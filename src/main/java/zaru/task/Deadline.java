package zaru.task;

import java.time.LocalDateTime;

import zaru.parser.DateTimeParser;
import zaru.exception.ZaruException;

public class Deadline extends Task {
    private LocalDateTime dueDate;

    public Deadline(String title, String dueDate) throws ZaruException {
        this(title, false, dueDate);
    }

    public Deadline(String title, boolean completed, String dueDate) throws ZaruException {
        this(title, completed, DateTimeParser.parse(dueDate));
    }

    public Deadline(String title, LocalDateTime dueDate) {
        this(title, false, dueDate);
    }

    public Deadline(String title, boolean completed, LocalDateTime dueDate) {
        super(title, completed);
        this.dueDate = dueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), DateTimeParser.format(dueDate));
    }
}
