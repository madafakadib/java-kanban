package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import status.Status;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getHistoryDefault();


    @Override
    public void addTask(Task task){
        this.id++;
        task.setId(this.id);
        tasks.put(task.getId(), task);
    }

    @Override
    public List<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void updateTask(Task task){
        tasks.replace(task.getId(), task);
    }

    @Override
    public void clearTasks(){
        tasks.clear();
    }

    @Override
    public Task getTaskByID(int id){

        historyManager.add(tasks.get(id));

        return tasks.get(id);
    }

    @Override
    public void deleteTaskByID(int id){
        tasks.remove(id);
    }

    @Override
    public void addEpic(Epic epic){
        this.id++;
        epic.setId(this.id);
        epics.put(epic.getId(), epic);
    }

    @Override
    public List<Epic> getEpics(){
        return new ArrayList<>(epics.values());
    }

    @Override
    public void updateEpic(Epic epic){
        epics.put(epic.getId(), epic);
        updateStatus(epic.getId());
    }

    @Override
    public void clearEpics(){
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicByID(int id){

        historyManager.add(epics.get(id));

        return epics.get(id);
    }

    @Override
    public void deleteEpicByID(int id){
        ArrayList<Subtask> subtaskArrayList = epics.get(id).getSubtaskArrayList();
        for (Subtask subtask : subtaskArrayList){
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
    }

    @Override
    public void addSubtask(Subtask subtask){
        this.id++;
        subtask.setId(this.id);
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
    }

    @Override
    public List<Subtask> getSubtask(){
                return new ArrayList<>(subtasks.values());
    }

    @Override
    public void updateSubtask(Subtask subtask){
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
    }

    @Override
    public void clearSubtasks(){
        subtasks.clear();
        for (Epic epic : epics.values()){
            epic.clearSubtaskArrayList();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public Subtask getSubtaskByID(int id){

        historyManager.add(subtasks.get(id));

        return subtasks.get(id);
    }

    @Override
    public void deleteSubtaskByID(int id){

        Subtask subtask = subtasks.get(id);

        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        newSubtask.remove(subtask);
        epic.setSubtaskArrayList(newSubtask);

        subtasks.remove(id);

        updateStatus(epic.getId());
    }

    @Override
    public List<Subtask> getSubtasksByEpicID(int id){
        Epic epic = epics.get(id);
        return epic.getSubtaskArrayList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateStatus(int id){
        Epic epic = getEpicID(id);
        ArrayList<Subtask> subtaskArrayList = epic.getSubtaskArrayList();

        int count = subtaskArrayList.size();
        int inProgressCount = 0;
        int doneCount = 0;
        int newCount = 0;

        for (Subtask subtask : subtaskArrayList){
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                inProgressCount++;
            } else if (subtask.getStatus() == Status.DONE) {
                doneCount++;
            } else if (subtask.getStatus() == Status.NEW) {
                newCount++;
            }
        }

        if (newCount == count || subtaskArrayList.isEmpty()){
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

    private Epic getEpicID(int id) {
        return epics.get(id);
    }
}