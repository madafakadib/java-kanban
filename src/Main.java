public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(1, new Task("vika", "do smile", Status.NEW));
        taskManager.print();
    }
}
