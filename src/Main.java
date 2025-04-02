public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.addEpic(new Epic("Denis", "Sitnov"));
        taskManager.addEpic(new Epic("Vika", "Sitnova"));
        taskManager.addSubtask(new Subtask(1, "qwerty", "ewqrt"));
        taskManager.addSubtask(new Subtask(1, "fsdfgsad", "egdfgwqrt"));
        taskManager.addSubtask(new Subtask(2, "j;pilmjynhbgfvd", "vfcdsx"));
        taskManager.addSubtask(new Subtask(2, "mjnhgbfv", "nhtbgf"));

        System.out.println(taskManager.getEpics());
     //   System.out.println(taskManager.getSubtask());
        taskManager.deleteSubtaskByID(3);
     //   System.out.println(taskManager.getSubtask());
        System.out.println(taskManager.getEpics());
        taskManager.updateSubtask(4, new Subtask(1, "Viktoriya", "Marryme"));
        System.out.println(taskManager.getSubtask());
    }
}