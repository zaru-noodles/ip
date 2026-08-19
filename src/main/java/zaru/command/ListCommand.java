package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code list} command. */
public class ListCommand extends Command {
    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public void execute(TaskList tasks, UI ui) throws ZaruException {
        if (tasks.size() == 0) {
            ui.sendMessage("You have no tasks!");
        } else {
            ui.sendMessage("Here are your tasks:\n%s".formatted(tasks));
        }
    }
}
