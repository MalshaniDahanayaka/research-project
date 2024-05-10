package salesmanagement;

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
import salesmanagement.dto.AnalyseResult;
import salesmanagement.dto.SaleDetailsDto;
import salesmanagement.model.SalesDetail;
import salesmanagement.service.SalesDataService;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private SalesDataService salesDataService = SalesDataService.getInstance();

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
                    if (path.equals("/salesdata")) {
                        return handleGetAllSalesData(response);
                    }
                    else if(path.equals("/analysis")){
                        return handleGetAnalysis(response);
                    }
                case "POST":
                    return handleSaveSaleDetail(body, response);
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


    private APIGatewayProxyResponseEvent handleGetAnalysis(APIGatewayProxyResponseEvent response) throws SQLException, JsonProcessingException {
        HashMap<String, String> analysis = salesDataService.getAnalysis();
        return response.withStatusCode(200).withBody(convertHashMapToJson(analysis));
    }
    private APIGatewayProxyResponseEvent handleGetAllSalesData(APIGatewayProxyResponseEvent response) throws JsonProcessingException, SQLException {
        List<SalesDetail> salesDetails = salesDataService.getAllSalesDetails();
        String responseBody = convertToJson(salesDetails);
        return response.withStatusCode(200).withBody(responseBody);
    }

    private APIGatewayProxyResponseEvent handleSaveSaleDetail(String body, APIGatewayProxyResponseEvent response) throws JsonProcessingException, SQLException {
        SaleDetailsDto salesDetail = parseTodoFromJson(body);
        salesDataService.saveSalesData(salesDetail);
        return response.withStatusCode(201).withBody("{\"message\": \"Shopping item created successfully\"}");
    }


    public String convertToJson(List<SalesDetail> salesDetails) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(salesDetails);
    }

    public String convertHashMapToJson(HashMap<String, String> analysis) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(analysis);
    }

    public SaleDetailsDto parseTodoFromJson(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SaleDetailsDto shoppingItem = mapper.readValue(body, SaleDetailsDto.class);
        return shoppingItem;
    }
}
