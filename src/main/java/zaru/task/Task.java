package zaru.task;

/** Stores the common description and completion state of a task. */
public abstract class Task {
    private final String title;
    private boolean isCompleted;

    /**
     * Creates a task with its description and completion state.
     *
     * @param title Task description.
     * @param isCompleted Whether the task is already complete.
     */
    public Task(String title, boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }

    /**
     * Returns the task description.
     *
     * @return Task description.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks whether the task is complete.
     *
     * @return {@code true} if the task is complete.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Updates the task completion state.
     *
     * @param isCompleted New completion state.
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Returns the common completion marker and task description.
     *
     * @return Formatted task text.
     */
    @Override
    public String toString() {
        return String.format("%s %s", isCompleted ? "[x]" : "[ ]", title);
    }
}
