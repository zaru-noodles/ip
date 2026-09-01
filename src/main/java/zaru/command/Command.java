package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.Task;
import zaru.task.TaskList;

/** Defines the common contract and validation helpers for user commands. */
public abstract class Command {
    /**
     * Returns the command word represented by this command.
     *
     * @return Lower-case command word.
     */
    public abstract String getCommand();

    /**
     * Executes this command using the application task list.
     *
     * @param tasks Current task list.
     * @return The response message for this command.
     * @throws ZaruException If command arguments or task operations are invalid.
     */
    public abstract String execute(TaskList tasks) throws ZaruException;

    /**
     * Parses and validates a user-provided task number.
     *
     * @param tasks Current task list.
     * @param numberText Task number entered by the user.
     * @return Valid one-based task number.
     * @throws ZaruException If the text is missing, is not a whole number, or is outside the task list.
     */
    protected static int parseTaskNumber(TaskList tasks, String numberText) throws ZaruException {
        validateNonEmpty(numberText, "Please provide a number.");

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new ZaruException("Number %s must be a valid number.".formatted(numberText));
        }

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ZaruException("Task number must be between 1 and %d!".formatted(tasks.size()));
        }

        return taskNumber;
    }

    /**
     * Adds a task and creates the standard task-added response.
     *
     * @param tasks Current task list.
     * @param task Task to add.
     * @return Task-added response message.
     * @throws ZaruException If the updated task list cannot be saved.
     */
    protected static String addTaskAndCreateResponse(TaskList tasks, Task task) throws ZaruException {
        tasks.add(task);
        int numberOfTasks = tasks.size();

        return "Oki! Adding this task:\n   %s\nYou now have %d task%s!".formatted(
                tasks.getTaskString(numberOfTasks),
                numberOfTasks,
                numberOfTasks == 1 ? "" : "s");
    }

    /**
     * Checks whether required command text is present.
     *
     * @param text Text to check.
     * @param errorMessage Message to show if the text is missing.
     * @throws ZaruException If the text is null or blank.
     */
    protected static void validateNonEmpty(String text, String errorMessage) throws ZaruException {
        if (text == null || text.isBlank()) {
            throw new ZaruException(errorMessage);
        }
    }
}
