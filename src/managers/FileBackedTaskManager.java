package managers;

import managers.InMemoryTaskManager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

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

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic");
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
        if (task instanceof Subtask subtask) {
            sbTask.append(subtask.getEpicID());
        }
        return sbTask.toString();
    }

    private Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String typeStr = parts[1];
        String name = parts[2];
        String statusStr = parts[3];
        String description = parts[4];

        Status status = Status.valueOf(statusStr);

        switch (typeStr) {
            case "Task":
                return new Task(id, name, description, status);
            case "Epic":
                return new Epic(id, name, description, status);
            case "Subtask":
                int epicId = Integer.parseInt(parts[5]);
                return new Subtask(id, epicId, name, description, status);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + typeStr);
        }
    }

    public void loadFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (br.ready()) {
                Task task = fromString(line);
                if (task instanceof Epic) {
                    epics.put(task.getId(), (Epic) task);
                } else if (task instanceof Subtask) {
                    subtasks.put(task.getId(), (Subtask) task);
                } else {
                    tasks.put(task.getId(), task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new ManagerSaveException("Error", e);
        }
    }

    public static void main(String[] args) {
        File file = new File("text.txt");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.addTask(new Task("Купить кольцо", "Купить обручальные кольца"));
        fileBackedTaskManager.addEpic(new Epic("Найти работу", "Я в питере без денег"));
        fileBackedTaskManager.addSubtask(new Subtask(2, "пойти в самокат", "зарабоать на еду"));
        String task = fileBackedTaskManager.toStr(new Task("Купить кольцо", "Купить обручальные кольца"));
        System.out.println(task);
        System.out.println(fileBackedTaskManager.fromString(task));
    }
}