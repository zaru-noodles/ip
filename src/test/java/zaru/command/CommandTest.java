package zaru.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import zaru.exception.ZaruException;
import zaru.storage.Storage;
import zaru.task.TaskList;
import zaru.task.ToDo;

/** Tests command execution that changes task-list state. */
public class CommandTest {
    @TempDir
    Path temporaryDirectory;

    /** Verifies that executing a todo command adds an incomplete todo task. */
    @Test
    public void todoCommand_execute_addsTask() throws ZaruException {
        TaskList tasks = createTaskList("todo.txt");

        new TodoCommand("read book").execute(tasks);

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] read book", tasks.getTaskString(1));
    }

    /** Verifies that executing a deadline command adds a deadline task. */
    @Test
    public void deadlineCommand_execute_addsDeadline() throws ZaruException {
        TaskList tasks = createTaskList("deadline.txt");

        new DeadlineCommand("submit report", "2026-08-19 1430").execute(tasks);

        assertEquals(1, tasks.size());
        assertEquals("[D][ ] submit report (by: Aug 19 2026, 2:30PM)", tasks.getTaskString(1));
    }

    /** Verifies that executing an event command adds an event task. */
    @Test
    public void eventCommand_execute_addsEvent() throws ZaruException {
        TaskList tasks = createTaskList("event.txt");

        new EventCommand("project meeting", "2026-08-20 1000", "2026-08-20 1100").execute(tasks);

        assertEquals(1, tasks.size());
        assertEquals("[E][ ] project meeting (from: Aug 20 2026, 10:00AM) (to: Aug 20 2026, 11:00AM)",
                tasks.getTaskString(1));
    }

    /** Verifies that mark, unmark, and delete commands update task state. */
    @Test
    public void markUnmarkDeleteCommands_execute_updatesTaskList() throws ZaruException {
        TaskList tasks = createTaskList("state-changes.txt");
        new TodoCommand("read book").execute(tasks);

        new MarkCommand("1").execute(tasks);
        assertEquals("[T][x] read book", tasks.getTaskString(1));

        new UnmarkCommand("1").execute(tasks);
        assertEquals("[T][ ] read book", tasks.getTaskString(1));

        new DeleteCommand("1").execute(tasks);
        assertEquals(0, tasks.size());
    }

    /** Verifies that a deadline without a due date is rejected. */
    @Test
    public void deadlineCommand_missingDueDate_throwsException() {
        TaskList tasks = createTaskList("invalid-deadline.txt");

        ZaruException exception = assertThrows(ZaruException.class, () ->
                new DeadlineCommand("submit report", null).execute(tasks));

        assertEquals("Please provide a deadline date using /by.", exception.getMessage());
    }

    /** Verifies that a find command displays only tasks matching the search text. */
    @Test
    public void findCommand_execute_displaysMatchingTasks() throws ZaruException {
        TaskList tasks = createTaskList("find.txt");
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("return book"));
        tasks.add(new ToDo("watch movie"));

        String response = new FindCommand("book").execute(tasks);

        assertTrue(response.contains("[T][ ] read book"));
        assertTrue(response.contains("[T][ ] return book"));
        assertFalse(response.contains("watch movie"));
    }

    /** Creates a task list backed by a temporary save file. */
    private TaskList createTaskList(String fileName) {
        return new TaskList(new Storage(temporaryDirectory.resolve(fileName)));
    }
}
