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
        task.setId(this.id++);
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
        this.id = 1;
    }
    public Task getTaskByID(int id){
        return tasks.get(id);
    }
    public void deleteTaskByID(int id){
        tasks.remove(id);
    }

    public void addEpic(Epic epic){
        epic.setId(this.id);
        epics.put(epic.getId(), epic);
        epic.setId(this.id++);
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
        this.id = 1;
    }
    public Epic getEpicByID(int id){
        return epics.get(id);
    }
    public void deleteEpicByID(int id){
        epics.remove(id);
    }
    
    public void addSubtask(Subtask subtask){
        subtask.setId(this.id);
        subtasks.put(subtask.getId(), subtask);
        subtask.setId(this.id++);
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
    }
    public ArrayList<Subtask> getSubtask(){
        return new ArrayList<>(subtasks.values());
    }
    public void updateSubtask(int id, Subtask subtask){
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
    }
    public void clearSubtasks(){
        subtasks.clear();
        this.id = 1;
    }
    public Subtask getSubtaskByID(int id){
        return subtasks.get(id);
    }
    public void deleteSubtaskByID(int id){
        subtasks.remove(id);
    }
}