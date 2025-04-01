public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addEpic(new Epic("Переезд в питер", "нужно успеть выполнить все дела в Казани"));
        taskManager.addSubtask(new Subtask(1,"Попросить перевод", "Поговорить с шефом"));
        taskManager.addSubtask(new Subtask(1, "Поднакопить деньги", "для проживания"));

        System.out.println(taskManager.getEpics());

    }
}