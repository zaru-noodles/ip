package zaru.task;

public class ToDo extends Task {
    public ToDo(String title, boolean completed) {
        super(title, completed);
    }

    public ToDo(String title) {
        this(title, false);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
