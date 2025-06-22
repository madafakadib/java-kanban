package node;

import tasks.Task;

public class Node<T> {
    private Task task;
    private Node<T> next;
    private Node<T> prev;

    public Node(Task task, Node<T> next, Node<T> prev) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    public Task getData() {
        return task;
    }

    public void setData(Task task) {
        this.task = task;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
