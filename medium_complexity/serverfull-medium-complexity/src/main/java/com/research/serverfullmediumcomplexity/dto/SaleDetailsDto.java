package com.research.serverfullmediumcomplexity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SaleDetailsDto {

    private Long id;
    private String productName;
    private double unitPrice;
    private int quantity;
    private Date salesDate;

}
