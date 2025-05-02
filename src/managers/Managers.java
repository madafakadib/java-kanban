package managers;

public class Managers {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }


}
