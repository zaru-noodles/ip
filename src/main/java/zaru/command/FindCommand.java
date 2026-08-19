package zaru.command;

import java.util.List;

import zaru.exception.ZaruException;
import zaru.task.Task;
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
        StringBuilder sb = new StringBuilder();
        List<Task> filteredTaskList = tasks.filterByTitle(target);

        for (Task task : filteredTaskList) {
            sb.append("   ").append(task.toString()).append("\n");
        }

        UI.sendMessage("Meow! Here are the matching tasks in your list:\n%s".formatted(sb));

    }
}
