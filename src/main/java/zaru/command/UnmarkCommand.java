package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code unmark} command. */
public class UnmarkCommand extends Command {
    private final String taskNumber;

    /**
     * Creates an unmark command.
     *
     * @param taskNumber Task number entered by the user.
     */
    public UnmarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String getCommand() {
        return "unmark";
    }

    @Override
    public void execute(TaskList tasks, UI ui) throws ZaruException {
        int index = parseNumber(taskNumber);
        validateTaskNumber(tasks, index);
        tasks.uncomplete(index);
        ui.sendMessage("I've unmarked that task!\n%s".formatted(tasks.getTaskString(index)));
    }
}
