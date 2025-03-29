import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int identeficator = 1;
    HashMap<Integer, Task> tasks = new HashMap<>();

    /**получение списка всех задача**/
    public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }
    /**удаление всех задача**/
    public void clearTasks(){
        tasks.clear();
        identeficator = 1;
    }
    /** получение по идентификатору **/
    public Task getById(int id){
        return tasks.get(id);
    }
    /**создание задача**/
    public void addTask(Task task){
        task.setId(identeficator);
        tasks.put(task.getId(), task);
        identeficator++;
    }
    /**обновление задачи**/
    public void updateTask(int id, Task task){
        task.setId(id);
        int taskId = task.getId();
        tasks.replace(taskId, task);
    }
    /**удаление по идентификатору**/
    public void deleteById(int id){
        tasks.remove(id);
        identeficator--;
    }

    public int getIdenteficator() {
        return identeficator;
    }

    public void setIdenteficator(int identeficator) {
        this.identeficator = identeficator;
    }
}