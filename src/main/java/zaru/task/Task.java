package zaru.task;

/** Stores the common description and completion state of a task. */
public abstract class Task {
    private String title;
    private boolean completed;

    /**
     * Creates a task with its description and completion state.
     *
     * @param title Task description.
     * @param completed Whether the task is already complete.
     */
    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
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
        return completed;
    }

    /**
     * Updates the task completion state.
     *
     * @param completed New completion state.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Returns the common completion marker and task description.
     *
     * @return Formatted task text.
     */
    @Override
    public String toString() {
        return String.format("%s %s", completed ? "[x]" : "[ ]", title);
    }
}
