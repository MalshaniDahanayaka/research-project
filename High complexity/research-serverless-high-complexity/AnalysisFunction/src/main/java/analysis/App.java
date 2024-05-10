package analysis;


import analysis.service.AvailableTimeSlotAnalysisService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private AvailableTimeSlotAnalysisService availableTimeSlotAnalysisService = AvailableTimeSlotAnalysisService.getInstance();
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
                    return getAnalysis(response);
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

    private APIGatewayProxyResponseEvent getAnalysis(APIGatewayProxyResponseEvent response) throws JsonProcessingException, SQLException {
        Map<String, Integer> value = availableTimeSlotAnalysisService.getAnalysis();
        String responseBody = convertToJson(value);
        return response.withStatusCode(200).withBody(responseBody);
    }

    public String convertToJson(Map<String, Integer> value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }

    public String convertHashMapToJson(HashMap<String, String> analysis) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(analysis);
    }

}
