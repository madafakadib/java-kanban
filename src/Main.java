import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskManager taskManager = new InMemoryTaskManager();

        taskManager.addTask(new Task("svadba", "dozhit"));
        //Thread.sleep(30000);
        taskManager.addEpic(new Epic("fgbjkh", "dsetrd", LocalDateTime.now()));
        //Thread.sleep(30000);
        taskManager.addSubtask(new Subtask(2, "qwerty", "ytrewq", LocalDateTime.now()));
        //Task task1 = taskManager.getTaskByID(1);
       // System.out.println(task1.getStartTime());

        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksByEpicID(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtask()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (int i = 0; i < manager.getHistory().size(); i++) {
            System.out.print(i + ": ");
            System.out.println(manager.getHistory().get(i));
        }
//        for (Task task : manager.getHistory()) {
//            System.out.println(task);
//        }
    }
}