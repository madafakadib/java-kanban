public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(1, new Task("Vika", "do smile", Status.NEW));
        taskManager.addTask(2, new Task("Denis", "marry", Status.NEW));

        taskManager.getTasks();

        taskManager.clearTasks();

        taskManager.addTask(1, new Task("Vika", "do smile", Status.NEW));
        taskManager.addTask(2, new Task("Denis", "marry", Status.NEW));
        taskManager.addTask(3, new Task("Lexa", "buy car", Status.NEW));
        taskManager.addTask(4, new Task("Andrew", "be happy", Status.NEW));

        taskManager.getById(4);

        taskManager.updateTask(2, new Task("Denis", "be happy", Status.IN_PROGRESS));

        taskManager.getTasks();

        taskManager.deleteById(2);
        taskManager.getTasks();
    }
}