package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import status.Status;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    public final Map<Integer, Task> tasks = new HashMap<>();
    public final Map<Integer, Epic> epics = new HashMap<>();
    public final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getHistoryDefault();
    private final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));


    @Override
    public void addTask(Task task) {
        this.id++;
        task.setId(this.id);
        if (intersection(task)) {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void updateTask(Task task) {
        Task oldTask = tasks.get(task.getId());
        prioritizedTasks.remove(oldTask);
        tasks.replace(task.getId(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskByID(int id) {

        historyManager.add(tasks.get(id));

        return tasks.get(id);
    }

    @Override
    public void deleteTaskByID(int id) {
        Task task = tasks.get(id);
        prioritizedTasks.remove(task);
        tasks.remove(id);
    }

    @Override
    public void addEpic(Epic epic) {
        this.id++;
        epic.setId(this.id);
        epics.put(epic.getId(), epic);
        updateTime(epic);
        prioritizedTasks.add(epic);
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        prioritizedTasks.remove(oldEpic);
        epics.put(epic.getId(), epic);
        updateStatus(epic.getId());
        updateTime(epic);
        prioritizedTasks.add(epic);
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicByID(int id) {

        historyManager.add(epics.get(id));

        return epics.get(id);
    }

    @Override
    public void deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        prioritizedTasks.remove(epic);
        ArrayList<Subtask> subtaskArrayList = epics.get(id).getSubtaskArrayList();
        for (Subtask subtask : subtaskArrayList) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        this.id++;
        subtask.setId(this.id);
        if (intersection(subtask)) {
            subtasks.put(subtask.getId(), subtask);
            prioritizedTasks.add(subtask);
        }

        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        updateTime(epic);
    }

    @Override
    public List<Subtask> getSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Task oldSubtask1 = subtasks.get(subtask.getId());
        prioritizedTasks.remove(oldSubtask1);
        prioritizedTasks.add(subtask);
        int epicID = subtask.getEpicID();
        int subtaskID = subtask.getId();

        Epic epic = epics.get(epicID);
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        Subtask oldSubtask = subtasks.get(subtaskID);
        newSubtask.remove(oldSubtask);
        newSubtask.add(subtask);
        epic.setSubtaskArrayList(newSubtask);

        subtasks.put(subtask.getId(), subtask);

        updateStatus(epicID);
        updateTime(epic);
    }

    @Override
    public void clearSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskArrayList();
            epic.setStatus(Status.NEW);
            epic.setStartTime(null);
            epic.setEndTime(null);
        }
    }

    @Override
    public Subtask getSubtaskByID(int id) {

        historyManager.add(subtasks.get(id));

        return subtasks.get(id);
    }

    @Override
    public void deleteSubtaskByID(int id) {

        Subtask subtask = subtasks.get(id);

        prioritizedTasks.remove(subtask);

        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        newSubtask.remove(subtask);
        epic.setSubtaskArrayList(newSubtask);

        subtasks.remove(id);

        updateStatus(epic.getId());
        updateTime(epic);
    }

    @Override
    public List<Subtask> getSubtasksByEpicID(int id) {
        Epic epic = epics.get(id);
        return epic.getSubtaskArrayList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private void updateStatus(int id) {
        Epic epic = getEpicID(id);
        ArrayList<Subtask> subtaskArrayList = epic.getSubtaskArrayList();

        int count = subtaskArrayList.size();
        int inProgressCount = 0;
        int doneCount = 0;
        int newCount = 0;

        for (Subtask subtask : subtaskArrayList) {
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                inProgressCount++;
            } else if (subtask.getStatus() == Status.DONE) {
                doneCount++;
            } else if (subtask.getStatus() == Status.NEW) {
                newCount++;
            }
        }

        if (newCount == count || subtaskArrayList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        if (doneCount == subtaskArrayList.size()) {
            epic.setStatus(Status.DONE);
            return;
        }
        if (inProgressCount >= 1) {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private void updateTime(Epic epic) {
        ArrayList<Subtask> subtaskArrayList = epic.getSubtaskArrayList();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        for (Subtask subtask : subtaskArrayList) {
            if (subtask.getStartTime() != null) {
                startTime = subtask.getStartTime();
            }
            if (subtask.getEndTime() != null) {
                endTime = subtask.getEndTime();
            }
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
    }

    private boolean intersection(Task task) {
        boolean intersection = false;
        for (Task otherTask : prioritizedTasks) {
            if (task.getStartTime() == null || otherTask.getStartTime() == null) {
                intersection = true;
            } else if (task.getEndTime().isBefore(otherTask.getStartTime())
                    || task.getEndTime().equals(otherTask.getStartTime())
                    || task.getStartTime().isAfter(otherTask.getEndTime())
                    || task.getStartTime().equals(otherTask.getStartTime())) {
                intersection = true;
            } else {
                intersection = false;
            }
        }
        return intersection;
    }

    private Epic getEpicID(int id) {
        return epics.get(id);
    }
}