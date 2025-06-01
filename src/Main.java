import managers.InMemoryTaskManager;
import managers.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        taskManager.addTask(new Task("Тренировка", "утренняя тренировка"));
        taskManager.addTask(new Task("Приготовить", "приготовить гречку с фаршем"));
        taskManager.addEpic(new Epic("Переезд", "подготовить к переезду в питер"));
        taskManager.addSubtask(new Subtask(3, "Билеты", "купить билеты в питер"));
        taskManager.addSubtask(new Subtask(3, "Собрать вещи", "отправить их доставкой СДЕК"));
        taskManager.addEpic(new Epic("Предложение", "нужно сделать предложение Вике"));
        taskManager.addSubtask(new Subtask(6, "Купить кольцо", "найти золотое кольцо с бриллиантом"));
        taskManager.addSubtask(new Subtask(6, "Купить машину", "найти машину до 200к"));
        taskManager.updateTask(new Task(1, "Тренировка", "иду на тренировку", Status.IN_PROGRESS));
        taskManager.updateSubtask(new Subtask(4, 3, "Билеты", "билеты куплены", Status.DONE));
        taskManager.updateSubtask(new Subtask(5, 3, "Собрать вещи", "осталось чуть-чуть", Status.IN_PROGRESS));
        taskManager.updateSubtask(new Subtask(7, 6, "Купить кольцо", "кольцо куплено", Status.DONE));
        taskManager.updateTask(new Task(2, "Приготовить", "Сделал рис с курице", Status.DONE));
        taskManager.deleteSubtaskByID(7);
        taskManager.addSubtask(new Subtask(6, "qwerty", "zxcvb"));
        taskManager.updateSubtask(new Subtask(8, 6, "qwerty", "qwerty", Status.IN_PROGRESS));

        taskManager.getTaskByID(2);
        taskManager.getTaskByID(2);
        taskManager.getEpicByID(3);
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);

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