package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.task.ToDo;
import zaru.ui.UI;

/** Executes the {@code todo} command. */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Creates a todo command.
     *
     * @param description Description of the todo task.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String getCommand() {
        return "todo";
    }

    @Override
    public void execute(TaskList tasks) throws ZaruException {
        validateNonEmpty(description, "The description of a todo cannot be empty.");
        tasks.add(new ToDo(description));
        UI.printAddTaskMessage(tasks);
    }
}
