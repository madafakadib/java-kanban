import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void addTask(Task task){
        task.setId(this.id);
        tasks.put(task.getId(), task);
        this.id++;
    }
    public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }
    public void updateTask(int id, Task task){
        task.setId(id);
        tasks.put(task.getId(), task);
    }
    public void clearTasks(){
        tasks.clear();
    }
    public Task getTaskByID(int id){
        return tasks.get(id);
    }
    public void deleteTaskByID(int id){
        System.out.println(id);
        tasks.remove(id);
    }

    public void addEpic(Epic epic){
        epic.setId(this.id);
        epics.put(epic.getId(), epic);
        this.id++;
    }
    public ArrayList<Epic> getEpics(){
        return new ArrayList<>(epics.values());
    }
    public void updateEpic(int id, Epic epic){
        epic.setId(id);
        epics.put(epic.getId(), epic);
    }
    public void clearEpics(){
        epics.clear();
        subtasks.clear();
    }
    public Epic getEpicByID(int id){
        return epics.get(id);
    }
    public void deleteEpicByID(int id){
        epics.remove(id);
        ArrayList<Subtask> hz = epics.get(id).getSubtaskArrayList();
        System.out.println(hz);
    }
    
    public void addSubtask(Subtask subtask){
        subtask.setId(this.id);
        subtasks.put(subtask.getId(), subtask);
        this.id++;

        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
    }
    public ArrayList<Subtask> getSubtask(){
        return new ArrayList<>(subtasks.values());
    }
    public void updateSubtask(int id, Subtask subtask){
        System.out.println("UPDATE - " + subtasks.get(id));

        Subtask updateSubtask = subtasks.get(id);

        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        newSubtask.add(subtask);
//        epic.setSubtaskArrayList(newSubtask);
    }
    public void clearSubtasks(int epicID){
        subtasks.clear();
        Epic epic = epics.get(epicID);
        epic.clearSubtaskArrayList();
        epic.setStatus(Status.NEW);
    }
    public Subtask getSubtaskByID(int id){
        return subtasks.get(id);
    }
    public void deleteSubtaskByID(int id){
        Subtask subtask = subtasks.get(id);
        subtasks.remove(id);

        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> newSubtask = epic.getSubtaskArrayList();
        newSubtask.remove(subtask);
        epic.setSubtaskArrayList(newSubtask);

    }
}