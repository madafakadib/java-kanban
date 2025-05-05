import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

class TaskTest {

    @Test
    public void tasksAreEqualToEachOtherIfTheirIDEqual() {
        Task task1 = new Task(10, "qwerty", "qwerty", Status.NEW);
        Task task2 = new Task(10, "ytrewq", "ytrewq", Status.DONE);
        assertEquals(task1, task2);
    }

    @Test
    public void subtasksAreEqualToEachOtherIfTheirIDEqual() {
        Subtask subtask1 = new Subtask(10, 5, "qwerty", "qwerty", Status.NEW);
        Subtask subtask2 = new Subtask(10, 5, "ytrewq", "ytrewq", Status.DONE);
        assertEquals(subtask1, subtask2);
    }

    @Test
    public void epicsAreEqualToEachOtherIfTheirIDEqual() {
        Epic epic1 = new Epic(1, "qwerty", "qwerty", Status.NEW);
        Epic epic2 = new Epic(1, "ytrewq", "ytrewq", Status.IN_PROGRESS);
        assertEquals(epic1, epic2);
    }
}