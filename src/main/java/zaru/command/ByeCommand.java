package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;


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
     * Returns the goodbye message.
     *
     * @param tasks Current task list, unused by this command.
     * @return Goodbye response message.
     * @throws ZaruException If displaying the command response fails.
     */
    @Override
    public String execute(TaskList tasks) throws ZaruException {
        return "Bye. Hope to see you again soon!";
    }
}
