package managers.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.util.List;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
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

        if (path.equals("/tasks")) {
            List<Task> tasks = manager.getTasks();
            String json = gson.toJson(tasks);
            sendText(exchange, json);
        } else if (path.startsWith("/tasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    Task task = manager.getTaskByID(id);
                    String json = gson.toJson(task);
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
        Task task = gson.fromJson(body, Task.class);

        if (manager.intersection(task)) {
            sendHasOverlaps(exchange);
            return;
        }

        if (path.equals("/tasks")) {
            manager.addTask(task);
        } else if (path.startsWith("/tasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                manager.updateTask(task);
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

        if (path.equals("/tasks")) {
            manager.clearTasks();
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else if (path.startsWith("/tasks/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    manager.deleteTaskByID(id);
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