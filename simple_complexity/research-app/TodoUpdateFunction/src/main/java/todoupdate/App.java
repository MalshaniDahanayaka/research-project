package todoupdate;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import todoupdate.model.Todo;
import todoupdate.service.TodoService;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private TodoService todoService = TodoService.getInstance();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        String path = apiGatewayProxyRequestEvent != null ? apiGatewayProxyRequestEvent.getPath() : "";
        String httpMethod = apiGatewayProxyRequestEvent != null ? apiGatewayProxyRequestEvent.getHttpMethod() : "";
        String body = apiGatewayProxyRequestEvent != null ? apiGatewayProxyRequestEvent.getBody() : "";

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        if(path.equals("/todo")){
            try {
                String message = todoService.updateTodo(parseTodoFromJson(body));
                return  response
                        .withStatusCode(201)
                        .withBody(message);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return  response
                .withStatusCode(201)
                .withBody("{\"message\": \"Todo is not updated successfully\"}");


    }


    private Todo parseTodoFromJson(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Map todoMap = mapper.readValue(body, Map.class);
        String title = (String) todoMap.get("title");
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Missing required field: title");
        }

        String description = (String) todoMap.getOrDefault("description", null);
        boolean completed = (boolean) todoMap.getOrDefault("completed", false);

        int id = (int)todoMap.get("id");

        Todo newTodo = new Todo();
        newTodo.setId(id);
        newTodo.setTitle(title);
        newTodo.setDescription(description);
        newTodo.setCompleted(completed);

        return newTodo;
    }

}
