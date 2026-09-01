package zaru.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import zaru.exception.ZaruException;
import zaru.storage.Storage;

/** Manages the current tasks and persists changes through a storage helper. */
public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    /**
     * Creates an empty task list backed by the given storage helper.
     *
     * @param storage Storage used when the list changes or loads data.
     */
    public TaskList(Storage storage) {
        assert storage != null : "Task list requires a storage helper.";

        this.storage = storage;
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task and saves the updated list.
     *
     * @param task Task to add.
     * @throws ZaruException If the updated list cannot be saved.
     */
    public void add(Task task) throws ZaruException {
        assert task != null : "Only constructed tasks should be added.";

        tasks.add(task);
        storage.save(tasks);
    }

    /**
     * Deletes the one-based task at the given index and saves the list.
     *
     * @param index One-based task index.
     * @throws ZaruException If the updated list cannot be saved.
     */
    public void delete(int index) throws ZaruException {
        assert isValidIndex(index) : "Task index should have been validated by the command.";

        tasks.remove(index - 1);
        storage.save(tasks);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Marks the one-based task at the given index complete and saves the list.
     *
     * @param index One-based task index.
     * @throws ZaruException If the updated list cannot be saved.
     */
    public void complete(int index) throws ZaruException {
        assert isValidIndex(index) : "Task index should have been validated by the command.";

        tasks.get(index - 1).setCompleted(true);
        storage.save(tasks);
    }

    /**
     * Marks the one-based task at the given index incomplete and saves the list.
     *
     * @param index One-based task index.
     * @throws ZaruException If the updated list cannot be saved.
     */
    public void uncomplete(int index) throws ZaruException {
        assert isValidIndex(index) : "Task index should have been validated by the command.";

        tasks.get(index - 1).setCompleted(false);
        storage.save(tasks);
    }

    /**
     * Returns the formatted task at a one-based index.
     *
     * @param index One-based task index.
     * @return Formatted task text.
     */
    public String getTaskString(int index) {
        assert isValidIndex(index) : "Task index should refer to an existing task.";

        return tasks.get(index - 1).toString();
    }

    /**
     * Replaces the current list with tasks loaded from storage.
     *
     * @throws ZaruException If saved task data cannot be loaded.
     */
    public void loadFromStorage() throws ZaruException {
        List<Task> loadedTasks = storage.load();
        assert loadedTasks != null : "Storage should always return a task list.";

        tasks.clear();
        tasks.addAll(loadedTasks);
    }

    /**
     * Filters the task list by title.
     *
     * @param target The target string to filter by.
     * @return Tasks whose titles contain the target string, ignoring case.
     */
    public List<Task> filterByTitle(String target) {
        assert target != null : "Search target should have been validated by the command.";

        List<Task> filteredTasks = new ArrayList<>();
        String normalizedTarget = target.toLowerCase(Locale.ROOT);

        for (Task task : tasks) {
            String normalizedTitle = task.getTitle().toLowerCase(Locale.ROOT);
            if (normalizedTitle.contains(normalizedTarget)) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }

    /**
     * Checks whether an index refers to a task using the list's one-based indexing convention.
     *
     * @param index One-based task index.
     * @return {@code true} if the index refers to an existing task.
     */
    private boolean isValidIndex(int index) {
        return index >= 1 && index <= tasks.size();
    }

    /**
     * Returns all tasks in display order with one-based numbering.
     *
     * @return Formatted multi-line task list.
     */
    @Override
    public String toString() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append(i + 1).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                taskList.append("\n");
            }
        }
        return taskList.toString();
    }
}
