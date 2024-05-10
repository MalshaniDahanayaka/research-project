package shoppingitems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shoppingitems.model.ShoppingItem;
import shoppingitems.service.ShoppingItemService;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private ShoppingItemService shoppingItemService = ShoppingItemService.getInstance();

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

        try {
            switch (httpMethod) {
                case "GET":
                    if (path.equals("/shoppingitems")) {
                        return handleGetAllShoppingItems(response);
                    }
                case "POST":
                    return handleCreateShoppingItem(body, response);
                default:
                    return response
                            .withStatusCode(405) // Method Not Allowed
                            .withBody("{\"message\": \"Unsupported HTTP method\"}");
            }
        } catch (Exception e) {
            context.getLogger().log(e.getMessage());
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }

    }

    private APIGatewayProxyResponseEvent handleGetAllShoppingItems(APIGatewayProxyResponseEvent response) throws JsonProcessingException, SQLException {
        List<ShoppingItem> shoppingItems = shoppingItemService.getAllShoppingItems();
        String responseBody = convertToJson(shoppingItems);
        return response.withStatusCode(200).withBody(responseBody);
    }

    private APIGatewayProxyResponseEvent handleCreateShoppingItem(String body, APIGatewayProxyResponseEvent response) throws JsonProcessingException, SQLException {
        ShoppingItem shoppingItem = parseTodoFromJson(body);
        shoppingItemService.saveShoppingItem(shoppingItem);
        return response.withStatusCode(201).withBody("{\"message\": \"Shopping item created successfully\"}");
    }


    public String convertToJson(List<ShoppingItem> shoppingItems) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(shoppingItems);
    }

    public ShoppingItem parseTodoFromJson(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ShoppingItem shoppingItem = mapper.readValue(body, ShoppingItem.class);
        return shoppingItem;
    }
}
