package managers;

import myLinkedList.MyLinkedList;
import tasks.Task;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{


    private final MyLinkedList<Task> historyList = new MyLinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            historyList.linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList.getTasks();
    }

    @Override
    public void remove(int id) {
        historyList.remove(id);
    }
}
