package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code bye} command. */
public class ByeCommand extends Command {
    /**
     * Returns the command word represented by this command.
     *
     * @return The {@code bye} command word.
     */
    @Override
    public String getCommand() {
        return "bye";
    }

    /**
     * Displays the goodbye message.
     *
     * @param tasks Current task list, unused by this command.
     * @throws ZaruException If displaying the command response fails.
     */
    @Override
    public void execute(TaskList tasks) throws ZaruException {
        UI.sendMessage("Bye. Hope to see you again soon!");
    }
}
