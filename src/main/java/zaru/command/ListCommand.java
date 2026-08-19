package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

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
     * Displays either the empty-list message or all current tasks.
     *
     * @param tasks Current task list.
     * @throws ZaruException If displaying the command response fails.
     */
    @Override
    public void execute(TaskList tasks) throws ZaruException {
        if (tasks.size() == 0) {
            UI.sendMessage("You have no tasks!");
        } else {
            UI.sendMessage("Here are your tasks:\n%s".formatted(tasks));
        }
    }
}
