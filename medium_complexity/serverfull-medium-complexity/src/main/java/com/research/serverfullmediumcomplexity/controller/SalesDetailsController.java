package com.research.serverfullmediumcomplexity.controller;

import com.research.serverfullmediumcomplexity.dto.SaleDetailsDto;
import com.research.serverfullmediumcomplexity.model.SalesDetail;
import com.research.serverfullmediumcomplexity.service.SalesDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sales-details")
public class SalesDetailsController {

    @Autowired
    private SalesDetailsService salesDetailsService;

    @PostMapping("/add")
    public String addSalesDetails(@RequestBody SaleDetailsDto saleDetailsDto){
        salesDetailsService.saveSalesDetails(saleDetailsDto);
        return "Sales details added successfully";
    }

    @GetMapping("/all")
    public List<SalesDetail> getAllSalesDetails(){
        return salesDetailsService.getAllSalesDetails();
    }

    @GetMapping("/analysis")
    public HashMap<String, String> getSalesAnalysis() {
        return salesDetailsService.getAnalysis();
    }
}
