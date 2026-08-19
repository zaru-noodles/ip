package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code mark} command. */
public class MarkCommand extends Command {
    private final String taskNumber;

    /**
     * Creates a mark command.
     *
     * @param taskNumber Task number entered by the user.
     */
    public MarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String getCommand() {
        return "mark";
    }

    @Override
    public void execute(TaskList tasks) throws ZaruException {
        int index = parseNumber(taskNumber);
        validateTaskNumber(tasks, index);
        tasks.complete(index);
        UI.sendMessage("Meow! I've marked that task as done!\n%s".formatted(tasks.getTaskString(index)));
    }
}
