package com.research.serverfullmediumcomplexity.service;

import com.research.serverfullmediumcomplexity.dto.SaleDetailsDto;
import com.research.serverfullmediumcomplexity.model.SalesDetail;
import com.research.serverfullmediumcomplexity.repository.SalesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SalesDetailsService {

    @Autowired
    private SalesDetailsRepository salesDetailsRepository;

    public String saveSalesDetails(SaleDetailsDto saleDetails) {
        SalesDetail salesDetail = new SalesDetail(
                saleDetails.getId(),
                saleDetails.getProductName(),
                saleDetails.getUnitPrice(),
                saleDetails.getQuantity(),
                saleDetails.getSalesDate()
        );
        salesDetailsRepository.saveAndFlush(salesDetail);
        return "SalesDetails saved successfully";
    }

    public List<SalesDetail> getAllSalesDetails() {
        return salesDetailsRepository.findAll();
    }

    public HashMap<String, String> getAnalysis()  {

        List<SalesDetail> salesDetails = salesDetailsRepository.findAll();
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
}
