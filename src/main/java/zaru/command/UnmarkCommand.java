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

    /**
     * Returns the command word represented by this command.
     *
     * @return The {@code unmark} command word.
     */
    @Override
    public String getCommand() {
        return "unmark";
    }

    /**
     * Marks the selected task as incomplete and displays the updated task.
     *
     * @param tasks Current task list.
     * @throws ZaruException If the task number is invalid or unmarking fails.
     */
    @Override
    public void execute(TaskList tasks) throws ZaruException {
        int index = parseNumber(taskNumber);
        validateTaskNumber(tasks, index);
        tasks.uncomplete(index);
        UI.sendMessage("I've unmarked that task!\n%s".formatted(tasks.getTaskString(index)));
    }
}
