import java.util.ArrayList;

public class Epic extends Task{

    ArrayList<Subtask> subtaskArrayList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask){
        subtaskArrayList.add(subtask);
    }

    public ArrayList<Subtask> getSubtaskArrayList(){
        return subtaskArrayList;
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