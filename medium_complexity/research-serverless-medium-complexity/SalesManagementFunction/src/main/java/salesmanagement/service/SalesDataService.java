package salesmanagement.service;



import salesmanagement.dto.AnalyseResult;
import salesmanagement.dto.SaleDetailsDto;
import salesmanagement.model.SalesDetail;
import salesmanagement.repository.SalesDataRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SalesDataService {

    private static SalesDataService instance;
    private final SalesDataRepository salesDataRepository = SalesDataRepository.getInstance();


    public static SalesDataService getInstance() {
        if (instance == null) {
            instance = new SalesDataService();
        }
        return instance;
    }

    public HashMap<String, String> getAnalysis() throws SQLException {

        List<SalesDetail> salesDetails = salesDataRepository.getAllSalesDetails();
        //i want to analyse the sales data and return the result. i want to know every product monthly sales moneya and quantity. save it to analysisResult
        HashMap<String, String> analysisResult = new HashMap<>();
        if(!salesDetails.isEmpty()){
            for (SalesDetail salesDetail : salesDetails) {
                String productName = salesDetail.getProductName();
                double unitPrice = salesDetail.getUnitPrice();
                int quantity = salesDetail.getQuantity();
                double total = unitPrice * quantity;
                String key = productName + " " + salesDetail.getSalesDate().getMonth();
                if (analysisResult.containsKey(key)) {
                    String[] value = analysisResult.get(key).split(" ");
                    double totalMoney = Double.parseDouble(value[0]);
                    int totalQuantity = Integer.parseInt(value[1]);
                    totalMoney += total;
                    totalQuantity += quantity;
                    analysisResult.put(key, totalMoney + " " + totalQuantity);
                } else {
                    analysisResult.put(key, total + " " + quantity);
                }
            }
        }else{
            analysisResult.put("No sales data", "No sales data");
        }

        return analysisResult;
    }
    public String saveSalesData(SaleDetailsDto salesDetail) throws SQLException {
         salesDataRepository.saveSaleDetail(salesDetail);
         return "Shopping sales data saved successfully";
    }

    public List<SalesDetail> getAllSalesDetails() throws SQLException {
        return salesDataRepository.getAllSalesDetails();
    }

}
