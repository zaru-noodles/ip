import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Iterable<Task> {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int index) {
        tasks.remove(index - 1);
    }

    public int size() {
        return tasks.size();
    }

    public void complete(int index) {
        tasks.get(index - 1).setCompleted(true);
    }

    public void uncomplete(int index) {
        tasks.get(index - 1).setCompleted(false);
    }

    public String getTaskString(int index) {
        return tasks.get(index - 1).toString();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
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
