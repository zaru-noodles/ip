package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.Deadline;
import zaru.task.TaskList;

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

    /**
     * Returns the command word represented by this command.
     *
     * @return The {@code deadline} command word.
     */
    @Override
    public String getCommand() {
        return "deadline";
    }

    /**
     * Validates and adds the deadline task, then returns the response message.
     *
     * @param tasks Current task list.
     * @return Task-added response message.
     * @throws ZaruException If required text or the deadline date is invalid.
     */
    @Override
    public String execute(TaskList tasks) throws ZaruException {
        validateNonEmpty(description, "The description of a deadline cannot be empty.");
        validateNonEmpty(dueDate, "Please provide a deadline date using /by.");
        return addTaskAndCreateResponse(tasks, new Deadline(description, dueDate));
    }
}
