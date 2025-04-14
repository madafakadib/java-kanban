import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private ArrayList<Subtask> subtaskArrayList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtaskArrayList.add(subtask);
    }

    public ArrayList<Subtask> getSubtaskArrayList() {
        return subtaskArrayList;
    }

    public void clearSubtaskArrayList() {
        subtaskArrayList.clear();
    }

    public void removeSubtask(int id) {
        subtaskArrayList.remove(id);
    }

    public void setSubtaskArrayList(ArrayList<Subtask> newSubtask) {
        subtaskArrayList = newSubtask;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskArrayList=" + subtaskArrayList +
                '}';
    }
}