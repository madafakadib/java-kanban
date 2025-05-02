package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);

    List<Task> getTasks();

    void updateTask(Task task);

    void clearTasks();

    Task getTaskByID(int id);

    void deleteTaskByID(int id);

    void addEpic(Epic epic);

    List<Epic> getEpics();

    void updateEpic(Epic epic);

    void clearEpics();

    Epic getEpicByID(int id);

    void deleteEpicByID(int id);

    void addSubtask(Subtask subtask);

    List<Subtask> getSubtask();

    void updateSubtask(Subtask subtask);

    void clearSubtasks();

    Subtask getSubtaskByID(int id);

    void deleteSubtaskByID(int id);

    List<Subtask> getSubtasksByEpicID(int id);

    List<Task> getHistory();
}
