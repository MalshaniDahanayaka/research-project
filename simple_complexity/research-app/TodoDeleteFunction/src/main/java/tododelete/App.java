package tododelete;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tododelete.model.Todo;
import tododelete.service.TodoService;

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

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        if (httpMethod.equals("DELETE") && path.matches("^/todo/\\d+$")) {

            String[] pathSegments = path.split("/");
            String todoId = pathSegments.length >= 3 ? pathSegments[2] : null;

            if (todoId != null) {
                try {
                    String deletedTodoResponse = todoService.deleteTodo(todoId);
                    return response
                            .withStatusCode(200)
                            .withBody(deletedTodoResponse);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return response
                        .withStatusCode(400)
                        .withBody("Missing or invalid todo ID");
            }
        } else {
            return response
                    .withStatusCode(404)
                    .withBody("Not Found");
        }

    }
}
