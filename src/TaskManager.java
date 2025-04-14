import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void addTask(Task task){
        this.id++;
        task.setId(this.id);
        tasks.put(task.getId(), task);
    }

    public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }

    public void updateTask(Task task){
        tasks.replace(task.getId(), task);
    }

    public void clearTasks(){
        tasks.clear();
    }

    public Task getTaskByID(int id){
        return tasks.get(id);
    }

    public void deleteTaskByID(int id){
        tasks.remove(id);
    }

    public void addEpic(Epic epic){
        this.id++;
        epic.setId(this.id);
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getEpics(){
        return new ArrayList<>(epics.values());
    }

    public void updateEpic(Epic epic){
        epics.put(epic.getId(), epic);
        updateStatus(epic.getId());
    }

    public void clearEpics(){
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicByID(int id){
        return epics.get(id);
    }

    public void deleteEpicByID(int id){
        ArrayList<Subtask> subtaskArrayList = epics.get(id).getSubtaskArrayList();
        for (Subtask subtask : subtaskArrayList){
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
    }
    
    public void addSubtask(Subtask subtask){
        this.id++;
        subtask.setId(this.id);
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
    }

    public ArrayList<Subtask> getSubtask(){
        return new ArrayList<>(subtasks.values());
    }

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

    public void clearSubtasks(){
        subtasks.clear();
        for (Epic epic : epics.values()){
            epic.clearSubtaskArrayList();
            epic.setStatus(Status.NEW);
        }
    }

    public Subtask getSubtaskByID(int id){
        return subtasks.get(id);
    }

    public void deleteSubtaskByID(int id){

        Subtask subtask = subtasks.get(id);

        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        newSubtask.remove(subtask);
        epic.setSubtaskArrayList(newSubtask);

        subtasks.remove(id);

        updateStatus(epic.getId());
    }

    public ArrayList<Subtask> getSubtasksByEpicID(int id){
        Epic epic = epics.get(id);
        return epic.getSubtaskArrayList();
    }

    private void updateStatus(int id){
        Epic epic = getEpicByID(id);
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

        if (newCount == count && subtaskArrayList.isEmpty()){
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
}