import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void complete(int index) {
        tasks.get(index - 1).setCompleted(true);
    }
    public String getTaskString(int index) {
        return tasks.get(index - 1).toString();
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
