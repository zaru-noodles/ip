public class Deadline extends Task {
    private String dueDate;

    public Deadline(String title, boolean completed, String date) {
        super(title, completed);
        this.dueDate = date;
    }

    public Deadline(String title, String date) {
        this(title, false, date);
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), dueDate);
    }
}
