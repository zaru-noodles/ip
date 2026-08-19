package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code bye} command. */
public class ByeCommand extends Command {
    @Override
    public String getCommand() {
        return "bye";
    }

    @Override
    public void execute(TaskList tasks, UI ui) throws ZaruException {
        ui.sendMessage("Bye. Hope to see you again soon!");
    }
}
