package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code find} command. */
public class FindCommand extends Command {
    private final String target;

    /**
     * Creates a find command.
     *
     * @param target Target entered by the user for the search.
     */
    public FindCommand(String target) {
        this.target = target;
    }

    @Override
    public String getCommand() {
        return "find";
    }

    @Override
    public void execute(TaskList tasks) throws ZaruException {
        validateNonEmpty(target, "Include a search target!");
        UI.sendMessage("Meow! Here are the matching tasks in your list:\n%s".formatted(tasks.filter(target)));
    }
}
