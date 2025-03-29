import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    //получение списка всех задача
    public void getTasks(){
        for (Task task : tasks.values()){
            System.out.println(task);
        }
    }
    //удаление всех задача
    public void clearTasks(){
        tasks.clear();
    }
    //получение по идентификатору
    public void getById(int id){
        System.out.println(tasks.get(id));
    }
    //создание задача
    public void addTask(int id, Task task){
        tasks.put(id, task);
    }
    //обновление задачи
    public void updateTask(int id, Task task){
        tasks.put(id, task);
    }
    //удаление по идентификатору
    public void deleteById(int id){
        tasks.remove(id);
    }
}