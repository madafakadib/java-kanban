package mylinkedlist;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyLinkedList<T extends Task> {
    public Node<T> head;
    public Node<T> tail;
    private final HashMap<Integer, Node<T>> historyMap = new HashMap<>();

    public void linkLast(T data) {
        final Node<T> oldTail = tail;
        final Node<T> newNode = new Node<>(data, null, tail);

        tail = newNode;

        if (oldTail != null) {
            oldTail.setNext(newNode);
        } else {
            head = newNode;
        }

        historyMap.put(newNode.getData().getId(), newNode);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<T> node = head;

        while (node != null) {
            tasks.add(node.getData());
            node = node.getNext();
        }
        return tasks;
    }

    private void removeNode(Node<T> node) {

        Node<T> prevNode = node.getPrev();
        Node<T> nextNode = node.getNext();
        historyMap.remove(node.getData().getId());

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.setNext(nextNode);
            node.setPrev(null);
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.setPrev(prevNode);
            node.setNext(null);
        }
    }

    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
    }
}
