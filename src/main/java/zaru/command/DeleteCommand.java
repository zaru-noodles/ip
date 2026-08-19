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

    @Override
    public String getCommand() {
        return "delete";
    }

    @Override
    public void execute(TaskList tasks, UI ui) throws ZaruException {
        int index = parseNumber(taskNumber);
        validateTaskNumber(tasks, index);
        String deletedTask = tasks.getTaskString(index);
        tasks.delete(index);
        ui.sendMessage("I've deleted that task!\n%s".formatted(deletedTask));
    }
}
