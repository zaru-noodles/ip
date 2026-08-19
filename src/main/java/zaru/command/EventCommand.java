package zaru.command;

import zaru.exception.ZaruException;
import zaru.task.Event;
import zaru.task.TaskList;
import zaru.ui.UI;

/** Executes the {@code event} command. */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Creates an event command.
     *
     * @param description Description of the event task.
     * @param from Event start time entered after {@code /from}.
     * @param to Event end time entered after {@code /to}.
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String getCommand() {
        return "event";
    }

    @Override
    public void execute(TaskList tasks) throws ZaruException {
        validateNonEmpty(description, "The description of an event cannot be empty.");
        validateNonEmpty(from, "Please provide an event start time using /from.");
        validateNonEmpty(to, "Please provide an event end time using /to.");
        tasks.add(new Event(description, from, to));
        UI.printAddTaskMessage(tasks);
    }
}
