package zaru.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import zaru.exception.ZaruException;
import zaru.storage.Storage;

/** Tests task-list mutations and loading from persistent storage. */
public class TaskListTest {
    @TempDir
    Path temporaryDirectory;

    /** Verifies that adding and deleting tasks updates list contents and size. */
    @Test
    public void addAndDelete_tasks_updatesListContents() throws ZaruException {
        TaskList tasks = createTaskList("tasks.txt");

        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("return book"));
        assertEquals(2, tasks.size());
        assertEquals("[T][ ] read book", tasks.getTaskString(1));

        tasks.delete(1);

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] return book", tasks.getTaskString(1));
    }

    /** Verifies that completion state can be toggled for a task. */
    @Test
    public void completeAndUncomplete_task_updatesCompletionState() throws ZaruException {
        TaskList tasks = createTaskList("completion.txt");
        tasks.add(new ToDo("read book"));

        tasks.complete(1);
        assertEquals("[T][x] read book", tasks.getTaskString(1));

        tasks.uncomplete(1);
        assertEquals("[T][ ] read book", tasks.getTaskString(1));
    }

    /** Verifies that filtering returns tasks whose titles contain the target text. */
    @Test
    public void filterByTitle_matchingTitles_returnsMatchingTasks() throws ZaruException {
        TaskList tasks = createTaskList("filter-matches.txt");
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("return book"));
        tasks.add(new ToDo("watch movie"));

        List<Task> matchingTasks = tasks.filterByTitle("book");

        assertEquals(2, matchingTasks.size());
        assertEquals("read book", matchingTasks.get(0).getTitle());
        assertEquals("return book", matchingTasks.get(1).getTitle());
    }

    /** Verifies that filtering ignores differences in letter case. */
    @Test
    public void filterByTitle_differentLetterCase_returnsMatchingTasks() throws ZaruException {
        TaskList tasks = createTaskList("filter-case.txt");
        tasks.add(new ToDo("Read Book"));

        List<Task> matchingTasks = tasks.filterByTitle("book");

        assertEquals(1, matchingTasks.size());
        assertEquals("Read Book", matchingTasks.get(0).getTitle());
    }

    /** Verifies that filtering returns an empty list when no title matches. */
    @Test
    public void filterByTitle_noMatchingTitles_returnsEmptyList() throws ZaruException {
        TaskList tasks = createTaskList("filter-empty.txt");
        tasks.add(new ToDo("read book"));

        assertEquals(List.of(), tasks.filterByTitle("exercise"));
    }

    /** Verifies that saved tasks are loaded into a task list. */
    @Test
    public void loadFromStorage_savedTasks_populatesList() throws ZaruException {
        Storage storage = new Storage(temporaryDirectory.resolve("saved.txt"));
        storage.save(List.of(new ToDo("read book")));
        TaskList tasks = new TaskList(storage);

        tasks.loadFromStorage();

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] read book", tasks.getTaskString(1));
    }

    /** Creates a task list backed by a temporary save file. */
    private TaskList createTaskList(String fileName) {
        return new TaskList(new Storage(temporaryDirectory.resolve(fileName)));
    }
}
