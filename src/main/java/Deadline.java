public class Deadline extends Task {
    private String dueDate;

    public Deadline(String title, String date) {
        super(title);
        this.dueDate = date;
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), dueDate);
    }
}
