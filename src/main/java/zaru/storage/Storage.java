package zaru.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import zaru.exception.ZaruException;
import zaru.parser.DateTimeParser;
import zaru.task.Deadline;
import zaru.task.Event;
import zaru.task.Task;
import zaru.task.ToDo;

/**
 * Handles loading tasks from and saving tasks to the hard disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a storage helper that reads from and writes to the given file path.
     *
     * @param filePath Location of the save file.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads saved tasks from the hard disk.
     *
     * @return zaru.task.Task list restored from the save file, or an empty list if the file does not exist.
     * @throws ZaruException If the file cannot be read or contains invalid task data.
     */
    public List<Task> load() throws ZaruException {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) {
                    continue;
                }
                tasks.add(fileStringToTask(line));
            }
        } catch (IOException e) {
            throw new ZaruException("Failed to load tasks from file!");
        }

        return tasks;
    }

    /**
     * Saves all tasks to the hard disk.
     *
     * @param tasks zaru.task.Task list to save.
     * @throws ZaruException If the save file cannot be written.
     */
    public void save(List<Task> tasks) throws ZaruException {
        StringBuilder contents = new StringBuilder();

        for (Task t : tasks) {
            contents.append(taskToFileString(t)).append(System.lineSeparator());
        }

        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, contents.toString());
        } catch (IOException e) {
            throw new ZaruException("Could not write save file.");
        }
    }

    /**
     * Converts a task object into one line of save-file text.
     *
     * @param task zaru.task.Task to convert.
     * @return Save-file representation of the task.
     * @throws ZaruException If the task type is not supported by the save format.
     */
    private String taskToFileString(Task task) throws ZaruException {
        String completionMarker = task.isCompleted() ? "1" : "0";

        return switch (task) {
            case ToDo toDo -> "T | %s | %s".formatted(completionMarker, toDo.getTitle());
            case Deadline deadline ->
                    "D | %s | %s | %s".formatted(
                            completionMarker,
                            deadline.getTitle(),
                            DateTimeParser.formatForStorage(deadline.getDueDate()));
            case Event event -> "E | %s | %s | %s | %s".formatted(
                    completionMarker,
                    event.getTitle(),
                    DateTimeParser.formatForStorage(event.getFrom()),
                    DateTimeParser.formatForStorage(event.getTo()));
            default -> throw new ZaruException("Unknown task type!");
        };
    }

    /**
     * Converts one line of save-file text into a task object.
     *
     * @param line One line from the save file.
     * @return zaru.task.Task represented by the line.
     * @throws ZaruException If the line does not match the expected save format.
     */
    private Task fileStringToTask(String line) throws ZaruException {
        String[] parts = line.split("\\s*\\|\\s*", -1);
        if (parts.length < 3) {
            throw new ZaruException("Invalid task data in save file!");
        }

        String taskType = parts[0];
        boolean isCompleted = parseCompleted(parts[1]);
        String title = parts[2];

        switch (taskType) {
        case "T" -> {
            validatePartCount(parts, 3);
            return new ToDo(title, isCompleted);
        }
        case "D" -> {
            validatePartCount(parts, 4);
            return new Deadline(title, isCompleted, parts[3]);
        }
        case "E" -> {
            validatePartCount(parts, 5);
            return new Event(title, isCompleted, parts[3], parts[4]);
        }
        default -> throw new ZaruException("Unknown task type!");
        }
    }

    /**
     * Converts a saved done marker into a boolean value.
     *
     * @param text Saved done marker, either {@code 1} or {@code 0}.
     * @return True if the task was done.
     * @throws ZaruException If the marker is not valid.
     */
    private boolean parseCompleted(String text) throws ZaruException {
        return switch (text) {
        case "1" -> true;
        case "0" -> false;
        default -> throw new ZaruException("Invalid task status in save file!");
        };
    }

    /**
     * Checks that a saved task has the expected number of fields.
     *
     * @param parts Fields parsed from one save-file line.
     * @param expected Expected number of fields.
     * @throws ZaruException If the field count is wrong.
     */
    private void validatePartCount(String[] parts, int expected) throws ZaruException {
        if (parts.length != expected) {
            throw new ZaruException("Invalid task data in save file!");
        }
    }
}
