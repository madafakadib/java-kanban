import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();

    public void addTask(int id, Task task){
        tasks.put(id, task);
    }

    public void print(){
        System.out.println(tasks);
    }

}
