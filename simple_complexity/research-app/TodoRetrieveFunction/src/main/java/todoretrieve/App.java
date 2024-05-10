package todoretrieve;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import todoretrieve.model.Todo;
import todoretrieve.service.TodoService;
import java.io.IOException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        if(path.equals("/todo")){
            try {
                List<Todo> todoList = todoService.getAllTodos();
                return  response
                        .withStatusCode(200)
                        .withBody(convertToJson(todoList));
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return  response
                .withStatusCode(201)
                .withBody("{\"message\": \"All Todo list fetch is failed\"}");

    }

    public String convertToJson(List<Todo> todos) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(todos);
    }

}
