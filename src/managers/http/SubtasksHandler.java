package managers.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.util.List;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager = new InMemoryTaskManager();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    exchange.sendResponseHeaders(405, 0);
                    exchange.close();
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/subtasks")) {
            List<Subtask> subtasks = manager.getSubtask();
            String json = gson.toJson(subtasks);
            sendText(exchange, json);
        } else if (path.startsWith("/subtasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    Subtask subtask = manager.getSubtaskByID(id);
                    String json = gson.toJson(subtask);
                    sendText(exchange, json);
                } catch (Exception e) {
                    sendNotFound(exchange);
                }
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String body = readRequestBody(exchange);
        Subtask subtask = gson.fromJson(body, Subtask.class);

        if (manager.intersection(subtask)) {
            sendHasOverlaps(exchange);
            return;
        }

        if (manager.intersection(subtask)) {
            sendHasOverlaps(exchange);
            return;
        }

        if (path.equals("/subtasks")) {
            manager.addTask(subtask);
        } else if (path.startsWith("/subtasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                manager.updateSubtask(subtask);
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }

        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/subtasks")) {
            manager.clearSubtasks();
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else if (path.startsWith("/subtasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    manager.deleteSubtaskByID(id);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception e) {
                    sendNotFound(exchange);
                }
                exchange.close();
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}