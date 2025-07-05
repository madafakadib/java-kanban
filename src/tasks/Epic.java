package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

import status.Status;

public class Epic extends Task {

    private ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status, ArrayList<Subtask> subtaskArrayList) {
        super(id, name, description, status);
    }

    public Epic(int id, String name, String desciption, Status status) {
        super(id, name, desciption, status);
    }

    public Epic(int id, String name, String desciption, Status status, LocalDateTime startTime) {
        super(id, name, desciption, status, startTime);
    }

    public Epic(String name, String description, LocalDateTime startTime) {
        super(name, description, startTime);
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
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskArrayList=" + subtaskArrayList.size() +
                ", startTime=" + getStartTime() +
                '}';
    }
}