package zaru.task;

/** Represents a task without a deadline or scheduled time. */
public class ToDo extends Task {
    /**
     * Creates a todo task with an explicit completion state.
     *
     * @param title Task description.
     * @param completed Whether the task is already complete.
     */
    public ToDo(String title, boolean completed) {
        super(title, completed);
    }

    /**
     * Creates an incomplete todo task.
     *
     * @param title Task description.
     */
    public ToDo(String title) {
        this(title, false);
    }

    /**
     * Returns the task text with the todo type marker.
     *
     * @return Formatted todo task text.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
