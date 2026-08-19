package zaru.task;

import java.util.ArrayList;
import java.util.List;

import zaru.storage.Storage;
import zaru.exception.ZaruException;

public class TaskList {
    private List<Task> tasks;
    private Storage storage;

    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) throws ZaruException {
        tasks.add(task);
        storage.save(tasks);
    }

    public void delete(int index) throws ZaruException {
        tasks.remove(index - 1);
        storage.save(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public void complete(int index) throws ZaruException {
        tasks.get(index - 1).setCompleted(true);
        storage.save(tasks);
    }

    public void uncomplete(int index) throws ZaruException {
        tasks.get(index - 1).setCompleted(false);
        storage.save(tasks);
    }

    public String getTaskString(int index) {
        return tasks.get(index - 1).toString();
    }

    public void loadFromStorage() throws ZaruException {
        tasks = storage.load();
    }

    /**
    * Filters the task list by title.
    * @param target The target string to filter by.
     */
    public List<Task> filterByTitle(String target) {
        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(target.toLowerCase())) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }

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
