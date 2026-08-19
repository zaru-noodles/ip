package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.Deadline;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code deadline} command. */
public class DeadlineCommand extends Command {
    private final String description;
    private final String dueDate;

    /**
     * Creates a deadline command.
     *
     * @param description Description of the deadline task.
     * @param dueDate Due date entered after {@code /by}.
     */
    public DeadlineCommand(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    @Override
    public String getCommand() {
        return "deadline";
    }

    @Override
    public void execute(TaskList tasks) throws ZaruException {
        validateNonEmpty(description, "The description of a deadline cannot be empty.");
        validateNonEmpty(dueDate, "Please provide a deadline date using /by.");
        tasks.add(new Deadline(description, dueDate));
        UI.printAddTaskMessage(tasks);
    }
}
