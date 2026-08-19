package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code delete} command. */
public class DeleteCommand extends Command {
    private final String taskNumber;

    /**
     * Creates a delete command.
     *
     * @param taskNumber Task number entered by the user.
     */
    public DeleteCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Returns the command word represented by this command.
     *
     * @return The {@code delete} command word.
     */
    @Override
    public String getCommand() {
        return "delete";
    }

    /**
     * Validates the task number, removes the selected task, and displays it.
     *
     * @param tasks Current task list.
     * @throws ZaruException If the task number is invalid or deletion fails.
     */
    @Override
    public void execute(TaskList tasks) throws ZaruException {
        int index = parseNumber(taskNumber);
        validateTaskNumber(tasks, index);
        String deletedTask = tasks.getTaskString(index);
        tasks.delete(index);
        UI.sendMessage("I've deleted that task!\n%s".formatted(deletedTask));
    }
}
