package managers;

import status.Status;
import status.Type;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy");
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addTask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addTask(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskByID(int id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteSubtaskByID(int id) {
        super.deleteSubtaskByID(id);
        save();
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    break;
                }
                Task task = fromString(line);
                manager.addTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new ManagerSaveException("Error", e);
        }
        return manager;
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic,startTime,endTime");
            bw.newLine();

            for (Task task : getTasks()) {
                bw.write(toStr(task));
                bw.newLine();
            }

            for (Subtask subtask : getSubtask()) {
                bw.write(toStr(subtask));
                bw.newLine();
            }

            for (Epic epic : getEpics()) {
                bw.write(toStr(epic));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка исключения ", e);
        }
    }

    private String toStr(Task task) {
        String time = task.getStartTime().format(FORMATER);
        StringBuilder sbTask = new StringBuilder();
        sbTask.append(task.getId()).append(",");
        if (task instanceof Epic) {
            sbTask.append("EPIC").append(",");
        } else if (task instanceof Subtask) {
            sbTask.append("Subtask").append(",");
        } else {
            sbTask.append("Task").append(",");
        }
        sbTask.append(task.getName()).append(",");
        sbTask.append(task.getStatus()).append(",");
        sbTask.append(task.getDescription()).append(",");
        sbTask.append(time);
    //    sbTask.append(task.getEndTime()).append(",");
        if (task instanceof Subtask subtask) {
            sbTask.append(subtask.getEpicID());
        }
        return sbTask.toString();
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String typeStr = parts[1];
        String name = parts[2];
        String description = parts[4];
        Status status = Status.valueOf(parts[3]);
        LocalDateTime localDateTime = LocalDateTime.parse(parts[5], FORMATER);

        System.out.println(localDateTime);

        switch (Type.valueOf(parts[1])) {
            case TASK:
                Task task =  new Task(id, name, description, status, localDateTime);
                return task;
            case EPIC:
                Epic epic = new Epic(id, name, description, status, localDateTime);
                return epic;
            case SUBTASK:
                int epicId = Integer.parseInt(parts[6]);
                Subtask subtask = new Subtask(id, epicId, name, description, status, localDateTime);
                return subtask;
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + typeStr);
        }
    }

    public static void main(String[] args) {
        File file = new File("text.txt");
        TaskManager taskManager = new InMemoryTaskManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.addTask(new Task("Купить кольцо", "Купить обручальные кольца", LocalDateTime.now()));
    }
}