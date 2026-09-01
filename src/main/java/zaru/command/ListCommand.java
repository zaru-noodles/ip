package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;

/** Executes the {@code list} command. */
public class ListCommand extends Command {
    /**
     * Returns the command word represented by this command.
     *
     * @return The {@code list} command word.
     */
    @Override
    public String getCommand() {
        return "list";
    }

    /**
     * Returns either the empty-list message or all current tasks.
     *
     * @param tasks Current task list.
     * @return Task-list response message.
     * @throws ZaruException If displaying the command response fails.
     */
    @Override
    public String execute(TaskList tasks) throws ZaruException {
        if (tasks.size() == 0) {
            return "You have no tasks!";
        }

        return "Here are your tasks:\n%s".formatted(tasks);
    }
}
