public abstract class Task {
    private String title;
    private boolean completed;

    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public Task(String title) {
        this(title, false);
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("%s %s", completed ? "[x]" : "[ ]", title);
    }
}
