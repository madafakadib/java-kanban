package managers.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.Epic;
import tasks.Task;

import java.io.IOException;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
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

        if (path.equals("/epics")) {
            List<Epic> epics = manager.getEpics();
            String json = gson.toJson(epics);
            sendText(exchange, json);
        } else if (path.startsWith("/epics/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    Epic epic = manager.getEpicByID(id);
                    String json = gson.toJson(epic);
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
        String body = readRequestBody(exchange);
        Epic epic = gson.fromJson(body, Epic.class);

        if (manager.intersection(epic)) {
            sendHasOverlaps(exchange);
            return;
        }

        manager.addEpic(epic);

        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/epics")) {
            manager.clearEpics();
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else if (path.startsWith("/epics/")) {
            String[] parts = path.split("/");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                try {
                    manager.deleteEpicByID(id);
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