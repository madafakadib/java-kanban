public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(new Task("Vika", "smile"));
        taskManager.addTask(new Task("Denis", "marry"));
        taskManager.addTask(new Task("Lexa", "buy car"));
        taskManager.addTask(new Task("Andrew", "be happy"));

        System.out.println(taskManager.getTasks());
        System.out.println();

        System.out.println(taskManager.getById(1));
        System.out.println();

        taskManager.updateTask(2, new Task("Denis", "be happy", Status.IN_PROGRESS));
        System.out.println(taskManager.getById(2));

        taskManager.deleteById(4);
        System.out.println(taskManager.getTasks());
        System.out.println();

        taskManager.addTask(new Task("Andrew", "be happy"));
        System.out.println(taskManager.getTasks());

        taskManager.clearTasks();
        System.out.println(taskManager.getTasks());
        System.out.println();

        taskManager.addTask(new Task("Переезд", "В Санкт-Петербург"));
        System.out.println(taskManager.getTasks());
    }
}