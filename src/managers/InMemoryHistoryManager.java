package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final int MAX_SIZE_HISTORY = 10;

    private final List<Task> historyList = new ArrayList<>(MAX_SIZE_HISTORY);

    @Override
    public void add(Task task) {
        if (historyList.size() == MAX_SIZE_HISTORY) {
            historyList.removeFirst();
        }
           historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }
}
