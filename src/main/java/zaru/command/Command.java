package zaru.command;

import zaru.exception.ZaruException;
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
     * Converts a user-provided number into an integer.
     *
     * @param num Text entered after a mark or unmark command.
     * @return The parsed task number.
     * @throws ZaruException If the text is missing or is not a whole number.
     */
    protected static int parseNumber(String num) throws ZaruException {
        validateNonEmpty(num, "Please provide a number.");
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new ZaruException("Number %s must be a valid number.".formatted(num));
        }
    }

    /**
     * Checks whether a task number refers to an existing task.
     *
     * @param tasks Current task list.
     * @param taskNumber One-based task number entered by the user.
     * @throws ZaruException If the number is outside the task list.
     */
    protected static void validateTaskNumber(TaskList tasks, int taskNumber) throws ZaruException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ZaruException("zaru.task.Task number must be between 1 and %d!".formatted(tasks.size()));
        }
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
