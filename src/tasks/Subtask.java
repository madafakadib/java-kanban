package tasks;

import status.Status;

import java.time.LocalDateTime;

public class Subtask extends Task {

    private int epicID;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Subtask(int epicID, String name, String description) {
        super(name, description);
        this.epicID = epicID;
    }

    public Subtask(int epicID, String name, String description, Status status) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public Subtask(int id, int epicID, String name, String description, Status status) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public Subtask(int epicID, String name, String description, LocalDateTime startTime) {
        super(name, description, startTime);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Status status, LocalDateTime startTime) {
        super(id, name, description, status, startTime);
    }

    public Subtask(int id, int epicId, String name, String description, Status status, LocalDateTime startTime) {
        super(id, epicId, name, description, status, startTime);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "id=" + getId() +
                ", epicID=" + epicID +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", startTime=" + getStartTime() +
                '}';
    }
}