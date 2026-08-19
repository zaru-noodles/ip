package zaru.command;

import zaru.task.TaskList;
import zaru.ui.UI;

public abstract class Command {
    public abstract String getCommand();
    public abstract void execute(TaskList tasks, UI ui);
}
