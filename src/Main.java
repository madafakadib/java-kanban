public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(new Task("Тренировка", "утренняя тренировка"));
        taskManager.addTask(new Task("Приготовить", "приготовить гречку с фаршем"));
        taskManager.addEpic(new Epic("Переезд", "подготовить к переезду в питер"));
        taskManager.addSubtask(new Subtask(3, "Билеты", "купить билеты в питер"));
        taskManager.addSubtask(new Subtask(3, "Собрать вещи", "отправить их доставкой СДЕК"));
        taskManager.addEpic(new Epic("Предложение", "нужно сделать предложение Вике"));
        taskManager.addSubtask(new Subtask(6, "Купить кольцо", "найти золотое кольцо с бриллиантом"));
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        taskManager.updateTask(1, new Task("Тренировка", "иду на тренировку", Status.IN_PROGRESS));
        taskManager.updateSubtask(4, new Subtask(3, "Билеты", "билеты куплены", Status.DONE));
        taskManager.updateSubtask(5, new Subtask(3, "Собрать вещи", "осталось чуть-чуть", Status.IN_PROGRESS));
        taskManager.updateSubtask(7, new Subtask(6,"Купить кольцо", "кольцо куплено", Status.DONE));
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
    }
}